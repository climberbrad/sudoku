package com.bjordan.sudoku;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.io.IOException;

public class SudokuTest {
  @Test
  public void validSudoku() throws IOException {
    Sudoku game = new Sudoku("data/valid.csv");
    assertTrue(game.validateRows());
    assertTrue(game.validateCols());
  }

  @Test
  public void invalidRowReturnsFalse() throws IOException {
    Sudoku game = new Sudoku("data/invalid-row-and-col.csv");
    assertFalse(game.validateRows());
  }

  @Test
  public void invalidColumn() throws IOException {
    Sudoku game = new Sudoku("data/invalid-row-and-col.csv");
    assertFalse(game.validateCols());
  }

  @Test
  public void validBox() throws IOException {
    Sudoku game = new Sudoku("data/valid.csv");
    assertTrue(game.validateBoxes());
  }

  @Test
  public void inValidBox() throws IOException {
    Sudoku game = new Sudoku("data/invalid-box.csv");
    assertFalse(game.validateBoxes());
  }

  @Test
  public void testValidateMatrix() {
    int[] validMatrix = new int[] {1,2,3,4,5,6,7,8,9};
    assertTrue(Sudoku.validMatrix(validMatrix));
  }

  @Test
  public void testInValidateMatrix() {
    int[] validMatrix = new int[] {2,2,3,4,5,6,7,8,9};
    assertFalse(Sudoku.validMatrix(validMatrix));
  }
}
