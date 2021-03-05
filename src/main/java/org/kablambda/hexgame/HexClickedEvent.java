package org.kablambda.hexgame;

import java.util.Optional;
import java.util.Set;

public record HexClickedEvent(HexAddress hexAddress, Optional<HexSide> hexSide, MouseButton mouseButton, Set<ModifierKey> modifierKeys) {}
