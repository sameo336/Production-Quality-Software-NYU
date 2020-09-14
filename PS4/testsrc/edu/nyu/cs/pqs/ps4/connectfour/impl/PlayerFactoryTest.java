package edu.nyu.cs.pqs.ps4.connectfour.impl;

import edu.nyu.cs.pqs.ps4.connectfour.api.Player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PlayerFactoryTest {
  private Player player;
  private HumanPlayer humanPlayerOne;
  private HumanPlayer humanPlayerTwo;
  private ComputerPlayer computerPlayer;

  @Before
  public void setUp() {
    player = Model.getInstance()
        .setPlayerOne_asHuman1ForTest(new HumanPlayer.Builder(PlayerType.HUMAN1).build());
    humanPlayerOne = (HumanPlayer) Model.getInstance()
        .setPlayerOne_asHuman1ForTest(new HumanPlayer.Builder(PlayerType.HUMAN1).build());
    humanPlayerTwo = (HumanPlayer) Model.getInstance()
        .setPlayerTwo_asHuman2ForTest(new HumanPlayer.Builder(PlayerType.HUMAN2).build());
    computerPlayer =
        (ComputerPlayer) Model.getInstance().setPlayerTwo_asComputerForTest(new ComputerPlayer());
  }

  @After
  public void tearDown() {
    player = null;
    humanPlayerOne = null;
    humanPlayerTwo = null;
    computerPlayer = null;
  }

  @Rule
  public ExpectedException exceptionThrown = ExpectedException.none();

  @Test
  public void testGetPlayer_nullPlayerType() {
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("Player Type cannot be null");
    player = PlayerFactory.getPlayer(null, "");
  }

  @Test
  public void testGetPlayer_nullName() {
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("Player Name cannot be null");
    player = PlayerFactory.getPlayer(PlayerType.HUMAN1, null);
  }

  @Test
  public void testGetPlayer_instanceOfHumanPlayer() {
    player = PlayerFactory.getPlayer(PlayerType.HUMAN2, "");
    assertTrue(player instanceof HumanPlayer);
    assertEquals(PlayerType.HUMAN2, player.getPlayerType());
    assertEquals("", player.getPlayerName());
    assertEquals(PlayerType.HUMAN2.getID(), player.getPlayerID());
  }

  @Test
  public void testGetPlayer_humanPlayerTypeWithEmptyName() {
    player = PlayerFactory.getPlayer(PlayerType.HUMAN1, "");
    assertEquals(PlayerType.HUMAN1, player.getPlayerType());
    assertEquals("", player.getPlayerName());
    assertEquals(PlayerType.HUMAN1.getID(), player.getPlayerID());
    assertTrue(humanPlayerOne.equals(player));
  }

  @Test
  public void testGetPlayer_humanPlayerTypeWithMultipleSpacesName() {
    player = PlayerFactory.getPlayer(PlayerType.HUMAN2, "   ");
    assertEquals(PlayerType.HUMAN2, player.getPlayerType());
    assertTrue(player.getPlayerName().isEmpty());
    assertEquals(PlayerType.HUMAN2.getID(), player.getPlayerID());
    assertEquals(humanPlayerTwo.getPlayerID(), player.getPlayerID());
  }

  @Test
  public void testGetPlayer_humanPlayerTypeWithValidName() {
    player = PlayerFactory.getPlayer(PlayerType.HUMAN1, "Test Name");
    assertEquals(PlayerType.HUMAN1, player.getPlayerType());
    assertEquals("Test Name", player.getPlayerName());
    assertEquals(PlayerType.HUMAN1.getID(), player.getPlayerID());
    assertTrue(humanPlayerOne.getPlayerType().equals(player.getPlayerType()));
  }

  @Test
  public void testGetPlayer_instanceOfComputerPlayerWithEmptyName() {
    player = PlayerFactory.getPlayer(PlayerType.COMPUTER, "");
    assertTrue(player instanceof ComputerPlayer);
    assertEquals(PlayerType.COMPUTER, player.getPlayerType());
    assertEquals("", player.getPlayerName());
    assertEquals(PlayerType.COMPUTER.getID(), player.getPlayerID());
  }

  @Test
  public void testGetPlayer_computerPlayerTypeWithOnlyMultipleSpacesName() {
    player = PlayerFactory.getPlayer(PlayerType.COMPUTER, " ");
    assertEquals(PlayerType.COMPUTER, player.getPlayerType());
    assertEquals(PlayerType.COMPUTER.getID(), player.getPlayerID());
    assertTrue(player.getPlayerName().isEmpty());
    assertEquals(computerPlayer, player);
  }

  @Test
  public void testGetPlayer_computerPlayerTypeWithNonEmptyName() {
    player = PlayerFactory.getPlayer(PlayerType.COMPUTER, "Computer Name");
    assertEquals(PlayerType.COMPUTER, player.getPlayerType());
    assertEquals(PlayerType.COMPUTER.getID(), player.getPlayerID());
    assertTrue(player.getPlayerName().isEmpty());
    assertEquals(computerPlayer, player);
  }
}