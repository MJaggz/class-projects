import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumberSecondary;

/**
 * {@code NaturalNumber} represented as a {@code String} with implementations of
 * primary methods.
 *
 * @convention <pre>
 * [all characters of $this.rep are '0' through '9']  and
 * [$this.rep does not start with '0']
 * </pre>
 * @correspondence <pre>
 * this = [if $this.rep = "" then 0
 *         else the decimal number whose ordinary depiction is $this.rep]
 * </pre>
 *
 * @author Emanuel Messele and Mohamed Jama
 *
 */
public class NaturalNumber3 extends NaturalNumberSecondary {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Representation of {@code this}.
     */
    private String rep;

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        //Creating a new representation of NaturalNumber by assigning an empty
        //string to this.rep.//
        this.rep = "";

    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public NaturalNumber3() {
        //Creating a new NaturalNumber representation with the default value
        //of o.
        this.createNewRep();

    }

    /**
     * Constructor from {@code int}.
     *
     * @param i
     *            {@code int} to initialize from
     */
    public NaturalNumber3(int i) {
        assert i >= 0 : "Violation of: i >= 0";
        //Initializing a new NaturalNumber with the default value and then
        //setting this.rep to a string representation of the integer i.
        this.createNewRep();
        if (i > 0) {
            this.rep = Integer.toString(i);
        }

    }

    /**
     * Constructor from {@code String}.
     *
     * @param s
     *            {@code String} to initialize from
     */
    public NaturalNumber3(String s) {
        assert s != null : "Violation of: s is not null";
        assert s.matches("0|[1-9]\\d*")
                : "" + "Violation of: there exists n: NATURAL (s = TO_STRING(n))";
        //Initializing a new NaturalNumber with the default value and then
        //setting this.rep to string s if s is not a 0. If it is, this.rep is
        //still the same.
        this.createNewRep();
        if (!s.equals("0")) {
            this.rep = s;
        }

    }

    /**
     * Constructor from {@code NaturalNumber}.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     */
    public NaturalNumber3(NaturalNumber n) {
        assert n != null : "Violation of: n is not null";
        //Initializing a new NaturalNumber with the default value and then
        //setting this.rep to n.toString() if it is not zero. If it is, then
        //this.rep is still the same.
        this.createNewRep();
        if (!n.toString().equals("0")) {
            this.rep = n.toString();
        }

    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @Override
    public final NaturalNumber newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void clear() {
        this.createNewRep();
    }

    @Override
    public final void transferFrom(NaturalNumber source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof NaturalNumber3
                : "" + "Violation of: source is of dynamic type NaturalNumberExample";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case.
         */
        NaturalNumber3 localSource = (NaturalNumber3) source;
        this.rep = localSource.rep;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void multiplyBy10(int k) {
        assert 0 <= k : "Violation of: 0 <= k";
        assert k < RADIX : "Violation of: k < 10";
        //Append k to the string representation of the number.
        this.rep += Integer.toString(k);

    }

    @Override
    public final int divideBy10() {

        int remainder = 0;
        if (this.rep.length() > 0) {
            //Get the last character of the string.
            String remainderStr = this.rep.substring(this.rep.length() - 1);
            //Convert the remainder to an integer.
            remainder = Integer.parseInt(remainderStr);
            //Remove the last character from the string.
            this.rep = this.rep.substring(0, this.rep.length() - 1);
        }
        //Return the remainder.
        return remainder;
    }

    @Override
    public final boolean isZero() {
        //Initializing a boolean check as false.
        boolean check = false;
        //If this.rep is empty, set the boolean check as true.
        if (this.rep.equals("")) {
            check = true;
        }
        //Return the boolean check.
        return check;
    }

}
