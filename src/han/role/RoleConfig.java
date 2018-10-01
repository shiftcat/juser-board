package han.role;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class RoleConfig
{
	private static Log log = LogFactory.getLog(RoleConfig.class);
	
	public static final String ROLE_ADMIN = "admin";
	
	public static final String ROLE_ANONYMOUS = "anonymous";
	
	private String name;
	
	private String extend;
	
	private boolean defaultPermission = false;

	private Map permission = new HashMap();

	public RoleConfig()
	{
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setExtends(String extend)
	{
		this.extend = extend;
	}
	
	public void setDefaultPermission(boolean permission)
	{
		this.defaultPermission = permission;
	}
	
	public void setPermissions(Map map)
	{
		permission.putAll(map);
	}
	
	public Map getPermissions()
	{
		return new HashMap(permission);
	}
	
	public void addPermission(String path, Boolean value)
	{
		this.permission.put(path, value);
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getExtends()
	{
		return this.extend;
	}
	
	public boolean getDefaultPermission()
	{
		return defaultPermission;
	}
	
	public Boolean getPermission(String path)
	{
		return (Boolean)permission.get(path);
	}
	
	public void roleComposite(RoleConfig[] role)
	{
		if(role == null) {
			return;
		}
		
		Map map = new HashMap(permission);
		permission.clear();
		
		for(int i = 0; i < role.length; i++) {
			if(role[i] == null || role[i].getPermissions().isEmpty()) {
				continue;
			}
			permission.putAll(role[i].getPermissions());			
		}
		
		permission.putAll(map);
	}
	
	public void debug()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(" ********** Role " + name + "**************** \n");
		sb.append("Default Permisssion : " + defaultPermission + "\n");
		for(Iterator i = permission.keySet().iterator(); i.hasNext();) {
			String name = (String)i.next();
			Boolean value = (Boolean)permission.get(name);
			sb.append("Permission " + name + " = " + value + "\n");
		}
		log.debug(sb.toString());
	}
};