package org.kablambda.hexgame.wargame;

/**
 * Mutable state of a hex
 */
public class HexState {
    private Unit unit;
    private Terrain terrain;

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }
}
