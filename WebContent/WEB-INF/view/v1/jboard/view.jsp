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
		<td width="80">��ȣ</td>
		<td>${con.articleId }</td>
		<td>�۾���</td>
		<td>${con.userId }</td>
		<td>�ۼ���</td>
		<td><fmt:formatDate value="${con.signDate}" pattern="yyyy-MM-dd HH:mm"/></td>
		<td>��ȸ</td>
		<td>${con.hit }</td>
		<td>��õ</td>
		<td>${con.vote }</td>
		<td>${con.remoteAddr }</td>
	</tr>
	<tr>
		<td>����</td>
		<td colspan="10" ><c:out value="${con.subject }" /></td>
	</tr>
	<c:if test="${!empty con.link}" >
	<tr>
		<td>��ũ</td>
		<td colspan="10" > 
			<html:link href="${con.link}" target="_blank">
			<c:out value="${con.link }"/>
			</html:link> &nbsp;
		</td>
	</tr>
	</c:if>
	<tr>
		<td>����</td>
		<td colspan="10" width="750" style="WORD-BREAK: break-all" nowrap="nowrap">
			<han:nlTobr>
			<c:out value="${con.content}" />
			<c:if test="${!empty con.updated}">
			������ ������ : <fmt:formatDate value="${con.updated}" pattern="yyyy-MM-dd HH:mm"/>
			</c:if>
			</han:nlTobr>
		</td>
	</tr>
	<c:if test="${!empty con.originalFiles}" >
	<tr>
		<td>÷������</td>
		<td colspan="10">
			<a href="<%=request.getContextPath() %>/jboard/download.do?articleId=${con.articleId}"> 
				${con.originalFiles} 
			</a> 
		</td>
	</tr>
	</c:if>
	</table>
	<bbs:indexLink>���</bbs:indexLink>
	<bbs:modifyLink>����</bbs:modifyLink>
	<a href="javascript:doRemove();"> ���� </a>
	<a href="javascript:doVote();"> ��õ </a>
	<bbs:writeLink>��۾���</bbs:writeLink>
</center>

<form name="frm">
	<bbs:hidden/>
</form>

<script type="text/javascript">
<!--
	function doRemove()
	{
		rs = confirm("���� ���� �Ͻðڽ��ϱ�?");
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