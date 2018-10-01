package han.juser.dao.spring;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.lob.LobHandler;

public abstract class SpringDAOSupport extends JdbcDaoSupport implements InitializingBean
{
	
	protected LobHandler lobHandler;
	
	
	public SpringDAOSupport()
	{
		super();
	}
	
	
	public void setLobHandler(LobHandler lobHandler)
	{
		this.lobHandler = lobHandler;
	}
	
	
	public abstract void setSqlResource(String arg);
	

}
