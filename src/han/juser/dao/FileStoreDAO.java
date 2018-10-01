package han.juser.dao;

import han.juser.model.FileDTO;

import java.io.InputStream;

public interface FileStoreDAO 
{
	public int saveFile(String tableName, long seq, String fileName, InputStream is, int size);
	
	public FileDTO getFile(String tableName, long seq);
	
	public int updateFile(String tableName, long seq, String fileName, InputStream is, int size);
	
	public int deleteFile(String tableName, long seq);
}
