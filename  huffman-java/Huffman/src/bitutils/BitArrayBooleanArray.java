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

public class BitArrayBooleanArray implements BitArray, Serializable{
	private static final long serialVersionUID = 1L;
	
	/* liste utilisée pour stocker en interne les bits */ 
	private boolean[] boolArray;
	
	/* Taille (nombre d'éléments) du boolArray */
	private int size = 0;
	public int size() {
		return size;
	}
	protected void incSize(){
		size++;
	}
	protected void decSize(){
		size--;
	}
	protected void setSize(int size) {
		this.size = size;
	}
	
	protected final int initialCapacity = 16;
	protected final int capacityIncrement = 8;
	
	/* Constante représentant l'endianness dans laquelle le BitArray travaille (BIG_ENDIAN uniquement pour le moment) */
	public enum Endianness { BIG_ENDIAN, LITTLE_ENDIAN };
	
	/* Index permettant de travailler en little ou big endian sur les bytes, 7 == big endian == lecture de gauche à droite */
	private final int endianShift = 7;
	
	/**
	 * Build an empty BitArray
	 */
	public BitArrayBooleanArray(){
		boolArray = new boolean[initialCapacity];
	}
	
	/**
	 * Build a BitArray from a Byte[]
	 * @param tb Byte Array from which bits will be read
	 * @throws ArrayTooShortException Thrown when tb contain less bits than nbBits
	 */
	public BitArrayBooleanArray(byte[] b){
		this(b,b.length*8);
	}
	
	/**
	 * Build a BitArray from a Byte[]
	 * @param tb Byte Array from which bits will be read
	 * @param nbBits Number of bits to be read
	 * @throws ArrayTooShortException Thrown when tb contain less bits than nbBits
	 */
	public BitArrayBooleanArray(byte[] b,int nbBits) throws ArrayTooShortException{
		this(b,nbBits,0);
	}
	
	/**
	 * Build a BitArray from a Byte[]
	 * @param tb Byte Array from which bits will be read
	 * @param nbBits Number of bits to be read
	 * @param bitOff bit offset from which we'll start reading
	 * @throws ArrayTooShortException Thrown when tb contain less bits than nbBits+bitOff
	 */
	public BitArrayBooleanArray(byte[] tb,int nbBits,int bitOff) throws ArrayTooShortException{
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
	public BitArrayBooleanArray(byte[] tb,int nbBits,int bitOff,int byteOff) throws ArrayTooShortException{
		this();
		
		if(tb.length*8 < (nbBits+bitOff)) throw new ArrayTooShortException();
		
		boolean res;
		int maxBit = byteOff*8+nbBits+bitOff;
		
		for(int i = (bitOff + byteOff*8); i < maxBit ; i++){
			res = (((tb[(i/8)] >> (endianShift - (i % 8))) & 1) == 1);
			add(res);
		}
	}
	
	public BitArrayBooleanArray(BitArrayBooleanArray ba){
		this();
		
		for(boolean b : ba)
			add(b);
	}
	
	public BitArray.Endianness getBitEndianness(){
		return (endianShift == 0)?BitArray.Endianness.LITTLE_ENDIAN:BitArray.Endianness.BIG_ENDIAN;
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
		if(!(ba instanceof BitArrayBooleanArray)) return false;
		BitArrayBooleanArray b = (BitArrayBooleanArray)ba;
		
		if(b.size() != size()) return false;
		
		for(int i = 0; i < size(); i++)
			if(get(i) != b.get(i)) return false;
		
		return true;
	}
	
	
	/*===== Implémentation de la représentation "Interne" d'un BitArray =====*/
	
	private class BitArrayIterator implements Iterator<Boolean>{
		
		private int index = 0;
		private BitArrayBooleanArray ba;
		
		public BitArrayIterator(BitArrayBooleanArray ba){
			this.ba = ba;
		}
		
		@Override
		public boolean hasNext() {
			return index < ba.size();
		}

		@Override
		public Boolean next() {
			return ba.get(index++);
		}

		@Override
		public void remove() {
			ba.remove(index);
		}
	}
	@Override
	public Iterator<Boolean> iterator() {
		return new BitArrayIterator(this);
	}
	
	public BitArrayBooleanArray add(boolean b){
		
		if(size() >= boolArray.length)
			boolArray = Arrays.copyOf(boolArray, boolArray.length + capacityIncrement);
			
		boolArray[size++] = b;

		return this;
	}
	
	public BitArray add(int index, boolean b){
		if(index < 0 || index > size())
			throw new ArrayIndexOutOfBoundsException();
		
		if(size() >= boolArray.length)
			boolArray = Arrays.copyOf(boolArray, boolArray.length + capacityIncrement);
		
		boolean oldB = b;
		for(int i = index; i < size();i++){
			oldB = boolArray[i];
			boolArray[i] = b;
			b = oldB;
		}
		boolArray[size++] = oldB;
		
		return this;
	}
	
	public boolean remove(int index){
		if(index < 0 || index >= size())
			throw new IndexOutOfBoundsException();
		
		boolean ret;
		
		ret = boolArray[index];
		
		for(int i = index+1; i < size(); i++)
			boolArray[i-1] = boolArray[i];
		
		decSize();
		
		return ret;
	}
	
	public boolean removeLast(){
		decSize();
		return boolArray[size()];
	}
	
	public void clear(){
		setSize(0);
	}
	
	public boolean get(int index){
		if(index < 0 || index >= size())
			throw new IndexOutOfBoundsException();
		
		return boolArray[index];
	}
	
	public void set(int index, boolean value){
		if(index < 0 || index >= size())
			throw new IndexOutOfBoundsException();
		
		boolArray[index] = value;
	}
	
	public void setLast(boolean value){
		boolArray[size()-1] = value;
	}
	
	public boolean isEmpty(){
		return size() == 0;
	}
	
	@Override
	public String toString(){
		String s = "";
		for(boolean b : this)
			s += (b)?"1":"0";
		return s;
	}
}
