package han.juser.url;

import han.juser.controller.param.BlogParam;

import java.net.URLDecoder;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public class BlogURL extends URLManager 
{
	
	private static String MODULE_NAME = "/blog";
	
	public BlogURL()
	{
		super();
	}
	
	
	public BlogURL(HttpServletRequest req)
	{
		super(req);
	}
	

	
	protected String getModulName() 
	{	
		return MODULE_NAME;
	}

	
	public void parseParameter(ServletRequest req) 
	{
		if(super.request == null ) {
			super.request = req;
		}
		clear();
		if( req.getParameter(BlogParam.PAGE_NUMBER) != null ) {
			setParameter(BlogParam.PAGE_NUMBER, req.getParameter(BlogParam.PAGE_NUMBER).trim());
		}
		if( req.getParameter(BlogParam.CATEGORY_ID) != null ) {
			setParameter(BlogParam.CATEGORY_ID, req.getParameter(BlogParam.CATEGORY_ID).trim());
		}
		if( req.getParameter(BlogParam.TARGET) != null ) {
			setParameter(BlogParam.TARGET, req.getParameter(BlogParam.TARGET).trim());
		}
		if( req.getParameter(BlogParam.KEYWORD) != null ) {
			try {
				setParameter(BlogParam.KEYWORD, URLDecoder.decode(req.getParameter(BlogParam.KEYWORD).trim(), CHAR_SET) );
			} catch (Exception e) {
				removeParameter(BlogParam.TARGET);
				log.warn(e.getMessage(), e);
			}
		}
		if( req.getParameter(BlogParam.ARTICLE_ID) != null ) {
			setParameter(BlogParam.ARTICLE_ID, req.getParameter(BlogParam.ARTICLE_ID).trim());
		}
	}

}
