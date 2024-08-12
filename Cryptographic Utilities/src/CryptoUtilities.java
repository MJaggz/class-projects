import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.random.Random;
import components.random.Random1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Utilities that could be used with RSA cryptosystems.
 *
 * @author Mohamed Jama
 *
 */
public final class CryptoUtilities {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private CryptoUtilities() {
    }

    /**
     * Useful constant, not a magic number: 3. This will be used in a later
     * method.
     */
    private static final int THREE = 3;
    /**
     * Useful constant, not a magic number: 4. This will be used in a later
     * method.
     */
    private static final int FOUR = 4;
    /**
     * Useful constant, not a magic number: 50.
     */
    private static final int NUMBER_OF_WITNESSES = 50;

    /**
     * Pseudo-random number generator.
     */
    private static final Random GENERATOR = new Random1L();

    /**
     * Returns a random number uniformly distributed in the interval [0, n].
     *
     * @param n
     *            top end of interval
     * @return random number in interval
     * @requires n > 0
     * @ensures <pre>
     * randomNumber = [a random number uniformly distributed in [0, n]]
     * </pre>
     */
    public static NaturalNumber randomNumber(NaturalNumber n) {
        assert !n.isZero() : "Violation of: n > 0";
        final int base = 10;
        NaturalNumber result;
        int d = n.divideBy10();
        if (n.isZero()) {
            /*
             * Incoming n has only one digit and it is d, so generate a random
             * number uniformly distributed in [0, d]
             */
            int x = (int) ((d + 1) * GENERATOR.nextDouble());
            result = new NaturalNumber2(x);
            n.multiplyBy10(d);
        } else {
            /*
             * Incoming n has more than one digit, so generate a random number
             * (NaturalNumber) uniformly distributed in [0, n], and another
             * (int) uniformly distributed in [0, 9] (i.e., a random digit)
             */
            result = randomNumber(n);
            int lastDigit = (int) (base * GENERATOR.nextDouble());
            result.multiplyBy10(lastDigit);
            n.multiplyBy10(d);
            if (result.compareTo(n) > 0) {
                /*
                 * In this case, we need to try again because generated number
                 * is greater than n; the recursive call's argument is not
                 * "smaller" than the incoming value of n, but this recursive
                 * call has no more than a 90% chance of being made (and for
                 * large n, far less than that), so the probability of
                 * termination is 1
                 */
                result = randomNumber(n);
            }
        }
        return result;
    }

    /**
     * Finds the greatest common divisor of n and m.
     *
     * @param n
     *            one number
     * @param m
     *            the other number
     * @updates n
     * @clears m
     * @ensures n = [greatest common divisor of #n and #m]
     */
    public static void reduceToGCD(NaturalNumber n, NaturalNumber m) {

        /*
         * Use Euclid's algorithm; in pseudocode: if m = 0 then GCD(n, m) = n
         * else GCD(n, m) = GCD(m, n mod m)
         */

        if (!m.isZero()) {
            //Use division to divide n and m to get the remainder.//
            NaturalNumber reduceGCD = n.divide(m);
            //Set n to the m.//
            n.copyFrom(m);
            //Give m the remainder value of the division here.///
            m.copyFrom(reduceGCD);
            //Recursive call to find the gcd.//
            reduceToGCD(n, m);
        }

    }

    /**
     * Reports whether n is even.
     *
     * @param n
     *            the number to be checked
     * @return true iff n is even
     * @ensures isEven = (n mod 2 = 0)
     */
    public static boolean isEven(NaturalNumber n) {

        //Set a isEven case as false to start.//
        boolean isEven = false;
        //Take off the last digit and divide by 10.//
        int lastDigitNumber = n.divideBy10();
        if (lastDigitNumber % 2 == 0) {
            //If the last digit is even, return true here.///
            isEven = true;
        }
        //At the end ad the last digit back.//
        n.multiplyBy10(lastDigitNumber);
        //Return if the number is even or not.//
        return isEven;
    }

