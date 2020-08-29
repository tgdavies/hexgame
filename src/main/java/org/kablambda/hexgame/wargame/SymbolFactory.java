package org.kablambda.hexgame.wargame;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class SymbolFactory {
    private LoadingCache<Double,Shape> infantryCache = CacheBuilder.newBuilder().build(CacheLoader.from(size -> {
        double dx = size * 0.4;
        double dy = size * 0.25;
        double yOff = 0;
        Path2D path = new Path2D.Double();
        path.moveTo(-dx, -dy + yOff);
        path.lineTo(dx, -dy + yOff);
        path.lineTo(dx, dy + yOff);
        path.lineTo(-dx, dy + yOff);
        path.lineTo(-dx, -dy + yOff);
        path.lineTo(dx, dy + yOff);
        path.moveTo(dx, -dy + yOff);
        path.lineTo(-dx, dy + yOff);
        return path;
    }));

    public Shape infantry(double size) {
        return infantryCache.getUnchecked(size);
    }

    public Shape combat(double size) {
        return star(8, size/2, size/4);
    }

    private Shape star(int points, double outerRadius, double innerRadius) {
        Path2D path = new Path2D.Double();
        for (double i = 0; i < points; i++) {
            double pointAngle = (2 * Math.PI / points) * i;
            double innerAngle = (2 * Math.PI / points) * (i + 0.5);
            var point = point(pointAngle, outerRadius);
            if (i == 0) {
                path.moveTo(point.x, point.y);
            } else {
                path.lineTo(point.x, point.y);
            }
            var innerPoint = point(innerAngle, innerRadius);
            path.lineTo(innerPoint.x, innerPoint.y);
        }
        path.closePath();
        return path;
    }

    private Point2D.Double point(double theta, double radius) {
        return new Point2D.Double(Math.cos(theta) * radius, Math.sin(theta) * radius);
    }
}
