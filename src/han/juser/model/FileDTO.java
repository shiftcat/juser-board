package han.juser.model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;

public class FileDTO  implements Serializable
{
	private String tableName;
	private long seq;
	private String fileName;
	private byte[] binary;
	
	
	public FileDTO() 
	{
		super();
	}
	
	public byte[] getBinary() 
	{
		return binary;
	}
	
	public void setBinary(byte[] binary) 
	{
		this.binary = binary;
	}
	
	public String getFileName() 
	{
		return fileName;
	}
	
	public void setFileName(String fileName) 
	{
		this.fileName = fileName;
	}
	
	public long getSeq() 
	{
		return seq;
	}
	
	public void setSeq(long seq) 
	{
		this.seq = seq;
	}
	
	public String getTableName() 
	{
		return tableName;
	}
	
	public void setTableName(String tableName) 
	{
		this.tableName = tableName;
	}
	
	
	public void setBinaryStream(InputStream is, int size)
	{
		try {
			this.binary = new byte[size];
			is.read(binary);
		} catch(Exception e){}
	}
	
	public InputStream getBinaryStream()
	{
		return new ByteArrayInputStream(binary);
	}
	
}
