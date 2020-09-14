package edu.nyu.cs.pqs.ps4.connectfour.impl;

import edu.nyu.cs.pqs.ps4.connectfour.api.Listener;
import edu.nyu.cs.pqs.ps4.connectfour.api.Player;
import edu.nyu.cs.pqs.ps4.connectfour.impl.Model;
import edu.nyu.cs.pqs.ps4.connectfour.view.GameView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.Before;
import org.junit.Rule;

public class ModelTest {
  private Model model;
  private int cols;
  private int rows;

  @Before
  public void setUp() {
    model = Model.getInstance();
    model.setPlayerOne_asHuman1ForTest(new HumanPlayer.Builder(PlayerType.HUMAN1).build());
    model.setPlayerTwo_asHuman2ForTest(new HumanPlayer.Builder(PlayerType.HUMAN2).build());
    cols = BoardDimensions.COLUMNS;
    rows = BoardDimensions.ROWS;
  }

  @After
  public void tearDown() {
    model.getBoard_forTest().resetBoard();
    model.setGameStarted_forTest(false);
    model.clearAllListeners_forTest();
    model = null;
  }

  @Rule
  public ExpectedException exceptionThrown = ExpectedException.none();

  @Test
  public void testAddListener() {
    Listener testListener = new Listener() {

      @Override
      public void modeMade(int columnNumber, int rowumber, Player player) {
        // do nothing
      }

      @Override
      public void gameWon(Player player, String name) {
        // do nothing
      }

      @Override
      public void gameStarted(String firstPlayerName, String secondPlayerName) {
        // do nothing
      }

      @Override
      public void gameReset() {
        // do nothing
      }

      @Override
      public void gameOverWhenBoardIsFull() {
        // do nothing
      }
    };
    assertEquals(0, model.getListeners().size());
    assertFalse(model.getListeners().contains(testListener));
    assertTrue(model.addListener(testListener));
    assertEquals(1, model.getListeners().size());
    assertTrue(model.getListeners().contains(testListener));
  }

  @Test
  public void testAddListener_nullListener() {
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("Listener cannot be null");
    model.addListener(null);
  }

  @Test
  public void testAddListener_alreadyExistingListener() {
    Listener testListener = new Listener() {

      @Override
      public void modeMade(int columnNumber, int rowumber, Player player) {
        // do nothing
      }

      @Override
      public void gameWon(Player player, String name) {
        // do nothing
      }

      @Override
      public void gameStarted(String firstPlayerName, String secondPlayerName) {
        // do nothing
      }

      @Override
      public void gameReset() {
        // do nothing
      }

      @Override
      public void gameOverWhenBoardIsFull() {
        // do nothing
      }
    };
    assertEquals(0, model.getListeners().size());
    assertFalse(model.getListeners().contains(testListener));
    assertTrue(model.addListener(testListener));
    assertEquals(1, model.getListeners().size());
    assertTrue(model.getListeners().contains(testListener));
    assertFalse(model.addListener(testListener));
    assertEquals(1, model.getListeners().size());
    assertTrue(model.getListeners().contains(testListener));
  }

  @Test
  public void testRemoveListener() {
    Listener testListener = new GameView(model);
    new GameView(model);
    new GameView(model);
    assertEquals(3, model.getListeners().size());
    assertTrue(model.getListeners().contains(testListener));
    assertTrue(model.removeListener(testListener));
    assertEquals(2, model.getListeners().size());
    assertFalse(model.getListeners().contains(testListener));
  }

  @Test
  public void testRemoveListener_onlyTwoListenersInListenerSet() {
    Listener testListener = new GameView(model);
    new GameView(model);
    assertEquals(2, model.getListeners().size());
    assertTrue(model.getListeners().contains(testListener));
    assertFalse(model.removeListener(testListener));
    assertEquals(2, model.getListeners().size());
    assertTrue(model.getListeners().contains(testListener));
  }

  @Test
  public void testRemoveListener_nullListener() {
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("Listener cannot be null");
    model.removeListener(null);
  }

