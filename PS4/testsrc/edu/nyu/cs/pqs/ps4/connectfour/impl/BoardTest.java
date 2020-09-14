package edu.nyu.cs.pqs.ps4.connectfour.impl;

import edu.nyu.cs.pqs.ps4.connectfour.api.Player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.Before;
import org.junit.Rule;

public class BoardTest {
  private Board board;
  private Player playerOne;
  private Player playerTwo;
  private int rows;
  private int cols;

  @Before
  public void setUp() {
    board = Board.getInstance();
    playerOne = Model.getInstance()
        .setPlayerOne_asHuman1ForTest(new HumanPlayer.Builder(PlayerType.HUMAN1).build());
    playerTwo = Model.getInstance()
        .setPlayerTwo_asHuman2ForTest(new HumanPlayer.Builder(PlayerType.HUMAN2).build());
    rows = BoardDimensions.ROWS;
    cols = BoardDimensions.COLUMNS;
  }

  @After
  public void tearDown() {
    board.resetBoard();
    board = null;
    playerOne = null;
    playerTwo = null;
  }

  @Rule
  public ExpectedException exceptionThrown = ExpectedException.none();

  @Test
  public void testGetInstance() {
    Board boardInstance = Board.getInstance();
    Board boardInstanceTwo = Board.getInstance();
    assertNotNull(boardInstance);
    assertNotNull(boardInstanceTwo);
    assertEquals(boardInstance, boardInstanceTwo);
  }

  @Test
  public void testIsValidColumn_negativeColumnNumber() {
    assertFalse(board.isValidColumn(-1));
  }

  @Test
  public void testIsValidColumn_columnNumberEqualToTotalNumberOfColumnsOnBoard() {
    assertFalse(board.isValidColumn(cols));
  }

  @Test
  public void testIsValidColumn_chipsAddedToFullColumn() {
    board.setChipsDroppedInColumn_forTest(0, rows, playerOne);
    assertFalse(board.isValidColumn(0));
  }

  @Test
  public void testIsValidColumn_validColumnNumber() {
    assertTrue(board.isValidColumn(0));
  }

  @Test
  public void testAddChip_validColumnNumber() {
    int[][] testState = board.getBoardState();
    assertEquals(0, testState[0][0]);
    assertEquals(0, board.getNumberOfChipsInColumn(0));
    assertTrue(board.addChip(0, playerOne));
    testState = board.getBoardState();
    assertEquals(1, testState[0][0]);
    assertEquals(1, board.getNumberOfChipsInColumn(0));
  }

  @Test
  public void testAddChip_addToFullBoard() {
    for (int i = 0; i < cols; i++) {
      board.setChipsDroppedInColumn_forTest(i, rows, playerOne);
    }
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("Invalid Column Number");
    board.addChip(0, playerOne);
  }

  @Test
  public void testAddChip_negativeColumnNumber() {
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("Invalid Column Number");
    board.addChip(-1, playerOne);
  }

  @Test
  public void testAddChip_columnNumberGreaterThanTotalNumberOfColumnsOn() {
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("Invalid Column Number");
    board.addChip(cols + 1, playerOne);
  }

  @Test
  public void testAddChip_nullPlayer() {
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("Player cannot be null");
    board.addChip(0, null);
  }

  @Test
  public void testRemoveChip_validColumn() {
    board.setChipsDroppedInColumn_forTest(0, 1, playerOne);
    int[][] testState = board.getBoardState();
    assertEquals(1, testState[0][0]);
    assertEquals(1, board.getNumberOfChipsInColumn(0));
    assertTrue(board.removeChip(0));
    testState = board.getBoardState();
    assertEquals(0, testState[0][0]);
    assertEquals(0, board.getNumberOfChipsInColumn(0));
  }

  @Test
  public void testRemoveChip_negativeColumnNumber() {
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("Invalid Column Number");
    board.removeChip(-1);
  }

  @Test
  public void testRemoveChip_columnNumberEqualToNumberOfTotalNumberOfColumnsOnBoard() {
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("Invalid Column Number");
    board.removeChip(cols);
  }

