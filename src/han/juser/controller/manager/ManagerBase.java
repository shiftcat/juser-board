package han.juser.controller.manager;

import han.juser.controller.session.LoginHandler;
import han.role.RoleManager;
import han.util.FileDown;
import han.util.multipart.DifficultReNamePolicy;
import han.util.multipart.MultipartFormdata;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.upload.FormFile;

public class ManagerBase 
{
	protected final Log log = LogFactory.getLog(getClass());
	
	protected RoleManager role;
	
	protected LoginHandler loginHandler;
	
	
	public void setRoleManager(RoleManager role)
	{
		this.role = role;
		if(log.isDebugEnabled()) {
			log.debug("RoleManager ������");
		}
	}
	
	
	public void setLoginHandler(LoginHandler arg)
	{
		this.loginHandler = arg;
	}
	
	
	public RoleManager getRoleManager()
	{
		return this.role;
	}
	
	
	public LoginHandler getLoginHandler()
	{
		return this.loginHandler;
	}
	
	
	protected void deleteFile(String saveDir, String fileName)
	{
		if(saveDir == null || fileName == null) {
			return;
		}
		
		File file = new File(saveDir, fileName);
		if(file.exists()) {
			file.delete();
			if( log.isDebugEnabled() ) {
				log.debug(" !! file delete success !! ");
			}
		}
	}
	
	
	
	protected String upload(FormFile formFile, String saveDir)
	{
		MultipartFormdata mf = null;
		try {
			mf = new MultipartFormdata(formFile, saveDir, new DifficultReNamePolicy());
		} catch (IOException e) {
			throw new UploadException();
		}
		
		List systemFile = mf.getSystemFileNames();
		if( systemFile != null && systemFile.size() > 0) {
			return systemFile.get(0).toString();
		}
		return null;
	}
	
	
	protected String upload(FormFile formFile, String saveDir, boolean b)
	{
		File f = new File(saveDir);
		if( b ) {
			try {
				if( !f.exists() ) f.mkdirs();
			} catch (Throwable e) {
				throw new UploadException("�������� �ʴ� ���͸� " + saveDir + "��(��) ������ �� ���� ���ε��� �� �����ϴ�.", e);
			}
		}
		return upload(formFile, saveDir);
	}
	
	
	
	protected  void download(HttpServletRequest req, HttpServletResponse res,
			String originalFile, File systemFile)
	{
		FileDown down = new FileDown(req, res);
		down.setHeader(originalFile);
		down.flush(systemFile);
		if( log.isDebugEnabled() ) {
			log.debug(" !! file download success !! ");
		}
	}

}
