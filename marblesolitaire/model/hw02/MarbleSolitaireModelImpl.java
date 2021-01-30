package cs3500.marblesolitaire.model.hw02;

/**
 * Represents an implementation of MarbleSolitaireModel.
 * It's used to construct the board Model with four different constructors.
 * <p>
 * move, isGameOver, getGameState, getScore are four methods can be triggered
 * by calling this model.
 * </p>
 */
public class MarbleSolitaireModelImpl implements MarbleSolitaireModel {

  private int armThickness; // armThickness must be an odd number
  private Cell[][] matrix;
  private int sRow;
  private int sCol;

  // constructor 1

  /**
   * Constructs a MarbleSolitaireModel, with default configuration.
   * Where armThickness = 3, and the empty cell is on the center of the board.
   */
  public MarbleSolitaireModelImpl() {
    this.armThickness = 3;
    this.sRow = 3;
    this.sCol = 3;
    this.newGame();
  }

  // constructor 2

  /**
   * Constructs a MarbleSolitaireModel, witch customized empty cell and armThickness = 3.
   *
   * @param sRow is the x position of the empty cell.
   * @param sCol is the y position of the empty cell.
   * @throws IllegalArgumentException if the empty cell position is invalid
   */
  public MarbleSolitaireModelImpl(int sRow, int sCol) {
    if ((sRow >= 0 && sRow < 2 && sCol >= 0 && sCol < 2)
            || (sRow >= 0 && sRow < 2 && sCol > 4 && sCol < 7)
            || (sRow > 4 && sRow < 7 && sCol >= 0 && sCol < 2)
            || (sRow > 4 && sRow < 7 && sCol > 4 && sCol < 7)) {
      throw new IllegalArgumentException(String.format("Invalid empty cell position (%d,%d)",
              sRow, sCol));
    } else {
      this.armThickness = 3;
      this.sRow = sRow;
      this.sCol = sCol;
      this.newGame();
    }
  }

  // constructor 3

  /**
   * Constructs a MarbleSolitaireModel.
   *
   * @param armThickness is the number of marbles in the top row.
   * @throws IllegalArgumentException if the arm thickness is not a positive odd number
   */
  public MarbleSolitaireModelImpl(int armThickness) {
    if (armThickness == 1
            || armThickness % 2 == 0
            || armThickness <= 0) {
      throw new IllegalArgumentException("the arm thickness is not a positive odd number!!!!");
    } else {
      this.armThickness = armThickness;
      this.sRow = ((armThickness * 3 - 2) - 1) / 2;
      this.sCol = this.sRow;
      this.newGame();
    }
  }


  // constructor 4

  /**
   * Constructs a MarbleSolitaireModel.
   *
   * @param armThickness is the number of marbles in the top row.
   * @param sRow         is the x position of the empty cell.
   * @param sCol         is the y position of the empty cell.
   * @throws IllegalArgumentException if the arm thickness is not a positive odd number, or the
   *                                  empty cell position is invalid.
   */
  public MarbleSolitaireModelImpl(int armThickness, int sRow, int sCol) {
    if (armThickness == 1
            || (sRow < armThickness - 1 && sRow >= 0 && sCol >= 0
            && sCol < armThickness - 1)
            || (sRow < armThickness - 1 && sRow >= 0 && sCol >= 0
            && sCol > (armThickness * 3 - 2) - armThickness)
            || (sRow > (armThickness * 3 - 2) - armThickness && sRow < armThickness * 3 - 2
            && sCol >= 0 && sCol < armThickness - 1)
            || (sRow > (armThickness * 3 - 2) - armThickness
            && sRow < armThickness * 3 - 2
            && sCol > (armThickness * 3 - 2) - armThickness
            && sCol < armThickness * 3 - 2)
            || armThickness % 2 == 0
            || armThickness <= 0) {
      throw new IllegalArgumentException("Invalid initialization!!!!!");
    } else {
      this.armThickness = armThickness;
      this.sRow = sRow;
      this.sCol = sCol;
      this.newGame();
    }
  }

