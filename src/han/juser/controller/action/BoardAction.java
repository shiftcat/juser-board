package han.juser.controller.action;

import han.juser.controller.form.BoardForm;
import han.juser.controller.form.BoardReplyForm;
import han.juser.controller.manager.BoardManager;
import han.juser.controller.manager.SubMenuItem;
import han.juser.controller.param.BoardParam;
import han.juser.model.CategoryDTO;
import han.juser.service.ContentNotFoundException;
import han.juser.service.NotOwnerException;
import han.juser.service.ParentContentNotFoundException;
import han.juser.service.ReplyNotFoundException;
import han.juser.url.BoardURL;

import java.io.FileNotFoundException;
import java.io.IOException;
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

/**
 * 
 * @author Y.Han Lee
 *
 */
public class BoardAction extends ActionBase
{
	
	private static final String CONTENT_KEY = "content";
	
	private static final String CONTENTLIST_KEY = "contentList";
	
	BoardManager manager;
	
	
	public void setBoardManager(BoardManager bm)
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
		String target = df.getString(BoardParam.TARGET);
		String keyword = df.getString(BoardParam.KEYWORD);
		
		common(req);
		manager.setContentList( req, CONTENTLIST_KEY, target, keyword);
		
		return mapping.findForward("fw-index");
	}
	
	
	
	

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws ContentNotFoundException
	{
		saveToken(req);
		DynaActionForm df = (DynaActionForm)form;
		String target = df.getString(BoardParam.TARGET);
		String keyword = df.getString(BoardParam.KEYWORD);

		common(req);
		manager.setContent(req, CONTENT_KEY);
		
		if( target != null && !target.equals("") ) {
			manager.setContentList( req, CONTENTLIST_KEY, target, keyword);
		}else {
			manager.setContentList( req, CONTENTLIST_KEY);
		}
		
		return mapping.findForward("fw-view");
	}
	
	
	
	
	
	

	
	public ActionForward writeForm(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws FileNotFoundException, IOException
	{
		BoardForm bf = (BoardForm)form;
		if( bf.isSubmit() ) {
			return writeAction(mapping, form, req, res);
		} 
		
		common(req);
		saveToken(req);
		req.setAttribute("action", "/frm-bbsWrite");
		return mapping.findForward("fw-writeForm");
	}
	

	private ActionForward writeAction(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws FileNotFoundException, IOException
	{
		BoardURL url = new BoardURL(req);
		
		if( !isTokenValid(req)) {
			url.removeParameter(BoardParam.CONTENT_ID);
			return resultMessage(mapping, req, Properties.FAIL_TOKEN, url.getURI("/index"));
		}
		
		BoardForm bf = (BoardForm)form;
		ActionErrors errors = bf.validate(mapping, req);
		
		if( errors == null || errors.isEmpty() ) {
			long bbsId = manager.addContent( req, bf);
			url.setParameter(BoardParam.CONTENT_ID, String.valueOf(bbsId) );
			resetToken(req);
			return resultMessage(mapping, req, Properties.SUCCESS_WRITE, url.getURI("/view"));
		}else {
			saveErrors(req, errors);
		}
		
		bf.setSubmit(false);
		req.setAttribute(CONTENT_KEY, bf);
		
		return writeForm(mapping, form, req, res);
	}
	
	
	

	public ActionForward modifyForm(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws ContentNotFoundException, NotOwnerException, FileNotFoundException, IOException
	{
		
		BoardForm bf = (BoardForm)form;
		if(bf.isSubmit()) {
			return modifyAction(mapping, form, req, res);
		}else {
			if( req.getAttribute(CONTENT_KEY) == null ) {
				manager.setContent(req, CONTENT_KEY, "");
			}
		}
		
		common(req);
		saveToken(req);
		req.setAttribute("action", "/frm-bbsModify");
		
		return mapping.findForward("fw-writeForm");
	}
	
	
	

	private ActionForward modifyAction(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws ContentNotFoundException, NotOwnerException, FileNotFoundException, IOException
	{
		BoardURL url = new BoardURL(req);
		
		if( !isTokenValid(req)) {
			url.removeParameter(BoardParam.CONTENT_ID);
			return resultMessage(mapping, req, Properties.FAIL_TOKEN, url.getURI("/index"));
		}
		
		BoardForm bf = (BoardForm)form;
		ActionErrors errors = bf.validate(mapping, req);
		
		if( errors == null || errors.isEmpty()) {
			manager.modifyContent(req, bf);
			resetToken(req);
			return resultMessage(mapping, req, Properties.SUCCESS_MODIFY, url.getURI("/view"));
		} else {
			saveErrors(req, errors);
		}
		
		bf.setSubmit(false);
		req.setAttribute(CONTENT_KEY, bf);
		
		return modifyForm(mapping, form, req, res);
	}
	
	

	
	
	
	
	

	public ActionForward removeAction(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws ContentNotFoundException, NotOwnerException
	{
		BoardURL url = new BoardURL(req);
		// 1.토큰 유효성 검사
		/*
		if( !isTokenValid(req)) {
			url.removeParameter(BoardParam.BBS_ID);
			return resultMessage(mapping, req, Properties.FAIL_TOKEN, url.getURI("/index"));
		}
		resetToken(req);
		*/
		
		// 2. 요청처리
		manager.removeContent( req );
		
		// 3.결과페이지 이동
		url.removeParameter(BoardParam.CONTENT_ID);
		return resultMessage(mapping, req, Properties.SUCCESS_REMOVE, url.getURI("/index"));
	}
	
	
	
	
	

	public ActionForward replyWriteAction(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws ParentContentNotFoundException
	{
		BoardURL url = new BoardURL(req);
		// 1.토큰 유효성 검사
		if( !isTokenValid(req)) {
			url.removeParameter(BoardParam.CONTENT_ID);
			return resultMessage(mapping, req, Properties.FAIL_TOKEN, url.getURI("/index"));
		}
		resetToken(req);
		
		
		//요청처리
		BoardReplyForm bf = (BoardReplyForm)form;
		manager.addReply( req, bf );
		
		// 3.결과페이지 이동
		return resultMessage(mapping, req, Properties.SUCCESS_WRITE, url.getURI("/view"));
	}
	
	
	
	

	public ActionForward replyRemoveAction(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws ReplyNotFoundException, NotOwnerException
	{
		BoardURL url = new BoardURL(req);
		// 1.토큰 유효성 검사
		if( !isTokenValid(req)) {
			url.removeParameter(BoardParam.CONTENT_ID);
			return resultMessage(mapping, req, Properties.FAIL_TOKEN, url.getURI("/index"));
		}
		resetToken(req);
		
		// 2. 요청처리
		manager.removeReply(req);
		
		// 3.결과페이지 이동
		return resultMessage(mapping, req, Properties.SUCCESS_REMOVE, url.getURI("/view"));
	}

	
	
	

	public ActionForward updateVote(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws ContentNotFoundException
	{
		// 1. 요청처리
		manager.updateVote(req);
		
		// 2.결과페이지 이동
		BoardURL url = new BoardURL(req);
		return resultMessage(mapping, req, Properties.SUCCESS_VOTE, url.getURI("/view"));
	}
	
	
	
	public ActionForward download(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		//getManager().download(req, res, getRealPath(SAVE_DIR) );
		manager.download(req, res);
		return null;
	}
	
}
