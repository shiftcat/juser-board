package han.juser.service;

public class ReplyNotFoundException extends ServiceException
{
	public ReplyNotFoundException()
	{
		super("�������� �ʴ� ���� �Դϴ�.");
	}
	
	
	public ReplyNotFoundException(String arg)
	{
		super(arg);
	}
	
	
	public ReplyNotFoundException(Throwable e)
	{
		super(e);
	}
	
	
	public ReplyNotFoundException(String arg, Throwable e)
	{
		super(arg, e);
	}

}
