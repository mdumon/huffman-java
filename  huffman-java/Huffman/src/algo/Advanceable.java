package algo;


/**
 * <code>Advanceable</code> used to work with
 * a progress bar
 * 
 * @author Franck Séhédic <sehedic@ecole.ensicaen.fr>
 */
public interface Advanceable {
	
	/**
	 * set the state of the progress bar to a particular advancement
	 * @param the percentage of advancement of the progress bar
	 * @return void
	 */
	public void setAdvance(int advance);
	
	/**
	 * increment the state of the progress bar of one
	 * percent
	 * @param void
	 * @return void
	 */
	public void incAdvance();
	
	/**
	 * return the state of the progress bar
	 * @param void
	 * @return the state of the progress bar in percentage
	 */
	public int getAdvance();
}
