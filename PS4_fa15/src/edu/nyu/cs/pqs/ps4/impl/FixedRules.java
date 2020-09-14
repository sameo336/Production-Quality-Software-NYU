package edu.nyu.cs.pqs.ps4.impl;

/**
 * defines the fixed rules of the game. These can be changed here to modify the
 * game as a whole.
 * 
 * @author Sanchit K
 *
 */
public final class FixedRules {

  private FixedRules() {

  }

  public static enum player {
    PLAYER_1, PLAYER_2, NA
  }

  public static final int MAX_PLAYERS = 2;
  public static final int ROWS = 6;
  public static final int COLUMNS = 7;
  public static final int MIN_WIN_LENGTH = 4;
}
