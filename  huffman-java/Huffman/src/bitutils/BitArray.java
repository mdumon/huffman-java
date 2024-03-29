package bitutils;

import java.io.Serializable;
import java.util.Iterator;

/**
 * <code>BitArray</code> is an interface
 * to work with bits arrays instead of byte arrays
 * 
 * @author Franck Séhédic <sehedic@ecole.ensicaen.fr>
 */
public interface BitArray extends Iterable<Boolean>, Serializable{
	
 	public boolean[] toBooleanArray();
 	
 	public byte[] toByteArray();
 	
 	public byte[] removeWritableByteArray();
 	
 	public byte[] removeByteArray();
 	
 	public byte removeByteArray2();
 	
 	public boolean equals(Object ba);
 	
 	public int size();
 	
 	@Override
 	public Iterator<Boolean> iterator();
 	
 	public BitArray add(boolean b);
 	
 	public BitArray add(int index, boolean b);
 	
 	public boolean remove(int index);
 	
 	public boolean removeLast();
 	
 	public void clear();
 	
 	public boolean get(int index);
 	
 	public void set(int index, boolean value);
 	
 	public void setLast(boolean value);
 	
 	public boolean isEmpty();
 	
 	public String toString();
 	
 	public enum Endianness { BIG_ENDIAN, LITTLE_ENDIAN };

 	public Endianness getBitEndianness();
}
