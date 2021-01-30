package cs3500.marblesolitaire.model.hw02;

/**
 * Represents a cell on the plus shape board. It stores x , y coordination and the status of the
 * cell.
 */
public class Cell {
  private int status;

  /**
   * Constructs a cell.
   * @param status is the {@link int} of this cell. {Invalid = -1, Empty = 0, Marble = 1}
   */
  public Cell(int status) {
    this.status = status;
  }


  /**
   * Changes the {@link int} for this Cell.
   * @param i is the new status of this Cell.
   */
  public void changeStatus(int i) {
    this.status = i;
  }

  /**
   * Returns the {@link int} of the state for this cell, which represents if there is a marble at
   * this position.
   */
  public int getStatus() {
    return this.status;
  }
}
