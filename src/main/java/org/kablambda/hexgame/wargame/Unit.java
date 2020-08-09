package org.kablambda.hexgame.wargame;

import org.kablambda.hexgame.HexAddress;

/**
 * state of a Unit
 */
public class Unit {
    private final Team team;
    private double supply = 1.0;
    private double fatigue = 0.0;
    private HexAddress goal;
    private HexAddress location;

    public Unit(Team team) {
        this.team = team;
    }

    public HexAddress getGoal() {
        return goal;
    }

    public HexAddress getLocation() {
        return location;
    }

    public Team getTeam() {
        return team;
    }

    public double getSupply() {
        return supply;
    }

    public double getFatigue() {
        return fatigue;
    }

    public void setGoal(HexAddress goal) {
        this.goal = goal;
    }

    public void setLocation(HexAddress location) {
        this.location = location;
    }

    public void setSupply(double supply) {
        this.supply = supply;
    }

    public void setFatigue(double fatigue) {
        this.fatigue = fatigue;
    }
}
