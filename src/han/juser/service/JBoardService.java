package han.juser.service;

import han.juser.dao.JBoardDAO;
import han.juser.model.JBoardDTO;

import java.util.List;

public interface JBoardService extends ServiceBase, SummaryService 
{
	public abstract void setJBoardDAO(JBoardDAO dao);

	public abstract long getRowCount();

	public abstract long getRowCount(long articleId);

	public abstract long getRowCount(String column, String keyword);

	public abstract int delete(String userId, long articleId, long groupId) throws ContentNotFoundException, NotOwnerException;

	public abstract int delete(long articleId) throws ContentNotFoundException;

	public abstract List getChildList(long articleId);

	public abstract JBoardDTO getContent(long articleId) throws ContentNotFoundException;

	public abstract JBoardDTO getContent(long articleId, String userId) throws ContentNotFoundException, NotOwnerException;

	public abstract List getContentList(int index);

	public abstract List getContentList(int index, String column, String keyword);

	public abstract long insert(long parentId, String userId, String link, String subject, String content, String originalFiles, String systemFiles, String remoteAddr) throws ParentContentNotFoundException;

	public abstract int unSetFileNames(String userId, long articleId) throws ContentNotFoundException, NotOwnerException;

	public abstract int update(long articleId, String userId, String subject, String content, String systemFiles, String originalFiles, String link, String remoteAddr) throws ContentNotFoundException, NotOwnerException;

	public abstract void updateHit(long articleId);

	public abstract void updateVote(long articleId);

	public abstract List getSummary(int limit);

}