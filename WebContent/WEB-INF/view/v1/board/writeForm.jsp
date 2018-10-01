<%@ page contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags/bbs-tag" prefix="bbs" %>


<c:set value="${requestScope['content']}" var="con" scope="page" />

<center>
<html:errors/>

<script type="text/javascript" src="./easyEditor.js"></script>

<html:form action="${action}"  enctype="multipart/form-data" onsubmit="submitOk(this)"  method="post">
	<bbs:hidden />
	<html:hidden property="content" />
	<table border="1">
	<tr>
		<td width="80">제목</td>
		<td><html:text property="subject" style="width:100%" value="${con.subject}"/></td>
	</tr>
	<tr>
		<td>링크</td>
		<td><html:text property="link" style="width:100%" value="${con.link}"/></td>
	</tr>
	<tr>
		<td>내용</td>
		<td><textarea name="edit" >${con.content}</textarea></td>
	</tr>
	<tr>
		<td>첨부파일</td>
		<td><html:file property="formFile" style="width:200px" onkeydown="inputDepend();"/> </td>
	</tr>
	</table>
	<html:submit value="확인" />
	<html:button property="" value="취소" onclick="cancle()" />
</html:form>
</center>
<script type="text/javascript">
<!--
	
    var ed = new easyEditor("edit");
    ed.cfg.width = "100%"; //가로 설정 (디폴트 100%)
    ed.cfg.height = "300px"; //세로 설정 (디폴트 200px)
    ed.cfg.imgpath = "./img/flat";
    ed.cfg.filepath = "./";
    ed.cfg.over_bordercolor = "#facf98"; //버튼마우스 오버시 border컬러
    ed.cfg.over_bgcolor = "#ffffea";    // 버튼 마우스 오버스 bg컬러
    ed.cfg.divbtn_bgcolor = "#e7e7e7";  // 버튼 영역 div bg컬러
    ed.init();
    
	
    function submitOk(frm)
    {
    	frm.content.value = "";
    	frm.content.value = ed.getHtml();
    }
    
    function cancle()
    {
    	window.history.back();
    }
    
//-->
</script>

