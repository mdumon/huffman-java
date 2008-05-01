package bitutils.test;

/**
 * @author Franck Séhédic
 * 
 * TestUnit pour la classe BitArray
 */
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Arrays;

import org.junit.Test;

import bitutils.ArrayTooShortException;
import bitutils.BitArray;
import bitutils.BitArrayBooleanArray;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;


public class TestBitArrayBooleanArray {
	
	private byte[] testArray= { (byte)0xF0, 0x55 , (byte)0xF0, 0x55 };
	
	private BitArray newBitArray(){
		return new BitArrayBooleanArray();
	}
	
/*	
    private BitArray newBitArray(byte[] ba){
		return new BitArrayBooleanArray(ba);
	}
*/
	
	private BitArray newBitArray(byte[] ba,int nbBits){
		return new BitArrayBooleanArray(ba,nbBits);
	}
	
	private BitArray newBitArray(byte[] ba,int nbBits,int bitOff){
		return new BitArrayBooleanArray(ba,nbBits,bitOff);
	}
	
	private BitArray newBitArray(byte[] ba,int nbBits,int bitOff, int byteOff){
		return new BitArrayBooleanArray(ba,nbBits,bitOff,byteOff);
	}
	
	@Test
	public void testBitArray() {
		BitArray b = newBitArray();
		assertNotNull(b);
	}

	@Test(expected= ArrayTooShortException.class)
	public void testBitArrayByteArrayIntException() throws ArrayTooShortException {
		BitArray b = newBitArray(testArray,500);
		assertNotNull(b);
	}
	
	@Test
	public void testBitArrayByteArrayInt() throws ArrayTooShortException {
		BitArray b = newBitArray(testArray,testArray.length*8);
		assertArrayEquals(testArray, b.toByteArray());
	}

	@Test
	public void testBitArrayByteArrayIntInt() throws ArrayTooShortException {
		BitArray b = newBitArray(testArray,15,9);
		assertArrayEquals( new byte[]{(byte)0xAB,(byte) 0xE0}, b.toByteArray());
	}
	
	@Test
	public void testBitArrayByteArrayIntIntInt() throws ArrayTooShortException {
		BitArray b = newBitArray(testArray,15,1,1);
		assertArrayEquals( new byte[]{(byte)0xAB,(byte) 0xE0}, b.toByteArray());
	}
	
	@Test
	public void testToBooleanArray() throws ArrayTooShortException {
		BitArray b = newBitArray(testArray,16);
		assertTrue( Arrays.equals(new boolean[]{ true,  true,  true,  true,
												false, false, false, false,				   
												false, true, false,  true,
												false, true, false,  true  },b.toBooleanArray()));
	}
	
	@Test
	public void testToByteArray() throws ArrayTooShortException {
		BitArray b = newBitArray(testArray,testArray.length*8);
		assertArrayEquals(testArray, b.toByteArray());
	}
	
	@Test
	public void testremoveByteArray() throws ArrayTooShortException {
		BitArray b = newBitArray(testArray,testArray.length*8);
		assertArrayEquals(testArray, b.removeByteArray());
	}
	
	@Test
	public void testIterator() {
		BitArray ba = newBitArray(testArray,16);
		boolean[] boolTab = new boolean[]{ true,  true,  true,  true,
				false, false, false, false,				   
				false, true, false,  true,
				false, true, false,  true  };
		
		int i = 0;
		for(boolean b : ba){
			assertTrue(boolTab[i++] == b);
		}
	}

	@Test
	public void testAddBoolean() {
		BitArray ba = newBitArray();
		
		for(int i = 0; i < 4; i++){
			ba.add(false);
			ba.add(true);
		}
		
		assertTrue(ba.toByteArray()[0] == 0x55 );
	}

	@Test
	public void testAddIntBoolean() {
		BitArray ba = newBitArray();
		
		for(int i = 0; i < 4 ; i++)
			ba.add(false);
		ba.add(1,true);
		ba.add(3,true);
		ba.add(5,true);
		ba.add(7,true);
		
		assertArrayEquals(ba.toByteArray(),new byte[]{ 0x55 } );
	}
	
	@Test( expected=ArrayIndexOutOfBoundsException.class)
	public void testAddIntBooleanException() {
		BitArray ba = newBitArray();
		
		for(int i = 0; i < 4 ; i++)
			ba.add(false);
		ba.add(1,true);
		ba.add(3,true);
		ba.add(5,true);
		ba.add(7,true);
		
		ba.add(9,true);	// Exception
	}

	@Test
	public void testRemove() {
		BitArray ba = newBitArray(testArray,16);
		
		for(int i = 0; i < 8; i++)
			ba.remove(1);
		
		assertTrue(ba.toByteArray()[0] == (byte)0xD5 );
	}


	@Test
	public void testSizeClearIsEmpty() {
		BitArray ba = newBitArray(testArray,16);
		
		assertTrue(ba.size() == 16);
		ba.clear();
		assertTrue(ba.size() == 0);
		assertTrue(ba.isEmpty());
	}

	@Test
	public void testGetSet() {
		BitArray ba = newBitArray(testArray,16);
		
		assertTrue(ba.get(9));
		ba.set(9,false);
		assertTrue(!ba.get(9));
	}
	
	@Test
	public void testEquals() {
		BitArray ba = newBitArray(testArray,16);
		BitArray bb = newBitArray(testArray,16);
		
		assertTrue(ba != bb);
		assertTrue(ba.equals(bb));
	}
	
	@Test
	public void testSerialisation() {
		BitArray ba = newBitArray(testArray,18);
		BitArray bb = newBitArray(testArray,18);
		
		ByteOutputStream bos = new ByteOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream((OutputStream)bos);
			oos.writeObject(ba);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ba = null;
		
		ByteArrayInputStream bais = new ByteArrayInputStream(bos.getBytes());
		
		try {
			ObjectInputStream ois = new ObjectInputStream((InputStream)bais);
			ba = (BitArray)ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		assertTrue(ba.equals(bb));
	}
}
