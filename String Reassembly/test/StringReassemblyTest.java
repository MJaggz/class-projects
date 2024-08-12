import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;
import components.set.Set2;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * @author Mohamed Jama
 *
 */
public class StringReassemblyTest {

    /**
     * Tests for combination method.
     */
    @Test
    //In this test case, str1 holds "HELLOWORLD" while str2 holds
    //"WORLDMYNAMEISMOHAMED"
    public void hello_world_combination() {
        String str1 = "HELLOWORLD";
        String str2 = "WORLDMYNAMEISMOHAMED";
        //This test combines both strings into a new string named testCombo where the
        //overlap is only counted once. This would be a normal routine test case.
        String testCombo = StringReassembly.combination(str1, str2, 5);
        assertEquals("HELLOWORLDMYNAMEISMOHAMED", testCombo);

    }

    @Test
    //In this test case, str1 holds "Ohio State"while str2 holds "State University"
    public void osu_combination() {
        String str1 = "Ohio State";
        String str2 = "State University";
        //This test combines both strings into a new string named osu where the
        //overlap is only counted once. This would be another normal routine test
        //case.
        String osu = StringReassembly.combination(str1, str2, 5);
        assertEquals("Ohio State University", osu);

    }

    @Test
    //In this test case, str1 holds "String Reassem"while str2 holds "embly"
    //This test case is sort of a challenging case since there is no overlap
    // the two strings would add to each other.
    public void project_name_combination() {
        String str1 = "String Reassem";
        String str2 = "embly";
        String project = StringReassembly.combination(str1, str2, 2);
        assertEquals("String Reassembly", project);

    }

    @Test
    //This test case would be a boundary case since both strings are empty.
    public void empty_string_combination() {
        String str1 = "";
        String str2 = "";
        String empty = StringReassembly.combination(str1, str2, 0);
        assertEquals("", empty);

    }

    @Test
    ///This test case is also another challenging case since it adds and empty
    //string to a string with words.
    public void moto_moto_combination() {
        String str1 = "Hippo";
        String str2 = "";
        String hippo = StringReassembly.combination(str1, str2, 0);
        assertEquals("Hippo", hippo);

    }

    @Test
    //This test case is a routine test cast which adds two strings to each other
    //with no overlap.
    public void song_combination() {
        String str1 = "Candy";
        String str2 = "Shop";
        String fiftyCent = StringReassembly.combination(str1, str2, 0);
        assertEquals("CandyShop", fiftyCent);

    }

    /**
     * Tests for addToSetAvoidingSubstrings method.
     */

    @Test
    //This test case tests the addToSetAvoidingSubstrings method and makes sure
    //that the string that is going be added to the set is not a substring
    //of another.
    public void directions_AvoidingSubstrings() {
        String str = "far north";
        String strExp = "far north";
        Set<String> strSet = new Set2<>();
        Set<String> strSetExp = new Set2<>();
        strSet.add("car");
        strSet.add("far");
        strSet.add("ear");
        StringReassembly.addToSetAvoidingSubstrings(strSet, str);
        strSetExp.add("car");
        strSetExp.add("far north");
        strSetExp.add("ear");
        assertEquals(strExp, str);
        assertEquals(strSetExp, strSet);

    }

    @Test
    //this test case is sort of a boundary and routine since the string is one
    //word and you can't have a substring of two of the same words so it just
    //adds monkey to the set.
    public void monkey_AvoidingSubstrings() {
        String str = "monkey";
        String strExp = "monkey";
        Set<String> strSet = new Set2<>();
        Set<String> strSetExp = new Set2<>();
        StringReassembly.addToSetAvoidingSubstrings(strSet, str);
        strSetExp.add("monkey");
        assertEquals(strExp, str);
        assertEquals(strSetExp, strSet);

    }

    @Test
    //This test case is sort of a challenging case since it is adding an empty
    //string and front is not a substring of the empty set since its empty.
    public void front_AvoidingSubstrings() {
        String str = "front";
        String strExp = "front";
        Set<String> strSet = new Set2<>();
        Set<String> strSetExp = new Set2<>();
        strSet.add("");
        StringReassembly.addToSetAvoidingSubstrings(strSet, str);
        strSetExp.add("front");
        assertEquals(strExp, str);
        assertEquals(strSetExp, strSet);

    }

