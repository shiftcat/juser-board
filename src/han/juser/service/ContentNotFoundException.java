package han.juser.service;

public class ContentNotFoundException extends ServiceException 
{
	public ContentNotFoundException()
	{
		super("�Խù��� �������� �ʽ��ϴ�.");
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
