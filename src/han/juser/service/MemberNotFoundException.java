package han.juser.service;

public class MemberNotFoundException extends ServiceException 
{
	public MemberNotFoundException()
	{
		super("���� ���� �ʴ� ����� �Դϴ�.");
	}
	
	
	public MemberNotFoundException(String arg)
	{
		super(arg);
	}
	
	
	public MemberNotFoundException(Throwable e) 
	{
		super(e);
	}
	
	
	public MemberNotFoundException(String arg, Throwable e)
	{
		super(arg, e);
	}

}
