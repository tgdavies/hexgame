package org.kablambda.hexgame.demo;

import org.kablambda.hexgame.HexAddress;
import org.kablambda.hexgame.SimpleHexMap;

import java.awt.Color;

public class DemoMap extends SimpleHexMap<Color> {

    public DemoMap() {
        super(20, 20);
        put(new HexAddress(0, 0), Color.BLACK);
        put(new HexAddress(1, 0), Color.RED);
        put(new HexAddress(0, 1), Color.GREEN);
        put(new HexAddress(1, 1), Color.BLUE);
        put(new HexAddress(2, 1), Color.YELLOW);
    }
}
