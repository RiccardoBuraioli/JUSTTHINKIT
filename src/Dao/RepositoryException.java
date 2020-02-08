package Dao;

import java.sql.SQLException;

public  class RepositoryException extends RuntimeException{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public RepositoryException(String message) {
        super(message);
    }
    public RepositoryException(String message, SQLException cause) {
        super(message, cause);
    }
}
