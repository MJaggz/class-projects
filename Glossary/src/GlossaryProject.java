import java.util.Comparator;

import components.map.Map;
import components.map.Map2;
import components.queue.Queue;
import components.queue.Queue2;
import components.set.Set;
import components.set.Set2;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * The main idea of this program is to take a text file that has definitions set
 * them on their own line and their definitions right under them and make a nice
 * formatted glossary index page. Each term should lead to its own respective
 * page. If a term has another term inside of its definition, it should lead to
 * that term if the term is part of the text file.
 *
 * @author Mohamed Jama
 *
 */

public final class GlossaryProject {
    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private GlossaryProject() {
    }

    /**
     * Compare {@code String}s in alphabetical order.
     */
    private static class StringLT implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
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
    public static String nextWordOrSeparator(String text, int position,
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
     * Returns and creates a glossary as a map from a text file where each term
     * has its respected definition reads each term and definition and if the
     * input is not at the end of its string, it will continue. The text file
     * should have the term on top with nothing after and the definition right
     * under it.
     *
     * @param inputFile
     *            which is a file that has the words with its definitions
     * @return a map where each term is matched with its definition.
     */
    public static Map<String, String> makeGlossaryMap(String inputFile) {

        //Initialize a SimpleReader to read the input file.
        SimpleReader in = new SimpleReader1L(inputFile);
        //Also initialize an empty map that will store the glossary.
        Map<String, String> glossaryMapping = new Map2<>();
        //While loop that will continue until it reaches the end of the input
        //file.
        while (!in.atEOS()) {
            //Store the first line as the term.
            String inputTerm = in.nextLine();
            //Store the second line as the definition.
            String inputDefinition = in.nextLine();
            //Store if there are more than one lines of definitions for terms.
            String currentLinePos = inputDefinition;

            //While loop to continue until there is a blank line and there
            //we are at the end of the string.
            while (currentLinePos.length() != 0 && !in.atEOS()) {
                currentLinePos = in.nextLine();
                if (currentLinePos.length() != 0) {
                    //If the line is not blank, add it to inputDefenition in the
                    //order that it was in.
                    inputDefinition = inputDefinition + " " + currentLinePos;
                }
            } //If the glossary term is not in the map and is not empty, add it
              //to the map with its defenition pair.
            if (!glossaryMapping.hasKey(inputTerm) && inputTerm.length() > 0) {
                glossaryMapping.add(inputTerm, inputDefinition);
            }
        }
        //Close SimpleReader and return the glossary map.
        in.close();
        return glossaryMapping;
    }

    /**
     * This method takes the glossary map and stores them in a queue
     * alphabetically by using the StringLT comparator method.
     *
     * @param glossary
     *            which will be a map that will have terms with their associated
     *            definition.
     *
     * @return a queue that will have all the terms and definitions in
     *         alphabetical order.
     */
    public static Queue<String> sortGlossaryTerms(
            Map<String, String> glossary) {

        //Make a new queue that will have the glossary terms and definitions
        //in alphabetical order.
        Queue<String> alhabetizedTerms = new Queue2<>();
        //Make a new comparator named placeInOrder that will help with placing
        //the terms in order.
        Comparator<String> placeInOrder = new StringLT();

        //A for each loop that will iterate over the glossary map.
        for (Map.Pair<String, String> pair : glossary) {
            //Use enqueue to add each term into the alphabetizedTerms queue.
            alhabetizedTerms.enqueue(pair.key());
        }
        //Sort the terms in the queue in alphabetical order and return that
        //queue.
        alhabetizedTerms.sort(placeInOrder);
        return alhabetizedTerms;
    }

    /**
     * This method outputs the opening tags in the generated HTML file for the
     * glossary's index page.
     *
     * @param folder
     *            in eclipse and my files where the index page will be saved.
     * @param terms
     *            that will be in a queue that will hold all the terms.
     *
     *
     */
    public static void makeTheIndexPage(String folder, Queue<String> terms) {

        //Make a path to the index file.
        String indexFilePath = folder + "/index.html";

        ///Initialize a SimpleWriter that will write.
        SimpleWriter indexPageOut = new SimpleWriter1L(indexFilePath);
        //Print the html tags that will be needed.
        indexPageOut.println("<!DOCTYPE html>");
        indexPageOut.println("<html>");
        indexPageOut.println("<head>");
        //Name of the WebSite will be called "Project 10: Glossary"
        indexPageOut.println("<title>Project 10: Glossary</title>");
        indexPageOut.println("</head>");
        indexPageOut.println("<body>");
        //Make a header for the glossary and centr it
        indexPageOut.println("<h1 style=\"text-align:center;\">Glossary</h1>");
        indexPageOut.println("<hr>");
        //A little glossary instructions text.
        indexPageOut.println(
                "<p>Click on a term to learn about its definition!</p>");

        //Make a list.
        indexPageOut.println("<ul>");
        //A for each loop that will iterate through each term in the queue.
        for (String term : terms) {
            //Make a path for each term in the queue
            String termHtmlPath = term + ".html";
            //Add each term to the list.
            indexPageOut.println("<li><h3><a href=\"" + termHtmlPath + "\">"
                    + term + "</a></h3></li>");
        }
        //Close the list, html tags, and SimpleWriter.
        indexPageOut.println("</ul>");
        indexPageOut.println("</body>");
        indexPageOut.println("</html>");
        indexPageOut.close();
    }

    /**
     * This method takes each term in the queue and creates an html file sort of
     * the same as the previous method and creates individual pages for each
     * term with its definition with hyperlink of other terms if they are in the
     * definition of other terms.
     *
     * @param folder
     *            in eclipse and my files where the index page will be saved.
     * @param terms
     *            that will be in a queue.
     * @param glossary
     *            which will be a map of the terms with their definitions.
     *
     */
    public static void makeTheTermPages(String folder, Queue<String> terms,
            Map<String, String> glossary) {
        //Start with a for each loop to iterate for each term in the queue of
        //terms.
        for (String term : terms) {
            //Make a string that will take the current definition for the term
            //that I am currently non.
            String inputDefinition = glossary.value(term);
            //Sort of similar to the last method, make a path for each term in
            //the queue.
            String termHtmlPath = folder + "/" + term + ".html";
            //Initialize a SimpleWriter that will write.
            SimpleWriter indexPageOut = new SimpleWriter1L(termHtmlPath);
            ///Just like the previous method, print the html tags that will be
            //needed.
            indexPageOut.println("<!DOCTYPE html>");
            indexPageOut.println("<html>");
            indexPageOut.println("<head>");
            //Make the title of the html page to whatever the term is.
            indexPageOut.println("<title>" + term + "</title>");
            indexPageOut.println("</head>");
            indexPageOut.println("<body>");
            //Make sure that the term is in red boldfaced italics.
            indexPageOut.println(
                    "<h1 style=\"color:red;\"><i>" + term + "</i></h1>");
            indexPageOut.println("<p><strong>Definition:</strong></p>");

            ///Use the definitionWithLinks method to print the term's
            //definition.
            definitionWithLinks(inputDefinition, indexPageOut, glossary);
            indexPageOut.println("<hr>");
            //Make a link that will take you back to the index page.
            indexPageOut.println(
                    "<p>Back to <a href=\"index.html\">Index Page</a></p>");
            //Close indexPageOut and the closing tags.
            indexPageOut.println("</body>");
            indexPageOut.println("</html>");
            indexPageOut.close();
        }
    }

    /**
     * This is a small helper method that will hold characters that are used as
     * separators.
     *
     * @return a set of separators.
     */
    public static Set<Character> setOfSeparators() {
        //Create a new set that will hold the separator characters.
        Set<Character> separatorSet = new Set2<>();
        //Add separators to the set such as a space, comma, period, question
        //mark and other separators.
        separatorSet.add(' ');
        separatorSet.add(',');
        separatorSet.add('.');
        separatorSet.add('!');
        separatorSet.add('?');
        separatorSet.add('/');
        separatorSet.add('\\');

        //Return the set of separators.
        return separatorSet;
    }

    /**
     * This method prints the definition to a SimpleWriter with links.
     *
     * @param definition
     *            the definition of the terms that will be printed.
     * @param out
     *            the output stream.
     * @param glossary
     *            which is the map that will have the terms with their
     *            definition.
     *
     *
     */
    public static void definitionWithLinks(String definition, SimpleWriter out,
            Map<String, String> glossary) {

        //Make a set of separators.
        Set<Character> separatorSet = setOfSeparators();
        //Make a block quote section for the definition.
        out.print("<blockquote>");
        int i = 0;
        //While i is less than the length of definition, get the next word or
        //Separator form its position.
        while (i < definition.length()) {
            String nextWordOrString = nextWordOrSeparator(definition, i,
                    separatorSet);
            //Check to see if the word or string is not a separator.
            if (!separatorSet.contains(nextWordOrString.charAt(0))) {
                //If the term exists in the glossary, hyperlink it to the term's
                //Definition.
                if (glossary.hasKey(nextWordOrString)) {
                    out.print("<a href=\"" + nextWordOrString + ".html\">");
                    out.print(nextWordOrString);
                    out.print("</a>");
                } else {
                    //If the word is not a term in the glossary, print it like
                    //normal.
                    out.print(nextWordOrString);
                }
                //Print out the separators.
            } else {
                out.print(nextWordOrString);
            }
            //Move to the next string or word.
            i += nextWordOrString.length();
        }
        //Close the block quote for the definition.
        out.println("</blockquote>");

    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        //Made a SimpleReader and SimpleWriter to ask and store the user's info.
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        //Ask the user for an input file.
        out.print("Please enter an input file: ");
        //Store the file as inputFileName.
        String inputFileName = in.nextLine();
        //Ask the user right after for an output folder to store the glossary,
        out.print("Please enter an output folder(must already be made): ");
        //Store the output folder name as outputFolderName
        String outputFolderName = in.nextLine();

        Map<String, String> glossaryMapFile = makeGlossaryMap(inputFileName);
        Queue<String> glossaryTermsAlphabetic = sortGlossaryTerms(
                glossaryMapFile);

        //Make an index html page for all the glossary terms which will link
        //them all to their definitions.
        makeTheIndexPage(outputFolderName, glossaryTermsAlphabetic);

        //Make a page for each of the terms that will show their definitions.
        makeTheTermPages(outputFolderName, glossaryTermsAlphabetic,
                glossaryMapFile);

        //Close SimpleReader and SimpleWriter.
        in.close();
        out.close();
    }

}
