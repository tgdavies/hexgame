package org.kablambda.hexgame.wargame;

import java.util.Optional;

/**
 * Mutable state of a hex
 */
public class HexState {
    private Unit unit;
    private Terrain terrain = new Terrain();

    public Optional<Unit> getUnit() {
        return Optional.ofNullable(unit);
    }

    public HexState setUnit(Unit unit) {
        this.unit = unit;
        return this;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public HexState setTerrain(Terrain terrain) {
        this.terrain = terrain;
        return this;
    }
}
