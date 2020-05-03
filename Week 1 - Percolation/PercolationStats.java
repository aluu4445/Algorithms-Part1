/* *****************************************************************************
 *  @author Andy Luu
 *  @description Models a percolation system
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final double[] results;
    private final int numTrials;


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        Percolation p;
        double pThreshold;
        int gridSize;
        numTrials = trials;
        results = new double[trials];
        double numOpenSites;

        gridSize = n;

        for (int t = 0; t < numTrials; t++) {
            p = new Percolation(gridSize);
            numOpenSites = 0;
            while (!p.percolates()) {
                int randomRow = StdRandom.uniform(1, gridSize + 1);
                int randomCol = StdRandom.uniform(1, gridSize + 1);
                if (!p.isOpen(randomRow, randomCol)) {
                    p.open(randomRow, randomCol);
                    numOpenSites += 1;
                }
            }
            pThreshold = numOpenSites / (double) (gridSize * gridSize);
            results[t] = pThreshold;
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(numTrials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(numTrials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats tester = new PercolationStats(n, t);
        System.out.println(tester.mean());
        System.out.println(tester.stddev());
    }


}

