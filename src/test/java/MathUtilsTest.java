import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class MathUtilsTest {

    private MathUtils mathUtils;

    @Before
    public void setUp() {
        // Runs before each test
        mathUtils = new MathUtils();
    }

    @After
    public void tearDown() {
        // Runs after each test
        mathUtils = null;
    }

    @org.testng.annotations.Test
    public void testAdd() {
        assertEquals(5, mathUtils.add(2, 3));
        assertEquals(0, mathUtils.add(-2, 2));
        assertEquals(-10, mathUtils.add(-5, -5));
        assertEquals(7, mathUtils.add(7, 0));
    }

    @Test
    public void testSubtract() {
        assertEquals(1, mathUtils.subtract(3, 2));
        assertEquals(-4, mathUtils.subtract(-2, 2));
        assertEquals(0, mathUtils.subtract(0, 0));
        assertEquals(10, mathUtils.subtract(10, 0));
    }

    @Test
    public void testMultiply() {
        assertEquals(6, mathUtils.multiply(2, 3));
        assertEquals(-10, mathUtils.multiply(-5, 2));
        assertEquals(0, mathUtils.multiply(0, 100));
        assertEquals(25, mathUtils.multiply(-5, -5));
    }

    @Test
    public void testDivide() {
        assertEquals(2.0, mathUtils.divide(6, 3), 0.001);
        assertEquals(-2.5, mathUtils.divide(-5, 2), 0.001);
        assertEquals(-1.0, mathUtils.divide(10, 0), 0.001);  // division by zero case
        assertEquals(0.5, mathUtils.divide(1, 2), 0.001);
    }
}