<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags/bbs-tag" prefix="bbs" %>
<%@ taglib uri="/tags/role-tag" prefix="role" %>

<div style="margin-top:30; padding: 10; text-align: center" >

<b> <bean:message key="member.memberInfo" />  </b>
<hr width="90%" />
<c:set var="mb" value="${requestScope.memberInfo}" />

<html:form action="/changeLevel" >
<bbs:hidden />
<table border="0" width="600" cellspacing="1" class="memberinfo-table">
    <tr>
        <td width="150"> <bean:message key="member.userId" /> </td>
        <td> ${mb.userId} </td>
    </tr>
    <tr>
        <td> <bean:message key="member.userName" /> </td>
        <td> ${mb.userName} </td>
    </tr>
     <tr>
        <td> <bean:message key="member.email" /> </td>
        <td> ${mb.email} </td>
    </tr>
    <tr>
        <td> <bean:message key="member.jumin" /> </td>
        <td> ${mb.juminNumber} </td>
    </tr>
    <tr>
        <td> <bean:message key="member.phone" /> </td>
        <td> ${empty mb.phoneNumber ? "&nbsp;" : mb.phoneNumber} </td>
    </tr>
    <tr>
        <td> <bean:message key="member.mobile" /> </td>
        <td> ${empty mb.mobileNumber ? "&nbsp;" : mb.mobileNumber} </td>
    </tr>
    <tr>
    	<td> <bean:message key="member.level" /> </td>
    	<td> 
    		<role:selectLevel name="level" level="${mb.level}" script="onchange='changeLevel();'"/>
    	</td>
    </tr>
     <tr>
        <td> <bean:message key="member.lastLogin" /> </td>
        <td> <fmt:formatDate value="${mb.lastLogin}" pattern="yyyy-MM-dd HH:mm"/></td>
    </tr>
</table>
</html:form>

	<div style="margin-top: 15" >
		<bbs:indexLink>
			<bean:message key="global.index" />
		</bbs:indexLink>
	</div>

</div>

<script language="javascript">
function changeLevel()
{
	rs = confirm("<bean:message key="messages.member.confirmChangeLevel" />");
	if(rs) {
		changeLevelForm.submit();
	}else {
		changeLevelForm.reset();
		return false;
	}
}

</script>