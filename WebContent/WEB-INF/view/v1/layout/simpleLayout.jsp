<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="/WEB-INF/tlds/struts-tiles.tld" prefix="tiles" %>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">

<title><tiles:getAsString name="title" /></title>
</head>
<body>
<tiles:insert attribute="body"/>
</body>
</html>