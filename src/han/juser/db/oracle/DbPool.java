package han.juser.db.oracle;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import oracle.jdbc.pool.OraclePooledConnection;


public class DbPool
{
	private static final Log log = LogFactory.getLog(DbPool.class);
	
    private static OraclePooledConnection pool;
    
	static {
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("han.juser.db.oracle.oracle");
			String url = bundle.getString("url");
			String user = bundle.getString("user");
			String passwd = bundle.getString("passwd");
			pool = new OraclePooledConnection(url, user, passwd);
			//pool = new OraclePooledConnection("jdbc:oracle:thin:juser/juser@localhost:1521:khe135");
			if(log.isDebugEnabled()) {
				log.debug(url + " 연결 성공 ");
			}
		}catch(Throwable e){
			log.fatal(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}


    private DbPool()
    {
        
    }
    
    public synchronized static Connection getConnection() throws SQLException
    {
        return pool.getConnection();
    }
    
    public static void close() throws SQLException
    {
        pool.close();
    }
}
