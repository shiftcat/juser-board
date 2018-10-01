package han.juser.service;

public class ContentNotFoundException extends ServiceException 
{
	public ContentNotFoundException()
	{
		super("게시물이 존재하지 않습니다.");
	}
	
	
	public ContentNotFoundException(String arg)
	{
		super(arg);
	}
	
	
	public ContentNotFoundException(Throwable e)
	{
		super(e);
	}
	
	
	public ContentNotFoundException(String arg, Throwable e)
	{
		super(arg, e);
	}
	

}
