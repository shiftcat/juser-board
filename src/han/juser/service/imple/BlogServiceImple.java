package han.juser.service.imple;

import han.juser.dao.BlogDAO;
import han.juser.model.BlogDTO;
import han.juser.model.BlogReplyDTO;
import han.juser.service.BlogService;
import han.juser.service.CategoryNotFoundException;
import han.juser.service.ContentNotFoundException;
import han.juser.service.NotOwnerException;
import han.juser.service.ParentContentNotFoundException;
import han.juser.url.PageBean;

import java.util.List;

public class BlogServiceImple extends PageBean implements BlogService
{
	private BlogDAO dao;
	
	public BlogServiceImple()
	{
		
	}
	
	
	/* (non-Javadoc)
	 * @see han.juser.service.BlogService#setBlogDAO(han.juser.dao.BlogDAO)
	 */
	public void setBlogDAO(BlogDAO dao)
	{
		this.dao = dao;
	}
	
	
	/* (non-Javadoc)
	 * @see han.juser.service.BlogService#getRowCount()
	 */
	public long getRowCount()
	{
		return dao.getRowCount();
	}

	
	
	
	/* (non-Javadoc)
	 * @see han.juser.service.BlogService#getRowCount(java.lang.String, java.lang.String)
	 */
	public long getRowCount(String column, String keyword)
	{
		return dao.getRowCount(column, keyword);
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see han.juser.service.BlogService#getRowCount(int)
	 */
	public long getRowCount(int cateId)
	{
		return dao.getRowCount(cateId);
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see han.juser.service.BlogService#getRowCount(int, java.lang.String, java.lang.String)
	 */
	public long getRowCount(int cateId, String column, String keyword)
	{
		return dao.getRowCount(cateId, column, keyword);
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see han.juser.service.BlogService#getContentList(int)
	 */
	public List getContentList(int index)
	{
		setCurrentPage( index );
		setRowCount( getRowCount() );
		return dao.getContentList(getStart()+1, getLimit());
	}
	
	

	
	
	/* (non-Javadoc)
	 * @see han.juser.service.BlogService#getContentList(int, int)
	 */
	public List getContentList(int cateId, int index)
	{
		setCurrentPage( index );
		setRowCount( getRowCount(cateId) );
		return dao.getContentList(cateId, getStart()+1, getLimit());
	}

	

	
	
	
	
	/* (non-Javadoc)
	 * @see han.juser.service.BlogService#getContentList(int, int, java.lang.String, java.lang.String)
	 */
	public List getContentList(int cateId, int index, String column, String keyword)
	{
		setCurrentPage( index );
		setRowCount( getRowCount(cateId, column, keyword) );
		return dao.getContentList(cateId, getStart()+1, getLimit(), column, keyword);
	}
	
	

	
	
	
	
	/* (non-Javadoc)
	 * @see han.juser.service.BlogService#getContentList(int, java.lang.String, java.lang.String)
	 */
	public List getContentList(int index, String column, String keyword)
	{
		setCurrentPage( index );
		setRowCount( getRowCount(column, keyword) );
		return dao.getContentList(getStart()+1, getLimit(), column, keyword);
	}
	
	
	
	
	
	/* (non-Javadoc)
	 * @see han.juser.service.BlogService#getContent(long)
	 */
	public BlogDTO getContent(long articleId) throws ContentNotFoundException
	{
		BlogDTO dto = dao.getContent(articleId);
		if(dto == null) {
			throw new ContentNotFoundException();
		}
		return dto;		
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see han.juser.service.BlogService#getContent(long, java.lang.String)
	 */
	public BlogDTO getContent(long articleId, String userId) throws ContentNotFoundException, NotOwnerException
	{
		BlogDTO dto = dao.getContent(articleId);
		if(dto == null) {
			throw new ContentNotFoundException();
		}
		if( !dto.getUserId().equals( userId ) ) {
			throw new NotOwnerException();
		}
		
		return dto;
	}
	
	
	/* (non-Javadoc)
	 * @see han.juser.service.BlogService#insert(han.juser.model.BlogDTO)
	 */
	public long insert(BlogDTO dto) throws CategoryNotFoundException
	{
		if( dao.getCategory(dto.getCateId()) == null ) {
			throw new CategoryNotFoundException();
		}
		
		//return dao.insertContent(dto);
		return insertBlogContent(dao, dto);
	}
	
	
	private synchronized static long insertBlogContent(BlogDAO dao, BlogDTO dto)
	{
		return dao.insertContent(dto);
	}
	
	
	/* (non-Javadoc)
	 * @see han.juser.service.BlogService#update(han.juser.model.BlogDTO)
	 */
	public int update(BlogDTO dto) throws CategoryNotFoundException, ContentNotFoundException, NotOwnerException
	{
		if( dao.getCategory(dto.getCateId()) == null ) {
			throw new CategoryNotFoundException();
		}
		BlogDTO bdto = dao.getContent( dto.getArticleId() );
		if( bdto == null ) {
			throw new ContentNotFoundException();
		}
		if( !bdto.getUserId().equals( dto.getUserId() ) ) {
			throw new NotOwnerException();
		}
		return dao.updateContent(dto);
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see han.juser.service.BlogService#delete(long)
	 */
	public int delete(long articleId)
	{
		return dao.deleteContent(articleId);
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see han.juser.service.BlogService#delete(long, java.lang.String)
	 */
	public int delete(long articleId, String userId) throws ContentNotFoundException, NotOwnerException
	{
		BlogDTO dto = dao.getContent( articleId );
		if( dto == null ) {
			throw new ContentNotFoundException();
		}
		if( !dto.getUserId().equals( userId ) ) {
			throw new NotOwnerException();
		}
		return dao.deleteContent(articleId);
	}
	
	

	/* (non-Javadoc)
	 * @see han.juser.service.BlogService#insertReply(han.juser.model.BlogReplyDTO)
	 */
	public int insertReply(BlogReplyDTO reply) throws ParentContentNotFoundException
	{
		if( dao.getContent(reply.getArticleId()) == null ) {
			throw new ParentContentNotFoundException();
		}
		return dao.insertReply(reply);
	}
	
	
	
	/* (non-Javadoc)
	 * @see han.juser.service.BlogService#deleteReply(long)
	 */
	public int deleteReply(long replyId)
	{
		return dao.deleteReply(replyId);
	}
	
	
	
	/* (non-Javadoc)
	 * @see han.juser.service.BlogService#deleteReply(long, java.lang.String)
	 */
	public int deleteReply(long replyId, String userId) throws ContentNotFoundException, NotOwnerException
	{
		BlogReplyDTO dto = dao.getReply(replyId);
		if( dto == null) {
			throw new ContentNotFoundException();
		}
		if( !dto.getUserId().equals( userId )) {
			throw new NotOwnerException();
		}
		return dao.deleteReply(replyId);
	}
	
	
	
	
	
	/* (non-Javadoc)
	 * @see han.juser.service.BlogService#getReply(long)
	 */
	public BlogReplyDTO getReply(long replyId) throws ContentNotFoundException
	{
		if(dao.getReply(replyId) == null) {
			throw new ContentNotFoundException();
		}
		return dao.getReply(replyId);
	}
	
	
	/* (non-Javadoc)
	 * @see han.juser.service.BlogService#unSetFileNames(long, java.lang.String)
	 */
	public int unSetFileNames(long articleId, String userId) throws ContentNotFoundException, NotOwnerException
	{
		BlogDTO dto = dao.getContent(articleId);
		if( dto == null) {
			throw new ContentNotFoundException();
		}
		if( !dto.getUserId().equals( userId )) {
			throw new NotOwnerException();
		}
		return dao.unSetFileNames(articleId);
	}
	
	
	
	
	/* (non-Javadoc)
	 * @see han.juser.service.BlogService#getReplyList(long)
	 */
	public List getReplyList(long articleId)
	{
		return dao.getReplyList(articleId);
	}
	
	
	/* (non-Javadoc)
	 * @see han.juser.service.BlogService#getSummary(int)
	 */
	public List getSummary( int limit)
	{
		return dao.getSummary(limit);
	}
}
