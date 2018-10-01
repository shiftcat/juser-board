<%@ page contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags/bbs-tag" prefix="bbs" %>
<%@ taglib uri="/tags/han-tag" prefix="han" %>


<c:set value="${requestScope.content}" var="con" scope="request" />

<center>
	<table border="0" width="100%" cellspacing="1" class="board-table">
	<tr>
		<td width="80">번호</td>
		<td>${con.articleId }</td>
		<td>글쓴이</td>
		<td>${con.userId }</td>
		<td>작성일</td>
		<td><fmt:formatDate value="${con.signDate}" pattern="yyyy-MM-dd HH:mm"/></td>
		<td>조회</td>
		<td>${con.hit }</td>
		<td>추천</td>
		<td>${con.vote }</td>
		<td>${con.remoteAddr }</td>
	</tr>
	<tr>
		<td>제목</td>
		<td colspan="10" ><c:out value="${con.subject }" /></td>
	</tr>
	<c:if test="${!empty con.link}" >
	<tr>
		<td>링크</td>
		<td colspan="10" > 
			<html:link href="${con.link}" target="_blank">
			<c:out value="${con.link }"/>
			</html:link> &nbsp;
		</td>
	</tr>
	</c:if>
	<tr>
		<td>내용</td>
		<td colspan="10" width="750" style="WORD-BREAK: break-all" nowrap="nowrap">
			<han:nlTobr>
			<c:out value="${con.content}" />
			<c:if test="${!empty con.updated}">
			마지막 변경일 : <fmt:formatDate value="${con.updated}" pattern="yyyy-MM-dd HH:mm"/>
			</c:if>
			</han:nlTobr>
		</td>
	</tr>
	<c:if test="${!empty con.originalFiles}" >
	<tr>
		<td>첨부파일</td>
		<td colspan="10">
			<a href="<%=request.getContextPath() %>/jboard/download.do?articleId=${con.articleId}"> 
				${con.originalFiles} 
			</a> 
		</td>
	</tr>
	</c:if>
	</table>
	<bbs:indexLink>목록</bbs:indexLink>
	<bbs:modifyLink>수정</bbs:modifyLink>
	<a href="javascript:doRemove();"> 삭제 </a>
	<a href="javascript:doVote();"> 추천 </a>
	<bbs:writeLink>답글쓰기</bbs:writeLink>
</center>

<form name="frm">
	<bbs:hidden/>
</form>

<script type="text/javascript">
<!--
	function doRemove()
	{
		rs = confirm("정말 삭제 하시겠습니까?");
		if(rs) {
			window.frm.method = "post";
			window.frm.action = "./remove.do";
			window.frm.submit();
		}
	}
	function doVote()
	{
		window.frm.method = "post";
		window.frm.action = "./vote.do";
		window.frm.submit();
	}
//-->
</script>

<jsp:include page="index.jsp" />