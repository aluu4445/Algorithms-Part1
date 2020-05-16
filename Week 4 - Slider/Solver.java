/* *****************************************************************************
 *  @author Andy Luu
 *  @description Implements A* search
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private Stack<Board> solution = new Stack<>();
    private int solutionMoves = 0;
    private boolean solvable;

    private class SearchNode implements Comparable<SearchNode> {
        Board board;
        int moves;
        SearchNode previous;
        int MP;

        @Override
        public int compareTo(SearchNode that) {
            return Integer.compare(this.MP, that.MP);
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        // create the initial search node
        SearchNode initNode = new SearchNode();
        initNode.board = initial;
        initNode.moves = 0;
        initNode.previous = null;
        initNode.MP = initNode.board.manhattan() + initNode.moves;

        // rate the twin search node
        SearchNode twinNode = new SearchNode();
        twinNode.board = initial.twin();
        twinNode.moves = 0;
        twinNode.previous = null;
        twinNode.MP = twinNode.board.manhattan() + twinNode.moves;

        // create a priority queue and insert the initial search node
        MinPQ<SearchNode> pq = new MinPQ<>();
        pq.insert(initNode);

        // create a second priority queue and insert the twin node
        MinPQ<SearchNode> pqTwin = new MinPQ<>();
        pqTwin.insert(twinNode);

        // initialise the search node that gets dequeued from the pq
        SearchNode min;
        SearchNode minTwin;

        // repeat the following until the board is the goal board
        // remove the minimum search node with the minimum priority and add its neighbors to the priority queue

        while (true) {
            min = pq.delMin();
            minTwin = pqTwin.delMin();
            if (min.board.isGoal()) {
                this.solvable = true;
                break;
            }

            if (minTwin.board.isGoal()) {
                this.solvable = false;
                break;
            }

            for (Board board : min.board.neighbors()) {
                if (min.previous == null || !board.equals(min.previous.board)) {
                    SearchNode newNode = new SearchNode();
                    newNode.board = board;
                    newNode.previous = min;
                    newNode.moves = min.moves + 1;
                    newNode.MP = newNode.board.manhattan() + newNode.moves;
                    pq.insert(newNode);
                }

            }

            for (Board board : minTwin.board.neighbors()) {
                if (minTwin.previous == null || !board.equals(minTwin.previous.board)) {
                    SearchNode newNode = new SearchNode();
                    newNode.board = board;
                    newNode.previous = minTwin;
                    newNode.moves = minTwin.moves + 1;
                    newNode.MP = newNode.board.manhattan() + newNode.moves;
                    pqTwin.insert(newNode);
                }

            }
        }

        SearchNode pointer = min;

        // Set pointer to the initial SearchNode
        while (true) {
            this.solution.push(pointer.board);
            if (pointer.previous == null) break;
            pointer = pointer.previous;
            this.solutionMoves += 1;
        }


    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return this.solvable;
    }

    // min number of moves to solve initial board
    public int moves() {
        return this.solutionMoves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        return this.solution;
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
