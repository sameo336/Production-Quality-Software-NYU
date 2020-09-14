package edu.nyu.cs.pqs.ps4.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.nyu.cs.pqs.ps4.api.Connect4Model;
import edu.nyu.cs.pqs.ps4.impl.BasicMove;
import edu.nyu.cs.pqs.ps4.impl.FixedRules;
import edu.nyu.cs.pqs.ps4.impl.GameStarter;

/**
 * This defines the UI of the Main menu where the game is played. This also
 * contains the Actionlistner function.
 * 
 * @author Sanchit K
 *
 */
public final class MainMenu {
  private static MainMenu instance = null;

  public static MainMenu getInstance() {
    if (instance == null) {
      instance = new MainMenu();
    }
    return instance;
  }

  private static JFrame frame;

  protected MainMenu() {

  }

  public final JFrame getFrame() {
    return frame;
  }

  public final void showMainMenu(FixedRules.player playerId,
      JPanel[][] playingBoard, JButton[] buttonList, Connect4Model model) {
    if (playerId == FixedRules.player.PLAYER_1) {
      if (GameStarter.getChoice() == 0) {
        frame = new JFrame("Single Player Game -> You: Blue  Computer: Red");
      } else {
        frame =
            new JFrame(
                "Double Player Game: Alternate turns -> Player 1: Blue  Player 2: Red");
      }
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      GridLayout grid = new GridLayout(FixedRules.ROWS + 1, FixedRules.COLUMNS);
      frame.setLayout(grid);
      frame.setSize(130 * grid.getColumns(), 75 * grid.getRows());
      Container pane = frame.getContentPane();
      ActionListener listner = getListner(playerId, buttonList, model);
      for (int col = 0; col < FixedRules.COLUMNS; col++) {
        JButton button = new JButton("Grid " + String.valueOf(col));
        button.setName(String.valueOf(col));
        button.setBackground(Color.LIGHT_GRAY);
        button.addActionListener(listner);
        buttonList[col] = button;
        pane.add(button);
      }
      for (JButton but : buttonList) {
        but.setEnabled(false);
      }

      for (int i = 0; i < FixedRules.ROWS; i++) {
        for (int j = 0; j < FixedRules.COLUMNS; j++) {
          JPanel panel = new JPanel();
          playingBoard[i][j] = panel;
          pane.add(panel);
        }
      }

      for (JPanel[] row : playingBoard) {
        for (JPanel panel : row) {
          panel.setBackground(Color.WHITE);
        }
      }
      frame.setVisible(true);
    }
  }

  private ActionListener getListner(FixedRules.player playerId,
      JButton[] buttonList, Connect4Model model) {
    ActionListener listner = new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        String s = button.getName();
        int col = Integer.parseInt(s);
        int row = model.getCurrGameStatus().getCurrRow(col);

        if (buttonList.length == 0) {
        } else {
          for (JButton but : buttonList) {
            but.setEnabled(false);
          }
        }
        model.notifyMove(new BasicMove(model.whosNext(), row, col));
      }

    };
    return listner;
  }

}
