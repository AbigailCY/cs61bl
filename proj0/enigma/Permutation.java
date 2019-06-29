package enigma;

import static enigma.EnigmaException.*;

/** Represents a permutation of a range of integers starting at 0 corresponding
 *  to the characters of an alphabet.
 *  @AbigailChen
 */
public class Permutation {

    /** Set this Permutation to that specified by CYCLES, a string in the
     *  form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     *  is interpreted as a permutation in cycle notation.  Characters in the
     *  alphabet that are not included in any cycle map to themselves.
     *  Whitespace is ignored. */


    public Permutation(String cycles, Alphabet alphabet) {
        _alphabet = alphabet;
        _cycles = cycles;

        // FIXME - Assign any additional instance variables.
    }

    /** Return the value of P modulo the size of this permutation. */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /** Returns the size of the alphabet I permute. */
    public int size() {
        return _alphabet.size();
    }

    /** Return the index result of applying this permutation to the character
     *  at index P in ALPHABET. */
    public int permute(int p) {
    	// NOTE: it might be beneficial to have one permute() method always call the other
        char charP = _alphabet.toChar(p);
        char Q = permute(charP);
        return _alphabet.toInt(Q);

    }

    /** Return the index result of applying the inverse of this permutation
     *  to the character at index C in ALPHABET. */
    public int invert(int c) {
    	// NOTE: it might be beneficial to have one invert() method always call the other
        char charC = _alphabet.toChar(c);
        char Q = invert(charC);
        return _alphabet.toInt(Q);

    }

    /** Return the character result of applying this permutation to the index
     * of character P in ALPHABET. */
    public char permute(char p) {
    	// NOTE: it might be beneficial to have one permute() method always call the other
        int posP = this._cycles.indexOf(p);
        String quote = "()";
        if (posP == -1){
            return p;
        }
        if (_cycles.charAt(posP+1) == quote.charAt(1)){
            int leftQ = _cycles.lastIndexOf(quote.charAt(0), posP);
            return _cycles.charAt(leftQ+1);
        }
        return  _cycles.charAt(posP+1);

    }

    /** Return the character result of applying the inverse of this permutation
	 * to the index of character C in ALPHABET. */
    public char invert(char c) {
    	// NOTE: it might be beneficial to have one invert() method always call the other
        int posC = this._cycles.indexOf(c);
        String quote = "()";
        if (posC == -1){
            return c;
        }
        if (_cycles.charAt(posC-1) == quote.charAt(0)){
            int rightQ = _cycles.indexOf(quote.charAt(1), posC);
            return _cycles.charAt(rightQ-1);
        }
        return  _cycles.charAt(posC-1);

    }

    /** Return the alphabet used to initialize this Permutation. */
    public Alphabet alphabet() {
        return _alphabet;
    }

    /** Alphabet of this permutation. */
    private Alphabet _alphabet;
    private String _cycles;

    // Some starter code for unit tests. Feel free to change these up!
    // To run this through command line, from the proj0 directory, run the following:
    // javac enigma/Permutation.java enigma/Alphabet.java enigma/CharacterRange.java enigma/EnigmaException.java
    // java enigma/Permutation
    public static void main(String[] args) {
//        Permutation perm = new Permutation("(ABCDEFGHIJKLMNOPQRSTUVWXYZ)", new CharacterRange('A', 'Z'));
        Permutation perm = new Permutation("(AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV) (JZ) (S)", new CharacterRange('A', 'Z'));
        System.out.println(perm.size() == 26);
        System.out.println(perm.permute('A') == 'E');
        System.out.println(perm.invert('W') == 'N');
        System.out.println(perm.permute(0) == 4);
        System.out.println(perm.invert(22) == 13);
    }
}
