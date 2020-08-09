package org.kablambda.hexgame;

public record HexEntry<T>(HexAddress address, T contents) {
}
