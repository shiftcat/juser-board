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
	 * �Ӽ����� �߰��� ��
	 */
	public void attributeAdded(HttpSessionBindingEvent event) 
	{
		HttpSession session = event.getSession();
		log.info( "session id " + session.getId() + "�Ӽ� �߰� ");
		if( event.getValue() instanceof Member )  {
			Member member = (Member)event.getValue();
			manager.createTemporary(session);
			int i = manager.getLoginMemberCount(event.getSession());
			log.info( member.getUserId() + " �α���" );
			log.info("���� �α����� ����� ��" + i);
		}
	}
	
	
	/**
	 * �Ӽ����� ���ŵ� ��
	 */
	public void attributeRemoved(HttpSessionBindingEvent event) 
	{
		HttpSession session = event.getSession();
		if( event.getValue() instanceof Member ) {
			Member member = (Member)event.getValue();
			manager.removeTemporary(session);
			manager.removeLoginMember(event.getSession(), member);
			int i = manager.getLoginMemberCount(event.getSession());
			log.info(member.getUserId() + " �α׾ƿ� ");
			log.info("���� �α����� ����� ��" + i);
		}	
	}
	
	
	/**
	 * �Ӽ����� ����� ��
	 */
	public void attributeReplaced(HttpSessionBindingEvent event) 
	{
		log.info("������ �Ӽ�����");
	}
	
	
	
	/**
	 * ������ ���� �Ǿ��� ��
	 */
	public void sessionCreated(HttpSessionEvent event) 
	{
		log.info("���� ����");
	}

	
	/**
	 * ������ ��ȿȭ �Ǿ��� ��
	 */
	public void sessionDestroyed(HttpSessionEvent event) 
	{
		log.info("���� ����");
	}

}
