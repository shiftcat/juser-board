<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<table width="98%" border="0" cellspacing="1" class="statistics" >
<tr>
	<th> 접속통계 </th>
</tr>
<tr>
	<td> Today : ${requestScope.todayCount }</td>
</tr>
<tr>
	<td> Total : ${requestScope.visitCount }</td>
</tr>
<tr>
	<td> 로그인 회원 </td>
</tr>
<tr>
	<td>
	<form name="memberListForm">
	<select name="currentLogin" id="currentLogin" style="width:100%;" multiple="multiple">
		
	</select>
	</form>
	</td>
</tr>
</table>
<script type="text/javascript">
<!--
	
	var MEM_REQ;
	
	function sendMember(url, data, callback)
	{
		MEM_REQ = newXMLHttpRequest();
		MEM_REQ.open("POST", url, true);
		MEM_REQ.onreadystatechange = callback;
		MEM_REQ.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		MEM_REQ.send(data);
	}
	
	
	function requestMemberList()
	{
		sendMember("<%=request.getContextPath()%>/member/loginMemberList.do", null, callbackMemberList);
	}
	
	requestMemberList();
	
	function callbackMemberList()
	{
		if (MEM_REQ.readyState == 4) {
			if (MEM_REQ.status == 200) {
				addLoginMember();
			}else{
				var message = MEM_REQ.getResponseHeader("Status");
				if((message.length == null) || (message.length <= 0)) {
					alert("Error! Request status is " + MEM_REQ.status);
				} else {
					alert(message);
				}
			}
		}
	}
	
	
	
	function addLoginMember()
	{
		var lists = MEM_REQ.responseXML.getElementsByTagName("loging-members");
		if(lists.length > 0) {
		
			var member = lists[0].getElementsByTagName("member");
			if(member.length > 0 ) {
				clearLoginMember();
				for(var i=0; i<member.length; i++) {
					var id = member[i].getAttribute("id");
					var userid = member[i].getElementsByTagName("user-id")[0].firstChild.nodeValue;
					//alert(userid);
					option = new Option(id, userid);
					memberListForm.currentLogin.options[memberListForm.currentLogin.options.length]  = option;
				}
			}
		}
	}
	
	function clearLoginMember()
	{
		//for(var i=0; i<memberListForm.currentLogin.options.length; i++) {
			//memberListForm.currentLogin.options[i] = null;
		//}
		document.getElementById("currentLogin").innerHTML = "";
	}
	
	
	setInterval('requestMemberList()', 1000*60*10);
//-->
</script>