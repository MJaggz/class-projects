import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * This project is a project that when given an input file will generate a tag
 * cloud html file using Java Imports.
 *
 * @author Mohamed Jama & Emanuel Messele
 *
 */
public final class TagCloudGenerator {

    /**
     * A comparator for values in Map.Pair<String,Integer> that will compare in
     * alphabetical order.
     */
    private static class MapIntegerLT implements Comparator<Map.Entry<String, Integer>> {

        @Override
        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
            return o1.getValue().compareTo(o2.getValue());
        }

    }

    /**
     * A comparator for keys in Map.Pair<String,Integer> that will compare in
     * alphabetical order.
     */
    private static class MapStringLT implements Comparator<Map.Entry<String, Integer>> {

        @Override
        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
            return o1.getKey().toLowerCase().compareTo(o2.getKey().toLowerCase());
        }

    }

    /**
     * Comparator for comparing String keys of Map.Pair<String, Integer>.
     */
    private static final Comparator<Map.Entry<String, Integer>> KEYORDERING = new MapStringLT();

    /**
     * Comparator for comparing Integer values of Map.Pair<String, Integer>.
     */
    private static final Comparator<Map.Entry<String, Integer>> VALORDERING = new MapIntegerLT();

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private TagCloudGenerator() {
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
            nextWordOSResult = nextWordOSResult + text.substring(position, position + 1);
            int i = position + 1;
            //Make a boolean isaSeperator case and set it to true to assume that
            //the next chars are separators as well.
            boolean isASeperator = true;
            //While the char is smaller than the text length and is also a
            //Separator, add it to nextWordOSResult.
            while (i < text.length() && isASeperator) {
                if (separators.contains(text.charAt(i))) {
                    nextWordOSResult = nextWordOSResult + text.substring(i, i + 1);
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
                    nextWordOSResult = nextWordOSResult + text.substring(i, i + 1);
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
     * Generates the set of characters in the given {@code String} into the
     * given {@code Set}.
     *
     * @param str
     *            the given {@code String}
     * @param charSet
     *            the {@code Set} to be replaced
     * @replaces charSet
     * @ensures charSet = entries(str)
     */
    private static void generateElements(String str, Set<Character> charSet) {

        //Clear charSet.
        charSet.clear();
        //Iterates through each char and checks if the set contains charAt(i).
        for (int i = 0; i < str.length(); i++) {
            if (!charSet.contains(str.charAt(i))) {
                //If it does not contain str.charAt(i), add it to charSet.
                charSet.add(str.charAt(i));
            }
        }
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
    private static Map<String, Integer> mapWordsToCounts(BufferedReader fileIn) {

        //Creating a new map.
        Map<String, Integer> map = new HashMap<>();

        //Initialize a string of characters that are considered separators.
        String separatorString = " \t\n\r,-.!?[]';:/()";
        Set<Character> separatorSet = new HashSet<>();
        generateElements(separatorString, separatorSet);

        //A try catch block with a while loop that will continue until it
        //reaches the end of the input.
        try {
            String line = fileIn.readLine();
            while (line != null) {
                int i = 0;
                line = line.toLowerCase();
                //Check each line for words and separators.
                while (i < line.length()) {
                    String wordSep = nextWordOrSeparator(line, i, separatorSet);
                    //If the string found is a word, add it to the map.
                    if (!separatorSet.contains(wordSep.charAt(0))) {
                        if (!map.containsKey(wordSep)) {
                            map.put(wordSep, 1);
                        } else {
                            //If the word is already in the map increment it.
                            int newVal = map.get(wordSep) + 1;
                            map.replace(wordSep, newVal);
                        }
                    }
                    i += wordSep.length();
                }
                line = fileIn.readLine();
            }
            //Catch portion of the try catch block.
        } catch (IOException e) {
            System.err.println("ERROR: Input file could not be read.");
        }

        //Returning the map
        return map;

    }

    /**
     * Keeps only the top n most frequent words in the given map by removing
     * less frequent words.
     *
     * @updates wordCount
     * @requires n <= |wordCount|
     * @param wordCount
     *            map that stores words along with their counts.
     * @param n
     *            The maximum number of top frequent words that will remain in
     *            the map.
     * @ensures wordCount is modified to hold only the top n most frequent words
     *          from its original contents, ensuring |wordCount| = n.
     */
    private static void topNFrequentWords(Map<String, Integer> wordCount, int n) {

        //Creating a set that will hold all the entries in the map
        Set<Map.Entry<String, Integer>> entries = wordCount.entrySet();
        //Creating an ArrayList
        List<Map.Entry<String, Integer>> entriesSorted = new ArrayList<>();

        //A for each loop that will remove each entry and add it to the
        //ArrayList.
        for (Map.Entry<String, Integer> pair : entries) {
            entriesSorted.add(pair);
        }

        //Sorting the Arraylist by using the valueOrdering
        entries.clear();
        entriesSorted.sort(VALORDERING);

        //Removing the least frequent words and then adding the top n
        //frequent words.
        for (int i = 0; i < entriesSorted.size(); i++) {
            Map.Entry<String, Integer> removedN = entriesSorted.get(i);
            if (i >= entriesSorted.size() - n) {
                //Adding the top n frequent words to temp.
                wordCount.put(removedN.getKey(), removedN.getValue());
            }
        }

    }

    /**
     * Converts the entries of a {@code <String, Integer>} map into a sorted
     * list.
     *
     * @param m
     *            the map containing key-value pairs to be processed
     * @return a list of map entries sorted in ascending order by their keys
     * @restores m
     * @ensures mapToSortedList = [a list containing all entries of the map
     *          sorted by keys]
     */
    private static List<Map.Entry<String, Integer>> mapToSortedList(
            Map<String, Integer> m) {
        //Creating an ArrayList to store the entries.
        List<Map.Entry<String, Integer>> entriesList = new ArrayList<>();
        //Storing the entries in a set.
        Set<Map.Entry<String, Integer>> entriesSet = m.entrySet();

        //Iterating through the entry set and adding them to the entries list.
        for (Map.Entry<String, Integer> pair : entriesSet) {
            entriesList.add(pair);
        }
        //Sorting the list
        entriesList.sort(KEYORDERING);
        //returning the list
        return entriesList;
    }

    /**
     * Changes the value of a pair to so that it is scaled appropriately between
     * two integers.
     *
     * @param p
     *            the pair to be from which the return will be calculated
     * @param maxFont
     *            the max font size
     * @param minFont
     *            the minimum font size
     * @param maxFreq
     *            the max integer value in the map
     * @param minFreq
     *            the minimum integer value in the map
     * @return the font size calculated by scaling the frequency value of the
     *         pair to fit within the range [minFont, maxFont], based on the
     *         range [minFreq, maxFreq].
     */
    private static int convertFrequencyToFontSize(Entry<String, Integer> p, int maxFont,
            int minFont, int maxFreq, int minFreq) {
        //Extract the freq value from the pair.
        Integer value = p.getValue();
        //Adjust the value.
        value -= minFreq;
        //Convert to a double.
        Double val = value.doubleValue();
        //Scale the value to 0 and 1 and then scale to font size range.
        val /= maxFreq - minFreq;
        val *= maxFont - minFont;
        //Convert back to an integer.
        int newVal = val.intValue();
        newVal += minFont;
        //Return the calculated font size.
        return newVal;

    }

    /**
     * Writes an HTML header section to the given output stream, including the
     * specified file name and the number of words displayed in the tag cloud.
     *
     * @param file
     *            the name of the file to include in the title and header of the
     *            HTML
     * @param n
     *            the number of words to be displayed in the tag cloud
     * @param out
     *            the output stream to write the HTML header
     * @ensures The output stream contains a well-formed HTML header section
     */
    private static void outputHeader(String file, int n, PrintWriter out) {

        //Outputting the header section
        out.println("<html>");
        out.println("<head>");
        out.println(" <title>Top " + n + " words in " + file + "</title>");
        out.println(
                " <link href=\"https://cse22x1.engineering.osu.edu/2231/web-sw2/assignments/projects/tag-cloud-generator/data/tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        out.println(" <link href=\"tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>Top " + n + " words in " + file + "</h2>");
        out.println("<hr>");
        out.println("<div class=\"cdiv\">");
        out.println("<p class=\"cbox\">");

    }

    /**
     * Outputs the body and footer sections of the tag cloud to the given output
     * stream.
     *
     * @param wordCount
     *            a map containing words and their corresponding counts
     * @param sortedList
     *            a list of map entries sorted in ascending order by their
     *            values, from the smallest to the largest
     * @param out
     *            the output stream used to write the HTML body and footer
     * @clears smFont, smCount
     * @ensures The output stream contains a well-formatted HTML body and footer
     *          suitable for rendering a tag cloud
     */

    private static void outputBodyAndFooter(Map<String, Integer> wordCount,
            List<Entry<String, Integer>> sortedList, PrintWriter out) {

        final int minFont = 11;
        final int maxFont = 48;
        int minFreq = Collections.min(wordCount.values());
        int maxFreq = Collections.max(wordCount.values());

        //Looping through all the elements and outputting the entries needed for
        //the html tag cloud.
        for (int i = 0; i < sortedList.size(); i++) {
            Entry<String, Integer> entry = sortedList.get(i);
            int fontSize = convertFrequencyToFontSize(entry, maxFont, minFont, maxFreq,
                    minFreq);
            out.println("<span style=\"cursor:default\" class=\"f" + fontSize + "\""
                    + " title=\"count: " + entry.getValue() + "\">" + entry.getKey()
                    + "</span>");
        }
        out.println("</p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");

    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {

        //Ask the user to enter the name of the input file.
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter the name of an input file: ");
        String inputFile;
        try {
            //Read the input file name from the user.
            inputFile = console.readLine();
        } catch (IOException e) {
            System.err.println("ERROR: Cannot read keyboard input.");
            return;
        }

        //Ask the user to enter the name of the output file.
        System.out.print("Enter the name of an output file: ");
        String outputFile;
        try {
            //Read the output file name from the user.
            outputFile = console.readLine();
        } catch (IOException e) {
            System.err.println("ERROR: Cannot read keyboard input.");
            return;
        }

        //Ask the user for the number of words.
        System.out.print("Enter the number of words in tag cloud: ");
        int numberOfWords;
        try {
            numberOfWords = Integer.parseInt(console.readLine());
        } catch (IOException e) {
            System.err.println("ERROR: Cannot read keyboard input.");
            return;
        } catch (NumberFormatException e) {
            System.err.println("ERROR: Value entered is not a valid integer");
            return;
        }

        //Create file input and output streans.
        BufferedReader fileIn;
        try {
            fileIn = new BufferedReader(new FileReader(inputFile));
        } catch (IOException e) {
            System.err.println("ERROR: Input file could not be opened.");
            return;
        }

        PrintWriter fout;
        try {
            fout = new PrintWriter(new BufferedWriter(new FileWriter(outputFile)));
        } catch (IOException e) {
            System.err.println("ERROR: Cannot open output file!");
            return;
        }
        //Create a map of the word pairs from the user input file.
        Map<String, Integer> wordCount = mapWordsToCounts(fileIn);
        //Check if the map has words and the number is bigger than 0.
        if (wordCount.size() > 0 && numberOfWords > 0) {
            topNFrequentWords(wordCount, numberOfWords);
            //Create a sorted list in alphabetical order.
            List<Map.Entry<String, Integer>> sortedListAlphabetic = mapToSortedList(
                    wordCount);

            //Output the html header,body, and footer to the output file.
            outputHeader(inputFile, numberOfWords, fout);
            outputBodyAndFooter(wordCount, sortedListAlphabetic, fout);
        } else if (wordCount.size() > 0) {
            //Handle when the number entered is 0.
            System.err.println("ERROR: Number of words in tag cloud must be > 0.");
        } else {
            //Handle when input file is empty.
            System.err.println("ERROR: Input file is empty, cannot generate tag cloud");
        }

        //Close the input and output and streams.
        try {
            console.close();
        } catch (IOException e1) {
            System.err.println("ERROR: Keyboard input stream could not be closed.");
            return;
        }
        try {
            fileIn.close();
        } catch (IOException e) {
            System.err.println("ERROR: Input file could not be closed.");
            return;
        }

        fout.close();
    }

}
