import static org.junit.Assert.*;

public class CodingBatTest {

    CodingBat test = new CodingBat();

    @org.junit.Test
    public void sleepIn() {
        assertTrue(test.sleepIn(true,false));
        assertFalse(test.sleepIn(false,false));
        assertTrue(test.sleepIn(true,true));
    }

    @org.junit.Test
    public void diff21() {
        assertEquals(2, test.diff21(19));
        assertEquals(0, test.diff21(21));
        assertEquals(10, test.diff21(31));
    }

    @org.junit.Test
    public void helloName() {
        assertEquals("Hello Wowo!", test.helloName("Wowo)"));
        assertEquals("Hello Wowo1!", test.helloName("Wowo1)"));
        assertEquals("Hello Xyz!", test.helloName("Xyz)"));
    }

    @org.junit.Test
    public void countEvens() {
        int [] n = {2,2,2,2,2};
        int [] m = {1,3,5};
        int [] o = {1,2,3,4,5};
        assertEquals(5, test.countEvens(n));
        assertEquals(0, test.countEvens(m));
        assertEquals(2, test.countEvens(o));

    }
}