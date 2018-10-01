package han.juser.controller.manager;

import han.juser.controller.form.BlogForm;
import han.juser.controller.param.BlogParam;
import han.juser.model.BlogDTO;
import han.juser.model.BlogReplyDTO;
import han.juser.service.BlogService;
import han.juser.service.CategoryNotFoundException;
import han.juser.service.ContentNotFoundException;
import han.juser.service.NotOwnerException;
import han.juser.service.ParentContentNotFoundException;
import han.juser.url.PageBean;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class BlogManager extends ManagerBase
{
	BlogService service;
	
	
	public BlogManager()
	{
		super();
	}
	
	
	
	public void setService(BlogService service)
	{
		this.service = service;
	}
	


	
	
	
	public void setContentList(HttpServletRequest req, String key) 
	{
		List list = null;

		int cate = BlogParam.getCategoryId(req);
		int pno = BlogParam.getPageNumber(req);
		if( cate == -1 ) {
			list = service.getContentList( pno );				
		} else {
			list = service.getContentList( cate, pno );
		}
		
		req.setAttribute(PageBean.PAGEBEAN_KEY, service.getPageBean() );
		req.setAttribute( key , list);
	}
	
	
	
	
	
	public void setContentList(HttpServletRequest req, String key, int cateId, String target, String keyword)
	{
		List list = null;

		int pno = BlogParam.getPageNumber(req);
		if( cateId == 0) {
			list = service.getContentList( pno,  target, keyword);
		} else {
			list = service.getContentList( cateId, pno,  target, keyword);
		}
		
		req.setAttribute(PageBean.PAGEBEAN_KEY, service.getPageBean() );
		req.setAttribute( key, list);
	}
	
	
	
	public void setContent(HttpServletRequest req, String key) throws ContentNotFoundException
	{
		BlogDTO dto = null;

		long articleId = BlogParam.getContentId(req);
		dto = service.getContent( articleId);
		req.setAttribute(key, dto);
	}
	
	
	public void setContent(HttpServletRequest req, String key, String userId) throws ContentNotFoundException, NotOwnerException
	{
		BlogDTO dto = null;

		long articleId = BlogParam.getContentId(req);
		dto = service.getContent( articleId, userId);

		req.setAttribute(key, dto);
	}
	
	
	
	public BlogDTO getContent(HttpServletRequest req, long articleId) throws ContentNotFoundException
	{
		return service.getContent( articleId);
	}
	
	
	
	
	public long addContent(HttpServletRequest req, BlogForm form) throws CategoryNotFoundException
	{
		BlogDTO dto = new BlogDTO();
		dto.setCateId( form.getCateId() );
		dto.setContent( form.getContent() );
		dto.setRemoteAddr( req.getRemoteAddr() );
		dto.setSubject( form.getSubject() );
		dto.setUserId( loginHandler.getMember(req).getUserId() );
		return service.insert(dto);
	}
	
	
	
	public void modifyContent(HttpServletRequest req, BlogForm form) throws ContentNotFoundException, NotOwnerException, CategoryNotFoundException
	{
		BlogDTO dto = service.getContent( form.getArticleId(), form.getUserId() );
			
		dto.setCateId( form.getCateId() );
		dto.setSubject( form.getSubject() );
		dto.setContent( form.getContent() );
		dto.setRemoteAddr( req.getRemoteAddr() );
		service.update( dto);
	}
	
	
	
	public void removeContent(HttpServletRequest req) throws ContentNotFoundException, NotOwnerException
	{
		long articleId = BlogParam.getContentId(req);
		BlogDTO dto = service.getContent( articleId);
		if( role.isAdmin(req)) {
			service.delete( articleId);
		}else {
			service.delete( articleId, loginHandler.getMember(req).getUserId());				
		}
	}
	
	
	public void addReply(HttpServletRequest req, long articleId,  String replyContent) throws ParentContentNotFoundException
	{
		BlogReplyDTO reply = new BlogReplyDTO();
		reply.setArticleId(articleId);
		reply.setContent(replyContent);
		reply.setRemoteAddr(req.getRemoteAddr());
		reply.setUserId(loginHandler.getMember(req).getUserId());
		service.insertReply( reply);
	}
	
	
	
	public void setReplyList(HttpServletRequest req, long articleId, String key)
	{
		List list = service.getReplyList( articleId);
		req.setAttribute(key, list);
	}
	
	
	
	
	public void removeReply(HttpServletRequest req, long replyId) throws ContentNotFoundException, NotOwnerException
	{
		if( role.isAdmin(req) ) {
			service.deleteReply( replyId);
		} else {
			service.deleteReply( replyId, loginHandler.getMember(req).getUserId());				
		}
	}
	
	
}
