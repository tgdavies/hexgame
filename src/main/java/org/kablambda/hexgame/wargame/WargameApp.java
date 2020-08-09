package org.kablambda.hexgame.wargame;

import org.kablambda.hexgame.FlatToppedHexPixelCalculator;
import org.kablambda.hexgame.Game;
import org.kablambda.hexgame.HexMap;
import org.kablambda.hexgame.HexPixelCalculator;
import org.kablambda.hexgame.SimpleHexMap;
import org.kablambda.hexgame.UIParameters;

public class WargameApp {

    public static void main(String[] args) {
        HexMap hexMap = new SimpleHexMap(30, 30);
        UIParameters uiParameters = new UIParameters() {
            @Override
            public int getHexSideLength() {
                return 20;
            }

            @Override
            public int getBorderSize() {
                return 10;
            }
        };
        HexPixelCalculator calc = new FlatToppedHexPixelCalculator(uiParameters);
        Game game = new Game(hexMap, new StateRenderer(new UnitRenderer(uiParameters, new NatoSymbolFactory(), calc), new TerrainRenderer()), new WargameListener(hexMap), uiParameters, calc);
        game.start();
    }
}
