package edu.nyu.cs.pqs.ps4.connectfour.impl;

import edu.nyu.cs.pqs.ps4.connectfour.api.Listener;
import edu.nyu.cs.pqs.ps4.connectfour.api.Player;

import java.util.HashSet;
import java.util.Set;

/**
 * The Model class consists of all the logic for the working of the Connect Four game. All listeners
 * register with the model. The Model instance fires events to all its listeners whenever an event
 * like game start, game over, game won, move made and game reset occurs. This thread-unsafe class
 * follows the Singleton pattern, i.e., only a single instance of the Model class can be created.
 * 
 * @author Anisha Bhatla
 */
public class Model {
  private Player playerOne;
  private Player playerTwo;
  private Player currentPlayer;
  private final Board board;
  private final Set<Listener> listeners;
  private boolean gameStarted;
  private static Model modelSingletonInstance = null;

  /**
   * Creates a new instance of the Model class if none exists or returns existing instance if one
   * was created earlier
   * 
   * @return Model the single instance of the Model class
   */
  public static Model getInstance() {
    if (modelSingletonInstance == null) {
      modelSingletonInstance = new Model();
    }
    return modelSingletonInstance;
  }

  /**
   * Private constructor to prevent instantiation of a Model instance from outside the class
   */
  private Model() {
    board = Board.getInstance();
    listeners = new HashSet<Listener>();
    currentPlayer = playerOne;
  }

  /**
   * Listeners can register themselves using this method
   * 
   * @param listener who wants to register
   * @return true if the listener is added successfully
   * @throws IllegalArgumentException if the listener is null
   */
  public boolean addListener(Listener listener) {
    if (listener == null) {
      throw new IllegalArgumentException("Listener cannot be null");
    }
    return listeners.add(listener);
  }

  /**
   * Listeners can unsubscribe themselves using this method, provided there are at least three
   * subscribed listeners present currently
   * 
   * @param listener who wants to get unregistered
   * @return true if the listener is removed successfully
   * @throws IllegalArgumentException if the listener is null
   */
  public boolean removeListener(Listener listener) {
    if (listener == null) {
      throw new IllegalArgumentException("Listener cannot be null");
    }
    if (listeners.size() <= 2) {
      return false;
    }
    return listeners.remove(listener);
  }

  /**
   * Gets a copy of the set of current listeners
   * 
   * @return the set of current listeners
   */
  public Set<Listener> getListeners() {
    return new HashSet<Listener>(listeners);
  }

  /**
   * Sets the players of the current game to HUMAN1 and HUMAN2 / COMPUTER along with their names.
   * Sets the first player to be the current player at the start of a new game.
   * 
   * @param typeOfFirstPlayer type of the first player
   * @param nameOfFirstPlayer name of the first player
   * @param typeOfSecondPlayer type of the second player
   * @param nameOfSecondPlayer name of the second player
   * @throws IllegalArgumentException if any of the player types or name is null or if the first
   *           player type is not HUMAN1 or the second player type is HUMAN1
   */
  public void setPlayers(PlayerType typeOfFirstPlayer, String nameOfFirstPlayer,
      PlayerType typeOfSecondPlayer, String nameOfSecondPlayer) {
    if (typeOfFirstPlayer == null || typeOfSecondPlayer == null) {
      throw new IllegalArgumentException("Player Type cannot be null");
    }
    if (nameOfFirstPlayer == null || nameOfSecondPlayer == null) {
      throw new IllegalArgumentException("Player Name cannot be null");
    }
    if (typeOfFirstPlayer != PlayerType.HUMAN1) {
      throw new IllegalArgumentException("Player 1 has to be of type HUMAN1");
    }
    if (typeOfSecondPlayer == PlayerType.HUMAN1) {
      throw new IllegalArgumentException("Player 2 cannot be of type HUMAN1");
    }
    playerOne = PlayerFactory.getPlayer(typeOfFirstPlayer, nameOfFirstPlayer);
    if (typeOfSecondPlayer == PlayerType.HUMAN2) {
      playerTwo = PlayerFactory.getPlayer(PlayerType.HUMAN2, nameOfSecondPlayer);
    } else {
      playerTwo = PlayerFactory.getPlayer(PlayerType.COMPUTER, "");
    }
    currentPlayer = playerOne;
  }

