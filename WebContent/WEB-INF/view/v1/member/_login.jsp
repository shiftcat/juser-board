<%@ page contentType="text/html; charset=euc-kr" pageEncoding="EUC-KR" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>

<%--  --%>
<c:if test="${empty sessionScope['member']}">
	<form action="<%=request.getContextPath() %>/member/login.do" method="post">
	<table width="98%" border="0" cellspacing="1" class="login-box" >
	<tr>
		<th> LOGIN </th>
	</tr>
	<tr>
		<td> <bean:message key="member.userId" /> 
		<input type="text" name="userId" size="8" /></td>
	</tr>
	<tr>
		<td> <bean:message key="member.passwd" /> 
		<input type="password" name="passwd" size="8"></td>
	</tr>
	<tr>
		<td>
			<a href="<%=request.getContextPath() %>/member/frm-account.do">
				<bean:message key="member.account" />
			</a>
			<html:submit>
				<bean:message key="global.submit" />
			</html:submit>
		</td>
	</tr>
	</table>
	</form>
</c:if>

<%--  --%>
<c:if test="${!empty sessionScope['member']}">
<c:set var="mem" value="${sessionScope.member}" />
	<table width="98%" border="0" cellspacing="1" class="login-box" >
	<tr>
		<th> LOGIN </th>
	</tr>
	<tr>
		<td><c:out value="${mem.userName}" /></td>
	</tr>
	<tr>
		<td>
			<%-- <a href="<%=request.getContextPath() %>/member/frm-verify.do"> 개인정보수정 </a> --%>
			<a href="<%=request.getContextPath() %>/member/logout.do">
				<bean:message key="member.logout" /> 
			</a>
		</td>
	</tr>
	</table>
</c:if>