package at.ac.tuwien.ifs.qse.tdd.exception;

/**
 * Class who has to handle SearchExceptions can implement this interface
 * 
 */
public interface IHandleException {
	/**
	 * Handle the Exception 
	 * @param exc thrown exception
	 * @param fileName search filename
	 */
	public void handleException(SearchException exc,String fileName);

}
