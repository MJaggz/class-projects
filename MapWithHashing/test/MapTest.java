import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;
import components.map.Map.Pair;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Emanuel Messele & Mohamed Jama
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i])
                    : "" + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i])
                    : "" + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /*
     * No argument constructor test.
     */

    @Test
    public void noArgConstructor() {

        Map<String, String> q = this.constructorTest();
        Map<String, String> qExpected = this.constructorRef();

        assertEquals(q, qExpected);

    }

    /*
     * add test cases.
     */

    @Test
    public void addMethodRoutine() {
        /*
         * Creating a Map with pair ("blue", "red") and the expected Map with
         * the pair ("green", "yellow") added. This would be a normal routine
         * test case.
         */
        Map<String, String> q = this.createFromArgsTest("blue", "red");
        Map<String, String> qExpected = this.createFromArgsRef("green", "yellow", "blue",
                "red");

        q.add("green", "yellow");

        assertEquals(q, qExpected);
    }

    @Test
    public final void testAddNonEmptyStringPairOhioState() {
        /*
         * Creating an empty Map and an expected Map with the pair ("Ohio",
         * "State"). This would be a routine test case.
         */
        Map<String, String> m = this.createFromArgsTest();
        Map<String, String> mExpected = this.createFromArgsRef("Ohio", "State");
        /*
         * Adding key-value pair to the map.
         */
        m.add("Ohio", "State");
        /*
         * Checking that m matches the expectation
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddEmptyStringPairToNonEmptyMapOhioStatee() {
        /*
         * Creating a Map with the pair ("Ohio", "State") and an expected Map
         * with an empty pair ("", ""). This would be a boundary case since we
         * are adding an empty pair.
         */
        Map<String, String> m = this.createFromArgsTest("Ohio", "State");
        Map<String, String> mExpected = this.createFromArgsRef("Ohio", "State", "", "");
        /*
         * Adding key-value pair to the map.
         */
        m.add("", "");
        /*
         * Checking that m matches the expectation
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddEmptyStringPairLetters() {
        /*
         * Creating a Map with several pairs with letters and an expected map
         * that will have the empty pair ("", "") added. This would be a
         * challenging test case since there are multiple pairs.
         */
        Map<String, String> m = this.createFromArgsTest("A", "B", "C", "D", "E", "F", "G",
                "H");
        Map<String, String> mExpected = this.createFromArgsRef("A", "B", "C", "D", "E",
                "F", "G", "H", "", "");
        /*
         * Adding key-value pair to the map.
         */
        m.add("", "");
        /*
         * Checking that m matches the expectation
         */
        assertEquals(mExpected, m);
    }

    /*
     * remove test cases.
     */

    @Test
    public void removeMethod() {
        /*
         * Creating a Map with pairs and an expected Map after removing the pair
         * with the key "blue". This would be a routine test case.
         */
        Map<String, String> q = this.createFromArgsTest("blue", "red", "green", "yellow");
        Map<String, String> qExpected = this.createFromArgsRef("green", "yellow");

        q.remove("blue");

        assertEquals(q, qExpected);
    }

    @Test
    public final void testRemoveToEmpty() {
        /*
         * /* Creating a Map with one pair ("Ohio", "State") and an expected
         * empty Map. This would be a boundary case since this test case ends up
         * leaving the map empty.
         */
        Map<String, String> m = this.createFromArgsTest("Ohio", "State");
        Map<String, String> n = this.createFromArgsRef();
        //the key and value that should be stored in the returned pair
        String key = "Ohio";
        String val = "State";
        //calls tested method
        Pair<String, String> p = m.remove(key);
        //asserts that the map and pair are correct
        assertEquals(m, n);
        assertEquals(key, p.key());
        assertEquals(val, p.value());
    }

    @Test
    public final void testRemoveEmptyPairr() {
        /*
         * Creating a Map with an empty pair and an empty Map after. This would
         * also be a boundary test case.
         */
        Map<String, String> m = this.createFromArgsTest("", "");
        Map<String, String> n = this.createFromArgsRef();
        //the key and value that should be stored in the returned pair
        String key = "";
        String val = "";
        //calls tested method
        Pair<String, String> p = m.remove(key);
        //asserts that the map and pair are correct
        assertEquals(m, n);
        assertEquals(key, p.key());
        assertEquals(val, p.value());
    }

    @Test
    public final void testRemoveBigMap() {
        /*
         * Creating a Map with several pairs and an expected Map with the pair
         * (E, F) removed. This would be a challenging test case since this is a
         * huge map.
         */
        Map<String, String> m = this.createFromArgsTest("A", "B", "C", "D", "E", "F", "G",
                "H", "I", "J");
        Map<String, String> n = this.createFromArgsRef("A", "B", "C", "D", "G", "H", "I",
                "J");

        // The key and value that should be stored in the returned pair after removal.
        String key = "E";
        String val = "F";

        /*
         * Calling the remove method to remove the pair.
         */
        Pair<String, String> p = m.remove(key);

        /*
         * Asserting that the map and the removed pair are correct.
         */
        assertEquals(m, n);
        assertEquals(key, p.key());
        assertEquals(val, p.value());
    }

    /*
     * removeAny test cases.
     */

    @Test
    public void removeAnyMethod() {
        /*
         * Creating a Map with initial pairs and an expected Map with the same
         * values. This would be a routine test case.
         */
        Map<String, String> q = this.createFromArgsTest("blue", "red", "green", "yellow");
        Map<String, String> qExpected = this.createFromArgsRef("blue", "red", "green",
                "yellow");

        Map.Pair<String, String> e = q.removeAny();

        boolean check = true;
        boolean answer = false;

        if (qExpected.hasKey(e.key())) {
            answer = true;
        }

        assertEquals(check, answer);

    }

    @Test
    public final void testRemoveAnyLetters() {
        /*
         * Creating a Map with several pairs and an expected Map with the pair
         * (E, F) is removed. This would be a challenging test case since this
         * is a huge map.
         */
        Map<String, String> m = this.createFromArgsTest("A", "B", "C", "D", "E", "F", "G",
                "H", "I", "J");
        Map<String, String> n = this.createFromArgsRef("A", "B", "C", "D", "G", "H", "I",
                "J");

        // The key and value that should be stored in the returned pair after removal.
        String key = "E";
        String val = "F";

        /*
         * Calling the remove method to remove the pair.
         */
        Pair<String, String> p = m.remove(key);

        /*
         * Asserting that the map and the removed pair are correct.
         */
        assertEquals(m, n);
        assertEquals(key, p.key());
        assertEquals(val, p.value());
    }

    @Test
    public final void testRemoveAnyBoundary() {
        /*
         * Creating a Map with an empty pair and an empty Map after. This would
         * also be a boundary test case.
         */
        Map<String, String> m = this.createFromArgsTest("", "");
        Map<String, String> n = this.createFromArgsRef();

        //the key and value that should be stored in the returned pair
        String key = "";
        String val = "";
        //calls tested method
        Pair<String, String> p = m.removeAny();

        //asserts that the map and pair are correct
        assertEquals(0, m.size());
        assertEquals(m, n);
        assertEquals(key, p.key());
        assertEquals(val, p.value());
    }

    /*
     * value test cases.
     */

    @Test
    public void valueMethod() {
        /*
         * Creating a Map with pairs and finding the value for the key "blue".
         * This would be a routine test case.
         */
        Map<String, String> q = this.createFromArgsTest("blue", "red", "green", "yellow");
        String value = q.value("blue");
        String expectedValue = "red";

        assertEquals(value, expectedValue);
    }

    @Test
    public final void testValueWithMapSizeOneOState() {
        /*
         * Creating a Map with one pair ("Ohio",
         * "State"") and finding the value for the key "Ohio". This would be a
         * routine test case.
         */
        Map<String, String> m = this.createFromArgsTest("Ohio", "State");
        Map<String, String> mExpected = this.createFromArgsRef("Ohio", "State");
        /*
         * Using value method
         */
        String value = m.value("Ohio");
        /*
         * Checking that m and value matches the expectation
         */
        String valueExp = "State";
        assertEquals(mExpected, m);
        assertEquals(valueExp, value);

    }

    @Test
    public final void testValueWithMapSize2Names() {
        /*
         * Creating a Map with two pairs and finding the value for the key
         * "Emanuel". This would be a routine test case.
         */
        Map<String, String> m = this.createFromArgsTest("Mohamed", "Jama", "Emanuel",
                "Messele");
        Map<String, String> mExpected = this.createFromArgsRef("Mohamed", "Jama",
                "Emanuel", "Messele");
        /*
         * Using value method
         */
        String value = m.value("Emanuel");
        /*
         * Checking that m and value matches the expectation
         */
        String valueExp = "Messele";
        assertEquals(mExpected, m);
        assertEquals(valueExp, value);

    }

    @Test
    public final void testValueWithMapBoundary() {
        /*
         * This would be a boundary case since the map is empty.
         */
        Map<String, String> m = this.createFromArgsTest("", "");
        Map<String, String> mExpected = this.createFromArgsRef("", "");
        /*
         * Using value method
         */
        String value = m.value("");
        /*
         * Checking that m and value matches the expectation
         */
        String valueExp = "";
        assertEquals(mExpected, m);
        assertEquals(valueExp, value);

    }

    @Test
    public final void testValueWithMapSizeColors() {
        /*
         * Creating a Map with pairs where the key and value are the same color.
         * This would be a challenging case since there are a lot of pairs in
         * the map
         */
        Map<String, String> m = this.createFromArgsTest("Red", "Red", "Blue", "Blue",
                "Green", "Green", "Yellow", "Yellow");
        Map<String, String> mExpected = this.createFromArgsRef("Red", "Red", "Blue",
                "Blue", "Green", "Green", "Yellow", "Yellow");

        /*
         * Using the value method to retrieve the value associated with the key.
         */
        String value = m.value("Red");

        /*
         * Checking that the Map m matches the expected Map mExpected, and that
         * the value retrieved for the key "Red" is as expected.
         */
        String valueExp = "Red";
        assertEquals(mExpected, m);
        assertEquals(valueExp, value);
    }

    /*
     * hasKey test cases.
     */

    @Test
    public void hasKey() {
        /*
         * Creating a Map and checking if it contains the key "blue". This would
         * be a routine test case.
         */
        Map<String, String> q = this.createFromArgsTest("blue", "red", "green", "yellow");

        boolean check = true;
        boolean answer = false;

        if (q.hasKey("blue")) {
            answer = true;
        }

        assertEquals(check, answer);

    }

    @Test
    public final void testHasKeyTrueEmpty() {
        /*
         * Creating a Map with an empty pair and checking if it contains the
         * empty key "". This would be a boundary case since the map is empty.
         */
        Map<String, String> m = this.createFromArgsTest("", "");
        Map<String, String> mExpected = this.createFromArgsRef("", "");
        /*
         * Using hasKey method
         */
        boolean hasKey = m.hasKey("");
        /*
         * Checking that m and hasKey matches the expectation
         */
        assertEquals(mExpected, m);
        assertEquals(true, hasKey);

    }

    @Test
    public final void testHasKeyFalseSft2() {
        /*
         * Creating an empty Map and checking if it contains the key "CSE2231".
         * This would be a routine/ challenging test case since the test case
         * should be false.
         */
        Map<String, String> m = this.createFromArgsTest();
        Map<String, String> mExpected = this.createFromArgsRef();
        /*
         * Using hasKey method
         */
        boolean hasKey = m.hasKey("CSE2231");
        /*
         * Checking that m and hasKey matches the expectation
         */
        assertEquals(mExpected, m);
        assertEquals(false, hasKey);

    }

    @Test
    public final void testHasKeyColorsChall() {
        /*
         * Creating a Map with pairs where the key is a color and the value is
         * its description. This would be a challenging test case since there is
         * a lot of pairs.
         */
        Map<String, String> m = this.createFromArgsTest("Red", "Crimson", "Blue", "Azure",
                "Green", "Emerald", "Yellow", "Golden");
        Map<String, String> mExpected = this.createFromArgsRef("Red", "Crimson", "Blue",
                "Azure", "Green", "Emerald", "Yellow", "Golden");

        /*
         * Using the hasKey method to check if the key "Green" exists in the
         * map.
         */
        boolean hasKey = m.hasKey("Green");

        /*
         * Checking that the Map m matches the expected Map mExpected, and that
         * the key "Green" exists in the map.
         */
        assertEquals(mExpected, m);
        assertEquals(true, hasKey);
    }

    /*
     * size test cases.
     */

    @Test
    public void size() {
        /*
         * Creating a Map with two pairs and verifying its size. This would be a
         * routine test case.
         */
        Map<String, String> q = this.createFromArgsTest("blue", "red", "green", "yellow");
        int check = 2;

        // Verifying if the size of the Map matches the expected size.
        assertEquals(check, q.size());
    }

    @Test
    public final void testSizeOffOne() {
        /*
         * Creating a Map with one pair and verifying its size. This would be a
         * routine test case
         */
        Map<String, String> m = this.createFromArgsTest("PlzGiveUsA10", "Plz");
        Map<String, String> mExpected = this.createFromArgsRef("PlzGiveUsA10", "Plz");
        /*
         * Using size method
         */
        int size = m.size();
        final int sizeExpected = 1;
        /*
         * Checking that m and size matches the expectation
         */
        assertEquals(mExpected, m);
        assertEquals(sizeExpected, size);
    }

    @Test
    public final void testSizeBoundary() {
        /*
         * This would be a boundary test case since the size of the map is 0.
         */
        Map<String, String> m = this.createFromArgsTest();
        Map<String, String> mExpected = this.createFromArgsRef();
        /*
         * Using size method
         */
        int size = m.size();
        final int sizeExpected = 0;
        /*
         * Checking that m and size matches the expectation
         */
        assertEquals(mExpected, m);
        assertEquals(sizeExpected, size);
    }

    @Test
    public final void testSizeWithManyPairs() {
        /*
         * Creating a Map with a large number of letter pairs. This would be a
         * challenging test case since there are many pairs in the map.
         */
        Map<String, String> m = this.createFromArgsTest("A", "B", "C", "D", "E", "F", "G",
                "H", "I", "J", "K", "L", "M", "N", "O", "P");
        Map<String, String> mExpected = this.createFromArgsRef("A", "B", "C", "D", "E",
                "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P");

        /*
         * Using the size method to get the number of pairs in the map.
         */
        int size = m.size();
        final int sizeExpected = 8;

        /*
         * Checking that the map m matches the expected map mExpected, and that
         * the size of the map is as expected.
         */
        assertEquals(mExpected, m);
        assertEquals(sizeExpected, size);
    }

}
