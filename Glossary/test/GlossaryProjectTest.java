import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;
import components.map.Map2;
import components.queue.Queue;
import components.set.Set;
import components.set.Set2;

/**
 * These are test cases for my glossary project.
 *
 * @author Mohamed Jama
 */
public class GlossaryProjectTest {

    /**
     * Tests for nextWordOrSeperator method.
     */
    @Test
    //This test case is a routine and a somewhat challenging test case since
    //it has two separator at the beginning of the sentence. This test case
    //returns the next word or sequence in my string which in this case is',.'
    public void test_favoriteSnackNextWordOrSeparator() {
        String text = " ,.I like to eat donuts,";
        int pos = 0;
        Set<Character> separators = new Set2<>();
        separators.add(' ');
        separators.add('\t');
        separators.add(',');
        separators.add('.');
        separators.add('\n');
        separators.add('!');
        separators.add('?');
        separators.add('/');
        separators.add('\\');
        Set<Character> separatorsExp = separators.newInstance();
        separatorsExp.add(' ');
        separatorsExp.add('\t');
        separatorsExp.add(',');
        separatorsExp.add('.');
        separatorsExp.add('\n');
        separatorsExp.add('!');
        separatorsExp.add('?');
        separatorsExp.add('/');
        separatorsExp.add('\\');
        String nextWordOSResult = GlossaryProject.nextWordOrSeparator(text, pos,
                separators);
        String nextWordOSResultExp = " ,.";
        assertEquals(nextWordOSResultExp, nextWordOSResult);
        assertEquals(separatorsExp, separators);

    }

    @Test
    //This test case is a normal routine test case since my string is just my name
    //and starts at the position 6 and since my name is just one word, it returns
    //the letter 'd'.
    public void test_myNameNextWordOrSeparator() {
        String text = "mohamed";
        int pos = 6;
        Set<Character> separators = new Set2<>();
        separators.add(' ');
        separators.add('\t');
        separators.add(',');
        separators.add('.');
        separators.add('\n');
        separators.add('!');
        separators.add('?');
        separators.add('/');
        separators.add('\\');
        Set<Character> separatorsExp = separators.newInstance();
        separatorsExp.add(' ');
        separatorsExp.add('\t');
        separatorsExp.add(',');
        separatorsExp.add('.');
        separatorsExp.add('\n');
        separatorsExp.add('!');
        separatorsExp.add('?');
        separatorsExp.add('/');
        separatorsExp.add('\\');
        String nextWordOSResult = GlossaryProject.nextWordOrSeparator(text, pos,
                separators);
        String nextWordOSResultExp = "d";
        assertEquals(nextWordOSResultExp, nextWordOSResult);
        assertEquals(separatorsExp, separators);

    }

    @Test
    //This test case is a routine test case since it just tests a sentence.
    //It starts at pos 0 and only takes in the word hi which is right before the
    //comma seperator.
    public void test_summerWordOrSeparator() {
        String text = "Hi, summer";
        int pos = 0;
        Set<Character> separators = new Set2<>();
        separators.add(' ');
        separators.add('\t');
        separators.add(',');
        separators.add('.');
        separators.add('\n');
        separators.add('!');
        separators.add('?');
        separators.add('/');
        separators.add('\\');
        Set<Character> separatorsExp = separators.newInstance();
        separatorsExp.add(' ');
        separatorsExp.add('\t');
        separatorsExp.add(',');
        separatorsExp.add('.');
        separatorsExp.add('\n');
        separatorsExp.add('!');
        separatorsExp.add('?');
        separatorsExp.add('/');
        separatorsExp.add('\\');
        String nextWordOSResult = GlossaryProject.nextWordOrSeparator(text, pos,
                separators);
        String nextWordOSResultExp = "Hi";
        assertEquals(nextWordOSResultExp, nextWordOSResult);
        assertEquals(separatorsExp, separators);

    }

    @Test
    //This test case is sort of similar to the last one but should begin at pos 4
    //which the next word is summer.
    public void test_summertWordOrSeparator2() {
        String text = "Hi, summer";
        int pos = 4;
        Set<Character> separators = new Set2<>();
        separators.add(' ');
        separators.add('\t');
        separators.add(',');
        separators.add('.');
        separators.add('\n');
        separators.add('!');
        separators.add('?');
        separators.add('/');
        separators.add('\\');
        Set<Character> separatorsExp = separators.newInstance();
        separatorsExp.add(' ');
        separatorsExp.add('\t');
        separatorsExp.add(',');
        separatorsExp.add('.');
        separatorsExp.add('\n');
        separatorsExp.add('!');
        separatorsExp.add('?');
        separatorsExp.add('/');
        separatorsExp.add('\\');
        String nextWordOSResult = GlossaryProject.nextWordOrSeparator(text, pos,
                separators);
        String nextWordOSResultExp = "summer";
        assertEquals(nextWordOSResultExp, nextWordOSResult);
        assertEquals(separatorsExp, separators);

    }

