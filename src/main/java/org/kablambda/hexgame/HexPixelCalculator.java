package org.kablambda.hexgame;

import java.awt.geom.Point2D;
import java.util.List;

/**
 * TODO: Document this class / interface here
 *
 * @since v8.0
 */
public interface HexPixelCalculator {
    Point2D center(HexAddress address);
    List<Point2D> vertices();
    Point2D extent(int columns, int rows);
    HexAddress pointToHex(int x, int y);
}
