<%@ page contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags/bbs-tag" prefix="bbs" %>
<%@ taglib uri="/tags/han-tag" prefix="han" %>

<center>
	<html:errors />
	
	<bbs:writeLink>�� �۾���</bbs:writeLink>
	
	<div align="right">
	<html:form action="/search" method="get">
		<bbs:categorySelect name="cate" category="${param.cate}" defaultOption="��ü�˻�" />
		<html:select property="target">
			<html:option value="subject">����</html:option>
			<html:option value="content">����</html:option>
			<html:option value="userid">���̵�</html:option>
		</html:select>
		<html:text property="keyword" />
		<html:submit value="�˻�"/>
	</html:form>
	</div>
	
	<table border="0" width="100%" cellspacing="1" class="board-table">
	<c:forEach items="${requestScope.contentList}" var="con" >
	<tr>
		<td width="50">��ȣ</td>
		<td width="100">${con.rowNum }</td>
		<td width="50">�۾���</td>
		<td width="50%">${con.userId }</td>
		<td width="80">�ۼ���</td>
		<td width="150"><fmt:formatDate value="${con.signDate}" pattern="yyyy-MM-dd HH:mm"/></td>
	</tr>
	<tr>
		<td colspan="6" > 
			<b><c:out value="${con.subject}" /></b> 
		</td>
	</tr>
	<tr>
		<td  colspan="6" >
		<iframe frameborder="0" src="./content.do?articleId=${con.articleId}" ALLOWTRANSPARENCY="true"
		   style="width:100%; height:800" onload="resizeIfr(this, 300)"></iframe>
		</td>
	</tr>
	<tr>
		<td colspan="6">
		<div align="right"> 
			<bbs:modifyLink contentId="${con.articleId}" >����</bbs:modifyLink>
			<a href="javascript:doRemove(${con.articleId });">����</a>
			<a href="javascript:showReplyForm(${con.articleId });">
			  ���۴ޱ�
			 <font id="replyCount${con.articleId}"> 
			 <c:if test="${con.replyCount != 0}"> 
			 [${con.replyCount}] 
			 </c:if>
			 </font>
			</a>
		</div>
		</td>
	</tr>
	<tr>
		<td colspan="6">
		<div id="replyForm${con.articleId}"></div>
		<div id="replyList${con.articleId}"></div>
		</td>
	</tr>
	</c:forEach>
	</table>
	
	
	
	<table>
	<tr>
		<td><bbs:pageLink/></td>
	</tr>
	</table>
	
</center>

<form name="frm" >
	<input type="hidden" name="articleId">
	<bbs:hidden />
</form>


