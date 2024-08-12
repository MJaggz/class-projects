import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.Reporter;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code NN}.
 *
 * @author Mohamed Jama
 *
 */
public final class XMLTreeNNExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeNNExpressionEvaluator() {
    }

    /**
     * Evaluate the given expression.
     *
     * @param exp
     *            the {@code XMLTree} representing the expression
     * @return the value of the expression
     * @requires <pre>
     * [exp is a subtree of a well-formed XML arithmetic expression]  and
     *  [the label of the root of exp is not "expression"]
     * </pre>
     * @ensures evaluate = [the value of the expression]
     */
    private static NaturalNumber evaluate(XMLTree exp) {

        ///Initialize new NaturalNumber variables for the result, right value,
        ///and the left value.///
        NaturalNumber result = new NaturalNumber2(0);
        NaturalNumber rightValue = new NaturalNumber2(0);
        NaturalNumber leftValue = new NaturalNumber2(0);

        ///Just like the intExpressionEvaluator, this is the base case where
        ///if there are no children store that number in result.///
        if (exp.numberOfChildren() == 0) {
            result = new NaturalNumber2(exp.attributeValue("value"));

        } else {
            ///If there are children, find the left and right value using
            ///recursion.///
            leftValue = evaluate(exp.child(0));
            rightValue = evaluate(exp.child(1));
        }
        ///If the operator label is "plus", add both the left and right value
        ///to the NaturalNumber resultt variable.//
        if (exp.label().equals("plus")) {
            result.add(leftValue);
            result.add(rightValue);

            ///Else if the operator label is "minus", and if the right value is
            ///less than the left value, add the left value to result, then
            ///subtract the right value.///
        } else if (exp.label().equals("minus")) {
            if (rightValue.compareTo(leftValue) > 0) {
                ///If the right value is large than the left value, report the
                ///error using the fatal error report method.///
                Reporter.fatalErrorToConsole("Math Error: This would result"
                        + " in a negative number.");
            } else {
                result.add(leftValue);
                result.subtract(rightValue);
            }
            ///If the operator label is "times", add the left value to result
            ///and multiply result and the right value.//
        } else if (exp.label().equals("times")) {
            result.add(leftValue);
            result.multiply(rightValue);

            ///If the operator label is "divide", and if the right value is not
            ///zero, add the left value to result and then divide the right
            ///value.///
        } else if (exp.label().equals("divide")) {
            if (rightValue.isZero()) {
                ///If the right value ends up being zero, report the error
                ///using the fatal report error method.///
                Reporter.fatalErrorToConsole("Math Error: Division by 0");
            } else {
                result.add(leftValue);
                result.divide(rightValue);
            }
        }
        ///Return the NaturalNumber result at the end of the expression at the
        ///end.///
        return result;
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

        out.print("Enter the name of an expression XML file: ");
        String file = in.nextLine();
        while (!file.equals("")) {
            XMLTree exp = new XMLTree1(file);
            out.println(evaluate(exp.child(0)));
            out.print("Enter the name of an expression XML file: ");
            file = in.nextLine();
        }

        in.close();
        out.close();
    }

}
