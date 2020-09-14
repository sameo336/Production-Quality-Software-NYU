package edu.nyu.cs.pqs.ps5.canvas.impl;

import edu.nyu.cs.pqs.ps5.canvas.api.Listener;
import edu.nyu.cs.pqs.ps5.canvas.view.WindowCountSelection;
import java.util.HashSet;
import java.util.Set;

/**
 * The Model class ensures the correct working of the Canvas Application. The Model follows the
 * singleton pattern, i.e., only a single instance of the Model class can be created. All listeners
 * register with the single Model instance. The thread-unsafe Model class fires events to all its
 * listeners for changing the paint color with which to paint the canvas, implementing a painting
 * move made by a user on the canvas, clearing the canvas board and restarting the application.
 * 
 * @author Anisha Bhatla
 */
public class Model {

  private final Set<Listener> listeners;
  private boolean appStarted;
  private static Model singletonInstance = null;

  /**
   * Private constructor in order to prevent instantiation from outside the class
   */
  private Model() {
    listeners = new HashSet<Listener>();
  }

  /**
   * Creates an instance of the Model class if none was created, else returns the already created
   * instance
   * 
   * @return the single instance of Model class
   */
  public static Model getInstance() {
    if (singletonInstance == null) {
      singletonInstance = new Model();
    }
    return singletonInstance;
  }

  /**
   * Listeners can register themselves to the Model using this method. A listener can only register
   * itself once.
   * 
   * @param listener the listener that wishes to register itself
   * @return true if the given listener is registered successfully
   * @throws IllegalArgumentException if the given listener is null
   */
  public boolean addListener(Listener listener) {
    if (listener == null) {
      throw new IllegalArgumentException("Listener cannot be null");
    }
    return listeners.add(listener);
  }

  /**
   * Listeners can unsubscribe themselves from the Model using this method. The given listener can
   * be only removed if there are at least two listeners currently registered, one of them being the
   * given listener.
   * 
   * @param listener the listener that wishes to remove itself as a listener
   * @return true if the given listener is removed successfully
   * @throws IllegalArgumentException if the given listener is null
   */
  public boolean removeListener(Listener listener) {
    if (listener == null) {
      throw new IllegalArgumentException("Listener cannot be null");
    }
    if (listeners.size() <= 1) {
      return false;
    }
    return listeners.remove(listener);

  }

  /**
   * Starts the canvas application, with the given number of pop-up windows and given paint color,
   * selected by the user. If there isn't any paint color chosen, the default paint color is used to
   * paint the canvas
   * 
   * @param paintColorChosen the paint color to paint the canvas with
   * @param numberOfWindows the number of pop-up windows to be displayed
   * @return true if the canvas application is started successfully
   * @throws IllegalArgumentException if the number of pop-up windows is less than minimum
   *           permissible count or greater than maximum permissible count
   * @throws IllegalStateException if the canvas application has already started
   */
  public boolean startApp(AppRules.PaintColor paintColorChosen, Integer numberOfWindows) {
    if (numberOfWindows < AppRules.MINIMUM_WINDOWS || numberOfWindows > AppRules.MAXIMUM_WINDOWS) {
      throw new IllegalArgumentException("Invalid Number of Windows");
    }
    if (appStarted) {
      throw new IllegalStateException("App has already started");
    }
    if (paintColorChosen == null) {
      for (int i = 0; i < numberOfWindows; i++) {
        new ListenerImplementation.Builder(this).build();
      }
    } else {
      for (int i = 0; i < numberOfWindows; i++) {
        new ListenerImplementation.Builder(this).color(paintColorChosen).build();
      }
    }
    appStarted = true;
    return true;
  }

  /**
   * When a request to select a new paint color is made, this method informs all listeners of this
   * new selection and the paint color is changed
   * 
   * @param paintColor the new paint color selected by the user to paint the canvas
   * @return true if the new paint color is chosen successfully
   * @throws IllegalArgumentException if the given paint color is null
   * @throws IllegalStateException if the canvas application has not yet started
   */
  public boolean changePaintColor(AppRules.PaintColor paintColor) {
    if (paintColor == null) {
      throw new IllegalArgumentException("New Paint Color cannot be null");
    }
    if (!appStarted) {
      throw new IllegalStateException("App has not yet started");
    }
    fireColorChangedEvent(paintColor);
    return true;
  }

  /**
   * When a request to clear the canvas board is made, this method informs all listeners of this
   * clearance and the canvas is cleared. The paint color to paint the canvas remains unchanged.
   * 
   * @return true if the canvas board is cleared successfully
   * @throws IllegalStateException if the canvas application has not yet started
   */
  public boolean clearCanvas() {
    if (!appStarted) {
      throw new IllegalStateException("App has not yet started");
    }
    fireCanvasClearedEvent();
    return true;
  }

  /**
   * When a user paints on the canvas in any one window using a mouse, this method informs all
   * listeners of this painting move and the move is made. The paint color to paint the canvas
   * remains unchanged.
   * 
   * @param oldXCoordinate the X co-ordinate of the point wherein the painting move starts
   * @param oldYCoordinate the Y co-ordinate of the point wherein the painting move starts
   * @param newXCoordinate the X co-ordinate of the point wherein the painting move ends
   * @param newYCoordinate the Y co-ordinate of the point wherein the painting move ends
   * @return true if the new painting move is made successfully
   * @throws IllegalStateException if the canvas application has not yet started
   */
  public boolean makePaintingMove(int oldXCoordinate, int oldYCoordinate, int newXCoordinate,
      int newYCoordinate) {
    if (!appStarted) {
      throw new IllegalStateException("App has not yet started");
    }
    fireMoveMadeEvent(oldXCoordinate, oldYCoordinate, newXCoordinate, newYCoordinate);
    return true;
  }

  /**
   * When a user restarts the canvas application, this method informs all listeners of the restart,
   * removes all current listeners and then displays the Window Selection GUI.
   * 
   * @return true if the application restarts successfully
   */
  public boolean restartApp() {
    fireRestartEvent();
    listeners.clear();
    appStarted = false;
    WindowCountSelection.getInstanceAndDisplayWindowSelectionPage();
    return true;
  }

  /**
   * For testing purposes only
   */
  Set<Listener> getListeners_forTest() {
    return new HashSet<Listener>(listeners);
  }

  /**
   * For testing purposes only
   */
  void clearListeners_forTest() {
    listeners.clear();
  }

  /**
   * For testing purposes only
   */
  boolean getAppStarted_forTest() {
    return appStarted;
  }

  /**
   * For testing purposes only
   */
  void setAppStarted_forTest(boolean appStarted) {
    this.appStarted = appStarted;
  }

  private void fireMoveMadeEvent(int oldXCoordinate, int oldYCoordinate, int newXCoordinate,
      int newYCoordinate) {
    for (Listener listener : listeners) {
      listener.paintingMoveMade(oldXCoordinate, oldYCoordinate, newXCoordinate, newYCoordinate);
    }
  }

  private void fireColorChangedEvent(AppRules.PaintColor newColorChosen) {
    for (Listener listener : listeners) {
      listener.paintColorChanged(newColorChosen);
    }
  }

  private void fireRestartEvent() {
    for (Listener listener : listeners) {
      listener.appRestarted();
    }
  }

  private void fireCanvasClearedEvent() {
    for (Listener listener : listeners) {
      listener.canvasCleared();
    }
  }
}