  @Test
  public void testRemoveListener_listenerDoesNotExistInListenerSet() {
    new GameView(model);
    new GameView(model);
    new GameView(model);
    Listener testListener = new Listener() {

      @Override
      public void modeMade(int columnNumber, int rowumber, Player player) {
        // do nothing
      }

      @Override
      public void gameWon(Player player, String name) {
        // do nothing
      }

      @Override
      public void gameStarted(String firstPlayerName, String secondPlayerName) {
        // do nothing
      }

      @Override
      public void gameReset() {
        // do nothing
      }

      @Override
      public void gameOverWhenBoardIsFull() {
        // do nothing
      }
    };
    assertEquals(3, model.getListeners().size());
    assertFalse(model.getListeners().contains(testListener));
    assertFalse(model.removeListener(testListener));
    assertEquals(3, model.getListeners().size());
  }

  @Test
  public void testGetInstance() {
    Model modelInstance = Model.getInstance();
    Model modelInstanceTwo = Model.getInstance();
    assertNotNull(modelInstance);
    assertNotNull(modelInstanceTwo);
    assertEquals(modelInstance, modelInstanceTwo);
  }

  @Test
  public void testSetPlayers_nullPlayerTypeOfPlayerOne() {
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("Player Type cannot be null");
    model.setPlayers(null, "", PlayerType.HUMAN2, "");
  }

  @Test
  public void testSetPlayers_nullPlayerTypeOfPlayerTwo() {
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("Player Type cannot be null");
    model.setPlayers(PlayerType.HUMAN1, "", null, "");
  }

  @Test
  public void testSetPlayers_nullPlayerTypeOfBothPlayers() {
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("Player Type cannot be null");
    model.setPlayers(null, "", null, "");
  }

  @Test
  public void testSetPlayers_nullNameOfPlayerOne() {
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("Player Name cannot be null");
    model.setPlayers(PlayerType.HUMAN1, null, PlayerType.HUMAN2, "");
  }

  @Test
  public void testSetPlayers_nullNameOfPlayerTwo() {
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("Player Name cannot be null");
    model.setPlayers(PlayerType.HUMAN1, "", PlayerType.HUMAN2, null);
  }

  @Test
  public void testSetPlayers_nullNameOfBothPlayers() {
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("Player Name cannot be null");
    model.setPlayers(PlayerType.HUMAN1, null, PlayerType.HUMAN2, null);
  }

  @Test
  public void testSetPlayers_secondPlayerTypeIsHuman1() {
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("Player 2 cannot be of type HUMAN1");
    model.setPlayers(PlayerType.HUMAN1, "", PlayerType.HUMAN1, "");
  }

  @Test
  public void testSetPlayers_firstPlayerPlayerTypeIsNotHuman1() {
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("Player 1 has to be of type HUMAN1");
    model.setPlayers(PlayerType.COMPUTER, "", PlayerType.HUMAN2, "");
  }

  @Test
  public void testSetPlayers_playerOneAsHuman1WithNonEmptyAndPlayerTwoAsHuman2WithNonEmptyName() {
    model.setPlayers(PlayerType.HUMAN1, "humanPlayer1", PlayerType.HUMAN2, "human2");
    assertEquals(PlayerType.HUMAN1, model.getPlayerOne_forTest().getPlayerType());
    assertEquals("humanPlayer1", model.getPlayerOne_forTest().getPlayerName());
    assertEquals(PlayerType.HUMAN2, model.getPlayerTwo_forTest().getPlayerType());
    assertEquals("human2", model.getPlayerTwo_forTest().getPlayerName());
    assertEquals(PlayerType.HUMAN1, model.getCurrentPlayer_forTest().getPlayerType());
  }

  @Test
  public void
      testSetPlayers_playerOneAsHuman1WithNonEmptyNameAndPlayerTwoAsComputerWithEmptyName() {
    model.setPlayers(PlayerType.HUMAN1, "human1", PlayerType.COMPUTER, "");
    assertEquals(PlayerType.HUMAN1, model.getPlayerOne_forTest().getPlayerType());
    assertEquals("human1", model.getPlayerOne_forTest().getPlayerName());
    assertEquals(PlayerType.COMPUTER, model.getPlayerTwo_forTest().getPlayerType());
    assertEquals("", model.getPlayerTwo_forTest().getPlayerName());
    assertEquals(PlayerType.HUMAN1, model.getCurrentPlayer_forTest().getPlayerType());
  }

