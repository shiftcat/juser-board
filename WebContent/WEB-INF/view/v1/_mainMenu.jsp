<%@ page contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tags/role-tag" prefix="role" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>


<role:ifAdmin>
	<table border="0" width="100%" cellspacing="1"  class="menu">
	<tr>
		<td>
			<a href="<%=request.getContextPath() %>/member/myInfo.do" > 내정보 </a>
		</td>
		<td>
			<a href="<%=request.getContextPath() %>/index.do" > HOME </a>
		</td>
		<td>
			<a href="<%=request.getContextPath() %>/board/index.do" >게시판</a>
		</td>
		<td>
			<a href="<%=request.getContextPath() %>/jboard/index.do" >답변형</a>
		</td>
		<td>
			<a href="<%=request.getContextPath() %>/blog/index.do">블로그</a>
		</td>
		<td>
			<a href="<%=request.getContextPath() %>/guest/index.do" >방명록</a>
		</td>
		<td>
			<a href="<%=request.getContextPath() %>/admin/memberList.do" > 관리 </a>
		</td>
	</tr>
	</table>
</role:ifAdmin>


<role:elseAdmin>
	<table border="0" width="100%" cellspacing="1"  class="menu">
	<tr>
		<td>
			<a href="<%=request.getContextPath() %>/member/myInfo.do" style="width:100%; padding: 10;">내정보</a>
		</td>
		<td>
			<a href="<%=request.getContextPath() %>/index.do" style="width:100%; padding: 10;"> HOME </a>
		</td>
		<td>
			<a href="<%=request.getContextPath() %>/board/index.do" style="width:100%; padding: 10;">게시판</a>
		</td>
		<td>
			<a href="<%=request.getContextPath() %>/jboard/index.do" style="width:100%; padding: 10;">답변형</a>
		</td>
		<td>
			<a href="<%=request.getContextPath() %>/blog/index.do" style="width:100%; padding: 10;">블로그</a>
		</td>
		<td>
			<a href="<%=request.getContextPath() %>/guest/index.do" style="width:100%; padding: 10;">방명록</a>
		</td>
	</tr>
	</table>
</role:elseAdmin>


