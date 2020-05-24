/* *****************************************************************************
 *  author: Andy Luu
 *  description: Uses a 2d-tree to implement the same API as PointSET
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class KdTree {

    private static class Node {
        private final Point2D p;
        private final RectHV rect;
        private Node left;
        private Node right;

        public Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;

        }
    }

    private int size;
    private Node root;

    public KdTree() {
        // construct an empty set of points
        this.size = 0;
        this.root = null;

    }


    public boolean isEmpty() {
        // is the set empty?
        return this.root == null;
    }


    public int size() {
        // number of points in the set
        return this.size;
    }


    public void insert(Point2D p) {
        // add the point to the set (if it is not already in the set)
        if (p == null) throw new IllegalArgumentException();

        if (!this.contains(p)) root = this.insert(root, p, "Vertical", new RectHV(0, 0, 1, 1));
    }

    private Node insert(Node temp, Point2D p, String orientation, RectHV rect) {

        if (temp == null) {
            this.size++;
            return new Node(p, rect);
        }

        int cmp;
        if (orientation.equals("Vertical")) cmp = Double.compare(p.x(), temp.p.x());
        else cmp = Double.compare(p.y(), temp.p.y());

        if (cmp < 0) {
            if (orientation.equals("Vertical"))
                rect = new RectHV(rect.xmin(), rect.ymin(), temp.p.x(), rect.ymax());
            else rect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), temp.p.y());
            if (orientation.equals("Horizontal")) orientation = "Vertical";
            else orientation = "Horizontal";
            temp.left = insert(temp.left, p, orientation, rect);
        }
        else {
            if (orientation.equals("Vertical"))
                rect = new RectHV(temp.p.x(), rect.ymin(), rect.xmax(), rect.ymax());
            else rect = new RectHV(rect.xmin(), temp.p.y(), rect.xmax(), rect.ymax());
            if (orientation.equals("Horizontal")) orientation = "Vertical";
            else orientation = "Horizontal";
            temp.right = insert(temp.right, p, orientation, rect);
        }
        return temp;
    }


    public boolean contains(Point2D p) {
        // does the set contain point p
        if (p == null) throw new IllegalArgumentException();

        // even counter corresponds to next node is vertical
        int counter = 0;

        Node temp = root;
        while (temp != null) {
            boolean vertical;
            if (counter % 2 == 0) vertical = true;
            else vertical = false;

            if (temp.p.equals(p)) return true;

            int cmp;
            if (vertical) cmp = Double.compare(p.x(), temp.p.x());
            else cmp = Double.compare(p.y(), temp.p.y());

            counter++;
            if (cmp < 0) temp = temp.left;
            else temp = temp.right;
        }
        return false;
    }


    public void draw() {
        // draw all points to standard draw
        draw(this.root, "Vertical");
    }

    private void draw(Node temp, String orientation) {

        if (temp == null) return;

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        temp.p.draw();

        if (orientation.equals("Vertical")) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            temp.p.drawTo(new Point2D(temp.p.x(), temp.rect.ymax()));
            temp.p.drawTo(new Point2D(temp.p.x(), temp.rect.ymin()));
            draw(temp.left, "Horizontal");
            draw(temp.right, "Horizontal");
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            temp.p.drawTo(new Point2D(temp.rect.xmin(), temp.p.y()));
            temp.p.drawTo(new Point2D(temp.rect.xmax(), temp.p.y()));
            draw(temp.left, "Vertical");
            draw(temp.right, "Vertical");
        }
    }


    public Iterable<Point2D> range(RectHV rect) {
        // all points that are inside the rectangle (or on the boundary)
        if (rect == null) throw new IllegalArgumentException();
        ArrayList<Point2D> pointsInside = new ArrayList<>();
        range(root, rect, pointsInside);
        return pointsInside;
    }

    private void range(Node temp, RectHV rect, ArrayList<Point2D> pointsInside) {

        if (temp == null) return;

        if (rect.contains(temp.p)) {
            pointsInside.add(temp.p);
        }

        if (temp.left != null && rect.intersects(temp.left.rect))
            range(temp.left, rect, pointsInside);
        if (temp.right != null && rect.intersects(temp.right.rect))
            range(temp.right, rect, pointsInside);

    }


    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null) throw new IllegalArgumentException();
        if (this.root == null) return null;
        return nearest(root, p, null, "Vertical").p;
    }

    private Node nearest(Node temp, Point2D p, Node champion, String orientation) {

        if (champion == null) {
            champion = temp;
        }

        if (temp.p.distanceSquaredTo(p) < champion.p.distanceSquaredTo(p)) {
            champion = temp;
        }

        // // if the closest node in the subtree is further than the champion, stop searching the subtree
        // if (temp.rect.distanceSquaredTo(p) > championDistance) return champion;

        if (orientation.equals("Vertical")) {
            if (p.x() < temp.p.x()) {
                if (temp.left != null && temp.left.rect.distanceSquaredTo(p) < champion.p
                        .distanceSquaredTo(p))
                    champion = nearest(temp.left, p, champion, "Horizontal");
                if (temp.right != null && temp.right.rect.distanceSquaredTo(p) < champion.p
                        .distanceSquaredTo(p))
                    champion = nearest(temp.right, p, champion, "Horizontal");
            }
            else {
                if (temp.right != null && temp.right.rect.distanceSquaredTo(p) < champion.p
                        .distanceSquaredTo(p))
                    champion = nearest(temp.right, p, champion, "Horizontal");

                if (temp.left != null && temp.left.rect.distanceSquaredTo(p) < champion.p
                        .distanceSquaredTo(p))
                    champion = nearest(temp.left, p, champion, "Horizontal");


            }
        }
        else {
            if (p.y() < temp.p.y()) {
                if (temp.left != null && temp.left.rect.distanceSquaredTo(p) < champion.p
                        .distanceSquaredTo(p))
                    champion = nearest(temp.left, p, champion, "Vertical");
                if (temp.right != null && temp.right.rect.distanceSquaredTo(p) < champion.p
                        .distanceSquaredTo(p))
                    champion = nearest(temp.right, p, champion, "Vertical");
            }
            else {
                if (temp.right != null && temp.right.rect.distanceSquaredTo(p) < champion.p
                        .distanceSquaredTo(p))
                    champion = nearest(temp.right, p, champion, "Vertical");
                if (temp.left != null && temp.left.rect.distanceSquaredTo(p) < champion.p
                        .distanceSquaredTo(p))
                    champion = nearest(temp.left, p, champion, "Vertical");
            }
        }

        return champion;

    }


    public static void main(String[] args) {

        Point2D a = new Point2D(0.25, 0.75);
        Point2D b = new Point2D(0.0, 0.5);
        Point2D c = new Point2D(0.875, 0.375);
        Point2D d = new Point2D(0.625, 0.25);
        Point2D e = new Point2D(0.375, 0.875);
        Point2D[] points = { a, b, c, d, e };

        KdTree tester = new KdTree();
        for (Point2D p : points) tester.insert(p);
        tester.draw();

        System.out.println(tester.nearest(new Point2D(0.75, 1.0)));
    }

}
