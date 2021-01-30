package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.Cell;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

/**
 * Represents an abstract model which implements MarbleSolitaireModel. It allows "english" and
 * "triangle" model to access. It's used to construct the board Model with four different
 * constructors.
 * <p>
 * move, isGameOver, getGameState, getScore are four methods can be triggered by calling this
 * model.
 * </p>
 */
public abstract class AbstractModelImpl implements MarbleSolitaireModel {

  protected Cell[][] matrix;
  protected int armThickness;
  protected int sRow;
  protected int sCol;


  // get the position of the marble between the given "from" and "to" cells
  // and put x and y into a String, separating two int by ","
  protected String getcMarble(int fromRow, int fromCol, int toRow, int toCol) {
    if (fromRow == toRow) {
      return Integer.toString(fromRow) + "," + Integer.toString(fromCol + (toCol - fromCol) / 2);
    }
    if (fromCol == toCol) {
      return Integer.toString(fromRow + (toRow - fromRow) / 2) + "," + Integer.toString(fromCol);
    }
    if (fromRow < toRow && fromCol < toCol) {
      return Integer.toString(fromRow + 1) + ","
              + Integer.toString(fromCol + 1);
    }
    if (fromRow > toRow && fromCol < toCol) {
      return Integer.toString(fromRow - 1) + ","
              + Integer.toString(fromCol + 1);
    }
    if (fromRow > toRow && fromCol > toCol) {
      return Integer.toString(fromRow - 1) + ","
              + Integer.toString(fromCol - 1);
    } else {
      return Integer.toString(fromRow + 1) + ","
              + Integer.toString(fromCol - 1);
    }
  }

  // move the marble from the given position to the destination cell
  @Override
  public abstract void move(int fromRow, int fromCol, int toRow, int toCol);

  // check if the game should terminate, different qualifications for two models
  @Override
  public abstract boolean isGameOver();

  // check if the given position is on the matrix
  protected boolean isvalid(int x, int y) {
    return ((x > 0) || (y > 0) || (x < this.matrix.length - 1) || (y < this.matrix.length - 1));
  }

  // check if the given move is valid: (different requirements for two models)
  // 1) within the matrix &
  // 2) the “to” and “from” positions are exactly two positions away (horizontally or vertically)
  protected abstract boolean validMove(int fromRow, int fromCol, int toRow, int toCol,
                                       int x, int y);


  // check game state and outputs it as a String
  @Override
  public String getGameState() {
    String result = "";
    int size = (this.armThickness * 3) - 2;

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        int status = this.matrix[i][j].getStatus();
        // update invalid cell to space
        if (status == -1 && j < (this.armThickness * 2) - 2) {
          result = result + " ";
        }

        // update empty cell to underline
        if (status == 0) {
          result = result + "_";
        }

        // update marble to "O"
        if (status == 1) {
          result = result + "O";
        }

        // add a space between each cell
        if ((j < size - 1 && this.matrix[i][j + 1].getStatus() != -1)
                || (j < (this.armThickness * 2) - 2 && this.matrix[i][j + 1].getStatus() == -1)) {
          result += " ";
        }

      }

      // add newline at each end of the line, except for the last line
      if (i + 1 < size) {
        result += "\n";
      }
    }
    return result;
  }

  // calculate the score
  @Override
  public int getScore() {
    int count = 0;
    int size = armThickness * 3 - 2;

    // if detects a marble, adds 1 to count
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (this.matrix[i][j].getStatus() == 1) {
          count++;
        }
      }
    }
    return count;
  }

  // generate a new game, dispatch to different models according on the user's request
  protected abstract void newGame();

}