<script type="text/javascript">
<!--

	//���̹����� ���°� ifame resize
	function resizeIfr(obj, minHeight) 
	{
		minHeight = minHeight || 10;
	
		try {
			var getHeightByElement = function(body) {
				var last = body.lastChild;
				try {
					while (last && last.nodeType != 1 || !last.offsetTop) last = last.previousSibling;
					return last.offsetTop+last.offsetHeight;
				} catch(e) {
					return 0;
				}
			}
				
			var doc = obj.contentDocument || obj.contentWindow.document;
			if (doc.location.href == 'about:blank') {
				obj.style.height = minHeight+'px';
				return;
			}
			
			//var h = Math.max(doc.body.scrollHeight,getHeightByElement(doc.body));
			//var h = doc.body.scrollHeight;
			if (/MSIE/.test(navigator.userAgent)) {
				var h = doc.body.scrollHeight;
			} else {
				var s = doc.body.appendChild(document.createElement('DIV'))
				s.style.clear = 'both';
	
				var h = s.offsetTop;
				s.parentNode.removeChild(s);
			}
			
			//if (/MSIE/.test(navigator.userAgent)) h += doc.body.offsetHeight - doc.body.clientHeight;
			if (h < minHeight) h = minHeight;
		
			obj.style.height = h + 'px';
			if (typeof resizeIfr.check == 'undefined') resizeIfr.check = 0;
			if (typeof obj._check == 'undefined') obj._check = 0;
	
	//		if (obj._check < 5) {
	//			obj._check++;
				setTimeout(function(){ resizeIfr(obj,minHeight) }, 200); // check 5 times for IE bug
	//		} else {
				//obj._check = 0;
	//		}	
		} catch (e) { 
			//alert(e);
		}
		
	}


	function doRemove(id)
	{
		rs = confirm("���� ���� �Ͻðڽ��ϱ�?");
		if(rs) {
			frm.action="./remove.do";
			frm.method="post";
			frm.articleId.value=id;
			frm.submit();
		}
	}
	
	
	var replyListNode;
	var articleId;
	
	function showReplyForm(id)
	{
		var obj = document.getElementById("replyForm"+id);
		replyListNode = document.getElementById("replyList"+id);
		if(!obj.innerHTML) {
			var html;
			html = "<form name='rfrm" + id + "'>";
			html += "<table width='100%'><tr><td>";
			html += "<input type='hidden' name='articleId' value='"+id+"'>";
			html += "<input type='text' name='reply' id='reply" + id + "' style='width:100%' />";
			html += "</td><td width='80'>";
			html += "<input type='button' value='Ȯ��' onclick='submitReply(rfrm" + id + ")'>";
			html += "</td></tr></table>";
			html += "</form>";
			obj.innerHTML = html;
			articleId = id;
			requestReplyList(id);
		}else {
			obj.innerHTML = "";
			replyListNode.innerHTML = "";
		}
	}
	
	var request;
	
	function sendReply(url, data, callback)
	{
		request = newXMLHttpRequest();
		request.open("POST", url, true);
		request.onreadystatechange = callback;
		request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		request.send(data);
	}
	
	
	/*
	* �����͸� �������� �Լ�
	*/
	function submitReply(frm)
	{
		var url = "writeReply.do";
		var data = "articleId=" + articleId + "&reply=" +  encodeURI(frm.reply.value);
		sendReply(url, data, callbackResponse);
		frm.reply.value = "";
	}
	
	
	/*
	* ����Ʈ ��÷ �Լ�
	*/
	function requestReplyList(id)
	{
		sendReply("replyList.do", "articleId=" + id, callbackReplyList);
	}
	
	
	/*
	* ������ ���� �Լ�
	*/
	function deleteReply(id)
	{
		sendReply("removeReply.do", "replyId="+id, callbackResponse);
	}
	
	
	
	/*
	* ������ ����, ���� �� ����� �ݹ� �Լ� 
	* ���������� ����Ǹ� ����Ʈ�� ��û�ϴ� �Լ��� ȣ��
	*/
	function callbackResponse()
	{
		if (request.readyState == 4) {
			if (request.status == 200) {
				requestReplyList(articleId);
			}else{
				var message = request.getResponseHeader("Status");
				if((message.length == null) || (message.length <= 0)) {
					alert("Error! Request status is " + request.status);
				} else {
					alert(message);
				}
			}
		}
	}
	
	
	
	/*
	* ����Ʈ�� ��û�ϴ� �Լ��� �ݹ��Լ� 
	*/
	function callbackReplyList()
	{
		if (request.readyState == 4) {
			if (request.status == 200) {
				showReplyList();
			}else{
				var message = request.getResponseHeader("Status");
				if((message.length == null) || (message.length <= 0)) {
					alert("Error! Request status is " + request.status);
				} else {
					alert(message);
				}
			}
		}
	}
	
	
	
	function showReplyList()
	{
		var lists = request.responseXML.getElementsByTagName("replys");
		if( lists.length > 0 ) {
			var items = lists[0].getElementsByTagName("reply");
			if( items.length > 0 ) {
				var html;
				html = "<table border='0' width='100%' cellspacing='1'>";
				
				for(var i = 0; i < items.length; i++) {
					var item = items[i];
					
					var id = item.getAttribute("id");
					var userid = item.getElementsByTagName("userid")[0].firstChild.nodeValue;
					var content = item.getElementsByTagName("content")[0].firstChild.nodeValue;
					var signdate = item.getElementsByTagName("signdate")[0].firstChild.nodeValue;
					content = toEntity(content);
					
					html +="<tr>";
					html += "<td width='50'>"+userid+"</td>";
					html += "<td>"+content+"</td>";
					html += "<td width='100'>"+signdate+"</td>";
					html += "<td width='30'><b><a href='javascript:deleteReply("+id+")'>��</a></b></td>";
					html += "</tr>";
					
				}
				html += "</table>";
				
				replyListNode.innerHTML = html;
				document.getElementById("replyCount"+articleId).innerHTML = "[" + items.length + "]";
			}else {
				replyListNode.innerHTML = "";
				document.getElementById("replyCount"+articleId).innerHTML = "";
			}
		} else {
			//
		}
		
		//alert(items.length);
	}
	
	
	function show()
	{
	    val = document.getElementById("layer1").style.display;
	    if(val == "none") {
	        document.getElementById("layer1").style.display = "inline";
	    }else if( val == "inline") {
	        document.getElementById("layer1").style.display = "none";
	    }
	    //alert(val);
	}
	

	
//-->
</script>
