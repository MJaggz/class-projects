import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Program with implementation of {@code NaturalNumber} secondary operation
 * {@code root} implemented as static method. Using the integerRoot lab to help
 * me with this project and guide me.
 *
 * @Mohamed Jama
 *
 */
public final class NaturalNumberRoot {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private NaturalNumberRoot() {
    }

    /**
     * Updates {@code n} to the {@code r}-th root of its incoming value.
     *
     * @param n
     *            the number whose root to compute
     * @param r
     *            root
     * @updates n
     * @requires r >= 2
     * @ensures n ^ (r) <= #n < (n + 1) ^ (r)
     */
    public static void root(NaturalNumber n, int r) {
        assert n != null : "Violation of: n is  not null";
        assert r >= 2 : "Violation of: r >= 2";

        ///Initialize the range variables of n and initialize a guess variable.///
        NaturalNumber lowEnough = new NaturalNumber2();
        NaturalNumber tooHigh = new NaturalNumber2(n);
        NaturalNumber guess = new NaturalNumber2();

        ///Initialize two NaturalNumber variables that will hold the values of
        ///two and one which will be used for with calculating and comparing
        ///values.///
        NaturalNumber numberOne = new NaturalNumber2(1);
        NaturalNumber numberTwo = new NaturalNumber2(2);

        ///Set the upper bounds of n by making tooHigh be (n + 1) which will
        ///not be in the range.///
        tooHigh.increment();

        ///Initialize a NaturalNumber variable named difference that will be the
        ///difference between tooHigh and lowEnough.///
        NaturalNumber difference = new NaturalNumber2();
        ///Copying tooHigh to difference and not transferring since I do not
        ///want to change the value of tooHigh and then subtracting
        ///lowEnough.///
        difference.copyFrom(tooHigh);
        difference.subtract(lowEnough);

        ////While loop comparing the difference between tooHigh and lowEnough
        ///to numberOne.
        while (difference.compareTo(numberOne) > 0) {
            ///Using the variable guess to find the midpoint of my range by first
            ///copying tooHigh to guess, adding lowEnough, and then dividing by
            ///numberTwo.///
            guess.copyFrom(tooHigh);
            guess.add(lowEnough);
            guess.divide(numberTwo);
            ///Initialize a new variable called guessPower which will then be raised
            ///to n.///
            NaturalNumber guessPower = new NaturalNumber2(guess);
            guessPower.power(r);

            ///If guessPower is too large, update the value of tooHigh by copying
            ///guess to tooHigh.///
            if (guessPower.compareTo(n) > 0) {
                tooHigh.copyFrom(guess);
            } else {
                ///Else statement if guessPower is too small, update the value of
                ///lowEnough by copying guess to lowEnough.///
                lowEnough.copyFrom(guess);
            }
            ///Checking the difference between tooHigh and lowEnough so that if the
            ///diffference is bigger than numberOne, the loop will continue.///
            difference.copyFrom(tooHigh);
            difference.subtract(lowEnough);

        }
        ///After finding the largest r-th root of n, transfer it to n instead of
        ///copying since there is no reason for aliases.///
        n.transferFrom(lowEnough);

    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();

        final String[] numbers = { "0", "1", "13", "1024", "189943527", "0",
                "1", "13", "4096", "189943527", "0", "1", "13", "1024",
                "189943527", "82", "82", "82", "82", "82", "9", "27", "81",
                "243", "143489073", "2147483647", "2147483648",
                "9223372036854775807", "9223372036854775808",
                "618970019642690137449562111",
                "162259276829213363391578010288127",
                "170141183460469231731687303715884105727" };
        final int[] roots = { 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 15, 15, 15, 15, 15,
                2, 3, 4, 5, 15, 2, 3, 4, 5, 15, 2, 2, 3, 3, 4, 5, 6 };
        final String[] results = { "0", "1", "3", "32", "13782", "0", "1", "2",
                "16", "574", "0", "1", "1", "1", "3", "9", "4", "3", "2", "1",
                "3", "3", "3", "3", "3", "46340", "46340", "2097151", "2097152",
                "4987896", "2767208", "2353973" };

        for (int i = 0; i < numbers.length; i++) {
            NaturalNumber n = new NaturalNumber2(numbers[i]);
            NaturalNumber r = new NaturalNumber2(results[i]);
            root(n, roots[i]);
            if (n.equals(r)) {
                out.println("Test " + (i + 1) + " passed: root(" + numbers[i]
                        + ", " + roots[i] + ") = " + results[i]);
            } else {
                out.println("*** Test " + (i + 1) + " failed: root("
                        + numbers[i] + ", " + roots[i] + ") expected <"
                        + results[i] + "> but was <" + n + ">");
            }
        }

        out.close();
    }

}
