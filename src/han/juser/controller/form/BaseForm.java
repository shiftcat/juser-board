package han.juser.controller.form;


import org.apache.commons.lang.builder.*;
import org.apache.struts.validator.ValidatorForm;

public class BaseForm  extends ValidatorForm
{
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
	
	public boolean equals(Object o)
	{
		return EqualsBuilder.reflectionEquals(this, o);
	}
	
	
	public int hashCode()
	{
		return HashCodeBuilder.reflectionHashCode(this);
	}

}
