/* *****************************************************************************
 *  @author Andy Luu
 *  @description Models a percolation system
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int gridSize;
    private final boolean[][] grid;
    private final int virtualTop;
    private final int virtualBot;
    private final WeightedQuickUnionUF wqu;
    private int numOpenSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {

        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        gridSize = n;
        grid = new boolean[gridSize][gridSize];
        wqu = new WeightedQuickUnionUF(gridSize * gridSize + 3);
        virtualTop = gridSize * gridSize + 1;
        virtualBot = gridSize * gridSize + 2;
        numOpenSites = 0;

    }

    private int getPos(int row, int col) {
        return (row - 1) * gridSize + (col - 1);
    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        if (row > gridSize || col > gridSize || row < 1 || col < 1) {
            throw new IllegalArgumentException();
        }

        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = true;

            // connect site to any open sites
            int sitePos = getPos(row, col);

            // if site is on top row connect to virtual top
            if (row == 1) {
                wqu.union(virtualTop, sitePos);
            }

            // if site is on bottom row connect to virtual bot
            if (row == gridSize) {
                wqu.union(virtualBot, sitePos);
            }

            // check left side
            if (col != 1 && isOpen(row, col - 1)) {
                wqu.union(sitePos, getPos(row, col - 1));
            }
            // check right side
            if (col != gridSize && isOpen(row, col + 1)) {
                wqu.union(sitePos, getPos(row, col + 1));
            }
            // check top side
            if (row != gridSize && isOpen(row + 1, col)) {
                wqu.union(sitePos, getPos(row + 1, col));
            }
            // check bottom side
            if (row != 1 && isOpen(row - 1, col)) {
                wqu.union(sitePos, getPos(row - 1, col));
            }
            numOpenSites++;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {

        if (row > gridSize || col > gridSize || row < 1 || col < 1) {
            throw new IllegalArgumentException();
        }

        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {

        if (row > gridSize || col > gridSize || row < 1 || col < 1) {
            throw new IllegalArgumentException();
        }

        return wqu.connected(virtualTop, getPos(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {

        return numOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {

        return wqu.connected(virtualTop, virtualBot);

    }

    // test client (optional)
    public static void main(String[] args) {
        // test client (optional)
    }
}

