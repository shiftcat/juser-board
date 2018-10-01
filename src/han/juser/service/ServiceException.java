package han.juser.service;

public class ServiceException extends Exception
{
	public ServiceException()
	{
		super("ServiceException");
	}
	
	
	public ServiceException(String arg)
	{
		super(arg);
	}
	
	
	public ServiceException(Throwable e)
	{
		super(e);
	}
	
	
	public ServiceException(String arg, Throwable e)
	{
		super(arg, e);
	}

}
