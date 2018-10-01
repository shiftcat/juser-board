<%@ page contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>


<script type="text/javascript">
<!--
function userIdCheck()
{
	window.open("idCheckForm.do", "CEHECK", " width=300, height=150, scrollbars=no, toolbar=no, status=no, menubar=no, resizeable=no");
}
function rollback()
{
	history.back();
}
//-->
</script>


<logic:messagesPresent>
	<div style="text-align: left; margin-left:150;">
	<html:errors/>
	</div>
	<div style="margin-top:10; padding: 10; text-align: center" >
</logic:messagesPresent>
<logic:messagesNotPresent>
	<div style="margin-top:30; padding: 10; text-align: center" >
</logic:messagesNotPresent>

<b> <bean:message key="member.accountTitle" /> </b>
<hr width="90%">
<html:form action="/account" method="post">
	<table border="0" width="600" cellspacing="1" class="account-table">
	    <tr>
	        <td width="150">
	        	<bean:message key="member.userId" />*
	        </td>
	        <td> 
	        	<html:text property="userId" size="8" maxlength="8" onclick="userIdCheck()" readonly="true"/>
	        	<html:button property="idCheck" onclick="userIdCheck()">
	        		<bean:message key="member.userIdCheck" />
	        	</html:button>
	        </td>
	    </tr>
	    <tr>
	        <td>
	        	<bean:message key="member.passwd" />*
	        </td>
	        <td>
	        	<html:password property="passwd" size="8" maxlength="8" />
	           	<bean:message key="member.validPasswd" />
	           	<html:password property="pwdCheck" size="8" maxlength="8" />
	        </td>
	    </tr>
	    <tr>
	        <td>
	        	<bean:message key="member.userName" />*
	        </td>
	        <td>
	        	<html:text property="userName" size="8" maxlength="8" />
	        </td>
	    </tr>
	     <tr>
	        <td>
	        	<bean:message key="member.email" />*
	        </td>
	        <td>
	        	<html:text property="email" />
	        </td>
	    </tr>
	    <tr>
	        <td>
	        	<bean:message key="member.jumin" />*
	        </td>
	        <td>
	        	<html:text property="jumin[0]" size="6" maxlength="6"/>
	            -<html:text property="jumin[1]" size="7" maxlength="7" />
	        </td>
	    </tr>
	    <tr>
	        <td>
	        	<bean:message key="member.phone" />
	        </td>
	        <td>
	        	<html:text property="phone[0]" size="3" maxlength="3" />
	            -<html:text property="phone[1]" size="4" maxlength="4" />
	            -<html:text property="phone[2]" size="4" maxlength="4" />
	        </td>
	    </tr>
	    <tr>
	        <td>
	        	<bean:message key="member.mobile" />
	        </td>
	        <td>
	        	<html:text property="mobile[0]" size="3" maxlength="3" />
	            -<html:text property="mobile[1]" size="4" maxlength="4" />
	            -<html:text property="mobile[2]" size="4" maxlength="4" />
	        </td>
	    </tr>
	</table>
	<div style="margin-top: 15">
	<html:submit >
		<bean:message key="global.submit" />
	</html:submit>
	<html:button property="cancle" onclick="rollback()">
		<bean:message key="global.cancle" /> 
	</html:button>
	</div>
</html:form>

</div>


