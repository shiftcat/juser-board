package han.juser.controller.param;

import javax.servlet.http.HttpServletRequest;

public class GuestParam 
{
	public static final String PAGE_NUMBER = "pno";
	
	public static final String CONTENT_ID = "gid";
	
	
	public static int getPageNumber(HttpServletRequest req)
	{
		String sp = req.getParameter(PAGE_NUMBER);
		try {
			return Integer.valueOf(sp);
		} catch (NumberFormatException e) {
			return 1;
		}
	}
	
	
	public static long getContentId(HttpServletRequest req)
	{
		String sid = req.getParameter(CONTENT_ID);
		
		try {
			return Long.valueOf(sid);
		} catch (NumberFormatException e) {
			return -1;
		}
	}
	
}
