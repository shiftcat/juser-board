package han.juser.service;

public class MemberNotFoundException extends ServiceException 
{
	public MemberNotFoundException()
	{
		super("존재 하지 않는 사용자 입니다.");
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
