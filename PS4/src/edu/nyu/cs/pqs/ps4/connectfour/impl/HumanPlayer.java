package edu.nyu.cs.pqs.ps4.connectfour.impl;

import edu.nyu.cs.pqs.ps4.connectfour.api.Player;

/**
 * The HumanPlayer class is a thread-unsafe representation of a Human Player that plays the Connect
 * Four game. The HumanPlayer has a Player Type of either HUMAN1 or HUMAN2. An instance of
 * HumanPlayer class is constructed utilizing the Builder pattern wherein the Player Type is a
 * mandatory field. Name is an optional field.
 * 
 * @author Anisha Bhatla
 */
class HumanPlayer implements Player {
  private final PlayerType playerType;
  private final String name;

  /**
   * Builder class is used to construct a HumanPlayer instance with Player Type as mandatory field
   * and name as an optional field. The Player Type and name cannot be null. The Player Type has to
   * be either HUMAN1 or HUMAN2.
   */
  static class Builder {
    private final PlayerType playerType;
    private String name = "";

    Builder(PlayerType playerType) {
      if (playerType == null) {
        throw new IllegalArgumentException("Player Type cannot be null");
      }
      if (playerType != PlayerType.HUMAN1 && playerType != PlayerType.HUMAN2) {
        throw new IllegalArgumentException("Invalid Player Type");
      }
      this.playerType = playerType;
    }

    /**
     * Sets name field of the Builder instance
     * 
     * @param name name of the Human Player
     * @return Builder builder instance with the updated name value
     */
    Builder name(String name) {
      if (name == null) {
        throw new IllegalArgumentException("Player Name cannot be null");
      }
      if (!name.trim().isEmpty()) {
        this.name = name.trim();
      }
      return this;
    }

    /**
     * Returns a HumanPlayer instance
     * 
     * @return HumanPlayer instance
     */
    HumanPlayer build() {
      return new HumanPlayer(this);
    }
  }

  /**
   * Private constructor to prevent instantiation from outside the class
   * 
   * @param builder the Builder instance used to create the HumanPlayer instance
   */
  private HumanPlayer(Builder builder) {
    playerType = builder.playerType;
    name = builder.name;
  }

  @Override
  public PlayerType getPlayerType() {
    return playerType;
  }

  @Override
  public int getPlayerID() {
    return playerType.getID();
  }

  @Override
  public String getPlayerName() {
    return name;
  }

  /**
   * {@inheritDoc} Two instances of HumanPlayers are considered equal if they have the same Player
   * Type and a case-sensitive match of the name string.
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof HumanPlayer)) {
      return false;
    }
    HumanPlayer other = (HumanPlayer) obj;
    if ((other.playerType != playerType) || (!other.name.equals(name))) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 17;
    result = prime * result + playerType.getID();
    result = prime * result + playerType.hashCode();
    result = prime * result + name.hashCode();
    return result;
  }

  @Override
  public String toString() {
    StringBuilder humanPlayerString = new StringBuilder("Connect Four Human Player [Player Type = "
        + playerType + ", Player ID = " + playerType.getID());
    if (!name.isEmpty()) {
      humanPlayerString.append(", Name = " + name);
    }
    humanPlayerString.append("]");
    return humanPlayerString.toString();
  }
}