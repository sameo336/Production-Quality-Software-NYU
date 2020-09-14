package edu.nyu.cs.pqs.ps4.connectfour.view;

import edu.nyu.cs.pqs.ps4.connectfour.api.Listener;
import edu.nyu.cs.pqs.ps4.connectfour.api.Player;
import edu.nyu.cs.pqs.ps4.connectfour.impl.BoardDimensions;
import edu.nyu.cs.pqs.ps4.connectfour.impl.Model;
import edu.nyu.cs.pqs.ps4.connectfour.impl.PlayerType;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * The GameView class implements the Listener interface and displays the current state of the game
 * to the Human player(s) at every point of time. An instance of the thread-unsafe GameView class
 * adds itself as a Listener and can unsubscribe itself provided there are at least three subscribed
 * Listeners when it requests for unsubscription. The GameView instance can also restart the game.
 * 
 * @author Anisha Bhatla
 */
public class GameView implements Listener {
  private final Model model;
  private final int rows = BoardDimensions.ROWS;
  private final int columns = BoardDimensions.COLUMNS;
  private final JFrame messageFrame;
  private final JFrame boardFrame;
  private final JPanel topPanel;
  private final JPanel centerPanel;
  private final JPanel bottomPanel;
  private final JButton[] dropButtons;
  private final JPanel[][] squareGrid;
  private final GridLayout dropButtonGridLayout;
  private final GridLayout squarePanelGridLayout;
  private final JLabel nextTurn;
  private final JButton resetButton;
  private final JButton unsubscribeButton;
  private final JPanel mainPanelCenterPanel;
  private String firstPlayerName = "";
  private String secondPlayerName = "";

