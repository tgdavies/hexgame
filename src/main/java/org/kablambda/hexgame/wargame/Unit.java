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
    private final ConstrainedDouble move = ConstrainedDouble.unitInterval(0.0);
    private boolean selected;
    private final ConstrainedDouble supply = ConstrainedDouble.unitInterval(1.0);
    private final ConstrainedDouble fatigue = ConstrainedDouble.unitInterval(0.0);
    private HexAddress goal;
    private HexAddress location;
    private final HexPixelCalculator calculator;
    private final HexMap<HexState> map;
    private final UnitParameters params = DEFAULT;

    public Unit(Team team, HexAddress address, HexPixelCalculator calculator, HexMap<HexState> map) {
        this.team = team;
        this.location = address;
        this.calculator = calculator;
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
    }

    public void setLocation(HexAddress location) {
        map.get(getLocation()).ifPresent(s -> s.setUnit(null));
        this.location = location;
        HexState s = map.get(getLocation()).orElse(new HexState());
        s.getUnit().ifPresent(u -> {throw new RuntimeException("Unexpected unit in " + location); });
        s.setUnit(this);
        map.put(getLocation(), s);
        if (location.equals(getGoal())) {
            setGoal(null);
        }
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
        if (goal == null || fatigue.isMax() /*|| supply.isMin()*/) {
            fatigue.dec(params.fatigueRecoveryPerTick());
        } else {
            fatigue.inc(params.movementFatiguePerTick());
            supply.dec(params.movementSupplyUsePerTick());
            move.inc(1.0 / params.ticksPerMove());
            if (move.isMax()) {
                attemptMove();
            }
        }
    }

    private void attemptMove() {
        List<HexAddress> candidates = calculator.surroundingHexes(getLocation(), map);
        Collections.shuffle(candidates);

        candidates.stream()
                .filter(l -> map.isValidAddress(l))
                .filter(l -> map.get(l).map(s -> s.getUnit().map(u -> u.getTeam() != getTeam()).orElse(true)).orElse(true))
                .sorted(destinationHexComparator(getGoal()))
                .findFirst().ifPresent(d -> moveTo(d));
    }

    private Comparator<? super HexAddress> destinationHexComparator(HexAddress goal) {
        return Comparator.comparingInt(l -> calculator.distance(l, goal));
    }

    private void moveTo(HexAddress d) {
        move.set(0);
        Optional<Unit> enemy = map.get(d).flatMap(s -> s.getUnit());
        if (enemy.isEmpty()) {
            // move to an empty hex
            setLocation(d);
        } else {
            battle(this, enemy.get(), d);
        }
    }

    private void battle(Unit attacker, Unit defender, HexAddress attackersTargetHex) {
        attacker.supply.apply(s -> s * 0.8);
        defender.supply.apply(s -> s * 0.8);
        if (defender.getGoal() != null) {
            if (calculator.closest(defender.getGoal(), attacker.getLocation(), defender.getLocation()) == attacker.getLocation()) {
                // advancing
            } else {
                // retreating
            }
        } else {
            if (attacker.power() > 1.25 * defender.power()) {
                if (defender.retreat(attacker.getLocation())) {
                    attacker.setLocation(attackersTargetHex);
                } else {
                    defender.supply.apply(s -> s/2);
                }
            }
        }
    }

    private boolean retreat(HexAddress enemyLocation) {
        var retreatLocation = calculator.surroundingHexes(getLocation(), map).stream()
                .filter(l -> !l.equals(location))
                .filter(l -> calculator.distance(l, enemyLocation) > 1)
                .filter(l -> map.get(l).flatMap(s -> s.getUnit()).isEmpty())
                .findFirst();
        return retreatLocation.map(l -> {
            setLocation(l);
            return true;
        }).orElse(false);
    }

    private double power() {
        return supply.get();
    }

    @Override
    public String toString() {
        return "Unit{" +
               "team=" + team +
               ", supply=" + supply +
               ", fatigue=" + fatigue +
               ", goal=" + goal +
               '}';
    }
}
