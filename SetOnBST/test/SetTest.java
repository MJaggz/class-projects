import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Emanuel Messele and Mohamed Jama
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /*
     * No argument constructor test.
     */

    public final void testNoArgumentConstructor() {

        Set<String> q = this.createFromArgsTest();
        Set<String> qExpected = this.createFromArgsRef();

        assertEquals(q, qExpected);
    }

    /*
     * add test cases.
     */

    @Test
    public final void testAddEmptyStringToEmpSet() {
        //This would be a boundary test case since we are adding an empty string.
        //Creating the sets.

        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef("");
        //Adding elements to the set.

        String add = "";
        //calling the add method.

        s.add(add);
        //Making sure that s matches the expectation.

        assertEquals(sExpected, s);
    }

    @Test
    public final void testAddEmptyStringSmall() {
        //This would be a routine test case with a small string.
        //Creating the sets.

        Set<String> s = this.createFromArgsTest("Ohio", "State", "Orgeon", "Ducks",
                "OSU Will WIn");
        Set<String> sExpected = this.createFromArgsRef("Ohio", "State", "Orgeon", "Ducks",
                "OSU Will WIn", "");

        //The string that will be added to the set.
        String add = "";
        //calling the add method.

        s.add(add);

        //Making sure that s matches the expectation.
        assertEquals(sExpected, s);
    }

    @Test
    public final void testAddEmptyStringToBigSetRandom() {
        //This would be a challenging test case with a huge set string.
        //Creating the sets.

        Set<String> s = this.createFromArgsTest("Apple", "Bee", "Car", "Dog", "Emanuel",
                "Fall", "Go", "Home", "Igloo", "Janga", "Kangaroo", "Lemon", "Mohamed",
                "Nose", "Ohio", "Panthers", "Quarter", "Rest", "Stars", "Tub", "Ukulele",
                "Virgina", "Window", "Xylophone", "Year", "Zebra");
        Set<String> sExpected = this.createFromArgsRef("Apple", "Bee", "Car", "Dog",
                "Emanuel", "Fall", "Go", "Home", "Igloo", "Janga", "Kangaroo", "Lemon",
                "Mohamed", "Nose", "Ohio", "Panthers", "Quarter", "Rest", "Stars", "Tub",
                "Ukulele", "Virgina", "Window", "Xylophone", "Year", "Zebra", "");

        //The string that will be added to the set.
        String add = "";
        //calling the add method.

        s.add(add);
        //Making sure that s matches the expectation.
        assertEquals(sExpected, s);
    }

    @Test
    public final void testAAddNonEmptyStringToEmpSet() {
        //This would sort of a boundary test case since we are adding a nonempty
        //string to an empty set.
        //Creating the sets.

        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef("OSU!!!");

        //The string that will be added to the set.
        String add = "OSU!!!";

        //calling the add method.
        s.add(add);

        //Making sure that s matches the expectation.
        assertEquals(sExpected, s);
    }

    @Test
    public final void testAddNonEmptyStringToAnotherNonEmpSet() {
        //This would be a challenging test case with a huge set string and adding
        //a non empty string to an already large set.
        //Creating the sets.
        Set<String> s = this.createFromArgsTest("Apple", "Bee", "Car", "Dog", "Emanuel",
                "Fall", "Go", "Home", "Igloo", "Janga", "Kangaroo", "Lemon", "Mohamed",
                "Nose", "Ohio", "Panthers", "Quarter", "Rest", "Stars", "Tub", "Ukulele",
                "Virgina", "Window", "Xylophone", "Year", "Zebra");
        Set<String> sExpected = this.createFromArgsRef("Apple", "Bee", "Car", "Dog",
                "Emanuel", "Fall", "Go", "Home", "Igloo", "Janga", "Kangaroo", "Lemon",
                "Mohamed", "Nose", "Ohio", "Panthers", "Quarter", "Rest", "Stars", "Tub",
                "Ukulele", "Virgina", "Window", "Xylophone", "Year", "Zebra",
                "123456789");

        //The string that will be added to the set.
        String add = "123456789";
        //calling the add method
        s.add(add);
        //Making sure that s matches the expectation.
        assertEquals(sExpected, s);
    }

    /*
     * remove test cases.
     */

    @Test
    public final void testRemoveEmptyStringSet() {
        //This would be a boundary case since both strings are empty.
        //Creating the sets.
        Set<String> s = this.createFromArgsTest("");
        Set<String> sExpected = this.createFromArgsRef();

        //Removing whatever is inside S.
        String removed = s.remove("");

        //Making sure that s matches the expectation.
        assertEquals("", removed);
        assertEquals(sExpected, s);
    }

    @Test
    public final void testRemoveNonEmptyStringSet() {
        //This would sort of be a routine/boundary case since we are removing a
        //string.
        ///Creating the sets.
        Set<String> s = this.createFromArgsTest("Fall");
        Set<String> sExpected = this.createFromArgsRef();

        //Removing whatever is inside S.
        String removed = s.remove("Fall");

        //Making sure that s matches the expectation.
        assertEquals("Fall", removed);
        assertEquals(sExpected, s);
    }

    @Test
    public final void testRemoveEmptyStringNonEmptySet() {
        //This would sort of be a routine/boundary case since we are removing a
        //string while still having a string inside of the set.
        //Creating the sets.
        Set<String> s = this.createFromArgsTest("", "Mohamed");
        Set<String> sExpected = this.createFromArgsRef("Mohamed");

        //Removing whatever is inside S.
        String removed = s.remove("");

        //Making sure that s matches the expectation.
        assertEquals("", removed);
        assertEquals(sExpected, s);
    }

    @Test
    public final void testRemoveNonEmptyStringRandomBig() {
        //This would be a challenging test case since we are removing a string
        //from a huge set.
        //Creating the sets.
        Set<String> s = this.createFromArgsTest("Apple", "Bee", "Car", "Dog", "Emanuel",
                "Fall", "Go", "Home", "Igloo", "Janga", "Kangaroo", "Lemon", "Mohamed",
                "Nose", "Ohio", "Panthers", "Quarter", "Rest", "Stars", "Tub", "Ukulele",
                "Virgina", "Window", "Xylophone", "Year", "Zebra");
        Set<String> sExpected = this.createFromArgsRef("Apple", "Bee", "Car", "Dog",
                "Emanuel", "Fall", "Go", "Home", "Igloo", "Janga", "Kangaroo", "Lemon",
                "Mohamed", "Nose", "Panthers", "Quarter", "Rest", "Stars", "Tub",
                "Ukulele", "Virgina", "Window", "Xylophone", "Year", "Zebra");

        //Removing whatever is inside S.
        String removed = s.remove("Ohio");

        //Making sure that s matches the expectation.
        assertEquals("Ohio", removed);
        assertEquals(sExpected, s);

    }

    @Test
    public final void testRemoveNonEmptyStringLeavingAnotherNonEmotySett() {
        //This would be a routine test case since we are removing a string
        //from a normal set while still having a non empty set.
        //Creating the sets.

        Set<String> s = this.createFromArgsTest("Mohamed", "Emanuel");
        Set<String> sExpected = this.createFromArgsRef("Emanuel");

        //Removing whatever is inside S.
        String removed = s.remove("Mohamed");

        //Making sure that s matches the expectation.
        assertEquals("Mohamed", removed);
        assertEquals(sExpected, s);
    }

    /*
     * removeAny test cases.
     */

    @Test
    public final void testRemoveAnyLeavingEmptySet() {
        //This would be a boundary test case since we are removing the only
        //string in the set.

        //Creating the sets.
        Set<String> s = this.createFromArgsTest("Emanuel");
        Set<String> sExpected = this.createFromArgsRef("Emanuel");

        //Removing Any string is inside S.
        String removed = s.removeAny();

        //Making sure that sExpected contains the element that was removed from
        //s and then removing it from sExpected.
        assertEquals(true, sExpected.contains(removed));
        sExpected.remove(removed);

        //Making sure that s matches the expectation.
        assertEquals(sExpected, s);
    }

    @Test
    public final void testRemoveAnyNothingEmptySet() {
        //This would also be a boundary since both sets are empty.
        //Creating the sets.
        Set<String> s = this.createFromArgsTest("");
        Set<String> sExpected = this.createFromArgsRef("");

        //Removing Any string whatever is inside S.
        String removed = s.removeAny();

        //Making sure that sExpected contains the element that was removed from
        //s and then removing it from sExpected.
        assertEquals(true, sExpected.contains(removed));
        sExpected.remove(removed);

        //Making sure that s matches the expectation.
        assertEquals(sExpected, s);
    }

    @Test
    public final void testRemoveAnyLeavingNonEmptySet1() {
        //This would be a routine/challenging case since we are removingAny from
        //a set that will not be empty after the method call.
        //Creating the sets.

        Set<String> s = this.createFromArgsTest("Ohio", "State");
        Set<String> sExpected = this.createFromArgsRef("Ohio", "State");

        //Removing Any string whatever is inside S.
        String removed = s.removeAny();

        //Making sure that sExpected contains the element that was removed from
        //s and then removing it from sExpected.
        assertEquals(true, sExpected.contains(removed));
        sExpected.remove(removed);

        //Making sure that s matches the expectation.
        assertEquals(sExpected, s);
    }

    @Test
    public final void testRemoveAnyNonEmptyStringRandomBig() {
        //This would be a challenging test case since the set is huge.
        //Creating the sets.
        Set<String> s = this.createFromArgsTest("Apple", "Bee", "Car", "Dog", "Emanuel",
                "Fall", "Go", "Home", "Igloo", "Janga", "Kangaroo", "Lemon", "Mohamed",
                "Nose", "Ohio", "Panthers", "Quarter", "Rest", "Stars", "Tub", "Ukulele",
                "Virgina", "Window", "Xylophone", "Year", "Zebra");
        Set<String> sExpected = this.createFromArgsRef("Apple", "Bee", "Car", "Dog",
                "Emanuel", "Fall", "Go", "Home", "Igloo", "Janga", "Kangaroo", "Lemon",
                "Mohamed", "Nose", "Ohio", "Panthers", "Quarter", "Rest", "Stars", "Tub",
                "Ukulele", "Virgina", "Window", "Xylophone", "Year", "Zebra");

        //Removing Any string whatever is inside S.
        String removed = s.removeAny();

        //Making sure that sExpected contains the element that was removed from
        //s and then removing it from sExpected.
        assertEquals(true, sExpected.contains(removed));
        sExpected.remove(removed);

        //Making sure that s matches the expectation.
        assertEquals(sExpected, s);

    }

    /*
     * contains test cases.
     */

    @Test
    public final void testContainsFalseEmpty() {
        //This would be a routine test case.
        // Creating the sets
        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef();

        // Value that will be tested
        String value = "d";
        boolean containsExpected = false;

        // Calling the method and storing the return in a variable
        boolean contains = s.contains(value);

        // Making sure that s was unchanged and it contains what was expected
        assertEquals(sExpected, s);
        assertEquals(containsExpected, contains);
    }

    @Test
    public final void testContainsFalseMultiple() {
        //This would be a routine test case.
        // Creating the sets
        Set<String> s = this.createFromArgsTest("a", "b", "c");
        Set<String> sExpected = this.createFromArgsRef("a", "b", "c");

        // Value that will be tested
        String value = "d";
        boolean containsExpected = false;

        // Calling the method and storing the return in a variable
        boolean contains = s.contains(value);

        // Making sure that s was unchanged and it contains what was expected
        assertEquals(sExpected, s);
        assertEquals(containsExpected, contains);
    }

    @Test
    public final void testContainsTrueMultiple() {
        // Creating the sets
        //This would be a routine test case.
        Set<String> s = this.createFromArgsTest("a", "b", "c", "d");
        Set<String> sExpected = this.createFromArgsRef("a", "b", "c", "d");

        // Value that will be tested
        String value = "d";
        boolean containsExpected = true;

        // Calling the method and storing the return in a variable
        boolean contains = s.contains(value);

        // Making sure that s was unchanged and it contains what was expected
        assertEquals(sExpected, s);
        assertEquals(containsExpected, contains);
    }

    @Test
    public final void testContainsTrue() {
        //This would be a routine test case.
        // Creating the sets
        Set<String> s = this.createFromArgsTest("c");
        Set<String> sExpected = this.createFromArgsRef("c");

        // Value that will be tested
        String value = "c";
        boolean containsExpected = true;

        // Calling the method and storing the return in a variable
        boolean contains = s.contains(value);

        // Making sure that s was unchanged and it contains what was expected
        assertEquals(sExpected, s);
        assertEquals(containsExpected, contains);
    }

    @Test
    public final void testContainsEmpStringOnEmpSet() {
        //This would be a boundary test case that will be false since there is no
        //"" in the set.
        //Creating the sets.

        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef();

        //Value that will be tested
        String value = "";
        boolean containsExpected = false;

        //Calling the method and storing the return in a variable.
        boolean contains = s.contains(value);

        //Making sure that s was unchanged and it contains what was expected.
        assertEquals(sExpected, s);
        assertEquals(containsExpected, contains);
    }

    @Test
    public final void testContainsEmpStringOnSetTrue() {
        //This would sort of be a routine and boundary test case since the empty
        //string is now inside the set.
        //Creating the sets.

        Set<String> s = this.createFromArgsTest("");
        Set<String> sExpected = this.createFromArgsRef("");

        //Value that will be tested
        String value = "";
        boolean containsExpected = true;

        //Calling the method and storing the return in a variable.
        boolean contains = s.contains(value);

        //Making sure that s was unchanged and it contains what was expected.
        assertEquals(sExpected, s);
        assertEquals(containsExpected, contains);
    }

    @Test
    public final void testContainsEmpStringOnSmallFalse() {
        //This would be a routine test case
        //Creating the sets.

        Set<String> s = this.createFromArgsTest("OHIO", "State", "Orgeon", "Ducks");
        Set<String> sExpected = this.createFromArgsRef("OHIO", "State", "Orgeon",
                "Ducks");

        //Value that will be tested
        String value = "";
        //Calling the method and storing the return in a variable.
        boolean containsExpected = false;
        boolean contains = s.contains(value);

        //Making sure that s was unchanged and it contains what was expected.
        assertEquals(sExpected, s);
        assertEquals(containsExpected, contains);
    }

    @Test
    public final void testContainsEmpStringOnSmallTrue() {
        //This would be a routine test case
        //Creating the sets.

        Set<String> s = this.createFromArgsTest("OHIO", "State", "Orgeon", "Ducks", "");
        Set<String> sExpected = this.createFromArgsRef("OHIO", "State", "Orgeon", "Ducks",
                "");

        //Value that will be tested
        String value = "";
        //Calling the method and storing the return in a variable.
        boolean containsExpected = true;
        boolean contains = s.contains(value);

        //Making sure that s was unchanged and it contains what was expected.
        assertEquals(sExpected, s);
        assertEquals(containsExpected, contains);
    }

    @Test
    public final void testContainsNonEmptyStringOnEmptySet() {
        //This would be a routine/boundary test case
        //Creating the sets.

        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef();

        //Value that will be tested
        String value = "CSE2231";
        boolean containsExpected = false;

        //Calling the method and storing the return in a variable.
        boolean contains = s.contains(value);

        //Making sure that s was unchanged and it contains what was expected.
        assertEquals(sExpected, s);
        assertEquals(containsExpected, contains);
    }

    @Test
    public final void testContainsNonEmptyStringOneStringTrue() {
        //This would be a routine test case
        //Creating the sets.

        Set<String> s = this.createFromArgsTest("Emanuel");
        Set<String> sExpected = this.createFromArgsRef("Emanuel");

        //Value that will be tested
        String value = "Emanuel";
        boolean containsExpected = true;

        //Calling the method and storing the return in a variable.
        boolean contains = s.contains(value);

        //Making sure that s was unchanged and it contains what was expected.
        assertEquals(sExpected, s);
        assertEquals(containsExpected, contains);
    }

    @Test
    public final void testContainsNonEmptyStringOneStringFalse() {
        //This would be a routine test case
        //Creating the sets.

        Set<String> s = this.createFromArgsTest("Emanuel");
        Set<String> sExpected = this.createFromArgsRef("Emanuel");

        //Value that will be tested
        String value = "Mohamed";
        boolean containsExpected = false;

        //Calling the method and storing the return in a variable.
        boolean contains = s.contains(value);

        //Making sure that s was unchanged and it contains what was expected.
        assertEquals(sExpected, s);
        assertEquals(containsExpected, contains);
    }

    @Test
    public final void testContainsBigSetRandomFalse() {
        //This would be a challenging test case that will be false because
        //the string will not be found in the set.
        //Creating the sets.
        Set<String> s = this.createFromArgsTest("Apple", "Bee", "Car", "Dog", "Emanuel",
                "Fall", "Go", "Home", "Igloo", "Janga", "Kangaroo", "Lemon", "Mohamed",
                "Nose", "Ohio", "Panthers", "Quarter", "Rest", "Stars", "Tub", "Ukulele",
                "Virgina", "Window", "Xylophone", "Year", "Zebra");
        Set<String> sExpected = this.createFromArgsRef("Apple", "Bee", "Car", "Dog",
                "Emanuel", "Fall", "Go", "Home", "Igloo", "Janga", "Kangaroo", "Lemon",
                "Mohamed", "Nose", "Ohio", "Panthers", "Quarter", "Rest", "Stars", "Tub",
                "Ukulele", "Virgina", "Window", "Xylophone", "Year", "Zebra");

        //Value that will be tested
        String value = "MohamedJama";
        boolean containsExpected = false;

        //Calling the method and storing the return in a variable.
        boolean contains = s.contains(value);

        //Making sure that s was unchanged and it contains what was expected.
        assertEquals(sExpected, s);
        assertEquals(containsExpected, contains);
    }

    @Test
    public final void testContainsBigSetRandomTrue() {
        //This would be a challenging test case that will be true because
        //the string will be found in the set.
        //Creating the sets.
        Set<String> s = this.createFromArgsTest("Apple", "Bee", "Car", "Dog", "Emanuel",
                "Fall", "Go", "Home", "Igloo", "Janga", "Kangaroo", "Lemon", "Mohamed",
                "Nose", "Ohio", "Panthers", "Quarter", "Rest", "Stars", "Tub", "Ukulele",
                "Virgina", "Window", "Xylophone", "Year", "Zebra");
        Set<String> sExpected = this.createFromArgsRef("Apple", "Bee", "Car", "Dog",
                "Emanuel", "Fall", "Go", "Home", "Igloo", "Janga", "Kangaroo", "Lemon",
                "Mohamed", "Nose", "Ohio", "Panthers", "Quarter", "Rest", "Stars", "Tub",
                "Ukulele", "Virgina", "Window", "Xylophone", "Year", "Zebra");

        //Value that will be tested
        String value = "Mohamed";
        boolean containsExpected = true;

        //Calling the method and storing the return in a variable.
        boolean contains = s.contains(value);

        //Making sure that s was unchanged and it contains what was expected.
        assertEquals(sExpected, s);
        assertEquals(containsExpected, contains);
    }

    /*
     * size test cases.
     */

    @Test
    public final void testSizeEmpty() {
        //This would be a boundary test case since the set is empty.
        //Creating the sets.
        Set<String> q = this.createFromArgsTest();
        Set<String> qExpected = this.createFromArgsRef();

        //Calling the size method.
        int size = q.size();
        final int sizeExpected = 0;

        //Making sure that s matches the expectation.
        assertEquals(qExpected, q);
        assertEquals(sizeExpected, size);
    }

    @Test
    public final void testSizeNotAnEmptySet() {
        //This would be a boundary/routine test case since the set is somewhat
        //empty.
        //Creating the sets.
        Set<String> s = this.createFromArgsTest("");
        Set<String> sExp = this.createFromArgsRef("");

        //Calling the size method.
        int size = s.size();
        final int sizeExpected = 1;

        //Making sure that s matches the expectation.
        assertEquals(sExp, s);
        assertEquals(sizeExpected, size);
    }

    @Test
    public final void testSizeNotEmptySeason() {
        //This would be a routine test case
        //Creating the sets.
        Set<String> s = this.createFromArgsTest("Fall");
        Set<String> sExpected = this.createFromArgsRef("Fall");

        //Calling the size method.
        int size = s.size();
        final int sizeExpected = 1;

        //Making sure that s matches the expectation.
        assertEquals(sExpected, s);
        assertEquals(sizeExpected, size);
    }

    @Test
    public final void testSizeNotEmptyNames() {
        //This would be a routine test case
        //creating the sets.
        Set<String> s = this.createFromArgsTest("Mohamed", "Emanuel");
        Set<String> sExpected = this.createFromArgsRef("Mohamed", "Emanuel");

        //Calling the size method.
        int size = s.size();
        final int sizeExpected = 2;

        //Making sure that s matches the expectation.
        assertEquals(sExpected, s);
        assertEquals(sizeExpected, size);
    }

    @Test
    public final void testSizeNotEmptyBigRandom() {
        //This would be a challenging case since there are multiple strings in
        //the sets.
        //Creating the sets.
        Set<String> s = this.createFromArgsTest("Apple", "Bee", "Car", "Dog", "Emanuel",
                "Fall", "Go", "Home", "Igloo", "Janga", "Kangaroo", "Lemon", "Mohamed",
                "Nose", "Ohio", "Panthers", "Quarter", "Rest", "Stars", "Tub", "Ukulele",
                "Virgina", "Window", "Xylophone", "Year", "Zebra");
        Set<String> sExpected = this.createFromArgsRef("Apple", "Bee", "Car", "Dog",
                "Emanuel", "Fall", "Go", "Home", "Igloo", "Janga", "Kangaroo", "Lemon",
                "Mohamed", "Nose", "Ohio", "Panthers", "Quarter", "Rest", "Stars", "Tub",
                "Ukulele", "Virgina", "Window", "Xylophone", "Year", "Zebra");

        //Calling the size method.
        int size = s.size();
        final int sizeExpected = 26;

        //Making sure that s matches the expectation.
        assertEquals(sExpected, s);
        assertEquals(sizeExpected, size);
    }

}
