package edu.nyu.cs.pqs.ps4.api;

import edu.nyu.cs.pqs.ps4.impl.BasicMove;
import edu.nyu.cs.pqs.ps4.impl.FixedRules;

/**
 * The observer of the Observer pattern. Defines the actions of the players.
 * 
 * @author Sanchit K
 *
 */
public interface Connect4Player {
  /**
   * Notifies the move that has been played by the user or computer player.
   * 
   * @param move
   *          the move that has been played by the player.
   */
  public void notifyMovePlayed(BasicMove move);

  /**
   * Notifies the start of the game.
   * 
   * @param playerId
   *          tells the id of the first player which will start the game.
   */
  public void notifyGameStarted(FixedRules.player playerId);

  /**
   * Notifies that the game has finished.
   * 
   * @param playerId
   *          the playerid of the winner.
   */
  public void notifyGameFinished(FixedRules.player playerId);

  /**
   * gets the playerid of the referencing player. i.e either user player or
   * computer player.
   * 
   * @return the playerid of the player which has called the function.
   */
  public FixedRules.player getPlayerId();

}
