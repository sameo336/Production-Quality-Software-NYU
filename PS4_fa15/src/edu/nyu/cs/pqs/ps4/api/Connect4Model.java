package edu.nyu.cs.pqs.ps4.api;

import edu.nyu.cs.pqs.ps4.impl.BasicMove;
import edu.nyu.cs.pqs.ps4.impl.CurrentBoardStatus;
import edu.nyu.cs.pqs.ps4.impl.FixedRules;

/**
 * The Class which describes the methods of the Model in the observer pattern.
 * 
 * @author Sanchit K
 *
 */
public interface Connect4Model {
  /**
   * Adds a player/observer to the game.
   * 
   * @param observer
   *          is the player which will be added to the observer list.
   * @throws IllegalStateException
   *           if trying to add a player when the game is already on.
   * @throws IllegalStateException
   *           if maximum number of players have already been added.
   */
  public void addObserver(Connect4Player observer);

  /**
   * Notifies the players that the game has started.
   * 
   * @param playerId
   *          playerid of the player to notify that the game has started
   * @throws IllegalStateException
   *           game is already running
   * @throws IllegalStateException
   *           all players have not joined. cannot start the game
   */
  public void notifyGameStart(FixedRules.player playerId);

  /**
   * Notify the move that has been played.
   * 
   * @param move
   *          the move which was played on the board.
   * @throws IllegalStateException
   *           if game has not started.
   * @throws IllegalStateException
   *           board is already full.
   * 
   */
  public void notifyMove(BasicMove move);

  /**
   * tell which players turn is next.
   * 
   * @return the playerID of the player who's turn is next
   */
  public FixedRules.player whosNext();

  /**
   * Notifies the players that the game has ended.
   * 
   * @param playerId
   *          the playerId of the player to notify
   */
  public void notifyGameEnd(FixedRules.player playerId);

  /**
   * gets the current board status
   * 
   * @return the current board status object.
   */
  public CurrentBoardStatus getCurrGameStatus();

  /**
   * checks if the game is still running or not
   * 
   * @return true or false on whether the game is running or not.
   */
  public boolean checkGameOn();

  /**
   * tells whether the game is a single player game or a double player game
   * 
   * @return true or false based on the type of the selected game by the user.
   */

  public boolean isMultiplayer();

}
