<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags/bbs-tag" prefix="bbs" %>
<%@ taglib uri="/tags/han-tag" prefix="han" %>

<div style=" border-bottom: 1px solid #cccccc" align="center"> 
<table cellspacing="3">
<tr>
	<td width="250" style="WORD-BREAK: break-all" nowrap="nowrap">
		<div style="border-right: 1px solid #000000;">
			<a href="<%=request.getContextPath()%>/blog/index.do"><b>
			<han:substring length="50" suffix="..." >
				<c:out value="${blogMainItem[0].subject}" />
			</han:substring>
			</b></a><br/>
			<han:extImage width="150" height="150">${blogMainItem[0].content}</han:extImage>
			<a href="<%=request.getContextPath()%>/blog/index.do">
			<han:substring length="200" suffix="..." >
				<han:escapeHTML>${blogMainItem[0].content}</han:escapeHTML>
			</han:substring> 
			</a><br/>
			<fmt:formatDate value="${blogMainItem[0].signDate}" pattern="yyyy-MM-dd HH:mm"/><br/>
		</div>
	</td>
	<td width="250" style="WORD-BREAK: break-all" nowrap="nowrap">
		<div style="border-right: 1px solid #000000;">
			<a href="<%=request.getContextPath()%>/blog/index.do"><b>
			<han:substring length="50" suffix="..." >
				<c:out value="${blogMainItem[1].subject}" />
			</han:substring>
			</b></a><br/>
			<han:extImage width="150" height="150">${blogMainItem[1].content}</han:extImage>
			<a href="<%=request.getContextPath()%>/blog/index.do">
			<han:substring length="200" suffix="..." >
				<han:escapeHTML>${blogMainItem[1].content}</han:escapeHTML>
			</han:substring> 
			</a><br/>
			<fmt:formatDate value="${blogMainItem[1].signDate}" pattern="yyyy-MM-dd HH:mm"/><br/>
		</div>
	</td>
	<td width="250" style="WORD-BREAK: break-all" nowrap="nowrap">
		<div style="border-bottom: 1px solid #000000;">
			<a href="<%=request.getContextPath()%>/blog/index.do"><b>
			<han:substring length="50" suffix="..." >
				<c:out value="${blogMainItem[2].subject}" />
			</han:substring>
			</b></a><br/>
			<han:extImage width="150" height="150">${blogMainItem[2].content}</han:extImage>
			<a href="<%=request.getContextPath()%>/blog/index.do">
			<han:substring length="150" suffix="..." >
				<han:escapeHTML>${blogMainItem[2].content}</han:escapeHTML>
			</han:substring> 
			</a><br/>
			<fmt:formatDate value="${blogMainItem[2].signDate}" pattern="yyyy-MM-dd HH:mm"/><br/>
		</div>
		<div style="">
			<a href="<%=request.getContextPath()%>/blog/index.do"><b>
			<han:substring length="50" suffix="..." >
				<c:out value="${blogMainItem[3].subject}" />
			</han:substring>
			</b></a><br/>
			<han:extImage width="150" height="150">${blogMainItem[3].content}</han:extImage>
			<a href="<%=request.getContextPath()%>/blog/index.do?pno=2">
			<han:substring length="150" suffix="..." >
				<han:escapeHTML>${blogMainItem[3].content}</han:escapeHTML>
			</han:substring> 
			</a><br/>
			<fmt:formatDate value="${blogMainItem[3].signDate}" pattern="yyyy-MM-dd HH:mm"/><br/>
		</div>
	</td>
</tr>
</table>
</div>



<div style="font-size: 12px; font-weight: bold; padding: 5;">
 - 게시판
</div>
<div style="border-bottom: 1px solid #cccccc" align="center"  >
<table cellspacing="5">
<tr>
<c:forEach items="${boardMainItem}" var="board" varStatus="st">
	<td width="250" style="WORD-BREAK: break-all" nowrap="nowrap">
		<b><a href="<%=request.getContextPath()%>/board/view.do?bbsId=${board['contentId'] }">
			<han:substring length="50" suffix="...">
				<c:out value="${ board['subject']}" />
			</han:substring>
		</a></b><br/>
		<a href="<%=request.getContextPath()%>/board/view.do?bbsId=${board['contentId'] }">
		<han:substring length="200" suffix="...."> 
			<han:escapeHTML>${ board['content']}</han:escapeHTML>
		</han:substring>
		</a><br/>
		<fmt:formatDate value="${ board['signDate'] }" pattern="yyyy-MM-dd HH:mm"/> <br/>
	</td>
	<c:if test="${ !st.last }" >
	<c:if test="${ st.count % 3 == 0}" >
	</tr><tr>
	</c:if>
	</c:if>
	<c:if test="${st.last}">
		</tr>
	</c:if>
</c:forEach>
</table>
</div>




<div style="font-size: 12px; font-weight: bold; padding: 5;">
 - 답변 게시판
</div>
<div style="border-bottom: 1px solid #cccccc" align="center"  >
<table cellspacing="5">
<tr>
<c:forEach items="${jboardMainItem}" var="board" varStatus="st">
	<td style="WORD-BREAK: break-all" nowrap="nowrap" width="250">
		<b><a href="<%=request.getContextPath()%>/jboard/view.do?articleId=${board['contentId'] }">
			<han:substring length="50" suffix="...">
				<c:out value="${ board['subject']}" />
			</han:substring>
		</a></b><br/>
		<a href="<%=request.getContextPath()%>/jboard/view.do?articleId=${board['contentId'] }">
		<han:substring length="200" suffix="...."> 
			${board['content']} 
		</han:substring>
		</a><br/>
		<fmt:formatDate value="${ board['signDate'] }" pattern="yyyy-MM-dd HH:mm"/><br/>
	</td>
	<c:if test="${ !st.last }" >
	<c:if test="${ st.count % 3 == 0}" >
	</tr><tr>
	</c:if>
	</c:if>
	<c:if test="${st.last}">
		</tr>
	</c:if>
</c:forEach>
</table>
</div>



<div style="font-size: 12px; font-weight: bold; padding: 5;">
 - 방명록
</div>
<div style="border-bottom: 1px solid #cccccc" align="center"  >
<table cellspacing="5">
<tr>
<c:forEach items="${guestMainItem}" var="guest" varStatus="st">
	<td style="WORD-BREAK: break-all" nowrap="nowrap" width="380">
		<a href="<%=request.getContextPath()%>/guest/index.do">
		<han:substring length="200" suffix="...."> 
			${guest['content']} 
		</han:substring>
		</a><br/>
		${guest['signDate']}<br/>
	</td>
	<c:if test="${ !st.last }" >
	<c:if test="${ st.count % 2 == 0}" >
	</tr><tr>
	</c:if>
	</c:if>
	<c:if test="${st.last}">
		</tr>
	</c:if>
</c:forEach>
</table>
</div>