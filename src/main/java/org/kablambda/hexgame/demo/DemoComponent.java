package org.kablambda.hexgame.demo;

import dagger.Component;
import org.kablambda.hexgame.Game;

import javax.inject.Singleton;

@Component(modules = {DemoModule.class})
@Singleton
public interface DemoComponent {
    Game buildGame();
}
