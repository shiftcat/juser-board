package han.role;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class UserConfig 
{
	private static Log log = LogFactory.getLog(UserConfig.class);
	
	private String code;
	
	private String role;
	
	public UserConfig()
	{
		
	}
	
	public void setCode(String code)
	{
		this.code = code;
	}
	
	public void setRole(String role)
	{
		this.role = role;
	}
	
	public String getCode()
	{
		return code;
	}
	
	public String getRole()
	{
		return role;
	}
	
	public void debug()
	{
		log.debug(code + " : " + role);
	}
}
