package classes;
/**
 * A simple Java arithmetic program
 * @author Mohamed Jama
 * @version 2023/29/08
 **/
import java.util.Scanner;


public class SimpleArithmaticProgram {
	public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter the first number: ");
        String firstNumber = keyboard.nextLine();
        System.out.print("Enter the second number: ");
        String secondNumber = keyboard.nextLine();
        int value = Integer.parseInt(firstNumber);
        int value2 = Integer.parseInt(secondNumber);
        int sum = value + value2;
        System.out.println(value + " + " + value2 + " = " +  sum); 
        int sum2 = value - value2;
        System.out.println(value + " - " + value2 + " = " + sum2);
        int sum3 = value * value2;
        System.out.println(value + " * " + value2 + " = " + sum3);
        int sum4 = value / value2;
        System.out.println(value + " / " + value2 + " = " + sum4);
        int sum5 = value % value2;
        System.out.println(value + " % " + value2 + " = " + sum5);
        int sum6 = (value + value2) / 2;
        System.out.println("The average of your two numbers is: " + sum6 );
        
        keyboard.close(); 
   }
}


