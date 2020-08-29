package org.kablambda.hexgame.wargame;

import java.awt.Graphics2D;

/**
 * TODO: Document this class / interface here
 *
 * @since v8.0
 */
public interface Decoration {
    void render(Graphics2D g);
    void tick();
    boolean shouldRemove();
}
