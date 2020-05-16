/* *****************************************************************************
 *  @author Andy Luu
 *  @description Examines four points at a time and checks whether they all line in the same line segment, returning all sugn line segments (brute force approach)
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private final ArrayList<LineSegment> lineSegs = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {

        if (points == null) throw new IllegalArgumentException();

        Point[] clonedPoints = points.clone();
        Arrays.sort(clonedPoints);

        for (int i = 0; i < points.length - 1; i++) {

            if (clonedPoints[i] == null) {
                throw new IllegalArgumentException();
            }

            if (clonedPoints[i].compareTo(clonedPoints[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }


        }
        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);
        int n = sortedPoints.length;

        for (int i = 0; i < n - 3; i++) {
            for (int j = 1; j < n - 2; j++) {
                for (int k = 2; k < n - 1; k++) {
                    for (int m = 3; m < n; m++) {

                        Point p = sortedPoints[i];
                        Point q = sortedPoints[j];
                        Point r = sortedPoints[k];
                        Point s = sortedPoints[m];

                        double slope1 = p.slopeTo(q);
                        double slope2 = q.slopeTo(r);
                        double slope3 = r.slopeTo(s);

                        if (slope1 == slope2 && slope2 == slope3) {
                            if (p.compareTo(q) < 0 && q.compareTo(r) < 0 && r.compareTo(s) < 0) {
                                LineSegment lineseg = new LineSegment(p, s);


                                this.lineSegs.add(lineseg);


                            }


                        }

                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return this.lineSegs.size();
    }

    public LineSegment[] segments() {
        return this.lineSegs.toArray(new LineSegment[lineSegs.size()]);
    }

    public static void main(String[] args) {

        // Point p1 = new Point(2, 0);
        // Point p2 = new Point(2, 1);
        // Point p3 = new Point(2, 2);
        // Point p4 = new Point(2, 3);
        // Point p5 = new Point(0, 1);
        // Point p6 = new Point(1, 2);
        // Point p7 = new Point(2, 3);
        // Point p8 = new Point(3, 4);
        // Point p9 = new Point(0, 2);
        // Point p10 = new Point(1, 2);
        // Point p11 = new Point(2, 2);
        // Point p12 = new Point(3, 2);
        // BruteCollinearPoints tester = new BruteCollinearPoints(
        //         new Point[] { p1, p2, p3, p4, p5, p6, p7, p8 });
        // System.out.println(Arrays.toString(tester.segments()));
        // BruteCollinearPoints tester2 = new BruteCollinearPoints(
        //         new Point[] { p5, p6, p7, p8 });
        // System.out.println(Arrays.toString(tester2.segments()));
        // BruteCollinearPoints tester3 = new BruteCollinearPoints(
        //         new Point[] { p9, p10, p11, p12 });
        // System.out.println(Arrays.toString(tester3.segments()));

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
