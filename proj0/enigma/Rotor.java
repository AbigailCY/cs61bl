package enigma;

import static enigma.EnigmaException.*;

/** Superclass that represents a rotor in the enigma machine.
 *  @AbigailChen
 */
public class Rotor {



    /** A rotor named NAME whose permutation is given by PERM. */
    public Rotor(String name, Permutation perm) {
        _name = name;
        _permutation = perm;
        _position = 0;
    }

    /** Return my name. */
    public String name() {
        return _name;
    }

    /** Return my alphabet. */
    public Alphabet alphabet() {
        return _permutation.alphabet();
    }

    /** Return my permutation. */
    public Permutation permutation() {
        return _permutation;
    }

    /** Return the size of my alphabet. */
    public int size() {
        return _permutation.size();
    }

    /** Return true if and only if I have a ratchet and can move. */
    public boolean rotates() {
        return false;
    }

    /** Return true if and only if I reflect. */
    public boolean reflecting() {
        return false;
    }

    /** Return my current setting. */
    public int setting() {

        return _position;
    }

    /** Set setting() to POSN.  */
    public void set(int posn) {
        _position = posn;
    }

    /** Set setting() to character CPOSN. */
    public void set(char cposn) {
        int posn = alphabet().toInt(cposn);
        _position = posn;
    }

    /** Return the conversion of P (an integer in the range 0..size()-1)
     *  according to my permutation. */
    public int convertForward(int p) {
        int rotorP = _permutation.wrap(_position + p);
        int rotorQ = _permutation.permute(rotorP);
        int Q = _permutation.wrap(rotorQ - _position);
        return Q;

    }

    /** Return the conversion of C (an integer in the range 0..size()-1)
     *  according to the inverse of my permutation. */
    public int convertBackward(int c) {

        int rotorC = _permutation.wrap(_position + c);
        int rotorQ = _permutation.invert(rotorC);
        int Q = _permutation.wrap(rotorQ - _position);
        return Q;

    }

    /** Returns true if and only if I am positioned to allow the rotor
     * to my left to advance. */
    public boolean atNotch() {
        return false;
    }

    /** Advance me one position, if possible. By default, does nothing. */
    public void advance() {
    }

//    @Override®
    public String toString() {
        return "Rotor " + _name;
    }

    /** My name. */
    private final String _name;

    /** The permutation implemented by this rotor in its 0 position. */
    private Permutation _permutation;

    /** My position now. */
    private int _position;

}
