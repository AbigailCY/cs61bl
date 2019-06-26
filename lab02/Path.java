

/** A class that represents a path via pursuit curves. */
public class Path {
    public Point Curr;
    public Point Next;


    public Path (double x, double y){
        this.Next = new Point(x, y);
        // this.Next.setX(x);
        // this.Next.setY(y);
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
        this.Curr.setX(this.Next.getX()); 
        this.Curr.setY(this.Next.getY());
        this.Next.setX(this.Curr.getX()+dx);
        this.Next.setY(this.Curr.getY()+dy);
    }

    public static void main(String[] args){
        Path mypa = new Path(0, 1);
        Point pa = new Point(3,4);
        mypa.setCurrentPoint(pa);
        mypa.iterate(5, 5);
        System.out.println(mypa.getCurrX()+" "+ mypa.getCurrY()+ " "+mypa.getNextX()+" "+mypa.getNextY());
        // System.out.println(String.format("%.1f %.1f", mypa.getNextX(), mypa.getNextY()));
    }
}
