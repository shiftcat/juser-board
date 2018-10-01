package han.juser.service;

import han.juser.url.PageBean;

public interface ServiceBase
{
	public PageBean getPageBean();
	
	public void setLimit(int limit);
}