    @Test
    //This test case is a challenging case since it is a run on sentence with no
    //separators or another word. So this test case should just return the whole
    //sentence.
    public void test_runOnWordOrSeparator() {
        String text = "ilovesleep";
        int pos = 0;
        Set<Character> separators = new Set2<>();
        separators.add(' ');
        separators.add('\t');
        separators.add(',');
        separators.add('.');
        separators.add('\n');
        separators.add('!');
        separators.add('?');
        separators.add('/');
        separators.add('\\');
        Set<Character> separatorsExp = separators.newInstance();
        separatorsExp.add(' ');
        separatorsExp.add('\t');
        separatorsExp.add(',');
        separatorsExp.add('.');
        separatorsExp.add('\n');
        separatorsExp.add('!');
        separatorsExp.add('?');
        separatorsExp.add('/');
        separatorsExp.add('\\');
        String nextWordOSResult = GlossaryProject.nextWordOrSeparator(text, pos,
                separators);
        String nextWordOSResultExp = "ilovesleep";
        assertEquals(nextWordOSResultExp, nextWordOSResult);
        assertEquals(separatorsExp, separators);

    }

    @Test
    //This is another challenging case with mynameismohamed as one run on sentence
    //and my last name jama as another word to make sure that this method works for
    //run on sentences with multiple words.
    public void test_runOnSentenceLongNextWordOrSeparator19() {
        String text = "mynameismohamed jama";
        int pos = 0;
        Set<Character> separators = new Set2<>();
        separators.add(' ');
        separators.add('\t');
        separators.add(',');
        separators.add('.');
        separators.add('\n');
        separators.add('!');
        separators.add('?');
        separators.add('/');
        separators.add('\\');
        Set<Character> separatorsExp = separators.newInstance();
        separatorsExp.add(' ');
        separatorsExp.add('\t');
        separatorsExp.add(',');
        separatorsExp.add('.');
        separatorsExp.add('\n');
        separatorsExp.add('!');
        separatorsExp.add('?');
        separatorsExp.add('/');
        separatorsExp.add('\\');
        String nextWordOSResult = GlossaryProject.nextWordOrSeparator(text, pos,
                separators);
        String nextWordOSResultExp = "mynameismohamed";
        assertEquals(nextWordOSResultExp, nextWordOSResult);
        assertEquals(separatorsExp, separators);

    }

    @Test
    //This test case is a boundary case which is just empty strings and the expected
    //is just empty strings.
    public void test_boundaryNextWordOrSeparator() {//boundary
        String text = " ";
        int pos = 0;
        Set<Character> separators = new Set2<>();
        separators.add(' ');
        separators.add('\t');
        separators.add(',');
        separators.add('.');
        separators.add('\n');
        separators.add('!');
        separators.add('?');
        separators.add('/');
        separators.add('\\');
        Set<Character> separatorsExp = separators.newInstance();
        separatorsExp.add(' ');
        separatorsExp.add('\t');
        separatorsExp.add(',');
        separatorsExp.add('.');
        separatorsExp.add('\n');
        separatorsExp.add('!');
        separatorsExp.add('?');
        separatorsExp.add('/');
        separatorsExp.add('\\');
        String nextWordOSResult = GlossaryProject.nextWordOrSeparator(text, pos,
                separators);
        String nextWordOSResultExp = " ";
        assertEquals(nextWordOSResultExp, nextWordOSResult);
        assertEquals(separatorsExp, separators);

    }

    /**
     * Tests for MakeGlossaryMap method.
     */

    @Test
    //This test case is a routune test case since it is just the index txt file
    //that I used on the project and wanted to make sure it works.
    public void test_indexMakeGlossaryMap() {//routine
        String inputFile = "data/test1.txt";
        Map<String, String> map = GlossaryProject.makeGlossaryMap(inputFile);
        Map<String, String> mapExp = map.newInstance();
        mapExp.add("meaning",
                "something that one wishes to convey, especially by language");
        mapExp.add("term", "a word whose definition is in a glossary");
        mapExp.add("word",
                "a string of characters in a language, which has at least one character");
        mapExp.add("definition",
                "a sequence of words that gives meaning to a term");
        mapExp.add("glossary",
                "a list of difficult or specialized terms, with their definitions, usually near the end of a book");
        mapExp.add("language",
                "a set of strings of characters, each of which has meaning");
        mapExp.add("book", "a printed or written literary work");
        assertEquals(mapExp, map);
    }

