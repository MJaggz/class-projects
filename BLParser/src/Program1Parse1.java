import components.map.Map;
import components.program.Program;
import components.program.Program1;
import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary method {@code parse} for {@code Program}.
 *
 * @author Mohamed Jama and Emanuel Messele
 *
 */
public final class Program1Parse1 extends Program1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Parses a single BL instruction from {@code tokens} returning the
     * instruction name as the value of the function and the body of the
     * instruction in {@code body}.
     *
     * @param tokens
     *            the input tokens
     * @param body
     *            the instruction body
     * @return the instruction name
     * @replaces body
     * @updates tokens
     * @requires <pre>
     * [<"INSTRUCTION"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [an instruction string is a proper prefix of #tokens]  and
     *    [the beginning name of this instruction equals its ending name]  and
     *    [the name of this instruction does not equal the name of a primitive
     *     instruction in the BL language] then
     *  parseInstruction = [name of instruction at start of #tokens]  and
     *  body = [Statement corresponding to the block string that is the body of
     *          the instruction string at start of #tokens]  and
     *  #tokens = [instruction string at start of #tokens] * tokens
     * else
     *  [report an appropriate error message to the console and terminate client]
     * </pre>
     */
    private static String parseInstruction(Queue<String> tokens, Statement body) {
        assert tokens != null : "Violation of: tokens is not null";
        assert body != null : "Violation of: body is not null";
        assert tokens.length() > 0 && tokens.front().equals("INSTRUCTION")
                : "" + "Violation of: <\"INSTRUCTION\"> is proper prefix of tokens";

        // Remove the "INSTRUCTION" token
        tokens.dequeue();

        // checking for and taking out the instruction name/identifier.
        String instructionName = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(instructionName),
                "Error: " + instructionName + " is not a valid BL identiifer");

        // checking for "IS"
        String is = tokens.dequeue();
        Reporter.assertElseFatalError(is.equals("IS"), "IS expected, found " + is);

        // parse the body of the instruction
        body.parseBlock(tokens);

        // check for end
        String endStr = tokens.dequeue();
        Reporter.assertElseFatalError(endStr.equals("END"),
                "Error: END expected, found " + endStr);

        // making sure that program name after end matches the instruction name earlier
        String nameOfInstruction = tokens.dequeue();
        Reporter.assertElseFatalError(nameOfInstruction.equals(instructionName),
                "Error: " + instructionName + " expected, found " + nameOfInstruction);

        return instructionName;
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Program1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(SimpleReader in) {
        assert in != null : "Violation of: in is not null";
        assert in.isOpen() : "Violation of: in.is_open";
        Queue<String> tokens = Tokenizer.tokens(in);
        this.parse(tokens);
    }

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0
                : "" + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        // TODO - fill in body

        this.clear();

        //dequeue the program token
        String program = tokens.dequeue();
        Reporter.assertElseFatalError(program.equals("PROGRAM"),
                "PROGRAM expected, found " + program);

        // checking for and taking out the instruction name/identifier.
        String programName = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(programName),
                "Error: " + programName + " is not a valid BL identiifer");

        this.setName(programName);

        String is = tokens.dequeue();
        Reporter.assertElseFatalError(is.equals("IS"), "IS expected, found " + is);

        Map<String, Statement> n = this.newContext();

        // checking for what is first, instruction or begin
        while (tokens.front().equals("INSTRUCTION")) {
            // call the parse instruction method
            Statement s = this.newBody();
            String name = parseInstruction(tokens, s);
            n.add(name, s);

        }

        String beginStr = tokens.dequeue();
        Reporter.assertElseFatalError(beginStr.equals("BEGIN"),
                "Error: BEGIN expected, found " + beginStr);

        Statement bodyAfterBegin = this.newBody();
        bodyAfterBegin.parseBlock(tokens);

        String endStr = tokens.dequeue();
        Reporter.assertElseFatalError(endStr.equals("END"),
                "Error: END expected, found " + endStr);

        // make sure name of program is there and matches the program name in beginning
        String nameOfProgram = tokens.dequeue();
        Reporter.assertElseFatalError(nameOfProgram.equals(programName),
                "Error:" + programName + "expected, found " + nameOfProgram);

        this.swapContext(n);
        this.swapBody(bodyAfterBegin);

        Reporter.assertElseFatalError(tokens.front().equals(Tokenizer.END_OF_INPUT),
                "Error:" + Tokenizer.END_OF_INPUT + "expected, found " + tokens.front());

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
        out.print("Enter valid BL program file name: ");
        String fileName = in.nextLine();
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Program p = new Program1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        p.parse(tokens);
        /*
         * Pretty print the program
         */
        out.println("*** Pretty print of parsed program ***");
        p.prettyPrint(out);

        in.close();
        out.close();
    }

}
