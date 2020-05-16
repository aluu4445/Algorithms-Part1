/* *****************************************************************************
 *  @author Andy Luu
 *  @description Models an n-by-n board with sliding tiles
 **************************************************************************** */

import edu.princeton.cs.algs4.Stack;

import java.util.Arrays;

public class Board {

    private final int n;
    private int[][] tiles;
    private int zeroRow;
    private int zeroCol;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {

        this.n = tiles.length;
        this.tiles = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // perform a deep copy of tiles into this.tiles
                this.tiles[i][j] = tiles[i][j];

                // save the location of the blank tile into zeroRow and zeroCol
                if (this.tiles[i][j] == 0) {
                    this.zeroRow = i;
                    this.zeroCol = j;
                }
            }
        }
    }

    private int[][] goalBoard() {
        int[][] goal = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // generate the goal array
                if (i == n - 1 && j == n - 1) {
                    goal[i][j] = 0;
                }
                else {
                    goal[i][j] = i * n + j + 1;
                }

            }
        }
        return goal;
    }

    // string representation of this board
    public String toString() {

        StringBuilder output = new StringBuilder();

        for (int[] row : this.tiles) {
            for (int i = 0; i < row.length; i++) {
                output.append(row[i]);
                output.append(" ");
                if (i == row.length - 1) {
                    output.append("\n");
                }
            }
        }
        return n + "\n" + output.toString();
    }

    // board dimension n
    public int dimension() {
        return this.n;
    }


    // number of tiles out of place
    public int hamming() {

        int counter = 0;

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (this.tiles[r][c] != 0 && this.tiles[r][c] != this.goalBoard()[r][c]) {
                    counter++;
                }
            }
        }
        return counter;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {

        int counter = 0;

        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                int boardTile = this.tiles[r][c];
                if (boardTile != 0 && boardTile != this.goalBoard()[r][c]) {
                    int vertDiff = Math.abs(r - Math.floorDiv((boardTile - 1), n));
                    int horDiff = Math.abs(c - (boardTile - 1) % n);
                    counter += vertDiff + horDiff;
                }
            }
        }
        return counter;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return Arrays.deepEquals(this.tiles, this.goalBoard());

    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board yBoard = (Board) y;
        return this.n == yBoard.n && Arrays.deepEquals(this.tiles, yBoard.tiles);

    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> boards = new Stack<>();
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (zeroRow > 0 && r == zeroRow - 1 && c == zeroCol) {
                    boards.push(this.swapTile(r, c, zeroRow, zeroCol));
                }
                else if (zeroRow < n - 1 && r == zeroRow + 1 && c == zeroCol) {
                    boards.push(this.swapTile(r, c, zeroRow, zeroCol));
                }
                else if (zeroCol > 0 && r == zeroRow && c == zeroCol - 1) {
                    boards.push(this.swapTile(r, c, zeroRow, zeroCol));
                }
                else if (zeroCol < n - 1 && r == zeroRow && c == zeroCol + 1) {
                    boards.push(this.swapTile(r, c, zeroRow, zeroCol));
                }
            }
        }
        return boards;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (this.tiles[i][j] != 0 && this.tiles[i + 1][j] != 0) {
                    return this.swapTile(i, j, i + 1, j + 1);
                }
            }
        }
        return this;
    }

    private Board swapTile(int row1, int col1, int row2, int col2) {


        int[][] tilesClone = new int[n][n];

        // deep copy tiles into tilesClone
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tilesClone[i][j] = this.tiles[i][j];
            }
        }
        tilesClone[row1][col1] = this.tiles[row2][col2];
        tilesClone[row2][col2] = this.tiles[row1][col1];
        return new Board(tilesClone);
    }

    // unit testing (not graded)
    public static void main(String[] args) {

        int[][] b1 = { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } };
        int[][] b2 = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
        int[][] b3 = { { 1, 2, 3 }, { 4, 0, 5 }, { 7, 8, 6 } };
        Board tester = new Board(b1);
        Board tester2 = new Board(b2);
        Board tester3 = new Board(b3);
        System.out.println(tester3.manhattan());
        System.out.println(tester.dimension());
        System.out.println(tester.hamming());
        System.out.println(tester.manhattan());
        System.out.println(tester.isGoal());
        System.out.println(tester.equals(tester2));
        System.out.println(tester.twin());


        for (Board board : tester2.neighbors()) {
            System.out.println(board);
        }
        for (Board board : tester.neighbors()) {
            System.out.println(board);
        }

    }

}
