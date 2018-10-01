<?xml version="1.0" encoding="utf-8" ?>
<%@ page contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
response.setHeader("Content-type", "application/xml; charset=utf-8");
response.setHeader("Cache-Control", "no-cache");
%>
<loging-members>
<c:forEach items="${requestScope.loginMembers}" var="m">
	<member id="${m.userId}">
		<user-id>${m.userId}</user-id>
	</member>
</c:forEach>
</loging-members>