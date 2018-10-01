<%@ page contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags/bbs-tag" prefix="bbs" %>

<center>
<html:errors/>

<script id="HABYEditor" type="text/JScript.Encode" src="./habyeditor.js"></script>

<c:if test="${ !empty requestScope.content}" >
	<c:set value="${requestScope.content}" var="con" />
</c:if>

 
<html:form  action="${requestScope.action}" method="post">
	<bbs:hidden />
	<table border="0" cellspacing="1" class="board-table">
	<tr>
		<td> �ۺз� </td>
		<td> <bbs:categorySelect name="cateId" defaultOption="�ۺз� ����" category="${con.cateId}" /> </td>
	</tr>
	<tr>
		<td width="80">����</td>
		<td><html:text property="subject" style="width:100%;" value="${con.subject}" /></td>
	</tr>
	<tr>
		<td>����</td>
		<td><html:textarea property="content" style="width:720px; height:430px;" value="${con.content}"/></td>
	</tr>
	</table>
	<html:submit value="Ȯ��" />
	<html:button property="" value="���" onclick="cancle()" />
</html:form>
</center>



<script type="text/javascript">
<!--
	var newEditor = new HABYeditor('content');
    newEditor.setConfig["MenuButton"] = [		// ������ ���� ��� �����Է�
		["Preview", "FullScreen"],
		["Bold", "Italic", "UnderLine", "StrikeThrough"],
		["Subscript", "Superscript"],
		["JustifyLeft", "JustifyCenter", "JustifyRight", "JustifyFull"],
		["InsertOrderedList", "InsertUnorderedList", "Outdent", "Indent"],
		["CreateLink", "UnLink"],
		"newLine",
		["FontFormat", "FontName", "FontSize"],
		["ForeColor", "BackColor"]
	];
	newEditor.Create();
	
	
	function cancle()
    {
    	window.history.back();
    }
    
    
//-->
</script>


