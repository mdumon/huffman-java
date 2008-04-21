package bitutils;

/**
 * An <code>ArrayTooShortException</code> is thrown
 * when a byte array does not contains enough bits 
 * to fill a BitArray.
 * 
 * It extends a RuntimeException and not an Exception because
 * if parameters are carefully chosen it will not be thrown.
 * 
 * @author Franck Séhédic, Dumon Maxime
 * @see java.lang.IllegalArgumentException
 */
public class ArrayTooShortException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public ArrayTooShortException(){}
}
