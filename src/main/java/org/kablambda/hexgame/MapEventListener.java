package org.kablambda.hexgame;

public interface MapEventListener {
    void hexClicked(HexClickedEvent event);

    void tick();
}
