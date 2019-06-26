/** A class that represents a path via pursuit curves. */
public class Path {
    public Point Curr;
    public Point Next;


    public Path (double x, double y){
        this.Next.setX(x);
        this.Next.setY(y);
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
        // mypa.Curr.setX(4);
        // mypa.Curr.setX(5);
        // System.out.println(mypa.getCurrX(), mypa.getCurrY(), mypa.getNextX(), mypa.getNextY(), mypa.getCurrentPoint().getX());
        // System.out.println("m");

    }
}
