package Algo;

public class FakeHuffmaneur extends Huffmaneur {

	@Override
	protected void huffmaner() {
		
		for (int i=0; i<=100; i++){
			try {
				Thread.sleep(180);
			} catch (InterruptedException e){};
			
			pauseIfIHaveTo();
			if(isCanceled()) break;
			
			incAdvance();
		}

	}

}
