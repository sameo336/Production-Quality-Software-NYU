package edu.nyu.cs.pqs.ps4.connectfour.demo;

import edu.nyu.cs.pqs.ps4.connectfour.impl.Model;
import edu.nyu.cs.pqs.ps4.connectfour.view.GameView;
import edu.nyu.cs.pqs.ps4.connectfour.view.ModeSelection;

/**
 * The AppManager class is used to trigger the initialization and initiation of the Connect Four
 * game.
 * 
 * @author Anisha Bhatla
 */
public class AppManager {

  /**
   * Initializes and initiates the Connect Four game
   * 
   * @param numberOfListenersToBeAdded the number of GameView windows that pop up when game starts
   */
  private void initializeAndStart(int numberOfListenersToBeAdded) {

    Model model = Model.getInstance();
    for (int i = 0; i < numberOfListenersToBeAdded; i++) {
      new GameView(model);
    }
    ModeSelection.setUpAndStartGame();
  }

  public static void main(String[] args) {

    new AppManager().initializeAndStart(1);

  }
}