package han.util.multipart;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.upload.FormFile;

public class MultipartFormdata 
{
	private final Log log = LogFactory.getLog(MultipartFormdata.class);
	
	
	private ArrayList files;
	
	
	
	public MultipartFormdata(FormFile formFile, String savePath, FileRenamePolicy policy) throws IOException
	{
		if(formFile == null ) {
			if(log.isWarnEnabled()) {
				log.warn("formFile null");
			}
			return;
		}
		if(formFile.getFileSize() == 0) {
			if(log.isWarnEnabled()) {
				log.warn("formFile size 0");
			}
			return;
		}
		
		File sf = new File(savePath);
		//���ڰ����� ������ ��ΰ� �������� �ʰų� �����ΰ��(��δ� �����ϴ� ���丮 ���� �Ѵ�.)
		if( !sf.exists() || sf.isFile() ) {
			if(log.isWarnEnabled()) {
				log.warn(savePath + "��(��) �������� �ʴ� ���丮 �̰ų� ���ϸ� �Դϴ�.");
			}
			return;
		}
		
		files = new ArrayList();
		File saveFile = policy.rename(new File( sf, formFile.getFileName() ));
		
		write(formFile, saveFile);
		files.add(saveFile.getName());
		if(log.isDebugEnabled()) {
			log.debug(" !! File Upload Success !!");
		}
	}
	
	
	public MultipartFormdata(FormFile[] formFile, String savePath, FileRenamePolicy policy) throws IOException
	{
		File sf = new File(savePath);
		if( sf.isFile() )
			return;
		
		files = new ArrayList();
		for( int i = 0; i < formFile.length; i++ ) {
			if(formFile[i].getFileSize() == 0) 
				continue;
			File saveFile = policy.rename(new File( sf, formFile[i].getFileName() ));
			write(formFile[i], saveFile);
			files.add(saveFile.getName());
		}
	}
	
	
	
	public List getSystemFileNames()
	{
		return this.files;
	}
	
	
	
	
	private void write(FormFile formFile, File f) throws IOException
	{
		FileOutputStream fos = null;
		try{
			InputStream is = formFile.getInputStream();
			fos = new FileOutputStream(f);
			int r = 0;
			byte[] buffer = new byte[1024];
			while( (r = is.read(buffer)) != -1 ) {
				fos.write(buffer, 0, r);
			}
			fos.flush();
		}finally{
			if( fos != null ) fos.close();
		}
	}

}
