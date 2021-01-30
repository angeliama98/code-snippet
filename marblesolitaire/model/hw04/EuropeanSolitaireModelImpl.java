package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.Cell;

/**
 * Represents the EuropeanSolitaireModel which extends AbstractModelImp. This class used to
 * initialize "european" model and keep track of different game functionality and status. It's used
 * to construct the board Model with four different constructors.
 * <p>
 * move, isGameOver, getGameState, getScore are four methods can be triggered by calling this
 * model.
 * </p>
 */
public class EuropeanSolitaireModelImpl extends AbstractModelImpl {

  // constructor 1

  /**
   * Constructs an EuropeanSolitaireModel, with default configuration. Where armThickness = 3, and
   * the empty cell is on the center of the board.
   */
  public EuropeanSolitaireModelImpl() {
    super.armThickness = 3;
    super.sRow = 3;
    super.sCol = 3;
    this.newGame();
  }

  // constructor 2

  /**
   * Constructs an EuropeanSolitaireModel, with customised armThickness.
   *
   * @param armThickness is the number of marbles in the top row.
   * @throws IllegalArgumentException if the arm thickness is not a positive odd number
   */
  public EuropeanSolitaireModelImpl(int armThickness) {
    if (armThickness == 1
            || armThickness % 2 == 0
            || armThickness <= 0) {
      throw new IllegalArgumentException("the arm thickness is not a positive odd number!!!!");
    } else {
      super.armThickness = armThickness;
      super.sRow = ((armThickness * 3 - 2) - 1) / 2;
      super.sCol = super.sRow;
      this.newGame();
    }
  }


  // constructor 3

