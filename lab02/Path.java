

/** A class that represents a path via pursuit curves. */
public class Path {
    public Point Curr;
    public Point Next;


    public Path (double x, double y){
        Point Curr = new Point(x, y);
    }

    public double getCurrX() {
        return this.Curr.getX();
    }
    public double getCurrY() {
        return this.Curr.getY();
    }
    public double getNextX() {
        return this.Next.getX();
    }
    public double getNextY() {
        return this.Next.getY();
    }

    public Point getCurrentPoint() {
        return this.Curr;
    }

    
    public void setCurrentPoint(Point point){
        this.Curr = point;
    }

    public void iterate(double dx, double dy){
        this.Curr = this.Next;
        this.Next.setX(this.Curr.getX()+dx);
        this.Next.setY(this.Curr.getY()+dy);
    }

    public static void main(String[] args){
        Path mypa = new Path(1, 2);
        Point pa = new Point(3,4);
        mypa.setCurrentPoint(pa);
        // System.out.println( mypa.getCurrX()+mypa.getCurrY());
    }
}
