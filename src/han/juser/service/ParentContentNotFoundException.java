package han.juser.service;

public class ParentContentNotFoundException extends ServiceException
{
	public ParentContentNotFoundException()
	{
		super("�θ���� �������� �ʽ��ϴ�.");
	}
	
	
	public ParentContentNotFoundException(String arg)
	{
		super(arg);
	}
	
	
	public ParentContentNotFoundException(Throwable e)
	{
		super(e);
	}
	
	
	public ParentContentNotFoundException(String arg, Throwable e)
	{
		super(arg, e);
	}
}
