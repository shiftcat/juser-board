package han.juser.service;

public class MissMatchPasswordException extends ServiceException
{
	public MissMatchPasswordException()
	{
		super("�н����尡 Ʋ���ϴ�.");
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
