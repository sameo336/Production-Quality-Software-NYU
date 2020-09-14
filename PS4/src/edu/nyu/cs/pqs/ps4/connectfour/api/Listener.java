package edu.nyu.cs.pqs.ps4.connectfour.api;

/**
 * The Listener is used to listen to and capture the current state of the Connect Four game at every
 * given point of time.
 * 
 * @author Anisha Bhatla
 */
public interface Listener {

  /**
   * Triggered when the game starts.
   * 
   * @param firstPlayerName name of first player
   * @param secondPlayerName name of second player
   */
  void gameStarted(String firstPlayerName, String secondPlayerName);

  /**
   * Triggered when a player makes a move.
   * 
   * @param columnNumber board column in which chip is added
   * @param rowNumber board row in which chip is added
   * @param player player who made the move
   */
  void modeMade(int columnNumber, int rowumber, Player player);

  /**
   * Triggered when the game is won by a player.
   * 
   * @param player player who won the game
   * @param name the winner's name if available
   */
  void gameWon(Player player, String name);

  /**
   * Triggered when the game ends since the board is full and no more moves are possible.
   */
  void gameOverWhenBoardIsFull();

  /**
   * Triggered when the game resets.
   */
  void gameReset();
}