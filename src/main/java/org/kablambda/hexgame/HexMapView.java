package org.kablambda.hexgame;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.List;

public class HexMapView<T> extends JPanel {

    private final HexMap<T> map;
    private final HexRenderer<T> renderer;
    private final UIParameters uiParameters;
    private final HexPixelCalculator calc;
    private final Path2D hexOutline;
    private final Dimension preferredSize;

    public HexMapView(HexMap<T> map, HexRenderer<T> renderer, MapEventListener mapEventListener, UIParameters uiParameters, HexPixelCalculator calc) {
        this.map = map;
        this.renderer = renderer;
        this.uiParameters = uiParameters;
        this.calc = calc;
        hexOutline = new Path2D.Double();
        List<Point2D> vertices = calc.vertices();
        hexOutline.moveTo(vertices.get(0).getX(), vertices.get(0).getY());
        vertices.stream().skip(1).forEach(v -> hexOutline.lineTo(v.getX(), v.getY()));
        hexOutline.closePath();
        Point2D size = calc.extent(map.getColumns(), map.getRows());
        preferredSize = new Dimension((int) size.getX(), (int) size.getY());
        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mapEventListener.tick();
                HexMapView.this.repaint();
            }
        });
        timer.setRepeats(true);
        timer.start();
        addMouseListener(new MouseAdapter() {
            HexAddress clickedHex = null;

            @Override
            public void mousePressed(MouseEvent e) {
                clickedHex = calc.pointToHex(e.getX(), e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (clickedHex != null && clickedHex.equals(calc.pointToHex(e.getX(), e.getY()))) {
                    System.out.println(clickedHex);
                    mapEventListener.hexClicked(clickedHex);
                    HexMapView.this.repaint();
                }
                clickedHex = null;
            }
        });
    }

    public Dimension getPreferredSize() {
        return preferredSize;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g);
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        map.streamAll().forEach(h -> {
            AffineTransform t = g2d.getTransform();
            try {
                Point2D center = calc.center(h);
                g2d.translate(center.getX(), center.getY());
                renderHexOutline(g2d);
            } finally {
                g2d.setTransform(t);
            }
        });
        map.streamOccupied().forEach(h -> {
            AffineTransform t = g2d.getTransform();
            try {
                Point2D center = calc.center(h.address());
                g2d.translate(center.getX(), center.getY());
                renderer.paint(g2d, h.contents());
            } finally {
                g2d.setTransform(t);
            }
        });
    }

    private void renderHexOutline(Graphics2D g) {
        g.setColor(Color.LIGHT_GRAY);
        g.draw(hexOutline);
    }
}
