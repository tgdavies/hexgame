package org.kablambda.hexgame.wargame;

/**
 * state of a Unit
 */
public class Unit {
    private final Team team;
    private double supply = 1.0;
    private double fatigue = 0.0;

    public Unit(Team team) {
        this.team = team;
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
}
