<%@ page contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/tags/bbs-tag" prefix="bbs" %>
<%@ taglib uri="/tags/han-tag" prefix="han" %>

<logic:messagesPresent>
	<center>
		<html:errors/>
	</center>
</logic:messagesPresent>


<c:if test="${empty requestScope.content}" >
	<%-- 새글 입력 --%>
	<c:set value="/write" var="action" scope="page" />
</c:if>
<c:if test="${!empty requestScope.content}" >
	<%-- 글 수정 --%>
	<c:set value="/modify" var="action" scope="page" />
	<c:set value="${requestScope.content}" var="con" scope="page"/>
</c:if>

<html:form action="${action}" method="post">
<html:hidden property="gid" value="${con.gid }" />
<bbs:hidden />
<table border="0" width="820" cellspacing="1" class="board-table">
	<tr>
		<td>
		<html:textarea property="content" style="width:100%; height:100px" value="${con.content }"/>
		</td>
		<td width="30">
		<input type="button" value="확인" onclick="doModify()">
		</td>
	</tr>
</table>
</html:form>

	
<form name="frm1" method="post">
	<input type="hidden" name="gid" />
	<bbs:hidden />
	<div align="right" >
		<input name="my" type="checkbox" onclick="showMyGuest()">내가 쓴 글보기 
	</div>
</form>

	
<table border="0" width="820" cellspacing="1" class="board-table">
<c:forEach items="${requestScope.contentList}" var="h" >
	<tr>
		<td width="30">번호</td>
		<td width="80">${h.gid}</td>
		<td width="50">글쓴이</td>
		<td width="50%"> <a name="${h.gid}">${h.userId}</a> </td>
		<td width="50">작성일</td>
		<td width="100"> <fmt:formatDate value="${h.logDate}" pattern="yyyy-MM-dd HH:mm"/></td>
	</tr>
	<tr>
		<td colspan="6" style="WORD-BREAK: break-all" nowrap="nowrap" width="818">
		<han:nlTobr>
			<c:out  value="${h.content}" />
		</han:nlTobr>
		</td>
	</tr>
	<tr>
		<td colspan="6" align="right">
			<a href="javascript:modify(${h.gid});">수정</a> 
			<a href="javascript:doDelete(${h.gid});">삭제</a> 
		</td>
	</tr>
</c:forEach>
</table>

<center>
	<bbs:pageLink />
</center>

	
<script type="text/javascript">
<!--
function doDelete(guid)
{
	rs = confirm("정말 삭제 하시겠습니까?");
	if(rs) {
		frm1.action = "./delete.do";
		frm1.gid.value = guid;
		frm1.submit();
	}else {
		frm1.reset();
	}
}

function modify(guid)
{
	frm1.gid.value = guid;
	frm1.submit();
}

function doModify()
{
	if(guestForm.gid.value) {
		rs = confirm("내용을 변경하시겠습니까?");
		if(rs) {
			guestForm.submit();
		}
	}else {
		guestForm.submit();
	}
}


function showMyGuest()
{
	if( getCookie("MYGUEST") == "true" ) {
		document.cookie = "MYGUEST=false";
	} else {
		document.cookie = "MYGUEST=true";
	}
	location.href = "<%=request.getContextPath()%>/guest/index.do";
}


function myGuest()
{
	if( getCookie("MYGUEST") == "true" ) {
		frm1.my.checked = true;
	}
}


function getCookie(key)
{
	var cook = document.cookie + ";";
	var index = cook.indexOf(key, 0);
	if( index != -1 ) {
		cook = cook.substring(index, cook.length);
		var index1 = cook.indexOf("=", 0)+1;
		var index2 = cook.indexOf(";", index1);
		var val = cook.substring(index1, index2);
		return val;
	}
	return;
}


 myGuest()

//-->
</script>