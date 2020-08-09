package org.kablambda.hexgame;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.PI;

public class FlatToppedHexPixelCalculator implements HexPixelCalculator {

    private final double SQRT3;
    private final double SQRT3_SIZE;
    private final int border;
    private final double SQRT3ON2_SIZE;
    private final double ONEPOINT5_SIZE;

    private final double size;

    public FlatToppedHexPixelCalculator(int size, int border) {
        this.size = size;
        SQRT3 = Math.sqrt(3.0);
        SQRT3_SIZE = Math.sqrt(3.0) * size;
        this.border = border;
        SQRT3ON2_SIZE = SQRT3_SIZE / 2.0;
        ONEPOINT5_SIZE = 1.5 * size;
    }

    @Override
    public Point2D center(HexAddress address) {
        return new Point2D.Double(
                ONEPOINT5_SIZE * address.q() + xOffset(),
                SQRT3ON2_SIZE * address.q() + SQRT3_SIZE * address.r() + yOffset()
        );
    }

    @Override
    public List<Point2D> vertices() {
        return IntStream.range(0, 6).mapToObj(this::flatHexCorner).collect(Collectors.toList());
    }

    @Override
    public Point2D extent(int columns, int rows) {
        return new Point2D.Double((columns) * ONEPOINT5_SIZE + ONEPOINT5_SIZE / 3 + 2 * border, (rows + 1) * SQRT3ON2_SIZE + 2 * border);
    }

    @Override
    public HexAddress pointToHex(int x, int y) {
        x = x - (int)xOffset();
        y = y - (int)yOffset();
        double q = (2.0/3.0*x) / size;
        double r = (-1.0/3.0*x + (SQRT3 / 3.0) * y) / size;
        return round(q, r);
    }

    private HexAddress round(double q, double r) {
        double x = q;
        double z = r;
        double y = -q-r;

        long rx = Math.round(x);
        long ry = Math.round(y);
        long rz = Math.round(z);

        double x_diff = Math.abs(rx - x);
        double y_diff = Math.abs(ry - y);
        double z_diff = Math.abs(rz - z);

        if (x_diff > y_diff && x_diff > z_diff) {
            rx = -ry - rz;
        } else if (y_diff > z_diff) {
            ry = -rx - rz;
        } else {
            rz = -rx - ry;
        }

        return new HexAddress((int)rx, (int)rz);
    }

    private Point2D flatHexCorner(int i) {
        double angleDeg = 60 * i;
        double angleRad = PI / 180 * angleDeg;
        return new Point2D.Double(size * Math.cos(angleRad),
                        size * Math.sin(angleRad));
    }

    /**
     * @return offset added to x coordinates to keep extent of all hexes > border
     */
    private double xOffset() {
        return ONEPOINT5_SIZE * 2 / 3 + border;
    }

    /**
     * @return offset added to y coordinates to keep extent of all hexes > border
     */
    private double yOffset() {
        return SQRT3ON2_SIZE + border;
    }
}
