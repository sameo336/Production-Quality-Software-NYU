package edu.nyu.cs.pqs.ps5.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JComponent;

import edu.nyu.cs.pqs.ps5.api.CanvasModel;

/**
 * This class extends JComponent and defines the board on which the drawings are
 * going to be displayed. The constructor contains the mouse click and mouse
 * motion event listeners.
 * 
 * @author Sanchit K
 *
 */
@SuppressWarnings("serial")
final class DrawingBoard extends JComponent {

  private Image image;
  private Graphics2D graphics;

  public Graphics2D getGraphics() {
    return graphics;
  }

  private int oldX;
  private int oldY;
  private int currentX;
  private int currentY;
  private final CanvasModel model;

  DrawingBoard(CanvasModel model1) {
    this.model = model1;
    setDoubleBuffered(false);
    addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent event) {
        oldX = event.getX();
        oldY = event.getY();
      }
    });

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent event) {
        currentX = event.getX();
        currentY = event.getY();

        if (graphics != null) {
          graphics.drawLine(oldX, oldY, currentX, currentY);
          model.notifyMove(oldX, oldY, currentX, currentY);
          repaint();
          oldX = currentX;
          oldY = currentY;
        }
      }
    });
  }

  protected void paintComponent(Graphics graphcs) {
    if (image == null) {
      image = createImage(getSize().width, getSize().height);
      graphics = (Graphics2D) image.getGraphics();
      graphics.setPaint(Color.white);
      graphics.fillRect(0, 0, getSize().width, getSize().height);
      graphics.setPaint(Color.black);
      repaint();
    }
    graphcs.drawImage(image, 0, 0, null);
  }

}
