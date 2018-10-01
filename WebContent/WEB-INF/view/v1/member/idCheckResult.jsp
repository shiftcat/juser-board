<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<style>
<!--
	body {
		background-color: white;
	}
-->
</style>
<script type="text/javascript">
<!--
function ok()
{
	opener.accountFormBean.userId.value = "${param.userId}";
	window.close();
}

function no()
{
	window.location = "idCheck.do";
}
-->
</script>

<div style="margin-top:30; text-align:center;" >
	<bean:message key="messages.member.confirmUseId" arg0="${param.userId }" /> <br>
	<html:button property="yes" onclick="ok()">
		<bean:message key="global.yes" />
	</html:button>
	<html:button property="no" onclick="no()">
		<bean:message key="global.no" />
	</html:button>
</div>