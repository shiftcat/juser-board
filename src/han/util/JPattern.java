package han.util;

import java.util.regex.Pattern;

public class JPattern 
{
	private static final String userIdPattern;
	private static final String hanglPattern;
	private static final String emailPattern;
	private static final String passwdPattern;
	private static final String juminPattern;
	private static final String phoneNumberPattern;
	private static final String mobileNumberPattern;
	
	static {
		userIdPattern = "^[a-zA-Z][a-zA-Z0-9]{3,7}";
		emailPattern = "^[a-zA-Z0-9-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z-]+)+$";
		hanglPattern = "[\uAC00-\uD7A3¤¡-¤¾¤¿-¤Ó]{2,10}";
		passwdPattern = "\\p{Graph}{4,8}";
		juminPattern = "[0-9]{6}-[0-9]{7}";
		phoneNumberPattern = "^[0][1-9]{1,2}-[0-9]{3,4}-[0-9]{4}";
		mobileNumberPattern = "^01[0-9]{1}-[0-9]{3,4}-[0-9]{4}";
	}
	
	public JPattern()
	{
	}
	
	public static boolean userId(String userid)
	{
		if( Pattern.matches(userIdPattern, userid) ) {
			return true;
		}
		return false;
	}
	
	public static boolean hanglName(String name)
	{
		if( Pattern.matches(hanglPattern, name) ) {
			return true;
		}
		return false;
	}
	
	public static boolean email(String email)
	{
		if( Pattern.matches( emailPattern, email ) ) {
			return true;
		}
		return false;
	}
	
	public static boolean passwd(String passwd)
	{
		if( Pattern.matches( passwdPattern, passwd ) ) {
			return true;
		}
		return false;
	}
	
	public static boolean jumin(String jumin)
	{
		if( Pattern.matches( juminPattern, jumin ) ) {
			return true;
		}
		return false;
	}
	
	public static boolean phone(String phone)
	{
		if( Pattern.matches(phoneNumberPattern, phone ) ) {
			return true;
		}
		return false;
	}
	
	public static boolean mobile(String mobile)
	{
		if( Pattern.matches( mobileNumberPattern, mobile ) ) {
			return true;
		}
		return false;
	}
}
