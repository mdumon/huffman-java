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

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;
import java.util.Iterator;

public class BitArrayByteArray implements BitArray, Externalizable{
	private static final long serialVersionUID = 1L;
	
	/* liste utilisée pour stocker en interne les bits */ 
	private byte[] byteArray;
	
	/* Taille (nombre d'éléments) du byteArray */
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
	
	protected final int initialCapacity = 2;
	protected final int capacityIncrement = 1;
	
	/* Constante représentant l'endianness dans laquelle le BitArray travaille (BIG_ENDIAN uniquement pour le moment) */
	public enum Endianness { BIG_ENDIAN, LITTLE_ENDIAN };
	
	/* Index permettant de travailler en little ou big endian sur les bytes, 7 == big endian == lecture de gauche à droite */
	private final int endianShift = 7;
	
	/**
	 * Build an empty BitArray
	 */
	public BitArrayByteArray(){
		byteArray = new byte[initialCapacity];
	}
	
	/**
	 * Build a BitArray from a Byte[]
	 * @param tb Byte Array from which bits will be read
	 * @throws ArrayTooShortException Thrown when tb contain less bits than nbBits
	 */
	public BitArrayByteArray(byte[] b){
		this(b,b.length*8);
	}
	
	/**
	 * Build a BitArray from a Byte[]
	 * @param tb Byte Array from which bits will be read
	 * @param nbBits Number of bits to be read
	 * @throws ArrayTooShortException Thrown when tb contain less bits than nbBits
	 */
	public BitArrayByteArray(byte[] b,int nbBits) throws ArrayTooShortException{
		this(b,nbBits,0);
	}
	
	/**
	 * Build a BitArray from a Byte[]
	 * @param tb Byte Array from which bits will be read
	 * @param nbBits Number of bits to be read
	 * @param bitOff bit offset from which we'll start reading
	 * @throws ArrayTooShortException Thrown when tb contain less bits than nbBits+bitOff
	 */
	public BitArrayByteArray(byte[] tb,int nbBits,int bitOff) throws ArrayTooShortException{
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
	public BitArrayByteArray(byte[] tb,int nbBits,int bitOff,int byteOff) throws ArrayTooShortException{
		this();
		
		if(tb.length*8 < (nbBits+bitOff)) throw new ArrayTooShortException();
		
		boolean res;
		int maxBit = byteOff*8+nbBits+bitOff;
		
		for(int i = (bitOff + byteOff*8); i < maxBit ; i++){
			res = (((tb[(i/8)] >> (endianShift - (i % 8))) & 1) == 1);
			add(res);
		}
	}
	
	public BitArrayByteArray(BitArrayBooleanArray ba){
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
	
	public byte removeByteArray2(){
		if(size() == 0) return 0;
		
		byte tb = 0;
		int bit;
		
		for(int i = 0; size() > 0; i++){
			bit = (remove(0)?1:0);
			tb |= (bit << (endianShift - (i%8)));
		}
		
		return tb;
	}
	
	@Override
	public boolean equals(Object ba){
		if(!(ba instanceof BitArrayByteArray)) return false;

		return Arrays.equals(byteArray, ((BitArrayByteArray)ba).byteArray);
	}
	
	
	/*===== Implémentation de la représentation "Interne" d'un BitArray =====*/
	
	private class BitArrayIterator implements Iterator<Boolean>{
		
		private int index = 0;
		private BitArrayByteArray ba;
		
		public BitArrayIterator(BitArrayByteArray ba){
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
	
	public BitArrayByteArray add(boolean b){
		
		if(size() >= byteArray.length*8)
			byteArray = Arrays.copyOf(byteArray, byteArray.length + capacityIncrement);
			
		if(b)
			byteArray[size/8] |=  (1 << (endianShift - (size%8)));

		return this;
	}
	
	public BitArray add(int index, boolean b){
		if(index < 0 || index > size())
			throw new ArrayIndexOutOfBoundsException();
		
		if(size() >= byteArray.length*8)
			byteArray = Arrays.copyOf(byteArray, byteArray.length + capacityIncrement);
		
		boolean oldB = b;
		for(int i = index; i < size();i++){
			oldB = ((byteArray[i/8] >> (i%8)) & 1) == 1;
			if(b) 
				byteArray[i/8] |= (1 << (endianShift - (i%8)));
			b = oldB;
		}
		if(oldB) byteArray[(size++/8)] |= (1 << (endianShift - (size%8)));
		
		return this;
	}
	
	/* TODO : finir l'implémentation à partir d'ici */
	public boolean remove(int index){
		if(index < 0 || index >= size())
			throw new IndexOutOfBoundsException();
		
		boolean ret;
		
		ret = ((byteArray[index/8] >> (index%8)) & 1) == 1;
		/*
		for(int i = index+1; i < size(); i++){
			if(((byteArray[i/8] >> (i%8)) & 1) == 1)
				byteArray[(i-1)/8] |= (1 << (endianShift - ((i-1)%8)));
			else
				byteArray[(i-1)/8] &= (1 << (endianShift - ((i-1)%8))) == ;
		}
		*/
		decSize();
		
		return ret;
	}
	
	public boolean removeLast(){
		decSize();
		return byteArray[size()] == 1;
	}
	
	public void clear(){
		setSize(0);
	}
	
	public boolean get(int index){
		if(index < 0 || index >= size())
			throw new IndexOutOfBoundsException();
		
		return byteArray[index] == 1;
	}
	
	public void set(int index, boolean value){
		if(index < 0 || index >= size())
			throw new IndexOutOfBoundsException();
		
		byteArray[index] = (byte)((value)?1:0);
	}
	
	public void setLast(boolean value){
		byteArray[size()-1] = (byte)((value)?1:0);;
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
	
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeByte(size());
		if(size() == 0) return;
		
		byte[] tb = toByteArray();
		for(int i = 0; i < tb.length; i++)
			out.writeByte(tb[i]);
	}
	

	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		/*this.size = in.readByte();
		if(size() == 0) return;
		
		BitInputStream bis = new BitInputStream((InputStream)in);
		this.byteArray = bis.readBits(size()).toBooleanArray(); */
		return;
	}
}