    /**
     * Updates n to its p-th power modulo m.
     *
     * @param n
     *            number to be raised to a power
     * @param p
     *            the power
     * @param m
     *            the modulus
     * @updates n
     * @requires m > 1
     * @ensures n = #n ^ (p) mod m
     */
    public static void powerMod(NaturalNumber n, NaturalNumber p,
            NaturalNumber m) {
        assert m.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: m > 1";

        /*
         * Use the fast-powering algorithm as previously discussed in class,
         * with the additional feature that every multiplication is followed
         * immediately by "reducing the result modulo m"
         */

        //Initialize NaturalNumber variables for number, power, one, and two.//
        NaturalNumber number = new NaturalNumber2(n);
        NaturalNumber power = new NaturalNumber2(p);
        NaturalNumber one = new NaturalNumber2(1);
        NaturalNumber two = new NaturalNumber2(2);

        //Check to see if the power is zero. if so copy from one.//
        if (power.isZero()) {
            n.copyFrom(one);

        } else {
            //If it is not 0, check if power is even.//
            if (isEven(power)) {
                //If power is even, divide by two and make a recursive call.//
                power.divide(two);
                powerMod(n, power, m);
                //At the end store the value.///
                number.copyFrom(n);

            } else {
                //If not even, decrement power,///
                power.decrement();
                number.copyFrom(n);
                //After decrementing make the recursive call.//
                powerMod(n, power, m);
            }
            //At the end of either the if or else statement, multiply and take//
            //the mod and store it in result.///
            n.multiply(number);
            NaturalNumber result = n.divide(m);
            n.copyFrom(result);
        }

    }

    /**
     * Reports whether w is a "witness" that n is composite, in the sense that
     * either it is a square root of 1 (mod n), or it fails to satisfy the
     * criterion for primality from Fermat's theorem.
     *
     * @param w
     *            witness candidate
     * @param n
     *            number being checked
     * @return true iff w is a "witness" that n is composite
     * @requires n > 2 and 1 < w < n - 1
     * @ensures <pre>
     * isWitnessToCompositeness =
     *     (w ^ 2 mod n = 1)  or  (w ^ (n-1) mod n /= 1)
     * </pre>
     */
    public static boolean isWitnessToCompositeness(NaturalNumber w,
            NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(2)) > 0 : "Violation of: n > 2";
        assert (new NaturalNumber2(1)).compareTo(w) < 0 : "Violation of: 1 < w";
        n.decrement();
        assert w.compareTo(n) < 0 : "Violation of: w < n - 1";
        n.increment();

        //Make a boolean isWitness case and set it to false at the start.//
        boolean isWitness = false;
        //Initialize NaturalNumbers for witness, number, one, and two.//
        NaturalNumber number = new NaturalNumber2(n);
        NaturalNumber witness = new NaturalNumber2(w);
        NaturalNumber one = new NaturalNumber2(1);
        NaturalNumber two = new NaturalNumber2(2);
        //Start off by decrementing number.//
        number.decrement();

        //Call to the powerMod method to see if the mod is one.//
        powerMod(witness, two, n);
        if (witness.compareTo(one) == 0) {
            //If so, set isWitness to true.//
            isWitness = true;
        }
        //Call to the powerMod method again to see if the mod is one.//
        powerMod(witness, number, n);
        if (witness.compareTo(one) != 0) {
            //If it is not, also set isWitness to true.///
            isWitness = true;
        }

