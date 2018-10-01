package han.juser.dao;


import han.juser.model.BoardDTO;
import han.juser.model.BoardReplyDTO;

import java.util.List;

public interface BoardDAO extends Summary
{
	public abstract long getRowCount();
	
	
	public abstract long getRowCount(String column, String keyword);
	

	public abstract long insert(String userId, String subject, String content, String remoteAddr, String link, String originalFiles, String systemFiles);

	
	public abstract List getContentList(long start, int limit);


	public abstract List getContentList(long start, int limit, String column, String keyword);
	
	
	public abstract BoardDTO getContent(long bbsId);

	
	public abstract int update(long contentId, String subject, String content, String link, String remoteAddr);
	
	
	public abstract int update(long contentId, String subject, String content, String link, String remoteAddr, String originalFiles, String systemFiles);

	
	public abstract int delete(long bbsId);
	
	
	
	public abstract BoardReplyDTO getReply(long replId);
	
	
	public abstract int insertReply(long parentId, String userId, String remoteAddr, String reply);

	
	public abstract int deleteReply(long replId);
	
	
	
	
	public abstract void updateHitCount(long bbsId);
	
	
	
	
	public abstract void updateVoteCount(long bbsId);

}