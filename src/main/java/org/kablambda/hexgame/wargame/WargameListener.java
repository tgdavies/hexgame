package org.kablambda.hexgame.wargame;

import org.kablambda.hexgame.HexAddress;
import org.kablambda.hexgame.HexClickedEvent;
import org.kablambda.hexgame.HexMap;
import org.kablambda.hexgame.MapEventListener;

public class WargameListener implements MapEventListener {

    private final HexMap<HexState> hexMap;
    private WargameEventHandler eventHandler = new UnselectedState();

    public WargameListener(HexMap<HexState> hexMap) {this.hexMap = hexMap;}

    @Override
    public void hexClicked(HexClickedEvent event) {
        eventHandler = eventHandler.hexClicked(event);
    }

    @Override
    public void tick() {
        hexMap.streamOccupied().forEach(e -> e.contents().getUnit().ifPresent(u -> u.tick()));
    }

    private class UnselectedState implements WargameEventHandler {

        @Override
        public WargameEventHandler hexClicked(HexClickedEvent event) {
            return hexMap.get(event.hexAddress()).<WargameEventHandler>flatMap(s -> s.getUnit().map(SelectedState::new)).orElse(this);
        }
    }

    private class SelectedState implements WargameEventHandler {

        private final Unit selectedUnit;

        public SelectedState(Unit selectedUnit) {
            this.selectedUnit = selectedUnit;
            selectedUnit.setSelected(true);
        }

        @Override
        public WargameEventHandler hexClicked(HexClickedEvent event) {
            if (!selectedUnit.getLocation().equals(event.hexAddress())) {
                selectedUnit.setGoal(event.hexAddress());
            }
            selectedUnit.setSelected(false);
            return new UnselectedState();
        }
    }
}
