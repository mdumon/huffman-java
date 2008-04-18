package BitUtils.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

import BitUtils.BitArray;
import BitUtils.BitInputStream;

public class TestBitInputStream {
	
	static final boolean[] testBoolTab = new boolean[]{
		false,false, true, true,false,false,false, true,	// '1'
		false,false, true, true,false,false, true,false,	// '2'
		false,false, true, true,false,false, true, true,	// '3'
		false,false, true, true,false, true,false,false,	// '4'
		false,false, true, true,false, true,false, true		// '5'
	};
	
	@Test
	public void testReadSkipSkipBits() {
		BitInputStream bis;
		byte b;
		
		try{
			bis = new BitInputStream(new FileInputStream("src/BitUtils/Test/testFile"));
		}catch(FileNotFoundException fnfe1){
			System.out.println(fnfe1.getMessage());
			return;
		}
		
		try{
			b = (byte)bis.read();
			assertTrue(b == (byte)'1');
		
			bis.skip(1);
			
			b = (byte)bis.read();
			assertTrue(b == (byte)'3');
			
			bis.skipBits(8);
			
			b = (byte)bis.read();
			assertTrue(b == (byte)'5');
			
			bis.close();
		}catch(IOException ioe1){
			System.out.println(ioe1.getMessage());
			fail("IOException :-/");
		}
	}

	@Ignore("Not yet implemented")
	@Test
	public void testMark() {
		fail("Not yet implemented");
	}


	@Ignore("Not yet implemented")
	@Test
	public void testReset() {
		fail("Not yet implemented");
	}

	@Test
	public void testReadBit() {
		BitInputStream bis;
		
		try{
			bis = new BitInputStream(new FileInputStream("src/BitUtils/Test/testFile"));
		}catch(FileNotFoundException fnfe1){
			System.out.println(fnfe1.getMessage());
			return;
		}
		
		try{
			for(boolean b : testBoolTab)
				assertTrue(b == bis.readBit());
			
			bis.close();
		}catch(IOException ioe1){
			System.out.println(ioe1.getMessage());
			fail("IOException :-/");
		}
	}

	@Test
	public void testReadBits() {
		BitInputStream bis;
		BitArray ba = new BitArray();
		
		try{
			bis = new BitInputStream(new FileInputStream("src/BitUtils/Test/testFile"));
		}catch(FileNotFoundException fnfe1){
			System.out.println(fnfe1.getMessage());
			return;
		}
		
		try{
			int nbBits;
			for(int i = 0; i < testBoolTab.length;){
				
				nbBits = bis.availableBits();
				ba = bis.readBits( (nbBits >= 9)?9:nbBits );
				
				for(boolean baBit: ba)
						assertTrue(testBoolTab[i++] == baBit);
			}
			
			bis.close();
		}catch(IOException ioe1){
			System.out.println(ioe1.getMessage());
			fail("IOException :-/");
		}
	}

}
