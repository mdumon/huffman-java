package BitUtils;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A <code>BitInputStream</code> allows to work 
 * with any InputStream on a bit based basis.
 * 
 * @author Franck Séhédic
 * @version 1.0, 18/04/08
 * @see BitArray
 */
public class BitInputStream extends FilterInputStream{
	/* Index permettant de travailler en little ou big endian sur les bytes, 7 == big endian == lecture de gauche à droite */
	private final int endianShift = 7;
	
	/* Buffer interne permettant de gérer la lecture bits à bits */
	private byte byteBuf;
	
	/* Index désignant le bit actuel dans le buffer byteBuf */
	private int bbIndex = 0;
	
	/**
	 * Creates a <code>BitInputStream</code>
	 * and saves its argument, the input stream
	 * <code>in</code>, for later use.
	 * @param	in	the underlying input stream
	 */
	public BitInputStream(InputStream in) {
		super(in);
	}
	
	/**
	 * Reads the next Byte (8 Bits) of data from the underlying stream.
	 * @return	the next byte of data, or <code>-1</code>
	 * 			if the end of the stream is reached.
	 * @exception	IOException if an I/O error occurs.
	 */
	public synchronized int read() throws IOException{
		int bRead;
		byte[] buf = new byte[2];
		
		if( (bbIndex % 8) == 0)	return super.read();
		
		bRead = super.read();
		if(bRead == -1) return -1;
		buf[0] = byteBuf;
		buf[1] = (byte)bRead;
		
		byteBuf = buf[1];

		return new BitArray(buf,8,bbIndex).toByteArray()[0];
	}

	/**
	 * Reads the next Bit of data from the underlying stream.
	 * @return the next bit of data
	 * @throws IOException Thrown if something wrong happens when reading the stream.
	 */
	public synchronized boolean readBit() throws IOException{

		/* Si bbIndex % 8 == 0 c'est que notre byteBuffer a été lu en entier,
		 * on passe donc au byte suivant
		 */
		if((bbIndex % 8) == 0){
			int bRead = super.read();
			if(bRead == -1) throw new IOException("End of the underlying stream reached");
			
			byteBuf = (byte)bRead;
			bbIndex = 0;
		}
		
		/* On retourne le bit suivant (true si 1, false si 0)
		 * 2 solutions, soit on shift le mask vers la gauche et on le compare avec 0
		 *              soit on shift le byte vers la droite qu'on le mask avec 1 et qu'on compare avec 1
		 */
		return (byteBuf & (1 << (endianShift - bbIndex++))) != 0;
		// return ((byteBuf >> (endianShift - bbIndex++)) & 1) == 1;
	}
	
	/**
	 * Reads the next <code>nbBits</code> bits 
	 * of data from the underlying stream.
	 * 
	 * @param nbBits Number of bits to read from the underlying stream.
	 * @return next nbBits bits of data as a BitArray.
	 * @throws IOException Thrown if something wrong happen when reading the stream.
	 */
	public synchronized BitArray readBits(int nbBits) throws IOException{
		BitArray ba = new BitArray();
		
		while(nbBits-- > 0)
			ba.add(readBit());
		
		return ba;
	}
	
	private int markedBbIndex;
	private byte markedByteBuf;
	/**
	 * See 
	 * 	the general contract of the <code>mark</code> 
	 * 	method of <code>InputStream</code>.
	 */
	@Override
	public void mark(int readLimit){
		if(markSupported()){
			super.mark(readLimit);
			markedByteBuf = byteBuf;
			markedBbIndex = bbIndex;
		}
	}
	
	@Override
	/**
	 * See 
	 * 	the general contract of the <code>reset</code> 
	 * 	method of <code>InputStream</code>.
	 */
	public void reset() throws IOException{
		if(markSupported()){
			super.reset();
			byteBuf = markedByteBuf;
			bbIndex = markedBbIndex;
		}
	}
	
	@Override
	/**
	 * See 
	 * 	the general contract of the <code>skip</code> 
	 * 	method of <code>InputStream</code>.
	 */
	public long skip(long n) throws IOException{
		if(n <= 0) return 0;
		
		long m = super.skip(n-1);
		
		if(m != (n-1)){
			bbIndex = 0;
			return m;
		}
		
		byteBuf = (byte)super.read();
		return n;
	}
	
	/**
	 * Skips <code>n</code> Bits from the underlying input stream
	 * @param n Number of bits to skip
	 * @return number of bit skipped
	 * @throws IOException Thrown if something wrong happen when reading the stream.
	 */
	public long skipBits(long n) throws IOException{
		if(n <= 0) return 0;
		
		long i = n;
		while(i-- > 0){
			readBit();
		}
		
		return n;
	}
	
	/**
	 * Returns an estimate of the number of bits 
	 * that can be read (or skipped over) from this 
	 * input stream without blocking by the next 
	 * invocation of a method for this input stream.
	 * 
	 * @return an estimate of the number of bits that can be read (or skipped over) from this input stream without blocking. 
	 * @throws IOException
	 */
	public int availableBits() throws IOException{
		return this.available() + 8 - bbIndex;
	}
}

/*
BitInputStream file;
file = new BitOutputStream(new FileOuputStream("monfichier"));

ByteArray b;
file.readBit(b,9);
file.readBit(b,9);


file.writeBits(b);
file.writeBits(b);
*/