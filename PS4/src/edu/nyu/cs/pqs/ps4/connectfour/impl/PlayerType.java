package edu.nyu.cs.pqs.ps4.connectfour.impl;

/**
 * The PlayerType enum represents the types of players that can play the Connect Four game. The
 * types and their default ID values are: HUMAN1 with ID value 1, HUMAN2 with ID value 2 and
 * COMPUTER with ID value 2.
 * 
 * @author Anisha Bhatla
 */
public enum PlayerType {
  /**
   * First Player of the game. Each game will have this player
   */
  HUMAN1(1),
  /**
   * Second player is HUMAN2 if Human vs. Human game mode is selected
   */
  HUMAN2(2),
  /**
   * Second player is COMPUTER if Human vs. Computer game mode is selected
   */
  COMPUTER(2);

  private final int id;

  private PlayerType(int id) {
    this.id = id;
  }

  /**
   * Gets the default ID value of the current PlayerType
   * 
   * @return ID of the current PlayerType
   */
  public int getID() {
    return id;
  }
}
