package org.kablambda.hexgame.wargame;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ConstrainedDoubleTest {
    @Test
    public void testCreation() {
        ConstrainedDouble d = ConstrainedDouble.unitInterval(2.0);
        assertThat(d.get()).isEqualTo(1.0);
        assertThat(d.isMax()).isTrue();
        assertThat(d.isMin()).isFalse();
    }

    @Test
    public void testInc() {
        ConstrainedDouble d = ConstrainedDouble.unitInterval(0.5);
        assertThat(d.inc(0.1)).isEqualTo(0.6);
        assertThat(d.inc(0.5)).isEqualTo(1.0);
    }

    @Test
    public void testDec() {
        ConstrainedDouble d = ConstrainedDouble.unitInterval(0.5);
        assertThat(d.dec(0.1)).isEqualTo(0.4);
        assertThat(d.dec(0.5)).isEqualTo(0.0);
    }

    @Test
    public void testMap() {
        ConstrainedDouble d = ConstrainedDouble.unitInterval(0.5);
        assertThat(d.map(Object::toString)).isEqualTo("0.5");
    }
}
