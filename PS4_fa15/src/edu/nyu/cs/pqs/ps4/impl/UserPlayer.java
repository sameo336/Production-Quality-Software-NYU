package edu.nyu.cs.pqs.ps4.impl;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import edu.nyu.cs.pqs.ps4.api.Connect4Model;
import edu.nyu.cs.pqs.ps4.api.Connect4Player;
import edu.nyu.cs.pqs.ps4.view.MainMenu;

/**
 * {@inheritDoc Connect4Player}
 * 
 * @author Sanchit K
 *
 */
final class UserPlayer implements Connect4Player {

  private final Connect4Model model;
  private FixedRules.player playerId;
  private static JPanel[][] playingBoard;
  private static JButton[] buttonList;
  private final MainMenu menu;
  private final Object userLock;

  /**
   * constructor which initializes the variables and starts the UI
   * 
   * @param model
   *          the model of the game
   * @param playerId
   *          playerid of the user who has been created.
   */
  UserPlayer(Connect4Model model, FixedRules.player playerId) {
    this.userLock = new Object();
    this.model = model;
    this.playerId = playerId;
    menu = MainMenu.getInstance();
    if (this.playerId == FixedRules.player.PLAYER_1) {
      playingBoard = new JPanel[FixedRules.ROWS][FixedRules.COLUMNS];
      buttonList = new JButton[FixedRules.COLUMNS];
      menu.showMainMenu(playerId, playingBoard, buttonList, this.model);
    }
  }

  @Override
  public void notifyMovePlayed(BasicMove move) {
    synchronized (userLock) {
      FixedRules.player player = move.getMovePlayedBy();
      if (player == FixedRules.player.PLAYER_1) {
        playingBoard[move.getRow()][move.getColumn()].setBackground(Color.BLUE);
        playingBoard[move.getRow()][move.getColumn()].setBorder(BorderFactory
            .createLineBorder(Color.black));
      } else if (player == FixedRules.player.PLAYER_2) {
        playingBoard[move.getRow()][move.getColumn()].setBackground(Color.RED);
        playingBoard[move.getRow()][move.getColumn()].setBorder(BorderFactory
            .createLineBorder(Color.black));
      } else if (player == FixedRules.player.NA) {
        playingBoard[move.getRow()][move.getColumn()]
            .setBackground(Color.WHITE);
        playingBoard[move.getRow()][move.getColumn()].setBorder(BorderFactory
            .createLineBorder(Color.WHITE));
      }
      if (model.whosNext() == this.playerId) {
        for (JButton but : buttonList) {
          but.setEnabled(true);
        }
        FixedRules.player[] firstRow =
            model.getCurrGameStatus().currBoardState()[0];
        for (int col = 0; col < FixedRules.COLUMNS; col++) {
          if (firstRow[col] == FixedRules.player.NA) {
          } else {
            buttonList[col].setEnabled(false);
          }
        }
      }
    }
  }

  @Override
  public void notifyGameStarted(FixedRules.player playerId) {
    synchronized (userLock) {
      this.playerId = playerId;
      for (JButton but : buttonList) {
        but.setEnabled(true);
      }
      if (!model.isMultiplayer()) {
        if (model.whosNext() == this.playerId) {
          for (JButton but : buttonList) {
            but.setEnabled(true);
          }
        } else {
          for (JButton but : buttonList) {
            but.setEnabled(false);
          }
        }
      }
    }
  }

  @Override
  public void notifyGameFinished(FixedRules.player playerId) {
    synchronized (userLock) {

      if (!model.isMultiplayer()) {
        for (JButton but : buttonList) {
          but.setEnabled(false);
        }
      }
      String result = null;
      String winner = null;
      String loser = null;
      if (!model.isMultiplayer()) {
        if (playerId == FixedRules.player.NA) {
          result = "No One";
        } else if (playerId == this.playerId) {
          result = "You";
        } else if (playerId != this.playerId) {
          result = "The Computer";
        }
        result = result + " won the game!";
      } else {
        if (playerId == FixedRules.player.NA) {
          winner = "No One";
          loser = "No One";
        } else if (playerId == FixedRules.player.PLAYER_1) {
          winner = "Player 1";
          loser = "Player 2";
        } else if (playerId == FixedRules.player.PLAYER_2) {
          winner = "Player 2";
          loser = "Player 1";
        }
      }

      if (!model.isMultiplayer()) {
        JOptionPane.showMessageDialog(menu.getFrame(), result, "Game Over!",
            JOptionPane.OK_OPTION);
      } else {
        if (playerId == FixedRules.player.NA) {
          JOptionPane.showMessageDialog(menu.getFrame(),
              "No one won the Game!", "Game Over!", JOptionPane.OK_OPTION);
        } else if (this.playerId == FixedRules.player.PLAYER_1) {
          JOptionPane
              .showMessageDialog(menu.getFrame(), winner
                  + " won the Game. Congrats!", "Game Over!",
                  JOptionPane.OK_OPTION);
        } else {
          JOptionPane.showMessageDialog(menu.getFrame(), loser
              + " lost the Game. Hard Luck!", "Game Over!",
              JOptionPane.OK_OPTION);
        }
      }

      if (!model.checkGameOn()) {
        model.notifyGameStart(FixedRules.player.PLAYER_1);
      }

      for (JPanel[] row : playingBoard) {
        for (JPanel panel : row) {
          panel.setBackground(Color.WHITE);
          panel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        }
      }
    }
  }

  @Override
  public FixedRules.player getPlayerId() {
    return playerId;
  }

}
