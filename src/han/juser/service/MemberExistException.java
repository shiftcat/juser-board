package han.juser.service;

public class MemberExistException extends ServiceException 
{
	public MemberExistException()
	{
		super("이미 존재하는 사용자 입니다.");
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
