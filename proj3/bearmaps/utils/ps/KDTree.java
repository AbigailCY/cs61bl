package bearmaps.utils.ps;
import java.util.List;

public class KDTree implements PointSet {

    KDTreeNode root;
    int size;

    /* Constructs a KDTree using POINTS. You can assume POINTS contains at least one
       Point object. */
    public KDTree(List<Point> points) {
        this.size = points.size();
        for (Point p : points) {
            this.root = insert(this.root, p, true);
        }
    }


//    You might find this insert helper method useful when constructing your KDTree!
//    Think of what arguments you might want insert to take in. If you need
//    inspiration, take a look at how we do BST insertion!

    private KDTreeNode insert(KDTreeNode node, Point key, boolean xy) {
        if (node == null) {
            return new KDTreeNode(key);
        }
        if (node.point.equals(key)) {
            return node;
        }
        double comp = 0;
        if (xy) {
            comp = node.point.getX() - key.getX();
        } else if (!xy) {
            comp = node.point.getY() - key.getY();
        }

        if (comp > 0) {
            node.left = insert(node.left, key, !xy);
        } else {
            node.right = insert(node.right, key, !xy);
        }
        return node;

    }


    /* Returns the closest Point to the inputted X and Y coordinates. This method
       should run in O(log N) time on average, where N is the number of POINTS. */
    public Point nearest(double x, double y) {
        return nearestHelper(new Point(x, y), root, root.point);
    }

    public Point nearestHelper(Point g, KDTreeNode node, Point best) {
        if (node == null) {
            return best;
        }
        double dist = Point.distance(g, node.point);
        KDTreeNode good;
        KDTreeNode bad;
        boolean useful = false;

        if (dist < Point.distance(g, best)) {
            best = node.point;
        }

        int xy = find(root, node.point, 1);
        if (xy == 1) {
            if (g.getX() < node.point.getX()) {
                good = node.left;
                bad = node.right;
            } else {
                good = node.right;
                bad = node.left;
            }
        } else {
            if (g.getY() < node.point.getY()) {
                good = node.left;
                bad = node.right;
            } else {
                good = node.right;
                bad = node.left;
            }
        }
        best = nearestHelper(g, good, best);


        if (xy == 1) {
            if (bad != null && Math.abs(g.getY() - bad.point.getY()) < Point.distance(g, best)) {
                best = nearestHelper(g, bad, best);
            }
        } else {
            if (bad != null && Math.abs(g.getX() - bad.point.getX()) < Point.distance(g, best)) {
                best = nearestHelper(g, bad, best);
            }
        }

        return best;

    }


    public int find(KDTreeNode node, Point target, int row) {
        if (node.point == target) {
            return row % 2;
        }

        if (row % 2 == 1) {
            if (target.getX() < node.point.getX()) {
                return find(node.left, target, row + 1);
            } else {
                return find(node.right, target, row + 1);
            }
        } else {
            if (target.getY() < node.point.getY()) {
                return find(node.left, target, row + 1);
            } else {
                return find(node.right, target, row + 1);
            }
        }
    }


    private class KDTreeNode {

        private Point point;
        private KDTreeNode left;
        private KDTreeNode right;

        // If you want to add any more instance variables, put them here!

        KDTreeNode(Point p) {
            this.point = p;
        }

        KDTreeNode(Point p, KDTreeNode left, KDTreeNode right) {
            this.point = p;
            this.left = left;
            this.right = right;
        }

        Point point() {
            return point;
        }

        KDTreeNode left() {
            return left;
        }

        KDTreeNode right() {
            return right;
        }

        // If you want to add any more methods, put them here!

    }
}
