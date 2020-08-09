package org.kablambda.hexgame;

import java.util.Optional;
import java.util.stream.Stream;

public interface HexMap<T> {
    void put(HexAddress address, T newContents);
    Optional<T> get(HexAddress address);
    Stream<HexEntry<T>> streamOccupied();
    Stream<HexAddress> streamAll();
    int getColumns();
    int getRows();
}
