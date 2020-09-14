package edu.nyu.cs.pqs.ps5.api;

/**
 * The Observer of the Observer Pattern. This is the object which stores the
 * singleton canvas object. It updates the observers after being notified by the
 * Model.
 * 
 * @author Sanchit K
 *
 */
public interface Window {
  /**
   * This method updates the drawing board of the observers. It draws a line
   * from old x,y to new x,y coordinates
   * 
   * @param oldX
   *          the x coordinate of the starting of the line
   * @param oldY
   *          the y coordinate of the starting of the line
   * @param currentX
   *          the x coordinate of the ending of the line
   * @param currentY
   *          the y coordinate of the ending of the line
   */
  public void
      updateDrawingBoard(int oldX, int oldY, int currentX, int currentY);

}
