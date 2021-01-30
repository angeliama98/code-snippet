package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.Cell;

/**
 * Represents the TriangleModel which extends AbstractModelImp. This class used to initialize
 * "triangular" model and keep track of different game functionality and status. It's used to
 * construct the board Model with four different constructors.
 * <p>
 * move, isGameOver, getGameState, getScore are four methods can be triggered by calling this
 * model.
 * </p>
 */
public class TriangleSolitaireModelImpl extends AbstractModelImpl {

  // constructor 1

  /**
   * Constructs a TriangleSolitaireModel, with default configuration. Where armThickness = 5, and
   * the empty cell is on the center of the board.
   */
  public TriangleSolitaireModelImpl() {
    super.armThickness = 5;
    super.sRow = 0;
    super.sCol = 0;
    this.newGame();
  }

  // constructor 2

  /**
   * Constructs a TriangleSolitaireModel, with customised armThickness.
   *
   * @param armThickness is the number of marbles in the top row.
   * @throws IllegalArgumentException if the arm thickness is not negative number.
   */
  public TriangleSolitaireModelImpl(int armThickness) {
    if (armThickness > 0) {
      super.armThickness = armThickness;
      super.sRow = 0;
      super.sCol = 0;
      this.newGame();
    } else {
      throw new IllegalArgumentException("Arm thickness cannot be negative!!!");
    }
  }


  // constructor 3

  /**
   * Constructs a TriangleSolitaireModel, witch customized empty cell and armThickness = 5.
   *
   * @param sRow is the x position of the empty cell.
   * @param sCol is the y position of the empty cell.
   * @throws IllegalArgumentException if the empty cell position is invalid
   */
  public TriangleSolitaireModelImpl(int sRow, int sCol) {
    if (sCol <= sRow) {
      super.sRow = sRow;
      super.sCol = sCol;
      super.armThickness = 5;
      this.newGame();
    } else {
      throw new IllegalArgumentException("Invalid empty position!!!");
    }
  }


  // constructor 4

  /**
   * Constructs a MarbleSolitaireModel, with the customised armThickness and empty cell.
   *
   * @param armThickness is the number of marbles in the top row.
   * @param sRow         is the x position of the empty cell.
   * @param sCol         is the y position of the empty cell.
   * @throws IllegalArgumentException if the arm thickness is not a positive odd number, or the
   *                                  empty cell position is invalid.
   */
  public TriangleSolitaireModelImpl(int armThickness, int sRow, int sCol) {
    if (armThickness > 0) {
      super.armThickness = armThickness;
    } else {
      throw new IllegalArgumentException("Arm thickness cannot be negative!!!");
    }

    if (sCol <= sRow) {
      super.sRow = sRow;
      super.sCol = sCol;
    } else {
      throw new IllegalArgumentException("Invalid empty position!!!");
    }
    this.newGame();
  }


  // check if there is no more valid moves can perform
  private boolean cannotMove() {
    for (int row = 0; row < super.matrix.length; row++) {
      for (int col = 0; col < super.matrix.length; col++) {
        // which means there is no two adjacent marbles with an empty reachable cell
        if ((col - 2 >= 0 && this.validMove(row, col, row, col - 2, row, col - 1))
                || (row + 2 < super.matrix.length
                && this.validMove(row, col, row + 2, col, row + 1, col))
                || (row + 2 < super.matrix.length && col + 2 < super.matrix.length
                && this.validMove(row, col, row + 2, col + 2, row + 1, col + 1))
                || (row - 2 >= 0
                && this.validMove(row, col, row - 2, col, row - 1, col))
                || (row - 2 >= 0 && col - 2 >= 0
                && this.validMove(row, col, row - 2, col - 2, row - 1, col - 1))
                || (col + 2 <= row && this.validMove(row, col, row, col + 2,
                row, col + 1))) {
          return false;
        }
      }
    }
    return true;
  }


