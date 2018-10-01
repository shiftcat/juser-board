package han.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * 
 * @author Y.Han Lee
 *
 */
public class FileDown 
{
	
	private HttpServletResponse response;
	
	private String agent;
	
	
	
	public FileDown(HttpServletRequest request, HttpServletResponse response)
	{
		response.setContentType("application/octet-stream"); 
		agent = request.getHeader("USER-AGENT");
		this.response = response;
	}
	
	
	
	

	public void setHeader(String fileName)
	{
		if(fileName == null) {
			return;
		}
		
		String fn = null;
		try {
			fn = new String( fileName.getBytes("euc-kr"), "8859_1");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		if( agent.indexOf("MSIE") >= 0 ){
			int i = agent.indexOf( 'M', 2 );
			String IEV = agent.substring( i + 5, i + 8 );
			if( IEV.equalsIgnoreCase("5.5") ) {
				response.setHeader("Content-Disposition", "filename=" + fn );
			}else{
				response.setHeader("Content-Disposition", "attachment;filename=" + fn );
			}
		}else{
			response.setHeader("Content-Disposition", "attachment;filename=" + fn );
		}
	}
	
	
	
	
	
	
	public void flush(File file)
	{
		byte b[] = new byte[1024];
		if( file != null && file.isFile() ) {
			BufferedOutputStream outs = null;
			BufferedInputStream fin = null;
			try {
				fin = new BufferedInputStream( new FileInputStream( file ) );  
				outs = new BufferedOutputStream( response.getOutputStream() );  
				int read = 0;
				while( ( read = fin.read( b ) ) != -1 ){
					outs.write(b, 0, read);
				}
				
				outs.flush();
			}catch( Exception e ){
				e.printStackTrace();
			}finally{
				if(outs != null ) try{ outs.close(); } catch(Exception e) {}
				if(fin != null ) try{ fin.close(); } catch(Exception e) {}
			}
		}
		
	}
	
	
	
	public void flush(InputStream is)
	{
		byte b[] = new byte[1024];
		if( is != null  ) {
			BufferedOutputStream outs = null;
			BufferedInputStream fin = null;
			try {
				fin = new BufferedInputStream( is );  
				outs = new BufferedOutputStream( response.getOutputStream() );  
				int read = 0;
				while( ( read = fin.read( b ) ) != -1 ){
					outs.write(b, 0, read);
				}
				
				outs.flush();
			}catch( Exception e ){
				e.printStackTrace();
			}finally{
				if(outs != null ) try{ outs.close(); } catch(Exception e) {}
				if(fin != null ) try{ fin.close(); } catch(Exception e) {}
			}
		}
		
	}
}
