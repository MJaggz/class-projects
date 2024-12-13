import java.util.Comparator;

import components.map.Map;
import components.map.Map.Pair;
import components.map.Map1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine2;

/**
 * This project is a project that when given an input file will generate a tag
 * cloud html file using OSU's Components.
 *
 * @author Mohamed Jama & Emanuel Messele
 *
 */
public final class TagCloudGenerator {

    /**
     * A comparator for values in Map.Pair<String,Integer> that will compare in
     * alphabetical order.
     */
    private static class MapIntegerLT implements Comparator<Map.Pair<String, Integer>> {

        @Override
        public int compare(Map.Pair<String, Integer> o1, Map.Pair<String, Integer> o2) {
            return o1.value().compareTo(o2.value());
        }

    }

    /**
     * A comparator for keys in Map.Pair<String,Integer> that will compare in
     * alphabetical order.
     */
    private static class MapStringLT implements Comparator<Map.Pair<String, Integer>> {

        @Override
        public int compare(Map.Pair<String, Integer> o1, Map.Pair<String, Integer> o2) {
            return o1.key().toLowerCase().compareTo(o2.key().toLowerCase());
        }

    }

    /**
     * Comparator for comparing String keys of Map.Pair<String, Integer>.
     */
    private static final Comparator<Map.Pair<String, Integer>> KEYORDERING = new MapStringLT();

    /**
     * Comparator for comparing Integer values of Map.Pair<String, Integer>.
     */
    private static final Comparator<Map.Pair<String, Integer>> VALORDERING = new MapIntegerLT();

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
    private static Map<String, Integer> mapWordsToCounts(String file) {
        //Initialize the input reader for the file and a new map that will hold
        //the word count.
        SimpleReader input = new SimpleReader1L(file);
        Map<String, Integer> map = new Map1L<>();

        //Initialize a string of characters that are considered separators.
        String separatorString = " \t\n\r,-.!?[]';:/()";
        Set<Character> separatorSet = new Set1L<>();
        generateElements(separatorString, separatorSet);

        //While loop that will continue until it reaches the end of the input
        //file.
        while (!input.atEOS()) {
            String line = input.nextLine().toLowerCase();
            int i = 0;
            //Check each line for words and separators.
            while (i < line.length()) {
                String wordSeper = nextWordOrSeparator(line, i, separatorSet);
                //If the string found is a word, add it to the map.
                if (!separatorSet.contains(wordSeper.charAt(0))) {
                    //Add the word to the map with the count of 1.
                    if (!map.hasKey(wordSeper)) {
                        map.add(wordSeper, 1);
                    } else {
                        //If the word is already in the map increment it.
                        int newValue = map.value(wordSeper) + 1;
                        map.replaceValue(wordSeper, newValue);
                    }
                }
                i += wordSeper.length();
            }
        }

        //close the input and return the map.
        input.close();
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

        //Creating a temporary map along with a comparator to compare the values
        //of pairs and using a sortingMachine to sort the pairs.
        Map<String, Integer> temp = wordCount.newInstance();
        SortingMachine<Map.Pair<String, Integer>> sorterCount = new SortingMachine2<>(
                VALORDERING);

        //A for each loop to add each pair to the sorting machine and then
        //changing it to extraction mode.
        for (Map.Pair<String, Integer> pair : wordCount) {
            sorterCount.add(pair);
        }
        sorterCount.changeToExtractionMode();

        //Removing the least frequent words and then adding the top n
        //frequent words.
        for (int i = 0; i < wordCount.size(); i++) {
            Map.Pair<String, Integer> removedN = sorterCount.removeFirst();
            if (i >= wordCount.size() - n) {
                //Adding the top n frequent words to temp.
                temp.add(removedN.key(), removedN.value());
            }
        } //Transferring temp to wordCount.
        wordCount.transferFrom(temp);

    }

    /**
     * Converts the key-value pairs from the map into a sorting machine,
     * restoring the map to its original state.
     *
     * @param m
     *            a map containing key-value pairs to be added to the sorting
     *            machine.
     *
     * @return a sorting machine in extraction mode containing all the entries
     *         originally in the map.
     *
     * @restores m.
     *
     * @ensures sorting machine will hold all the values in m.
     */
    private static SortingMachine<Pair<String, Integer>> mapToSortingMachine(
            Map<String, Integer> m) {

        //Creating a sortingMachine along with a temp value that will restore
        //the map.
        SortingMachine<Pair<String, Integer>> sorterMach = new SortingMachine2<>(
                KEYORDERING);
        Map<String, Integer> temp = m.newInstance();
        //A while loop to iterate through the map and then sorting the values
        //in sm and the temp map.
        while (m.size() > 0) {
            Map.Pair<String, Integer> p = m.removeAny();
            sorterMach.add(p);
            String stringKey = p.key();
            Integer n = p.value();
            temp.add(stringKey, n);
        }
        //Restoring m.
        m.transferFrom(temp);
        //Changing sm to extraction mode so it will now hold the values from m.
        sorterMach.changeToExtractionMode();
        return sorterMach;

    }

