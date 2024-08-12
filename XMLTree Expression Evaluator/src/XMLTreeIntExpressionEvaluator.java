import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code int}.
 *
 * @author Mohamed Jama
 *
 */
public final class XMLTreeIntExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeIntExpressionEvaluator() {
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
    private static int evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";
        ///Initialize variables for the leftValue, right value, and result.///
        int result = 0;
        int rightValue = 0;
        int leftValue = 0;
        ///This is the base case where if there are no children, return that
        ///number and store it in result.///
        if (exp.numberOfChildren() == 0) {
            result = Integer.parseInt(exp.attributeValue("value"));

        } else {
            ///If there are children, it is an operator and find the left value
            ///and the right value using recursion.///
            leftValue = evaluate(exp.child(0));
            rightValue = evaluate(exp.child(1));
        }
        ///If the operator is "plus", add both the left and right values and
        ///store it in result.///
        if (exp.label().equals("plus")) {
            result = (leftValue + rightValue);

            ///Else if the operator is "minus", subtract both the left and right
            ///values and store it in result.///
        } else if (exp.label().equals("minus")) {
            result = (leftValue - rightValue);

            ///Else if the operator is "times", multiply both the right and left
            ///values and store it in result.///
        } else if (exp.label().equals("times")) {
            result = (leftValue * rightValue);

            ///Else if the operator is "divide", divide the left value from the
            ///right value and store it in result.///
        } else if (exp.label().equals("divide")) {
            result = (leftValue / rightValue);

        }
        ///Return the result of the expression at the end.///
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
