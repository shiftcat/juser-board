<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags/bbs-tag" prefix="bbs" %>

<center>
<table border="0" width="100%" cellspacing="1" class="member-list">
<tr>
	<th>번호</th>
	<th>아이디</th>
	<th>이름</th>
	<th>회원등급</th>
	<th>이메일</th>
	<th>가입일</th>
</tr>
<c:forEach items="${requestScope.memberList}" var="m">
<tr>
	<td>${m.num }</td>
	<td><bbs:viewLink contentId="${m.userId}" >${m.userId }</bbs:viewLink></td>
	<td>${m.userName }</td>
	<td>${m.level} </td>
	<td>${m.email }</td>
	<td><fmt:formatDate value="${m.logDate}" pattern="yyyy-MM-dd HH:mm"/></td>
</tr>
</c:forEach>
</table>
<bbs:pageLink />
</center>