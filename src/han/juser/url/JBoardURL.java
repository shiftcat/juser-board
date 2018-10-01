package han.juser.url;

import han.juser.controller.param.JBoardParam;

import java.net.URLDecoder;

import javax.servlet.ServletRequest;

public class JBoardURL extends URLManager
{

	private static final String MODUL_NAME = "/jboard";
	
	
	public JBoardURL()
	{
		super();
	}
	
	
	public JBoardURL(ServletRequest req)
	{
		super(req);
	}
	

	
	protected String getModulName() 
	{	
		return MODUL_NAME;
	}

	
	public void parseParameter(ServletRequest req)
	{
		if(super.request == null ) {
			super.request = req;
		}
		clear();
		if( req.getParameter(JBoardParam.PAGE_NUMBER) != null ) {
			setParameter(JBoardParam.PAGE_NUMBER, req.getParameter(JBoardParam.PAGE_NUMBER).trim());
		}
		if( req.getParameter(JBoardParam.TARGET) != null ) {
			setParameter(JBoardParam.TARGET, req.getParameter(JBoardParam.TARGET).trim());
		}
		if( req.getParameter(JBoardParam.KEYWORD) != null ) {
			try {
				setParameter(JBoardParam.KEYWORD, URLDecoder.decode(req.getParameter(JBoardParam.KEYWORD).trim(), CHAR_SET) );
			} catch (Exception e) {
				removeParameter(JBoardParam.TARGET);
				log.warn(e.getMessage(), e);
			}
		}
		if( req.getParameter(JBoardParam.ARTICLE_ID) != null ) {
			setParameter(JBoardParam.ARTICLE_ID, req.getParameter(JBoardParam.ARTICLE_ID).trim());
		}
		
	}


	
}
