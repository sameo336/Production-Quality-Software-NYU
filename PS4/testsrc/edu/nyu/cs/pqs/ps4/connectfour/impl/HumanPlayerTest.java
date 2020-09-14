package edu.nyu.cs.pqs.ps4.connectfour.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class HumanPlayerTest {

  private HumanPlayer humanPlayer;

  @After
  public void tearDown() {
    humanPlayer = null;
  }

  @Rule
  public ExpectedException exceptionThrown = ExpectedException.none();

  @Test
  public void testBuilder_nullPlayerType() {
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("Player Type cannot be null");
    humanPlayer = new HumanPlayer.Builder(null).build();
  }

  @Test
  public void testBuilder_invalidPlayerType() {
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("Invalid Player Type");
    humanPlayer = new HumanPlayer.Builder(PlayerType.COMPUTER).build();
  }

  @Test
  public void testBuilder_validPlayerTypeWithoutName() {
    humanPlayer = new HumanPlayer.Builder(PlayerType.HUMAN1).build();
    assertNotNull(humanPlayer);
    assertEquals(PlayerType.HUMAN1, humanPlayer.getPlayerType());
    assertEquals("", humanPlayer.getPlayerName());
    assertEquals(PlayerType.HUMAN1.getID(), humanPlayer.getPlayerID());
  }

  @Test
  public void testBuilder_nullName() {
    exceptionThrown.expect(IllegalArgumentException.class);
    exceptionThrown.expectMessage("Player Name cannot be null");
    humanPlayer = new HumanPlayer.Builder(PlayerType.HUMAN2).name(null).build();
  }

  @Test
  public void testBuilder_validPlayerTypeWithName() {
    humanPlayer = new HumanPlayer.Builder(PlayerType.HUMAN2).name("Valid Test Name").build();
    assertNotNull(humanPlayer);
    assertEquals(PlayerType.HUMAN2, humanPlayer.getPlayerType());
    assertEquals("Valid Test Name", humanPlayer.getPlayerName());
    assertEquals(PlayerType.HUMAN2.getID(), humanPlayer.getPlayerID());
  }

  @Test
  public void testBuilder_nameWithEmptySpaces() {
    humanPlayer = new HumanPlayer.Builder(PlayerType.HUMAN2).name("   ").build();
    assertTrue(humanPlayer.getPlayerName().isEmpty());
    assertEquals(PlayerType.HUMAN2, humanPlayer.getPlayerType());
    assertEquals(PlayerType.HUMAN2.getID(), humanPlayer.getPlayerID());
  }

  @Test
  public void testEquals_twoHumanPlayersWithDifferentPlayerTypes() {
    humanPlayer = new HumanPlayer.Builder(PlayerType.HUMAN1).build();
    HumanPlayer humanPlayerTwo = new HumanPlayer.Builder(PlayerType.HUMAN2).build();
    assertNotEquals(humanPlayerTwo, humanPlayer);
  }

  @Test
  public void testEquals_humanPlayerAndComputerPlayer() {
    ComputerPlayer computerPlayer =
        (ComputerPlayer) PlayerFactory.getPlayer(PlayerType.COMPUTER, "");
    humanPlayer = new HumanPlayer.Builder(PlayerType.HUMAN1).build();
    assertNotEquals(computerPlayer, humanPlayer);
  }

  @Test
  public void testEquals_twoHumanPlayersWithSamePlayerTypesAndDifferentNames() {
    humanPlayer = new HumanPlayer.Builder(PlayerType.HUMAN1).name("FirstName").build();
    HumanPlayer humanPlayerTwo =
        new HumanPlayer.Builder(PlayerType.HUMAN1).name("SecondName").build();
    assertNotEquals(humanPlayerTwo, humanPlayer);
  }

  @Test
  public void testEquals_twoHumanPlayersWithSamePlayerTypesAndSameNames() {
    humanPlayer = new HumanPlayer.Builder(PlayerType.HUMAN1).name("FirstName").build();
    HumanPlayer humanPlayerTwo =
        new HumanPlayer.Builder(PlayerType.HUMAN1).name("FirstName").build();
    assertEquals(humanPlayerTwo, humanPlayer);
  }

  @Test
  public void testEquals_twoHumanPlayersWithSamePlayerTypesAndSameNamesWithExtraSpaces() {
    humanPlayer = new HumanPlayer.Builder(PlayerType.HUMAN1).name("  FirstName").build();
    HumanPlayer humanPlayerTwo =
        new HumanPlayer.Builder(PlayerType.HUMAN1).name("FirstName").build();
    assertEquals(humanPlayerTwo, humanPlayer);
  }

  @Test
  public void testEquals_twoHumanPlayersWithSamePlayerTypesAndSameNamesWithDifferentCases() {
    humanPlayer = new HumanPlayer.Builder(PlayerType.HUMAN1).name("FirstName").build();
    HumanPlayer humanPlayerTwo =
        new HumanPlayer.Builder(PlayerType.HUMAN1).name("FirSTName").build();
    assertNotEquals(humanPlayerTwo, humanPlayer);
  }

  @Test
  public void testEquals_sameHumanPlayerInstance() {
    humanPlayer = new HumanPlayer.Builder(PlayerType.HUMAN1).build();
    assertTrue(humanPlayer.equals(humanPlayer));
  }

  @Test
  public void testEquals_humanPlayerAndNull() {
    humanPlayer = new HumanPlayer.Builder(PlayerType.HUMAN1).build();
    assertFalse(humanPlayer.equals(null));
  }

  @Test
  public void testHashCode_twoHumanPlayersWithDifferentPlayerTypes() {
    humanPlayer = new HumanPlayer.Builder(PlayerType.HUMAN1).build();
    HumanPlayer humanPlayerTwo = new HumanPlayer.Builder(PlayerType.HUMAN2).build();
    assertNotEquals(humanPlayer.hashCode(), humanPlayerTwo.hashCode());
  }

  @Test
  public void testHashCode_humanPlayerAndComputerPlayer() {
    ComputerPlayer computerPlayer =
        (ComputerPlayer) PlayerFactory.getPlayer(PlayerType.COMPUTER, "");
    humanPlayer = new HumanPlayer.Builder(PlayerType.HUMAN1).build();
    assertNotEquals(computerPlayer.hashCode(), humanPlayer.hashCode());
  }

  @Test
  public void testHashCode_twoHumanPlayersWithSamePlayerTypesAndDifferentNames() {
    humanPlayer = new HumanPlayer.Builder(PlayerType.HUMAN1).name("FirstName").build();
    HumanPlayer humanPlayerTwo =
        new HumanPlayer.Builder(PlayerType.HUMAN1).name("SecondName").build();
    assertNotEquals(humanPlayer.hashCode(), humanPlayerTwo.hashCode());
  }

  @Test
  public void testHashCode_twoHumanPlayersWithSamePlayerTypesAndSameNames() {
    humanPlayer = new HumanPlayer.Builder(PlayerType.HUMAN1).name("FirstName").build();
    HumanPlayer humanPlayerTwo =
        new HumanPlayer.Builder(PlayerType.HUMAN1).name("FirstName").build();
    assertEquals(humanPlayer.hashCode(), humanPlayerTwo.hashCode());
  }

  @Test
  public void testHashCode_twoHumanPlayersWithSamePlayerTypesAndSameNamesWithExtraSpaces() {
    humanPlayer = new HumanPlayer.Builder(PlayerType.HUMAN1).name("  FirstName").build();
    HumanPlayer humanPlayerTwo =
        new HumanPlayer.Builder(PlayerType.HUMAN1).name("FirstName").build();
    assertEquals(humanPlayerTwo.hashCode(), humanPlayer.hashCode());
  }

  @Test
  public void testHashCode_twoHumanPlayersWithSamePlayerTypesAndSameNamesWithDifferentCases() {
    humanPlayer = new HumanPlayer.Builder(PlayerType.HUMAN1).name("FirstName").build();
    HumanPlayer humanPlayerTwo =
        new HumanPlayer.Builder(PlayerType.HUMAN1).name("FirSTName").build();
    assertNotEquals(humanPlayerTwo.hashCode(), humanPlayer.hashCode());
  }

  @Test
  public void testEqualsAndHashCode_twoHumanPlayersWithDifferentPlayerTypes() {
    humanPlayer = new HumanPlayer.Builder(PlayerType.HUMAN1).build();
    HumanPlayer humanPlayerTwo = new HumanPlayer.Builder(PlayerType.HUMAN2).build();
    assertFalse(humanPlayerTwo.equals(humanPlayer));
    assertNotEquals(humanPlayer.hashCode(), humanPlayerTwo.hashCode());
  }

  @Test
  public void testEqualsAndHashCode_humanPlayerAndComputerPlayer() {
    ComputerPlayer computerPlayer =
        (ComputerPlayer) PlayerFactory.getPlayer(PlayerType.COMPUTER, "");
    humanPlayer = new HumanPlayer.Builder(PlayerType.HUMAN1).build();
    assertNotEquals(computerPlayer, humanPlayer);
    assertNotEquals(computerPlayer.hashCode(), humanPlayer.hashCode());
  }

  @Test
  public void testEqualsAndHashCode_twoHumanPlayersWithSamePlayerTypesAndDifferentNames() {
    humanPlayer = new HumanPlayer.Builder(PlayerType.HUMAN1).name("FirstName").build();
    HumanPlayer humanPlayerTwo =
        new HumanPlayer.Builder(PlayerType.HUMAN1).name("SecondName").build();
    assertNotEquals(humanPlayer, humanPlayerTwo);
    assertNotEquals(humanPlayer.hashCode(), humanPlayerTwo.hashCode());
  }

  @Test
  public void testEqualsAndHashCode_twoHumanPlayersWithSamePlayerTypesAndSameNames() {
    humanPlayer = new HumanPlayer.Builder(PlayerType.HUMAN1).name("FirstName").build();
    HumanPlayer humanPlayerTwo =
        new HumanPlayer.Builder(PlayerType.HUMAN1).name("FirstName").build();
    assertEquals(humanPlayer, humanPlayerTwo);
    assertEquals(humanPlayer.hashCode(), humanPlayerTwo.hashCode());
  }

  @Test
  public void
      testEqualsAndHashCode_twoHumanPlayersWithSamePlayerTypesAndSameNamesWithExtraSpaces() {
    humanPlayer = new HumanPlayer.Builder(PlayerType.HUMAN1).name("  FirstName").build();
    HumanPlayer humanPlayerTwo =
        new HumanPlayer.Builder(PlayerType.HUMAN1).name("FirstName").build();
    assertEquals(humanPlayerTwo, humanPlayer);
    assertEquals(humanPlayerTwo.hashCode(), humanPlayer.hashCode());
  }

  @Test
  public void
      testEqualsAndHashCode_twoHumanPlayersWithSamePlayerTypesAndSameNamesWithDifferentCases() {
    humanPlayer = new HumanPlayer.Builder(PlayerType.HUMAN1).name("FirstName").build();
    HumanPlayer humanPlayerTwo =
        new HumanPlayer.Builder(PlayerType.HUMAN1).name("FirSTName").build();
    assertNotEquals(humanPlayerTwo, humanPlayer);
    assertNotEquals(humanPlayerTwo.hashCode(), humanPlayer.hashCode());
  }
}
