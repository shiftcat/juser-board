package han.juser.service.imple;

import han.juser.dao.VisitorDAO;
import han.juser.service.VisitorService;


public class VisitorServiceImple implements VisitorService
{
	private VisitorDAO dao;
	
	public VisitorServiceImple()
	{
		super();
	}
	
	
	/* (non-Javadoc)
	 * @see han.juser.service.VisitorService#setVisitorDAO(han.juser.dao.VisitorDAO)
	 */
	public void setVisitorDAO(VisitorDAO dao)
	{
		this.dao = dao;
	}
	
	
	/* (non-Javadoc)
	 * @see han.juser.service.VisitorService#insert(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public long insert(String userAgent, String remoteAddr, String remoteHost, String referer)
	{
		return dao.insert(userAgent, remoteAddr, remoteHost, referer);
	}
	
	
	/* (non-Javadoc)
	 * @see han.juser.service.VisitorService#getVisitCount()
	 */
	public long getVisitCount()
	{
		return dao.getRowCount();
	}
	
	
	
	/* (non-Javadoc)
	 * @see han.juser.service.VisitorService#getTodayCount()
	 */
	public long getTodayCount()
	{
		return dao.getRowCount(new java.util.Date() );
	}

}
