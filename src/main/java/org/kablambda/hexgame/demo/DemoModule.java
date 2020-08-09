package org.kablambda.hexgame.demo;

import dagger.Module;
import dagger.Provides;
import org.kablambda.hexgame.Game;
import org.kablambda.hexgame.HexMap;
import org.kablambda.hexgame.HexRenderer;
import org.kablambda.hexgame.MapEventListener;
import org.kablambda.hexgame.UIParameters;

import javax.inject.Inject;
import javax.inject.Singleton;

@Module
public class DemoModule {
    @Provides
    @Singleton
    HexMap provideHexMap() {
        return new DemoMap();
    }
    @Provides
    @Inject
    @Singleton
    MapEventListener provideListener(HexMap map) {
        return new DemoListener(map);
    }
    @Provides
    @Singleton
    HexRenderer provideHexRenderer() {
        return new DemoRenderer();
    }

    @Provides
    @Inject
    @Singleton
    Game provideGame(HexMap hexMap, HexRenderer hexRenderer, MapEventListener listener) {
        return new Game(hexMap, hexRenderer, listener, new UIParameters() {
            @Override
            public int getHexSideLength() {
                return 30;
            }

            @Override
            public int getBorderSize() {
                return 10;
            }
        });
    }
}
