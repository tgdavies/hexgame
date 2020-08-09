package org.kablambda.hexgame;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
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

    public HexMapView(HexMap<T> map, HexRenderer<T> renderer, MapEventListener mapEventListener, UIParameters uiParameters) throws HeadlessException {
        this.map = map;
        this.renderer = renderer;
        this.uiParameters = uiParameters;
        calc = new FlatToppedHexPixelCalculator(uiParameters.getHexSideLength(), uiParameters.getBorderSize());
        hexOutline = new Path2D.Double();
        List<Point2D> vertices = calc.vertices();
        hexOutline.moveTo(vertices.get(0).getX(), vertices.get(0).getY());
        vertices.stream().skip(1).forEach(v -> hexOutline.lineTo(v.getX(), v.getY()));
        hexOutline.closePath();
        Point2D size = calc.extent(map.getColumns(), map.getRows());
        preferredSize = new Dimension((int) size.getX(), (int) size.getY());
        addMouseListener(new MouseAdapter() {
            HexAddress clickedHex = null;

            @Override
            public void mousePressed(MouseEvent e) {
                clickedHex = calc.pointToHex(e.getX(), e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (clickedHex != null && clickedHex.equals(calc.pointToHex(e.getX(), e.getY()))) {
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
