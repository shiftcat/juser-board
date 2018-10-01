package han.juser.controller.session;

import han.juser.controller.form.Member;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginHandler
{
	
	private static String LOGINMEMBER_CONTEXT_KEY = "han.juser.controller.manager.LoginManager";
	
	
	public LoginHandler()
	{
		super();
	}

	
	/**
	 * ���� ���ǿ��� Member�� ����
	 */
	public Member getMember(HttpServletRequest req)
	{
		HttpSession session = req.getSession();
		synchronized (session) {
			return (Member) session.getAttribute(Member.MEMBER_SESSION_KEY);
		}
	}

	
	/**
	 * �α��� ���� 
	 */
	public boolean isLogin(HttpServletRequest req)
	{
		HttpSession session = req.getSession();
		synchronized(session) {
			if( session.getAttribute(Member.MEMBER_SESSION_KEY) == null ) {
				return false;
			}else {
				return true;
			}
		}
	}
	
	
	
	/**
	 * �α��� ó�� Member�� ���ǿ� �����ϰ� Context�� Member List�� �߰� 
	 */
	public void addLoginMember(HttpServletRequest req, Member member)
	{
		HttpSession session = req.getSession();
		ServletContext context = session.getServletContext();
		session.setAttribute(Member.MEMBER_SESSION_KEY, member);
		synchronized(context) {
			if( context.getAttribute(LOGINMEMBER_CONTEXT_KEY) == null ) {
				List members = new ArrayList();
				members.add(member);
				context.setAttribute(LOGINMEMBER_CONTEXT_KEY, members);
			} else {
				List members = (List)context.getAttribute(LOGINMEMBER_CONTEXT_KEY);
				if(members.indexOf(member) == -1) {
					members.add(member);
				}
			}
		}
	}
	
	

	
	
	/**
	 * �α��� ��
	 */
	public int getLoginMemberCount(HttpServletRequest req)
	{
		HttpSession session = req.getSession();
		ServletContext context = session.getServletContext();
		synchronized(context) {
			List members = (List)context.getAttribute(LOGINMEMBER_CONTEXT_KEY);
			return members.size();
		}
	}
	
	
	
	/**
	 * �α��� ��
	 */
	public int getLoginMemberCount(HttpSession session)
	{
		ServletContext context = session.getServletContext();
		synchronized(context) {
			List members = (List)context.getAttribute(LOGINMEMBER_CONTEXT_KEY);
			return members.size();
		}
	}
	
	
	
	/**
	 * Member����
	 */
	public void removeLoginMember(HttpServletRequest req, Member member)
	{
		HttpSession session = req.getSession();
		ServletContext context = session.getServletContext();
		session.removeAttribute(Member.MEMBER_SESSION_KEY);
		synchronized(context) {
			if( context.getAttribute(LOGINMEMBER_CONTEXT_KEY) != null ) {
				List members = (List)context.getAttribute(LOGINMEMBER_CONTEXT_KEY);
				members.remove(member);
			}
		}
	}
	
	
	
	/**
	 * Member����
	 */
	public void removeLoginMember(HttpSession session, Member member)
	{
		ServletContext context = session.getServletContext();
		session.removeAttribute(Member.MEMBER_SESSION_KEY);
		synchronized(context) {
			if( context.getAttribute(LOGINMEMBER_CONTEXT_KEY) != null ) {
				List members = (List)context.getAttribute(LOGINMEMBER_CONTEXT_KEY);
				members.remove(member);
			}
		}
	}
	
	
	public List getLoginMemberList(HttpServletRequest req)
	{
		ServletContext context = req.getSession().getServletContext();
		synchronized(context) {
			return (List)context.getAttribute(LOGINMEMBER_CONTEXT_KEY);
		}
	}
	
	
	
	
	public synchronized void createTemporary(HttpSession session)
	{
		String temp = session.getServletContext().getRealPath("/temp");
		File f = new File(temp, session.getId());
		f.mkdirs();
	}

	
	
	public synchronized void removeTemporary(HttpSession session)
	{
		String temp = session.getServletContext().getRealPath("/temp");
		File f = new File(temp, session.getId());
		if (f.exists()) {
			File cf[] = f.listFiles();
			for (int i = 0; i < cf.length; i++) {
				cf[i].delete();
			}
			f.delete();
		}
	}
	
	
	
	public void destory()
	{
		
	}
	
	
}
