package org.kablambda.hexgame.wargame;

import org.junit.jupiter.api.Test;
import org.kablambda.hexgame.FlatToppedHexPixelCalculator;
import org.kablambda.hexgame.HexAddress;
import org.kablambda.hexgame.HexSide;
import org.kablambda.hexgame.demo.DemoUiParameters;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * TODO: Document this class / interface here
 *
 * @since v8.0
 */
public class HexPixelCalculaterTest {
    FlatToppedHexPixelCalculator calc = new FlatToppedHexPixelCalculator(new DemoUiParameters());
    @Test
    public void testHexBetween() {
        HexAddress a = new HexAddress(1,1);
        HexAddress b = new HexAddress(2,1);
        assertThat(calc.hexSideBetween(a,b)).isEqualByComparingTo(HexSide.S3);
        assertThat(calc.hexSideBetween(b,a)).isEqualByComparingTo(HexSide.S6);

        HexAddress c = new HexAddress(1, 0);
        assertThat(calc.hexSideBetween(a,c)).isEqualByComparingTo(HexSide.S1);
        assertThat(calc.hexSideBetween(c,a)).isEqualByComparingTo(HexSide.S4);

        HexAddress d = new HexAddress(2, 0);
        assertThat(calc.hexSideBetween(a,d)).isEqualByComparingTo(HexSide.S2);
        assertThat(calc.hexSideBetween(d,a)).isEqualByComparingTo(HexSide.S5);

        HexAddress e = new HexAddress(1,2);
        assertThat(calc.hexSideBetween(a,e)).isEqualByComparingTo(HexSide.S4);
        assertThat(calc.hexSideBetween(e,a)).isEqualByComparingTo(HexSide.S1);

        HexAddress f = new HexAddress(0,2);
        assertThat(calc.hexSideBetween(a,f)).isEqualByComparingTo(HexSide.S5);
        assertThat(calc.hexSideBetween(f,a)).isEqualByComparingTo(HexSide.S2);

        HexAddress g = new HexAddress(0,1);
        assertThat(calc.hexSideBetween(a,g)).isEqualByComparingTo(HexSide.S6);
        assertThat(calc.hexSideBetween(g,a)).isEqualByComparingTo(HexSide.S3);
    }
}
