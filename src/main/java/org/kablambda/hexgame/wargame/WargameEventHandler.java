package org.kablambda.hexgame.wargame;

import org.kablambda.hexgame.HexAddress;
import org.kablambda.hexgame.HexClickedEvent;

public interface WargameEventHandler {
    WargameEventHandler hexClicked(HexClickedEvent address);
}
