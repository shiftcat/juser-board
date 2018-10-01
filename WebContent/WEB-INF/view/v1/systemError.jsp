<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="org.apache.struts.Globals;"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<center>
<html:errors/>
<%
	Exception exception = (Exception)request.getAttribute(Globals.EXCEPTION_KEY);
	out.println(exception.getMessage() + "<br><p>");
	//exception.printStackTrace(response.getWriter());
%>
</center>