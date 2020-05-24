/* *****************************************************************************
 *  author: Andy Luu
 *  description: A mutable data type that represents a set of points in the unit square
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;

public class PointSET {

    private final SET<Point2D> set;
    private int size;

    public PointSET() {
        // construct an empty set of points
        this.set = new SET<Point2D>();
        this.size = 0;

    }


    public boolean isEmpty() {
        // is the set empty?
        return this.size == 0;
    }


    public int size() {
        // number of points in the set
        return this.size;
    }


    public void insert(Point2D p) {
        // add the point to the set (if it is not already in the set)
        if (p == null) throw new IllegalArgumentException();
        if (!this.set.contains(p)) this.size++;
        this.set.add(p);
    }


    public boolean contains(Point2D p) {
        // does the set contain point p
        if (p == null) throw new IllegalArgumentException();
        return this.set.contains(p);
    }


    public void draw() {
        // draw all points to standard draw
        for (Point2D p : set) p.draw();
    }


    public Iterable<Point2D> range(RectHV rect) {
        // all points that are inside the rectangle (or on the boundary)
        if (rect == null) throw new IllegalArgumentException();
        ArrayList<Point2D> pointsInside = new ArrayList<>();
        for (Point2D p : set) {
            if (rect.contains(p)) pointsInside.add(p);
        }
        return pointsInside;
    }


    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null) throw new IllegalArgumentException();
        if (set.isEmpty()) return null;

        Point2D nearest = null;

        for (Point2D q : set) {
            if (nearest == null) nearest = q;
            if (p.distanceSquaredTo(q) < p.distanceSquaredTo(nearest)) nearest = q;
        }
        return nearest;
    }


    public static void main(String[] args) {
        // unit testing of the methods (optional)
        Point2D a = new Point2D(0, 0);
        Point2D b = new Point2D(2, 2);
        Point2D c = new Point2D(4, 4);
        Point2D d = new Point2D(8, 8);
        Point2D[] points = { a, b, c, d };

        PointSET tester = new PointSET();
        for (Point2D p : points) tester.insert(p);
        System.out.println(tester.nearest(new Point2D(4, 3)));

        for (Point2D p : tester.range(new RectHV(1, 0, 5, 5))) System.out.println(p);


    }

}
