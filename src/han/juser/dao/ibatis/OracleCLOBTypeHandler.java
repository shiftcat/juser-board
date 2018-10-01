package han.juser.dao.ibatis;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleResultSet;
import oracle.sql.CLOB;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

public class OracleCLOBTypeHandler implements TypeHandlerCallback 
{
	private Log log = LogFactory.getLog( getClass() );
	
    public void setParameter(ParameterSetter setter, Object param) throws SQLException {

        try {
            setter.setClob((Clob) param);
        } catch (SQLException e) {
            if (log.isErrorEnabled())
                log.error("Failed to set CLOB parameter");
            throw e;
        }
    }


    public Object getResult(ResultGetter getter) throws SQLException {

        String str = null;
        try {
            str = getClobData(getter.getClob());
        } catch (SQLException e) {
            if (log.isErrorEnabled())
                log.error("Failed to set CLOB result property");
            throw e;
        }

        return str;
    }

    public Object valueOf(String arg0) {

        return arg0;
    }

	public boolean setClobDate(ResultSet rs, String column, String data)
	{
		Writer writer = null;
		Reader reader = null;
		try{
			CLOB clob = ((OracleResultSet)rs).getCLOB(column);
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
	
	
	
	public String getClobData(Clob clob)
	{
		StringBuffer sb = new StringBuffer();
		Reader input = null;
		try{
			input = clob.getCharacterStream();
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