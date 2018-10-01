package han.juser.controller.param;

import javax.servlet.http.HttpServletRequest;

public class BoardParam 
{
	public static final String PAGE_NUMBER = "pno";
	
	public static final String CONTENT_ID = "bbsId";
	
	public static final String TARGET = "target";
	
	public static final String KEYWORD = "keyword";
	
	public static final String REPLY_ID = "replId";
	
	
	
	public static int getPageNumber(HttpServletRequest req)
	{
		String sp = req.getParameter(PAGE_NUMBER);
		if(sp == null) {
			return 1;
		}
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
	
	
	public static long getReplyId(HttpServletRequest req)
	{
		String str = req.getParameter(REPLY_ID);
		
		try {
			return Long.valueOf(str);
		} catch (NumberFormatException e) {
			return -1;
		}
	}
	
	
	public static String getTarget(HttpServletRequest req)
	{
		String target = req.getParameter(TARGET);
		if( target == null || target.equals("")) {
			return "";
		}else {
			return target;
		}
	}
	
	
	public static String getKeyword(HttpServletRequest req)
	{
		String keyword = req.getParameter(KEYWORD);
		if( keyword == null ) {
			return "";
		} else {
			return keyword;
		}
	}
	
	
}
