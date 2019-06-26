

/** A class that represents a path via pursuit curves. */
public class Path {
    public Point Curr;
    public Point Next;


    public Path (double x, double y){
        this.Next = new Point(x, y);
        this.Curr = new Point(1,1);
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
        mypa.iterate(1,1);
        System.out.println(mypa.getCurrX()+" "+ mypa.getCurrY()+ " "+mypa.getNextX()+" "+mypa.getNextY());
        mypa.iterate(1,1);
        System.out.println(mypa.getCurrX()+" "+ mypa.getCurrY()+ " "+mypa.getNextX()+" "+mypa.getNextY());
        // System.out.println(String.format("%.1f %.1f", mypa.getNextX(), mypa.getNextY()));
    }
}
