import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 *
 * @author Mohamed Jama and Emanuel Messele
 *
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorTest = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorTest(Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorRef(Comparator<String> order);

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    /*
     * test for no-arg constructor.
     */
    @Test
    public final void testConstructor() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        assertEquals(mExpected, m);
    }

    /*
     * tests for add method
     */
    @Test
    public final void testAddEmpty() {
        /*
         * Creating Sorting machines
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true, "green");
        //Testing the add method
        m.add("green");
        //Asserting that m matches the expectation.
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddMultiple() {
        /*
         * Creating Sorting machines
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "Emanuel",
                "Mohamed");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true, "Emanuel",
                "Mohamed", "Jama");
        //Testing the add method
        m.add("Jama");
        //Asserting that m matches the expectation.
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddEmptyQuotes() {
        /*
         * Creating Sorting machines
         */
        String added = "";
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true, added);
        //Testing the add method
        m.add(added);
        //Asserting that m matches the expectation.
        assertEquals(mExpected, m);
    }

    @Test
    public final void testEmptyAddNotEmpty() {
        /*
         * Creating Sorting machines
         */
        String added = "Emanuel";
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true, added);
        //Testing the add method
        m.add(added);
        //Asserting that m matches the expectation.;
        assertEquals(mExpected, m);
    }

    @Test
    public final void testNonEmptyAddNoTEmptySize() {
        /*
         * Creating Sorting machines
         */
        String added = "";
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "Fall");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true, "Fall",
                added);
        //Testing the add method;
        m.add(added);
        //Asserting that m matches the expectation.
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddDuplicates() {
        /*
         * Creating Sorting machines
         */
        String added = "Goku";
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "Goku", "Gohan");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true, "Goku",
                "Gohan", added);
        //Testing the add method;
        m.add(added);
        //Asserting that m matches the expectation.
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddTonMultiple() {
        //Creating Sorting machines
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "Emanuel",
                "Mohamed", "Software", "Stats", "Dog", "Cat", "Elephant", "Ravens", "Tom",
                "Elevator", "Tortilla", "Birds");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true, "Emanuel",
                "Mohamed", "Software", "Stats", "Dog", "Cat", "Elephant", "Ravens", "Tom",
                "Elevator", "Tortilla", "Birds", "Jama");
        //Testing the add method;
        m.add("Jama");

        //Asserting that m matches the expectation.
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddTonMultipleDuplicate() {
        /*
         * Creating Sorting machines
         */
        String added = "Cat";
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "Emanuel",
                "Mohamed", "Software", "Stats", "Dog", "Cat", "Elephant", "Ravens", "Tom",
                "Elevator", "Tortilla", "Birds");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true, "Emanuel",
                "Mohamed", "Software", "Stats", "Dog", "Cat", "Elephant", "Ravens", "Tom",
                "Elevator", "Tortilla", "Birds", added);
        //Testing the add method;
        m.add(added);
        //Asserting that m matches the expectation.
        assertEquals(mExpected, m);
    }

    /*
     * tests for changeToExtractionMode.
     */
    @Test
    public final void testChangeToExtractionModeEmpty() {
        /*
         * Creating Sorting machines
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        //Testing the changeToExtractionMode method;
        m.changeToExtractionMode();

        /*
         * Changing extraction mode.
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testChangeToExtractionModeNotEmoty() {
        /*
         * Creating Sorting machines
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "Emanuel",
                "Messele");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false, "Emanuel",
                "Messele");
        /*
         * Changing extraction mode.
         */
        m.changeToExtractionMode();
        /*
         * Asserting that m matches the expectation.
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testChangeToExtractionModeAlphabet() {
        /*
         * Creating Sorting machines
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "A", "B", "C",
                "D", "E", "F", "G", "H", "I", "J");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false, "A", "B",
                "C", "D", "E", "F", "G", "H", "I", "J");
        /*
         * Changing extraction mode.
         */
        m.changeToExtractionMode();
        /*
         * Asserting that m matches the expectation.
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testChangeToExtractionModeAlphabetFull() {
        /*
         * Creating Sorting machines
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "A", "B", "C",
                "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
                "S", "T", "U", "V", "W", "X", "Y", "Z");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false, "A", "B",
                "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
                "R", "S", "T", "U", "V", "W", "X", "Y", "Z");
        /*
         * Changing extraction mode.
         */
        m.changeToExtractionMode();
        /*
         * Asserting that m matches the expectation.
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testChangeToExtractionModeAlphabetDuplicate() {
        /*
         * Creating Sorting machines
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "A", "A", "A",
                "A", "A", "A", "A", "A", "A", "A");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false, "A", "A",
                "A", "A", "A", "A", "A", "A", "A", "A");

        /*
         * Changing extraction mode.
         */
        m.changeToExtractionMode();
        /*
         * Asserting that m matches the expectation.
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testChangeToExtractionModeAlphabetDuplicatesBig() {
        /*
         * Creating Sorting machines
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "A", "A", "A",
                "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A",
                "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A",
                "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A",
                "A", "A");
        SortingMachine<String> mExpected = this.createFromArgsTest(ORDER, false, "A", "A",
                "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A",
                "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A",
                "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A", "A",
                "A", "A", "A");
        /*
         * Changing extraction mode.
         */
        m.changeToExtractionMode();
        /*
         * Asserting that m matches the expectation.
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testChangeToExtractionModeWithEntries() {
        /*
         * Creating Sorting machines
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "Zoro", "Lebron",
                "Gohan");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false, "Zoro",
                "Lebron", "Gohan");
        /*
         * Changing extraction mode.
         */
        m.changeToExtractionMode();
        /*
         * Asserting that m matches the expectation.
         */
        assertEquals(mExpected, m);
    }

    /*
     * tests for removeFirst.
     */
    @Test
    public final void testRemoveFirstOneEntry() {
        /*
         * Creating Sorting machines
         */
        SortingMachine<String> q = this.createFromArgsTest(ORDER, false, "Foundations");
        SortingMachine<String> qExpected = this.createFromArgsRef(ORDER, false);

        //Testing the removeFirst method.
        String removedWord = q.removeFirst();
        //Checking the expectation
        assertEquals(removedWord, "Foundations");
        assertEquals(q, qExpected);
    }

    @Test
    public final void testRemoveFirstMultipleEntries() {
        /*
         * Creating Sorting machines
         */
        SortingMachine<String> q = this.createFromArgsTest(ORDER, false, "Emanuel",
                "Foundations", "Mohamed", "Software", "Stats");
        SortingMachine<String> qExpected = this.createFromArgsRef(ORDER, false,
                "Foundations", "Mohamed", "Software", "Stats");

        //Testing the removeFirst method.
        String removedWord = q.removeFirst();
        //Checking the expectation
        assertEquals(removedWord, "Emanuel");
        assertEquals(q, qExpected);
    }

    @Test
    public final void testRemoveFirstMultipleEntriesChallenging() {
        /*
         * Creating Sorting machines
         */
        SortingMachine<String> q = this.createFromArgsTest(ORDER, false, "Emanuel",
                "Foundations", "Mohamed", "Software", "Stats");
        SortingMachine<String> qExpected = this.createFromArgsRef(ORDER, false,
                "Foundations", "Mohamed", "Software", "Stats");

        //Testing the removeFirst method.
        String removedWord = q.removeFirst();

        //Checking the expectation
        assertEquals(removedWord, "Emanuel");
        assertEquals(q, qExpected);
    }

    @Test
    public final void testRemoveFirstEmpty() {
        /*
         * Creating Sorting machines
         */
        String eRemoved = "";
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, eRemoved);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        //Testing the removeFirst method.
        String removed = m.removeFirst();
        assertEquals(eRemoved, removed);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveFirstNotEmptyString() {
        /*
         * Creating Sorting machines
         */
        String eRemoved = "Fall";
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, eRemoved);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        //Testing the removeFirst method.
        String removed = m.removeFirst();
        //Checking the expectation
        assertEquals(eRemoved, removed);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveFirstNonEmptyStringToSize1() {
        /*
         * Creating Sorting machines
         */
        String eRemoved = "Mohamed";
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "Mohamed",
                eRemoved);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "Mohamed");
        //Testing the removeFirst method.
        String removed = m.removeFirst();
        //Checking the expectation
        assertEquals(eRemoved, removed);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveFirstAlphabet() {
        /*
         * Creating Sorting machines
         */
        String eRemoved = "A";
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "B", "C",
                eRemoved);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false, "B", "C");
        //Testing the removeFirst method.
        String removed = m.removeFirst();
        //Checking the expectation
        assertEquals(eRemoved, removed);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveFirstChallengingAlphabet() {

        String eRemoved = "A";

        /*
         * Creating Sorting machines
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "A", "B", "C",
                "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
                "S", "T", "U", "V", "W", "X", "Y", "Z", "AA");

        m.changeToExtractionMode();

        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false, "B", "C",
                "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
                "S", "T", "U", "V", "W", "X", "Y", "Z", "AA");
        //Testing the removeFirst method.
        String removed = m.removeFirst();
        assertEquals(eRemoved, removed);
        //Checking the expectation
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveFirstChallengingAlphabetWithDuplicates() {

        String eRemoved = "A";
        /*
         * Creating Sorting machines
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "A", "B", "C",
                "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
                "S", "T", "U", "V", "W", "X", "Y", "Z", "A", "B", "C", "AA");

        m.changeToExtractionMode();

        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false, "A", "B",
                "B", "C", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
                "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "AA");
        //Testing the removeFirst method.
        String removed = m.removeFirst();
        assertEquals(eRemoved, removed);
        //Checking the expectation
        assertEquals(mExpected, m);
    }

    /*
     * test for isInInsertionMode Method.
     */
    @Test
    public final void isInInsertionModeTrue() {
        boolean check = true;
        /*
         * Creating Sorting machine
         */
        SortingMachine<String> q = this.createFromArgsTest(ORDER, true);
        //Testing the isInInsertion method.
        boolean isInInsertion = q.isInInsertionMode();
        //Checking the expectation
        assertEquals(check, isInInsertion);
    }

    /*
     * test for isInInsertionMode.
     */

    @Test
    public final void testIsInsertionModeEmptyT() {
        /*
         * Creating Sorting machines
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        //Testing the isInInsertion method.
        assertEquals(true, m.isInInsertionMode());
        //Checking the expectation
        assertEquals(mExpected, m);
    }

    @Test
    public final void isInInsertionModeFalse() {
        /*
         * Creating Sorting machine
         */
        boolean check = false;
        SortingMachine<String> q = this.createFromArgsTest(ORDER, false);

        //Testing isInInsertionMode
        boolean isInInsertion = q.isInInsertionMode();

        //Checking the expectation
        assertEquals(check, isInInsertion);
    }

    @Test
    public final void testIsInsertionModeEmptyFalse() {
        /*
         * Creating Sorting machines
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        //Testing isInInsertionMode
        assertEquals(false, m.isInInsertionMode());
        //Checking the expectation
        assertEquals(mExpected, m);
    }

    @Test
    public final void testIsInsertionModeTrue() {
        /*
         * Creating Sorting machines
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "Mohamed");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true, "Mohamed");
        //Testing isInInsertionMode
        assertEquals(true, m.isInInsertionMode());
        //Checking the expectation
        assertEquals(mExpected, m);
    }

    @Test
    public final void testIsInsertionModeFalse() {
        /*
         * Creating Sorting machines
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "Emanuel");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "Emanuel");
        //Testing isInInsertionMode
        assertEquals(false, m.isInInsertionMode());
        //Checking the expectation
        assertEquals(mExpected, m);
    }

    @Test
    public final void testIsInertionModeSmallTrue() {
        /*
         * Creating Sorting machines
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "Mohamed", "Jama",
                "Emanuel", "Messele");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true, "Mohamed",
                "Jama", "Emanuel", "Messele");
        //Testing isInInsertionMode
        assertEquals(true, m.isInInsertionMode());
        //Checking the expectation
        assertEquals(mExpected, m);
    }

    @Test
    public final void testIsInsertionModeSmallFalse() {
        /*
         * Creating Sorting machines
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "Mohamed",
                "Jama", "Emanuel", "Messele");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false, "Mohamed",
                "Jama", "Emanuel", "Messele");
        //Testing isInInsertionMode
        assertEquals(false, m.isInInsertionMode());
        //Checking the expectation
        assertEquals(mExpected, m);
    }

    @Test
    public final void testIsInsertionDuplicateTrue() {
        /*
         * Creating Sorting machines
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "Mohamed", "Jama",
                "Emanuel", "Messele", "Emanuel");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true, "Mohamed",
                "Jama", "Emanuel", "Messele", "Emanuel");
        //Testing isInInsertionMode
        assertEquals(true, m.isInInsertionMode());
        //Checking the expectation
        assertEquals(mExpected, m);
    }

    @Test
    public final void testIsInsertionDuplicateFalse() {
        /*
         * Creating Sorting machines
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "Mohamed",
                "Jama", "Emanuel", "Messele", "Emanuel");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false, "Mohamed",
                "Jama", "Emanuel", "Messele", "Emanuel");
        //Testing isInInsertionMode
        assertEquals(false, m.isInInsertionMode());
        //Checking the expectation
        assertEquals(mExpected, m);
    }

    /*
     * test cases for order.
     */
    @Test
    public final void testOrderEmptyTrue() {
        /*
         * Creating Sorting machines
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        //Checking the expectation
        assertEquals(ORDER, m.order());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testOrderEmptyMohamed() {
        /*
         * Creating Sorting machines
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "Mohamed");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true, "Mohamed");
        //Checking the expectation
        assertEquals(ORDER, m.order());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testOrderLargeTrue() {
        /*
         * Creating Sorting machines
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "A", "B", "C",
                "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
                "S", "T", "U", "V", "W", "X", "Y", "Z");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true, "A", "B",
                "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
                "R", "S", "T", "U", "V", "W", "X", "Y", "Z");
        //Checking the expectation
        assertEquals(ORDER, m.order());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testOrderEmpty() {
        /*
         * Creating Sorting machines
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        //Checking the expectation
        assertEquals(ORDER, m.order());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testOrderLargeFalse() {
        /*
         * Creating Sorting machines
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "A", "B", "C",
                "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
                "S", "T", "U", "V", "W", "X", "Y", "Z");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false, "A", "B",
                "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
                "R", "S", "T", "U", "V", "W", "X", "Y", "Z");
        //Checking the expectation
        assertEquals(ORDER, m.order());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testOrderDupelicate() {
        /*
         * Creating Sorting machines
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "Mohamed",
                "Jama", "Emanuel", "Messele", "Emanuel");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false, "Mohamed",
                "Jama", "Emanuel", "Messele", "Emanuel");
        //Checking the expectation
        assertEquals(ORDER, m.order());
        assertEquals(mExpected, m);
    }

    /*
     * test for size.
     */
    @Test
    public final void testSizeZeroInsertion() {
        /*
         * Creating Sorting machines
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        //Checking the expectation
        assertEquals(0, m.size());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testSizeZeroExtraction() {
        /*
         * Creating Sorting machines
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        //Checking the expectation
        assertEquals(0, m.size());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testSize5Insertion() {
        /*
         * Creating Sorting machines
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "A", "B", "C",
                "D", "E");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true, "A", "B",
                "C", "D", "E");
        //Checking the expectation
        assertEquals(5, m.size());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testSize5Extraction() {
        /*
         * Creating Sorting machines
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "A", "B", "C",
                "D", "E");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false, "A", "B",
                "C", "D", "E");
        //Checking the expectation
        assertEquals(5, m.size());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testSizeAlphabetInsertion() {
        /*
         * Creating Sorting machines
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "A", "B", "C",
                "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
                "S", "T", "U", "V", "W", "X", "Y", "Z");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true, "A", "B",
                "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
                "R", "S", "T", "U", "V", "W", "X", "Y", "Z");
        //Checking the expectation
        assertEquals(26, m.size());
        assertEquals(mExpected, m);
    }

    @Test
    public final void testSizeFiftyExtraction() {
        /*
         * Creating Sorting machines
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "A", "B", "C",
                "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
                "S", "T", "U", "V", "W", "X", "Y", "Z");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false, "A", "B",
                "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
                "R", "S", "T", "U", "V", "W", "X", "Y", "Z");
        //Checking the expectation
        assertEquals(26, m.size());
        assertEquals(mExpected, m);
    }
}
