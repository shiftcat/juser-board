package han.juser.controller.action;

import han.juser.controller.manager.MemberManager;
import han.juser.controller.manager.SubMenuItem;
import han.juser.service.MemberNotFoundException;
import han.juser.url.MemberURL;
import han.juser.url.URLManager;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class AdminAction  extends ActionBase
{
	MemberManager manager;
	
	
	public void setMemberManager(MemberManager mm)
	{
		manager = mm;
	}

	
	
	public void setSubMenu(HttpServletRequest req, String key) 
	{
		List sub = new ArrayList();
		sub.add( new SubMenuItem("회원관리", req.getContextPath() + "/admin/memberList.do"));
		sub.add( new SubMenuItem("글분류설정", req.getContextPath() + "/admin/category.do"));
		req.setAttribute(key, sub);
	}
	
	
	
	public ActionForward memberList(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		if( !manager.getRoleManager().isAdmin(req) ) {
			// TODO
			return mapping.findForward("fw-error");
		}
		common(req);
		manager.setMemberList(req, "memberList");
		
		return mapping.findForward("fw-memberList");
	}
	
	
	
	
	
	
	public ActionForward memberInfo(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws MemberNotFoundException
	{
		if( !manager.getRoleManager().isAdmin(req) ) {
			// TODO
			return mapping.findForward("fw-error");
		}
		
		common(req);
		manager.setMemberInfo(req, "memberInfo" );
		
		return mapping.findForward("fw-memberInfo");
	}
	
	
	
	public ActionForward changeLevel(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse response) throws MemberNotFoundException
	{
		DynaActionForm df = (DynaActionForm)form;
		String strLev = df.getString("level");
		String userId = df.getString("userId");
		
		int level = Integer.valueOf(strLev);
		manager.changeLevel(req, userId, level);
		
		URLManager urlManager = new MemberURL(req);
		String url = urlManager.getURI("/memberInfo");
		
		return resultMessage(mapping, req, Properties.MEMBER_SUCCESS_MODIFY, url);
	}
	
	
	public ActionForward category(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		common(req);
		categoryManager.setCategorys(req, "categoryList");
		String[] values = req.getParameterValues("to");
		if( values != null ) {
			if( !isTokenValid(req)) {
				return new ActionForward("./category.do", true);
			}
			resetToken(req);
			categoryManager.computeCategory(req, values);
			return new ActionForward("./category.do", true);
		}
		
		saveToken(req);
		return mapping.findForward("fw-category");
	}




	

}