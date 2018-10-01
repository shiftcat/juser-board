package han.juser.dao.core;

import han.juser.dao.CategoryDAO;
import han.juser.dao.DAOException;
import han.juser.model.CategoryDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryOraDAO extends OraDAOBase implements CategoryDAO 
{
	
	private static volatile int cateId = -1;
	
	
	public CategoryOraDAO()
	{
		super();
	}
	
	
	public int deleteCategory(int cateId) 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM category WHERE cateid = ?";
		
		int irs = -1;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cateId);
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

	public List getCategoryList() 
	{
		Connection con = null;
		List cate = new ArrayList();
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM category ORDER BY ord ASC";
		
		try {
			con = ds.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				CategoryDTO dto = new CategoryDTO();
				dto.setCateId( rs.getInt("CATEID") );
				dto.setCateName( rs.getString("CATENAME") );
				dto.setOrd( rs.getInt("ORD") );
				cate.add(dto);
			}
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			close(stmt);
			close(rs);
			close(con);
		}
		
		return cate;
	}

	private static synchronized int setId(Connection con) throws DAOException 
	{
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT max(cateid) FROM category";
		
		int irs = -1;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				irs = rs.getInt(1);
			}
			cateId = irs;
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			if( rs != null ) try { rs.close(); } catch (SQLException e) {}
			if( stmt != null ) try { stmt.close(); } catch (SQLException e){}
		}
		
		return irs;
	}
	
	
	private static synchronized int getNextId(Connection con) throws DAOException
	{
		if( cateId == -1 ) {
			setId(con);
		}
		return ++cateId;
	}
	
	
	public int insertCategory(String cateName, int ord) 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO category(cateid, catename, ord) VALUES(?, ?, ?)";
		
		int irs = -1;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, getNextId(con));
			pstmt.setString(2, cateName);
			pstmt.setInt(3, ord);
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

	public int updateCategory(int cateId, String cateName, int ord) 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "UPDATE category SET catename = ?, ord = ? WHERE cateid = ?";
		
		int irs = -1;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, cateName);
			pstmt.setInt(2, ord);
			pstmt.setInt(3, cateId);
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


	public CategoryDTO getCategory(int cateId) 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CategoryDTO dto = null;
		String sql = "SELECT cateid, catename, ord FROM category WHERE cateid = ?";
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, cateId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto = new CategoryDTO();
				dto.setCateId(cateId);
				dto.setCateName( rs.getString("CATENAME") );
				dto.setOrd( rs.getInt("ORD") );
			}
			return dto;
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}
	}
	
	
}
