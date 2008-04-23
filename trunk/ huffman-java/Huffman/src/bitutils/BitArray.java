 package bitutils;

/**
 * A <code>BitArray</code> is an Array of bits
 * beeing able to be built from a byte array or
 * a boolean array and storing each bit as a boolean value.
 * 
 * @author Franck Séhédic
 * @author Maxime Dumon
 * 
 * @version 0.0.1 - 19/04/08
 */

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class BitArray implements Iterable<Boolean>, Serializable{
	private static final long serialVersionUID = 1L;
	
	/* liste utilisée pour stocker en interne les bits */ 
	private List<Boolean> bList;
	
	/* Constante représentant l'endianness dans laquelle le BitArray travaille (BIG_ENDIAN uniquement pour le moment) */
	public enum Endianness { BIG_ENDIAN, LITTLE_ENDIAN };
	
	/* Index permettant de travailler en little ou big endian sur les bytes, 7 == big endian == lecture de gauche à droite */
	private final int endianShift = 7;
	
	/**
	 * Build an empty BitArray
	 */
	public BitArray(){
		bList = new Vector<Boolean>(8,8);
	}
	
	/**
	 * Build a BitArray from a Byte[]
	 * @param tb Byte Array from which bits will be read
	 * @throws ArrayTooShortException Thrown when tb contain less bits than nbBits
	 */
	public BitArray(byte[] b){
		this(b,b.length*8);
	}
	
	/**
	 * Build a BitArray from a Byte[]
	 * @param tb Byte Array from which bits will be read
	 * @param nbBits Number of bits to be read
	 * @throws ArrayTooShortException Thrown when tb contain less bits than nbBits
	 */
	public BitArray(byte[] b,int nbBits) throws ArrayTooShortException{
		this(b,nbBits,0);
	}
	
	/**
	 * Build a BitArray from a Byte[]
	 * @param tb Byte Array from which bits will be read
	 * @param nbBits Number of bits to be read
	 * @param bitOff bit offset from which we'll start reading
	 * @throws ArrayTooShortException Thrown when tb contain less bits than nbBits+bitOff
	 */
	public BitArray(byte[] tb,int nbBits,int bitOff) throws ArrayTooShortException{
		this(tb,nbBits,bitOff,0);
	}
	
	/**
	 * Build a BitArray from a Byte[]
	 * @param tb Byte Array from which bits will be read
	 * @param nbBits Number of bits to be read
	 * @param bitOff bit offset from which we'll start reading
	 * @param byteOff byte offset from which we'll start reading (reading will start at bit byteOff*8+bitOff)
	 * @throws ArrayTooShortException Thrown when tb contain less bits than nbBits+bitOff+byteOff*8
	 */
	public BitArray(byte[] tb,int nbBits,int bitOff,int byteOff) throws ArrayTooShortException{
		this();
		
		if(tb.length*8 < (nbBits+bitOff)) throw new ArrayTooShortException();
		
		boolean res;
		int maxBit = byteOff*8+nbBits+bitOff;
		
		for(int i = (bitOff + byteOff*8); i < maxBit ; i++){
			res = (((tb[(i/8)] >> (endianShift - (i % 8))) & 1) == 1);
			add(res);
		}
	}
	
	public Endianness getBitEndianness(){
		return (endianShift == 0)?Endianness.LITTLE_ENDIAN:Endianness.BIG_ENDIAN;
	}

	public boolean[] toBooleanArray(){
		boolean[] tb;
		int listSize = size();
		
		tb = new boolean[listSize];
		
		for(int i = 0; i < listSize; i++)
			tb[i] = get(i);
		
		return tb;
	}
	
	public byte[] toByteArray(){
		if(size() == 0) return new byte[0];
		
		byte[] tb;
		int bit;
		
		tb = new byte[((size()-1)/8) + 1];
		Arrays.fill(tb, (byte)0);
		
		for(int i = 0; i < size(); i++){
			bit = (get(i)?1:0);
			tb[i/8] |= (bit << (endianShift - (i%8)));
		}
		
		return tb;
	}
	
	public byte[] removeWritableByteArray(){
		byte[] tb = new byte[(size()/8)];
		int bit;
		
		Arrays.fill(tb, (byte)0);
		
		for(int i = 0; i < tb.length*8; i++){
			bit = (remove(0)?1:0);
			tb[i/8] |= (bit << (endianShift - (i%8)));
		}
		
		return tb;
	}
	
	public byte[] removeByteArray(){
		if(size() == 0) return new byte[0];
		
		byte[] tb = new byte[((size() - 1)/8) + 1];
		int bit;
		
		Arrays.fill(tb, (byte)0);
		
		for(int i = 0; size() > 0; i++){
			bit = (remove(0)?1:0);
			tb[i/8] |= (bit << (endianShift - (i%8)));
		}
		
		return tb;
	}
	
	@Override
	public boolean equals(Object ba){
		if(!(ba instanceof BitArray)) return false;
		BitArray b = (BitArray)ba;
		
		if(b.size() != size()) return false;
		
		for(int i = 0; i < size(); i++)
			if(get(i) != b.get(i)) return false;
		
		return true;
	}
	
	@Override
	public Iterator<Boolean> iterator() {
		return bList.listIterator();
	}
	
	public void add(boolean b){
		bList.add(b);
	}
	
	public void add(int index,boolean b){
		bList.add(index,b);
	}
	
	public boolean remove(int index){
		return bList.remove(index);
	}
	
	public void clear(){
		bList.clear();
	}
	
	public boolean get(int index){
		return bList.get(index);
	}
	
	public void set(int index, boolean value){
		bList.set(index, value);
	}
	
	public boolean isEmpty(){
		return bList.isEmpty();
	}
	
	public int size(){
		return bList.size();
	}
	
	@Override
	public String toString(){
		String s = "";
		for(boolean b : this)
			s += (b)?"1":"0";
		return s;
	}
}
