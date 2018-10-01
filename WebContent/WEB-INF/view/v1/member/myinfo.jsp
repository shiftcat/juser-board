<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="/tags/role-tag" prefix="role" %>
<%@ taglib uri="/tags/han-tag" prefix="han" %>

<div style="margin-top:30; padding: 10; text-align: center" >

<b> <bean:message key="member.memberInfo" />  </b>
<hr width="90%" />

<table border="0" width="600" cellspacing="1" class="memberinfo-table">
    <tr>
        <td width="150"> <bean:message key="member.userId" /> </td>
        <td> <han:memberInfo property="userId" /> </td>
    </tr>
    <tr>
        <td> <bean:message key="member.userName" /> </td>
        <td> <han:memberInfo property="userName" /> </td>
    </tr>
     <tr>
        <td> <bean:message key="member.email" /> </td>
        <td><han:memberInfo property="email" /> </td>
    </tr>
    <tr>
        <td> <bean:message key="member.jumin" /> </td>
        <td> <han:memberInfo property="juminNumber" /> </td>
    </tr>
    <tr>
        <td> <bean:message key="member.phone" /> </td>
        <td> <han:memberInfo property="phoneNumber" /> </td>
    </tr>
    <tr>
        <td> <bean:message key="member.mobile" /> </td>
        <td> <han:memberInfo property="mobileNumber" /> </td>
    </tr>
    <tr>
    	<td> <bean:message key="member.level" /> </td>
    	<td> <han:memberInfo property="level" /></td>
    </tr>
     <tr>
        <td> <bean:message key="member.lastLogin" /> </td>
        <td> <han:memberInfo property="lastLogin" /> </td>
    </tr>
</table>

	<div style="margin-top: 15" >
		<html:link action="frm-newPasswd">
			<bean:message key="member.changePasswd" />
		</html:link>
		<html:link forward="fw-verifyModeModify">
			<bean:message key="member.changeMyInfo" />
		</html:link>
		<role:elseAdmin>
			<html:link forward="fw-verifyModeSecede">
				<bean:message key="member.secede" />
			</html:link>
		</role:elseAdmin>
	</div>

</div>