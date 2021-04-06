package com.bjordan.sudoku;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Sudoku {
  public static final int SIZE = 9;
  private final int[][] board = new int[SIZE][SIZE];

  public Sudoku(String pathToCsv) throws IOException {
    ingestCsv(pathToCsv);
  }

  /**
   * Run each validation method
   *
   * @return true of it is a completely correct Sudoku.
   * @throws IllegalArgumentException
   */
  public boolean isValidBoard() throws IllegalArgumentException {
    return validateRows()
        && validateCols()
        && validateBoxes();
  }

  /**
   * Check that each row is valid itself.
   * If any row is invalid print the row and break.
   *
   * @return true if all rows are valid
   */
  public boolean validateRows() {
    for (int x = 0; x < SIZE; x++) {
      if (!validMatrix(board[x])) {
        System.out.println("Invalid row: " + Arrays.toString(board[x]));
        return false;
      }
    }
    return true;
  }

  /**
   * Check each column is valid.
   * If any column is invalid then print the column and break.
   *
   * @return true if all columns are valid.
   */
  public boolean validateCols() {
    int colValues[] = new int[SIZE];
    for (int y = 0; y < SIZE; y++) {
      for (int x = 0; x < SIZE; x++) {
        colValues[x] = board[x][y];
      }

      if (!validMatrix(colValues)) {
        System.out.println("Invalid column: " + Arrays.toString(colValues));
        return false;
      }
    }
    return true;
  }

  /**
   * Sudoku has nine boxes which all must be valid.
   * Check each box. If any is invalid then print the box and break.
   *
   * @return true if all boxes are valid.
   */
  public boolean validateBoxes() {
    return
        sumBox(0, 0)
            && sumBox(0, 3)
            && sumBox(0, 6)

            && sumBox(3, 0)
            && sumBox(3, 3)
            && sumBox(3, 6)

            && sumBox(6, 0)
            && sumBox(6, 3)
            && sumBox(6, 6);
  }

  private boolean sumBox(int xPos, int yPos) {
    int[] boxValues = new int[SIZE];
    int i = 0;
    for (int x = xPos; x < 3 + xPos; x++) {
      for (int y = yPos; y < 3 + yPos; y++) {
        boxValues[i++] = board[x][y];
      }
    }

    if (!validMatrix(boxValues)) {
      System.out.println("Invalid box: " + Arrays.toString(boxValues));
      return false;
    }
    return true;
  }

  /**
   * Read in a CSV with nine rows and nine columns of numbers 1-9 in any order.
   *
   * @param pathToCsv the data file.
   * @throws IOException if values are outside of [1-9] or the file does not exist.
   */
  private void ingestCsv(String pathToCsv) throws IOException {
    BufferedReader csvReader = new BufferedReader(new FileReader(pathToCsv));

    int x = 0;
    String row;
    while ((row = csvReader.readLine()) != null) {
      String[] rowValues = row.split(",");
      int y = 0;
      for (String value : rowValues) {
        int intVal = Integer.parseInt(value);
        if (intVal < 1 || intVal > 9) {
          throw new IllegalArgumentException("Sudoku values must be between [1-9]. Found: " + intVal);
        }
        board[x][y] = intVal;
        y++;
      }
      x++;
    }
    csvReader.close();
  }

  /**
   * Validate an individual row, column or box.
   * @param sudokuMatrix row/column/box
   *
   * @return true if it adds up to 45
   */
  public static boolean validMatrix(int[] sudokuMatrix) {
    return Arrays.stream(sudokuMatrix).sum() == 45;
  }

  private static void printBoard(int[][] board) {
    for (int i = 0; i < SIZE; i++) {
      System.out.println(Arrays.toString(board[i]));
    }
  }

  /**
   * MAIN
   * Take in the path to the csv file, parse it into a board and validate the board.
   *
   * @param args path to the Sudoku csv file.
   * @throws IOException if the file does not exist.
   */
  public static void main(String[] args) throws IOException {
    String csvPath = args[0];

    Sudoku game = new Sudoku(csvPath);
    if (game.isValidBoard()) {
      System.out.println("Congratulations, you win!");
    } else {
      System.out.println("You lost :(");
    }
    printBoard(game.board);
  }
}
