<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>


<div style="margin-top: 50; text-align: center;" >

<html:errors/>

<form action="<%=request.getContextPath() %>/member/login.do" method="post">
<table border="0" width="300" class="login-box" cellspacing="1">
	<tr>
		<td width="100"> ���̵� </td>
		<td><input type="text" name="userId" size="8" /></td>
	</tr>
	<tr>
		<td>�н�����</td>
		<td><input type="password" name="passwd" size="8"></td>
	</tr>
</table>
	<div style="margin-top: 15">
	���̵�/�н����� ã��
	<input type="submit" value="Ȯ��" />
	</div>
</form>
</div>