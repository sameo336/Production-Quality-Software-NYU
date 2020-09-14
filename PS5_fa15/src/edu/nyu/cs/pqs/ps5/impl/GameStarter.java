package edu.nyu.cs.pqs.ps5.impl;

import javax.swing.JOptionPane;

import edu.nyu.cs.pqs.ps5.api.CanvasModel;

/**
 * The starter class which accepts the number of windows from the user and
 * creates and adds the observers to the model.
 * 
 * @author Sanchit K
 *
 */
public final class GameStarter {

  // These two are mainly included to test the main method.
  private static int numberOfWindows;
  private static String value = "0";

  public static int getNumberOfWindows() {
    return numberOfWindows;
  }

  public static String getValue() {
    return value;
  }

  /**
   * Checks if the string entered is an integer or not
   * 
   * @param str
   *          the string to be checked
   * @return true if its an integer or else false
   */
  private static boolean isNumeric(String str) {
    return str.matches("^\\d+$");
  }

  private final static String startGame() {
    return JOptionPane.showInputDialog(null,
        "Please input number of Windows, only integers allowed!",
        "Welcome to Canvas painting", JOptionPane.OK_CANCEL_OPTION);
  }

  public static void main(String[] args) {
    CanvasModel model = CanvasModelImpl.getInstance();
    value = GameStarter.startGame();

    if (value == null) {
      value = "0";
      numberOfWindows = Integer.parseInt(value);
    } else if (isNumeric(value)) {
      for (int i = 0; i < Integer.parseInt(value); i++) {
        model.addObserver(new WindowImpl(model));
      }
      numberOfWindows = Integer.parseInt(value);
    } else if (value.isEmpty() || !(isNumeric(value))) {
      value = "0";
      JOptionPane.showMessageDialog(null,
          "Not an Integer, run the application again!", "Error!",
          JOptionPane.OK_OPTION, null);
      numberOfWindows = Integer.parseInt(value);
    }
  }
}
