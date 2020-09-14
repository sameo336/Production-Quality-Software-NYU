package edu.nyu.cs.pqs.ps4.impl;

/**
 * This class gives a read only copy of the board object. can be used to get the
 * present status of the game and the board. The names of the functions define
 * their working and functionality.
 * 
 * @author Sanchit K
 *
 */
public final class CurrentBoardStatus {

  private Connect4Board board;

  public CurrentBoardStatus(Connect4Board board) {
    // TODO Auto-generated constructor stub
    this.board = board;
  }

  public final FixedRules.player[][] currBoardState() {
    return board.getCurrBoardState();
  }

  public final int getCurrRow(int col) {
    return board.getRow(col);
  }

  public final boolean checkWinningMove(BasicMove move) {
    return board.isItWinningMove(move);
  }
}
