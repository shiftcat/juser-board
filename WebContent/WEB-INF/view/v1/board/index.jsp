<%@ page contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags/bbs-tag" prefix="bbs" %>
<%@ taglib uri="/tags/han-tag" prefix="han" %>

<center>
	<html:errors />
	<div align="right">
	<html:form action="/search" method="get">
		<html:select property="target">
			<html:option value="subject">제목</html:option>
			<html:option value="content">내용</html:option>
			<html:option value="userid">아이디</html:option>
		</html:select>
		<html:text property="keyword" />
		<html:submit value="검색"/>
	</html:form>
	</div>
	<table border="0" width="100%" cellspacing="1" class="board-table">
	<tr>
		<th>번호</th>
		<th>글쓴이</th>
		<th>제목</th>
		<th>작성일</th>
		<th>조회</th>
		<th>추천</th>
	</tr>
	<c:forEach items="${requestScope.contentList}" var="con">
	<tr>
		<td align="right">${con.bbsId }</td>
		<td align="left">${con.userId }</td>
		<td align="left"> 
			<bbs:viewLink contentId="${con.bbsId}">
			<c:if test="${param.bbsId == con.bbsId }">
				<b>
			</c:if>
			<han:substring length="30" filter="true" suffix="..." >${con.subject}</han:substring>
			<c:if test="${con.replyCount != 0}">
			[${con.replyCount}]
			</c:if>
			<c:if test="${param.bbsId == con.bbsId }">
				</b>
			</c:if>
			</bbs:viewLink> 
		</td>
		<td><fmt:formatDate value="${con.logDate}" pattern="yyyy-MM-dd HH:mm"/> </td>
		<td align="right">${con.hit }</td>
		<td align="right">${con.vote }</td>
	</tr>
	</c:forEach>
	</table>
	
	<table>
	<tr>
		<td></td>
		<td>
			<bbs:pageLink />
		</td>
		<td> <bbs:writeLink>새 글쓰기</bbs:writeLink> </td>
	</tr>
	</table>
	
</center>