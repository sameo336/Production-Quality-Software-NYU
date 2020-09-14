package edu.nyu.cs.pqs.ps5.canvas.impl;

import edu.nyu.cs.pqs.ps5.canvas.api.Listener;
import edu.nyu.cs.pqs.ps5.canvas.view.AppWindow;

/**
 * The ListenerImplementation class implements the Listener interface and is used to monitor and
 * display the current state of the canvas application in the form of a pop-up window. An instance
 * of the thread-unsafe ListenerImplementation class adds itself as a listener to the Model in order
 * to monitor and display any changes made to any of the pop-up windows of the application -
 * painting move made, paint color changed, canvas board cleared and application restarted. It can
 * also remove itself as a listener provided there are at least two current listeners (including
 * itself) when it requests for removal. An instance of the ListenerImplementation class is created
 * utilizing the Builder pattern wherein an instance of the Model class is mandatory. A paint color
 * selected by the user to paint the canvas is optional.
 * 
 * @author Anisha Bhatla
 */
class ListenerImplementation implements Listener {

  private final Model model;
  private final AppWindow appWindowInstance;
  private AppRules.PaintColor paintColorChosen;

  /**
   * Builder class is used to create an instance of the ListenerImplementation class wherein the
   * Model instance is a mandatory field and cannot be null. A paint color chosen by the user to
   * paint the canvas is an optional field and cannot be null. If no paint color is chosen by the
   * user, the default paint color is used.
   * 
   * @author Anisha Bhatla
   */
  public static final class Builder {
    private final Model model;
    private AppRules.PaintColor paintColorChosen = AppRules.DEFAULT_COLOR;

    public Builder(Model model) {
      if (model == null) {
        throw new IllegalArgumentException("Model instance cannot be null");
      }
      this.model = model;
    }

    /**
     * Sets the paint color with which the user paints the canvas
     * 
     * @param paintColorChosen the paint color selected by the user
     * @return the current Builder instance
     * @throws IllegalArgumentException if the given paint color is null
     */
    public Builder color(AppRules.PaintColor paintColorChosen) {
      if (paintColorChosen == null) {
        throw new IllegalArgumentException("Paint Color cannot be null");
      }
      this.paintColorChosen = paintColorChosen;
      return this;
    }

    /**
     * Returns a ListenerImplementation instance utilizing the current Builder instance
     * 
     * @return a ListenerImplementation instance
     */
    public ListenerImplementation build() {
      return new ListenerImplementation(this);
    }
  }

  /**
   * Private constructor to prevent instantiation from outside the class
   */
  private ListenerImplementation(Builder builder) {
    if (builder == null) {
      throw new IllegalArgumentException("Builder instance cannot be null");
    }
    model = builder.model;
    paintColorChosen = builder.paintColorChosen;
    model.addListener(this);
    appWindowInstance = new AppWindow(model, paintColorChosen, this);
  }

  /**
   * {@inheritDoc} Reflects the painting move made in its pop-up window. The paint color to paint
   * the canvas remains unchanged
   */
  @Override
  public boolean paintingMoveMade(int oldXCoordinate, int oldYCoordinate, int newXCoordinate,
      int newYCoordinate) {
    appWindowInstance.onPaintingMoveMade(oldXCoordinate, oldYCoordinate, newXCoordinate,
        newYCoordinate);
    return true;
  }

  /**
   * {@inheritDoc} Updates the paint color to be used to paint the canvas with the given new paint
   * color.
   * 
   * @return true if the paint color is changed successfully, false if the new paint color is same
   *         as the old paint color
   * @throws IllegalArgumentException if the new paint color chosen is null
   */
  @Override
  public boolean paintColorChanged(AppRules.PaintColor newPaintColorChosen) {
    if (newPaintColorChosen == null) {
      throw new IllegalArgumentException("Paint Color cannot be null");
    }
    if (paintColorChosen.equals(newPaintColorChosen)) {
      return false;
    }
    paintColorChosen = newPaintColorChosen;
    appWindowInstance.onPaintColorChanged(paintColorChosen);
    return true;
  }

  /**
   * {@inheritDoc} Clears the canvas in its pop-up window. The paint color to paint the canvas
   * remains unchanged.
   */
  @Override
  public boolean canvasCleared() {
    appWindowInstance.onCanvasCleared();
    return true;
  }

  /**
   * {@inheritDoc} Hides its pop-up window. The paint color to paint the canvas remains unchanged.
   */
  @Override
  public boolean appRestarted() {
    appWindowInstance.onAppRestarted();
    return true;
  }

  @Override
  public String toString() {
    return "Current Paint Color: " + paintColorChosen;
  }

  /**
   * For testing purposes only
   */
  AppRules.PaintColor getCurrentPaintColor_forTest() {
    return paintColorChosen;
  }

  /**
   * For testing purposes only
   */
  Model getModel_forTest() {
    return model;
  }

  /**
   * For testing purposes only
   */
  AppWindow getAppWindowInstance_forTest() {
    return appWindowInstance;
  }
}
