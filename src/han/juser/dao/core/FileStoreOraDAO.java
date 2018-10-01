package han.juser.dao.core;

import han.juser.dao.DAOException;
import han.juser.dao.FileStoreDAO;
import han.juser.model.FileDTO;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * 
 * @author Y.Han Lee
 *	BinaryData를 데이터베이스 저장하거나 가저오는 클래스
 */
public class FileStoreOraDAO extends OraDAOBase implements FileStoreDAO 
{
	
	public FileDTO getFile(String tableName, long seq) 
	{
		String sql = "SELECT table_name, seq, file_name, up_file, DBMS_LOB.GETLENGTH(up_file) AS size FROM file_store WHERE table_name = ? AND seq = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		FileDTO dto = null;
		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, tableName);
			pstmt.setLong(2, seq);
			rs = pstmt.executeQuery();
			if( rs.next() ) {
				dto = new FileDTO();
				dto.setTableName(rs.getString(1));
				dto.setSeq(rs.getLong(2));
				dto.setFileName(rs.getString(3));
				dto.setBinaryStream(rs.getBinaryStream(4), rs.getInt(5));
			}
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException e) {
			throw new DAOException(e.getMessage(), e);
		} finally {
			close(rs);
			close(pstmt);
			close(con);
		}
		return dto;
	}

	
	
	public int saveFile(String tableName, long seq, String fileName, InputStream is, int size)
	{
		String sql = "INSERT INTO file_store(table_name, seq, file_name, up_file) "
			+ "VALUES(?, ?, ?, ?)";
		Connection con = null;
		PreparedStatement pstmt = null;
		int i = -1;
		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, tableName);
			pstmt.setLong(2, seq);
			pstmt.setString(3, fileName);
			pstmt.setBinaryStream(4, is, size);
			i = pstmt.executeUpdate();
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException e) {
			throw new DAOException(e.getMessage(), e);
		} finally {
			close(pstmt);
			close(con);
		}
		
		return i;
	}
	
	
	
	public int updateFile(String tableName, long seq, String fileName, InputStream is, int size)
	{
		String sql = "UPDATE file_store SET file_name = ?, up_file = ? WHERE table_name = ? AND seq = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		int i = -1;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, fileName);
			pstmt.setBinaryStream(2, is, size);
			pstmt.setString(3, tableName);
			pstmt.setLong(4, seq);
			i = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e.getMessage(), e);
		} finally {
			close(pstmt);
			close(con);
		}
		
		return i;
	}
	
	
	public int deleteFile(String tableName, long seq)
	{
		String sql = "DELETE FROM file_store WHERE table_name = ? AND seq = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		int i = -1;
		try{
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, tableName);
			pstmt.setLong(2, seq);
			i = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e.getMessage(), e);
		}finally {
			close(pstmt);
			close(con);
		}
		
		return i;
	}
}