  public GameView(Model model) {
    if (model == null) {
      throw new IllegalArgumentException("Model instance cannot be null");
    }
    this.model = model;
    this.model.addListener(this);
    messageFrame = new JFrame("Connect Four Game");
    boardFrame = new JFrame("Connect Four Game");
    messageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainPanelCenterPanel = new JPanel();
    mainPanelCenterPanel.setLayout(new BorderLayout());
    topPanel = new JPanel();
    dropButtonGridLayout = new GridLayout(0, columns);
    topPanel.setLayout(dropButtonGridLayout);
    dropButtons = new JButton[columns];
    for (int i = 0; i < columns; i++) {
      dropButtons[i] = new JButton("Drop");
      int index = i;
      dropButtons[i].addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent event) {
          onDrop(index);
        }
      });
      topPanel.add(dropButtons[i]);
    }
    centerPanel = new JPanel();
    squarePanelGridLayout = new GridLayout(rows, columns, 2, 2);
    centerPanel.setLayout(squarePanelGridLayout);
    squareGrid = new JPanel[rows][columns];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        squareGrid[i][j] = createSquare(DesignatedGameColor.BACKGROUND.getColor(),
            DesignatedGameColor.BORDER.getColor(), 50);
        centerPanel.add(squareGrid[i][j]);
      }
    }
    bottomPanel = new JPanel();
    bottomPanel.setLayout(new BorderLayout());
    nextTurn =
        new JLabel("Turn: " + (firstPlayerName.trim().isEmpty() ? "Player One" : firstPlayerName));
    bottomPanel.add(nextTurn, BorderLayout.WEST);
    resetButton = new JButton("Restart Game");
    resetButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent event) {
        onReset();
      }
    });
    unsubscribeButton = new JButton(" Unsubsribe From Game ");
    unsubscribeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        onUnsubscribeFromGame();
      }
    });
    bottomPanel.add(resetButton);
    bottomPanel.add(unsubscribeButton, BorderLayout.SOUTH);
    mainPanelCenterPanel.add(topPanel, BorderLayout.NORTH);
    mainPanelCenterPanel.add(centerPanel, BorderLayout.CENTER);
    mainPanelCenterPanel.add(bottomPanel, BorderLayout.SOUTH);
    boardFrame.getContentPane().add(mainPanelCenterPanel);
    boardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    boardFrame.pack();
    boardFrame.setVisible(false);
  }

  private JPanel createSquare(Color backgroundColor, Color borderColor, int size) {
    JPanel panel = new JPanel();
    panel.setBackground(backgroundColor);
    panel.setBorder(BorderFactory.createLineBorder(borderColor));
    panel.setPreferredSize(new Dimension(size, size));
    return panel;
  }

  private void onReset() {
    model.resetGame();
  }

  private void onUnsubscribeFromGame() {
    if (!model.removeListener(this)) {
      JOptionPane.showMessageDialog(messageFrame,
          " Sorry! Minimum one window required to remain open. ", "Error: Cannot Unsubsribe",
          JOptionPane.INFORMATION_MESSAGE);
    } else {
      boardFrame.setVisible(false);
    }
  }

  private void onDrop(int columnNumber) {
    model.addChipToColumn(columnNumber);
  }

  /**
   * {@inheritDoc}
   * 
   * @throws IllegalArgumentException if name of first or second player is null
   */
  @Override
  public void gameStarted(String firstPlayerName, String secondPlayerName) {
    if ((firstPlayerName == null) || (secondPlayerName == null)) {
      throw new IllegalArgumentException("Name cannot be null");
    }
    this.firstPlayerName = firstPlayerName.trim();
    this.secondPlayerName = secondPlayerName.trim();
    nextTurn
        .setText("Turn: " + (firstPlayerName.trim().isEmpty() ? "Player One" : firstPlayerName));
    boardFrame.pack();
    boardFrame.setVisible(true);
  }

  /**
   * {@inheritDoc} Colors the selected square with player's color.
   * 
   * @throws IllegalArgumentException if row or column number is invalid or player instance is null
   */
  @Override
  public void modeMade(int column, int row, Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null");
    }
    if (column < 0 || column >= BoardDimensions.COLUMNS) {
      throw new IllegalArgumentException("Invalid Column Number");
    }
    if (row < 0 || row >= BoardDimensions.ROWS) {
      throw new IllegalArgumentException("Invalid Row Number");
    }
    String textToBeDisplayed = "";
    if (player.getPlayerID() == PlayerType.HUMAN1.getID()) {
      squareGrid[row][column].setBackground(DesignatedGameColor.PLAYER1.getColor());
      textToBeDisplayed =
          " Turn: " + (!secondPlayerName.trim().isEmpty() ? secondPlayerName : "Player Two");
      nextTurn.setText(textToBeDisplayed);
    } else if (player.getPlayerID() == PlayerType.HUMAN2.getID()) {
      squareGrid[row][column].setBackground(DesignatedGameColor.PLAYER2.getColor());
      textToBeDisplayed =
          " Turn: " + (!firstPlayerName.trim().isEmpty() ? firstPlayerName : "Player One");
      nextTurn.setText(textToBeDisplayed);
    }
    if (row == 0) {
      dropButtons[column].setEnabled(false);
    }
  }

  /**
   * {@inheritDoc} Disables all drop buttons, updates game status bar and a popup appears with the
   * winner information.
   * 
   * @throws IllegalArgumentException if provided player or name is null
   */
  @Override
  public void gameWon(Player player, String name) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null");
    }
    if (name == null) {
      throw new IllegalArgumentException("Name cannot be null");
    }
    for (JButton dropButton : dropButtons) {
      dropButton.setEnabled(false);
    }
    if (player.getPlayerType() == PlayerType.HUMAN1) {
      nextTurn.setText(name.trim().isEmpty() ? "Player One wins!" : name + " wins!");
      JOptionPane.showMessageDialog(messageFrame,
          name.trim().isEmpty() ? "Player One wins!" : name + " wins!", "We have a winner",
          JOptionPane.INFORMATION_MESSAGE);
    } else if (player.getPlayerType() == PlayerType.HUMAN2) {
      nextTurn.setText(name.trim().isEmpty() ? "Player Two wins!" : name + " wins!");
      JOptionPane.showMessageDialog(messageFrame,
          name.trim().isEmpty() ? "Player Two wins!" : name + " wins!", "We have a winner",
          JOptionPane.INFORMATION_MESSAGE);
    } else if (player.getPlayerType() == PlayerType.COMPUTER) {
      nextTurn.setText("Computer Wins!");
      JOptionPane.showMessageDialog(messageFrame, "Computer wins!", "We have a winner",
          JOptionPane.INFORMATION_MESSAGE);
    }
  }

  /**
   * {@inheritDoc} Disables all drop buttons.
   */
  @Override
  public void gameOverWhenBoardIsFull() {
    nextTurn.setText("Game Over");
    for (JButton dropButton : dropButtons) {
      dropButton.setEnabled(false);
    }
  }

  /**
   * {@inheritDoc} Resets all the color boxes, enables all drop buttons and hides the game view
   * window.
   */
  @Override
  public void gameReset() {
    String textToBeDisplayed =
        " Turn: " + (!firstPlayerName.trim().isEmpty() ? firstPlayerName : "Player One");
    nextTurn.setText(textToBeDisplayed);
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        squareGrid[i][j].setBackground(DesignatedGameColor.BACKGROUND.getColor());
        squareGrid[i][j]
            .setBorder(BorderFactory.createLineBorder(DesignatedGameColor.BORDER.getColor()));
      }
    }
    for (JButton dropButton : dropButtons) {
      dropButton.setEnabled(true);
    }
    firstPlayerName = "";
    secondPlayerName = "";
    boardFrame.setVisible(false);
  }
}