  // check if the given move is valid
  @Override
  protected boolean validMove(int fromRow, int fromCol, int toRow, int toCol, int x, int y) {
    return !(
            !(Math.sqrt(
                    Math.pow(Math.abs(toRow - fromRow), 2)
                            + Math.pow(Math.abs(toCol - fromCol), 2)) != 2
                    || Math.pow(Math.abs(toRow - fromRow), 2)
                    + Math.pow(Math.abs(toCol - fromCol), 2) != 8)
                    || (fromRow < 0
                    || fromRow >= this.matrix.length
                    || fromCol < 0
                    || fromCol >= this.matrix.length)
                    || (toRow < 0
                    || toCol < 0
                    || toRow >= this.matrix.length
                    || toCol >= this.matrix.length)
                    || (fromRow == toRow && Math.abs(fromCol - toCol) != 2)
                    || (fromCol == toCol && Math.abs(fromRow - toRow) != 2)
                    || Math.pow(Math.abs(toRow - fromRow), 2)
                    + Math.pow(Math.abs(toCol - fromCol), 2) == 5
                    || Math.pow(Math.abs(toRow - fromRow), 2)
                    + Math.pow(Math.abs(toCol - fromCol), 2) > 8
                    || this.matrix[fromRow][fromCol] == null
                    || this.matrix[fromRow][fromCol].getStatus() != 1
                    || this.matrix[toRow][toCol] == null
                    || this.matrix[x][y] == null
                    || this.matrix[x][y].getStatus() != 1
                    || this.matrix[toRow][toCol].getStatus() != 0);
  }

  // get the current game state, print friendly
  @Override
  public String getGameState() {
    // Initialize a result holder, which is a StringBuilder
    StringBuilder result = new StringBuilder();
    for (int row = 0; row < super.armThickness; row++) {
      for (int col = 0; col < super.armThickness; col++) {
        if (col == 0) {
          // triangle pattern invert call
          for (int count = 1; count < super.armThickness - row; count++) {
            result.append(" ");
          }
        }
        // update cell's status to corresponding String symbol
        if (super.matrix[row][col].getStatus() == 0) {
          result.append("_");
        } else if (super.matrix[row][col].getStatus() == 1) {
          result.append("O");
        }
        if (col < row) {
          result.append(" ");
        }
      }
      // when we reach the end of line, print a newline (exclude last line)
      if (row + 1 < super.armThickness) {
        result.append("\n");
      }
    }
    return result.toString();
  }

  // Initialize game
  @Override
  protected void newGame() {
    // super the matrix field from MarbleSolitaireModel
    super.matrix = new Cell[super.armThickness][super.armThickness];

    // generate a triangle pattern
    for (int row = 0; row < super.armThickness; row++) {
      for (int col = 0; col < super.armThickness; col++) {
        if (col <= row) {
          super.matrix[row][col] = new Cell(1);
          if (super.sRow == row && super.sCol == col) {
            super.matrix[row][col] = new Cell(0);
          }
        } else {
          super.matrix[row][col] = new Cell(-1);
        }
      }
    }
  }

  // move the marble from the given position to the destination cell
  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol) {
    // get the x and y coordination of the marble between "from" and "to" cells
    int c_x = 0;
    int c_y = 0;
    c_x = Integer.valueOf(this.getcMarble(fromRow, fromCol, toRow, toCol).split(",")[0]);
    c_y = Integer.valueOf(this.getcMarble(fromRow, fromCol, toRow, toCol).split(",")[1]);

    // check if the given move is valid
    if (this.isvalid(fromRow, fromCol)
            && this.isvalid(toRow, toCol)
            && this.isvalid(c_x, c_y)
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


  // calculate the score (detects how many marbles are on the board)
  @Override
  public int getScore() {
    int count = 0;
    // if detects a marble, adds 1 to count
    for (int i = 0; i < super.armThickness; i++) {
      for (int j = 0; j < super.armThickness; j++) {
        if (this.matrix[i][j].getStatus() == 1) {
          count++;
        }
      }
    }
    return count;
  }

  // get the cell status, used for test
  public int getStatus(int row, int col) {
    return super.matrix[row][col].getStatus();
  }

  // if we have no move valid move or we only have one marble on the board(which means the player
  // wins), the game terminates
  @Override
  public boolean isGameOver() {
    return this.cannotMove() || this.getScore() <= 1;
  }
}
