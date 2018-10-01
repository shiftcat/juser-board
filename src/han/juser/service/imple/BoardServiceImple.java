package han.juser.service.imple;

import han.juser.dao.BoardDAO;
import han.juser.dao.FileStoreDAO;
import han.juser.model.BoardDTO;
import han.juser.model.BoardReplyDTO;
import han.juser.model.FileDTO;
import han.juser.service.BoardService;
import han.juser.service.ContentNotFoundException;
import han.juser.service.NotOwnerException;
import han.juser.service.ParentContentNotFoundException;
import han.juser.service.ReplyNotFoundException;
import han.juser.url.PageBean;

import java.io.InputStream;
import java.util.List;

public class BoardServiceImple extends PageBean implements BoardService
{
	
	private BoardDAO dao;
	
	private FileStoreDAO fdao;
	
	public BoardServiceImple()
	{
		super();
	}
	
	

	public void setBoardDAO(BoardDAO dao)
	{
		this.dao = dao;
	}
	

	public void setFileDAO(FileStoreDAO dao)
	{
		this.fdao = dao;
	}
	
	

	public long getRowCount()
	{
		return dao.getRowCount();
	}
	
	

	public long getRowCount(String column, String keyword)
	{
		return dao.getRowCount(column, keyword);
	}
	
	

	public List getContentList(int index) 
	{
		setCurrentPage(index);
		setRowCount( getRowCount() );
		return dao.getContentList(getStart()+1, getLimit() );
	}
	
	

	public List getContentList(int index, String column, String keyword) 
	{
		setCurrentPage(index);
		setRowCount( getRowCount(column, keyword) );
		return dao.getContentList(getStart()+1, getLimit(), column, keyword );
	}
	
	
	
	

	public BoardDTO getContent(long contentId) throws ContentNotFoundException
	{
		BoardDTO dto = dao.getContent(contentId);
		if(dto == null) {
			throw new ContentNotFoundException();
		}
		return dto;
	}
	
	
	

	public BoardDTO getContent(long contentId, String userId) throws ContentNotFoundException, NotOwnerException
	{
		BoardDTO dto = dao.getContent(contentId);

		if(dto == null) {
			throw new ContentNotFoundException();
		}
		if( userId == null || !userId.equals(dto.getUserId()) ) {
			throw new NotOwnerException();
		}
		
		return dto;
	}
	
	

	public void updateHit(long contentId)
	{
		dao.updateHitCount( contentId );
	}
	
	
	

	public void updateVote(long contentId)
	{
		dao.updateVoteCount(contentId);
	}
	
	

	public BoardReplyDTO getReply(long replyId) throws ReplyNotFoundException
	{
		BoardReplyDTO dto = dao.getReply(replyId);
		if(dto == null) {
			throw new ReplyNotFoundException();
		}
		return dto;
	}
	
	

	public BoardReplyDTO getReply(long replyId, String userId) throws ReplyNotFoundException, NotOwnerException
	{
		BoardReplyDTO dto = null;
		dto = getReply(replyId);
		if( !userId.equals(dto.getUserId()) ) {
			throw new NotOwnerException();
		}
		return dto;
	}
	
	
	

	public long insert(String userId, String subject, String content, String remoteAddr, String link, 
			String originalFiles, String systemFiles, InputStream is, int size)
	{
		long seq = dao.insert(userId, subject, content, remoteAddr, link, originalFiles, systemFiles);
		
		if( is != null && size > 0 ) {
			applyFile(seq, originalFiles, is, size);
		}
		
		return seq;
	}
	

	public int update(long contentId, String userId, String subject, String content, String link, String remoteAddr, 
			String originalFiles, String systemFiles, InputStream is, int size) throws ContentNotFoundException, NotOwnerException
	{
		getContent(contentId, userId);
		int i = -1;
		if( is != null && size > 0 ) {
			i = dao.update( contentId, subject, content, link, remoteAddr, originalFiles, systemFiles );
			if(i == 1) {
				applyFile(contentId, originalFiles, is, size);
			}
		} else {
			i = dao.update( contentId, subject, content, link, remoteAddr );
		}
		
		return i;
	}
	
	
	
	private void applyFile(long contentId, String originalFiles, InputStream is, int size)
	{
		if( fdao.getFile("board", contentId) == null ) {
			fdao.saveFile("board", contentId, originalFiles, is, size);
		} else {
			fdao.updateFile("board", contentId, originalFiles, is, size);
		}
	}
	
	
	

	public int delete(long contentId)
	{
		int i = dao.delete(contentId);
		if( i == 1 ) {
			fdao.deleteFile("board", contentId);
		}
		return i;
	}
	
	
	

	public int delete(long contentId, String userId) throws ContentNotFoundException, NotOwnerException
	{
		BoardDTO dto = dao.getContent(contentId);

		if( dto == null) {
			throw new ContentNotFoundException();
		}
		if( !dto.getUserId().equals( userId ) ){
			throw new NotOwnerException();
		}
		
		return delete(contentId);
	}
	
	
	

	public int insertReply(long parentId, String userId, String remoteAddr, String reply) throws ParentContentNotFoundException
	{
		BoardDTO dto = dao.getContent(parentId);
		
		if( dto == null) {
			throw new ParentContentNotFoundException();
		}

		return dao.insertReply(parentId, userId, remoteAddr, reply);
	}
	

	public int deleteReply(long replyId) throws ReplyNotFoundException
	{
		getReply(replyId);
		return dao.deleteReply(replyId);
	}
	
	

	public int deleteReply(long replyId, String userId) throws ReplyNotFoundException, NotOwnerException
	{
		getReply(replyId, userId);
		return dao.deleteReply(replyId);
	}
	
	

	public List getSummary( int limit)
	{
		return dao.getSummary(limit);
	}
	
	

	public InputStream getFileStream(long contentId)
	{
		FileDTO dto = fdao.getFile("board", contentId);
		return dto == null ? null : dto.getBinaryStream();
	}
	
}
