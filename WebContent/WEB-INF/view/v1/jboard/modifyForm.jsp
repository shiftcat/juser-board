<%@ page contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags/bbs-tag" prefix="bbs" %>

<c:set value="${requestScope['content']}" var="con" scope="request" />


<center>
<html:errors/>

<html:form action="/modifyForm"  enctype="multipart/form-data" method="post">
	<bbs:hidden />
	<table border="1" width="100%">
	<tr>
		<td width="80">����</td>
		<td><html:text property="subject" style="width:100%" value="${con.subject}"/></td>
	</tr>
	<tr>
		<td>��ũ</td>
		<td><html:text property="link" style="width:100%" value="${con.link}"/></td>
	</tr>
	<tr>
		<td>����</td>
		<td><html:textarea property="content" style="width:100%; height:300px"  value="${con.content}" /></td>
	</tr>
	<tr>
		<td>÷������</td>
		<td>
			<html:file property="formFile" style="width:200px"  onkeydown="inputDepend();"></html:file>
			<c:if test="${!empty con.originalFiles}">
				�������ϻ��� <input type="checkbox" onclick="doDeleteFile()" name="fchk" /> 
			</c:if>
		 </td>
	</tr>
	</table>
	<html:submit value="Ȯ��" />
	<html:button property="" value="���" onclick="cancle()"/>
</html:form>
</center>

<form action="./deleteFile.do" name="deleteFile">
	<bbs:hidden />
</form>

<script type="text/javascript">
<!--
function cancle()
{
	window.history.back();
}
function doDeleteFile()
{
	rs = confirm("÷�������� �����Ͻðڽ��ϱ�?");
	if(rs) {
		deleteFile.submit();
	}else {
		writeForm.fchk.checked = false;
	}
}

//-->
</script>
