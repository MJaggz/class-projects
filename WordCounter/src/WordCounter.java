import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * This project is a word counter that counts the word occurrences from an input
 * file of many words and outputs an HTML page that displays the words with
 * their counts in alphabetical order.
 *
 * @author Mohamed Jama
 *
 */
public final class WordCounter {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private WordCounter() {
    }

    /**
     * Compare {@code String}s in alphabetical order.
     */
    private static class StringLT implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.toLowerCase().compareTo(o2.toLowerCase());
        }
    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code separators}) or "separator string" (maximal length string of
     * characters in {@code separators}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @param separators
     *            the {@code Set} of separator characters
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires 0 <= position < |text|
     * @ensures <pre>
     * nextWordOrSeparator =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection separators = {}
     * then
     *   entries(nextWordOrSeparator) intersection separators = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection separators /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of separators  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of separators)
     * </pre>
     */
    private static String nextWordOrSeparator(String text, int position,
            Set<Character> separators) {
        assert text != null : "Violation of: text is not null";
        assert separators != null : "Violation of: separators is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";

        //Initialize a string as empty that will hold the next word or
        //Separator.
        String nextWordOSResult = "";

        //If the char at the position is a separator, enter the if statement
        //and add that to the empty string I initialized.
        if (separators.contains(text.charAt(position))) {
            nextWordOSResult = nextWordOSResult
                    + text.substring(position, position + 1);
            int i = position + 1;
            //Make a boolean isaSeperator case and set it to true to assume that
            //the next charss are separators as well.
            boolean isASeperator = true;
            //While the char is smaller than the text length and is also a
            //Separator, add it to nextWordOSResult.
            while (i < text.length() && isASeperator) {
                if (separators.contains(text.charAt(i))) {
                    nextWordOSResult = nextWordOSResult
                            + text.substring(i, i + 1);
                } else {
                    //If it is not, set isASeperator to false.
                    isASeperator = false;
                }
                i++;
            }

        } else {
            //Else if the char is not a separator, then it must be the a word.
            nextWordOSResult = nextWordOSResult
                    .concat(text.substring(position, position + 1));
            int i = position + 1;
            boolean isAWord = true;
            while (i < text.length() && isAWord) {
                //If the char at the position is not a separator, add it to the
                //string nextWordOSResult.
                if (!separators.contains(text.charAt(i))) {
                    nextWordOSResult = nextWordOSResult
                            + text.substring(i, i + 1);
                } else {
                    //If a separator char was found, set isAWord to false to
                    //stop adding to that word.
                    isAWord = false;
                }
                i++;
            }

        }
        //Return that sentence or maybe word that was stored.
        return nextWordOSResult;

    }

    /**
     * Generates a word frequency map from the words that were found in the
     * input file that was provided by the user.
     *
     * @requires file = [a valid location of a text file]
     * @ensures mapWordsToCounts = [a map that stores the words in file as the
     *          key and the word count as the value]
     * @param file
     *            the name of the input file.
     * @return a map with the words in file as a key and the words count as its
     *         value pair.
     */
    private static Map<String, Integer> mapWordsToCounts(String file) {

        //Initialize the input reader for the file and a new map that will hold
        //the word count.
        SimpleReader input = new SimpleReader1L(file);
        Map<String, Integer> wordCountMap = new Map1L<>();

        //Initialize a string of characters that are consideredd separators.
        String separators = "\\\'\"\t\n~`!@#$%^&*()_-+=[]{}|;:<>,./? ";
        Set<Character> separatorSet = new Set1L<>();
        //Use the addCharsToSet to make a set of chars from the separators above
        addCharsToSet(separators, separatorSet);

        //While loop that will continue until it reaches the end of the input
        //file.
        while (!input.atEOS()) {
            String line = input.nextLine();
            int i = 0;

            //Check each line for words and separators.
            while (i < line.length()) {
                String wordOrSeparator = nextWordOrSeparator(line, i,
                        separatorSet);

                //If the string found is a word, add it to the map.
                if (!separatorSet.contains(wordOrSeparator.charAt(0))) {
                    if (wordCountMap.hasKey(wordOrSeparator)) {
                        //If the word is already in the map, increment count.
                        int count = wordCountMap.value(wordOrSeparator);
                        wordCountMap.replaceValue(wordOrSeparator, count + 1);
                    } else {
                        //Add the new word to the map and give it the count of 1
                        wordCountMap.add(wordOrSeparator, 1);
                    }
                }

                //Increment i by the length of the word that was extracted.
                i += wordOrSeparator.length();
            }
        }

        //Close input and return wordCountMap..
        input.close();
        return wordCountMap;

    }

    /**
     * Adds all characters from the given {@code String} into the given
     * {@code Set}.
     *
     * @param str
     *            the given {@code String}
     * @param charSet
     *            the {@code Set} to be replaced
     * @replaces charSet
     * @ensures charSet = entries(str)
     */
    private static void addCharsToSet(String str, Set<Character> charSet) {

        //Clears charSet and removes anything inside of of that set.
        charSet.clear();

        //A for each loop that converts the string to a char array and iterates
        //iterates over each character.
        for (char c : str.toCharArray()) {

            //Ignores duplicates in the set and adds each character to the set
            //instead.
            charSet.add(c);
        }
    }

    /**
     * Returns a queue containing the keys from the provided map which are then
     * sorted in alphabetical order.
     *
     * @param map
     *            map with string,integer pairs whose strings will be put in
     *            alphabetical order in a queue.
     * @ensures getSortedKeys = [a queue who has the keys of map in alphabetical
     *          order]
     * @return a queue with the keys in map in alphabetical order.
     */
    private static Queue<String> getSortedKeys(Map<String, Integer> map) {
        //Initialize a new string named keys to hold keys and string comparator
        //using the stringLT class.
        Queue<String> keys = new Queue1L<>();
        Comparator<String> alphabetizedOrdering = new StringLT();

        //Use a for each loop to add each key from the map to the queue.
        for (Map.Pair<String, Integer> pair : map) {
            keys.enqueue(pair.key());
        }

        //Sort the keys in alphabetical order.
        keys.sort(alphabetizedOrdering);

        //return the sorted queue of keys.
        return keys;
    }

    /**
     * Creates an HTML page listing the words from the input file along with the
     * number of times that word appears in the input file.
     *
     * @requires inputFile and outputFile to be a valid location of a file and
     *           for queue to contain all keys of map.
     * @param inputFile
     *            the name of the input file.
     * @param outputFile
     *            the name of the output file.
     * @param map
     *            map that stores words and their count
     * @param queue
     *            queue that stores words in map in alphabetical order
     */
    private static void createWordCountHTMLPage(String inputFile,
            String outputFile, Map<String, Integer> map, Queue<String> queue) {

        //Initialize output stream and make HTML headerss.
        SimpleWriter output = new SimpleWriter1L(outputFile);
        output.println("<html>");
        output.println("<head>");
        output.println("<title>Words Counted in " + inputFile + "</title>");
        output.println("</head>");
        output.println("<body>");
        output.println("<h1>Words Counted in " + inputFile + "</h1><hr/>");
        output.println("<table border=\"1\">");
        output.println("<tr><th>Words</th><th>Counts</th></tr>");

        //Use a for each loop to display a table with words and their counts.
        for (String word : queue) {
            output.println("<tr><td>" + word + "</td><td>" + map.value(word)
                    + "</td></tr>");
        }

        //close the output stream and HTMl headers.
        output.println("</table>");
        output.println("</body>");
        output.println("</html>");
        output.close();

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

        //Ask the user for a name of an input file and store it as input.
        out.print("Please enter the name of an input file: ");
        String input = in.nextLine();
        //Ask the user also for a name of an output file and store it as output.
        out.print("Please enter the name of an output file: ");
        String output = in.nextLine();

        Map<String, Integer> wordCount = mapWordsToCounts(input);
        Queue<String> wordsInOrder = getSortedKeys(wordCount);

        //Generates the HTML page with the words and their counts
        //alphabetically.
        createWordCountHTMLPage(input, output, wordCount, wordsInOrder);
        out.println("Done! The html page: " + output + " has been generated!");

        ///Close the input and output streams.
        in.close();
        out.close();
    }

}
