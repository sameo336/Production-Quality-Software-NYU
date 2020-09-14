package edu.nyu.cs.pqs.ps4.connectfour.impl;

import edu.nyu.cs.pqs.ps4.connectfour.api.Player;

/**
 * The Board class is used to represent the structure and current state of the Connect Four Board.
 * This thread-unsafe class follows the Singleton pattern, i.e., only a single instance of the Board
 * class can be created.
 * 
 * @author Anisha Bhatla
 */
class Board {
  private final int totalNumberOfRows = BoardDimensions.ROWS;
  private final int totalNumberOfColumns = BoardDimensions.COLUMNS;
  private int[][] boardState;
  private int[] chipsDroppedInColumn;
  private static Board boardSingletonInstance = null;

  /**
   * Private constructor to prevent instantiation from outside the class
   */
  private Board() {
    boardState = new int[totalNumberOfRows][totalNumberOfColumns];
    chipsDroppedInColumn = new int[totalNumberOfColumns];
  }

  /**
   * Creates a new instance of the Board class if none exists or returns existing instance if one
   * was created earlier
   * 
   * @return instance of Board class
   */
  static Board getInstance() {
    if (boardSingletonInstance == null) {
      boardSingletonInstance = new Board();
    }
    return boardSingletonInstance;
  }

  /**
   * Checks if column number is valid and has space for at least one more chip
   * 
   * @param columnNumber column to be checked
   * @return true if the column number is valid
   */
  boolean isValidColumn(int columnNumber) {
    if (columnNumber < 0 || columnNumber >= totalNumberOfColumns) {
      return false;
    } else if (chipsDroppedInColumn[columnNumber] >= totalNumberOfRows) {
      return false;
    }
    return true;
  }

  /**
   * Adds chip to the given column number
   * 
   * @param columnNumber column index to which chip is to be added
   * @param player Player adding the chip
   * @return true if chip is added to the column successfully
   * @throws IllegalArgumentException if column number is invalid or player instance is null
   */
  boolean addChip(int columnNumber, Player player) {
    if (!isValidColumn(columnNumber)) {
      throw new IllegalArgumentException("Invalid Column Number");
    } else if (player == null) {
      throw new IllegalArgumentException("Player cannot be null");
    } else {
      boardState[chipsDroppedInColumn[columnNumber]][columnNumber] = player.getPlayerID();
      chipsDroppedInColumn[columnNumber]++;
    }
    return true;
  }

  /**
   * Removes chip from the given column number
   * 
   * @param column column index from which chip is to be removed
   * @return true if chip is removed successfully
   * @throws IllegalArgumentException if column number is invalid or no chip exists in the column
   */
  boolean removeChip(int column) {
    if (column < 0 || column >= totalNumberOfColumns) {
      throw new IllegalArgumentException("Invalid Column Number");
    } else if (chipsDroppedInColumn[column] <= 0) {
      throw new IllegalArgumentException("This column has no chip");
    } else {
      boardState[chipsDroppedInColumn[column] - 1][column] = 0;
      chipsDroppedInColumn[column]--;
    }
    return true;
  }

  /**
   * Checks if player has four consecutive chips on the game board, in a single row/column/diagonal
   * 
   * @param player Player whose chips have to be checked for a win
   * @return true if player has four consecutive chips on the game board
   * @throws IllegalArgumentException if the player is null
   */
  boolean checkWin(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null");
    }
    if (checkRowWin(player) || checkColumnWin(player) || checkDiagonallyUpWin(player)
        || checkDiagonallyDownWin(player)) {
      return true;
    }
    return false;
  }

  private boolean checkRowWin(Player player) {
    for (int i = 0; i < totalNumberOfRows; i++) {
      for (int j = 0; j < totalNumberOfColumns - 3; j++) {
        if (boardState[i][j] == player.getPlayerID() && boardState[i][j + 1] == player.getPlayerID()
            && boardState[i][j + 2] == player.getPlayerID()
            && boardState[i][j + 3] == player.getPlayerID()) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean checkColumnWin(Player player) {
    for (int i = 0; i < totalNumberOfColumns; i++) {
      for (int j = 0; j < totalNumberOfRows - 3; j++) {
        if (boardState[j][i] == player.getPlayerID() && boardState[j + 1][i] == player.getPlayerID()
            && boardState[j + 2][i] == player.getPlayerID()
            && boardState[j + 3][i] == player.getPlayerID()) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean checkDiagonallyUpWin(Player player) {
    for (int i = 0; i < totalNumberOfRows - 3; i++) {
      for (int j = 0; j < totalNumberOfColumns - 3; j++) {
        if (boardState[i][j] == player.getPlayerID()
            && boardState[i + 1][j + 1] == player.getPlayerID()
            && boardState[i + 2][j + 2] == player.getPlayerID()
            && boardState[i + 3][j + 3] == player.getPlayerID()) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean checkDiagonallyDownWin(Player player) {
    for (int i = totalNumberOfRows - 1; i > 2; i--) {
      for (int j = 0; j < totalNumberOfColumns - 3; j++) {
        if (boardState[i][j] == player.getPlayerID()
            && boardState[i - 1][j + 1] == player.getPlayerID()
            && boardState[i - 2][j + 2] == player.getPlayerID()
            && boardState[i - 3][j + 3] == player.getPlayerID()) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Resets the game board to its original state by removing all moves made by all players
   * 
   * @return true if the game board is reset successfully
   */
  boolean resetBoard() {
    for (int i = 0; i < totalNumberOfRows; i++) {
      for (int j = 0; j < totalNumberOfColumns; j++) {
        boardState[i][j] = 0;
      }
    }
    for (int i = 0; i < totalNumberOfColumns; i++) {
      chipsDroppedInColumn[i] = 0;
    }
    return true;
  }

  /**
   * Checks if there are chips on every possible location on the game board
   * 
   * @return true if the game board has chips on every possible location and no more moves can be
   *         made
   */
  boolean isBoardFull() {
    for (int i = 0; i < totalNumberOfColumns; i++) {
      if (chipsDroppedInColumn[i] < totalNumberOfRows) {
        return false;
      }
    }
    return true;
  }

  /**
   * Gets number of chips added to a column
   * 
   * @param columnNumber column whose chip count is to be obtained
   * @return number of chips added to a column
   * @throws IllegalArgumentException if invalid column number is provided
   */
  int getNumberOfChipsInColumn(int columnNumber) {
    if (columnNumber < 0 || columnNumber >= totalNumberOfColumns) {
      throw new IllegalArgumentException("Invalid Column Number");
    }
    return chipsDroppedInColumn[columnNumber];
  }

  /**
   * Gets total number of rows on the game board
   * 
   * @return the total number of rows on the game board
   */
  int getRowCount() {
    return totalNumberOfRows;
  }

  /**
   * Gets total number of columns on the game board
   * 
   * @return total number of columns on the game board
   */
  int getColumnCount() {
    return totalNumberOfColumns;
  }

  /**
   * For testing purposes only
   */
  int[][] getBoardState() {
    return boardState;
  }

  /**
   * For testing purposes only
   */
  void setChipsDroppedInColumn_forTest(int columnNumber, int chipCount, Player player) {
    int i = 0;
    while (i < chipCount) {
      boardState[chipsDroppedInColumn[columnNumber]][columnNumber] = player.getPlayerID();
      chipsDroppedInColumn[columnNumber] = ++i;
    }
  }

  @Override
  public String toString() {
    return "Connect Four board [Rows = " + totalNumberOfRows + ", " + " Columns = "
        + totalNumberOfColumns + "]";
  }
}