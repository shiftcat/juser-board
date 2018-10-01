<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>


<div style="margin-top: 15; text-align: center;">

<fieldset style="width: 500px; padding: 15; border-style: solid; border-width: 1px">
	<LEGEND>글분류 설정</LEGEND>
	<html:form action="/category" method="post">
	<table>
	<tr>
		<td>	
			<select name="to" multiple="multiple" style="width:200px; height:200px" onchange="selectedOption(this.selectedIndex)">
			<c:forEach items="${requestScope['categoryList']}" var="cate">
				<option value="cateId=${cate.cateId},cateName=${cate.cateName}">${cate.cateName }</option>
			</c:forEach>
			</select>
		</td>
		<td valign="middle">
			<a href="javascript:listup();">▲</a>
			<p>
			<a href="javascript:listdown();">▼</a>
			<p>
			<input type="button" value="삭제" onclick="rmOption(cgiform.to)"/>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<input type="text" name="cateName"  onkeypress="setKeyCode()"/>
			<input type="button" value="추가" onclick="addOption(cgiform.to)" />
			<input type="button" value="변경" onclick="mdOption(cgiform.to, cgiform.cateName.value)"/>
			<input type="button" value="확인" onclick="OK()" />
		</td>
	</tr>
	</table>
	</html:form>
	
</fieldset>

</div>




<script type="text/javascript" >
<!--
	
	function setKeyCode()
	{
		//alert(event.keyCode);
	}
	
	var selectedOptIndex;
	
	function selectedOption(index)
	{
		var option = cgiform.to.options[index];
		cgiform.cateName.value = option.text;
		selectedOptIndex = index;
		parseValue(option.value);
		//alert(option.text);
	}
	
	function parseValue(value)
	{
		var i = value.indexOf(",", 0);
		var cateIdPair = value.substring(0, i);
		var cateNamePair = value.substring(i+1, value.length);
		this.cateId = cateIdPair.substring( cateIdPair.indexOf("=", 0)+1, cateIdPair.length);
		this.cateName = cateNamePair.substring( cateNamePair.indexOf("=",0)+1, cateNamePair.length);
		//alert(cateId + "\n" + cateName);
	}
	
	
	function listup()
	{
		if(selectedOptIndex > 0) {
			swapOption(cgiform.to, selectedOptIndex, selectedOptIndex-1);
			cgiform.to.selectedIndex = selectedOptIndex-1;
			selectedOptIndex = selectedOptIndex -1;
		}
	}
	
	function listdown()
	{
		if(selectedOptIndex < cgiform.to.options.length-1) {
			swapOption(cgiform.to, selectedOptIndex, selectedOptIndex+1);
			cgiform.to.selectedIndex = selectedOptIndex+1;
			selectedOptIndex = selectedOptIndex +1;
		}
	}
	
	
	function swapOption(target, index_a, index_b)
	{
		var option_a = new Option(target.options[index_a].text, target.options[index_a].value);
		var option_b = new Option(target.options[index_b].text, target.options[index_b].value);
		target.options[index_a] = option_b;
		target.options[index_b] = option_a;
	}
	
	function change_option(text, value, obj, idx)
	{
	    obj.options[idx].value = value;
        obj.options[idx].text = text;  
	}
	
	
	function addOption(target)
	{
		var option = new Option("게시판", "cateId=0,cateName=게시판");
		target.options[target.options.length] = option;
	}
	
	function rmOption(target)
	{
		try {
			target.options[target.selectedIndex] = null;
		} catch(e) {
			alert("삭제할 카테고리를 선택하세요.");
		}
	}
	
	
	function mdOption(target, text)
	{
		try {
			var optionValue = new Object();
			var option = target.options[target.selectedIndex];
			optionValue = new parseValue(option.value);
			option.text = text;
			option.value = makeValue(optionValue.cateId, text);
		}catch(e) {
			alert("변경할 카테고리를 선택하세요.");
		}
	}
	
	function selectAll(target)
	{
		for(var i=0; i < target.options.length; i++) {
			target.options[i].selected = true;
		}
	}
	
	function makeValue(cateId, cateName)
	{
		return "cateId=" + cateId + ",cateName=" + cateName;
	}
	
	
	
	function OK()
	{
		selectAll(cgiform.to);
		cgiform.submit();
	}
//-->
</script>