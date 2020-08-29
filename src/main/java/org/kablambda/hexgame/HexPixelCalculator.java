package org.kablambda.hexgame;

import org.kablambda.hexgame.wargame.HexSide;

import java.awt.geom.Point2D;
import java.util.List;


public interface HexPixelCalculator {
    Point2D center(HexAddress address);

    int distance(HexAddress l1, HexAddress l2);

    HexAddress closest(HexAddress target, HexAddress... candidates);

    HexSide hexSideBetween(HexAddress h1, HexAddress h2);

    List<HexAddress> surroundingHexes(HexAddress location, HexMap<?> map);

    List<HexAddress> hexesInRange(HexAddress location, int i, HexMap<?> map);

    List<Point2D> vertices();
    Point2D extent(int columns, int rows);
    HexAddress pointToHex(int x, int y);
}
