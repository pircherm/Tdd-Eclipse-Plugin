package at.ac.tuwien.ifs.qse.tdd.exception;

/**
 * This Exception will be thrown every time an error occurs in the TestFinder class
 */
public class SearchException extends Exception {
	
  /**
   * 
   */
  private static final long serialVersionUID = 5613361601981781048L;

  public SearchException(String message) {
    super(message);
  }

}