  // Initialize game
  protected void newGame() {
    int size = this.armThickness * 3 - 2;
    this.matrix = new Cell[size][size];

    // fill up all cells with marbles
    for (int row = 0; row < this.matrix.length; row++) {
      for (int col = 0; col < this.matrix.length; col++) {
        this.matrix[row][col] = new Cell(1);
      }
    }

    // replace four corner squares with empty string
    // top-left invalid square
    for (int row = 0; row < armThickness - 1; row++) {
      for (int col = 0; col < armThickness - 1; col++) {
        this.matrix[row][col].changeStatus(-1);
      }
    }

    // top-right invalid square
    for (int row = 0; row < armThickness - 1; row++) {
      for (int col = (armThickness * 3 - 2) - armThickness + 1;
           col < size; col++) {
        this.matrix[row][col].changeStatus(-1);
      }
    }

    // bottom-left invalid square
    for (int row = (armThickness * 3 - 2) - armThickness + 1; row < size; row++) {
      for (int col = 0; col < armThickness - 1; col++) {
        this.matrix[row][col].changeStatus(-1);
      }
    }

    // bottom-right invalid square
    for (int row = (armThickness * 3 - 2) - armThickness + 1; row < size; row++) {
      for (int col = (armThickness * 3 - 2)
              - armThickness + 1; col < size; col++) {
        this.matrix[row][col].changeStatus(-1);
      }
    }

    // update the center cell to empty status
    matrix[sRow][sCol].changeStatus(0);
  }

  // check if the given position is on the matrix
  private boolean isvalid(int x, int y) {
    return ((x > 0) || (y > 0) || (x < this.matrix.length) || (y < this.matrix.length));
  }

  // check if the given move is valid:
  // 1) within the matrix &
  // 2) the “to” and “from” positions are exactly two positions away (horizontally or vertically)
  private boolean validMove(int fromRow, int fromCol, int toRow, int toCol, int x, int y) {
    return !(Math.sqrt(
            Math.pow(Math.abs(toRow - fromRow), 2)
                    + Math.pow(Math.abs(toCol - fromCol), 2)) != 2
            || (fromRow < 0)
            || (fromCol < 0)
            || (toRow < 0)
            || (toCol < 0)
            || (fromRow >= this.matrix.length)
            || (fromCol >= this.matrix.length)
            || (toRow >= this.matrix.length)
            || (toCol >= this.matrix.length));
  }

  // get the position of the marble between the given "from" and "to" cells
  // and put x and y into a String, separating two int by ","
  private String getcMarble(int fromRow, int fromCol, int toRow, int toCol) {
    if (fromRow == toRow) {
      return Integer.toString(fromRow) + "," + Integer.toString(fromCol + (toCol - fromCol) / 2);
    } else {
      return Integer.toString(fromRow + (toRow - fromRow) / 2) + "," + Integer.toString(fromCol);
    }
  }

  // move the marble from the given position to the destination cell
  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol) {
    // get the x and y coordination of the marble between "from" and "to" cells
    int c_x = Integer.valueOf(this.getcMarble(fromRow, fromCol, toRow, toCol).split(",")[0]);
    int c_y = Integer.valueOf(this.getcMarble(fromRow, fromCol, toRow, toCol).split(",")[1]);

    // check if the given move is valid
    if (this.isvalid(fromRow, fromCol)
            && this.isvalid(toRow, toCol)
            && this.validMove(fromRow, fromCol, toRow, toCol, c_x, c_y)
            && (this.matrix[fromRow][fromCol].getStatus() == 1)
            && (this.matrix[c_x][c_y].getStatus() == 1)
            && (this.matrix[toRow][toCol].getStatus() == 0)) {
      this.matrix[fromRow][fromCol].changeStatus(0);
      this.matrix[c_x][c_y].changeStatus(0);
      this.matrix[toRow][toCol].changeStatus(1);
    } else {
      throw new IllegalArgumentException("Invalid move!!!");
    }
  }

  // check if game is over
  @Override
  public boolean isGameOver() {
    int size = (this.armThickness * 3) - 2;

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (    // if there are still valid movements on the board
                (i - 2 >= 0
                        && (this.matrix[i][j].getStatus() == 1)
                        && (this.matrix[i - 2][j].getStatus() == 0)
                        && (this.matrix[i - 1][j].getStatus() == 1)
                        && this.validMove(i, j, i - 2, j, i - 1, j))
                        || (j + 2 < size
                        && (this.matrix[i][j].getStatus() == 1)
                        && (this.matrix[i][j + 2].getStatus() == 0)
                        && (this.matrix[i][j + 1].getStatus() == 1)
                        && this.validMove(i, j, i, j + 2, i, j + 1))
                        || (i + 2 < size
                        && (this.matrix[i][j].getStatus() == 1)
                        && (this.matrix[i + 2][j].getStatus() == 0)
                        && (this.matrix[i + 1][j].getStatus() == 1)
                        && this.validMove(i, j, i + 2, j, i + 1, j))
                        || (j - 2 >= 0
                        && (this.matrix[i][j].getStatus() == 1)
                        && (this.matrix[i][j - 2].getStatus() == 0)
                        && (this.matrix[i][j - 1].getStatus() == 1)
                        && this.validMove(i, j, i, j - 2, i, j - 1))) {
          return false;
        }
      }
    }
    return true;
  }




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


}
