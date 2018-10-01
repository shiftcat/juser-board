<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags/bbs-tag" prefix="bbs" %>
<%@ taglib uri="/tags/han-tag" prefix="han" %>


<html:form action="/replyWriteAction" method="post" >
	<bbs:hidden/>
    <table border="1" width="100%">
    <tr>
    	<td width="750">
    	<html:textarea property="reply"  style="width:100%; height:60px" />
    	</td>
    	<td><html:submit value="확인" /></td>
    </tr>
    </table>
</html:form>
    
    
    
<c:if test="${!empty con.replyList }" >
    <table border="1" width="100%">
    <c:forEach items="${con.replyList}" var="re">
    <tr>
    	<td valign="top" width="80">
    		${re.userId }
    	</td>
    	<td>
    		<han:nlTobr>
    		<c:out value="${re.reply}" />
    		</han:nlTobr>
    	</td>
    	<td valign="top" width="150">
    	    <fmt:formatDate value="${re.logdate}" pattern="yyyy-MM-dd HH:mm"/>
    		<br/>
    		${re.remoteAddr}
    	</td>
    	<td width="50">
	    	<c:if test="${re.userId == sessionScope.member.userId}" >
	    		<a href="javascript:removeReply(${re.replId})">삭제</a>
	    	</c:if>
	    	<c:if test="${re.userId != sessionScope.member.userId}" >
	    		<a href="javascript:removeReply(${re.replId})">삭제</a>
	    	</c:if>
    	</td>
    </tr>
    </c:forEach>
    </table>
</c:if>

<html:form action="/replyRemoveAction">
	<html:hidden property="replId" />
	<bbs:hidden/>
</html:form>
<script type="text/javascript">
<!--
	function removeReply(rid)
	{
		rs = confirm("정말 삭제 하시겠습니까?");
		if(rs) {
			window.replyRemoveForm.replId.value = rid;
			window.replyRemoveForm.submit();
		}
	}
//-->
</script>
    