package han.role;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.xml.sax.SAXException;

public class RoleContext
{
	
	private static Log log = LogFactory.getLog(RoleContext.class);
	
	public static final String ROLE_CONFIG_CONTEXT_KEY = "han.role.RoleConfig";
	
	private Map role = new Hashtable();
	
	private Map user = new Hashtable();
	
	public RoleContext()
	{
		super();
		init();
	}
	
	
	private void init()
	{
		RoleConfig admin = new RoleConfig();
		admin.setName(RoleConfig.ROLE_ADMIN);
		admin.setDefaultPermission(true);
		RoleConfig anonymous = new RoleConfig();
		anonymous.setName(RoleConfig.ROLE_ANONYMOUS);
		anonymous.setDefaultPermission(false);
		role.put(admin.getName(), admin);
		role.put(anonymous.getName(), anonymous);
	}
	
	
	public void setSource(Resource source) throws SAXException, IOException
	{
		log.debug(source.getFilename());
		File input = source.getFile();
		Digester digester = new Digester();
	    digester.push(this);
		addRules(digester);
		digester.parse(input);
	}
	
	private void addRules(Digester d)
	{
		d.addObjectCreate("role-config/user", UserConfig.class);
		d.addSetProperties("role-config/user");
		d.addSetNext("role-config/user", "addUser");
		
		d.addObjectCreate("role-config/role", RoleConfig.class);
		d.addSetProperties("role-config/role");
		d.addSetNext("role-config/role", "addRole");
		d.addCallMethod("role-config/role/url", "addPermission", 2,  new Class[]{String.class, Boolean.class});
		d.addCallParam("role-config/role/url", 0, "path");
		d.addCallParam("role-config/role/url", 1, "allow");
	}
	
	public void addRole(RoleConfig role)
	{
		if(role.getExtends() != null) {
			String[] ex = role.getExtends().split(",");
			RoleConfig[] rc = new RoleConfig[ex.length];
			for(int i = 0; i < ex.length; i++) {
				rc[i] = (RoleConfig)this.role.get(ex[i].trim());
			}
			role.roleComposite(rc);
		}
		this.role.put(role.getName(), role);
	}

	public RoleConfig getRole(String name)
	{
		return (RoleConfig)role.get(name);
	}
	
	public Map getUserConfig()
	{
		return new Hashtable(this.user);
	}
	
	public void addUser(UserConfig user)
	{
		this.user.put(user.getCode(), user);
	}
	
	public UserConfig getUser(String code)
	{
		return (UserConfig)user.get(code);
	}
	
	public void release()
	{
		this.user.clear();
		this.role.clear();
	}
	
	public synchronized UserConfig getAdminUser()
	{
		for(Iterator i = user.keySet().iterator(); i.hasNext(); ) {
			String key = (String)i.next();
			UserConfig value = (UserConfig)user.get(key);
			if(RoleConfig.ROLE_ADMIN.equals(value.getRole())) {
				return value;
			}
		}
		if(log.isDebugEnabled()) {
			log.debug("관리자 유저가 없습니다.");
		}
		return null;
	}
	
	public void debug()
	{
		for(Iterator i = role.keySet().iterator(); i.hasNext(); ) {
			String name = (String)i.next();
			RoleConfig r = (RoleConfig)role.get(name);
			r.debug();
		}
		for(Iterator i = user.keySet().iterator(); i.hasNext(); ) {
			String code = (String)i.next();
			UserConfig u = (UserConfig)user.get(code);
			u.debug();
		}
	}
};