public class Salmon extends Fish {

//    // Constructor C: No-args Salmon Constructor
//    public Salmon() {
//
//    }

//    // Constructor D: Salmon Constructor with an int parameter
//    public Salmon(int w) {
//        weight = w;
//    }


    // Constructor E: Salmon Constructor with an int parameter
    // and a call to the super int parameter Constructor
    int length;

    public Salmon(int w, int p) {
        super(w);
        length = p;

    }



    public void fry() {
        System.out.println("Frying sssssssssalmon");
    }

    @Override
    public void swim() {
        System.out.println("splish splash");
    }

    public void swim(int speed) {
        System.out.println("swimming at " + speed + " mph");
    }

    public static void main(String[] args){

        Fish d = new Salmon(1,2);
        d.swim();
    }
}
