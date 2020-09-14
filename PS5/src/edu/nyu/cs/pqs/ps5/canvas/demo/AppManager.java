package edu.nyu.cs.pqs.ps5.canvas.demo;

import edu.nyu.cs.pqs.ps5.canvas.view.WindowCountSelection;

/**
 * The AppManager class is used to trigger the initialization and initiation of the Canvas
 * Application which is a multi-window application where users can paint on a canvas using the
 * mouse. All windows see changes made to the canvas in any other window.
 * 
 * @author Anisha Bhatla
 */
public class AppManager {

  private void initializeAndStart() {
    WindowCountSelection.getInstanceAndDisplayWindowSelectionPage();
  }

  public static void main(String[] args) {
    new AppManager().initializeAndStart();
  }
}
