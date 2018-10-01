package han.juser.db.oracle;


import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletRequest;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ConnectionManager
{
	private static String CONNECTION_KEY = "han.juser.db.oracle.ConnectionManager";
	
	private static DataSource ds;
	
	private static final Log log = LogFactory.getLog(ConnectionManager.class);
	
	static{
		try{
			Context initContext = new InitialContext();
			
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/juser");
			
			//ds = (DataSource)initContext.lookup("java:/comp/env/jdbc/juser");
			if(log.isDebugEnabled()) {
				log.debug(" !! lookup success !!");
			}
		}catch(Throwable e) {
			log.fatal(e.getMessage(), e);
		}
	}
	
	
	private ConnectionManager()
	{
		
	}
	
	
	
	public static Connection getConnection() throws SQLException
	{
		return ds.getConnection();
	}
	
	
	
	public static void setConnection(ServletRequest req) throws SQLException
	{
		req.setAttribute(CONNECTION_KEY, ds.getConnection());
		//req.setAttribute(CONNECTION_KEY, DbPool.getConnection());
		if(log.isDebugEnabled()) {
			log.debug(req.getRemoteAddr() + "에 Connection 할당");
		}
	}
	
	
	public static Connection getConnection(ServletRequest req)
	{
		Connection con = (Connection)req.getAttribute(CONNECTION_KEY);
		if(con == null) {
			throw new RuntimeException("데이터베이스에 연결되어 있지 않습니다.");
		}
		return con;
	}
	
	
	public static void releaseConnection(ServletRequest req)
	{
		Connection con = getConnection(req);
		if(con != null ) {
			try {
				con.close();
				if(log.isDebugEnabled()) {
					log.debug("Connection 닫힘");
				}
			}catch (SQLException e) {
				log.warn(e.getMessage(), e);
			}
			req.removeAttribute(CONNECTION_KEY);
			if(log.isDebugEnabled()) {
				log.debug(req.getRemoteAddr() + "에서 Connection 제거됨");
			}
		}else {
			log.warn("Connection이 널임");
		}
	}
};