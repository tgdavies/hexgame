package org.kablambda.hexgame.demo;

import org.kablambda.hexgame.HexAddress;
import org.kablambda.hexgame.HexMap;
import org.kablambda.hexgame.MapEventListener;

import java.awt.Color;

public class DemoListener implements MapEventListener {

    private final HexMap map;

    public DemoListener(HexMap map) {
        this.map = map;
    }

    @Override
    public void hexClicked(HexAddress address) {
        map.put(address, map.get(address).map(c -> c == Color.CYAN ? Color.ORANGE : Color.CYAN).orElse(Color.CYAN));
    }
}
