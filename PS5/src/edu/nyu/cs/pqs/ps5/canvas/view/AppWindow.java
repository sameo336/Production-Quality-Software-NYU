package edu.nyu.cs.pqs.ps5.canvas.view;

import edu.nyu.cs.pqs.ps5.canvas.api.Listener;
import edu.nyu.cs.pqs.ps5.canvas.impl.AppRules;
import edu.nyu.cs.pqs.ps5.canvas.impl.Model;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * The AppWindow class displays the current state of the canvas application in the form of a pop-up
 * window. The different changes monitored and displayed by this thread-unsafe class include
 * painting move made, paint color changed, canvas board cleared and application restarted.
 * 
 * @author Anisha Bhatla
 */
public class AppWindow {

  private final Model model;
  private final Listener listenerInstance;
  private final CanvasBoard board;
  private final JFrame mainFrame;
  private final JFrame messageFrame;
  private final JPanel optionsPanel;
  private final JButton unsubscribeButton;
  private final JButton clearCanvasButton;
  private final JButton restartAppButton;
  private final JLabel currentPaintColor;
  private JButton paintColorButton;

  public AppWindow(Model model, AppRules.PaintColor paintColorChosen, Listener listenerInstance) {
    if (model == null) {
      throw new IllegalArgumentException("Model instance cannot be null");
    }
    if (paintColorChosen == null) {
      throw new IllegalArgumentException("Paint Color cannot be null");
    }
    if (listenerInstance == null) {
      throw new IllegalArgumentException("Listener cannot be null");
    }
    this.model = model;
    this.listenerInstance = listenerInstance;
    board = new CanvasBoard(paintColorChosen);
    mainFrame = new JFrame("Canvas App Window");
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setSize(950, 550);
    Container content = mainFrame.getContentPane();
    content.setLayout(new BorderLayout());
    content.add(board, BorderLayout.CENTER);
    currentPaintColor = new JLabel("Current Color: " + paintColorChosen.getColorString());
    messageFrame = new JFrame("Canvas App");
    messageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    unsubscribeButton = new JButton("Unsubscribe");
    unsubscribeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        onUnsubscribeButtonPressed();
      }
    });
    clearCanvasButton = new JButton("Clear");
    clearCanvasButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent event) {
        onClearCanvasButtonPressed();
      }
    });
    restartAppButton = new JButton("Restart App");
    restartAppButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent event) {
        onRestartAppButtonPressed();
      }
    });
    optionsPanel = new JPanel();
    optionsPanel.add(currentPaintColor);
    for (AppRules.PaintColor paintColor : AppRules.PaintColor.values()) {
      paintColorButton = new JButton(paintColor.getColorString());
      paintColorButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent event) {
          onColorChange(paintColor);
        }
      });
      optionsPanel.add(paintColorButton);
    }
    optionsPanel.add(clearCanvasButton);
    optionsPanel.add(unsubscribeButton);
    optionsPanel.add(restartAppButton);
    content.add(optionsPanel, BorderLayout.SOUTH);
    Point randomPoint = new Point();
    randomPoint.x = new Random().nextInt(((mainFrame.getWidth() - 25 + 1)));
    randomPoint.y = new Random().nextInt(((mainFrame.getHeight() - 25 + 1)));
    mainFrame.setLocation(randomPoint);
    mainFrame.setVisible(true);
  }

  /**
   * The CanvasBoard class extends the Swing JComponent and is used by the AppWindow class to
   * display the current state of the canvas. Whenever a user paints on the canvas using the mouse,
   * this class notifies the model to inform all listeners of this painting move in order to reflect
   * it. The class is also used to clear the canvas board and to set the paint color of the canvas
   * whenever the user selects a paint color.
   * 
   * @author Anisha Bhatla
   */
  private class CanvasBoard extends JComponent {

    private static final long serialVersionUID = 1L;

    private Image image;
    private Graphics2D boardGraphics;
    private AppRules.PaintColor defaultColor;
    private int oldXCoordinate;
    private int oldYCoordinate;
    private int newXCoordinate;
    private int newYCoordinate;

    private CanvasBoard(AppRules.PaintColor defaultColor) {
      this.defaultColor = defaultColor;
      setDoubleBuffered(false);
      addMouseListener(new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent mousePressedEvent) {
          oldXCoordinate = mousePressedEvent.getX();
          oldYCoordinate = mousePressedEvent.getY();
        }
      });

      addMouseMotionListener(new MouseMotionAdapter() {
        @Override
        public void mouseDragged(MouseEvent mouseDraggedEvent) {
          newXCoordinate = mouseDraggedEvent.getX();
          newYCoordinate = mouseDraggedEvent.getY();
          model.makePaintingMove(oldXCoordinate, oldYCoordinate, newXCoordinate, newYCoordinate);
          oldXCoordinate = newXCoordinate;
          oldYCoordinate = newYCoordinate;
        }
      });
    }

    @Override
    protected void paintComponent(Graphics graphic) {
      if (graphic == null) {
        throw new IllegalArgumentException("Graphic instance cannot be null");
      }
      if (image == null) {
        image = createImage(getSize().width, getSize().height);
        boardGraphics = (Graphics2D) image.getGraphics();
        boardGraphics.setPaint(Color.WHITE);
        boardGraphics.fillRect(0, 0, getSize().width, getSize().height);
        boardGraphics.setPaint(defaultColor.getColor());
        repaint();
      }
      graphic.drawImage(image, 0, 0, null);
    }

    private void setBoardColor(AppRules.PaintColor newPaintColorChosen) {
      if (newPaintColorChosen == null) {
        throw new IllegalArgumentException("Paint Color cannot be null");
      }
      defaultColor = newPaintColorChosen;
      if (boardGraphics != null) {
        boardGraphics.setPaint(defaultColor.getColor());
      }
    }

    private void paintBoard(int oldXCoordinate, int oldYCoordinate, int newXCoordinate,
        int newYCoordinate) {
      this.oldXCoordinate = oldXCoordinate;
      this.oldYCoordinate = oldYCoordinate;
      this.newXCoordinate = newXCoordinate;
      this.newYCoordinate = newYCoordinate;

      if (boardGraphics != null) {
        boardGraphics.drawLine(this.oldXCoordinate, this.oldYCoordinate, this.newXCoordinate,
            this.newYCoordinate);
        repaint();
      }
    }

    private void clearBoard() {
      if (boardGraphics != null) {
        boardGraphics.setPaint(Color.WHITE);
        boardGraphics.fillRect(0, 0, getSize().width, getSize().height);
        boardGraphics.setPaint(defaultColor.getColor());
        repaint();
      }
    }
  }

  private void onUnsubscribeButtonPressed() {
    if (!model.removeListener(listenerInstance)) {
      JOptionPane.showMessageDialog(messageFrame,
          " Sorry! Minimum one window required to remain open. ", "Error: Cannot Unsubscribe",
          JOptionPane.INFORMATION_MESSAGE);
    } else {
      mainFrame.setVisible(false);
    }
  }

  private void onColorChange(AppRules.PaintColor newPaintColorChosen) {
    if (!board.defaultColor.equals(newPaintColorChosen)) {
      model.changePaintColor(newPaintColorChosen);
    }
  }

  private void onClearCanvasButtonPressed() {
    model.clearCanvas();
  }

  private void onRestartAppButtonPressed() {
    mainFrame.setVisible(false);
    model.restartApp();
  }

  /**
   * Reflects and displays the painting move made by the user in its pop-up window. The paint color
   * to paint the canvas remains unchanged
   * 
   * @param oldXCoordinate the X co-ordinate of the point wherein the painting move starts
   * @param oldYCoordinate the Y co-ordinate of the point wherein the painting move starts
   * @param newXCoordinate the X co-ordinate of the point wherein the painting move ends
   * @param newYCoordinate the Y co-ordinate of the point wherein the painting move ends
   * @return true if the given painting move is displayed successfully
   */
  public boolean onPaintingMoveMade(int oldXCoordinate, int oldYCoordinate, int newXCoordinate,
      int newYCoordinate) {
    board.paintBoard(oldXCoordinate, oldYCoordinate, newXCoordinate, newYCoordinate);
    return true;
  }

  /**
   * Updates the paint color to be used to paint the canvas with the given paint color, if a new
   * paint color is chosen by the user
   * 
   * @param newPaintColorChosen the new paint color chosen to paint the canvas with
   * @return true if the paint color is changed successfully, false if the new paint color is same
   *         as the old paint color
   * @throws IllegalArgumentException if the new paint color is null
   */
  public boolean onPaintColorChanged(AppRules.PaintColor newPaintColorChosen) {
    if (newPaintColorChosen == null) {
      throw new IllegalArgumentException("Paint Color cannot be null");
    }
    if (board.defaultColor.equals(newPaintColorChosen)) {
      return false;
    }
    currentPaintColor.setText("Current Color: " + newPaintColorChosen.getColorString());
    board.setBoardColor(newPaintColorChosen);
    return true;
  }

  /**
   * Clears the canvas in its pop-up window. The paint color to paint the canvas remains unchanged
   * 
   * @return true if the canvas board clears successfully
   */
  public boolean onCanvasCleared() {
    board.clearBoard();
    return true;
  }

  /**
   * Hides its pop-up window upon restart of the application. The paint color to paint the canvas
   * remains unchanged
   * 
   * @return true if the application restarts successfully
   */
  public boolean onAppRestarted() {
    mainFrame.setVisible(false);
    return true;
  }

  /**
   * For testing purposes only
   */
  public Model getModel_forTest() {
    return model;
  }

  /**
   * For testing purposes only
   */
  public CanvasBoard getBoard_forTest() {
    return board;
  }

  /**
   * For testing purposes only
   */
  public AppRules.PaintColor getBoardDefaultColor_forTest() {
    return board.defaultColor;
  }

  /**
   * For testing purposes only
   */
  public Listener getListenerInstance_forTest() {
    return listenerInstance;
  }

  /**
   * For testing purposes only
   */
  public int getOldXCoordinate_forTest() {
    return board.oldXCoordinate;
  }

  /**
   * For testing purposes only
   */
  void setOldXCoordinate_forTest(int oldXCoordinate) {
    board.oldXCoordinate = oldXCoordinate;
  }

  /**
   * For testing purposes only
   */
  public int getOldYCoordinate_forTest() {
    return board.oldYCoordinate;
  }

  /**
   * For testing purposes only
   */
  void setOldYCoordinate_forTest(int oldYCoordinate) {
    board.oldYCoordinate = oldYCoordinate;
  }

  /**
   * For testing purposes only
   */
  public int getNewXCoordinate_forTest() {
    return board.newXCoordinate;
  }

  /**
   * For testing purposes only
   */
  public int getNewYCoordinate_forTest() {
    return board.newYCoordinate;
  }
}