  @Test
  public void testRemoveChip_zeroChipsPresentInColumn() {
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("This column has no chip");
    board.removeChip(1);
  }

  @Test
  public void testCheckWin_noWinner() {
    assertFalse(board.checkWin(playerOne));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCheckWin_nullPlayer() {
    assertFalse(board.checkWin(null));
  }

  @Test
  public void testCheckWin_consecutiveRowWin() {
    board.setChipsDroppedInColumn_forTest(0, 1, playerOne);
    board.setChipsDroppedInColumn_forTest(1, 1, playerOne);
    board.setChipsDroppedInColumn_forTest(2, 1, playerOne);
    board.setChipsDroppedInColumn_forTest(3, 1, playerOne);
    assertTrue(board.checkWin(playerOne));
  }

  @Test
  public void testCheckWin_consecutiveColumnWin() {
    board.setChipsDroppedInColumn_forTest(0, 4, playerTwo);
    assertTrue(board.checkWin(playerTwo));
  }

  @Test
  public void testCheckWin_consecutiveDiagonalUpwardWin() {
    board.setChipsDroppedInColumn_forTest(0, 1, playerOne);
    board.setChipsDroppedInColumn_forTest(1, 1, playerTwo);
    board.setChipsDroppedInColumn_forTest(1, 1, playerOne);
    board.setChipsDroppedInColumn_forTest(2, 2, playerTwo);
    board.setChipsDroppedInColumn_forTest(2, 1, playerOne);
    board.setChipsDroppedInColumn_forTest(3, 3, playerTwo);
    board.setChipsDroppedInColumn_forTest(3, 1, playerOne);
    assertTrue(board.checkWin(playerOne));
  }

  @Test
  public void testCheckWin_conecutiveDiagonalDownwardWin() {
    board.setChipsDroppedInColumn_forTest(3, 1, playerOne);
    board.setChipsDroppedInColumn_forTest(2, 1, playerTwo);
    board.setChipsDroppedInColumn_forTest(2, 1, playerOne);
    board.setChipsDroppedInColumn_forTest(1, 2, playerTwo);
    board.setChipsDroppedInColumn_forTest(1, 1, playerOne);
    board.setChipsDroppedInColumn_forTest(0, 3, playerTwo);
    board.setChipsDroppedInColumn_forTest(0, 1, playerOne);
    assertTrue(board.checkWin(playerOne));
  }

  @Test
  public void testResetBoard() {
    board.setChipsDroppedInColumn_forTest(0, 1, playerOne);
    int[][] testState = board.getBoardState();
    assertEquals(1, testState[0][0]);
    assertEquals(1, board.getNumberOfChipsInColumn(0));
    assertTrue(board.resetBoard());
    testState = board.getBoardState();
    assertEquals(0, testState[0][0]);
    assertEquals(0, board.getNumberOfChipsInColumn(0));
  }

  @Test
  public void testIsBoardFull_boardNotFull() {
    assertFalse(board.isBoardFull());
  }

  @Test
  public void testIsBoardFull_boardFull() {
    for (int i = 0; i < cols; i++) {
      board.setChipsDroppedInColumn_forTest(i, rows, playerOne);
    }
    assertTrue(board.isBoardFull());
  }

  @Test
  public void testGetNumberOfChipsInColumn() {
    assertEquals(0, board.getNumberOfChipsInColumn(cols - 1));
    board.setChipsDroppedInColumn_forTest(cols - 1, 1, playerOne);
    assertEquals(1, board.getNumberOfChipsInColumn(cols - 1));
  }

  @Test
  public void testGetNumberOfChipsInColumn_negativeColumnNumber() {
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("Invalid Column Number");
    board.getNumberOfChipsInColumn(-1);
  }

  @Test
  public void testGetNumberOfChipsInColumn_columnNumerEqualToTotalNumberOfColumnsOnBoard() {
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("Invalid Column Number");
    board.getNumberOfChipsInColumn(cols);
  }

  @Test
  public void testGetRowCount() {
    assertEquals(rows, board.getRowCount());
  }

  @Test
  public void testGetColumnCount() {
    assertEquals(cols, board.getColumnCount());
  }
}