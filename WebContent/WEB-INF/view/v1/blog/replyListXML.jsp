<?xml version="1.0" encoding="utf-8" ?>
<%@ page contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
response.setHeader("Content-type", "application/xml; charset=utf-8");
%>
<!DOCTYPE reply-list SYSTEM "reply-list.dtd">

<replys>
<c:forEach items="${requestScope.replyList}" var="re">
	<reply id="${re.replyId}">
		<userid>${re.userId}</userid>
		<content><c:out value="${re.content}" /></content>
		<signdate><fmt:formatDate value="${re.signDate}" pattern="yyyy-MM-dd HH:mm"/></signdate>
		<remoteaddr>${re.remoteAddr}</remoteaddr>
	</reply>
</c:forEach>
</replys>