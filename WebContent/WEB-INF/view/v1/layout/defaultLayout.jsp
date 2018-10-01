<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="/WEB-INF/tlds/struts-tiles.tld" prefix="tiles" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/jboard.css" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath() %>/script/common.js"></script>
<title><tiles:getAsString name="title" /></title>

</head>
<body background="<%=request.getContextPath() %>/images/img/0007_body.gif" >


<div align="center">
<table border="0" width="980" cellspacing="0">
<tr>
	<td colspan="2" background="<%=request.getContextPath() %>/images/img/0007_h982.png" height="41">
	<tiles:insert attribute="head" />
	</td>
</tr>
<tr>
	<td colspan="2" bgcolor="#ffffff">
		<div style="border-bottom: 1px solid #000000;">
		<tiles:insert attribute="mainmenu" />
		</div>
	</div>
	</td>
</tr>
<tr>
	<td width="150" bgcolor="#ffffff">
	<!-- left -->
		<tiles:insert attribute="login" /> <br>
		<tiles:insert attribute="submenu" /> <br>
		<tiles:insert attribute="statistics" /> <br>
	</td>
	<td bgcolor="#ffffff">
	<!-- center -->
		<tiles:insert attribute="bodyTop" />
		<tiles:insert attribute="bodyCenter" />
		<tiles:insert attribute="bodyBottom" />
	</td>
</tr>
<tr>
	<td colspan="2" background="<%=request.getContextPath() %>/images/img/0007_f982.png" height="80">
	<tiles:insert attribute="bottom"/>
	</td>
</tr>
</table>
</div>
</body>
</html>