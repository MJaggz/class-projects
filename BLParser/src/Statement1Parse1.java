import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.statement.Statement1;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary methods {@code parse} and
 * {@code parseBlock} for {@code Statement}.
 *
 * @author Mohamed Jama & Emanuel Messele
 *
 */
public final class Statement1Parse1 extends Statement1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Converts {@code c} into the corresponding {@code Condition}.
     *
     * @param c
     *            the condition to convert
     * @return the {@code Condition} corresponding to {@code c}
     * @requires [c is a condition string]
     * @ensures parseCondition = [Condition corresponding to c]
     */
    private static Condition parseCondition(String c) {
        assert c != null : "Violation of: c is not null";
        assert Tokenizer.isCondition(c) : "Violation of: c is a condition string";
        return Condition.valueOf(c.replace('-', '_').toUpperCase());
    }

    /**
     * Parses an IF or IF_ELSE statement from {@code tokens} into {@code s}.
     *
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces s
     * @updates tokens
     * @requires <pre>
     * [<"IF"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [an if string is a proper prefix of #tokens] then
     *  s = [IF or IF_ELSE Statement corresponding to if string at start of #tokens]  and
     *  #tokens = [if string at start of #tokens] * tokens
     * else
     *  [reports an appropriate error message to the console and terminates client]
     * </pre>
     */
    private static void parseIf(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0 && tokens.front().equals("IF")
                : "" + "Violation of: <\"IF\"> is proper prefix of tokens";

        //Start off by dequeuing IF.
        tokens.dequeue();

        //Checking the parse condition.
        String conditionStr = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isCondition(conditionStr),
                "Error: " + conditionStr + " is not a valid BL condition");
        Condition c = parseCondition(conditionStr);

        //Checking THEN and then parsing the THEN block.
        String thenStr = tokens.dequeue();
        Reporter.assertElseFatalError(thenStr.equals("THEN"),
                "THEN expected, found " + thenStr);
        Statement thenBlock = s.newInstance();
        thenBlock.parseBlock(tokens);

        //Checking for ELSE and END.
        String endOrElse = tokens.dequeue();
        Statement elseBlock = s.newInstance();
        boolean elseBlockExists = false;
        if (endOrElse.equals("ELSE")) {
            elseBlock.parseBlock(tokens);
            endOrElse = tokens.dequeue();
            elseBlockExists = true;
        }
        Reporter.assertElseFatalError(endOrElse.equals("END"),
                "Error: END expected, found " + endOrElse);
        endOrElse = tokens.dequeue();
        Reporter.assertElseFatalError(endOrElse.equals("IF"),
                "Error: IF expected, found " + endOrElse);

        //Assembling the IF or IF_ELSE statement.
        if (elseBlockExists) {
            s.assembleIfElse(c, thenBlock, elseBlock);
        } else {
            s.assembleIf(c, thenBlock);
        }

    }

    /**
     * Parses a WHILE statement from {@code tokens} into {@code s}.
     *
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces s
     * @updates tokens
     * @requires <pre>
     * [<"WHILE"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [a while string is a proper prefix of #tokens] then
     *  s = [WHILE Statement corresponding to while string at start of #tokens]  and
     *  #tokens = [while string at start of #tokens] * tokens
     * else
     *  [reports an appropriate error message to the console and terminates client]
     * </pre>
     */
    private static void parseWhile(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0 && tokens.front().equals("WHILE")
                : "" + "Violation of: <\"WHILE\"> is proper prefix of tokens";

        //Start off by dequeuing WHILE.
        tokens.dequeue();

        //Checking the parse condition.
        String cStr = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isCondition(cStr),
                "Error: " + cStr + " is not a valid BL condition");
        Condition condition = parseCondition(cStr);

        //Checking Do and then parsing the DO block.
        String doStr = tokens.dequeue();
        Reporter.assertElseFatalError(doStr.equals("DO"), "DO expected, found " + doStr);
        Statement doBlock = s.newInstance();
        doBlock.parseBlock(tokens);

        //Checking for END and WHILE.
        String end = tokens.dequeue();
        Reporter.assertElseFatalError(end.equals("END"),
                "Error: END expected, found " + end);
        end = tokens.dequeue();
        Reporter.assertElseFatalError(end.equals("WHILE"),
                "Error: WHILE expected, found " + end);

        //Assembling the WHILE statement.
        s.assembleWhile(condition, doBlock);

    }

    /**
     * Parses a CALL statement from {@code tokens} into {@code s}.
     *
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces s
     * @updates tokens
     * @requires [identifier string is a proper prefix of tokens]
     * @ensures <pre>
     * s =
     *   [CALL Statement corresponding to identifier string at start of #tokens]  and
     *  #tokens = [identifier string at start of #tokens] * tokens
     * </pre>
     */
    private static void parseCall(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0 && Tokenizer.isIdentifier(tokens.front())
                : "" + "Violation of: identifier string is proper prefix of tokens";

        //Parsing the CALL statement.
        String call = tokens.dequeue();
        s.assembleCall(call);

    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Statement1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0
                : "" + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        //Clearing the statement to get it ready for parsing.
        this.clear();
        //Finding the statement type from the first token and then calling the
        //appropriate parsing method.
        if (tokens.front().equals("IF")) {
            parseIf(tokens, this);
        } else if (tokens.front().equals("WHILE")) {
            parseWhile(tokens, this);
        } else if (Tokenizer.isIdentifier(tokens.front())) {
            parseCall(tokens, this);
        }

    }

    @Override
    public void parseBlock(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0
                : "" + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        //Clearing the current statement, parsing, and then adding an each IF,
        //WHILE, or CALL statement in the block
        this.clear();
        while (tokens.front().equals("IF") || tokens.front().equals("WHILE")
                || Tokenizer.isIdentifier(tokens.front())) {
            //Creating a new statement for each parsed statement in the block.
            Statement child = this.newInstance();
            child.parse(tokens);
            if (!child.kind().equals(Kind.BLOCK)) {
                //At the end adding the parsed statement to this block if it is
                //not a block itself.
                this.addToBlock(this.lengthOfBlock(), child);
            }
        }

    }

    /*
     * Main test method -------------------------------------------------------
     */

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
         * Get input file name
         */
        out.print("Enter valid BL statement(s) file name: ");
        String fileName = in.nextLine();
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Statement s = new Statement1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        s.parse(tokens); // replace with parseBlock to test other method
        /*
         * Pretty print the statement(s)
         */
        out.println("*** Pretty print of parsed statement(s) ***");
        s.prettyPrint(out, 0);

        in.close();
        out.close();
    }

}
