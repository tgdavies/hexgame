package org.kablambda.hexgame.wargame;

import org.kablambda.hexgame.HexAddress;
import org.kablambda.hexgame.HexMap;
import org.kablambda.hexgame.HexPixelCalculator;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.kablambda.hexgame.wargame.UnitParameters.DEFAULT;

/**
 * state of a Unit
 */
public class Unit {
    private final Team team;
    private boolean selected;
    private final ConstrainedDouble supply = ConstrainedDouble.unitInterval(1.0);
    private final ConstrainedDouble fatigue = ConstrainedDouble.unitInterval(0.0);
    private HexAddress goal;
    private HexAddress location;
    private static HexPixelCalculator calculator;
    private static HexMap<HexState> map;
    private static final UnitParameters params = DEFAULT;
    private UnitState state = new Idle();

    public Unit(Team team, HexAddress address, HexPixelCalculator calc, HexMap<HexState> map) {
        this.team = team;
        this.location = address;
        calculator = calc;
        this.map = map;
    }

    public HexAddress getGoal() {
        return goal;
    }

    public HexAddress getLocation() {
        return location;
    }

    public boolean getSelected() {
        return selected;
    }

    public UnitState getState() {
        return state;
    }

    public Team getTeam() {
        return team;
    }

    public double getSupply() {
        return supply.get();
    }

    public double getFatigue() {
        return fatigue.get();
    }

    public void setGoal(HexAddress goal) {
        this.goal = goal;
        state = new Moving();
    }

