package edu.nyu.cs.pqs.ps4.connectfour.impl;

import edu.nyu.cs.pqs.ps4.connectfour.api.Player;

/**
 * The ComputerPlayer class is a thread-unsafe representation of Computer Player that plays the
 * Connect Four game. ComputerPlayer has a Player Type of COMPUTER. Upon its turn, the
 * ComputerPlayer instance looks ahead a single move in the game and makes that move if it results
 * in a win. Else, it makes a random valid move.
 * 
 * @author Anisha Bhatla
 */
class ComputerPlayer implements Player {
  private final PlayerType playerType;

  ComputerPlayer() {
    playerType = PlayerType.COMPUTER;
  }

  @Override
  public PlayerType getPlayerType() {
    return playerType;
  }

  @Override
  public int getPlayerID() {
    return playerType.getID();
  }

  /**
   * {@inheritDoc} Returns an empty string.
   * 
   */
  @Override
  public String getPlayerName() {
    return "";
  }

  /**
   * Gets the index of the column where the computer player should make the move. Initially, it
   * tries to finds a move by which the computer may result as the winner of the game. If such a
   * move exists, it is returned. Else, a random valid column is returned.
   * 
   * @param board the instance of the Board
   * @return column number of the next move which the computer should make
   * @throws IllegalArgumentException if the board instance is null
   */
  int getNextMove(Board board) {
    if (board == null) {
      throw new IllegalArgumentException("Board instance cannot be null");
    }
    for (int i = 0; i < board.getColumnCount(); i++) {
      if (board.isValidColumn(i)) {
        board.addChip(i, this);
        if (board.checkWin(this)) {
          board.removeChip(i);
          return i;
        }
        board.removeChip(i);
      }
    }
    int columnNumber = (int) (Math.random() * board.getColumnCount());
    while (!board.isValidColumn(columnNumber)) {
      columnNumber = (int) (Math.random() * board.getColumnCount());
    }
    return columnNumber;
  }

  /**
   * {@inheritDoc} Two Computer Players are equal if they have the same type.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof ComputerPlayer)) {
      return false;
    }
    ComputerPlayer other = (ComputerPlayer) obj;
    if (other.playerType != playerType) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 17;
    result = prime * result + playerType.getID();
    result = prime * result + playerType.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "Connect Four Computer Player [Player Type = " + playerType + ", Player ID = "
        + playerType.getID() + "]";
  }
}