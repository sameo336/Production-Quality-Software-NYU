package edu.nyu.cs.pqs.ps5.impl;

import java.util.ArrayList;
import java.util.List;

import edu.nyu.cs.pqs.ps5.api.CanvasModel;
import edu.nyu.cs.pqs.ps5.api.Window;

/**
 * {@inheritDoc CanvasModel}
 * 
 * @author Sanchit K
 *
 */
public final class CanvasModelImpl implements CanvasModel {

  private final Object modelLock;
  private List<Window> observerList;

  /**
   * created to test if the addobserver function works or not.
   * 
   * @return a copy of the observer list.
   */
  public List<Window> getObserverList() {
    List<Window> temp = new ArrayList<Window>(observerList);
    return temp;
  }

  private static CanvasModelImpl instance = null;

  /**
   * Singleton implementation of the CanvasModel. this assures that there is
   * only one canvas model.
   */
  private CanvasModelImpl() {
    modelLock = new Object();
    observerList = new ArrayList<Window>();
  }

  public static CanvasModelImpl getInstance() {
    if (instance == null) {
      instance = new CanvasModelImpl();
    }
    return instance;
  }

  @Override
  public final void addObserver(Window window) {
    synchronized (modelLock) {
      observerList.add(window);
    }
  }

  @Override
  public final void removeObserver(Window window) {
    // do nothing. To be added later if required.
  }

  @Override
  public final void notifyMove(int oldX, int oldY, int currentX, int currentY) {
    synchronized (modelLock) {
      for (Window observer : observerList) {
        observer.updateDrawingBoard(oldX, oldY, currentX, currentY);
      }
    }
  }

}
