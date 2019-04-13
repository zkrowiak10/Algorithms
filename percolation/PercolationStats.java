import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final int sites;
    private final double[] p;
    private final double zValue = 1.96;

    public PercolationStats(int n,
                            int trials) {    // perform trials independent experiments on an n-by-n grid

        if (!(n > 0) || !(trials > 0)) {
            throw new java.lang.IllegalArgumentException("N and Trials must be greater than 0");
        }
        sites = n * n;
        p = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation grid = new Percolation(n);
            int max = n + 1; // array address max is n (no zero), random must be able to yield n


            while (!grid.percolates()) {
                int row = StdRandom.uniform(1, max);
                int col = StdRandom.uniform(1, max);

                grid.open(row, col);


            }
            double threshold = (double) grid.numberOfOpenSites() / (double) sites;
            p[i] = threshold;
            // System.out.printf("opensites = %d \n", grid.openSites);
            // System.out.println(threshold);
        }
    }

    public double mean() {                          // sample mean of percolation threshold
        return StdStats.mean(p);
    }

    public double stddev() {                        // sample standard deviation of percolation threshold
        return StdStats.stddev(p);
    }

    public double confidenceLo() {                  // low  endpoint of 95% confidence interval
        double rootT = java.lang.Math.sqrt((double) sites);
        double x = this.stddev() / rootT;
        return this.mean() - zValue * x;
    }

    public double confidenceHi() {                  // high endpoint of 95% confidence interval
        double rootT = java.lang.Math.sqrt((double) sites);
        double x = this.stddev() / rootT;
        return this.mean() + zValue * x;
    }


    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        System.out.printf("Beginning test with grid of size %d and running %d times\n", n, t);
        PercolationStats testObject = new PercolationStats(n, t);

        double lo = testObject.confidenceLo();
        double hi = testObject.confidenceHi();

        System.out.printf("Mean = %f\n", testObject.mean());
        System.out.printf("stdev = %f\n", testObject.stddev());
        System.out.printf("95%% Confidence interval = [ %f , %f ]\n", lo, hi);


    }
}
