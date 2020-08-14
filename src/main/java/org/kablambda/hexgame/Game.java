package org.kablambda.hexgame;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 * Class which creates the UI
 */
public class Game<T> {

    private final HexMap hexMap;
    private final HexRenderer<T> hexRenderer;
    private final MapEventListener listener;
    private final UIParameters uiParameters;
    private final HexPixelCalculator calc;

    public Game(HexMap hexMap, HexRenderer<T> hexRenderer, MapEventListener listener, UIParameters uiParameters, HexPixelCalculator calc) {
        this.hexMap = hexMap;
        this.hexRenderer = hexRenderer;
        this.listener = listener;
        this.uiParameters = uiParameters;
        this.calc = calc;
    }

    public void start() {
            JFrame f = new JFrame(uiParameters.getTitle());
            HexMapView<T> hexMapView = new HexMapView<>(hexMap, hexRenderer, listener, uiParameters, calc);
            JScrollPane scrollPane = new JScrollPane(hexMapView);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.add(scrollPane);
            f.pack();
            f.setVisible(true);
        }
}
