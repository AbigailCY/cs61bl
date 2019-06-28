public class SieveOfEratosthenes {

	public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("You need to enter an argument!" +
                    "\nIn IntelliJ, you can do this by clicking on the button with \n`SieveOfEratosthenes` on it" +
                    " > `Edit Configurations...` " +
                    "and provide a number in the `Program arguments` box.");
            return;
        }
		int upperBound = Integer.parseInt(args[0]);
		boolean[] isNotPrime = new boolean[upperBound];

		for (int i = 2; i < upperBound; i++) {

			int previous = 2;
			while (previous <= i/2){
				if (i % previous == 0){
					break;
				}
				previous ++;
			}
			if (previous > i/2){
				isNotPrime[i] = true;
			}
		}


		for (int i = 0; i < upperBound; i++) {
			if (isNotPrime[i]) {
				System.out.println(i + " is a prime number.");
			}
		}
	}
}
