package org.kablambda.hexgame;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Graphics2D;

public class HexApp {

    private static SimpleHexMap<Color> map = new SimpleHexMap<>(20, 20);

    public static void main(String[] args) {
        map.put(new HexAddress(0, 0), Color.BLACK);
        map.put(new HexAddress(1, 0), Color.RED);
        map.put(new HexAddress(0, 1), Color.GREEN);
        map.put(new HexAddress(1, 1), Color.BLUE);
        map.put(new HexAddress(2, 1), Color.YELLOW);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        JFrame f = new JFrame("HexMap Demo");
        HexMapView<Color> hexMapView = new HexMapView<>(
                map,
                new HexRenderer<Color>() {
                    @Override
                    public void paint(Graphics2D g, Color contents) {
                        g.setColor(contents);
                        g.fillRect(-15, -15, 30, 30);
                    }

                    @Override
                    public void paintEmpty(Graphics2D g) {
                        // do nothing
                    }
                },
                new MapEventListener() {
                    @Override
                    public void hexClicked(HexAddress address) {
                        map.put(address, map.get(address).map(c -> c == Color.CYAN ? Color.ORANGE : Color.CYAN).orElse(Color.CYAN));
                    }
                });
        JScrollPane scrollPane = new JScrollPane(hexMapView);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(scrollPane);
        f.pack();
        f.setVisible(true);
    }
}
