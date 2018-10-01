package han.juser.controller.action;

import han.juser.controller.form.BlogForm;
import han.juser.controller.manager.BlogManager;
import han.juser.controller.manager.ManagerHelper;
import han.juser.controller.manager.SubMenuItem;
import han.juser.controller.param.BlogParam;
import han.juser.model.CategoryDTO;
import han.juser.service.CategoryNotFoundException;
import han.juser.service.ContentNotFoundException;
import han.juser.service.NotOwnerException;
import han.juser.service.ParentContentNotFoundException;
import han.juser.url.BlogURL;
import han.juser.url.URLManager;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.DynaActionForm;

public class BlogAction extends ActionBase
{
	
	private BlogManager manager;
	
	
	private final String CONTENT_KEY = "content";
	
	
	private final String CONTENTLIST_KEY = "contentList";
	
	
	
	
	public void setServlet(ActionServlet servlet) 
	{
		super.setServlet(servlet);
		manager = ManagerHelper.getBlogManager(servlet.getServletContext());
	}
	
	
	public void setBlogManager(BlogManager bm)
	{
		manager = bm;
	}
	
	
	public void setSubMenu(HttpServletRequest req, String key) 
	{
		List sub = new ArrayList();
		List cate = categoryManager.getCategorys(req);
		for(Iterator i = cate.iterator(); i.hasNext();) {
			CategoryDTO dto = (CategoryDTO)i.next();
			SubMenuItem item = new SubMenuItem();
			item.setName(dto.getCateName());
			item.setUrl(req.getContextPath() + "/blog/index.do?cate=" + dto.getCateId());
			sub.add(item);
		}
		req.setAttribute(key, sub);
		
	}
	
	
	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		common(req);
		manager.setContentList(req, CONTENTLIST_KEY);
		return mapping.findForward("fw-index");
	}
	
	
	
	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		DynaActionForm df = (DynaActionForm)form;
		Integer cateId = (Integer)df.get(BlogParam.CATEGORY_ID);
		String target = df.getString(BlogParam.TARGET);
		String keyword = df.getString(BlogParam.KEYWORD);
		
		common(req);
		manager.setContentList(req, CONTENTLIST_KEY, cateId, target, keyword);
		return mapping.findForward("fw-index");
	}
	
	
	
	public ActionForward writeForm(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws CategoryNotFoundException 
	{
		BlogForm bf = (BlogForm)form;
		if(bf.isSubmit()) {
			return write(mapping, form, req, res);
		}
		common(req);
		req.setAttribute("action", "/writeForm");
		saveToken(req);
		return mapping.findForward("fw-writeForm");
	}
	
	
	
	
	private ActionForward write(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws CategoryNotFoundException
	{
		URLManager url = new BlogURL(req);
		url.removeParameter(BlogParam.ARTICLE_ID);
		
		if( !isTokenValid(req)) {
			return resultMessage(mapping, req, Properties.FAIL_TOKEN, url.getURI("/index"));
		}
		
		BlogForm bf = (BlogForm)form;
		ActionErrors errors = bf.validate(mapping, req);
		
		if(errors == null || errors.isEmpty() ) {
			manager.addContent(req, bf );
			resetToken(req);
			return resultMessage(mapping, req, Properties.SUCCESS_WRITE, url.getURI("/index") );
		}else {
			saveErrors(req, errors);
		}
		
		bf.setSubmit(false);
		req.setAttribute(CONTENT_KEY, bf);
		return writeForm(mapping, form, req, res);
		
	}
	
	
	
	
	
	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws ContentNotFoundException, NotOwnerException
	{
		URLManager url = new BlogURL(req);
		url.removeParameter(BlogParam.ARTICLE_ID);
		manager.removeContent(req );
		
		return resultMessage(mapping, req, Properties.SUCCESS_REMOVE, url.getURI("/index"));		
	}
	
	
	
	
	public ActionForward modifyForm(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws ContentNotFoundException, NotOwnerException, CategoryNotFoundException
	{
		
		BlogForm bf = (BlogForm)form;
		log.debug(bf);
		
		if(bf.isSubmit()) {
			return modify(mapping, form, req, res);
		} else {
			if( req.getAttribute(CONTENT_KEY) == null ) {
				manager.setContent(req, CONTENT_KEY);
			}
		}
		
		common(req);
		saveToken(req);
		req.setAttribute("action", "/modifyForm");
		
		return mapping.findForward("fw-modifyForm");
	}
	
	
	
	private ActionForward modify(ActionMapping mapping , ActionForm form, HttpServletRequest req, HttpServletResponse res) throws ContentNotFoundException, NotOwnerException, CategoryNotFoundException
	{
		URLManager url = new BlogURL(req);
		url.removeParameter(BlogParam.ARTICLE_ID);
		
		if( !isTokenValid(req)) {
			return resultMessage(mapping, req, Properties.FAIL_TOKEN, url.getURI("/index"));
		}
		
		BlogForm bf = (BlogForm)form;
		ActionErrors errors = bf.validate(mapping, req);
		
		if(errors == null || errors.isEmpty() ) {
				manager.modifyContent(req, bf);
				resetToken(req);
				return resultMessage(mapping, req, Properties.SUCCESS_WRITE, url.getURI("/index") );
		}else {
			saveErrors(req, errors);
		}
		
		bf.setSubmit(false);
		req.setAttribute(CONTENT_KEY, bf);
		return modifyForm(mapping, form, req, res);
	}
	
	
	
	// ajax
	public ActionForward writeReply(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws UnsupportedEncodingException, ParentContentNotFoundException
	{
		req.setCharacterEncoding("UTF-8");
		
		long articleId = Long.valueOf( req.getParameter("articleId") );
		String reply = req.getParameter("reply");

		manager.addReply(req, articleId, reply);
		log.debug("ok");
		return null;
	}
	
	
	
	// ajax
	public ActionForward replyList(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		manager.setReplyList(req, Long.valueOf( req.getParameter("articleId") ), "replyList");

		return mapping.findForward("fw-replyList");
	}
	
	
	
	
	//ajax
	public ActionForward removeReply(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws ContentNotFoundException, NotOwnerException
	{
		manager.removeReply(req, Long.valueOf(req.getParameter("replyId")) );

		log.debug("ok");
		return null;
	}
	
	
	
	//iframe
	public ActionForward content(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws ContentNotFoundException
	{
		//loginMember.getMember(req);
		manager.setContent(req, CONTENT_KEY);
		return mapping.findForward("fw-content");
	}
	
	
}
