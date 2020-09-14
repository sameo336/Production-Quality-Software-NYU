package edu.nyu.cs.pqs.ps4.connectfour.impl;

import edu.nyu.cs.pqs.ps4.connectfour.api.Player;
import edu.nyu.cs.pqs.ps4.connectfour.impl.ComputerPlayer;

/**
 * The PlayerFactory class provides an instance of the required Player type - namely HUMAN1, HUMAN2
 * or COMPUTER. The class follows the Factory Pattern, i.e., a static factory method is used to
 * obtain an instance of the Player Type.
 * 
 * @author Anisha Bhatla
 */
class PlayerFactory {

  /**
   * Private constructor to prevent instantiation from outside the class
   */
  private PlayerFactory() {
  }

  /**
   * Gets an instance of a player of the requested type
   * 
   * @param type type of the player requested
   * @param name name of the player
   * @return player of requested type
   * @throws IllegalArgumentException if the requested player type or name is null
   */
  static Player getPlayer(PlayerType playerType, String name) {
    Player player;
    if (playerType == null) {
      throw new IllegalArgumentException("Player Type cannot be null");
    } else if (name == null) {
      throw new IllegalArgumentException("Player Name cannot be null");
    } else if (playerType == PlayerType.HUMAN1 || playerType == PlayerType.HUMAN2) {
      if (!name.trim().isEmpty()) {
        player = new HumanPlayer.Builder(playerType).name(name.trim()).build();
      } else {
        player = new HumanPlayer.Builder(playerType).build();
      }
    } else {
      player = new ComputerPlayer();
    }
    return player;
  }
}