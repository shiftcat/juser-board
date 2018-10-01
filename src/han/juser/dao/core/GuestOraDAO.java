package han.juser.dao.core;

import han.juser.dao.DAOException;
import han.juser.dao.GuestDAO;
import han.juser.model.GuestDTO;
import han.juser.model.SummaryDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;




/**
 * 
 * @author Administrator
 *
 */
public class GuestOraDAO extends OraDAOBase implements GuestDAO
{
	
	private static volatile long id = -1;
	
	private static ResourceBundle sql;
	
	public GuestOraDAO()
	{
		try {
			sql = ResourceBundle.getBundle("han.juser.dao.core.GuestSQL");
		} catch(Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	
	public GuestDTO getContent(long gid) 
	{
		GuestDTO dto = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql.getString("guest.oracle.Content"));
			pstmt.setLong(1, gid);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto = new GuestDTO();
				dto.setGid( rs.getLong("GID"));
				dto.setUserId(rs.getString("USERID"));
				dto.setContent( rs.getString("CONTENT"));
				dto.setLogDate(rs.getTimestamp("LOGDATE"));
				dto.setRemoteAddr(rs.getString("REMOTEADDR"));
			}
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally{
			close(rs);
			close(pstmt);
			if(con != null) try{ con.close(); } catch(SQLException e){}
		}
		return dto;
	}
	
	
	
	
	public List getContentList(long start, int limit)
	{
		List list = new ArrayList();
		
		Connection con = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql.getString("guest.oracle.ContentList"));
			pstmt.setLong(1, start );
			pstmt.setInt(2, limit );
			rs = pstmt.executeQuery();
			while(rs.next()) {
				GuestDTO gbo = new GuestDTO();
				gbo.setGid( rs.getLong("GID") ); //1
				gbo.setUserId( rs.getString("USERID") ); //2
				gbo.setContent( rs.getString("CONTENT") ); //3
				gbo.setLogDate( rs.getTimestamp("LOGDATE") ); //4
				gbo.setRemoteAddr( rs.getString("REMOTEADDR") ); //5
				list.add(gbo);
			}
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally {
			close(rs);
			close(stmt);
			close(pstmt);
			if(con != null) try{ con.close(); } catch(SQLException e){}
		}
		return list;

	}
	
	
	
	public List getContentList(String userId, long start, int limit)
	{
		List list = new ArrayList();
		
		Connection con = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql.getString("guest.oracle.user.ContentList"));
			pstmt.setString(1, userId);
			pstmt.setString(2, userId);
			pstmt.setLong(3, start );
			pstmt.setInt(4, limit );
			rs = pstmt.executeQuery();
			while(rs.next()) {
				GuestDTO gbo = new GuestDTO();
				gbo.setGid( rs.getLong("GID") ); //1
				gbo.setUserId( rs.getString("USERID") ); //2
				gbo.setContent( rs.getString("CONTENT") ); //3
				gbo.setLogDate( rs.getTimestamp("LOGDATE") ); //4
				gbo.setRemoteAddr( rs.getString("REMOTEADDR") ); //5
				list.add(gbo);
			}
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally {
			close(rs);
			close(stmt);
			close(pstmt);
			if(con != null) try{ con.close(); } catch(SQLException e){}
		}
		return list;

	}
	
	
	private static void setId(Connection con) throws DAOException 
	{
		Statement stmt = null;
		ResultSet rs = null;
		long maxId = -1;

		try{
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql.getString("guest.oracle.MaxId"));
			if( rs.next() ){
				maxId = rs.getLong(1);
			}
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally{
			if( rs != null ) try { rs.close(); } catch(SQLException e) {}
			if( stmt != null ) try { stmt.close(); } catch( SQLException e) {}
		}
		id = maxId;
	}
	
	
	
	
	private static synchronized long getNextId(Connection con) throws DAOException
	{
		if( id == -1) {
			setId(con);
		}
		return ++id;
	}
	
	
	
	public long insert(String userId, String content, String remoteAddr)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		
		int irs = -1;
		int i = 1;
		long maxId = -1;
		try{
			con = ds.getConnection();
			maxId = getNextId(con);
			pstmt = con.prepareStatement(sql.getString("guest.oracle.Insert"));
			pstmt.setLong(i++, ++maxId); //1
			pstmt.setString(i++, userId ); //2
			pstmt.setString(i++, content ); //3
			pstmt.setString(i++, remoteAddr ); //4
			irs = pstmt.executeUpdate();
			if(irs != 1) {
				if(log.isDebugEnabled()) {
					log.debug("방명록 인서트 실패");
				}
				return -1;
			}
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally{
			close(pstmt);
			if(con != null) try{ con.close(); } catch(SQLException e){}
		}
		return maxId;
	}
	
	
	
	/*
	public long insert(Connection con, String userId, String content, String remoteAddr) throws DAOException
	{
		HibernateUtil.beginTransaction();
		try {
			Session session = HibernateUtil.getCurrentSession();
			GuestDTO dto = new GuestDTO();
			dto.setUserId(userId);
			dto.setContent(content);
			dto.setRemoteAddr(remoteAddr);
			session.save(dto);
		} catch (Throwable e) {
			throw new DAOException(e.getMessage(), e);
		}
		HibernateUtil.commitTransaction();
		return 1;
	}
	*/
	
	
	public int delete(long gid)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int irs = -1;

		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql.getString("guest.oracle.Delete"));
			pstmt.setLong(1, gid);
			irs = pstmt.executeUpdate();
			if(irs != 1) {
				if(log.isDebugEnabled()) {
					log.debug("방명록 삭제 실패");
				}
			}
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally{
			close(pstmt);
			if(con != null) try{ con.close(); } catch(SQLException e){}
		}
		return irs;
	}
	
	
	
	public int update(long gid, String content, String remoteAddr)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int irs = -1;
		int i = 1;
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql.getString("guest.oracle.Update"));
			pstmt.setString(i++, content );
			pstmt.setString(i++, remoteAddr );
			pstmt.setLong(i++, gid );
			irs = pstmt.executeUpdate();
			if(irs != 1) {
				if(log.isDebugEnabled()) {
					log.debug("방명록 변경 실패");
				}
			}
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally {
			close(pstmt);
			if(con != null) try{ con.close(); } catch(SQLException e){}
		}
		
		return irs;
	}


	
	
	
	public long getRowCount() 
	{
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		long irs = -1;
		try{
			con = ds.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql.getString("guest.oracle.RowCount"));
			if( rs.next() ) {
				irs = rs.getLong(1);
			}
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			close(rs);
			close(stmt);
			if(con != null) try{ con.close(); } catch(SQLException e){}
		}
		
		return irs;
	}
	
	
	
	
	
	public long getRowCount(String userId) 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long irs = -1;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql.getString("guest.oracle.user.RowCount"));
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			if( rs.next() ) {
				irs = rs.getLong(1);
			}
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			close(rs);
			close(pstmt);
			if(con != null) try{ con.close(); } catch(SQLException e){}
		}
		
		return irs;
	}



	public List getSummary(int limit) 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List summary = new ArrayList();
		String sql = "SELECT /*+ index_desc(guest pk_guest_gid)*/ rownum rn, gid, content, logdate FROM guest WHERE rownum <= ?";
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, limit);
			rs = pstmt.executeQuery();
			while (rs.next() ) {
				SummaryDTO dto = new SummaryDTO();
				dto.setContentId( rs.getLong("GID") );
				dto.setContent( rs.getString("CONTENT") );
				dto.setSignDate( rs.getTimestamp("LOGDATE") );
				summary.add(dto);
			}
			return summary;
		} catch (Throwable e) {
			throw new DAOException(e.getMessage(), e);
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}
	}
	
}
