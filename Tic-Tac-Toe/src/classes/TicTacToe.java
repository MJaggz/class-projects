package classes;
import java.util.Scanner;
import java.util.Arrays;

public class TicTacToe {
   public static void main(String[] args) {
     Scanner keyboard = new Scanner(System.in);///import scanner and name it as keyboard
        Board board = new Board();
        boolean gameFinished = false;

        while (!gameFinished) {///while the game is stilll playing//
           
           
            char currentPlayer = board.getCurrentPlayer();
          board.print();///prins the board///
            
            System.out.print(currentPlayer + " player: Enter row and column numbers:");
            ///prompt the users to enter what row and column they want to place their character on///
            boolean inputValid = false;
            
            while (!inputValid) {
               
               
                int row = keyboard.nextInt(); /// store the row number
                int col = keyboard.nextInt();///store the column number
                keyboard.nextLine(); //makes a new line

                  //board.print();//test1//

                if (row < 1 || row > 3 || col < 1 || col > 3) {
                    System.out.println("Incorrect cell. Try again!");///print if row and column is above 3 or below 1//
                    System.out.print("Enter row and column numbers:");
                    
                    
                } else if (!board.setRowCol(row, col)) {
                    System.out.println("Incorrect cell. Try again!");
                    System.out.print("Enter row and column numbers:");
                    
                } else {
                    inputValid = true;
                }
            }
     
                   //board.print();//test1//

            if (board.isWin()) {
                board.print();
                
                
                System.out.println(currentPlayer + " player wins!");///output the player who won//
                gameFinished = true;
            } else if (board.isFull()) {
                board.print();
                
                System.out.println("It's a draw!");////output if its a draw///
                gameFinished = true;
            } else {
               
               
                board.changePlayer();
            }
        }
        System.out.println("Goodbye!");///output goodbye statement///
        keyboard.close();
    }
}
