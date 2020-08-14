package org.kablambda.hexgame.wargame;

import org.kablambda.hexgame.FlatToppedHexPixelCalculator;
import org.kablambda.hexgame.Game;
import org.kablambda.hexgame.HexAddress;
import org.kablambda.hexgame.HexMap;
import org.kablambda.hexgame.HexPixelCalculator;
import org.kablambda.hexgame.SimpleHexMap;
import org.kablambda.hexgame.UIParameters;

public class WargameApp {
    private static final int COLUMNS = 30;
    private static final int ROWS = 30;

    public static void main(String[] args) {
        UIParameters uiParameters = new UIParameters() {
            @Override
            public int getHexSideLength() {
                return 20;
            }

            @Override
            public int getBorderSize() {
                return 10;
            }

            @Override
            public String getTitle() {
                return "Wargame";
            }
        };        HexMap<HexState> hexMap = new SimpleHexMap<>(COLUMNS, ROWS);
        HexPixelCalculator calc = new FlatToppedHexPixelCalculator(uiParameters);

        setup(hexMap, calc);

        Game game = new Game(hexMap, new StateRenderer(new UnitRenderer(uiParameters, new NatoSymbolFactory(), calc), new TerrainRenderer()), new WargameListener(hexMap), uiParameters, calc);
        game.start();
    }

    private static void setup(HexMap<HexState> hexMap, HexPixelCalculator calc) {
        int noOfUnits = ROWS/2;
        for (int i = 0; i < noOfUnits; i++) {
            addUnit(Team.RED, hexMap, HexAddress.grid(0, i * 2), calc);
            addUnit(Team.BLUE, hexMap, HexAddress.grid(COLUMNS-1, i * 2), calc);
        }
    }

    private static void addUnit(Team team, HexMap<HexState> hexMap, HexAddress address,
                                HexPixelCalculator calc) {
        hexMap.put(address, new HexState().setUnit(new Unit(team, address, calc, hexMap)));
    }
}
