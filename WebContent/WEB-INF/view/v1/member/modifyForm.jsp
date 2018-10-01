<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>

<c:set var="mem" value="${requestScope.memberInfo}" />

<div style="margin-top:30; padding: 10; text-align: center" align="center">

<html:errors/>
<b> <bean:message key="member.modifyTitle" /> </b>
<hr width="80%">

<html:form action="/modify" >
	<table border="0" width="600" class="member-modify" cellspacing="1">
	    <tr>
	        <td> <bean:message key="member.userId" /> </td>
	        <td> ${mem.userId} </td>
	    </tr>
	    <tr>
	        <td> <bean:message key="member.userName" /> </td>
	        <td> 
	        	<html:text property="userName" value="${mem.userName}" />
	        </td>
	    </tr>
	     <tr>
	        <td> <bean:message key="member.email" /> </td>
	        <td><html:text property="email" value="${mem.email }"/></td>
	    </tr>
	    <tr>
	        <td> <bean:message key="member.jumin" /> </td>
	        <td> ${mem.juminNumber }</td>
	    </tr>
	    <tr>
	        <td> <bean:message key="member.phone" /> </td>
	        <td><html:text property="phone[0]" size="3" maxlength="3" value="${mem.phone[0] }" />-
	            <html:text property="phone[1]" size="4" maxlength="4" value="${mem.phone[1] }" />-
	            <html:text property="phone[2]" size="4" maxlength="4" value="${mem.phone[2] }" />
	        </td>
	    </tr>
	    <tr>
	        <td> <bean:message key="member.mobile" /> </td>
	        <td><html:text property="mobile[0]" size="3" maxlength="3" value="${mem.mobile[0] }" />-
	            <html:text property="mobile[1]" size="4" maxlength="4" value="${mem.mobile[1] }" />-
	            <html:text property="mobile[2]" size="4" maxlength="4" value="${mem.mobile[2] }" />
	        </td>
	    </tr>
	</table>
	<html:submit >
		<bean:message key="global.submit" /> 
	</html:submit>
	<html:button property="cancle">
		<bean:message key="global.cancle" />
	</html:button>
	
</html:form>

</div>

