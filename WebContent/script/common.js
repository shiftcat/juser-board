	
	
	
	function newXMLHttpRequest() 
	{
		var xmlreq = false;
		if (window.XMLHttpRequest) {
			xmlreq = new XMLHttpRequest();
		} else if (window.ActiveXObject) {
			try {
				xmlreq = new ActiveXObject("Msxml2.XMLHTTP");
			} catch (e1) {
				try {
					xmlreq = new ActiveXObject("Microsoft.XMLHTTP");
				} catch (e2) {
				}
			}
		}
		return xmlreq;
	}
	
	
	/*==========================================================
	파일명		:func.js
	파일설명		:자바스크립트내에서 사용되는 일련의 함수들
	제  작		:백승현(gazerkr)
	==========================================================*/
	/***********************************************************
	함수명		:replace123(str1, str2, str3)
	처리내용		:str3에서 str1을 str2로 변환하여 반환
	***********************************************************/
	function replace123(str1, str2, str3){
	    var rgexp = new RegExp(str1,"g");
	    return (str3.replace(rgexp, str2));
	}
	
	/***********************************************************
		함수명		:toEntity(strHtml)
		처리내용		:특수 문자(<)를 HTML 엔터티로 변환후 반환
	***********************************************************/
	function toEntity(strHtml){
		return replace123("<","&lt;",strHtml);
	}
	

	function inputDepend() {
		event.returnValue = false;
	}