    public void setLocation(HexAddress location) {
        map.get(getLocation()).ifPresent(s -> s.setUnit(null));
        this.location = location;
        HexState s = map.get(getLocation()).orElse(new HexState());
        s.getUnit().ifPresent(u -> {throw new RuntimeException("Unexpected unit in " + location); });
        s.setUnit(this);
        map.put(getLocation(), s);
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setSupply(double supply) {
        this.supply.set(supply);
    }

    public void setFatigue(double fatigue) {
        this.fatigue.set(fatigue);
    }

    public void tick() {
        // we always recover fatigue
        fatigue.dec(params.fatigueRecoveryPerTick());
        state.tick(this);
//        if (goal == null || fatigue.isMax() /*|| supply.isMin()*/) {
//            fatigue.dec(params.fatigueRecoveryPerTick());
//        } else {
//            //fatigue.inc(params.movementFatiguePerTick());
//            supply.dec(params.movementSupplyUsePerTick());
//            move.inc(1.0 / params.ticksPerMove());
//            if (move.isMax()) {
//                attemptMove();
//            }
//        }
    }


    private static Comparator<? super HexAddress> destinationHexComparator(HexAddress goal) {
        return Comparator.comparingInt(l -> calculator.distance(l, goal));
    }



    private double power() {
        return supply.get() + fatigue.map(f -> f / 5);
    }

    @Override
    public String toString() {
        return "Unit{" +
               "team=" + team +
               ", selected=" + selected +
               ", supply=" + supply +
               ", fatigue=" + fatigue +
               ", goal=" + goal +
               ", location=" + location +
               ", state=" + state +
               '}';
    }

    private interface UnitState {
        void tick(Unit u);
    }

    private static class Idle implements UnitState {

        public Idle() {
        }

        @Override
        public void tick(Unit u) {
            if (u.getGoal() != null && !u.getGoal().equals(u.getLocation())) {
                u.setState(new Moving());
            }
        }
    }

    private static class Moving implements UnitState {

        private int ticksUntilMove = params.ticksPerMove();

        public Moving() {
        }

        @Override
        public void tick(Unit u) {
            if (u.getGoal() == null || u.getLocation().equals(u.getGoal())) {
                u.setState(new Idle());
                u.setGoal(null);
            } else if (ticksUntilMove <= 0) {
                // we'll try to move each tick after we finish the countdown
                findValidMoveHex(u, u.getGoal()).ifPresent(ha -> {
                    moveTo(ha, u);
                });
                ticksUntilMove = params.ticksPerMove();
            } else {
                ticksUntilMove--;
            }
        }

        private void moveTo(HexAddress d, Unit u) {
            Optional<Unit> enemy = map.get(d).flatMap(s -> s.getUnit());
            if (enemy.isEmpty()) {
                // move to an empty hex
                u.setLocation(d);
            } else if (u.supply.gt(0.2)) {
                Unit enemyUnit = enemy.get();
                UnitState fighting = new Fighting(u, enemyUnit, d);
                u.setState(fighting);
                enemyUnit.setState(fighting);
            }
            u.fatigue.inc(params.movementFatiguePerTick() * params.ticksPerMove());
        }

        private Optional<HexAddress> findValidMoveHex(Unit movingUnit, HexAddress goal) {
            List<HexAddress> candidates = calculator.surroundingHexes(movingUnit.getLocation(), map);
            Collections.shuffle(candidates);

            return candidates.stream()
                    .filter(l -> map.isValidAddress(l))
                    .filter(l -> map.get(l).map(s -> s.getUnit().map(u -> u.getTeam() != movingUnit.getTeam()).orElse(true)).orElse(true))
                    .sorted(destinationHexComparator(goal))
                    .findFirst();
        }
    }

    public static class Fighting implements UnitState {
        private final Unit attacker;
        private final Unit defender;
        private final HexAddress attackersTargetHex;
        private int ticksUntilResolution;

        public Fighting(Unit attacker, Unit defender, HexAddress attackersTargetHex) {
            this.attacker = attacker;
            this.defender = defender;
            this.attackersTargetHex = attackersTargetHex;
            ticksUntilResolution = params.ticksPerMove();
        }

        public Unit getAttacker() {
            return attacker;
        }

        public Unit getDefender() {
            return defender;
        }

        public HexAddress getAttackersTargetHex() {
            return attackersTargetHex;
        }

        @Override
        public void tick(Unit u) {
            if (u != attacker) {
                // attacker does all combat resolution
                return;
            }
            if (ticksUntilResolution <= 0) {
                battle(attacker, defender, attackersTargetHex);
                attacker.setState(new Idle());
                defender.setState(new Idle());
            } else {
                ticksUntilResolution--;
            }
        }

        private void battle(Unit attacker, Unit defender, HexAddress attackersTargetHex) {
            if (attacker.supply.lt(0.2)) {
                return;
            }
            attacker.supply.apply(s -> s * 0.8);
            defender.supply.apply(s -> s * 0.8);
            if (defender.getGoal() != null) {
                if (calculator.closest(defender.getGoal(), attacker.getLocation(), defender.getLocation()) == attacker.getLocation() && defender.supply.gt(0.2)) {
                    // defender also attacking
                    if (attacker.power() > defender.power()) {
                        resolve(attacker, defender, attackersTargetHex);
                    } else {
                        resolve(defender, attacker, attacker.getLocation());
                    }
                } else {
                    // defender retreating
                    if (attacker.power() > 0.8 * defender.power()) {
                        resolve(attacker, defender, attackersTargetHex);
                    }
                }
            } else {
                if (attacker.power() > 1.25 * defender.power()) {
                    resolve(attacker, defender, attackersTargetHex);
                }
            }
        }

        private void resolve(Unit winner, Unit loser, HexAddress winnersTargetHex) {
            if (retreat(loser, winner.getLocation())) {
                if (map.get(winnersTargetHex).flatMap(s -> s.getUnit()).isEmpty()) {
                    winner.setLocation(winnersTargetHex);
                }
            } else {
                loser.supply.apply(s -> s/2);
            }
        }

        private boolean retreat(Unit defender, HexAddress enemyLocation) {
            var retreatLocation = calculator.surroundingHexes(defender.getLocation(), map).stream()
                    .filter(l -> !l.equals(defender.getLocation()))
                    .filter(l -> calculator.distance(l, enemyLocation) > 1)
                    .filter(l -> map.get(l).flatMap(s -> s.getUnit()).isEmpty())
                    .findFirst();
            return retreatLocation.map(l -> {
                defender.setLocation(l);
                return true;
            }).orElse(false);
        }

    }

    private void setState(UnitState newState) {
        state = newState;
    }
}
