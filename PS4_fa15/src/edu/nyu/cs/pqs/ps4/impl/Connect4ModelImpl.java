package edu.nyu.cs.pqs.ps4.impl;

import java.util.ArrayList;
import java.util.List;

import edu.nyu.cs.pqs.ps4.api.Connect4Model;
import edu.nyu.cs.pqs.ps4.api.Connect4Player;

/**
 * {@inheritDoc Connect4Model}
 * 
 * @author Sanchit K
 *
 */
final class Connect4ModelImpl implements Connect4Model {

  private final Object modelLock;
  private boolean checkGameOn;
  private List<Connect4Player> observerList;
  private int noOfPlayers;
  private final Connect4Board board;
  private FixedRules.player nextChance;
  private boolean isMultiplayer;

  public Connect4ModelImpl() {
    modelLock = new Object();
    checkGameOn = false;
    observerList = new ArrayList<Connect4Player>();
    noOfPlayers = 0;
    board = new Connect4Board();
    isMultiplayer = true;
  }

  @Override
  public final void addObserver(Connect4Player observer) {
    synchronized (modelLock) {
      if (checkGameOn) {
        throw new IllegalStateException(
            "Trying to add player when game is on. Incorrect!");
      } else {
        if (noOfPlayers == FixedRules.MAX_PLAYERS) {
          throw new IllegalStateException(
              "Maximum number of players added already!");
        }
        noOfPlayers++;
      }

      observerList.add(observer);
    }
  }

  @Override
  public final void notifyGameStart(FixedRules.player playerId) {
    synchronized (modelLock) {
      if (checkGameOn) {
        throw new IllegalStateException("Game alredy in progress!");
      }
      if (noOfPlayers != FixedRules.MAX_PLAYERS) {
        throw new IllegalStateException(
            "All players have not joined. Cannot start the game!");
      }

      checkGameOn = true;
      board.resetBoard();
      for (Connect4Player observer : observerList) {
        Connect4Player player = (Connect4Player) observer;
        nextChance = playerId;
        player.notifyGameStarted(player.getPlayerId());
      }

    }
  }

  @Override
  public final void notifyMove(BasicMove move) {
    synchronized (modelLock) {
      if (!checkGameOn) {
        throw new IllegalStateException("Game has not started!");
      }
      board.recordMove(move);
      if (nextChance == FixedRules.player.PLAYER_1) {
        nextChance = FixedRules.player.PLAYER_2;
      } else if (nextChance == FixedRules.player.PLAYER_2) {
        nextChance = FixedRules.player.PLAYER_1;
      } else {
        throw new IllegalStateException("Board is full. Cannot play further!");
      }
      for (Connect4Player observer : observerList) {
        observer.notifyMovePlayed(move);
      }
      if (board.checkFull()) {
        notifyGameEnd(FixedRules.player.NA);
      } else if (board.isItWinningMove(move)) {
        notifyGameEnd(move.getMovePlayedBy());

      }

    }

  }

  @Override
  public final void notifyGameEnd(FixedRules.player playerId) {
    synchronized (modelLock) {
      checkGameOn = false;
      for (Connect4Player observer : observerList) {
        observer.notifyGameFinished(playerId);
      }
    }
  }

  @Override
  public final CurrentBoardStatus getCurrGameStatus() {
    // TODO Auto-generated method stub
    return new CurrentBoardStatus(board);
  }

  @Override
  public final boolean checkGameOn() {
    synchronized (modelLock) {
      boolean result = checkGameOn;
      return result;
    }
  }

  @Override
  public final FixedRules.player whosNext() {
    synchronized (modelLock) {
      FixedRules.player temp = nextChance;
      return temp;
    }
  }

  @Override
  public final boolean isMultiplayer() {
    for (Connect4Player observer : observerList) {
      if (observer instanceof UserPlayer) {
        isMultiplayer = (isMultiplayer & true);
      } else {
        isMultiplayer = (isMultiplayer & false);
      }
    }
    return isMultiplayer;
  }

}
