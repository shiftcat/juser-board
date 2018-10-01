<%@ page contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tags/role-tag" prefix="role" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>


<role:ifAdmin>
	<table border="0" width="100%" cellspacing="1"  class="menu">
	<tr>
		<td>
			<a href="<%=request.getContextPath() %>/member/myInfo.do" > ������ </a>
		</td>
		<td>
			<a href="<%=request.getContextPath() %>/index.do" > HOME </a>
		</td>
		<td>
			<a href="<%=request.getContextPath() %>/board/index.do" >�Խ���</a>
		</td>
		<td>
			<a href="<%=request.getContextPath() %>/jboard/index.do" >�亯��</a>
		</td>
		<td>
			<a href="<%=request.getContextPath() %>/blog/index.do">��α�</a>
		</td>
		<td>
			<a href="<%=request.getContextPath() %>/guest/index.do" >����</a>
		</td>
		<td>
			<a href="<%=request.getContextPath() %>/admin/memberList.do" > ���� </a>
		</td>
	</tr>
	</table>
</role:ifAdmin>


<role:elseAdmin>
	<table border="0" width="100%" cellspacing="1"  class="menu">
	<tr>
		<td>
			<a href="<%=request.getContextPath() %>/member/myInfo.do" style="width:100%; padding: 10;">������</a>
		</td>
		<td>
			<a href="<%=request.getContextPath() %>/index.do" style="width:100%; padding: 10;"> HOME </a>
		</td>
		<td>
			<a href="<%=request.getContextPath() %>/board/index.do" style="width:100%; padding: 10;">�Խ���</a>
		</td>
		<td>
			<a href="<%=request.getContextPath() %>/jboard/index.do" style="width:100%; padding: 10;">�亯��</a>
		</td>
		<td>
			<a href="<%=request.getContextPath() %>/blog/index.do" style="width:100%; padding: 10;">��α�</a>
		</td>
		<td>
			<a href="<%=request.getContextPath() %>/guest/index.do" style="width:100%; padding: 10;">����</a>
		</td>
	</tr>
	</table>
</role:elseAdmin>


