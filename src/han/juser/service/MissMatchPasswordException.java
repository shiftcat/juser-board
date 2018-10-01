package han.juser.service;

public class MissMatchPasswordException extends ServiceException
{
	public MissMatchPasswordException()
	{
		super("패스워드가 틀립니다.");
	}
	
	public MissMatchPasswordException(String arg)
	{
		super(arg);
	}
	
	public MissMatchPasswordException(Throwable e)
	{
		super(e);
	}
	
	
	public MissMatchPasswordException(String arg, Throwable e)
	{
		super(arg, e);
	}
	

}
