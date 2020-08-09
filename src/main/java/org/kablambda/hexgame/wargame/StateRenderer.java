package org.kablambda.hexgame.wargame;

import org.kablambda.hexgame.HexRenderer;

import java.awt.Graphics2D;

public class StateRenderer implements HexRenderer<HexState> {

    private final UnitRenderer unitRenderer;
    private final TerrainRenderer terrainRenderer;

    public StateRenderer(UnitRenderer unitRenderer, TerrainRenderer terrainRenderer) {
        this.unitRenderer = unitRenderer;
        this.terrainRenderer = terrainRenderer;
    }

    @Override
    public void paint(Graphics2D g, HexState contents) {
        terrainRenderer.paint(g, contents.getTerrain());
        unitRenderer.paint(g, contents.getUnit());
    }
}