        //Return the boolean if it is true or not.//
        return isWitness;

    }

    /**
     * Reports whether n is a prime; may be wrong with "low" probability.
     *
     * @param n
     *            number to be checked
     * @return true means n is very likely prime; false means n is definitely
     *         composite
     * @requires n > 1
     * @ensures <pre>
     * isPrime1 = [n is a prime number, with small probability of error
     *         if it is reported to be prime, and no chance of error if it is
     *         reported to be composite]
     * </pre>
     */
    public static boolean isPrime1(NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: n > 1";
        boolean isPrime;
        if (n.compareTo(new NaturalNumber2(THREE)) <= 0) {
            /*
             * 2 and 3 are primes
             */
            isPrime = true;
        } else if (isEven(n)) {
            /*
             * evens are composite
             */
            isPrime = false;
        } else {
            /*
             * odd n >= 5: simply check whether 2 is a witness that n is
             * composite (which works surprisingly well :-)
             */
            isPrime = !isWitnessToCompositeness(new NaturalNumber2(2), n);
        }
        return isPrime;
    }

    /**
     * Reports whether n is a prime; may be wrong with "low" probability.
     *
     * @param n
     *            number to be checked
     * @return true means n is very likely prime; false means n is definitely
     *         composite
     * @requires n > 1
     * @ensures <pre>
     * isPrime2 = [n is a prime number, with small probability of error
     *         if it is reported to be prime, and no chance of error if it is
     *         reported to be composite]
     * </pre>
     */
    public static boolean isPrime2(NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: n > 1";

        /*
         * Use the ability to generate random numbers (provided by the
         * randomNumber method above) to generate several witness candidates --
         * say, 10 to 50 candidates -- guessing that n is prime only if none of
         * these candidates is a witness to n being composite (based on fact #3
         * as described in the project description); use the code for isPrime1
         * as a guide for how to do this, and pay attention to the requires
         * clause of isWitnessToCompositeness
         */

        //Make a boolean isPrime2 case and set it to true to start.//
        boolean isPrime2 = true;
        //Initializze NaturalNumber variables for two and four.//
        NaturalNumber two = new NaturalNumber2(2);
        NaturalNumber four = new NaturalNumber2(FOUR);

        if (n.compareTo(new NaturalNumber2(THREE)) <= 0) {
            isPrime2 = true;
            //Check if n is an even number, if so set boolean to false.//
        } else if (isEven(n)) {
            isPrime2 = false;
        } else {
            //Use a for loop to look at the witness candidates up to 50.///
            for (int i = 0; i < NUMBER_OF_WITNESSES; i++) {
                NaturalNumber number = new NaturalNumber2(n);
                //Subtracting four to check the range is less than n-2.//
                number.subtract(four);

                NaturalNumber random = randomNumber(number);
                //Adding two to change the range to (n, n-2)
                random.add(two);

                while (random.compareTo(two) < 0) {
                    //Check to see if random is less than two.///
                    random = randomNumber(n);
                    //Recursive call inside the if statement.//
                    if (isWitnessToCompositeness(random, n)) {
                        //If a witness was found, set isPrime2 to false
                        isPrime2 = false;
                    }
                }

            }

        }

        return isPrime2;
    }

    /**
     * Generates a likely prime number at least as large as some given number.
     *
     * @param n
     *            minimum value of likely prime
     * @updates n
     * @requires n > 1
     * @ensures n >= #n and [n is very likely a prime number]
     */
    public static void generateNextLikelyPrime(NaturalNumber n) {
        assert n.compareTo(new NaturalNumber2(1)) > 0 : "Violation of: n > 1";

        //Initialize new NaturalNumber variables for one and two.//
        NaturalNumber one = new NaturalNumber2(1);
        NaturalNumber two = new NaturalNumber2(2);
        //Check to see if n is even, if so add one.
        if (isEven(n)) {
            n.add(one);
        } else {
            //If n is not even, add two
            n.add(two);
        }
        //After the if statement, check to see if n is now a prime number.//
        while (!isPrime2(n)) {
            //If n is not, add two again.//
            n.add(two);
        }

    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        /*
         * Sanity check of randomNumber method -- just so everyone can see how
         * it might be "tested"
         */
        final int testValue = 17;
        final int testSamples = 100000;
        NaturalNumber test = new NaturalNumber2(testValue);
        int[] count = new int[testValue + 1];
        for (int i = 0; i < count.length; i++) {
            count[i] = 0;
        }
        for (int i = 0; i < testSamples; i++) {
            NaturalNumber rn = randomNumber(test);
            assert rn.compareTo(test) <= 0 : "Help!";
            count[rn.toInt()]++;
        }
        for (int i = 0; i < count.length; i++) {
            out.println("count[" + i + "] = " + count[i]);
        }
        out.println("  expected value = "
                + (double) testSamples / (double) (testValue + 1));

        /*
         * Check user-supplied numbers for primality, and if a number is not
         * prime, find the next likely prime after it
         */
        while (true) {
            out.print("n = ");
            NaturalNumber n = new NaturalNumber2(in.nextLine());
            if (n.compareTo(new NaturalNumber2(2)) < 0) {
                out.println("Bye!");
                break;
            } else {
                if (isPrime1(n)) {
                    out.println(n + " is probably a prime number"
                            + " according to isPrime1.");
                } else {
                    out.println(n + " is a composite number"
                            + " according to isPrime1.");
                }
                if (isPrime2(n)) {
                    out.println(n + " is probably a prime number"
                            + " according to isPrime2.");
                } else {
                    out.println(n + " is a composite number"
                            + " according to isPrime2.");
                    generateNextLikelyPrime(n);
                    out.println("  next likely prime is " + n);
                }
            }
        }

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
