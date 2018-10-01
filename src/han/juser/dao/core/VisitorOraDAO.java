package han.juser.dao.core;

import han.juser.dao.DAOException;
import han.juser.dao.VisitorDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

public class VisitorOraDAO extends OraDAOBase implements VisitorDAO 
{
	private static volatile long id = -1;
	
	public VisitorOraDAO()
	{
		super();
	}
	
	
	private static synchronized void setId(Connection con) throws DAOException
	{
		String sql = "SELECT max(idx) FROM visitor";
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			if( rs.next() ) {
				id = rs.getLong(1);
			}
		} catch ( Throwable e) {
			throw new DAOException(e.getMessage(), e);
		}
	}
	
	
	private static synchronized long getNextId(Connection con) throws DAOException
	{
		if( id == -1) {
			setId(con);
		}
		return ++id;
	}
	
	
	
	public long getRowCount()
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT count(idx) FROM visitor";
		long cnt = -1;
		try {
			con = ds.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			if( rs.next() ) {
				cnt = rs.getLong(1);
			}
			return cnt;
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			close(rs);
			close(stmt);
			close(con);
		}
	}
	
	
	public long getRowCount(java.util.Date date)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select count(idx) from visitor where signdate < ?+1"
                                     + "and signdate >= ?";
		
		try {
			java.util.Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			java.sql.Timestamp t = new java.sql.Timestamp(c.getTimeInMillis());
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setTimestamp(1, t );
			pstmt.setTimestamp(2, t );
			rs = pstmt.executeQuery();
			if( rs.next() ) {
				 return rs.getLong(1);
			}
			return -1;
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}
	}
	
	
	
	public int delete(long idx) 
	{
		
		return 0;
	}

	public long insert(String userAgent, String remoteAddr,
			String remoteHost, String referer) 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO visitor(idx, userAgent, remoteAddr, remoteHost, referer) VALUES( ?, ?, ?, ?, ?)";
		long nextId = -1;
		try {
			con = ds.getConnection();
			nextId = getNextId(con);
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, nextId);
			pstmt.setString(2, userAgent);
			pstmt.setString(3, remoteAddr);
			pstmt.setString(4, remoteHost);
			pstmt.setString(5, referer);
			if( pstmt.executeUpdate() == 1) {
				return nextId;
			}
		} catch (Throwable e) {
			throw new DAOException(e.getMessage(), e);
		} finally {
			close(pstmt);
			close(con);
		}
		
		return nextId;
	}




}
