package org.kablambda.hexgame.demo;

import org.kablambda.hexgame.UIParameters;

public class DemoUiParameters implements UIParameters {

    @Override
    public int getHexSideLength() {
        return 30;
    }

    @Override
    public int getBorderSize() {
        return 15;
    }
}
