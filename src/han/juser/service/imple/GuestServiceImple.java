package han.juser.service.imple;

import han.juser.dao.GuestDAO;
import han.juser.model.GuestDTO;
import han.juser.service.ContentNotFoundException;
import han.juser.service.GuestService;
import han.juser.service.NotOwnerException;
import han.juser.url.PageBean;

import java.util.List;

public class GuestServiceImple extends PageBean implements GuestService
{
	private GuestDAO dao;
	
	
	public GuestServiceImple()
	{
		
	}
	
	

	public void setGuestDAO(GuestDAO dao)
	{
		this.dao = dao;
	}
	
	
	

	public long getRowCount()
	{
		return dao.getRowCount();
	}
	
	
	
	

	public long getRowCount(String userId)
	{
		return dao.getRowCount(userId);
	}
	
	
	
	

	public GuestDTO getContent(long contentId) throws ContentNotFoundException
	{
		GuestDTO dto = dao.getContent(contentId);

		if( dto == null) {
			throw new ContentNotFoundException();
		}
		
		return dto;
	}
	
	
	
	

	public GuestDTO getContent(long contentId, String userId) throws ContentNotFoundException, NotOwnerException
	{
		GuestDTO dto = getContent(contentId);
		if( !dto.getUserId().equals( userId ) ) {
			throw new NotOwnerException();
		}
		return dto;
	}
	
	
	
	

	public List getContentList(int index)
	{
		setCurrentPage(index);
		setRowCount( getRowCount() );
		return dao.getContentList(getStart()+1, getLimit());
	}
	
	
	
	
	

	public List getContentList(int index, String userId)
	{
		setCurrentPage(index);
		setRowCount( getRowCount(userId) );
		return dao.getContentList(userId, getStart()+1, getLimit());
	}
	
	
	
	

	public long insert(String userId, String content, String remoteAddr)
	{
		return dao.insert(userId, content, remoteAddr);
	}
	
	

	public int update(long contentId, String userId, String content, String remoteAddr ) throws ContentNotFoundException, NotOwnerException
	{
		getContent(contentId, userId);
		return dao.update(contentId, content, remoteAddr);
	}
	
	
	

	public int delete(long contentId, String userId)throws ContentNotFoundException, NotOwnerException
	{
		getContent(contentId, userId);
		return dao.delete(contentId);
	}

	
	

	public int delete(long contentId )throws ContentNotFoundException
	{
		getContent(contentId );
		return dao.delete(contentId);
	}
	
	

	public List getSummary(int limit)
	{
		return dao.getSummary(limit);
	}
}
