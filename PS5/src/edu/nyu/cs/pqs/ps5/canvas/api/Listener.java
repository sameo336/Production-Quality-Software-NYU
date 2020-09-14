package edu.nyu.cs.pqs.ps5.canvas.api;

import edu.nyu.cs.pqs.ps5.canvas.impl.AppRules;

/**
 * The Listener listens and monitors all changes made to the canvas application - when the user
 * changes the paint color with which the canvas is painted, clears the canvas, paints on the canvas
 * using the mouse and restarts the application
 * 
 * @author Anisha Bhatla
 */
public interface Listener {

  /**
   * Triggered when a user paints on the canvas using the mouse.
   * 
   * @param oldXCoordinate the X co-ordinate of the point wherein the painting move starts
   * @param oldYCoordinate the Y co-ordinate of the point wherein the painting move starts
   * @param newXCoordinate the X co-ordinate of the point wherein the painting move ends
   * @param newYCoordinate the Y co-ordinate of the point wherein the painting move ends
   * @return true if the painting move is made successfully
   */
  boolean paintingMoveMade(int oldXCoordinate, int oldYCoordinate, int newXCoordinate,
      int newYCoordinate);

  /**
   * Triggered when a user changes the paint color with which the user wishes to paint on the
   * canvas.
   * 
   * @param newPaintColorChosen the new paint color chosen to paint the canvas with
   * @return true if the paint color is changed successfully
   */
  boolean paintColorChanged(AppRules.PaintColor newPaintColorChosen);

  /**
   * Triggered when a user clears the canvas board.
   * 
   * @return true if the canvas board is cleared successfully
   */
  boolean canvasCleared();

  /**
   * Triggered when a user restarts the application.
   * 
   * @return true if the application is restarted successfully
   */
  boolean appRestarted();

}
