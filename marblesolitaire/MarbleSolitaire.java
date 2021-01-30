package cs3500.marblesolitaire;

import cs3500.marblesolitaire.controller.MarbleSolitaireControllerImpl;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelImpl;
import cs3500.marblesolitaire.model.hw04.EuropeanSolitaireModelImpl;
import cs3500.marblesolitaire.model.hw04.TriangleSolitaireModelImpl;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

import java.io.InputStreamReader;

/**
 * A class represents MarbleSolitaire game. It is used to run the main() method,
 * in order to allow users choose their game mode.
 * - "English"
 * - "European"
 * - "Triangular"
 */
public final class MarbleSolitaire {

  // helper method to render change model
  private static MarbleSolitaireModel switchModel(boolean size, boolean hole, int side,
                                                  int sRow, int sCol, String in)
          throws IllegalArgumentException {
    // enter three parameters mode
    // Note: 0-index, we should start the position method by subtracting the given value by 1
    if (size && hole) {
      switch (in) {
        case "european":
          return new EuropeanSolitaireModelImpl(side, sRow - 1, sCol - 1);
        case "triangular":
          return new TriangleSolitaireModelImpl(side, sRow - 1, sCol - 1);
        case "english":
          return new MarbleSolitaireModelImpl(side, sRow - 1, sCol - 1);
        default:
          break;
      }
    }
    // enter customised cell mode
    else if (hole) {
      switch (in) {
        case "triangular":
          return new TriangleSolitaireModelImpl(sRow - 1, sCol - 1);
        case "european":
          return new EuropeanSolitaireModelImpl(sRow - 1, sCol - 1);
        case "english":
          return new MarbleSolitaireModelImpl(sRow - 1, sCol - 1);
        default:
          break;
      }
    }
    // enter size parameter mode
    else if (size) {
      switch (in) {
        case "triangular":
          return new TriangleSolitaireModelImpl(side);
        case "english":
          return new MarbleSolitaireModelImpl(side);
        case "european":
          return new EuropeanSolitaireModelImpl(side);
        default:
          break;
      }
    } else {
      // enter default mode
      switch (in) {
        case "european":
          return new EuropeanSolitaireModelImpl();
        case "english":
          return new MarbleSolitaireModelImpl();
        case "triangular":
          return new TriangleSolitaireModelImpl();
        default:
          break;
      }
    }
    throw new IllegalArgumentException("Invalid Inputs!!!!");
  }


  /**
   * A main() method for MarbleSolitaire.
   * This main() method will be the entry point for your program.
   * The program needs to take inputs as command-line arguments.
   * @param args takes in args as command to initialize the game
   */
  public static void main(String[] args) {
    // FILL IN HERE
    String in = ""; // Initialize variable to avoid NullPointerException
    // default setting
    int side = 3;
    int sRow = 3;
    int sCol = 3;
    // check the command type
    boolean size = false;
    boolean hole = false;

    // create a controller
    MarbleSolitaireControllerImpl c =
            new MarbleSolitaireControllerImpl(new InputStreamReader(System.in), System.out);
    for (int i = 0; i < args.length; i++) {
      try {
        // get the type command from args[i]
        switch (args[i]) {
          case "english":
          case "european":
          case "triangular":
            // pass the command to the controller we created
            in = args[i];
            break;
            // if the command = "-size", change the size flag boolean to true
          // and check the next value
          case "-size":
            side = Integer.parseInt(args[i + 1]);
            i += 1;
            size = true;
            break;
          // if the command = "-hole", change the size flag boolean to true
          // and check the next two values to render methods based on the position input
          case "-hole":
            sRow = Integer.parseInt(args[i + 1]);
            sCol = Integer.parseInt(args[i + 2]);
            i += 2;
            hole = true;
            break;
          default:
            break;
        }
      } catch (IndexOutOfBoundsException o) {
        System.out.println("Invalid Input!!!:" + o);
      }
    }
    try {
      // change to another model
      c.playGame(switchModel(size, hole, side, sRow, sCol, in));
    } catch (IllegalArgumentException e) {
      System.out.println("Invalid Argument:" + e);
    }
  }
}

