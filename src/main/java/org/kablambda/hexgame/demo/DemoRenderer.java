package org.kablambda.hexgame.demo;

import org.kablambda.hexgame.HexRenderer;

import java.awt.Color;
import java.awt.Graphics2D;

public class DemoRenderer implements HexRenderer<Color> {
    @Override
    public void paint(Graphics2D g, Color contents) {
        g.setColor(contents);
        g.fillRect(-15, -15, 30, 30);
    }
}
