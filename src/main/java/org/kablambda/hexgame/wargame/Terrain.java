package org.kablambda.hexgame.wargame;

import java.util.ArrayList;
import java.util.List;

/**
 * State of the terrain in a hex
 */
public class Terrain {
    private List<Decoration> decorations = new ArrayList();

    public Terrain() {
    }

    public void tick() {
        decorations.stream().forEach(Decoration::tick);
        decorations.removeIf(d -> d.shouldRemove());
    }

    public List<Decoration> getDecorations() {
        return decorations;
    }
}
