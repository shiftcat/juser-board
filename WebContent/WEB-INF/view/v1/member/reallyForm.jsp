<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
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

<div style="margin-top:50; padding: 10; text-align: center" align="center">

<bean:message key="messages.member.confirmSecede" />
<html:form action="/secede" method="post">
	<input type="submit" value="확인" />
	<input type="button" value="취소" onclick="rollback()"/>
</html:form>

</div>
