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

<div style="margin-top:30; text-align:center;" >

<html:errors />
<html:form action="/idCheck" method="post">
<table border="0" style="background-color: #999999;" cellspacing="1" cellpadding="5" >
<tr>
	<td style="background-color: #ffffff">
	<html:text property="userId" />
	<html:submit>
		<bean:message key="global.submit"/>
	</html:submit>
	</td>
</tr>
</table>
</html:form>

</div>