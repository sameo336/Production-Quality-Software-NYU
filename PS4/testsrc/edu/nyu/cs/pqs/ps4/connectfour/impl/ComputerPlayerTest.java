package edu.nyu.cs.pqs.ps4.connectfour.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ComputerPlayerTest {

  private ComputerPlayer computerPlayer;
  private ComputerPlayer computerPlayerTwo;
  private HumanPlayer humanPlayer;
  private Board board;
  private int cols;

  @Before
  public void setUp() {
    computerPlayer =
        (ComputerPlayer) Model.getInstance().setPlayerTwo_asComputerForTest(new ComputerPlayer());
    computerPlayerTwo =
        (ComputerPlayer) Model.getInstance().setPlayerTwo_asComputerForTest(new ComputerPlayer());
    humanPlayer = (HumanPlayer) Model.getInstance()
        .setPlayerOne_asHuman1ForTest(new HumanPlayer.Builder(PlayerType.HUMAN1).build());
    board = Board.getInstance();
    cols = BoardDimensions.COLUMNS;
  }

  @After
  public void tearDown() {
    computerPlayer = null;
    computerPlayerTwo = null;
    humanPlayer = null;
    board.resetBoard();
    board = null;
  }

  @Rule
  public ExpectedException exceptionThrown = ExpectedException.none();

  @Test
  public void testGetNextMove_nullBoardInstance() {
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("Board instance cannot be null");
    computerPlayer.getNextMove(null);
  }

  @Test
  public void testGetNextMove_randomMove() {
    board.setChipsDroppedInColumn_forTest(0, 1, computerPlayer);
    board.setChipsDroppedInColumn_forTest(2, 1, computerPlayer);
    int columnIndex = computerPlayer.getNextMove(board);
    assertTrue(columnIndex >= 0 && columnIndex < cols);
  }

  @Test
  public void testGetNextMove_moveWhenOnlyOneColumnIsNotFull() {
    for (int i = 0; i < cols - 1; i++) {
      board.setChipsDroppedInColumn_forTest(i, BoardDimensions.ROWS, computerPlayer);
    }
    int columnIndex = computerPlayer.getNextMove(board);
    assertEquals(cols - 1, columnIndex);
  }

  @Test
  public void testGetNextMove_winningMove() {
    board.setChipsDroppedInColumn_forTest(cols - 1, 3, computerPlayer);
    assertEquals(cols - 1, computerPlayer.getNextMove(board));
  }

  @Test
  public void testEquals_twoComputerPlayers() {
    assertTrue(computerPlayer.equals(computerPlayerTwo));
    assertEquals(computerPlayer.getPlayerID(), computerPlayerTwo.getPlayerID());
    assertEquals(computerPlayer.getPlayerType(), computerPlayerTwo.getPlayerType());
    assertEquals(computerPlayer.getPlayerName(), computerPlayerTwo.getPlayerName());
  }

  @Test
  public void testEquals_sameComputerPlayerInstance() {
    assertTrue(computerPlayer.equals(computerPlayer));
  }

  @Test
  public void testEquals_computerPlayerAndNull() {
    assertFalse(computerPlayer.equals(null));
  }

  @Test
  public void testEquals_computerPlayerAndHumanPlayer() {
    assertFalse(computerPlayer.equals(humanPlayer));
  }

  @Test
  public void testHashCode_twoComputerPlayers() {
    assertEquals(computerPlayer.hashCode(), computerPlayerTwo.hashCode());
  }

  @Test
  public void testHashCode_computerPlayerAndHumanPlayer() {
    assertNotEquals(computerPlayer.hashCode(), humanPlayer.hashCode());
  }

  @Test
  public void testEqualsAndHashCode_twoComputerPlayers() {
    assertEquals(computerPlayer, computerPlayerTwo);
    assertEquals(computerPlayer.hashCode(), computerPlayerTwo.hashCode());
  }

  @Test
  public void testEqualsAndHashCode_computerPlayerAndHumanPlayer() {
    assertNotEquals(computerPlayer, humanPlayer);
    assertNotEquals(computerPlayer.hashCode(), humanPlayer.hashCode());
  }
}
