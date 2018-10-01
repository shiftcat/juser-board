<%@ page contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags/bbs-tag" prefix="bbs" %>

<c:set value="${requestScope['content']}" var="con" scope="request" />

<center>
<html:errors/>

<html:form action="${action}"  enctype="multipart/form-data" method="post">
	<bbs:hidden />
	<table border="1" width="100%">
	<tr>
		<td width="80" >제목</td>
		<td><html:text property="subject" style="width:100%" value="${con.subject}"/></td>
	</tr>
	<tr>
		<td>링크</td>
		<td><html:text property="link" style="width:100%" value="${con.link}"/></td>
	</tr>
	<tr>
		<td>내용</td>
		<td><html:textarea property="content" style="width:100%; height:300px" value="${con.content}" /></td>
	</tr>
	<tr>
		<td>첨부파일</td>
		<td><html:file property="formFile" style="width:200px" onkeydown="inputDepend();" /> </td>
	</tr>
	</table>
	<html:submit value="확인" />
	<html:button property="" value="취소" onclick="cancle()"/>
</html:form>
</center>
<script type="text/javascript">
<!--
function cancle()
{
	window.history.back();
}
//-->
</script>


