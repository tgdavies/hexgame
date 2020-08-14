package org.kablambda.hexgame;

/**
 * See https://www.redblobgames.com/grids/hexagons/
 */
public record HexAddress(int q, int r) {

    public static HexAddress grid(int x, int y) {
        int q = x;
        int r = (y - x) / 2;
        return new HexAddress(q, r);
    }
}
