package han.juser.controller.manager;

import han.juser.service.MemberService;
import han.juser.service.ServiceHelper;

import javax.servlet.http.HttpServletRequest;

public class MemberBase 
{

	public MemberBase() 
	{
		super();
	}
	
	
	protected MemberService getService(HttpServletRequest req)
	{
		return ServiceHelper.getMemberService(req.getSession().getServletContext());
	}
	
	
}