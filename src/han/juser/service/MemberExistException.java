package han.juser.service;

public class MemberExistException extends ServiceException 
{
	public MemberExistException()
	{
		super("�̹� �����ϴ� ����� �Դϴ�.");
	}
	
	public MemberExistException(String arg, Throwable e)
	{
		super(arg, e);
	}
	
	
	public MemberExistException(String arg)
	{
		super(arg);
	}
	
	
	public MemberExistException(Throwable e)
	{
		super(e);
	}
	
}
