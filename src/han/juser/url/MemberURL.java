package han.juser.url;

import han.juser.controller.param.MemberParam;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.ServletRequest;

public class MemberURL extends URLManager 
{
	
	private static final String MODUL_NAME = "/admin";
	
	public MemberURL()
	{
		super();
	}
	
	
	public MemberURL(ServletRequest req)
	{
		super(req);
	}
	

	
	public void parseParameter(ServletRequest req) 
	{
		if(super.request == null ) {
			super.request = req;
		}
		super.clear();
		if( req.getParameter(MemberParam.PAGE_NUMBER) != null ) {
			setParameter(MemberParam.PAGE_NUMBER, req.getParameter(MemberParam.PAGE_NUMBER).trim());
		}
		if(req.getParameter(MemberParam.USER_ID) != null) {
			try {
				setParameter(MemberParam.USER_ID, URLDecoder.decode(req.getParameter(MemberParam.USER_ID).trim(), CHAR_SET) );
			} catch (UnsupportedEncodingException e) {}
		}
	}


	
	protected String getModulName() 
	{	
		return MODUL_NAME;
	}


}
