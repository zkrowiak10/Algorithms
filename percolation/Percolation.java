/* *****************************************************************************
 *  Name:Percolation
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF grid;
    private boolean[] opened;
    private int openSites = 0;
    private final int rowLength;
    private final int sites;
    private final int top;
    private final int bottom;

    public Percolation(int n) {                // create n-by-n grid, with all sites blocked
        if (n < 0) {
            throw new java.lang.IllegalArgumentException("n must be greater than 0");
        }
        rowLength = n;
        n = n * n;
        sites = n;
        opened = new boolean[n];
        int length = n + 2;
        grid = new WeightedQuickUnionUF(length); // adding two virtual points as top and bottom;
        top = length - 1;
        bottom = length - 2;

    }


    private int getSite(int row, int col) { // returns index of site
        row -= 1;
        col -= 1;
        if (row < 0 || col < 0) {
            throw new java.lang.IllegalArgumentException(
                    "Outside prescribed range");
        }
        row *= rowLength;
        row += col;
        if (row >= sites) {
            throw new IllegalArgumentException(
                    "Outside prescribed range");
        }
        return row;
    }


    public void open(int row, int col) {  // open site (row, col) if it is not open already
        if (!isOpen(row, col)) {
            int index = getSite(row, col);
            openSites++;
            opened[index] = true;
            int[][] neighbors = {
                    { row + 1, col }, { row - 1, col }, { row, col + 1 }, { row, col - 1 }
            };
            if (row == 1) {
                grid.union(index, top);
            }
            if (row == rowLength) {
                grid.union(index, bottom);
            }

            for (int[] neighbor : neighbors) {
                try {
                    int x = neighbor[0];
                    int y = neighbor[1];
                    int place = getSite(x, y);
                    int theRow = place / rowLength;
                    if (this.isOpen(x, y)) {
                        if (col == y) {
                            grid.union(index, place);
                        }
                        else {
                            if (theRow == (row - 1)) {
                                grid.union(index, place);
                            }
                        }
                    }
                }
                catch (java.lang.IllegalArgumentException badInput) {
                    continue;
                }
            }

        }
    }


    public boolean isOpen(int row, int col) {  // is site (row, col) open?
        int index = getSite(row, col);
        return opened[index];

    }

    public boolean isFull(int row, int col) {  // is site (row, col) full?
        int index = getSite(row, col);
        return grid.connected(index, top);
    }

    public int numberOfOpenSites() {       // number of open sites
        return openSites;
    }

    public boolean percolates() {              // does the system percolate?
        return grid.connected(top, bottom);
    }

}


