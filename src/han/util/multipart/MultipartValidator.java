package han.util.multipart;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.Resources;


public class MultipartValidator implements Serializable 
{
	
	public static boolean allowExtension(Object bean, ValidatorAction va, 
			Field field, ActionMessages errors, HttpServletRequest request)
	{
		String value = field.getVarValue("extension");
		if( value == null) {
			return false;
		}
		String fileName = ValidatorUtils.getValueAsString(bean, field.getProperty());
		
		if(fileName == null || fileName.equals("")) {
			return false;
		}
		if( !inStr(value.split(","), getExtension(fileName) ) ) {
			errors.add(field.getKey(), Resources.getActionMessage(request, va, field));
			return false;
		}
		return true;
	}
	
	
	public static boolean denyExtension(Object bean, ValidatorAction va, 
			Field field, ActionMessages errors, HttpServletRequest request)
	{
		String value = field.getVarValue("extension");
		if( value == null) {
			return false;
		}
		String fileName = ValidatorUtils.getValueAsString(bean, field.getProperty());
		
		if(fileName == null || fileName.equals("")) {
			return false;
		}
		if( inStr(value.split(","), getExtension(fileName) ) ) {
			errors.add(field.getKey(), Resources.getActionMessage(request, va, field));
			return false;
		}
		return true;
	}
	
	
	public static boolean inStr(String[] arr, String str)
	{
		for(int i=0; i < arr.length; i++) {
			if(arr[i].equals(str)) {
				return true;
			}
		}
		return false;
	}
	
	public static String getExtension(String fileName)
	{
		int pos = fileName.lastIndexOf('.');
		return fileName.substring(++pos);
	}

}
