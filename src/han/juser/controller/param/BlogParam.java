package han.juser.controller.param;

import javax.servlet.http.HttpServletRequest;

public class BlogParam 
{
	
	public static final String CATEGORY_ID = "cate";
	
	public static final String PAGE_NUMBER = "pno";
	
	public static final String ARTICLE_ID = "articleId";
	
	public static final String TARGET = "target";
	
	public static final String KEYWORD = "keyword";

	
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
		String sid = req.getParameter(ARTICLE_ID);
		
		try {
			return Long.valueOf(sid);
		} catch (NumberFormatException e) {
			return -1;
		}
	}
	
	
	public static int getCategoryId(HttpServletRequest req)
	{
		String cate = req.getParameter(CATEGORY_ID);
		if( cate == null ) {
			return -1;
		}
		try {
			return Integer.valueOf(cate);
		} catch(NumberFormatException e) {
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
