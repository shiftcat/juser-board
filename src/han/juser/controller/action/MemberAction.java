package han.juser.controller.action;

import han.juser.controller.form.MemberAccountForm;
import han.juser.controller.form.MemberModifyForm;
import han.juser.controller.manager.MemberManager;
import han.juser.controller.manager.SubMenuItem;
import han.juser.controller.param.MemberParam;
import han.juser.model.CategoryDTO;
import han.juser.service.MemberExistException;
import han.juser.service.MemberNotFoundException;
import han.juser.service.MissMatchPasswordException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.DynaActionForm;




/**
 * 
 * @author Y.Han Lee
 *
 */
public class MemberAction extends ActionBase
{
	
	MemberManager manager;
	
	
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
	
	
	public void setMemberManager(MemberManager mm)
	{
		manager = mm;
	}
	
	
	
	
	public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws MemberNotFoundException, MissMatchPasswordException
	{
		if( manager.getLoginHandler().isLogin(req) ) {
			return mapping.findForward("fw-myinfo");
		} else {
			DynaActionForm df = (DynaActionForm)form;
			ActionErrors errors = df.validate(mapping, req);
			if( errors != null && !errors.isEmpty() ) {
				saveErrors(req, errors);
				return loginForm(mapping, form, req, res);
			}
			
			String userId = df.getString("userId").trim();
			String passwd = df.getString("passwd").trim();
			manager.login(req, userId, passwd);
		}
		
		return resultMessage(mapping, req, Properties.MEMBER_SUCCESS_LOGIN, req.getHeader("referer"));
	}
	
	
	public ActionForward loginForm(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		common(req);
		if ( manager.getLoginHandler().isLogin(req) ) {
			return mapping.findForward("fw-myinfo");
		} else {
			return mapping.findForward("fw-loginForm");
		}
	}
	
	
	
	
	
	public ActionForward logout(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		manager.logout(req);
		return new ActionForward("../index.do", true);
	}
	
	
	
	public ActionForward loginMemberList(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		manager.setLoginMemberList(req, "loginMembers");
		return mapping.findForward("fw-loginMemberList");
	}
	
	
	
	public ActionForward accountForm(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		common(req);
		if( manager.getLoginHandler().isLogin(req) ) {
			return mapping.findForward("fw-myinfo");
		} else {
			return mapping.findForward("fw-accountform");
		}
	}
	
	
	
	public ActionForward myInfo(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		common(req);
		if( manager.getLoginHandler().isLogin(req) ) {
			return mapping.findForward("fw-myinfo");
		} else {
			return mapping.findForward("fw-loginForm");
		}
	}

	
	
	public ActionForward verifyForm(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		common(req);
		if( !manager.getLoginHandler().isLogin(req) ) {
			return mapping.findForward("fw-loginForm");
		}
		
		String mode = MemberParam.getActionMode(req);
		if( mode.equalsIgnoreCase("modify")) {
			req.setAttribute("action", "/frm-modify");
		} else if( mode.equalsIgnoreCase("secede")) {
			req.setAttribute("action", "/frm-really");
		} else {
			return errorMessage(mapping, req, Properties.NOT_VALID_REQUEST);
		}
		
		return mapping.findForward("fw-verifyfrom");
	}
	
	

	public ActionForward modifyForm(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws MemberNotFoundException
	{
		common(req);
		if( !manager.getLoginHandler().isLogin(req)) {
			return mapping.findForward("fw-loginForm");
		}
		manager.setMyInfo(req, "memberInfo");
	
		saveToken(req);
		return mapping.findForward("fw-modifyform");
	}
	
	

	public ActionForward passwdForm(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res)
	{
		common(req);
		if( !manager.getLoginHandler().isLogin(req) ) {
			return mapping.findForward("fw-loginForm");
		}
		
		saveToken(req);
		return  mapping.findForward("fw-newPasswdForm");
	}
	
	

	public ActionForward reallyForm(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws MemberNotFoundException, MissMatchPasswordException
	{
		DynaActionForm df = (DynaActionForm)form;
		String userId = df.getString("userId").trim();
		String passwd = df.getString("passwd").trim();
		
		common(req);
		if( !manager.getLoginHandler().isLogin(req) ) {
			return mapping.findForward("fw-loginForm");
		}
		manager.checkSessionMember(req, userId, passwd);
		
		saveToken(req);
		return mapping.findForward("fw-reallyform");
	}
	

	

	public ActionForward account(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws MemberExistException, MemberNotFoundException 
	{
		if(!isTokenValid(req)) {
			ActionErrors errors = new ActionErrors();
			errors.add("changePasswd", new ActionMessage( Properties.FAIL_TOKEN ));
			saveErrors(req, errors);
			return mapping.findForward("fw-error");
		}
		resetToken(req);
		manager.account(req, (MemberAccountForm)form);
	
		return resultMessage(mapping, req, Properties.MEMBER_SUCCESS_ACCOUNT, "./frm-account.do");
	}
	
	
	

	public ActionForward modify(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws MemberNotFoundException
	{
		if( !manager.getLoginHandler().isLogin(req) ) {
			return mapping.findForward("fw-loginForm");
		}
		manager.modify(req, (MemberModifyForm)form);
		
		return resultMessage(mapping, req, Properties.MEMBER_SUCCESS_MODIFY, "./frm-account.do");
	}
	
	

	public ActionForward changePasswd(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws MemberNotFoundException, MissMatchPasswordException
	{	
		DynaActionForm df = (DynaActionForm)form;
		String oldPasswd = df.getString("currentPasswd").trim();
		String newPasswd = df.getString("newPasswd").trim();
		
		if( !manager.getLoginHandler().isLogin(req) ) {
			return mapping.findForward("fw-loginForm");
		}
		manager.changePassword(req, oldPasswd, newPasswd);
		
		return resultMessage(mapping, req, Properties.MEMBER_SUCCESS_MODIFY, "./frm-account.do");
	}

	

	public ActionForward secede(ActionMapping mapping, ActionForm form, HttpServletRequest req, HttpServletResponse res) throws MemberNotFoundException 
	{
		if ( !isTokenValid(req) ) {
			ActionErrors errors = new ActionErrors();
			errors.add("changePasswd", new ActionMessage( Properties.FAIL_TOKEN ));
			saveErrors(req, errors);
			return mapping.findForward("fw-error");
		}
		resetToken(req);
		manager.secede(req);

		return resultMessage(mapping, req, Properties.MEMBER_SUCCESS_SECEDE, "./frm-account.do");
	}
	
	
	

	public ActionForward checkId(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		DynaActionForm df = (DynaActionForm)form;
		String userId = df.getString("userId");
		manager.checkId(request, userId);
		
		return mapping.findForward("fw-idCheckResult");
	}

}
