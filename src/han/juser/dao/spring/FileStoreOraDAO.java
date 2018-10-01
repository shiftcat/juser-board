package han.juser.dao.spring;

import han.juser.dao.FileStoreDAO;
import han.juser.model.FileDTO;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ResourceBundle;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.SqlLobValue;

public class FileStoreOraDAO extends SpringDAOSupport implements FileStoreDAO  
{
	
	protected static ResourceBundle sql;
	
	public FileStoreOraDAO()
	{
		super();
	}
	
	
	public void setSqlResource(String arg)
	{
		try {
			sql = ResourceBundle.getBundle(arg);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public int deleteFile(String tableName, long seq) 
	{
		String sql = "DELETE FROM file_store WHERE table_name = ? AND seq = ?";
		return getJdbcTemplate().update(sql, new Object[]{tableName, new Long(seq)});
	}

	
	
	public FileDTO getFile(String tableName, long seq) 
	{
		String sql = "SELECT table_name, seq, file_name, up_file, DBMS_LOB.GETLENGTH(up_file) AS size FROM file_store WHERE table_name = ? AND seq = ?";
		return (FileDTO)getJdbcTemplate().query(sql, 
				new Object[]{tableName, new Long(seq)}, 
				new ResultSetExtractor()
				{
					public Object extractData(ResultSet rs) throws SQLException
					{
						FileDTO dto = null;
						if( rs.next() ) {
							dto = new FileDTO();
							dto.setTableName(rs.getString(1));
							dto.setSeq(rs.getLong(2));
							dto.setFileName(rs.getString(3));
							dto.setBinaryStream(rs.getBinaryStream(4), rs.getInt(5));
						}
						return dto;
					}
				}
		);
	}

	public int saveFile(String tableName, long seq, String fileName, InputStream is, int size) 
	{
		String sql = "INSERT INTO file_store(table_name, seq, file_name, up_file) VALUES(?, ?, ?, ?)";
		
		return getJdbcTemplate().update(sql, 
				new Object[]{tableName, new Long(seq), fileName, new SqlLobValue(is, size, lobHandler)},
				new int[]{Types.VARCHAR, Types.BIGINT, Types.VARCHAR, Types.BLOB });
	}

	public int updateFile(String tableName, long seq, String fileName, InputStream is, int size) 
	{
		String sql = "UPDATE file_store SET file_name = ?, up_file = ? WHERE table_name = ? AND seq = ?";
		
		return getJdbcTemplate().update(sql,
				new Object[]{fileName, new SqlLobValue(is, size, lobHandler), tableName, new Long(seq)},
				new int[]{Types.VARCHAR, Types.BLOB, Types.VARCHAR, Types.BIGINT});
	}

}
