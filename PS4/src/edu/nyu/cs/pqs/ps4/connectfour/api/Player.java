package edu.nyu.cs.pqs.ps4.connectfour.api;

import edu.nyu.cs.pqs.ps4.connectfour.impl.PlayerType;

/**
 * The Player plays the Connect Four game.
 * 
 * @author Anisha Bhatla
 */
public interface Player {
  /**
   * Gets the type of player.
   * 
   * @return PlayerType type of player
   */
  PlayerType getPlayerType();

  /**
   * Gets the player ID.
   * 
   * @return the player ID
   */
  int getPlayerID();

  /**
   * Gets the name of the player.
   * 
   * @return name of the player
   */
  String getPlayerName();
}