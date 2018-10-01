package han.util.multipart;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DifficultReNamePolicy implements FileRenamePolicy
{
	public File rename(File f)
	{
		if(f.isDirectory()) {
			return null;
		}
		
		String name = f.getName();
		String reName = generate(name);
		File rf = new File( f.getParent() + File.separatorChar + reName );
		if( rf.exists() ) {
			rename(rf);
		}
		return rf;
	}
	
	
	public String generate(String str) 
	{
        try {
            byte id[] = str.getBytes();
            byte now[] = new Long(System.currentTimeMillis()).toString().getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(id);
            md.update(now);
            return this.toHex(md.digest());

        } catch (IllegalStateException e) {
        	e.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e) {
        	e.printStackTrace();
            return null;
        }

    }
	
	 public String toHex(byte buffer[]) {
	        StringBuffer sb = new StringBuffer();
	        String s = null;
	        for (int i = 0; i < buffer.length; i++) {
	            s = Integer.toHexString((int) buffer[i] & 0xff);
	            if (s.length() < 2) {
	                sb.append('0');
	            }
	            sb.append(s);
	        }
	        return sb.toString();
	    }
}
