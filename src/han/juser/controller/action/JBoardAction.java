package han.juser.controller.action;

import han.juser.controller.form.JBoardForm;
import han.juser.controller.manager.JBoardManager;
import han.juser.controller.manager.SubMenuItem;
import han.juser.controller.param.JBoardParam;
import han.juser.model.CategoryDTO;
import han.juser.service.ContentNotFoundException;
import han.juser.service.NotOwnerException;
import han.juser.service.ParentContentNotFoundException;
import han.juser.url.JBoardURL;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class JBoardAction extends ActionBase
{
	
	//private static final String SAVE_DIR = "/upload/jboard";
	
	
	private static final String CONTENT_KEY = "content";
	
	
	private static final String CONTENTLIST_KEY = "contentList";
	
	JBoardManager manager;
	
	
	public void setJboardManager(JBoardManager jm)
	{
		manager = jm;
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
	
	
	

	public ActionForward fileDown(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		manager.download(req, res );
		return null;
	}
	
	
	
	
	

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		common(req);
		manager.setContentList(req, CONTENTLIST_KEY);
		
		return mapping.findForward("fw-index");
	}
	
	
	
	
	

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws ContentNotFoundException
	{
		common(req);
		manager.setContent(req, CONTENT_KEY);
		manager.setGroupList(req, CONTENTLIST_KEY);
		
		return mapping.findForward("fw-view");
	}
	
	
	
	

	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		
		DynaActionForm df = (DynaActionForm)form;
		String target = df.getString(JBoardParam.TARGET);
		String keyword = df.getString(JBoardParam.KEYWORD);
		
		common(req);
		manager.setContentList(req, CONTENTLIST_KEY, target, keyword);
			
		return mapping.findForward("fw-index");
	}
	
	
	
	
	

	public ActionForward writeForm(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws ContentNotFoundException, ParentContentNotFoundException
	{
		JBoardForm bf = (JBoardForm)form;
	
		if( bf.isSubmit() ) {
			return write(mapping, form, req, res);
		} else {
			if( JBoardParam.getContentId(req) != -1 ) {
				if( req.getAttribute(CONTENT_KEY) == null ) {
					manager.setContent(req, CONTENT_KEY);
				}
			}
		}
		
		common(req);
		saveToken(req);
		req.setAttribute("action", "/writeForm");
		return mapping.findForward("fw-writeForm");
	}
	
	
	
	private ActionForward write(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws ParentContentNotFoundException, ContentNotFoundException
	{
		
		JBoardURL url = new JBoardURL(req);
		if( !isTokenValid(req) ){
			url.removeParameter(JBoardParam.ARTICLE_ID);
			return resultMessage(mapping, req, Properties.FAIL_TOKEN, url.getURI("/index"));
		}
		
		
		JBoardForm jf = (JBoardForm)form;
		ActionErrors errors = jf.validate(mapping, req);
		if(errors == null || errors.isEmpty() ) {
			long articleId = manager.addContent( req,  jf);
			url.setParameter(JBoardParam.ARTICLE_ID, String.valueOf(articleId) );
			resetToken(req);
			return resultMessage(mapping, req, Properties.SUCCESS_WRITE, url.getURI("/view"));
		} else {
			saveErrors(req, errors);
		}
		
		jf.setSubmit(false);
		req.setAttribute(CONTENT_KEY, jf);
		return writeForm(mapping, form, req, res);
		
	}
	

	

	
	
	
	
	public ActionForward modifyForm(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws ContentNotFoundException, NotOwnerException
	{
		
		JBoardForm bf = (JBoardForm)form;
		if( bf.isSubmit() ) {
			return modify(mapping, form, req, res);
		} else {
			if( req.getAttribute(CONTENT_KEY) == null ) {
				manager.setContent(req, CONTENT_KEY);
			}
		}
		common(req);
		saveToken(req);
		return mapping.findForward("fw-modifyForm");
	}
	
	
	

	private ActionForward modify(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws ContentNotFoundException, NotOwnerException
	{
		JBoardURL url = new JBoardURL(req);
		if( !isTokenValid(req) ){
			url.removeParameter(JBoardParam.ARTICLE_ID);
			return resultMessage(mapping, req, Properties.FAIL_TOKEN, url.getURI("/index"));
		}
		
		JBoardForm jf = (JBoardForm)form;
		ActionErrors errors = jf.validate(mapping, req);
		
		if(errors == null || errors.isEmpty() ) {
			manager.modifyContent(req, jf);
			resetToken(req);
			return resultMessage(mapping, req, Properties.SUCCESS_MODIFY, url.getURI("/view"));
		}else {
			saveErrors(req, errors);
		}
		
		jf.setSubmit(false);
		req.setAttribute(CONTENT_KEY, jf);
		return modifyForm(mapping, form, req, res);
	}
	
	
	
	

	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws ContentNotFoundException, NotOwnerException 
	{
		JBoardURL url = new JBoardURL(req);
		
		manager.removeContent(req );
		
		url.removeParameter(JBoardParam.ARTICLE_ID);
		return resultMessage(mapping, req, Properties.SUCCESS_REMOVE, url.getURI("/index"));
	}
	
	
	
	
	

	public ActionForward vote(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws ContentNotFoundException 
	{
		JBoardURL url = new JBoardURL(req);
		manager.updateVote(req);
		
		return resultMessage(mapping, req, Properties.SUCCESS_VOTE, url.getURI("/view"));
	}
	
	
	

	public ActionForward deleteFile(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws ContentNotFoundException, NotOwnerException 
	{
		JBoardURL url = new JBoardURL(req);
		manager.deleteFile(req );
		
		return resultMessage(mapping, req, Properties.SUCCESS_VOTE, url.getURI("/modifyForm"));
	}
}
