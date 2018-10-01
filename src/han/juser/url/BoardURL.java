package han.juser.url;

import han.juser.controller.param.BoardParam;

import java.net.URLDecoder;

import javax.servlet.ServletRequest;

public class BoardURL extends URLManager
{

	private static final String MODUL_NAME = "/board";
	
	public BoardURL()
	{
		super();
	}
	
	
	public BoardURL(ServletRequest req)
	{
		super(req);
	}
	
	
	public void parseParameter(ServletRequest req)
	{
		if(super.request == null ) {
			super.request = req;
		}
		
		clear();
		if( req.getParameter(BoardParam.PAGE_NUMBER) != null ) {
			setParameter(BoardParam.PAGE_NUMBER, req.getParameter(BoardParam.PAGE_NUMBER).trim());
		}
		if( req.getParameter(BoardParam.TARGET) != null ) {
			setParameter(BoardParam.TARGET, req.getParameter(BoardParam.TARGET).trim());
		}
		if( req.getParameter(BoardParam.KEYWORD) != null ) {
			try {
				setParameter(BoardParam.KEYWORD, URLDecoder.decode(req.getParameter(BoardParam.KEYWORD).trim(), CHAR_SET) );
			} catch (Exception e) {
				removeParameter(BoardParam.TARGET);
				log.warn(e.getMessage(), e);
			}
		}
		if( req.getParameter(BoardParam.CONTENT_ID) != null ) {
			setParameter(BoardParam.CONTENT_ID, req.getParameter(BoardParam.CONTENT_ID).trim());
		}
	}
	
	

	
	protected String getModulName() 
	{	
		return MODUL_NAME;
	}



	
	

}
