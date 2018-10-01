package han.juser.dao;

public class DAOException extends RuntimeException 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public DAOException()
	{
		super();
	}
	
	
	public DAOException(String arg)
	{
		super(arg);
	}
	
	
	public DAOException(Throwable e)
	{
		super(e);
	}
	
	
	public DAOException(String arg, Throwable e)
	{
		super(arg, e);
	}

}
