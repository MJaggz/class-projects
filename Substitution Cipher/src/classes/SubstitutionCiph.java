package classes;
/**
 * This final project builds on the lab that was due last weekwith the whole substitution cipher.
 * For this project you are asking the user encode a file or decode a file. You prompt the user to enter an input file and
 * an output file. After tgat you ask for a shift amount. After everything the file should be shifted by the shift amount
 * picked by the user.
 * @author Mohamed Jama
 * @version 11/24/23
 */
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

public class SubstitutionCiph {
	/**
     * Private constants used to shift characters for the substitution cipher.
     */
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";

    /**
     * Constructs a new String where each letter in the String input is shifted
     * by the amount shift to the right, preserving whether the original
     * character was uppercase or lowercase. For example, the String "ABC" with
     * shift 3 would cause this method to return "DEF". A negative value should
     * shift to the left. For example, the String "ABC" with shift -3 would
     * cause this method to return "XYZ". Punctuation, numbers, whitespace and
     * other non-letter characters should be left unchanged. NOTE: For full
     * credit you are REQUIRED to use a StringBuilder to build the String in
     * this method rather than using String concatenation.
     *
     * @param input
     *            String to be encrypted
     * @param shift
     *            Amount to shift each character of input to the right
     * @return the encrypted String as outlined above
     */
    public static String shift(String input, int shift) {
       char[] resultChars = new char[input.length()];
    for (int i = 0; i < input.length(); i++) {
        char c = input.charAt(i);

        if (Character.isUpperCase(c)) {///shift by amount if character is uppercase///
            int idx = UPPERCASE.indexOf(c);
            idx = (26 + idx + shift) % 26;
            c = UPPERCASE.charAt(idx);
        } else if (Character.isLowerCase(c)) { ///shift by amount if character is lowercase///
            int idx = LOWERCASE.indexOf(c);
            idx = (26 + idx + shift) % 26;
            c = LOWERCASE.charAt(idx);
        }
        resultChars[i] = c;
    }

    return new String(resultChars); ///return the string///
    }

    /**
     * Displays the message "promptMsg" to the user and reads the next full line
     * that the user enters. If the user enters an empty string, reports the
     * error message "ERROR! Empty Input Not Allowed!" and then loops,
     * repeatedly prompting them with "promptMsg" to enter a new string until
     * the user enters a non-empty String
     *
     * @param in
     *            Scanner to read user input from
     * @param promptMsg
     *            Message to display to user to prompt them for input
     * @return the String entered by the user
     */
    public static String promptForString(Scanner in, String promptMsg) {
        String input = "";
    while (input.trim().length() == 0) {
        System.out.print(promptMsg);
        input = in.nextLine().trim();
        if (input.length() == 0) {
            System.out.println("ERROR! Empty Input Not Allowed!");///print if the imput is empty///
        }
    }
            return input;
    }

    /**
     * Opens the file inFile for reading and the file outFile for writing,
     * reading one line at a time from inFile, shifting it the number of
     * characters given by "shift" and writing that line to outFile. If an
     * exception occurs, must report the error message: "ERROR! File inFile not
     * found or cannot write to outFile" where "inFile" and "outFile" are the
     * filenames given as parameters.
     *
     * @param inFile
     *            the file to be transformed
     * @param outFile
     *            the file to write the transformed output to
     * @param shift
     *            the amount to shift the characters from inFile by
     * @return false if an exception occurs and the error message is written,
     *         otherwise true
     */
    public static boolean transformFile(String inFile, String outFile,
            int shift) {
         try (Scanner fileScanner = new Scanner(new File(inFile));
         PrintWriter writer = new PrintWriter(outFile)) {

        while (fileScanner.hasNextLine()) {
            String line2 = fileScanner.nextLine();
            String shiftedLine1 = shift(line2, shift); 
            writer.write(shiftedLine1); 
        }
        return true; 

    } catch (FileNotFoundException e) { ///catch if the file name is not found or is typed in wrong///
        System.out.println("ERROR! File " + inFile + " not found or cannot write to " + outFile);
        return false; 
    }

    }

    /**
     * Prompts the user to enter a single character choice. The only allowable
     * values are 'E', 'D' or 'Q'. All other values are invalid, including all
     * values longer than one character in length, however the user is allowed
     * to enter values in either lower or upper case. If the user enters an
     * invalid value, the method displays the error message "ERROR! Enter a
     * valid value!" and then prompts the user repeatedly until a valid value is
     * entered. Returns a single uppercase character representing the user's
     * choice.
     *
     * @param in
     *            Scanner to read user choices from
     * @return the user's choice as an uppercase character
     */
    public static char getChoice(Scanner in) {
     char choice = ' ';
      while (choice != 'E' && choice != 'D' && choice != 'Q') {
        System.out.print("Enter your choice: "); ///Prompt the user to enter one of the three letters//
        String input = in.nextLine().toUpperCase();
      if (input.length() > 0) {
        choice = input.charAt(0);
      } else {
        System.out.println("ERROR! Enter a valid value!");/// State if user does not enter one of the three letters///
       }
       if (choice != 'E' && choice != 'D' && choice != 'Q') {
        System.out.println("ERROR! Enter a valid value!");/// State if user does not enter one of the three letters///
    }
}
return choice;
    }

    /**
     * Displays the menu of choices to the user.
     */
    public static void displayMenu() {
        System.out.println("[E]ncode a file"); ///Prompt the user to enter 'E' if they want to encode a file///
        System.out.println("[D]ecode a file");  ///Prompt the user to enter 'D' if they want to Dncode a file///
        System.out.println("[Q]uit");         /// Prommpt the user to enter 'Q' If they want to quit///
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        displayMenu();

        char choice;
       do {
        choice = getChoice(in);
        if (choice == 'E' || choice == 'D') {
            System.out.print("Enter an input file: ");///Prompt the user for an input file name///
            String inputFileName = in.nextLine();

            System.out.print("Enter an output file: ");///Prompt the user for an output pile name///
            String outputFileName = in.nextLine();

            System.out.print("Enter a shift ammount: ");///Prompt the user for a shift amount and store it as int shift///
            int shift = in.nextInt();
            in.nextLine(); 

            boolean true2;
            if (choice == 'E') {
                true2 = transformFile(inputFileName, outputFileName, shift);
            } else {
                true2 = transformFile(outputFileName, inputFileName, -shift);
            }

            if (true2) {
                System.out.println("Finished writing to file.");///print if the file is finished///
            }
        }

    } while (choice != 'Q');

    System.out.println("Goodbye!");///end witha goodbye statement///
        in.close();
    }

}

