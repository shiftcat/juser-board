package han.juser.dao.core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OraDAOBase 
{
	
	protected static Log log = LogFactory.getLog( OraDAOBase.class );
	
	
	protected DataSource ds;
	
	
	
	public OraDAOBase()
	{
		super();
	}
	
	
	public OraDAOBase(DataSource ds)
	{
		this.ds = ds;
	}
	
	
	public void setDataSource(DataSource ds)
	{
		this.ds = ds;
	}
	
	
	protected void close(Statement stmt)
	{
		if(stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				log.warn(e.getMessage(), e);
			}
		}
	}
	
	protected void close(ResultSet rs)
	{
		if( rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				log.warn(e.getMessage(), e );
			}
		}
	}
	
	protected void close(Connection con)
	{
		if( con != null ) {
			try {
				con.close();
			} catch(SQLException e) {
				log.warn(e.getMessage(), e );
			}
		}
	}
	
}
