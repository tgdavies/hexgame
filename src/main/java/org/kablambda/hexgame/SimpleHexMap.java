package org.kablambda.hexgame;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SimpleHexMap<T> implements HexMap<T> {

    private final int columns;
    private final int rows;
    private final Map<HexAddress, T> map = new ConcurrentHashMap<>();

    public SimpleHexMap(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;
    }

    @Override
    public int getColumns() {
        return columns;
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public void put(HexAddress address, T newContents) {
        validateAddress(address);
        map.put(address, newContents);
    }

    @Override
    public Optional<T> get(HexAddress address) {
        validateAddress(address);
        return Optional.ofNullable(map.get(address));
    }

    @Override
    public Stream<HexEntry<T>> streamOccupied() {
        return map.entrySet().stream().map(e -> new HexEntry(e.getKey(), e.getValue()));
    }

    @Override
    public Stream<HexAddress> streamAll() {
        return IntStream.range(0, columns).boxed().flatMap(col -> IntStream.range(0, rows).mapToObj(row -> doubleHeightToAxial(col, row)));
    }

    private HexAddress doubleHeightToAxial(int col, int row) {
        int q = col;
        int r = (row - col) / 2;
        return new HexAddress(q, r);
    }

    private void validateAddress(HexAddress a) {
        DoubleHeightAddress da = axialToDoubleheight(a);
        if (!isValidAddress(a)) {
            throw new RuntimeException("Bad address: " + a + " (" + da + ")");
        }
    }

    @Override
    public boolean isValidAddress(HexAddress a) {
        DoubleHeightAddress da = axialToDoubleheight(a);
        return da.col() >= 0 && da.row() >= 0 && da.col() < columns && da.row() < rows;
    }

    private DoubleHeightAddress axialToDoubleheight(HexAddress a) {
        int y = -a.q() - a.r();
        int col = a.q();
        int row = 2 * a.r() + a.q();
        return new DoubleHeightAddress(col, row);
    }

    private record DoubleHeightAddress(int col, int row) {}
}
