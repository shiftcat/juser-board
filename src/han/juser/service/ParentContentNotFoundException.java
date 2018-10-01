package han.juser.service;

public class ParentContentNotFoundException extends ServiceException
{
	public ParentContentNotFoundException()
	{
		super("부모글이 존재하지 않습니다.");
	}
	
	
	public ParentContentNotFoundException(String arg)
	{
		super(arg);
	}
	
	
	public ParentContentNotFoundException(Throwable e)
	{
		super(e);
	}
	
	
	public ParentContentNotFoundException(String arg, Throwable e)
	{
		super(arg, e);
	}
}