  /**
   * Constructs an EuropeanSolitaireModel, witch customized empty cell and armThickness = 3.
   *
   * @param sRow is the x position of the empty cell.
   * @param sCol is the y position of the empty cell.
   * @throws IllegalArgumentException if the empty cell position is invalid
   */
  public EuropeanSolitaireModelImpl(int sRow, int sCol) {
    EuropeanSolitaireModelImpl game = new EuropeanSolitaireModelImpl(3);
    // if the specified empty cell is invalid, throws exception
    if (game.getStatus(sRow, sCol) == -1) {
      throw new IllegalArgumentException(String.format("Invalid empty cell position (%d,%d)",
              sRow, sCol));
    } else {
      super.armThickness = 3;
      super.sRow = sRow;
      super.sCol = sCol;
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
  public EuropeanSolitaireModelImpl(int armThickness, int sRow, int sCol) {
    if (armThickness % 2 == 1 && armThickness > 0) {
      super.armThickness = armThickness;
    } else {
      throw new IllegalArgumentException("Arm thickness cannot be negative or even!!!.");
    }

    // make sure given position is within the valid board
    if ((((sRow > (super.armThickness * 2 - 2) && sCol < (super.armThickness - 1)))
            && sCol >= (sRow - (super.armThickness * 2 - 2))
            || ((sRow < (super.armThickness - 1) && sCol < (super.armThickness - 1))
            && sCol >= (super.armThickness - 1) - sRow)
            || ((sRow < (super.armThickness - 1) && sCol > (super.armThickness * 2 - 2))
            && sCol <= (super.armThickness * 3 - 2) - (super.armThickness - sRow))
            || ((sRow > (super.armThickness * 2 - 2) && sCol > (super.armThickness * 2 - 2))
            && sCol <= (super.armThickness * 3 - 2) - (sRow - (super.armThickness * 2 - 3))))) {
      super.sRow = sRow;
      super.sCol = sCol;
      this.newGame();
    }
    // make sure given position is not in the invalid cells
    else if (!((sRow < (armThickness - 1)
            && sCol < (armThickness - 1))
            || (sRow > (armThickness * 2 - 2)
            && sCol > (armThickness * 2 - 2))
            || (sRow > (armThickness * 2 - 2)
            && sCol < (armThickness - 1))
            || (sRow < (armThickness - 1)
            && sCol > (armThickness * 2 - 2)))) {
      super.sRow = sRow;
      super.sCol = sCol;
      this.newGame();
    } else {
      throw new IllegalArgumentException(String.format("Invalid empty cell position (%d, %d)",
              sRow, sCol));
    }
  }

  // Initialize game
  protected void newGame() {
    int size = super.armThickness * 3 - 2;
    super.matrix = new Cell[size][size];

    for (int row = 0; row < matrix.length; row++) {
      for (int col = 0; col < matrix.length; col++) {
        if (super.sRow == row && super.sCol == col) {
          super.matrix[row][col] = new Cell(0);
        } else if ((((row < (super.armThickness - 1)
                && col < (super.armThickness - 1))
                && col >= (super.armThickness - 1) - row)
                || ((col > (super.armThickness * 2 - 2)
                && row > (super.armThickness * 2 - 2))
                && col <= (super.armThickness * 3 - 2) - (row - (super.armThickness * 2 - 3))
                || (col <= (super.armThickness * 3 - 2) - (super.armThickness - row)
                && (row < (super.armThickness - 1) && col > (super.armThickness * 2 - 2))
                || (((row > (super.armThickness * 2 - 2) && col < (super.armThickness - 1)))
                && col >= (row - (super.armThickness * 2 - 2)))))
                || (!((col > (super.armThickness * 2 - 2) && row > (super.armThickness * 2 - 2))
                || (row < (super.armThickness - 1) && col < (super.armThickness - 1))
                || (row > (super.armThickness * 2 - 2) && col < (super.armThickness - 1))
                || (row < (super.armThickness - 1) && col > (super.armThickness * 2 - 2)))))) {
          super.matrix[row][col] = new Cell(1);
        } else {
          super.matrix[row][col] = new Cell(-1);
        }
      }
    }
  }


  // get the cell status of the given position
  public int getStatus(int row, int col) {
    return super.matrix[row][col].getStatus();
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
    if (!(this.isvalid(fromRow, fromCol)
            && this.isvalid(toRow, toCol)
            && this.isvalid(c_x, c_y))) {
      throw new IllegalArgumentException("Invalid move!!!");
    }
    if (this.validMove(fromRow, fromCol, toRow, toCol, c_x, c_y)
            && (this.matrix[fromRow][fromCol].getStatus() == 1)
            && (this.matrix[c_x][c_y].getStatus() == 1)
            && (this.matrix[toRow][toCol].getStatus() == 0)) {
      this.matrix[fromRow][fromCol].changeStatus(0);
      this.matrix[c_x][c_y].changeStatus(0);
      this.matrix[toRow][toCol].changeStatus(1);
    } else {
      throw new IllegalArgumentException("Invalid cell!!!");
    }
  }


  // make sure the move is valid (within the board, without overlap or no-from-cell or no-mid-cell)
  @Override
  protected boolean validMove(int fromRow, int fromCol, int toRow, int toCol, int x, int y) {
    return !(
            (Math.pow(Math.abs(toRow - fromRow), 2)
                    + Math.pow(Math.abs(toCol - fromCol), 2) != 4)
                    || (fromRow < 0)
                    || (fromCol < 0)
                    || (toRow < 0)
                    || (toCol < 0)
                    || (x < 0)
                    || (y < 0)
                    || (x >= this.matrix.length)
                    || (y >= this.matrix.length)
                    || (fromRow >= this.matrix.length)
                    || (fromCol >= this.matrix.length)
                    || (toRow >= this.matrix.length)
                    || (toCol >= this.matrix.length)
                    || this.matrix[toRow][toCol].getStatus() != 0
                    || this.matrix[fromRow][fromCol].getStatus() != 1
                    || this.matrix[x][y].getStatus() != 1);
  }

  // check if there is no move valid moves, and the game reaches the end
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

}
