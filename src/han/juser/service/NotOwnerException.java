package han.juser.service;

public class NotOwnerException extends ServiceException
{
	public NotOwnerException()
	{
		super("�ش� �Խù��� �����ڰ� �ƴմϴ�.");
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
