package org.kablambda.hexgame;

import java.awt.Graphics2D;

public interface HexRenderer<T> {
    void paint(Graphics2D g, T contents);

    void paintEmpty(Graphics2D g);
}
