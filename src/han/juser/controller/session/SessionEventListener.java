package han.juser.controller.session;

import han.juser.controller.form.Member;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class SessionEventListener implements HttpSessionAttributeListener, HttpSessionListener 
{
	
	private static Log log = LogFactory.getLog(SessionEventListener.class);
	
	
	private static LoginHandler manager = new LoginHandler();
	
	/**
	 * 속성값이 추가될 때
	 */
	public void attributeAdded(HttpSessionBindingEvent event) 
	{
		HttpSession session = event.getSession();
		log.info( "session id " + session.getId() + "속성 추가 ");
		if( event.getValue() instanceof Member )  {
			Member member = (Member)event.getValue();
			manager.createTemporary(session);
			int i = manager.getLoginMemberCount(event.getSession());
			log.info( member.getUserId() + " 로그인" );
			log.info("현재 로그인한 사용자 수" + i);
		}
	}
	
	
	/**
	 * 속성값이 제거될 때
	 */
	public void attributeRemoved(HttpSessionBindingEvent event) 
	{
		HttpSession session = event.getSession();
		if( event.getValue() instanceof Member ) {
			Member member = (Member)event.getValue();
			manager.removeTemporary(session);
			manager.removeLoginMember(event.getSession(), member);
			int i = manager.getLoginMemberCount(event.getSession());
			log.info(member.getUserId() + " 로그아웃 ");
			log.info("현재 로그인한 사용자 수" + i);
		}	
	}
	
	
	/**
	 * 속성값이 변경될 때
	 */
	public void attributeReplaced(HttpSessionBindingEvent event) 
	{
		log.info("세션의 속성변경");
	}
	
	
	
	/**
	 * 세션이 생성 되었을 때
	 */
	public void sessionCreated(HttpSessionEvent event) 
	{
		log.info("세션 생성");
	}

	
	/**
	 * 세션이 무효화 되었을 때
	 */
	public void sessionDestroyed(HttpSessionEvent event) 
	{
		log.info("세션 삭제");
	}

}
