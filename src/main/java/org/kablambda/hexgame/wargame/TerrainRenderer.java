package org.kablambda.hexgame.wargame;

import org.kablambda.hexgame.HexRenderer;

import java.awt.Graphics2D;

public class TerrainRenderer implements HexRenderer<Terrain> {

    @Override
    public void paint(Graphics2D g, Terrain contents) {
        contents.getDecorations().stream().forEach(d -> d.render(g));
    }
}
