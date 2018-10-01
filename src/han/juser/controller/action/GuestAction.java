package han.juser.controller.action;


import han.juser.controller.manager.GuestManager;
import han.juser.controller.manager.SubMenuItem;
import han.juser.model.CategoryDTO;
import han.juser.service.ContentNotFoundException;
import han.juser.service.NotOwnerException;
import han.juser.url.GuestURL;
import han.juser.url.URLManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class GuestAction extends ActionBase 
{
	
	private final String CONTENT_KEY = "content";
	
	private final String CONTENTLIST_KEY = "contentList";
	
	GuestManager manager;
	
	
	public void setGuestManager(GuestManager gm)
	{
		manager = gm;
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
	
	
	

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws ContentNotFoundException, NotOwnerException
	{
		common(req);
		manager.setContentList(req, res, CONTENTLIST_KEY);
		manager.setContent(req, CONTENT_KEY);
		
		saveToken(req);
		return mapping.findForward("fw-index");
	}
	
	

	

	public ActionForward write(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		if( !isTokenValid(req) ) {
			return resultMessage(mapping, req, Properties.FAIL_TOKEN, "./index.do");
		}
		resetToken(req);
		
		DynaActionForm df = (DynaActionForm)form;
		manager.addContent( req, df.getString("content") );
		
		return resultMessage(mapping, req, Properties.SUCCESS_WRITE, "./index.do");
	}
	
	
	
	
	

	public ActionForward modify(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws ContentNotFoundException, NotOwnerException
	{
		if( !isTokenValid(req) ) {
			return resultMessage(mapping, req, Properties.FAIL_TOKEN, "./index.do");
		}
		resetToken(req);
		
		DynaActionForm df = (DynaActionForm)form;
		String content = df.getString("content");
		Long gid = (Long)df.get("gid");

		manager.modifyContent(req, gid, content);
		URLManager url = new GuestURL(req);
		return resultMessage(mapping, req, Properties.SUCCESS_MODIFY, url.getURI("/index") + "#" + String.valueOf(gid) );
	}
	
	
	

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws ContentNotFoundException, NotOwnerException
	{
		manager.delete( req );
		URLManager url = new GuestURL(req);
		return resultMessage(mapping, req, Properties.SUCCESS_REMOVE, url.getURI("/index"));
	}
}
