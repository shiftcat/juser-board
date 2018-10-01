package han.juser.service;

import han.juser.dao.GuestDAO;
import han.juser.model.GuestDTO;

import java.util.List;

public interface GuestService extends ServiceBase, SummaryService 
{

	public abstract void setGuestDAO(GuestDAO dao);

	public abstract long getRowCount();

	public abstract long getRowCount(String userId);

	public abstract GuestDTO getContent(long contentId) throws ContentNotFoundException;

	public abstract GuestDTO getContent(long contentId, String userId) throws ContentNotFoundException, NotOwnerException;

	public abstract List getContentList(int index);

	public abstract List getContentList(int index, String userId);

	public abstract long insert(String userId, String content, String remoteAddr);

	public abstract int update(long contentId, String userId, String content, String remoteAddr) throws ContentNotFoundException, NotOwnerException;

	public abstract int delete(long contentId, String userId) throws ContentNotFoundException, NotOwnerException;

	public abstract int delete(long contentId) throws ContentNotFoundException;

}