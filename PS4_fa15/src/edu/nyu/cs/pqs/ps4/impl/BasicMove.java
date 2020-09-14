package edu.nyu.cs.pqs.ps4.impl;

/**
 * The class which defines the structure and the behavior of a basic move.
 * 
 * @author Sanchit K
 *
 */
public final class BasicMove {
  private FixedRules.player movePlayedBy;
  private int row;
  private int column;

  public BasicMove(FixedRules.player movePlayedBy, int row, int column) {
    this.movePlayedBy = movePlayedBy;
    this.row = row;
    this.column = column;
  }

  /**
   * Get the playerid of the player which has played the most recent move.
   * 
   * @return the playerid of the most recent player who played the move
   */
  public final FixedRules.player getMovePlayedBy() {
    return movePlayedBy;
  }

  /**
   * gets the row number of the move which is calling the function.
   * 
   * @return row number of the move
   */
  public final int getRow() {
    return row;
  }

  /**
   * gets the column number of the move which is calling the function
   * 
   * @return the column number of the move.
   */
  public final int getColumn() {
    return column;
  }

}
