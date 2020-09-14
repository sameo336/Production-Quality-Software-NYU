package edu.nyu.cs.pqs.ps4.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.nyu.cs.pqs.ps4.api.Connect4Model;
import edu.nyu.cs.pqs.ps4.api.Connect4Player;

/**
 * {@inheritDoc Connect4Player}
 * 
 * @author Sanchit K
 *
 */
final class ComputerPlayer implements Connect4Player {

  private final Connect4Model model;
  private FixedRules.player playerId;

  ComputerPlayer(Connect4Model model) {
    this.model = model;
    this.playerId = FixedRules.player.PLAYER_2;
  }

  @Override
  public final void notifyMovePlayed(BasicMove basicMove) {
    CurrentBoardStatus board = model.getCurrGameStatus();
    if (model.whosNext() == this.playerId) {
      if (!board.checkWinningMove(basicMove)) {
        FixedRules.player[][] currBoardState = board.currBoardState();
        List<BasicMove> moves = new ArrayList<BasicMove>();
        for (int i = 0; i < FixedRules.COLUMNS; i++) {
          for (int j = FixedRules.ROWS - 1; j >= 0; j--) {
            if (currBoardState[j][i] == FixedRules.player.NA) {
              moves.add(new BasicMove(currBoardState[j][i], j, i));
              break;
            }
          }
        }
        BasicMove random = moves.get(new Random().nextInt(moves.size()));
        BasicMove comMove =
            new BasicMove(this.playerId, random.getRow(), random.getColumn());

        for (BasicMove move : moves) {
          boolean check =
              board.checkWinningMove(new BasicMove(this.playerId,
                  move.getRow(), move.getColumn()));
          if (check) {
            comMove =
                new BasicMove(this.playerId, move.getRow(), move.getColumn());
            break;
          }

          FixedRules.player second;
          if (this.playerId == FixedRules.player.PLAYER_1) {
            second = FixedRules.player.PLAYER_2;
          } else {
            second = FixedRules.player.PLAYER_1;
          }
          boolean check2 =
              board.checkWinningMove(new BasicMove(second, move.getRow(), move
                  .getColumn()));
          if (check2) {
            comMove =
                new BasicMove(this.playerId, move.getRow(), move.getColumn());
            break;
          }
        }
        model.notifyMove(comMove);
      }
    }

  }

  @Override
  public final void notifyGameStarted(FixedRules.player playerId) {

    this.playerId = playerId;
    if (model.whosNext() == this.playerId) {
      CurrentBoardStatus board = model.getCurrGameStatus();
      FixedRules.player[][] currBoardState = board.currBoardState();
      List<BasicMove> moves = new ArrayList<BasicMove>();
      for (int i = 0; i < FixedRules.COLUMNS; i++) {
        for (int j = FixedRules.ROWS - 1; j >= 0; j--) {
          if (currBoardState[j][i] == FixedRules.player.NA) {
            moves.add(new BasicMove(currBoardState[j][i], j, i));
            break;
          }
        }
      }
      BasicMove random = moves.get(new Random().nextInt(moves.size()));
      BasicMove comMove =
          new BasicMove(this.playerId, random.getRow(), random.getColumn());

      for (BasicMove move : moves) {
        boolean check =
            board.checkWinningMove(new BasicMove(this.playerId, move.getRow(),
                move.getColumn()));
        if (check) {
          comMove =
              new BasicMove(this.playerId, move.getRow(), move.getColumn());
          break;
        }

        FixedRules.player second;
        if (this.playerId == FixedRules.player.PLAYER_1) {
          second = FixedRules.player.PLAYER_2;
        } else {
          second = FixedRules.player.PLAYER_1;
        }
        this.playerId = second;
        boolean check2 =
            board.checkWinningMove(new BasicMove(second, move.getRow(), move
                .getColumn()));
        if (check2) {
          comMove = new BasicMove(second, move.getRow(), move.getColumn());
          break;
        }
      }
      model.notifyMove(comMove);
    }
  }

  @Override
  public final void notifyGameFinished(FixedRules.player playerId) {
  }

  @Override
  public final FixedRules.player getPlayerId() {
    return playerId;
  }

}
