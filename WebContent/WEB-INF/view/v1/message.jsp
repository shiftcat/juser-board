<%@ page contentType="text/html;charset=euc-kr"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>

<style>
<!--
	a {text-decoration:none;color:black;}
	a:hover {text-decoration:underline;color:#B2B2B2;}
	a:hover.menu {text-decoration:none;color:red;}
	a:visited.title {text-decoration:underline;color:#FF9900;}
	body {
	background-color: white;
	}
-->
</style>

<script type="text/javascript">
<!--
function nextPage(url)
{
	location = url;
}

setTimeout('nextPage("<bean:write name="redirect"/>")', 1000*3);
//-->
</script>

<div style="margin-top: 30; text-align: center;" >

<table border="0" width="60%" style="background-color: #999999;" cellspacing="1" cellpadding="15">
<tr>
	<td style="background-color: #ffffff">
		<html:messages id="msg" message="true">
			<bean:write name="msg"/>
		</html:messages>
		<br/>
		다음 주소로 이동합니다.
		<html:link href="${requestScope.redirect}" >
		<bean:write name="redirect" />
		</html:link>
	</td>
</tr>
</table>

</div>