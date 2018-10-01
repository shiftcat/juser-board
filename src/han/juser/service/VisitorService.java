package han.juser.service;

import han.juser.dao.VisitorDAO;

public interface VisitorService {

	public abstract void setVisitorDAO(VisitorDAO dao);

	public abstract long insert(String userAgent, String remoteAddr,
			String remoteHost, String referer);

	public abstract long getVisitCount();

	public abstract long getTodayCount();

}