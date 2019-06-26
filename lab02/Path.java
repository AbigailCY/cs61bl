/** A class that represents a path via pursuit curves. */
public class Path {
    public Point Curr;
    public Point Next;


    public Path (double x, double y){
        this.Next.setX(x);
        this.Next.setY(y);
        this.Curr.setX(0);
        this.Curr.setY(0);
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

        this.Next.setX(this.Next.getX()+dx);
        this.Next.setY(this.Next.getY()+dy);
    }
}
