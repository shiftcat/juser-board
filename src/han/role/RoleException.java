package han.role;

public class RoleException extends RuntimeException 
{
	
	public RoleException(String message)
	{
		super(message);
	}
	
	public RoleException(String message, Throwable e)
	{
		super(message, e);
	}
	
	
}
