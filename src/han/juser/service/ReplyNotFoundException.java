package han.juser.service;

public class ReplyNotFoundException extends ServiceException
{
	public ReplyNotFoundException()
	{
		super("존재하지 않는 뎃글 입니다.");
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
