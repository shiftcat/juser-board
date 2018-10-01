package han.juser.url;

import han.juser.controller.param.GuestParam;

import javax.servlet.ServletRequest;


public class GuestURL extends URLManager 
{
	
	private static final String MODUL_NAME = "/guest";
	
	
	
	public GuestURL()
	{
		super();
	}
	
	
	public GuestURL(ServletRequest req) 
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
		super.clear();
		if( req.getParameter(GuestParam.PAGE_NUMBER) != null ) {
			setParameter(GuestParam.PAGE_NUMBER, req.getParameter(GuestParam.PAGE_NUMBER).trim());
		}
	}



}