    @Test
    //This test case would be a boundary test case since it is dealing with
    //empty strings.
    public void empty_string_AvoidingSubstrings() {
        String str = "";
        String strExp = "";
        Set<String> strSet = new Set2<>();
        Set<String> strSetExp = new Set2<>();
        StringReassembly.addToSetAvoidingSubstrings(strSet, str);
        strSetExp.add("");
        assertEquals(strExp, str);
        assertEquals(strSetExp, strSet);

    }

    /**
     * Tests for linesFromInput method.
     */

    @Test
    //In this test case, I made a text file in the data folder for this project
    //named "test1.txt" which had "hello hi my name is mohamed" with one word
    //on each line and no spaces.
    public void my_name_linesFromInput() {
        SimpleReader inputFile = new SimpleReader1L("./data/test1.txt");
        Set<String> lines = StringReassembly.linesFromInput(inputFile);
        //Made an empty set called linesExpected.
        Set<String> linesExpected = new Set2<>();
        linesExpected.add("hello");
        linesExpected.add("hi");
        linesExpected.add("my");
        linesExpected.add("name");
        linesExpected.add("is");
        linesExpected.add("mohamed");
        assertEquals(linesExpected, lines);
        assertEquals(true, inputFile.isOpen());
    }

    @Test
    //This test case I talk about my hobby and just like the last test case,,
    //I make a new text file named "test2.txt".
    public void hobby_linesFromInput() {
        SimpleReader inputFile = new SimpleReader1L("./data/test2.txt");
        Set<String> lines = StringReassembly.linesFromInput(inputFile);
        Set<String> linesExpected = new Set2<>();
        //Made an empty set called linesExpected.
        linesExpected.add("I");
        linesExpected.add("love");
        linesExpected.add("playing");
        linesExpected.add("video");
        linesExpected.add("games");
        assertEquals(linesExpected, lines);
        assertEquals(true, inputFile.isOpen());
    }

    @Test
    public void brutus_linesFromInput() {
        //This test case hold "Brutus has goat status" with each word havving its
        //own line.
        SimpleReader inputFile = new SimpleReader1L("./data/test3.txt");
        Set<String> lines = StringReassembly.linesFromInput(inputFile);
        //Made an empty set called linesExpected.
        Set<String> linesExpected = new Set2<>();
        linesExpected.add("Brutus");
        linesExpected.add("has");
        linesExpected.add("goat");
        linesExpected.add("status");
        assertEquals(linesExpected, lines);
        assertEquals(true, inputFile.isOpen());

    }

    /**
     * Tests for printWithLineSeparators method.
     */
    @Test
    //This test case uses the printLineWithSeperators method and takes my string
    //and where ever there is a '~', it will seperate it. in this case i tested
    //it with "Ohio State University",
    public void OSU_printWithLineSeparators() {
        SimpleWriter Out = new SimpleWriter1L("./test/test.txt");
        SimpleReader In = new SimpleReader1L("./test/test.txt");

        StringReassembly.printWithLineSeparators("Ohio~State~University", Out);

        assertEquals(In.nextLine(), "Ohio");
        assertEquals(In.nextLine(), "State");
        assertEquals(In.nextLine(), "University");

        In.close();
        Out.close();
    }

    @Test
    //This test case is sort of similar to the first one but instead of having
    //only one '~' separator, it has a case where it has 2. This would be
    //a challenging case.
    public void testing_printWithLineSeparators() {
        SimpleWriter Out = new SimpleWriter1L("./test/test.txt");
        SimpleReader In = new SimpleReader1L("./test/test.txt");

        StringReassembly.printWithLineSeparators("~Hello~This is a~~test~",
                Out);
        assertEquals(In.nextLine(), "");
        assertEquals(In.nextLine(), "Hello");
        assertEquals(In.nextLine(), "This is a");
        assertEquals(In.nextLine(), "");
        assertEquals(In.nextLine(), "test");

        In.close();
        Out.close();
    }

