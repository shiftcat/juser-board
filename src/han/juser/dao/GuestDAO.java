package han.juser.dao;

import java.util.List;

import han.juser.model.GuestDTO;


/**
 * 
 * @author Y.Han Lee
 *	
 */
public interface GuestDAO extends Summary
{
	
	public abstract void setDataSource(javax.sql.DataSource dataSource);
	
	
	public abstract long getRowCount();
	
	
	
	
	public abstract long getRowCount(String userId);
	
	
	
	
	public abstract GuestDTO getContent(long gid);
	
	
	
	public abstract List getContentList(long start, int limit);
	
	
	
	
	public abstract List getContentList(String userId, long start, int limit);
	
	
	public abstract long insert(String userId, String content, String remoteAddr);
	
	
	
	
	public abstract int delete(long gid);
	
	
	
	
	public abstract int update(long gid, String content, String remoteAddr);

}