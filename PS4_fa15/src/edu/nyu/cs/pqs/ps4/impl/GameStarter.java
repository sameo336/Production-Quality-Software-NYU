package edu.nyu.cs.pqs.ps4.impl;

import edu.nyu.cs.pqs.ps4.api.Connect4Model;
import edu.nyu.cs.pqs.ps4.view.InitialPage;

/**
 * Main class which starts the game.
 * 
 * @author Sanchit K
 *
 */
public final class GameStarter {
  private static int choice;

  public static int getChoice() {
    return choice;
  }

  public static void main(String[] args) {
    choice = InitialPage.startGame();

    if (choice == 0) {
      Connect4Model model = new Connect4ModelImpl();
      model.addObserver(new UserPlayer(model, FixedRules.player.PLAYER_1));
      model.addObserver(new ComputerPlayer(model));
      model.notifyGameStart(FixedRules.player.PLAYER_1);
    } else if (choice == 1) {
      Connect4Model model = new Connect4ModelImpl();
      model.addObserver(new UserPlayer(model, FixedRules.player.PLAYER_1));
      model.addObserver(new UserPlayer(model, FixedRules.player.PLAYER_2));
      model.notifyGameStart(FixedRules.player.PLAYER_1);
    }
  }
}
