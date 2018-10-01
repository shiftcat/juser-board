<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>

<script type="text/javascript">
<!--
function rollback()
{
	window.location = "./frm-account.do";
}
//-->
</script>



<div style="margin-top:50; padding: 10; text-align: center" >

개인정보 보호를 위해서 아이디와 패스워드를 입력하여주세요.

<html:form action="${requestScope.action }" method="post">
<table border="0" width="300" class="verify-form" cellspacing="1">
<tr>
	<td> <bean:message key="member.userId" /> </td>
	<td> <html:text property="userId" size="8" maxlength="8" /></td>
</tr>
<tr>
	<td> <bean:message key="member.passwd"  /> </td>
	<td> <html:password property="passwd" size="8"  maxlength="8"/></td>
</tr>
</table>

	<div style="margin-top: 15">
	<html:submit> 
		<bean:message key="global.submit"  /> 
	</html:submit>
	<html:button property="cancle" onclick="rollback()">
		<bean:message key="global.cancle" />
	</html:button>
	</html:form>
	</div>
</div>