  /**
   * Adds a chip to the provided column
   * 
   * @param column column number in which a chip is to be added
   * @return true if the chip is successfully added to the column
   * @throws IllegalArgumentException if column number is invalid
   * @throws IllegalStateException if addition is attempted when the game has not yet started
   */
  public boolean addChipToColumn(int column) {
    if (!board.isValidColumn(column)) {
      throw new IllegalArgumentException("Invalid column number");
    }
    if (!gameStarted) {
      throw new IllegalStateException("Game has not yet started");
    }
    board.addChip(column, currentPlayer);
    int rowNumber = board.getRowCount() - board.getNumberOfChipsInColumn(column);
    fireChipAddedSuccessfullyEvent(column, rowNumber, currentPlayer);
    boolean isWinnigMove = board.checkWin(currentPlayer);
    boolean hasBoardFilled = board.isBoardFull();
    if (isWinnigMove || hasBoardFilled) {
      gameStarted = false;
      if (isWinnigMove)
        fireGameWonEvent(currentPlayer);
      else
        fireGameOverEvent();
    } else {
      switchTurn();
      if (currentPlayer.getPlayerType() == PlayerType.COMPUTER) {
        ComputerPlayer computerPlayer = (ComputerPlayer) currentPlayer;
        int computerDropColumn = computerPlayer.getNextMove(board);
        addChipToColumn(computerDropColumn);
      }
    }
    return true;
  }

  /**
   * Resets the game by making Player One as current player and resetting board to its original
   * state
   * 
   * @return true if the game is reset successfully
   */
  public boolean resetGame() {
    currentPlayer = playerOne;
    board.resetBoard();
    gameStarted = false;
    fireGameResetEvent();
    return true;
  }

  /**
   * Starts the game, with the players with the given names.
   * 
   * @param nameOfFirstPlayer name of the first player
   * @param nameOfSecondPlayer name of the second player
   * @return true if the game is successfully started
   * @throws IllegalArgumentException if the name of the first or second player is null
   * @throws IllegalStateException if the game has already started
   */
  public boolean startGame(String nameOfFirstPlayer, String nameOfSecondPlayer) {
    if (nameOfFirstPlayer == null || nameOfSecondPlayer == null) {
      throw new IllegalArgumentException("Player Name cannot be null");
    }
    if (gameStarted) {
      throw new IllegalStateException("Game has already started");
    }
    gameStarted = true;
    fireGameStartedEvent(nameOfFirstPlayer, nameOfSecondPlayer);
    return true;
  }

  /**
   * For testing purposes only
   */
  Player getPlayerOne_forTest() {
    return playerOne;
  }

  /**
   * For testing purposes only
   */
  Player setPlayerOne_asHuman1ForTest(Player playerOne) {
    this.playerOne = playerOne;
    return playerOne;
  }

  /**
   * For testing purposes only
   */
  Player getPlayerTwo_forTest() {
    return playerTwo;
  }

  /**
   * For testing purposes only
   */
  Player setPlayerTwo_asHuman2ForTest(Player playerTwo) {
    this.playerTwo = playerTwo;
    return playerTwo;
  }

  /**
   * For testing purposes only
   */
  Player setPlayerTwo_asComputerForTest(Player playerTwo) {
    this.playerTwo = playerTwo;
    return playerTwo;
  }

  /**
   * For testing purposes only
   */
  Player getCurrentPlayer_forTest() {
    return currentPlayer;
  }

  /**
   * For testing purposes only
   */
  Player setCurrentPlayer_forTest(Player player) {
    currentPlayer = player;
    return currentPlayer;
  }

  /**
   * For testing purposes only
   */
  Board getBoard_forTest() {
    return board;
  }

  /**
   * For testing purposes only
   */
  boolean getGameStarted_forTest() {
    return gameStarted;
  }

  /**
   * For testing purposes only
   */
  void setGameStarted_forTest(boolean gameStarted) {
    this.gameStarted = gameStarted;
  }

  /**
   * For testing purposes only
   */
  void clearAllListeners_forTest() {
    this.listeners.clear();
  }

  private void switchTurn() {
    if (currentPlayer == playerOne) {
      currentPlayer = playerTwo;
    } else {
      currentPlayer = playerOne;
    }
  }

  private void fireGameStartedEvent(String firstName, String secondName) {
    for (Listener listener : listeners) {
      listener.gameStarted(firstName, secondName);
    }
  }

  private void fireChipAddedSuccessfullyEvent(int column, int row, Player player) {
    for (Listener listener : listeners) {
      listener.modeMade(column, row, player);
    }
  }

  private void fireGameWonEvent(Player player) {
    for (Listener listener : listeners) {
      listener.gameWon(player, player.getPlayerName());
    }
  }

  private void fireGameOverEvent() {
    for (Listener listener : listeners) {
      listener.gameOverWhenBoardIsFull();
    }
  }

  private void fireGameResetEvent() {
    for (Listener listener : listeners) {
      listener.gameReset();
    }
  }
}