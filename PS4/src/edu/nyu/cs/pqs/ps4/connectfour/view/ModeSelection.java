package edu.nyu.cs.pqs.ps4.connectfour.view;

import edu.nyu.cs.pqs.ps4.connectfour.api.Listener;
import edu.nyu.cs.pqs.ps4.connectfour.api.Player;
import edu.nyu.cs.pqs.ps4.connectfour.impl.Model;
import edu.nyu.cs.pqs.ps4.connectfour.impl.PlayerType;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * The ModeSelection class is used to choose a Game Mode for the Connect Four Game. A Human user can
 * select playing the game against the Computer or another Human from an interactive GUI. The user
 * can also add names to the players. In case of Human Vs. Computer mode, the name of the second
 * player is discarded and an empty string is set as the name of the Computer. This thread-unsafe
 * class follows the Singleton pattern, i.e., only a single instance of the ModeSelection class can
 * be created. The instance also adds itself as a Listener to the game.
 * 
 * @author Anisha Bhatla
 *
 */
public class ModeSelection {
  private static ModeSelection singletonInstance = null;
  private final Model model;
  private final JPanel mainPanel;
  private final JFrame modeSelectionFrame;
  private final JPanel mainPanelTopPanel;
  private final JRadioButton playAgainstHuman;
  private final JRadioButton playAgainstComputer;
  private final ButtonGroup radioButtonGroup;
  private final JLabel opponentPrompt;
  private final JLabel firstPlayerNamePrompt;
  private final JLabel secondPlayerNamePrompt;
  private final JTextField firstNameTextField;
  private final JTextField secondNameTextField;
  private final JButton startButton;
  private String firstPlayerName = "";
  private String secondPlayerName = "";
  private boolean hasSelectedHuman = true;

  /**
   * Private constructor to prevent instantiation from outside the class
   */
  private ModeSelection() {
    model = Model.getInstance();
    mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());
    modeSelectionFrame = new JFrame("Connect Four Game");
    mainPanelTopPanel = new JPanel();
    mainPanelTopPanel.setLayout(new GridLayout(9, 2, 100, 10));
    opponentPrompt = new JLabel(" Please choose a mode: ");
    playAgainstHuman = new JRadioButton(" Human vs. Human ");
    playAgainstHuman.setSelected(true);
    playAgainstHuman.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        hasSelectedHuman = true;
      }
    });
    playAgainstComputer = new JRadioButton(" Human vs. Computer ");
    playAgainstComputer.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        hasSelectedHuman = false;
      }
    });
    startButton = new JButton("Start");
    startButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        onStartGame();
      }
    });
    radioButtonGroup = new ButtonGroup();
    radioButtonGroup.add(playAgainstHuman);
    radioButtonGroup.add(playAgainstComputer);
    firstPlayerNamePrompt = new JLabel(" Name of first player (optional) : ");
    firstNameTextField = new JTextField();
    secondPlayerNamePrompt = new JLabel(" Name of second player (optional) : ");
    secondNameTextField = new JTextField();
    mainPanelTopPanel.add(opponentPrompt);
    mainPanelTopPanel.add(playAgainstHuman);
    mainPanelTopPanel.add(playAgainstComputer);
    mainPanelTopPanel.add(firstPlayerNamePrompt);
    mainPanelTopPanel.add(firstNameTextField);
    mainPanelTopPanel.add(secondPlayerNamePrompt);
    mainPanelTopPanel.add(secondNameTextField);
    mainPanelTopPanel.add(startButton);
    mainPanel.add(mainPanelTopPanel, BorderLayout.NORTH);
    modeSelectionFrame.getContentPane().add(mainPanelTopPanel);
    modeSelectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    model.addListener(new Listener() {

      @Override
      public void modeMade(int columnNumber, int rowNumber, Player player) {
        // do nothing
      }

      @Override
      public void gameWon(Player player, String name) {
        // do nothing
      }

      /**
       * {@inheritDoc} Hides the mode selection window.
       */
      @Override
      public void gameStarted(String firstPlayerName, String secondPlayerName) {
        modeSelectionFrame.setVisible(false);
      }

      /**
       * {@inheritDoc} Displays the mode selection window to choose between Human Vs. Human and
       * Human Vs. Computer modes
       */
      @Override
      public void gameReset() {
        firstPlayerName = "";
        secondPlayerName = "";
        firstNameTextField.setText("");
        secondNameTextField.setText("");
        playAgainstHuman.setSelected(true);
        hasSelectedHuman = true;
        modeSelectionFrame.setVisible(true);
      }

      @Override
      public void gameOverWhenBoardIsFull() {
        // do nothing
      }
    });
    modeSelectionFrame.pack();
    modeSelectionFrame.setVisible(true);
  }

  /**
   * Creates a new instance of the ModeSelection class if none was created earlier.
   */
  public static final void setUpAndStartGame() {
    if (singletonInstance == null) {
      singletonInstance = new ModeSelection();
    }
  }

  private void onStartGame() {
    firstPlayerName = firstNameTextField.getText().trim();
    secondPlayerName = secondNameTextField.getText().trim();
    if (hasSelectedHuman) {
      model.setPlayers(PlayerType.HUMAN1, firstPlayerName, PlayerType.HUMAN2, secondPlayerName);
    } else {
      model.setPlayers(PlayerType.HUMAN1, firstPlayerName, PlayerType.COMPUTER, "");
    }
    if (model.getListeners().size() < 2) {
      model.addListener(new GameView(model));
    }
    modeSelectionFrame.setVisible(false);
    firstNameTextField.setText("");
    secondNameTextField.setText("");
    model.startGame(firstPlayerName, secondPlayerName);
  }
}
