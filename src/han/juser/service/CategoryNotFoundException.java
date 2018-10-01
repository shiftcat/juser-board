package han.juser.service;

public class CategoryNotFoundException extends ServiceException 
{
	public CategoryNotFoundException()
	{
		super("존재하지 않는 카테고리 입니다.");
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
