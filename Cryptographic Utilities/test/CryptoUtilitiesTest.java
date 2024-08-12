import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 *
 * @author Mohamed Jama
 *
 */

public class CryptoUtilitiesTest {

    /*
     * Tests of reduceToGCD
     */
    ///This test case finds the gcd of 0 and 0 which is 0.//
    @Test
    public void testReduceToGCD_0_0() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber nExpected = new NaturalNumber2(0);
        NaturalNumber m = new NaturalNumber2(0);
        NaturalNumber mExpected = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
    }

    ///This test case finds the gcd of 30 and 21 which is 3.//
    @Test
    public void testReduceToGCD_30_21() {
        NaturalNumber n = new NaturalNumber2(30);
        NaturalNumber nExpected = new NaturalNumber2(3);
        NaturalNumber m = new NaturalNumber2(21);
        NaturalNumber mExpected = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
    }

    ///This test case finds the gcd of 92 and 12 which is 4.//
    @Test
    public void testReduceToGCD_92_12() {
        NaturalNumber n = new NaturalNumber2(92);
        NaturalNumber nExpected = new NaturalNumber2(4);
        NaturalNumber m = new NaturalNumber2(12);
        NaturalNumber mExpected = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
    }

    ///This test case finds the gcd of 5005 and 25 which is 5.//
    @Test
    public void testReduceToGCD_92366_123456() {
        NaturalNumber n = new NaturalNumber2(5005);
        NaturalNumber nExpected = new NaturalNumber2(5);
        NaturalNumber m = new NaturalNumber2(25);
        NaturalNumber mExpected = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
    }

    ///This test case finds the gcd of 92 and 0 which is 0.//
    @Test
    public void testReduceToGCD_0_92() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber nExpected = new NaturalNumber2(92);
        NaturalNumber m = new NaturalNumber2(92);
        NaturalNumber mExpected = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
    }

    ///This test case finds the gcd of 12 and 1 which is 1.//
    @Test
    public void testReduceToGCD_12_1() {
        NaturalNumber n = new NaturalNumber2(12);
        NaturalNumber nExpected = new NaturalNumber2(1);
        NaturalNumber m = new NaturalNumber2(1);
        NaturalNumber mExpected = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
    }

    /*
     * Tests of isEven
     */
    ///This test case checks if 0 is even, and 0 is even.//
    @Test
    public void testIsEven_0() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber nExpected = new NaturalNumber2(0);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(nExpected, n);
        assertEquals(true, result);
    }

    ///This test case checks if 1 is even, and 1 is not even.//
    @Test
    public void testIsEven_1() {
        NaturalNumber n = new NaturalNumber2(1);
        NaturalNumber nExpected = new NaturalNumber2(1);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(nExpected, n);
        assertEquals(false, result);
    }

    ///This test case checks if 2 is even, and 2 is even.//
    @Test
    public void testIsEven_2() {
        NaturalNumber n = new NaturalNumber2(2);
        NaturalNumber nExpected = new NaturalNumber2(2);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(nExpected, n);
        assertEquals(true, result);
    }

    ///This test case checks if 3684826844 is even, and it is even.//
    @Test
    public void testIsEven_3684826844() {
        NaturalNumber n = new NaturalNumber2("3684826844");
        NaturalNumber nExpected = new NaturalNumber2("3684826844");
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(nExpected, n);
        assertEquals(true, result);
    }

    ///This test case checks if 3684826845 is even, and it is not even.//
    @Test
    public void testIsEven_3684826835() {
        NaturalNumber n = new NaturalNumber2("3684826835");
        NaturalNumber nExpected = new NaturalNumber2("3684826835");
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(nExpected, n);
        assertEquals(false, result);
    }

    /*
     * Tests of powerMod
     */
    ///This test case tests powerMod of (0,0,2).//
    @Test
    public void testPowerMod_0_0_2() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber nExpected = new NaturalNumber2(1);
        NaturalNumber p = new NaturalNumber2(0);
        NaturalNumber pExpected = new NaturalNumber2(0);
        NaturalNumber m = new NaturalNumber2(2);
        NaturalNumber mExpected = new NaturalNumber2(2);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(nExpected, n);
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    ///This test case tests powerMod of (17,18,19).//
    @Test
    public void testPowerMod_17_18_19() {
        NaturalNumber n = new NaturalNumber2(17);
        NaturalNumber nExpected = new NaturalNumber2(1);
        NaturalNumber p = new NaturalNumber2(18);
        NaturalNumber pExpected = new NaturalNumber2(18);
        NaturalNumber m = new NaturalNumber2(19);
        NaturalNumber mExpected = new NaturalNumber2(19);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(nExpected, n);
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    ///This test case tests powerMod of (2,8,2).//
    @Test
    public void testPowerMod_2_8_2() {
        NaturalNumber n = new NaturalNumber2(2);
        NaturalNumber nExpected = new NaturalNumber2(0);
        NaturalNumber p = new NaturalNumber2(8);
        NaturalNumber pExpected = new NaturalNumber2(8);
        NaturalNumber m = new NaturalNumber2(2);
        NaturalNumber mExpected = new NaturalNumber2(2);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(nExpected, n);
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    ///This test case tests powerMod of (3,1,2).//
    @Test
    public void testPowerMod_3_1_2() {
        NaturalNumber n = new NaturalNumber2(3);
        NaturalNumber nExpected = new NaturalNumber2(1);
        NaturalNumber p = new NaturalNumber2(1);
        NaturalNumber pExpected = new NaturalNumber2(1);
        NaturalNumber m = new NaturalNumber2(2);
        NaturalNumber mExpected = new NaturalNumber2(2);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(nExpected, n);
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    /*
     * Tests of isWitnessToCompositeness
     */
    ///This test case tests testWitnessToComp of (6,9).//
    @Test
    public void testWitnessToComp_6_9() {
        NaturalNumber w = new NaturalNumber2(6);
        NaturalNumber wExpected = new NaturalNumber2(6);
        NaturalNumber n = new NaturalNumber2(9);
        NaturalNumber nExpected = new NaturalNumber2(9);
        boolean result = CryptoUtilities.isWitnessToCompositeness(w, n);
        assertEquals(wExpected, w);
        assertEquals(nExpected, n);
        assertEquals(nExpected, n);
        assertEquals(true, result);
    }

    ///This test case tests testWitnessToComp of (7,12).//
    @Test
    public void testWitnessToComp_7_12() {
        NaturalNumber w = new NaturalNumber2(7);
        NaturalNumber wExpected = new NaturalNumber2(7);
        NaturalNumber n = new NaturalNumber2(12);
        NaturalNumber nExpected = new NaturalNumber2(12);
        boolean result = CryptoUtilities.isWitnessToCompositeness(w, n);
        assertEquals(wExpected, w);
        assertEquals(nExpected, n);
        assertEquals(nExpected, n);
        assertEquals(true, result);
    }

    ///This test case tests testWitnessToComp of (3,5).//
    @Test
    public void testWitnessToComp_3_5() {
        NaturalNumber w = new NaturalNumber2(3);
        NaturalNumber wExpected = new NaturalNumber2(3);
        NaturalNumber n = new NaturalNumber2(5);
        NaturalNumber nExpected = new NaturalNumber2(5);
        boolean result = CryptoUtilities.isWitnessToCompositeness(w, n);
        assertEquals(wExpected, w);
        assertEquals(nExpected, n);
        assertEquals(nExpected, n);
        assertEquals(false, result);
    }

    ///This test case tests testWitnessToComp of (2,13).//
    @Test
    public void testWitnessToComp_2_13() {
        NaturalNumber w = new NaturalNumber2(2);
        NaturalNumber wExpected = new NaturalNumber2(2);
        NaturalNumber n = new NaturalNumber2(13);
        NaturalNumber nExpected = new NaturalNumber2(13);
        boolean result = CryptoUtilities.isWitnessToCompositeness(w, n);
        assertEquals(wExpected, w);
        assertEquals(nExpected, n);
        assertEquals(nExpected, n);
        assertEquals(false, result);
    }

    /*
     * Tests for isPrime1
     */
    ///This test case checks if 2 is a prime number, and it is.//
    @Test
    public void isPrime1_2() {
        NaturalNumber n = new NaturalNumber2(2);
        NaturalNumber nExpected = new NaturalNumber2(2);
        boolean result = CryptoUtilities.isPrime1(n);
        assertEquals(nExpected, n);
        assertEquals(true, result);
    }

    ///This test case checks if 3 is a prime number, and it is.//
    @Test
    public void isPrime1_3() {
        NaturalNumber n = new NaturalNumber2(3);
        NaturalNumber nExpected = new NaturalNumber2(3);
        boolean result = CryptoUtilities.isPrime1(n);
        assertEquals(nExpected, n);
        assertEquals(true, result);
    }

    ///This test case checks if 4 is a prime number, and it is not.//
    @Test
    public void isPrime1_4() {
        NaturalNumber n = new NaturalNumber2(4);
        NaturalNumber nExpected = new NaturalNumber2(4);
        boolean result = CryptoUtilities.isPrime1(n);
        assertEquals(nExpected, n);
        assertEquals(false, result);
    }

    ///This test case checks if 5 is a prime number, and it is.//
    @Test
    public void isPrime1_5() {
        NaturalNumber n = new NaturalNumber2(5);
        NaturalNumber nExpected = new NaturalNumber2(5);
        boolean result = CryptoUtilities.isPrime1(n);
        assertEquals(nExpected, n);
        assertEquals(true, result);
    }

    ///This test case checks if 9 is a prime number, and it is not.//
    @Test
    public void isPrime1_9() {
        NaturalNumber n = new NaturalNumber2(9);
        NaturalNumber nExpected = new NaturalNumber2(9);
        boolean result = CryptoUtilities.isPrime1(n);
        assertEquals(nExpected, n);
        assertEquals(false, result);
    }

    ///This test case checks if 33 is a prime number, and it is not.//
    @Test
    public void isPrime1_33() {
        NaturalNumber n = new NaturalNumber2(55);
        NaturalNumber nExpected = new NaturalNumber2(55);
        boolean result = CryptoUtilities.isPrime1(n);
        assertEquals(nExpected, n);
        assertEquals(false, result);
    }

    ///This test case checks if 1234 is a prime number, and it is nnot.//
    @Test
    public void isPrime1_1234() {
        NaturalNumber n = new NaturalNumber2(1234);
        NaturalNumber nExpected = new NaturalNumber2(1234);
        boolean result = CryptoUtilities.isPrime1(n);
        assertEquals(nExpected, n);
        assertEquals(false, result);
    }

    ///This test case checks if 100001 is a prime number, and it is not.//
    @Test
    public void isPrime1_100001() {
        NaturalNumber n = new NaturalNumber2(100001);
        NaturalNumber nExpected = new NaturalNumber2(100001);
        boolean result = CryptoUtilities.isPrime1(n);
        assertEquals(nExpected, n);
        assertEquals(false, result);
    }

    ///This test case checks if 7919 is a prime number, and it is.//
    @Test
    public void isPrime1_7919() {
        NaturalNumber n = new NaturalNumber2(7919);
        NaturalNumber nExpected = new NaturalNumber2(7919);
        boolean result = CryptoUtilities.isPrime1(n);
        assertEquals(nExpected, n);
        assertEquals(true, result);
    }

    /*
     * Tests for isPrime2
     */
    ///This test case checks if 2 is a prime number, and it is.//
    @Test
    public void isPrime2_2() {
        NaturalNumber n = new NaturalNumber2(2);
        NaturalNumber nExpected = new NaturalNumber2(2);
        boolean result = CryptoUtilities.isPrime2(n);
        assertEquals(nExpected, n);
        assertEquals(true, result);
    }

    ///This test case checks if 3 is a prime number, and it is.//
    @Test
    public void isPrime2_3() {
        NaturalNumber n = new NaturalNumber2(3);
        NaturalNumber nExpected = new NaturalNumber2(3);
        boolean result = CryptoUtilities.isPrime2(n);
        assertEquals(nExpected, n);
        assertEquals(true, result);
    }

    ///This test case checks if 4 is a prime number, and it is not.//
    @Test
    public void isPrime2_4() {
        NaturalNumber n = new NaturalNumber2(4);
        NaturalNumber nExpected = new NaturalNumber2(4);
        boolean result = CryptoUtilities.isPrime2(n);
        assertEquals(nExpected, n);
        assertEquals(false, result);
    }

    ///This test case checks if 5 is a prime number, and it is.//
    @Test
    public void isPrime2_5() {
        NaturalNumber n = new NaturalNumber2(5);
        NaturalNumber nExpected = new NaturalNumber2(5);
        boolean result = CryptoUtilities.isPrime2(n);
        assertEquals(nExpected, n);
        assertEquals(true, result);
    }

    /*
     * Tests for generateNextLikelyPrime
     */
    ///This test case generates the next likely prime number for 2 which is 3.//
    @Test
    public void generateNextLikelyPrime_2() {
        NaturalNumber n = new NaturalNumber2(2);
        NaturalNumber nExpected = new NaturalNumber2(3);
        CryptoUtilities.generateNextLikelyPrime(n);
        assertEquals(nExpected, n);
        //ssert
    }

    ///This test case generates the next likely prime number for 3 which is 5.//
    @Test
    public void generateNextLikelyPrime_3() {
        NaturalNumber n = new NaturalNumber2(3);
        NaturalNumber nExpected = new NaturalNumber2(5);
        CryptoUtilities.generateNextLikelyPrime(n);
        assertEquals(nExpected, n);
    }

    ///This test case generates the next likely prime number for 4 which is 5.//
    @Test
    public void generateNextLikelyPrime_4() {
        NaturalNumber n = new NaturalNumber2(4);
        NaturalNumber nExpected = new NaturalNumber2(5);
        CryptoUtilities.generateNextLikelyPrime(n);
        assertEquals(nExpected, n);
    }

    ///This test case generates the next likely prime number for 5 which is 7.//
    @Test
    public void generateNextLikelyPrime_5() {
        NaturalNumber n = new NaturalNumber2(5);
        NaturalNumber nExpected = new NaturalNumber2(7);
        CryptoUtilities.generateNextLikelyPrime(n);
        assertEquals(nExpected, n);
    }

    ///This test case generates the next likely prime number for 11 which is//
    //13.//
    @Test
    public void generateNextLikelyPrime_11() {
        NaturalNumber n = new NaturalNumber2(11);
        NaturalNumber nExpected = new NaturalNumber2(13);
        CryptoUtilities.generateNextLikelyPrime(n);
        assertEquals(nExpected, n);
    }

    ///This test case generates the next likely prime number for 100 which is//
    //101.//
    @Test
    public void generateNextLikelyPrime_100() {
        NaturalNumber n = new NaturalNumber2(100);
        NaturalNumber nExpected = new NaturalNumber2(101);
        CryptoUtilities.generateNextLikelyPrime(n);
        assertEquals(nExpected, n);
    }

    ///This test case generates the next likely prime number for 15000 which is//
    //15001.//
    @Test
    public void generateNextLikelyPrime_15000() {
        NaturalNumber n = new NaturalNumber2(15000);
        NaturalNumber nExpected = new NaturalNumber2(15001);
        CryptoUtilities.generateNextLikelyPrime(n);
        assertEquals(nExpected, n);
    }

    ///This test case generates the next likely prime number for 17005 which is//
    //17007.//
    @Test
    public void generateNextLikelyPrime_17005() {
        NaturalNumber n = new NaturalNumber2(17005);
        NaturalNumber nExpected = new NaturalNumber2(17007);
        CryptoUtilities.generateNextLikelyPrime(n);
        assertEquals(nExpected, n);
    }

    ///This test case generates the next likely prime number for 20402 which is//
    //20403.//
    @Test
    public void generateNextLikelyPrime_20402() {
        NaturalNumber n = new NaturalNumber2(20402);
        NaturalNumber nExpected = new NaturalNumber2(20403);
        CryptoUtilities.generateNextLikelyPrime(n);
        assertEquals(nExpected, n);
    }

}