    /**
     * Determines the smallest value in a map with string keys and integer
     * values.
     *
     * @param m
     *            a map containing string keys and integer values.
     * @return the smallest integer value in the map.
     */
    private static int findMinimumValue(Map<String, Integer> m) {

        //Creating a sortingMachine and then a temp value that will restore the
        //map.
        SortingMachine<Pair<String, Integer>> sorterMach = new SortingMachine2<>(
                VALORDERING);
        Map<String, Integer> temp = m.newInstance();
        //A while loop to iterate through the map and then sorting the values
        //in sm and the temp map.
        while (m.size() > 0) {
            Map.Pair<String, Integer> p = m.removeAny();
            sorterMach.add(p);
            String stringKey = p.key();
            Integer n = p.value();
            temp.add(stringKey, n);
        }
        m.transferFrom(temp);
        sorterMach.changeToExtractionMode();
        //Returning the first value since that will be the minimum value.
        return sorterMach.removeFirst().value();

    }

    /**
     * Determines the largest value in a map with string keys and integer
     * values.
     *
     * @param m
     *            a map containing string keys and integer values.
     * @return the largest integer value in the map.
     */
    private static int findMaximumValue(Map<String, Integer> m) {
        //Creating a sortingMachine and then a temp value that will restore the
        //map.
        SortingMachine<Pair<String, Integer>> sorterMach = new SortingMachine2<>(
                VALORDERING);
        Map<String, Integer> temp = m.newInstance();
        //A while loop to iterate through the map and then sorting the values
        //in sm and the temp map.
        while (m.size() > 0) {
            Map.Pair<String, Integer> p = m.removeAny();
            sorterMach.add(p);
            String stringKey = p.key();
            Integer n = p.value();
            temp.add(stringKey, n);
        }
        m.transferFrom(temp);
        sorterMach.changeToExtractionMode();

        // same as last method but just getting to the last number
        int number = 0;
        while (sorterMach.size() >= 1) {
            number = sorterMach.removeFirst().value();
        }

        // returns last number in the map (biggest number)
        return number;
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
    private static int convertFrequencyToFontSize(Pair<String, Integer> p, int maxFont,
            int minFont, int maxFreq, int minFreq) {

        //Extract the freq value from the pair.
        Integer value = p.value();
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
     * Converts frequency values in a map to corresponding font sizes between a
     * defined minimum and maximum font size.
     *
     * @param m
     *            the map containing integer frequency values
     * @restores m
     * @ensures the returned map contains the same keys as m but with values
     *          converted to font sizes.
     * @return a map with font sizes corresponding to the frequency values in m.
     */
    private static Map<String, Integer> mapToFontSizes(Map<String, Integer> m) {

        //Setting the max and min font along with the frequency.
        final int maxFont = 48;
        final int minFont = 11;
        int maxVal = findMaximumValue(m);
        int minVal = findMinimumValue(m);

        Map<String, Integer> mapToFontSizes = m.newInstance();
        Map<String, Integer> temp = m.newInstance();

        while (m.size() > 0) {
            //Take a pair and change the value of the pair to the font size.
            Pair<String, Integer> p = m.removeAny();
            int newVal = convertFrequencyToFontSize(p, maxFont, minFont, maxVal, minVal);
            temp.add(p.key(), p.value());
            //Then reinsert it.
            mapToFontSizes.add(p.key(), newVal);
        }
        m.transferFrom(temp);

        return mapToFontSizes;
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
    private static void outputHeader(String file, int n, SimpleWriter out) {

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
     * Writes the body and footer sections of the HTML tag cloud to the given
     * output stream, including formatted spans for words and their respective
     * counts and font sizes.
     *
     * @param smFont
     *            a sorting machine containing the words and their corresponding
     *            font sizes
     * @param smCount
     *            a sorting machine containing the words and their frequency
     *            counts
     * @param out
     *            the output stream to write the HTML body and footer
     * @clears smFont, smCount
     * @ensures The output stream contains a well-formatted HTML body and footer
     *          for the tag cloud
     */

    private static void outputBodyAndFooter(SortingMachine<Pair<String, Integer>> smFont,
            SortingMachine<Pair<String, Integer>> smCount, SimpleWriter out) {

        //Looping through all the elements
        while (smFont.size() > 0) {
            //Removing the first word font pair
            Pair<String, Integer> p1 = smFont.removeFirst();
            //Removing the first word count pair.
            Pair<String, Integer> p2 = smCount.removeFirst();
            out.println("<span style=\"cursor:default\" class=\"f" + p1.value() + "\""
                    + " title=\"count: " + p2.value() + "\">" + p1.key() + "</span>");
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
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        //Asking the user for an input and output file name and storing them as
        //strings.
        out.print("Enter the name of an input file: ");
        String inputFile = in.nextLine();
        out.print("Enter the name of an output file: ");
        String outputFile = in.nextLine();
        out.print("Enter the number of words in tag cloud: ");
        int numberOfWords = in.nextInteger();

        //Creating a map of the words and count pairs from the user file.
        Map<String, Integer> wordCount = mapWordsToCounts(inputFile);
        SimpleWriter fout = new SimpleWriter1L(outputFile);

        if (wordCount.size() > 0) {
            topNFrequentWords(wordCount, numberOfWords);
            //Generating a map from counts in wordCount to get the font sizes
            //for the words.
            Map<String, Integer> wordFont = mapToFontSizes(wordCount);

            //Creating a sm to hold values in wordCount and wordFont.
            SortingMachine<Pair<String, Integer>> sortCount = mapToSortingMachine(
                    wordCount);
            SortingMachine<Pair<String, Integer>> sortFont = mapToSortingMachine(
                    wordFont);

            //outputting everything to an html file output.
            outputHeader(inputFile, numberOfWords, fout);
            outputBodyAndFooter(sortFont, sortCount, fout);
        } else {
            out.println("Error: Input file is empty, cannot generate tag cloud");
        }

        //Close the input and output streams.
        in.close();
        out.close();
        fout.close();
    }

}
