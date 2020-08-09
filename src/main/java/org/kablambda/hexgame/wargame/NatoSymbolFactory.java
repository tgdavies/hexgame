package org.kablambda.hexgame.wargame;

import java.awt.Shape;
import java.awt.geom.Path2D;

public class NatoSymbolFactory {
    public Shape infantry(double size) {
        double dx = size * 0.4;
        double dy = size * 0.25;
        double yOff = 0;
        Path2D path = new Path2D.Double();
        path.moveTo(-dx, -dy + yOff);
        path.lineTo(dx, -dy + yOff);
        path.lineTo(dx, dy + yOff);
        path.lineTo(-dx, dy + yOff);
        path.lineTo(-dx, -dy + yOff);
        path.lineTo(dx, dy + yOff);
        path.moveTo(dx, -dy + yOff);
        path.lineTo(-dx, dy + yOff);
        return path;
    }
}
