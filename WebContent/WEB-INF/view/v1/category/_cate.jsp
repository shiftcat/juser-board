<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>


<table border="0" width="98%" class="submenu" cellspacing="1">
<tr>
	<th>SubMenu</th>
</tr>
<c:forEach items="${requestScope.subMenu}" var="sub">
	<tr>
		<td>
		<a href="${sub.url}">${sub.name}</a>
		</td>
	</tr>
</c:forEach>
</table>