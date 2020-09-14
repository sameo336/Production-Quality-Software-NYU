package edu.nyu.cs.pqs.ps5.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Point;
import java.util.Random;

import javax.swing.JFrame;

import edu.nyu.cs.pqs.ps5.api.CanvasModel;
import edu.nyu.cs.pqs.ps5.api.Window;

/**
 * {@inheritDoc Window}
 * 
 * @author Sanchit K
 *
 */
public final class WindowImpl implements Window {

  private final CanvasModel model;
  private final int windowNumber;
  private static int counter;
  private DrawingBoard drawingBoard;
  // These are there only for testing and not used in the application.
  private int oldX;
  private int oldY;
  private int currentX;
  private int currentY;

  public int getOldX() {
    return oldX;
  }

  public int getOldY() {
    return oldY;
  }

  public int getCurrentX() {
    return currentX;
  }

  public int getCurrentY() {
    return currentY;
  }

  public WindowImpl(CanvasModel model) {
    this.model = model;
    counter++;
    windowNumber = counter;
    popUpGUI();
  }

  @Override
  public void
      updateDrawingBoard(int oldX, int oldY, int currentX, int currentY) {

    // These variables are to test if updateDrawingBoard is working properly or
    // not
    this.oldX = oldX;
    this.oldY = oldY;
    this.currentX = currentX;
    this.currentY = currentY;

    // Cannot test this as graphics is being set by the GUI part of the code
    // i.e. when the mouse click or motion event happens. Do
    // not count in code coverage
    if (drawingBoard.getGraphics() != null) {
      drawingBoard.getGraphics().setPaint(Color.black);
      drawingBoard.getGraphics().drawLine(oldX, oldY, currentX, currentY);
      drawingBoard.repaint();
    }
  }

  /**
   * The GUI on which the drawing is done.
   */
  private void popUpGUI() {
    JFrame frame = new JFrame("Canvas Painting: Window No " + windowNumber);
    Container content = frame.getContentPane();
    content.setLayout(new BorderLayout());
    drawingBoard = new DrawingBoard(this.model);
    content.add(drawingBoard, BorderLayout.CENTER);

    Point p = new Point();
    p.x = new Random().nextInt((400 - 25 + 1) + 25);
    p.y = new Random().nextInt((400 - 25 + 1) + 25);

    frame.setLocation(p);
    frame.setSize(400, 400);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setVisible(true);

  }

}