    @Test
    //This test case is also a routine test case which has 3 colors of the rainbow.
    public void test_colorsInRainbowMakeGlossaryMap() {//routine
        String inputFile = "data/test2.txt";
        Map<String, String> map = GlossaryProject.makeGlossaryMap(inputFile);
        Map<String, String> mapExp = map.newInstance();
        mapExp.add("Red", "The first color of the rainbow");
        mapExp.add("Orange", "The second color of the rainbow");
        mapExp.add("Yellow", "The third color of the rainbow");
        assertEquals(mapExp, map);

    }

    @Test
    //This test case is somewhat of a challenging case since for Monday and Wednesday,
    //there are multiiple words on a line.
    public void test_daysOfTheWeekMakeGlossaryMap() {//challenging
        String inputFile = "data/test3.txt";
        Map<String, String> map = GlossaryProject.makeGlossaryMap(inputFile);
        Map<String, String> mapExp = map.newInstance();
        mapExp.add("Monday", "the first day of the week");
        mapExp.add("Tuesday", "the second day of the week");
        mapExp.add("Wednesday", "the third day of the week");
        assertEquals(mapExp, map);

    }

    @Test
    //This test case is a semi boundary case since it uses puncuation which are
    //special values.
    public void test_puncutationMakeGlossaryMap() {//boundary
        String inputFile = "data/test4.txt";
        Map<String, String> map = GlossaryProject.makeGlossaryMap(inputFile);
        Map<String, String> mapExp = map.newInstance();
        mapExp.add(".", ".");
        mapExp.add("!", "!");
        assertEquals(mapExp, map);

    }

    /**
     * Tests for GlossaryTerms method.
     */

    @Test
    //This test case for the glossaryTerms method is a challenging case since
    //there are multiple states that start with the letter a and wanted to make sure
    //that it alphabetizes these states correctly.
    public void test_alphabeticStatesGlossaryTerms() {//challenging
        Map<String, String> map = new Map2<>();
        map.add("alaska", "1");
        map.add("california", "1");
        map.add("alabama", "1");
        map.add("colorado", "1");
        map.add("arizona", "1");
        map.add("arkansas", "1");
        Map<String, String> mapExp = new Map2<>();
        mapExp.add("alaska", "1");
        mapExp.add("california", "1");
        mapExp.add("alabama", "1");
        mapExp.add("colorado", "1");
        mapExp.add("arizona", "1");
        mapExp.add("arkansas", "1");

        Queue<String> q = GlossaryProject.sortGlossaryTerms(map);
        Queue<String> qExp = q.newInstance();
        qExp.enqueue("alabama");
        qExp.enqueue("alaska");
        qExp.enqueue("arizona");
        qExp.enqueue("arkansas");
        qExp.enqueue("california");
        qExp.enqueue("colorado");

        assertEquals(mapExp, map);
        assertEquals(qExp, q);

    }

    @Test
    //This test case is a boundary case since it is just an empty map.
    public void test_emptyCaseGlossaryTerms() {//boundary empty case
        Map<String, String> map = new Map2<>();
        Map<String, String> mapExp = new Map2<>();

        Queue<String> q = GlossaryProject.sortGlossaryTerms(map);
        Queue<String> qExp = q.newInstance();

        assertEquals(mapExp, map);
        assertEquals(qExp, q);

    }

    @Test
    //This test case is a routine test case since it just alphabetizes some days
    //of the week
    public void test_daysOfTheWeekGlossaryTerms() {//routine
        Map<String, String> map = new Map2<>();
        map.add("monday", "1");
        map.add("thursday", "1");
        map.add("friday", "1");
        map.add("tuesday", "1");
        map.add("saturday", "1");
        map.add("sunday", "1");
        Map<String, String> mapExp = new Map2<>();
        mapExp.add("monday", "1");
        mapExp.add("thursday", "1");
        mapExp.add("friday", "1");
        mapExp.add("tuesday", "1");
        mapExp.add("saturday", "1");
        mapExp.add("sunday", "1");

        Queue<String> q = GlossaryProject.sortGlossaryTerms(map);
        Queue<String> qExp = q.newInstance();
        qExp.enqueue("friday");
        qExp.enqueue("monday");
        qExp.enqueue("saturday");
        qExp.enqueue("sunday");
        qExp.enqueue("thursday");
        qExp.enqueue("tuesday");

        assertEquals(mapExp, map);
        assertEquals(qExp, q);

    }
}
