package han.role;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 
 * @author Y.H Lee
 *
 */
public abstract class RoleManager 
{
	
	
	protected static Log log = LogFactory.getLog(RoleManager.class);
	
	protected RoleContext roleContext;
	
	public RoleManager()
	{
		
	}
	
	
	
	public void setRoleContext(RoleContext context)
	{
		this.roleContext = context;
	}
	
	
	
	private RoleConfig getRole(String code)
	{
		RoleConfig role = null;
		synchronized(roleContext) {
			UserConfig user = roleContext.getUser(code);
			if(user == null) {
				return null;
			}
			String roleName = user.getRole();
			role = roleContext.getRole(roleName);
			if(role == null) {
				log.warn("Role Name '" + roleName + "' 이(가) 존재하지 않아 익명사용자로 전환 합니다.");
				role = roleContext.getRole(RoleConfig.ROLE_ANONYMOUS);
			}
		}
		return role;
	}
	
	
	
	
	public boolean isAllow(HttpServletRequest request)
	{
		if(request == null) {
			return false;
		}
		
		String code = getRequestUserCode(request);
		String uri = getRequestURI(request);
		RoleConfig role = getRole(code);
		if(role == null) {
			log.warn("권한코드 '" + code + "'는(은) 존재하지 않은 코드번호 입니다.");
			return false;
		}
		Boolean b = role.getPermission(uri);
		if(b == null) {
			return role.getDefaultPermission();
		}
		return b;
	}
	
	
	
	
	
	public boolean isAdmin(HttpServletRequest request)
	{
		if(request == null) {
			return false;
		}
		String code = getRequestUserCode(request);
		String adminCode = getAdminCode();
		if(adminCode == null) {
			log.warn("관리자 코드가 존재하지 않습니다.");
			return false;
		}
		if(adminCode.equals(code)) {
			return true;
		}else {
			return false;
		}
	}
	
	
	
	
	
	public boolean compareCode(HttpServletRequest request, String code)
	{
		if(request == null || code == null || code.trim().equals("")) {
			return false;
		}
		
		UserConfig userConfig = null;
		synchronized(roleContext) {
			userConfig = roleContext.getUser(code.trim());
		}
		
		//존재하지 않은 유저 false
		if( userConfig == null){
			return false;
		}
		String userCode = getRequestUserCode(request);
		if(userCode == null){
			return false;
		}
		
		if(userCode.equals(code.trim())) {
			return true;
		}else {
			return false;
		}
	}
	
	
	
	
	public Map getUserConfig()
	{
		synchronized(roleContext) {
			return (Map)roleContext.getUserConfig();
		}
	}
	
	
	
	
	
	private String getAdminCode()
	{
		synchronized(roleContext) {
			return roleContext.getAdminUser().getCode();
		}
	}
	
	
	
	
	protected abstract String getRequestURI(HttpServletRequest request);
	
	
	
	
	protected abstract String getRequestUserCode(HttpServletRequest request);
	
	
}
