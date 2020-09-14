package edu.nyu.cs.pqs.ps5.canvas.impl;

import java.awt.Color;

/**
 * The AppRules class defines the rules related to the canvas application - the minimum and maximum
 * number of pop-up windows allowed to be created and displayed to the users, the different paint
 * colors with which the users can paint on the canvas and the default paint color if the user does
 * not select any. Any changes made here will cascade throughout the entire application.
 * 
 * @author Anisha Bhatla
 */
public final class AppRules {

  public static final int MINIMUM_WINDOWS = 1;

  public static final int MAXIMUM_WINDOWS = 100;

  public static final PaintColor DEFAULT_COLOR = PaintColor.BLACK;

  /**
   * The PaintColor enum consists of all possible paint colors with which the canvas can be painted.
   * Any addition/removal of paint color(s) here will cascade throughout the entire application.
   * 
   * @author Anisha Bhatla
   */
  public static enum PaintColor {

    BLUE(Color.BLUE, "Blue"),

    GREEN(Color.GREEN, "Green"),

    BLACK(Color.BLACK, "Black"),

    RED(Color.RED, "Red"),

    YELLOW(Color.YELLOW, "Yellow"),

    CYAN(Color.CYAN, "Cyan");

    private final Color color;
    private final String colorString;

    private PaintColor(Color color, String colorString) {
      this.color = color;
      this.colorString = colorString;
    }

    public Color getColor() {
      return color;
    }

    public String getColorString() {
      return colorString;
    }

    @Override
    public String toString() {
      return colorString;
    }
  }

  /**
   * Private constructor to prevent instantiation from outside the class
   */
  private AppRules() {

  }

}