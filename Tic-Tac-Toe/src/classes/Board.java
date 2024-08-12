package classes;

///Mohamed Jama
/// this final project is a tic tac toe game where there are two players just a like a normal tic tac toe game
/// there are many different methods in this code. Some examples include a method to see if the board is full,
///a method to see if there is a winner, a method to alternate between the two players and many more.

public class Board {

  private char[][] board; 
  private char currentPlayer;
	
	   /**
   * This is the constructor for the class. It initializes the board so all cells in the board are equal to '-'. The currentPlayer 
   * is initialized to 'x'.
   *
   */
   public Board() {
      board = new char[3][3]; //// makes a 3 by 3 board
     int m = 0;
     while (m < board.length) {
    int j = 0;
    while (j < board[m].length) {
       board[m][j] = '-';
    j++;
    }
    m++;
     }
     currentPlayer = 'x';/// player number one starts or in this case 'x' character//
   }
	
	  /**
   * The method outputs the board in the following format. First the message "Current board" underligned is printed. 
   * Then the content of the board is printed as a 3 by 3 matrix.
   */
   public void print() {///in charge of the board///
     System.out.println("Current Board"); ///displays the current board after each 'x' or 'o'
      System.out.println("-------------");
      int m = 0;
      while (m < board.length) {
        int j = 0;///used m and j for loop because of initials
       while (j < board[m].length) {
          System.out.print(board[m][j] + "  ");
       j++;
       }
    System.out.println("");
    m++;
      }
   }
     
	
	  /**
   * The method checks if all the positions on the board have been played.
   *
   * @return true if all the cells in the board are different than '-', false otherwise.
   */
   public boolean isFull() {//// used to see if the board is empty
      int m = 0;
      while (m < board.length) {
         int j = 0;
         while (j < board[m].length) {
            if (board[m][j] == '-') {
      return false;/// if all the cells on the board are = to '-'
   }
   j++;
         }
         m++;
      }
      return true;///if all the cells on the board are not equal to '-'
   }
	
	  /**
   * The method checks if there is a winner.
   *
   * @return true if either a column, a row or a diagonal is filled by the same character and the character is different than '-',
   * false otherwise.
   */
   public boolean isWin() {///used to see if either 'x' or 'o' won at either in a row, columns, or even diagonal
       if (checkRows() || checkColumns() || checkDiagonals()) {
 return true;
  } else {
    return false;
  }
   }
  
	  /**
   * The method checks if at least one row is occupied by the same player.
   *
   * @return true if any row is filled by the same character and the character is different than '-',
   * false otherwise.
   */
   private boolean checkRows() {///sees if at least one row is used by the same player either 'x' char ot 'o' char///
      int m = 0;
    while (m < board.length) {
       if (board[m][0] != '-' && board[m][0] == board[m][1] && board[m][0] == board[m][2]) {
       return true;
       }
    m++;
   }
   return false;
   }
	 
	  /**
   * The method checks if at least one column is occupied by the same player.
   *
   * @return true if any column is filled by the same character and the character is different than '-',
   * false otherwise.
   */
   private boolean checkColumns() {///sees if at least one column is used by the same player either 'x' char ot 'o' char///
    int m = 0;
    while (m < board.length) {
       if (board[0][m] != '-' && board[0][m] == board[1][m] && board[0][m] == board[2][m]) {
       return true;
       }
    m++;
   }
   return false;
   }
	
	  /**
   * The method checks if at least one diagonal is occupied by the same player.
   *
   * @return true if any diagonal is filled by the same character and the character is different than '-',
   * false otherwise.
   */
   private boolean checkDiagonals() {///sees if at least one diagonal spot is used by the same player either 'x' char ot 'o' char///
      if (board[0][0] != '-' && board[0][0] == board[1][1] && board[0][0] == board[2][2]) {
          return true;
      }
      if (board[0][2] != '-' && board[0][2] == board[1][1] && board[0][2] == board[2][0]) {
          return true;
      }
      return false;
   }
   
   /**
   * The method changes the currentPlayer. If the currentPlayer is 'x', it changes to 'o'.  
   * If the currentPlayer is 'o', it changes to 'x'. 
   *
   */
   public void changePlayer() { ///altenates between 'x' and 'o' charachters.
         if (currentPlayer == 'x') {
          currentPlayer = 'o';
       } else 
       {
      currentPlayer = 'x';
       }
   }
   
	  /**
   * The method attempts to set the cell on the position indicated by the row and column given to the currentPlayer value if
   * the cell is unoccupied (equal to '-') in which case returns true. If the position is occupied (not equal to '-') the cell
   * remains unchanged and the method returns false.
   *
   */
   public boolean setRowCol(int row, int col) {////sees if the row or column is either occupied or not
		if (row >= 1 && row <= 3 && col >= 1 && col <= 3) {
          if (board[row - 1][col - 1] == '-') {
              board[row - 1][col - 1] = currentPlayer;
              return true;///if the row or column is occupied returns '-' and continues///
          }
      }
      return false;//if not occupied stays the same///
   }
   
   public char getCurrentPlayer() { ///returns the current player///
      return currentPlayer;
  }
}
