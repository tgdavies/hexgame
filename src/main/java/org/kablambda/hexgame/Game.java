package org.kablambda.hexgame;

import dagger.Provides;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * Class which creates the UI
 */
@Singleton
public class Game<T> {

    private final HexMap hexMap;
    private final HexRenderer<T> hexRenderer;
    private final MapEventListener listener;
    private final UIParameters uiParameters;

    @Inject
    public Game(HexMap hexMap, HexRenderer<T> hexRenderer, MapEventListener listener, UIParameters uiParameters) {
        this.hexMap = hexMap;
        this.hexRenderer = hexRenderer;
        this.listener = listener;
        this.uiParameters = uiParameters;
    }

    public void start() {
            JFrame f = new JFrame("HexMap Demo");
            HexMapView<T> hexMapView = new HexMapView<>(hexMap, hexRenderer, listener, uiParameters);
            JScrollPane scrollPane = new JScrollPane(hexMapView);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.add(scrollPane);
            f.pack();
            f.setVisible(true);
        }
}
