package han.juser.dao.core;

import han.juser.dao.DAOException;
import han.juser.dao.MemberDAO;
import han.juser.model.MemberDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


/**
 * 
 * @author Y.Han Lee
 *
 */
public class MemberOraDAO extends OraDAOBase implements MemberDAO
{
	
	private ResourceBundle sql;
	
	
	public MemberOraDAO()
	{
		try {
			sql = ResourceBundle.getBundle("han.juser.dao.core.MemberSQL");
		} catch (Exception e) {
			log.fatal(e.getMessage(), e);
		}
	}
	

	
	
	public MemberDTO getMember(String userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberDTO mb = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql.getString("member.oracle.Member"));
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				mb = new MemberDTO();
				mb.setUserId( rs.getString("USERID") );
				mb.setPasswd( rs.getString("PASSWD") );
				mb.setEmail( rs.getString("EMAIL") );
				mb.setJuminNumber( rs.getString("JUMIN") );
				mb.setUserName( rs.getString("USERNAME") );
				mb.setPhoneNumber( rs.getString("PHONE") );
				mb.setMobileNumber( rs.getString("MOBILE") );
				mb.setLastLogin(rs.getTimestamp("LASTLOGIN"));
				mb.setLevel(rs.getInt("LEV"));
				mb.setLogDate(rs.getTimestamp("LOGDATE"));
			}
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally {
			close(rs);
			close(pstmt);
			close(con);
		}
		
		return mb;
	}
	
	
	
	public int insert(String userId, String password, String userName, String juminNumber, 
			String email, String phoneNumber, String mobileNumber)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int irs = -1;
		try {
			con = ds.getConnection();
			int i = 1;
			pstmt = con.prepareStatement(sql.getString("member.oracle.Insert"));
			pstmt.setString(i++, userId );
			pstmt.setString(i++, password );
			pstmt.setString(i++, userName );
			pstmt.setString(i++, juminNumber );
			pstmt.setString(i++, email );
			pstmt.setString(i++, phoneNumber );
			pstmt.setString(i++, mobileNumber );
			irs = pstmt.executeUpdate();
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally {
			close(pstmt);
			close(con);
		}
		
		return irs;
	}
	
	
	
	public int update(String userName, String email, String phoneNumber, String mobileNumber, 
			String userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int irs = -1;
		try{
			con = ds.getConnection();
			int i = 1;
			pstmt = con.prepareStatement(sql.getString("member.oracle.Update"));
			pstmt.setString(i++, userName );
			pstmt.setString(i++, email );
			pstmt.setString(i++, phoneNumber );
			pstmt.setString(i++, mobileNumber );
			pstmt.setString(i++, userId );
			irs = pstmt.executeUpdate();
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally {
			close(pstmt);
			close(con);
		}
		return irs;
	}
	
	
	
	public int changePasswd(String userId, String newPasswd)
	{
		int irs = -1;
		Connection con = null;
		PreparedStatement pstmt = null;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql.getString("member.oracle.ChangePassword"));
			pstmt.setString(1, newPasswd.trim());
			pstmt.setString(2, userId.trim());
			irs = pstmt.executeUpdate();
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally{
			close(pstmt);
			close(con);
		}
		return irs;
	}
	

	
	
	
	
	public int setLevel(String userId, int lev)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int irs = -1;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql.getString("member.oracle.ChangeLevel"));
			pstmt.setInt(1, lev);
			pstmt.setString(2, userId);
			irs = pstmt.executeUpdate();
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally {
			close(pstmt);
			close(con);
		}
		return irs;
	}
	
	
	
	
	public int setLastLogin(String userId)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int irs = -1;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql.getString("member.oracle.SetLastLogin"));
			pstmt.setString(1, userId);
			irs = pstmt.executeUpdate();
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally {
			close(pstmt);
			close(con);
		}
		
		return irs;
	}
	
	
	
	public List getMemberList(long start, int limit)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List list = new ArrayList();
		long num = -1;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql.getString("member.oracle.MemberList"));
			pstmt.setLong(1, start );
			pstmt.setLong(2, limit+1 );
			rs = pstmt.executeQuery();
			num = getRowCount() - --start;
			while(rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setUserId( rs.getString("USERID"));
				dto.setUserName( rs.getString("USERNAME"));
				dto.setLevel( rs.getInt("LEV"));
				dto.setLogDate( rs.getTimestamp("LOGDATE"));
				dto.setEmail(rs.getString("EMAIL"));
				dto.setNum(num--);
				list.add(dto);
			}
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally{
			close(rs);
			close(pstmt);
			close(con);
		}
		
		return list;
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
			rs = stmt.executeQuery(sql.getString("member.oracle.RowCount"));
			if(rs.next()) {
				irs = rs.getLong(1);
			}
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		}finally{
			close(rs);
			close(stmt);
			close(con);
		}
		return irs;
	}



	public int delete(String userId) 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		int irs = -1;
		
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql.getString("member.oracle.Delete"));
			pstmt.setString(1, userId);
			irs = pstmt.executeUpdate();
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			close(pstmt);
			close(con);
		}
		
		return irs;
	}
	
}
