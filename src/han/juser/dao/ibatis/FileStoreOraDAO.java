package han.juser.dao.ibatis;

import han.juser.dao.FileStoreDAO;
import han.juser.model.FileDTO;

import java.io.InputStream;

public class FileStoreOraDAO extends SqlMapBaseDAO implements FileStoreDAO
{

	public int deleteFile(String tableName, long seq) 
	{
		FileDTO dto = new FileDTO();
		dto.setTableName(tableName);
		dto.setSeq(seq);
		return getSqlMapClientTemplate().delete("File.deleteFile", dto);
	}

	public FileDTO getFile(String tableName, long seq) 
	{
		FileDTO dto = new FileDTO();
		dto.setTableName(tableName);
		dto.setSeq(seq);
		return (FileDTO)getSqlMapClientTemplate().queryForObject("File.getFile", dto);
	}

	public int saveFile(String tableName, long seq, String fileName, InputStream is, int size) 
	{
		FileDTO dto = new FileDTO();
		dto.setTableName(tableName);
		dto.setSeq(seq);
		dto.setFileName(fileName);
		dto.setBinaryStream(is, size);
		getSqlMapClientTemplate().insert("File.saveFile", dto);
		return 1;
	}

	public int updateFile(String tableName, long seq, String fileName, InputStream is, int size) 
	{
		FileDTO dto = new FileDTO();
		dto.setTableName(tableName);
		dto.setSeq(seq);
		dto.setFileName(fileName);
		dto.setBinaryStream(is, size);
		return getSqlMapClientTemplate().update("File.updateFile", dto);
	}

}
