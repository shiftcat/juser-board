	
	
	
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
	���ϸ�		:func.js
	���ϼ���		:�ڹٽ�ũ��Ʈ������ ���Ǵ� �Ϸ��� �Լ���
	��  ��		:�����(gazerkr)
	==========================================================*/
	/***********************************************************
	�Լ���		:replace123(str1, str2, str3)
	ó������		:str3���� str1�� str2�� ��ȯ�Ͽ� ��ȯ
	***********************************************************/
	function replace123(str1, str2, str3){
	    var rgexp = new RegExp(str1,"g");
	    return (str3.replace(rgexp, str2));
	}
	
	/***********************************************************
		�Լ���		:toEntity(strHtml)
		ó������		:Ư�� ����(<)�� HTML ����Ƽ�� ��ȯ�� ��ȯ
	***********************************************************/
	function toEntity(strHtml){
		return replace123("<","&lt;",strHtml);
	}
	

	function inputDepend() {
		event.returnValue = false;
	}