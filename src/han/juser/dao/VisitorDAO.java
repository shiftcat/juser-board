package han.juser.dao;


public interface VisitorDAO 
{
	public long insert(String userAgent, String remoteAddr, String remoteHost, String referer);
	
	public int delete(long idx);
	
	public long getRowCount();
	
	public long getRowCount(java.util.Date date);
}