    @Test
    //This is another challenging case since there are multiple '~' in the string.
    public void testing_with_JUnit_printWithLineSeparators() {
        SimpleWriter Out = new SimpleWriter1L("./test/test.txt");
        SimpleReader In = new SimpleReader1L("./test/test.txt");

        StringReassembly.printWithLineSeparators(
                "~~~~Testing JUnit is so~~~ fun~~~", Out);
        assertEquals(In.nextLine(), "");
        assertEquals(In.nextLine(), "");
        assertEquals(In.nextLine(), "");
        assertEquals(In.nextLine(), "");
        assertEquals(In.nextLine(), "Testing JUnit is so");
        assertEquals(In.nextLine(), "");
        assertEquals(In.nextLine(), "");
        assertEquals(In.nextLine(), " fun");
        assertEquals(In.nextLine(), "");
        assertEquals(In.nextLine(), "");

        In.close();
        Out.close();
    }

    @Test
    //This would be a routine case that replaces the '~' with new lines. for this
    //Test case, I used Eid since Eid is a couple days away.
    public void eid_printWithLineSeparators() {
        SimpleWriter Out = new SimpleWriter1L("./test/test.txt");
        SimpleReader In = new SimpleReader1L("./test/test.txt");

        StringReassembly.printWithLineSeparators("I~~can't~~wait~~for~~eid!",
                Out);
        assertEquals(In.nextLine(), "I");
        assertEquals(In.nextLine(), "");
        assertEquals(In.nextLine(), "can't");
        assertEquals(In.nextLine(), "");
        assertEquals(In.nextLine(), "wait");
        assertEquals(In.nextLine(), "");
        assertEquals(In.nextLine(), "for");
        assertEquals(In.nextLine(), "");
        assertEquals(In.nextLine(), "eid!");

        In.close();
        Out.close();
    }

    @Test
    //This would be a challenging case since there are 2/3 '`' between each color
    //of the rainbow from red too blue.
    public void colors_of_rainbow_printWithLineSeparators() {
        SimpleWriter Out = new SimpleWriter1L("./test/test.txt");
        SimpleReader In = new SimpleReader1L("./test/test.txt");

        StringReassembly.printWithLineSeparators(
                "~red~~~orange~~~yellow~~~~~green~~~~~blue~~~", Out);
        assertEquals(In.nextLine(), "");
        assertEquals(In.nextLine(), "red");
        assertEquals(In.nextLine(), "");
        assertEquals(In.nextLine(), "");
        assertEquals(In.nextLine(), "orange");
        assertEquals(In.nextLine(), "");
        assertEquals(In.nextLine(), "");
        assertEquals(In.nextLine(), "yellow");
        assertEquals(In.nextLine(), "");
        assertEquals(In.nextLine(), "");
        assertEquals(In.nextLine(), "");
        assertEquals(In.nextLine(), "");
        assertEquals(In.nextLine(), "green");
        assertEquals(In.nextLine(), "");
        assertEquals(In.nextLine(), "");
        assertEquals(In.nextLine(), "");
        assertEquals(In.nextLine(), "");
        assertEquals(In.nextLine(), "blue");
        assertEquals(In.nextLine(), "");
        assertEquals(In.nextLine(), "");

        In.close();
        Out.close();
    }

    @Test
    //This is a routine test case which is a small string.
    public void summer_printWithLineSeparators() {
        SimpleWriter Out = new SimpleWriter1L("./test/test.txt");
        SimpleReader In = new SimpleReader1L("./test/test.txt");

        StringReassembly.printWithLineSeparators(" ~i ~love ~summer ~", Out);
        assertEquals(In.nextLine(), " ");
        assertEquals(In.nextLine(), "i ");
        assertEquals(In.nextLine(), "love ");
        assertEquals(In.nextLine(), "summer ");

        In.close();
        Out.close();
    }

    @Test
    //this is also another routine case that checks if the method works with
    //the '~' being replaced with a new line.
    public void cake_printWithLineSeparators() {
        SimpleWriter Out = new SimpleWriter1L("./test/test.txt");
        SimpleReader In = new SimpleReader1L("./test/test.txt");

        StringReassembly.printWithLineSeparators(" ~i ~love ~cake ~", Out);
        assertEquals(In.nextLine(), " ");
        assertEquals(In.nextLine(), "i ");
        assertEquals(In.nextLine(), "love ");
        assertEquals(In.nextLine(), "cake ");

        In.close();
        Out.close();
    }
}
