package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MatrixIt implements Iterator<Integer> {
    private final int[][] data;
    private int row = 0;
    private int column = 0;

    public MatrixIt(int[][] data) {
        this.data = data;
    }

    private void nextCell() {
        if ((data[row].length == 0 || column == data[row].length - 1)) {
            row++;
            column = 0;
        } else {
            column++;
        }
    }

    @Override
    public boolean hasNext() {
        while (row < data.length
                && (data[row].length == 0 || data[row][column] == 0)) {
            nextCell();
        }
        return row < data.length
                && data[row].length >= 0
                && column <= data[row].length;
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        int rsl = data[row][column];
        nextCell();
        return rsl;
    }
}