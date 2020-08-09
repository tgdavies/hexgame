package org.kablambda.hexgame.wargame;

import org.kablambda.hexgame.HexRenderer;

import java.awt.Graphics2D;

public class StateRenderer implements HexRenderer<HexState> {
    private UnitRenderer unitRenderer = new UnitRenderer();
    private TerrainRenderer terrainRenderer = new TerrainRenderer();

    @Override
    public void paint(Graphics2D g, HexState contents) {
        terrainRenderer.paint(g, contents.getTerrain());
        unitRenderer.paint(g, contents.getUnit());
    }
}
