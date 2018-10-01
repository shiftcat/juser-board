package han.juser.controller.param;

import javax.servlet.http.HttpServletRequest;

public class JBoardParam 
{
	public static final String PAGE_NUMBER = "pno";
	
	public static final String ARTICLE_ID = "articleId";
	
	public static final String TARGET = "target";
	
	public static final String KEYWORD = "keyword";
	
	public static final String COMMENT_ID = "comment";
	
	
	
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
		String sid = req.getParameter(ARTICLE_ID);
		
		try {
			return Long.valueOf(sid);
		} catch (NumberFormatException e) {
			return -1;
		}
	}
	
	
	
	public static long getCommentId(HttpServletRequest req)
	{
		String str = req.getParameter(COMMENT_ID);
		
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
