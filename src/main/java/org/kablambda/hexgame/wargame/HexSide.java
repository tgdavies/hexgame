package org.kablambda.hexgame.wargame;

import org.kablambda.hexgame.HexPixelCalculator;

import java.awt.geom.Point2D;
import java.util.List;

/**
 *   1
 *   -
 * 6/ \2
 * 5\ /3
 *   -
 *   4
 */
public enum HexSide {
    S1, S2, S3, S4, S5, S6;

    Edge edgeVertices(HexPixelCalculator calculator) {
        int i = this.ordinal();
        var vertices = calculator.vertices();
        return new Edge(vertices.get((i + 5) % 6), vertices.get(i));
    }

    public record Edge(Point2D p1, Point2D p2) {
    }
}
