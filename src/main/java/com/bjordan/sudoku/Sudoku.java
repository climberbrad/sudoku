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

  public boolean isValidBoard() throws IllegalArgumentException {
    return validateRows()
        && validateCols()
        && validateBoxes();
  }

  public boolean validateRows() {
    for (int x = 0; x < SIZE; x++) {
      if (!validMatrix(board[x])) {
        System.out.println("Invalid row: " + Arrays.toString(board[x]));
        return false;
      }
    }
    return true;
  }

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

  public static boolean validMatrix(int[] sudokuMatrix) {
    return Arrays.stream(sudokuMatrix).sum() == 45;
  }

  private static void printBoard(int[][] board) {
    for (int i = 0; i < SIZE; i++) {
      System.out.println(Arrays.toString(board[i]));
    }
  }

  public static void main(String[] args) throws IOException {
    // TODO: validate
    String dataPath = args[0];

    Sudoku game = new Sudoku(dataPath);
    if (game.isValidBoard()) {
      System.out.println("Congratulations, you win!");
      printBoard(game.board);
    } else {
      System.out.println("You lost :(");
      printBoard(game.board);
    }
  }
}
