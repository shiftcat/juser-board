package han.juser.service;

public class NotOwnerException extends ServiceException
{
	public NotOwnerException()
	{
		super("해당 게시물의 소유자가 아닙니다.");
	}
	
	public NotOwnerException(String arg)
	{
		super(arg);
	}
	
	
	public NotOwnerException(Throwable e)
	{
		super(e);
	}
	
	
	public NotOwnerException(String arg, Throwable e)
	{
		super(arg, e);
	}

}
