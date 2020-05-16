/* *****************************************************************************
 *  @author Andy Luu
 *  @description Examines four points at a time and checks whether they all line in the same line segment, returning all sugn line segments (faster sorting-based approach)
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FastCollinearPoints {

    private final ArrayList<LineSegment> lineSegs = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {

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
        for (Point p : points) {
            Arrays.sort(sortedPoints);
            Arrays.sort(sortedPoints, p.slopeOrder());

            for (int i = 1; i < sortedPoints.length; i++) {
                int n = 0;
                int a = i;
                int b = i + 1;
                while (b < sortedPoints.length && p.slopeTo(sortedPoints[a]) == p
                        .slopeTo(sortedPoints[b])) {

                    n++;
                    a++;
                    b++;
                }
                if (n >= 2 && p.compareTo(sortedPoints[i]) < 0) {

                    ArrayList<Point> seg = new ArrayList<>();
                    seg.add(p);
                    for (int z = i; z <= i + n; z++) {
                        seg.add(sortedPoints[z]);
                    }

                    Collections.sort(seg);


                    // if (p.slopeTo(q) == p.slopeTo(r)
                    //         && p.slopeTo(r) == p.slopeTo(s)) {
                    //     Point max;
                    //     Point min;
                    //
                    //     int sP = s.compareTo(p);
                    //     int sQ = s.compareTo(q);
                    //     int sR = s.compareTo(r);
                    //     int rP = r.compareTo(p);
                    //     int rQ = r.compareTo(q);
                    //     int rS = r.compareTo(s);
                    //     int qP = q.compareTo(p);
                    //     int qR = q.compareTo(r);
                    //     int qS = q.compareTo(s);
                    //
                    //
                    //     if (sP > 0 && sQ > 0 && sR > 0) {
                    //         max = s;
                    //     }
                    //     else if (rP > 0 && rQ > 0 && rS > 0) {
                    //         max = r;
                    //     }
                    //     else if (qP > 0 && qR > 0 && qS > 0) {
                    //         max = q;
                    //     }
                    //     else {
                    //         max = p;
                    //     }
                    //
                    //     if (sP < 0 && sQ < 0 && sR < 0) {
                    //         min = s;
                    //     }
                    //     else if (rP < 0 && rQ < 0 && rS < 0) {
                    //         min = r;
                    //     }
                    //     else if (qP < 0 && qR < 0 && qS < 0) {
                    //         min = q;
                    //     }
                    //     else {
                    //         min = p;
                    //     }

                    System.out.println(seg);
                    LineSegment lineseg = new LineSegment(seg.get(0), seg.get(seg.size() - 1));

                    this.lineSegs.add(lineseg);

                    // if (p.compareTo(min) == 0) {
                    //     this.lineSegs.add(lineseg);
                    // }

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

        // Point p1 = new Point(0, 0);
        // Point p2 = new Point(1, 1);
        // Point p3 = new Point(2, 2);
        // Point p4 = new Point(3, 3);
        // // Point p5 = new Point(0, 1);
        // // Point p6 = new Point(1, 2);
        // // Point p7 = new Point(2, 3);
        // // Point p8 = new Point(3, 4);
        // // Point p9 = new Point(0, 2);
        // // Point p10 = new Point(1, 2);
        // // Point p11 = new Point(2, 2);
        // // Point p12 = new Point(3, 2);
        // FastCollinearPoints tester = new FastCollinearPoints(
        //         new Point[] { p1, p2, p3, p4 });
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

