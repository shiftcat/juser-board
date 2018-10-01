package han.juser.controller.param;

import javax.servlet.http.HttpServletRequest;

public class MemberParam 
{
	
	public static final String PAGE_NUMBER = "pno";
	
	public static final String USER_ID = "userId";
	
	public static final String ACTION_MODE = "mode";
	
	public static int getPageNumber(HttpServletRequest req)
	{
		String str = req.getParameter(PAGE_NUMBER);
		
		try {
			return Integer.valueOf(str);
		} catch (NumberFormatException e) {
			return 1;
		}
	}
	
	
	public static String getUserId(HttpServletRequest req)
	{
		String str = req.getParameter(USER_ID);
		if( str == null || str.equals("")) {
			return "";
		}
		return str;
	}
	
	
	public static String getActionMode(HttpServletRequest req)
	{
		String str = req.getParameter(ACTION_MODE);
		if( str == null || str.equals("")) {
			return "";
		}else if( !str.equalsIgnoreCase("modify") && !str.equalsIgnoreCase("secede") ) {
			return "";
		}
		
		return str;
	}
	
	
}
