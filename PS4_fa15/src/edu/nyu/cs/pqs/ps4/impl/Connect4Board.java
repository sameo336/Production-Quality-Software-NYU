package edu.nyu.cs.pqs.ps4.impl;

/**
 * Defines the working of a connectfour board and the various functions it
 * employees. The playerturn multidimensional array marks the player moves on
 * the actual board.
 * 
 * @author Sanchit K
 *
 */
final class Connect4Board {

  private FixedRules.player[][] playerTurn;
  private final Object boardLock;
  private static final int cols = FixedRules.COLUMNS;
  private static final int rows = FixedRules.ROWS;

  Connect4Board() {
    boardLock = new Object();
    this.playerTurn = new FixedRules.player[rows][cols];
    resetBoard();
  }

  /**
   * Checks if a move is valid or not.
   * 
   * @param move
   *          the move that is to be checked
   * @return return true or false based on the check.
   */
  private boolean checkMoveValid(BasicMove move) {

    if (move.getColumn() >= 0 && move.getColumn() < cols && move.getRow() >= 0
        && move.getRow() < rows) {

      if (playerTurn[move.getRow()][move.getColumn()] == FixedRules.player.NA) {
        boolean check = true;
        for (int a = move.getRow() + 1; a < rows; a++) {
          if (playerTurn[a][move.getColumn()] == FixedRules.player.NA) {
            check = false;
          }
        }
        return check;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  /**
   * records a move in the playerturn board. It basically marks the playerid on
   * the baord using the move argument
   * 
   * @param move
   *          the move which is to be used for recording.
   */
  final void recordMove(BasicMove move) {
    synchronized (boardLock) {
      if (!checkMoveValid(move)) {
        throw new IllegalArgumentException("Invalid Move!");
      }
      playerTurn[move.getRow()][move.getColumn()] = move.getMovePlayedBy();
    }
  }

  /**
   * checks if the move is a winning move or not!
   * 
   * @param move
   *          the move to be checked for
   * @return true of false based on the whether the move is a winning move or
   *         not.
   */
  final boolean isItWinningMove(BasicMove move) {
    synchronized (boardLock) {
      return checkVertical(move) || checkHorizontal(move)
          || checkDiagonal(move);
    }
  }

  /**
   * checks if the player plays the next move vertically, will the player win or
   * not
   * 
   * @param move
   *          the move to be checked for
   * @return true or false based on the check
   */
  private boolean checkVertical(BasicMove move) {
    int check = move.getRow();
    for (int a = move.getRow() + 1; a < rows; a++) {
      if (playerTurn[a][move.getColumn()] != move.getMovePlayedBy()) {
        break;
      } else {
        check = a;
      }
    }
    boolean result = (check - move.getRow() + 1) >= FixedRules.MIN_WIN_LENGTH;
    return result;
  }

  /**
   * checks if the player plays the next move horizontally, will the player win
   * or not
   * 
   * @param move
   *          the move to be checked for
   * @return true or false based on the check
   */
  private boolean checkHorizontal(BasicMove move) {
    int check = move.getColumn();
    for (int a = move.getColumn() - 1; a >= 0; a--) {
      if (playerTurn[move.getRow()][a] == move.getMovePlayedBy()) {
        check = a;
      } else {
        break;
      }
    }

    int check2 = move.getColumn();
    for (int a = move.getColumn() + 1; a < cols; a++) {
      if (playerTurn[move.getRow()][a] == move.getMovePlayedBy()) {
        check2 = a;
      } else {
        break;
      }
    }
    boolean result = (check2 - check + 1) >= FixedRules.MIN_WIN_LENGTH;
    return result;
  }

  /**
   * checks if the player plays the next move diagonally(down and up diagonal
   * both), will the player win or not
   * 
   * @param move
   *          the move to be checked for
   * @return true or false based on the check
   */
  private boolean checkDiagonal(BasicMove move) {
    FixedRules.player currPlayer = move.getMovePlayedBy();

    int check = move.getColumn();
    for (int a = move.getRow() - 1; a >= 0 && check > 0; a--) {
      if (playerTurn[a][check - 1] == currPlayer) {
        check--;
      } else {
        break;
      }
    }

    int check2 = move.getColumn();
    for (int a = move.getRow() + 1; a < rows && check2 < cols - 1; a++) {
      if (playerTurn[a][check2 + 1] == currPlayer) {
        check2++;
      } else {
        break;
      }
    }

    int check3 = move.getColumn();
    for (int a = move.getRow() + 1; a < rows && check3 > 0; a++) {
      if (playerTurn[a][check3 - 1] == currPlayer) {
        check3--;
      } else {
        break;
      }
    }

    int check4 = move.getColumn();
    for (int a = move.getRow() - 1; a >= 0 && check4 < cols - 1; a--) {
      if (playerTurn[a][check4 + 1] == currPlayer) {
        check4++;
      } else {
        break;
      }
    }

    boolean result =
        ((check2 - check + 1) >= FixedRules.MIN_WIN_LENGTH)
            || ((check4 - check3 + 1) >= FixedRules.MIN_WIN_LENGTH);
    return result;

  }

  /**
   * resets the board to its original (no moves played) state
   */
  final void resetBoard() {
    synchronized (boardLock) {
      for (int a = 0; a < rows; a++) {
        for (int b = 0; b < cols; b++) {
          playerTurn[a][b] = FixedRules.player.NA;
        }
      }
    }
  }

  /**
   * checks if the board is full or not
   * 
   * @return true or false based on the check
   */
  final boolean checkFull() {
    synchronized (boardLock) {
      for (FixedRules.player[] check : playerTurn) {
        for (FixedRules.player a : check) {
          if (a == FixedRules.player.NA) {
            return false;
          }
        }
      }
      return true;
    }
  }

  /**
   * gets the current status of the board. this is used in many places
   * indirectly to get the current state of the board
   * 
   * @return the playerboard array markings.
   */
  FixedRules.player[][] getCurrBoardState() {
    synchronized (boardLock) {
      FixedRules.player[][] arr = new FixedRules.player[rows][];
      for (int b = 0; b < rows; b++) {
        FixedRules.player[] temp = playerTurn[b];
        arr[b] = temp;
      }
      return arr;
    }
  }

  /**
   * gets the first row number of a particular column where no move has been
   * played till now. should implement the vice versa as well. recommended.
   * 
   * @param col
   *          the column number who's first not marked row has to be fetched.
   * @return the row number.
   */
  final int getRow(int col) {
    synchronized (boardLock) {
      int val = Integer.MIN_VALUE;
      for (int i = rows - 1; i >= 0; i--) {
        if (playerTurn[i][col] == FixedRules.player.NA) {
          val = i;
          break;
        }
      }
      return val;
    }
  }

}
