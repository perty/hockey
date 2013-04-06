package util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: perty
 * Date: 2013-04-06
 * Time: 17:03
 */
public class ArithmeticPointTest {

    @Test
    public void givenAt00WhenFacing01Then90Degrees() {
        ArithmeticPoint testPoint = new ArithmeticPoint(0.0, 0.0);

        double expectedAngle = 270.0;
        assertEquals(expectedAngle, testPoint.angleTo(new ArithmeticPoint(0.0, -1.0)), 0.1);
    }

    @Test
    public void givenAt00WhenFacing10Then180Degrees() {
        ArithmeticPoint testPoint = new ArithmeticPoint(0.0, 0.0);

        double expectedAngle = 0.0;
        assertEquals(expectedAngle, testPoint.angleTo(new ArithmeticPoint(1.0, 0.0)), 0.1);
    }
}
