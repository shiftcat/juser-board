package han.juser.dao;

import han.juser.model.JBoardDTO;

import java.util.List;

public interface JBoardDAO extends Summary
{
	public abstract long getRowCount();
	
	public abstract long getRowCount(long articleId);
	
	public abstract long getRowCount(String column, String keyword);
	
	public abstract List getContentList(long start, long end);
	
	public abstract List getContentList(long start, long end, String column, String keyword);
	
	public abstract List getChildList(long articleId);

	public abstract JBoardDTO getContent(long articleId);
	
	public abstract long insert(JBoardDTO dto);
	
	public abstract int update(JBoardDTO dto);
	
	public abstract void updateHit(long articleId);
	
	public abstract void updateVote(long articleId);
	
	public abstract int deleteAll(long articleId);
	
	public abstract int deleteChild(long parentId);
	
	public abstract int unSetFileNames(long articleId);
}