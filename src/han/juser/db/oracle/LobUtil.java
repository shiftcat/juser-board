package han.juser.db.oracle;


import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.sql.ResultSet;

import oracle.sql.CLOB;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LobUtil 
{
	private static final Log log = LogFactory.getLog(LobUtil.class);
	
	
	
	public static boolean setClobDate(ResultSet rs, String column, String data)
	{
		Writer writer = null;
		Reader reader = null;
		try{
			CLOB clob = (CLOB)rs.getClob(column);
			writer = clob.getCharacterOutputStream();
			reader = new CharArrayReader(data.toCharArray());
			char[] buffer = new char[1024];
			int read = 0;
			while( (read = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, read);
			}
			writer.flush();
			return true;
		} catch(Exception e) {
			if(log.isDebugEnabled()) {
				log.debug(e.getMessage(), e);
			}
			return false;
		}finally {
			if( reader != null ) try{ reader.close(); } catch(IOException e){}
			if( writer != null ) try{ writer.close(); } catch(IOException e){}
		}
	}
	
	
	
	public static String getClobData(ResultSet rs, String column)
	{
		StringBuffer sb = new StringBuffer();
		Reader input = null;
		try{
			input = rs.getCharacterStream(column);
			char[] buffer = new char[1024];
			int read = 0;
			while( (read = input.read(buffer)) != -1) {
				sb.append(buffer, 0, read);
			}
			return sb.toString();
		} catch(Exception e) {
			if(log.isDebugEnabled()) {
				log.debug(e.getMessage(), e);
			}
			return null;
		}finally{
			if( input != null ) try{ input.close(); } catch(IOException e){}
		}
		
	}
}
