package edu.nyu.cs.pqs.ps5.api;

/**
 * The Model or the Subject in the Observer pattern. The Canvas model is the
 * model which is notified by the observer of any changes. It adds the
 * observers, can remove them and also notifies all the observers of any changes
 * made by an observer.
 * 
 * @author Sanchit K
 *
 */
public interface CanvasModel {
  /**
   * This adds the Window observers to the observer list.
   * 
   * @param window
   *          the observer to be added in the observer list
   */
  public void addObserver(Window window);

  /**
   * Removes an observer from the list. Can be implemented in the future to add
   * the option of deleting one or multiple windows.
   * 
   * @param window
   *          the window observer object to be removed
   */
  public void removeObserver(Window window);

  /**
   * The notify method which notifies all the observers of the changes made by a
   * single observer.
   * 
   * @param oldX
   *          the old x coordinate from where the mouse started the drawing. its
   *          basically the starting coordinate of the line.
   * @param oldY
   *          the starting y coordinate of the line.
   * @param currentX
   *          the ending x coordinate of the line.
   * @param currentY
   *          the ending y coordinate of the line.
   */
  public void notifyMove(int oldX, int oldY, int currentX, int currentY);
}
