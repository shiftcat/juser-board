package han.role;

import han.juser.controller.form.Member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RoleManagerImpl extends RoleManager
{
	
	public RoleManagerImpl()
	{
		super();
	}
	
	

	
	protected String getRequestUserCode(HttpServletRequest request) 
	{
		Member member = null;
		HttpSession session = request.getSession();
		synchronized(session) {
			member = (Member)session.getAttribute(Member.MEMBER_SESSION_KEY);
		}
		if(member == null) {
			return "0";
		}
		return String.valueOf(member.getLevel());
	}

	
	public String getRequestURI(HttpServletRequest request) 
	{
		String contextPath = request.getContextPath();
		String uri = request.getRequestURI();
		uri = uri.substring(contextPath.length());
		return uri;
	}

}
