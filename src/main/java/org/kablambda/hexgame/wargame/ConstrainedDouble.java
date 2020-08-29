package org.kablambda.hexgame.wargame;

import java.util.function.Function;

import static java.lang.StrictMath.max;
import static java.lang.StrictMath.min;

public class ConstrainedDouble {

    private final double min;
    private final double max;
    private double value = 0.0;

    public static ConstrainedDouble unitInterval(double initialValue) {
        return new ConstrainedDouble(0.0, 1.0, initialValue);
    }

    public ConstrainedDouble(double min, double max, double initialValue) {
        this.min = min;
        this.max = max;
        value = set(initialValue);
    }

    public double set(double newValue) {
        return apply(v -> newValue);
    }

    public double inc(double inc) {
        return apply(v -> v + inc);
    }

    public double dec(double dec) {
        return inc(-dec);
    }

    public double get() {
        return value;
    }

    public boolean isMax() {
        return value == max;
    }

    public boolean isMin() {
        return value == min;
    }

    public boolean gt(double comparisonValue) {
        return map(v -> v > comparisonValue);
    }

    public boolean gteq(double comparisonValue) {
        return map(v -> v >= comparisonValue);
    }

    public boolean lt(double comparisonValue) {
        return map(v -> v < comparisonValue);
    }

    public <T> T map(Function<Double, T> f) {
        return f.apply(value);
    }

    public double apply(Function<Double, Double> f) {
        value = max(min(f.apply(value), max), min);
        return value;
    }

    @Override
    public String toString() {
        return "ConstrainedDouble{" +
               "value=" + value +
               '}';
    }
}
