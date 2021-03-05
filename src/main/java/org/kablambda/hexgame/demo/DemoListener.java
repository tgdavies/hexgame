package org.kablambda.hexgame.demo;

import org.kablambda.hexgame.HexAddress;
import org.kablambda.hexgame.HexClickedEvent;
import org.kablambda.hexgame.HexMap;
import org.kablambda.hexgame.MapEventListener;

import java.awt.Color;

public class DemoListener implements MapEventListener {

    private final HexMap map;

    public DemoListener(HexMap map) {
        this.map = map;
    }

    @Override
    public void hexClicked(HexClickedEvent event) {
        map.put(event.hexAddress(), map.get(event.hexAddress()).map(c -> c == Color.CYAN ? Color.ORANGE : Color.CYAN).orElse(Color.CYAN));
    }

    @Override
    public void tick() {
    }
}
