package org.kablambda.hexgame.wargame;

import org.kablambda.hexgame.HexAddress;

public interface WargameEventHandler {
    WargameEventHandler hexClicked(HexAddress address);
}
