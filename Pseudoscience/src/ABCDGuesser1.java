import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.FormatChecker;

/**
 * This code asks the user for 5 values, w,x,y,z and a μ value. After that, the
 * this code approximates the user value of μ within 0.01% and uses the de Jager
 * formula using array values to find combinations of values that will be
 * relativity close to the user input of μ.
 *
 * @author Mohamed Jama
 *
 */
public final class ABCDGuesser1 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private ABCDGuesser1() {
    }

    /**
     * Creates a final double number named RELATIVE_ERROR which is set to 0.01
     * so that it deals with magic numbers and can not be changed since it is a
     * final number. 0.01 is also the relative error estimate.
     */
    private static final double RELATIVE_ERROR = 0.01;

    /**
     * . Creates a final integer number named NUMBER_THREE which is set to 3 so
     * that it deals with magic numbers in my code for where the number 3 is
     * needed.
     */
    private static final int NUMBER_THREE = 3;

    /**
     * Repeatedly asks the user for a positive real number until the user enters
     * one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number entered by the user
     * @requires x > 0
     * @ensures <pre>
     *  μ > 0
     *  </pre>
     */
    private static double getPositiveDouble(SimpleReader in, SimpleWriter out) {
        /// Use -1 to initialize and start while loop///
        double positiveValue = -1;
        /// While loop that ensures that μ will be a positive number//
        while (positiveValue <= 0) {
            out.print("Enter a positive real number to approximate μ: ");
            /**
             * FormatChecker makes sure that the string entered is a number and
             * converts the string to a double number.
             */
            String stringNumber = in.nextLine();
            if (FormatChecker.canParseDouble(stringNumber)) {
                positiveValue = Double.parseDouble(stringNumber);
                if (positiveValue <= 0) {
                    out.println(
                            "Error. Please retry with a positive real number.");
                }
            } else {
                out.println("Error. Please enter a real number.");
            }
        }
        return positiveValue;
    }

    /**
     * Repeatedly asks the user for a positive real number not equal to 1.0
     * until the user enters one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number not equal to 1.0 entered by the user
     * @requires x > 0 && x != 1
     * @ensures <pre> [that variables w,x,y,z are positive variables that can
     * help with the equation]
     * </pre>
     */
    private static double getPositiveDoubleNotOne(SimpleReader in,
            SimpleWriter out) {
        /// Use -1 to initialize and start while loop///
        double positiveValue = -1;
        /**
         * While loop to ensure that w,x,y,z are numbers that are positive and
         * not one.
         */
        while (positiveValue <= 0
                || Math.abs(positiveValue - 1.0) < RELATIVE_ERROR) {
            out.print("Enter a postitive number that is not 1.0: ");
            /**
             * FormatChecker makes sure that the string entered is a number and
             * converts the string to a double number also in this method.
             */
            String stringNumber = in.nextLine();
            if (FormatChecker.canParseDouble(stringNumber)) {
                positiveValue = Double.parseDouble(stringNumber);
                if (positiveValue <= 0
                        || Math.abs(positiveValue) < RELATIVE_ERROR) {
                    out.println(
                            "Error. please retry with a positive number that is not 1.0");
                }
            }
        }
        return positiveValue;
    }

    /**
     * Uses the 5 values the user enters in the main statement to calculate and
     * find the exponents a,b,c,d which when raised to w,x,y,z will give out the
     * combination that minimizes the percent error of μ.
     *
     * @param microOne
     * @param w
     * @param x
     * @param y
     * @param z
     * @return array values that help with approximating microOne(μ)
     * @requires the previous variables are true and can be used. Also requires
     *           the 17 numbers in an array.
     * @ensures <pre> [that this method finds and gives the best a,b,c,d values
     * that can help compute microOne(μ)]
     * </pre>
     */
    private static double[] formulaExponents(double microOne, double w,
            double x, double y, double z) {
        /**
         * Initialize a final integer 4 so that it is not a magic number and is
         * an empty array that can hold 4 numbers.
         */
        final int four = 4;
        /// Create an empty array that will store 4 numbers///
        double[] resultExponents = new double[four];
        double goodError = -1;
        /// These are the 17 values that will help compute μ///
        final double[] exponentsArray = { -5, -4, -3, -2, -1, (-1.0 / 2.0),
                (-1.0 / 3.0), (-1.0 / 4.0), 0, (1.0 / 4.0), (1.0 / 3.0),
                (1.0 / 2.0), 1, 2, 3, 4, 5 };

        /**
         * While loops inside while loops that will try to find and will
         * approximate a,b,c,d values that are the closet to μ.
         */
        int i = 0;
        while (i < exponentsArray.length) {
            double a = exponentsArray[i];
            int j = 0;
            while (j < exponentsArray.length) {
                double b = exponentsArray[j];
                int k = 0;
                while (k < exponentsArray.length) {
                    double c = exponentsArray[k];
                    int l = 0;
                    while (l < exponentsArray.length) {
                        double d = exponentsArray[l];
                        /// Using the de Jager formula in the equation below///
                        double approxValue = Math.pow(w, a) * Math.pow(x, b)
                                * Math.pow(y, c) * Math.pow(z, d);
                        double relativeError = Math.abs(approxValue - microOne)
                                / microOne;
                        /**
                         * If statement at the end that will give the exponents
                         * that are the closest to μ
                         */

                        if (goodError < 0 || relativeError < goodError) {
                            goodError = relativeError;
                            resultExponents[0] = a;
                            resultExponents[1] = b;
                            resultExponents[2] = c;
                            resultExponents[NUMBER_THREE] = d;
                        }
                        l++;
                    }
                    k++;
                }
                j++;
            }
            i++;
        }
        return resultExponents;
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
        /// Prompt for a μ and use the getPositiveDouble method///
        double microOne = getPositiveDouble(in, out);
        /// Prompt for w,x,y,z values which will be positive and not 1.0///
        out.println("Enter numbers for (w,x,y,z): ");
        double w = getPositiveDoubleNotOne(in, out);
        double x = getPositiveDoubleNotOne(in, out);
        double y = getPositiveDoubleNotOne(in, out);
        double z = getPositiveDoubleNotOne(in, out);
        /**
         * formulaExponents will return the exponents using w,x,y,z that the
         * user entered to show the closest exponents to μ and store the results
         * as finalResultExponents.
         */
        double[] finalResultExponents = formulaExponents(microOne, w, x, y, z);
        /**
         * Initialize a final integer in main instead of a global final integer
         * that will be used to find the relative percent.
         */
        final int hundred = 100;
        /// An equation used to find the approximate value///
        double approxValue = Math.pow(w, finalResultExponents[0])
                * Math.pow(x, finalResultExponents[1])
                * Math.pow(y, finalResultExponents[2])
                * Math.pow(z, finalResultExponents[NUMBER_THREE]);
        /// An equation that is used to find the relative percent///
        double relativePercent = Math.abs(approxValue - microOne) / microOne
                * hundred;

        /**
         * Print out the exponents statements, approximate value statement, and
         * the relative percent statement.
         */
        out.println("Here are the following exponents:");
        out.println("a = " + finalResultExponents[0]);
        out.println("b = " + finalResultExponents[1]);
        out.println("c = " + finalResultExponents[2]);
        out.println("d = " + finalResultExponents[NUMBER_THREE]);
        out.println("Your approximate value of μ is " + approxValue);
        out.println("Your relative percent is: " + relativePercent);

        out.println("Goodbye");

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
