package org.kablambda.hexgame;

public interface MapEventListener {
    void hexClicked(HexAddress address);

    void tick();
}
