package BitUtils.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

import BitUtils.BitInputStream;

public class TestBitInputStream {

	@Test
	public void testRead() {
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
		}catch(IOException ioe1){
			System.out.println(ioe1.getMessage());
			return;
		}
		
		assertTrue(b == (byte)'1');
	}

	@Ignore("Not yet implemented")
	@Test
	public void testSkip() {
		fail("Not yet implemented");
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


	@Ignore("Not yet implemented")
	@Test
	public void testBitInputStream() {
		fail("Not yet implemented");
	}


	@Ignore("Not yet implemented")
	@Test
	public void testReadBit() {
		fail("Not yet implemented");
	}


	@Ignore("Not yet implemented")
	@Test
	public void testReadBits() {
		fail("Not yet implemented");
	}

}
