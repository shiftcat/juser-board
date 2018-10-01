<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<script type="text/javascript">
<!--
function rollback()
{
	window.location = "./frm-account.do";
}
//-->
</script>



<div style="margin-top:30; padding: 10; text-align: center" align="center">
<html:errors/>

<b> 암호변경  </b>
<hr width="60%" />
<html:form action="/changePasswd">
	<table border="0" width="300" class="change-password" cellspacing="1">
	<tr>
		<td>현재암호</td>
		<td><html:password property="currentPasswd" size="8" maxlength="8" /></td>
	</tr>
	<tr>
		<td>새 암호</td>
		<td><html:password property="newPasswd" size="8" maxlength="8"  /></td>
	</tr>
	<tr>
		<td>암호확인</td>
		<td><html:password property="checkPasswd" size="8" maxlength="8" /></td>
	</tr>
	</table>
	<div style="margin-top: 15">
	<html:submit> 
		<bean:message key="global.submit"  /> 
	</html:submit>
	<html:button property="cancle" onclick="rollback()">
		<bean:message key="global.cancle" />
	</html:button>
	</div>
</html:form>

</div>

