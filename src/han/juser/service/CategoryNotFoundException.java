package han.juser.service;

public class CategoryNotFoundException extends ServiceException 
{
	public CategoryNotFoundException()
	{
		super("�������� �ʴ� ī�װ� �Դϴ�.");
	}
	
	public CategoryNotFoundException(String arg)
	{
		super(arg);
	}
	
	
	public CategoryNotFoundException(Throwable e)
	{
		super(e);
	}
	
	
	public CategoryNotFoundException(String arg, Throwable e)
	{
		super(arg, e);
	}
	
}
