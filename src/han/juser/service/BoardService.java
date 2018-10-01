package han.juser.service;

import han.juser.dao.BoardDAO;
import han.juser.dao.FileStoreDAO;
import han.juser.model.BoardDTO;
import han.juser.model.BoardReplyDTO;

import java.io.InputStream;
import java.util.List;

public interface BoardService  extends ServiceBase, SummaryService 
{

	public abstract void setBoardDAO(BoardDAO dao);

	public abstract void setFileDAO(FileStoreDAO dao);

	public abstract long getRowCount();

	public abstract long getRowCount(String column, String keyword);

	public abstract List getContentList(int index);

	public abstract List getContentList(int index, String column, String keyword);

	public abstract BoardDTO getContent(long contentId) throws ContentNotFoundException;

	public abstract BoardDTO getContent(long contentId, String userId) throws ContentNotFoundException, NotOwnerException;

	public abstract void updateHit(long contentId);

	public abstract void updateVote(long contentId);

	public abstract BoardReplyDTO getReply(long replyId) throws ReplyNotFoundException;

	public abstract BoardReplyDTO getReply(long replyId, String userId) throws ReplyNotFoundException, NotOwnerException;

	public abstract long insert(String userId, 
			                       String subject, 
			                       String content, 
			                       String remoteAddr,
			                       String link, 
			                       String originalFiles,
			                       String systemFiles, 
			                       InputStream is, int size);
	
	public abstract int update( long contentId, 
			                       String userId, 
			                       String subject,
			                       String content, 
			                       String link, 
			                       String remoteAddr,
			                       String originalFiles, 
			                       String systemFiles, 
			                       InputStream is, 
			                       int size) throws ContentNotFoundException, NotOwnerException;

	public abstract int delete(long contentId);

	public abstract int delete(long contentId, String userId) throws ContentNotFoundException, NotOwnerException;

	public abstract int insertReply(long parentId, String userId, String remoteAddr, String reply)
			throws ParentContentNotFoundException;

	public abstract int deleteReply(long replyId) throws ReplyNotFoundException;

	public abstract int deleteReply(long replyId, String userId) throws ReplyNotFoundException, NotOwnerException;

	public abstract InputStream getFileStream(long contentId);

}