package edu.nyu.cs.pqs.ps4.view;

import javax.swing.JOptionPane;

/**
 * The initial page which asks the user to choose between a single or double
 * player game.
 * 
 * @author Sanchit K
 *
 */
public final class InitialPage {

  private InitialPage() {

  }

  public final static int startGame() {
    return JOptionPane.showOptionDialog(null,
        "Choose \n 1) Single player \n 2) Double player",
        "Welcome to Connect Four Game", JOptionPane.DEFAULT_OPTION,
        JOptionPane.QUESTION_MESSAGE, null,
        new String[] { "Single", "Double" }, null);
  }
}
