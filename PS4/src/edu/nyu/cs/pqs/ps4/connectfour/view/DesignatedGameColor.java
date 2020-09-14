package edu.nyu.cs.pqs.ps4.connectfour.view;

import java.awt.Color;

/**
 * The DesignatedGameColor enum represents the default colors for the Connect Four GameView. Any
 * changes made here will cascade throughout all GameView instances.
 * 
 * @author Anisha Bhatla
 *
 */
enum DesignatedGameColor {

  PLAYER1(Color.blue),

  PLAYER2(Color.green),

  BACKGROUND(Color.white),

  BORDER(Color.gray);

  private final Color designatedColor;

  private DesignatedGameColor(Color color) {
    this.designatedColor = color;
  }

  /**
   * Gets the designated color of the current DesignatedGameColor element
   * 
   * @return Color designated color of the current DesignatedGameColor element
   */
  Color getColor() {
    return designatedColor;
  }
}
