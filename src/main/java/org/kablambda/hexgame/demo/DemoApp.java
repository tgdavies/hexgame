package org.kablambda.hexgame.demo;

import org.kablambda.hexgame.FlatToppedHexPixelCalculator;
import org.kablambda.hexgame.Game;
import org.kablambda.hexgame.HexMap;
import org.kablambda.hexgame.UIParameters;

public class DemoApp {

    public static void main(String[] args) {
        HexMap hexMap = new DemoMap();
        UIParameters uiParameters = new DemoUiParameters();
        Game game = new Game(hexMap, new DemoRenderer(), new DemoListener(hexMap), uiParameters, new FlatToppedHexPixelCalculator(uiParameters));
        game.start();
    }
}
