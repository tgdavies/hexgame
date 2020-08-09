package org.kablambda.hexgame.wargame;

import org.kablambda.hexgame.HexAddress;
import org.kablambda.hexgame.HexMap;
import org.kablambda.hexgame.MapEventListener;

import java.util.Random;

import static org.kablambda.hexgame.wargame.Team.BLUE;
import static org.kablambda.hexgame.wargame.Team.RED;

public class WargameListener implements MapEventListener {

    private final HexMap<HexState> hexMap;

    public WargameListener(HexMap<HexState> hexMap) {this.hexMap = hexMap;}

    @Override
    public void hexClicked(HexAddress address) {
        Random r = new Random();
        HexState state = hexMap.get(address).orElse(new HexState());
        Unit unit = new Unit(r.nextBoolean() ? RED : BLUE);
        unit.setFatigue(r.nextDouble());
        unit.setSupply(r.nextDouble());
        unit.setLocation(address);
        if (r.nextDouble() > 0.5) {
            unit.setGoal(new HexAddress(address.q() + r.nextInt(4)-2, address.r() + r.nextInt(4) - 2));
        }
        state.setUnit(unit);
        hexMap.put(address, state);
    }
}
