package han.juser.controller.manager;

import han.juser.controller.form.Member;
import han.juser.controller.form.MemberAccountForm;
import han.juser.controller.form.MemberModifyForm;
import han.juser.controller.param.MemberParam;
import han.juser.model.MemberDTO;
import han.juser.service.MemberExistException;
import han.juser.service.MemberNotFoundException;
import han.juser.service.MemberService;
import han.juser.service.MissMatchPasswordException;
import han.juser.url.PageBean;

import java.util.List;

import javax.servlet.http.HttpServletRequest;


/*
 * 
 */
public class MemberManager extends ManagerBase
{
	MemberService service;
	
	
	public MemberManager()
	{
		super();
	}
	
	
	public void setService(MemberService service)
	{
		this.service = service;
	}
	
	
	
	
	public boolean checkId( HttpServletRequest req, String userId )
	{
		return service.checkMember( userId); 
	}
	
	
	
	
	public boolean account(HttpServletRequest req, MemberAccountForm form) throws MemberExistException, MemberNotFoundException 
	{
		service.insert( form.getUserId(), form.getPasswd(), form.getUserName(),
					form.getJuminNumber(), form.getEmail(), form.getPhoneNumber(), form.getMobileNumber() );
		
		loginHandler.addLoginMember(req, new Member(service.getMember(form.getUserId())));
		//MemberDTO dto = null;
		//dto = service.getMember( form.getUserId());
		return true;
	}
	
	
	
	
	
	
	public int modify(HttpServletRequest req, MemberModifyForm form) throws MemberNotFoundException 
	{
		// 세션에 변경된 정보 반영 추갈할 것 
		return service.update(form.getUserName(), form.getEmail(), 
					form.getPhoneNumber(), form.getMobileNumber() ,loginHandler.getMember(req).getUserId() );
	}
	
	
	
	
	
	public int changePassword(HttpServletRequest req, String oldPasswd, String newPasswd) throws MemberNotFoundException, MissMatchPasswordException
	{
		return service.changePasswd( loginHandler.getMember(req).getUserId(), oldPasswd, newPasswd);
	}
	
	
	
	
	
	public int secede(HttpServletRequest req) throws MemberNotFoundException
	{
		service.setLevel( loginHandler.getMember(req).getUserId(), 0);
		loginHandler.removeLoginMember(req, loginHandler.getMember(req) );
		return 1;
	}
	
	
	
	
	
	
	public void setMyInfo(HttpServletRequest req, String key) throws MemberNotFoundException
	{
		Member member = super.loginHandler.getMember(req);
		MemberDTO dto = service.getMember(member.getUserId());
		req.setAttribute(key, new Member(dto));
	}
	
	
	public void setMemberInfo(HttpServletRequest req, String key) throws MemberNotFoundException
	{
		if( !role.isAdmin(req) ) {
			return;
		}
		MemberDTO dto = service.getMember(MemberParam.getUserId(req) );
		
		req.setAttribute(key, dto);
	}
	
	
	public boolean checkSessionMember(HttpServletRequest req, String userId, String passwd) throws MemberNotFoundException, MissMatchPasswordException
	{
		Member member = super.loginHandler.getMember(req);
		service.getMember(member.getUserId(), passwd);
		return true;
	}
	
	
	
	
	
	public void setMemberList(HttpServletRequest req, String key)
	{
		if( !role.isAdmin(req) ) {
			//return;
		}
		
		List list = service.getMemberList(  MemberParam.getPageNumber(req) );

		req.setAttribute(key, list );
		req.setAttribute( PageBean.PAGEBEAN_KEY , service.getPageBean() );
	}
	
	
	
	public void setLoginMemberList(HttpServletRequest req, String key)
	{
		List list = loginHandler.getLoginMemberList(req);
		req.setAttribute(key, list );
	}

	
	
	
	public void changeLevel(HttpServletRequest req, String userId, int level) throws  MemberNotFoundException
	{
		if( !role.isAdmin(req) ) {
			return;
		}
		
		service.setLevel( userId, level);
	}
	
	
	
	public boolean login(HttpServletRequest req, String userId, String passwd) throws MemberNotFoundException, MissMatchPasswordException
	{

		MemberDTO dto = service.getMember(userId, passwd);
		Member member = new Member(dto);
		loginHandler.addLoginMember(req, member);
		service.setLastLogin(userId);
		return false;
	}

	
	
	public void logout(HttpServletRequest req)
	{
		loginHandler.removeLoginMember(req, loginHandler.getMember(req));
	}
	
}
