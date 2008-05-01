package bitutils.test;

import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

import bitutils.BitArrayBooleanList;
import bitutils.BitOutputStream;

public class TestBitOutputStream{

	static final byte[] b = new byte[]{(byte)'0',(byte)'1','6','8','4','6','5','9'};
	BitArrayBooleanList bitArray = new BitArrayBooleanList(b);

	@Test
	public void testFlush() {
		BitOutputStream output;

		try{
			output = new BitOutputStream(new FileOutputStream("src/BitUtils/Test/testFileOut"));
			output.write(b);
			output.write(b[5]);
			output.writeBits(bitArray);
			output.flush();
			output.writeBits(bitArray, 2, 3);
			output.close();

		}catch(FileNotFoundException e3){
			System.out.println(e3.getMessage());	
			fail("File not found\n");

		}catch(IOException e1){
			System.out.println(e1.getMessage());
			fail("IOexception\n");
		}
	}

	@Test
	public void testBitOutputStream(){

		BitOutputStream output;

		try{
			output = new BitOutputStream(new FileOutputStream("src/BitUtils/Test/testFileOut"));
			output.close();

		}catch(FileNotFoundException e3){
			System.out.println(e3.getMessage());	
			fail("File not found\n");

		}catch(IOException e1){
			System.out.println(e1.getMessage());
			fail("IOexception\n");
		}
	}

	@Test
	public void testWriteBitsBitArray() {

		BitOutputStream output;

		try{
			output = new BitOutputStream(new FileOutputStream("src/BitUtils/Test/testFileOut"));
			output.writeBits(bitArray);
			output.close();

		}catch(FileNotFoundException e){
			System.out.println(e.getMessage());	
			fail("File not found\n");

		}catch(IOException e1){
			System.out.println(e1.getMessage());
			fail("IOexception\n");
		}
	}
	@Test
	public void testWriteBitsBitArrayIntInt() {
		
		BitOutputStream output;

		try{
			output = new BitOutputStream(new FileOutputStream("src/BitUtils/Test/testFileOut"));
			output.writeBits(bitArray, 8, 16);
			output.flush();
			output.close();

		}catch(FileNotFoundException e){
			System.out.println(e.getMessage());	
			fail("File not found\n");

		}catch(IOException e1){
			System.out.println(e1.getMessage());
			fail("IOexception\n");
		}
	}

}
