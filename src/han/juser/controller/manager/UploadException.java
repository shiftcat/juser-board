package han.juser.controller.manager;

public class UploadException extends RuntimeException 
{
	public UploadException()
	{
		super("���� ���ε� ����");
	}
	
	
	public UploadException(String arg)
	{
		super(arg);
	}
	
	
	public UploadException(Throwable e)
	{
		super(e);
	}
	
	public UploadException(String arg, Throwable e)
	{
		super(arg, e);
	}
	
}
