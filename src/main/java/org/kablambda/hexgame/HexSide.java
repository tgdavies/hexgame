package org.kablambda.hexgame;

import java.awt.geom.Point2D;

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

    public Edge edgeVertices(HexPixelCalculator calculator) {
        int i = this.ordinal();
        var vertices = calculator.vertices();
        return new Edge(vertices.get((i + 4) % 6), vertices.get((i + 5) % 6));
    }

    public record Edge(Point2D p1, Point2D p2) {
    }
}