  @Test
  public void
      testSetPlayers_playerOneAsHuman1WithNonEmptyNameAndPlayerTwoAsComputerWithNonEmptyName() {
    model.setPlayers(PlayerType.HUMAN1, "human1", PlayerType.COMPUTER, "computer");
    assertEquals(PlayerType.HUMAN1, model.getPlayerOne_forTest().getPlayerType());
    assertEquals("human1", model.getPlayerOne_forTest().getPlayerName());
    assertEquals(PlayerType.COMPUTER, model.getPlayerTwo_forTest().getPlayerType());
    assertEquals("", model.getPlayerTwo_forTest().getPlayerName());
    assertEquals(PlayerType.HUMAN1, model.getCurrentPlayer_forTest().getPlayerType());
  }

  @Test
  public void testAddChipToColumn_invalidColumn() {
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("Invalid column number");
    model.addChipToColumn(cols);
  }

  @Test
  public void testAddChipToColumn_whenGameHasNotYetStarted() {
    exceptionThrown.expect(IllegalStateException.class);
    exceptionThrown.expectMessage("Game has not yet started");
    model.addChipToColumn(0);
  }

  @Test
  public void testAddChipToColumn_validColumn() {
    Player testPlayer = new HumanPlayer.Builder(PlayerType.HUMAN1).build();
    model.setGameStarted_forTest(true);
    model.setCurrentPlayer_forTest(testPlayer);
    model.setPlayerOne_asHuman1ForTest(testPlayer);
    assertEquals(0, model.getBoard_forTest().getNumberOfChipsInColumn(cols - 1));
    assertEquals(PlayerType.HUMAN1, model.getCurrentPlayer_forTest().getPlayerType());
    assertTrue(model.addChipToColumn(cols - 1));
    assertEquals(1, model.getBoard_forTest().getNumberOfChipsInColumn(cols - 1));
    assertEquals(PlayerType.HUMAN2, model.getCurrentPlayer_forTest().getPlayerType());
  }

  @Test
  public void testAddChipToColumn_validColumnResultingInGameOver() {
    model.setGameStarted_forTest(true);
    for (int i = 0; i < cols - 1; i++) {
      model.getBoard_forTest().setChipsDroppedInColumn_forTest(i, rows,
          model.getCurrentPlayer_forTest());
    }
    model.getBoard_forTest().setChipsDroppedInColumn_forTest(cols - 1, rows - 1,
        model.getCurrentPlayer_forTest());
    assertEquals(rows - 1, model.getBoard_forTest().getNumberOfChipsInColumn(cols - 1));
    assertTrue(model.addChipToColumn(cols - 1));
    assertEquals(rows, model.getBoard_forTest().getNumberOfChipsInColumn(cols - 1));
    assertFalse(model.getGameStarted_forTest());
  }

  @Test
  public void testResetGame() {
    model.getBoard_forTest().setChipsDroppedInColumn_forTest(0, 1,
        model.getCurrentPlayer_forTest());
    assertTrue(model.resetGame());
    assertEquals(0, model.getBoard_forTest().getNumberOfChipsInColumn(0));
  }

  @Test
  public void testStartGame_whenGameHasAlreadyStarted() {
    model.setGameStarted_forTest(true);
    exceptionThrown.expect(IllegalStateException.class);
    exceptionThrown.expectMessage("Game has already started");
    model.startGame("", "");
  }

  @Test
  public void testStartGame_nullNameOfPlayerTwo() {
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("Player Name cannot be null");
    model.startGame("PlayerOneName", null);
  }

  @Test
  public void testStartGame_nullNameOfPlayerOne() {
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("Player Name cannot be null");
    model.startGame(null, "PlayerTwoName");
  }

  @Test
  public void testStartGame_nullNameOfBothPlayers() {
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("Player Name cannot be null");
    model.startGame(null, null);
  }

  @Test
  public void testStartGame() {
    assertFalse(model.getGameStarted_forTest());
    assertTrue(model.startGame("", ""));
    assertTrue(model.getGameStarted_forTest());
  }
}