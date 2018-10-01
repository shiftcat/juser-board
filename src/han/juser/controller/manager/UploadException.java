package han.juser.controller.manager;

public class UploadException extends RuntimeException 
{
	public UploadException()
	{
		super("파일 업로드 실패");
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
