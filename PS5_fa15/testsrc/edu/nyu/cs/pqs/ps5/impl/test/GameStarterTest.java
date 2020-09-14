package edu.nyu.cs.pqs.ps5.impl.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.nyu.cs.pqs.ps5.impl.GameStarter;

public class GameStarterTest {
  /**
   * This tests the gameStarter main method. To get full test coverage for
   * GameStarter, please put null,10 and empty values for the number of windows
   * prompt.
   */
  @Test
  public void testMainFunction() {
    GameStarter.main(null);
    assertTrue(GameStarter.getNumberOfWindows() == Integer.parseInt(GameStarter
        .getValue()));
  }
}
