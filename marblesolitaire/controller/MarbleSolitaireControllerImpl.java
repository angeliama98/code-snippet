package cs3500.marblesolitaire.controller;

import java.io.IOException;
import java.util.Scanner;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

/**
 * Represents an implementation of MarbleSolitaireController.
 * It has two parameters, Readable in and Appendable out.
 * <p>
 * The constructor inside the class:
 * Accepts and stores these objects for doing input and output.
 * Any input coming from the user will be received via the Readable object,
 * and any output sent to the user should be written to the Appendable object.
 * </p>
 * <p>
 * The method inside: void PlayGame()
 * It allows user to play a game.
 * It should “run” the game with specified cases and fixed sequences until the game is over.
 * </p>
 */
public class MarbleSolitaireControllerImpl implements MarbleSolitaireController {
  private Readable in;
  private Appendable out;


  /**
   * Constructs a MarbleSolitaireController.
   * Accepts and stores these objects for doing input and output.
   * Any input coming from the user will be received via the Readable object,
   * and any output sent to the user should be written to the Appendable object.
   *
   * @param rd represents the Readable input.
   * @param ap represents the Appendable output.
   * @throws IllegalArgumentException if and only either of its arguments are null.
   */
  public MarbleSolitaireControllerImpl(Readable rd, Appendable ap) {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("Input or Output cannot be null!!!!");
    }
    this.in = rd;
    this.out = ap;
  }


  /**
   * It allows user to play a game.
   * It should “run” the game in the following sequence until the game is over.
   * It works with the following sequences until the game is over.
   * <p>
   * 1) Transmit game state to the Appendable object exactly as the model provides it.
   * 2) Transmit "Score: N", replacing N with the actual score.
   * 3) If the game is ongoing, obtain the next user input from the  Readable object.
   * 4) If the game is over, the method should transmit each of the
   * following in order: the message "Game over!", the final game state, and the message "Score: N"
   * with N replaced by the final score. The method should then end.
   * </p>
   * @param model the model of MarbleSolitaire that we create to play the game
   */
  public void playGame(MarbleSolitaireModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null!!!" + "\n");
    }


    int fromRow = 0;
    int toRow = 0;
    int fromCol = 0;
    int toCol = 0;


    Scanner scan = new Scanner(this.in);
    if (!scan.hasNext()) {
      throw new IllegalStateException("Run out of Inputs!!!");
    }

    while (scan.hasNext()) {
      String next = scan.next();

      try {
        if (Integer.parseInt(next) > 0) {
          if (fromRow <= 0 && fromCol <= 0 && toRow <= 0 && toCol <= 0) {
            fromRow = Integer.parseInt(next);
          } else if (fromCol <= 0) {
            fromCol = Integer.parseInt(next);
          } else if (toRow <= 0) {
            toRow = Integer.parseInt(next);
          } else if (toCol <= 0) {
            toCol = Integer.parseInt(next);
          }
        }
      } catch (NumberFormatException n) {
        if (next.equals("q") || next.equals("Q")) {
          this.exceptionDetect("Game quit!" + "\n"
                  + "State of game when quit:" + "\n"
                  + model.getGameState() + "\n"
                  + "Score: "
                  + model.getScore());
          break;
        } else {
          this.exceptionDetect("Invalid move. Play again. " + n + " is invalid!!!" + "\n");
        }
      }

      if (fromRow > 0 && fromCol > 0 && toRow > 0 && toCol > 0) {
        try {
          model.move(fromRow - 1, fromCol - 1, toRow - 1, toCol - 1);
        } catch (IllegalArgumentException e) {
          this.exceptionDetect("Invalid move. Play again. " + e + " is invalid!!!" + "\n");
        }
        fromRow = 0;
        fromCol = 0;
        toRow = 0;
        toCol = 0;
      }

      if (model.isGameOver()) {
        this.exceptionDetect("Game over!" + "\n"
                + model.getGameState() + "\n"
                + "Score: " + model.getScore());
        break;
      }

      if (!scan.hasNext()) {
        throw new IllegalStateException("Readable Error!!!");
      }
    }


  }


  // a private method to try-catch IOException
  // in order to reduce redundant code
  private void exceptionDetect(String s) {
    try {
      this.out.append(s);
    } catch (IOException e) {
      throw new IllegalStateException("Appendable Error!!!");
    }
  }


}

