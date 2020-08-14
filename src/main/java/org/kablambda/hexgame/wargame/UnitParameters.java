package org.kablambda.hexgame.wargame;


public record UnitParameters(
        double fatigueRecoveryPerTick,
        double movementFatiguePerTick,
        double movementSupplyUsePerTick,
        double ticksPerMove
) {

    public static UnitParameters DEFAULT = new UnitParameters(
            0.005,
            0.01,
            0.001,
            20
    );
}
