package han.juser.service.imple;

import han.juser.dao.JBoardDAO;
import han.juser.model.JBoardDTO;
import han.juser.service.ContentNotFoundException;
import han.juser.service.JBoardService;
import han.juser.service.NotOwnerException;
import han.juser.service.ParentContentNotFoundException;
import han.juser.service.SummaryService;
import han.juser.url.PageBean;

import java.util.List;

public class JBoardServiceImple extends PageBean implements SummaryService, JBoardService
{
	private JBoardDAO dao;
	
	public JBoardServiceImple()
	{
		
	}
	

	public void setJBoardDAO(JBoardDAO dao)
	{
		this.dao = dao;
	}
	
	

	public long getRowCount()
	{
		return dao.getRowCount();
	}
	
	
	

	public long getRowCount(long articleId)
	{
		return dao.getRowCount(articleId);
	}
	
	

	public long getRowCount(String column, String keyword)
	{
		return dao.getRowCount(column, keyword);
	}
	
	

	public int delete(String userId, long articleId, long groupId) throws ContentNotFoundException, NotOwnerException
	{
		JBoardDTO dto = dao.getContent(articleId);
		if(dto == null) {
			throw new ContentNotFoundException();
		}
		if( !dto.getUserId().equals( userId )) {
			throw new NotOwnerException();
		}
		return dao.deleteAll(articleId);
	}
	
	

	public int delete(long articleId) throws ContentNotFoundException
	{
		JBoardDTO dto = dao.getContent(articleId);
		if(dto == null) {
			throw new ContentNotFoundException();
		}
		return dao.deleteAll(articleId);
	}
	
	
	

	public List getChildList(long articleId)
	{
		return dao.getChildList(articleId);
	}
	
	
	
	public JBoardDTO getContent(long articleId) throws ContentNotFoundException
	{
		JBoardDTO dto = dao.getContent(articleId);
		if(dto == null) {
			throw new ContentNotFoundException();
		}
		return dao.getContent(articleId);
	}
	
	
	
	public JBoardDTO getContent(long articleId, String userId) throws ContentNotFoundException, NotOwnerException
	{
		JBoardDTO dto = dao.getContent(articleId);
		if(dto == null) {
			throw new ContentNotFoundException();
		}
		if( !dto.getUserId().equals( userId )) {
			throw new NotOwnerException();
		}
		return dao.getContent(articleId);
	}
	
	
	
	public List getContentList(int index)
	{
		setCurrentPage(index);
		setRowCount( getRowCount() );
		return dao.getContentList(getStart(), getStart()+getLimit());
	}
	
	
	
	public List getContentList(int index, String column, String keyword)
	{
		setCurrentPage(index);
		setRowCount( getRowCount(column, keyword) );
		return dao.getContentList(getStart(), getStart()+getLimit(), column, keyword);
	}
	
	
	
	
	public long insert(long parentId, String userId, String link, String subject, String content, String originalFiles, String systemFiles, String remoteAddr) throws ParentContentNotFoundException
	{
		if(parentId > 0 && dao.getContent(parentId) == null) {
			throw new ParentContentNotFoundException();
		}
		JBoardDTO dto = new JBoardDTO();
		dto.setParentId( parentId );
		dto.setUserId( userId );
		dto.setLink( link );
		dto.setSubject( subject );
		dto.setContent( content );
		dto.setOriginalFiles( originalFiles );
		dto.setSystemFiles( systemFiles );
		dto.setRemoteAddr( remoteAddr );
		return dao.insert(dto);
	}

	
	
	
	public int unSetFileNames(String userId, long articleId) throws ContentNotFoundException, NotOwnerException
	{
		JBoardDTO dto = dao.getContent(articleId);
		if(dto == null) {
			throw new ContentNotFoundException();
		}
		if( !dto.getUserId().equals( userId )) {
			throw new NotOwnerException();
		}
		return dao.unSetFileNames(articleId);
	}
	
	
	
	
	public int update(long articleId, String userId, String subject, String content, String systemFiles, String originalFiles, String link, String remoteAddr) throws ContentNotFoundException, NotOwnerException
	{
		JBoardDTO dto = dao.getContent(articleId);
		if(dto == null) {
			throw new ContentNotFoundException();
		}
		if( !dto.getUserId().equals( userId )) {
			throw new NotOwnerException();
		}
		dto.setSubject(subject);
		dto.setContent(content);
		dto.setSystemFiles(systemFiles);
		dto.setOriginalFiles(originalFiles);
		dto.setLink(link);
		dto.setRemoteAddr(remoteAddr);
		return dao.update(dto);
	}
	
	
	
	
	public void updateHit(long articleId)
	{
		dao.updateHit(articleId);
	}
	
	
	
	
	
	public void updateVote(long articleId)
	{
		dao.updateVote(articleId);
	}
	
	
	
	public List getSummary( int limit)
	{
		return dao.getSummary(limit);
	}
	
}
