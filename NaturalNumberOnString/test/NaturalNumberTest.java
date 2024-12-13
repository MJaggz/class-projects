import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;

/**
 * JUnit test fixture for {@code NaturalNumber}'s constructors and kernel
 * methods.
 *
 * @author Emanuel Messele and Mohamed Jama
 *
 */
public abstract class NaturalNumberTest {

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new number
     * @ensures constructorTest = 0
     */
    protected abstract NaturalNumber constructorTest();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorTest = i
     */
    protected abstract NaturalNumber constructorTest(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorTest)
     */
    protected abstract NaturalNumber constructorTest(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorTest = n
     */
    protected abstract NaturalNumber constructorTest(NaturalNumber n);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @return the new number
     * @ensures constructorRef = 0
     */
    protected abstract NaturalNumber constructorRef();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorRef = i
     */
    protected abstract NaturalNumber constructorRef(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorRef)
     */
    protected abstract NaturalNumber constructorRef(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorRef = n
     */
    protected abstract NaturalNumber constructorRef(NaturalNumber n);

    /*
     * NaturalNumber test cases
     */

    /**
     * Test cases for constructors.
     */
    @Test
    public final void testNothingArgConstructor() {
        /*
         * Creating NaturalNumbers
         */
        NaturalNumber n = this.constructorTest();
        NaturalNumber nExpected = this.constructorRef();

        /*
         * Checking that n matches the expectation
         */
        assertEquals(nExpected, n);
    }

    /**
     * Integer constructor tests.
     */
    @Test
    public final void testNumberZeroIntegerConstructor() {
        /*
         * Creating NaturalNumbers
         */
        NaturalNumber n = this.constructorTest(0);
        NaturalNumber nExpected = this.constructorRef(0);

        /*
         * Checking that n matches the expectation
         */
        assertEquals(nExpected, n);
    }

    /**
     * Integer constructor tests. Using multiple zeros.
     */
    @Test
    public final void testMultipleZeroesIntegerConstructor() {
        /*
         * Creating NaturalNumbers
         */
        NaturalNumber n = this.constructorTest(00000000);
        NaturalNumber nExpected = this.constructorRef(00000000);

        /*
         * Checking that n matches the expectation
         */
        assertEquals(nExpected, n);
    }

    /**
     * Integer constructor tests. non zero integers.
     */
    @Test
    public final void testNonZeroIntegerConstructorNUmber55() {
        /*
         * Creating NaturalNumbers
         */
        NaturalNumber n = this.constructorTest(55);
        NaturalNumber nExpected = this.constructorRef(55);

        /*
         * Checking that n matches the expectation
         */
        assertEquals(nExpected, n);
    }

    /**
     * String Constructor tests.
     */
    @Test
    public final void testZeroNonEmptyStringConstructor() {
        /*
         * Creating NaturalNumbers
         */
        NaturalNumber n = this.constructorTest("0");
        NaturalNumber nExpected = this.constructorRef("0");

        /*
         * Checking that n matches the expectation
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testNonZeroStringConstructorBigNumber() {
        /*
         * Creating NaturalNumbers
         */
        NaturalNumber n = this.constructorTest("101010101010101010101010");
        NaturalNumber nExpected = this.constructorRef("101010101010101010101010");

        /*
         * Checking that n matches the expectation
         */
        assertEquals(nExpected, n);
    }

    /**
     * NaturalNumber Constructor tests.
     */
    @Test
    public final void testZeroNaturalNumberNConstructor() {
        /*
         * Creating NaturalNumbers
         */
        final NaturalNumber o = this.constructorRef();
        NaturalNumber n = this.constructorTest(o);
        NaturalNumber nExpected = this.constructorRef(o);

        /*
         * Checking that n matches the expectation
         */
        assertEquals(nExpected, n);
    }

    /**
     * NaturalNumber Constructor tests.
     */
    @Test
    public final void testNaturalNumberConstructor5262() {
        /*
         * Creating NaturalNumbers
         */
        final NaturalNumber o = this.constructorRef(5262);
        NaturalNumber n = this.constructorTest(o);
        NaturalNumber nExpected = this.constructorRef(o);

        /*
         * Checking that n matches the expectation
         */
        assertEquals(nExpected, n);
    }

    /**
     * NaturalNumber Constructor tests.
     */
    @Test
    public final void testNaturalNumberConstructor91021() {
        /*
         * Creating NaturalNumbers
         */
        final NaturalNumber o = this.constructorRef(91021);
        NaturalNumber n = this.constructorTest(o);
        NaturalNumber nExpected = this.constructorRef(o);

        /*
         * Checking that n matches the expectation
         */
        assertEquals(nExpected, n);
    }

    /**
     * NaturalNumber Constructor tests.
     */
    @Test
    public final void testNaturalNumberConstructorMaxVal() {
        /*
         * Creating NaturalNumbers
         */
        final NaturalNumber o = this.constructorRef(Integer.MAX_VALUE);
        NaturalNumber n = this.constructorTest(o);
        NaturalNumber nExpected = this.constructorRef(o);

        /*
         * Checking that n matches the expectation
         */
        assertEquals(nExpected, n);
    }

    /*
     * More constructor Test Cases
     */

    /**
     * tests the no argument construct.
     */
    @Test
    public final void noArgConstruct() {

        // creating the natural number using no arg
        NaturalNumber q = this.constructorTest();
        NaturalNumber qExpected = this.constructorRef();

        // check
        assertEquals(q, qExpected);
    }

    /**
     * tests the int construct.
     */
    @Test
    public final void intConstruct() {

        // create natural number using int arg
        final int number = 10;
        NaturalNumber q = this.constructorTest(number);
        NaturalNumber qExpected = this.constructorRef(10);

        // check
        assertEquals(q, qExpected);
    }

    /**
     * natural number constructor.
     */
    @Test
    public final void naturalNumberConstruct() {

        // create natural number using natural number arg
        NaturalNumber n = this.constructorTest(10);
        NaturalNumber q = this.constructorTest(n);
        NaturalNumber qExpected = this.constructorRef(10);

        // check
        assertEquals(q, qExpected);
    }

    /**
     * string constructor.
     */
    @Test
    public final void stringConstruct() {

        // create natural number using string arg
        NaturalNumber q = this.constructorTest("123");
        NaturalNumber qExpected = this.constructorRef(123);

        // check
        assertEquals(q, qExpected);
    }

    /*
     * Kernel Method Test Cases
     */

    /**
     * tests the multiplyBY10 method.
     */
    @Test
    public final void multiplyBy10Method() {

        // creating the natural numbers
        NaturalNumber q = this.constructorTest(10);
        NaturalNumber qExpected = this.constructorRef(107);

        // calling method
        q.multiplyBy10(7);

        // check
        assertEquals(q, qExpected);
    }

    /**
     * tests the divideby10 method.
     */
    @Test
    public final void divideBy10Method() {

        // Creating the natural numbers
        NaturalNumber q = this.constructorTest(100);
        NaturalNumber qExpected = this.constructorRef(10);

        // calling method
        q.divideBy10();

        // assert equality
        assertEquals(q, qExpected);

    }

    /**
     * tests the isZero method.
     */
    @Test
    public final void isZeroMethod() {

        // creating natural number
        NaturalNumber q = this.constructorTest();

        // using boolean to check
        boolean check = true;
        boolean answer = false;

        // calling method
        if (q.isZero()) {
            answer = true;
        }

        // asserting equality.
        assertEquals(check, answer);

    }

}
