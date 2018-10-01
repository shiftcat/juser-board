/***************************************************************
 �� ��ũ��Ʈ ���Ͽ��� �����ϴ� �Լ��� �ݵ�� 'cm_'�� �Լ��� �տ�  ���δ�.
****************************************************************/

//addec by mj.chong 2006.12.06
//tag ��Ģ ���� (%, &, +, <, >, ?, /, \, ', ", =,  \n)
//comma�� ���ε� �����ϴ� ������ ����
var restrictedTagChars = /[\x25\x26\x2b\x3c\x3e\x3f\x2f\x5c\x27\x22\x3d]|(\x5c\x6e)/g;

var IE = false ;
if (window.navigator.appName.indexOf("Explorer") !=-1)
{
	IE = true;
}

// Iframe ������ ȣ���Ͽ� ������ ���̸� �����.
function cm_paperInit(minHeight)
{
	try 
	{
		var timeArray = new Array(200, 500, 1500, 3500, 7000, 12000, 20000);
		//alert("1_cm_paperInit, timeArray.length="+timeArray.length);
		for(var i=0; i < timeArray.length; i++)
			setTimeout('_cm_paperInit(\''+minHeight+'\')', timeArray[i]);
	
		if(self.name!=null && self.name=="papermain")
		{
			top.window.scrollTo(0,0);
		}
	}catch (e) {}
}

// Iframe ������ ȣ���Ͽ� ������ ���̸� �����.
// �ִ� ���� 3�ܱ����� ������¡�� �ϵ��� �Ѵ�.
function _cm_paperInit(minHeight)
{
	try
	{
		//	alert("2_cm_paperInit");
		if(minHeight==null) minHeight = 0;
		if (self.name!=null && self.name!="" && parent!=null 
	    	&& parent._cm_resizeIframe!=null)
	    {
	        parent._cm_resizeIframe(self.name, minHeight);
	    }
	
		if (parent.name!=null && parent.name!=""
			&& (parent.name=="papermain" || parent.name=="mainFrame")
	    	&& parent.parent!=null && parent.parent._cm_resizeIframe!=null && parent.parent._cm_paperInit!=null)
	    {
	        parent.parent._cm_resizeIframe(parent.name, minHeight);
	    }
	
		if (parent.parent.name!=null && parent.parent.name!=""
			&& (parent.parent.name=="papermain" || parent.parent.name=="mainFrame")
	    	&& parent.parent.parent!=null && parent.parent.parent._cm_resizeIframe!=null && parent.parent.parent._cm_paperInit!=null)
	    {
	        parent.parent.parent._cm_resizeIframe(parent.parent.name, minHeight);
	    }
	}catch (e) {}
}

// ��������� ����. cm_paperInit()�� ����Ұ�.
function _cm_resizeIframe(name, minHeight)
{
	//alert("cm_resizeIframe, name="+name);
	if(minHeight==null) minHeight = 0;
    if(name==null || name=="") return;

    try
    {
        if (IE)
            var oBody = document.frames(name).document.body;
		else
            var oBody = document.getElementById(name).contentDocument.body;

		var oIFrame = document.getElementById(name);
            
        var frmWidth  = oBody.scrollWidth;// + (oBody.offsetWidth - oBody.clientWidth);
        var frmHeight = oBody.scrollHeight;// + (oBody.offsetHeight - oBody.clientHeight);

		if(name=="papermain" || name=="mainFrame")
		{
			if(frmHeight >= 500)
				oIFrame.style.height = frmHeight;
			else
				oIFrame.style.height = 500;
			
			var oOpacityDiv = document.getElementById("opacityDiv");
			if(oOpacityDiv!=null)
			{
				oOpacityDiv.style.height = parseInt(oIFrame.style.height) + 200;
			}
		}
		else
		{
			oIFrame.style.height = frmHeight;
		}
		
		// ���ὺŲ ����2���� ���. ����Ʈ�� �������� �����Ѵ�.
        if(applyResizeContentWidth)
        {
        	if(frmWidth > 590) oIFrame.style.width = frmWidth;
        	//else oIFrame.style.width = 590;
        }
        
    }catch (e) {}
}

// �ܺ� �������� Iframe���� ȣ���� ��쿡 �ܺ����������� ȣ���ϱ� ���� �Լ�.
function cm_paperInitExternal(name, minHeight)
{
	try
	{
		//alert("cm_paperInitExternal, name="+name);
		var timeArray = new Array(200, 500, 1500, 3500, 7000, 12000, 20000);
		for(var i=0; i < timeArray.length; i++)
		{
			setTimeout('_cm_paperInitExternal(\''+name+'\',\''+minHeight+'\')', timeArray[i]);
		}
	}catch (e) {}
}

// Iframe ������ ȣ���Ͽ� ������ ���̸� �����.
// �ִ� ���� 3�ܱ����� ������¡�� �ϵ��� �Ѵ�.
function _cm_paperInitExternal(name, minHeight)
{
	try
	{
		if(minHeight==null) minHeight = 0;
	
		if (name!=null && name!='')
	    {
	        _cm_resizeIframe(name, minHeight);
	    }
		if (self.name!=null && self.name!='' 
			&& (self.name=="papermain" || self.name=="mainFrame")
			&& parent!=null && parent._cm_resizeIframe!=null)
	    {
	        parent._cm_resizeIframe(self.name, minHeight);
	    }
	    if (parent.name!=null && parent.name!='' 
			&& (parent.name=="papermain" || parent.name=="mainFrame")
	    	&& parent.parent!=null && parent.parent._cm_resizeIframe!=null)
	    {
	        parent.parent._cm_resizeIframe(parent.name, minHeight);
	    }
	}catch (e) {}
}

/* onLoad Handler */
LOAD_LIST = new Array();
function LH_create()
{
    this.LIST = LOAD_LIST;
    this.add = LH_add;
    this.exec = LH_exec;
}

function LH_add(strExec)
{
    LOAD_LIST[LOAD_LIST.length] = strExec;
}

function LH_exec()
{
    var list_len = LOAD_LIST.length;
    for (var i = 0; i < list_len; i++)
    {
		try {eval(LOAD_LIST[i]); }catch(e){} 
    }
}





function cm_imageZoom(url)
{   
	window.open("/common/util/imageZoom.jsp?url="+url,"imageZoom","scrollbars=no,width=100,height=100");
}

//============================================================================
// �Ʒ��� �Լ��� ����� ��� �����ٶ�. by jhhany.
//============================================================================




function reSize()
{
	var ParentFrame	    =	papermain.document.body;
	var ContentFrame	=	document.all["papermain"];
	ContentFrame.style.height = ParentFrame.scrollHeight + (ParentFrame.offsetHeight - ParentFrame.clientHeight) + 100;
	//ContentFrame.style.width = ParentFrame.scrollWidth + (ParentFrame.offsetWidth - ParentFrame.clientWidth);
}

function paperInit()
{
	parent.reSize();
	//parent.document.location.href = '#';
}

function settop()
{
	/* var topurl = document.location.href;
	if (topurl.indexOf('#') > 0)
		document.location.href = document.location.href;
	else
		document.location.href = document.location.href + '#';*/
	window.scrollTo(0,0);
}


// �˾� ���� �Լ� �ֿ�ö 20050531 �߰�
function open_window(url, name, width, height, feature)
{
    var oWnd;

    if (IE && width < window.screen.width && height < window.screen.height) 
    {
        var windowX = Math.ceil( (window.screen.width  - width) / 2 );
        var windowY = Math.ceil( (window.screen.height - height) / 2 );

        oWnd = window.open(url, name, feature+",width=" + width +",height=" + height+",left="+windowX+",top="+windowY + ",resizable=yes");
    }
    else 
    {
        oWnd = window.open(url, name, feature+",width=" + width +",height=" + height + ",resizable=yes");
    }

    return oWnd;
}
//Object Tag for ActiveX
function diplayDynamicObjectforSkin(_objPath_)
{
	var _object_ = "";
	
	_object_= '<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" width="955" height="200">';
	_object_ += '<param name="movie" value="'+_objPath_+'">';
	_object_ += '<param name="quality" value="high">';
	_object_ += '<param name="wmode" value="transparent">';
	_object_ += '<embed src="'+_objPath_+'" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="955" height="200"></embed>';
	_object_ += '</object>';
	
	document.write(_object_);			
}
function displayStaticFlashMovie()
{
	var _object_ = "";
	_object_= '<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,0,0" id="BluemetalPlayer" width="140" height="37" align="middle" >';
	_object_ += '<param name="allowScriptAccess" value="always" />';
	_object_ += '<param name="movie" value="/common/item/BluemetalPlayer.swf" />';
	_object_ += '<param name="quality" value="high" />';
	_object_ += '<param name="wmode" value="transparent" />';
	_object_ += '<param name="bgcolor" value="#000000" />';
	_object_ += '<embed src="/common/item/BluemetalPlayer.swf" quality="high" wmode="transparent" bgcolor="#000000" width="140" height="37" swLiveConnect=true id="BluemetalPlayer" name="BluemetalPlayer" align="middle" allowScriptAccess="sameDomain" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" />';
	_object_ += '</object>';
	
	document.write(_object_);
}
function displayMultiMediaPlayer()
{
	var _object_ = "";
	
	_object_= '<OBJECT ID="MediaPlayer" Name="MediaPlayer" classid="clsid:22D6F312-B0F6-11D0-94AB-0080C74C7E95" codebase="http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=5,1,52,701" standby="Loading Microsoft Windows Media Player components..." type="application/x-oleobject " bgcolor="DarkBlue" width="320" Height="240" VIEWASTEXT>';
	_object_ += '<PARAM NAME="AutoStart" VALUE="1">';
	_object_ += '<PARAM NAME="AutoSize" VALUE="1">';
	_object_ += '<PARAM NAME="AnimationAtStart" VALUE="0">';
	_object_ += '<PARAM NAME="DisplayMode" VALUE="4">';
	_object_ += '<PARAM NAME="Enabled" VALUE="1">';
	_object_ += '<PARAM NAME="ShowControls" VALUE="0">';
	_object_ += '<PARAM NAME="ShowAudioControls" VALUE="0">';
	_object_ += '<PARAM NAME="ShowDisplay" VALUE="0">';
	_object_ += '<PARAM NAME="ShowGotoBar" VALUE="0">';
	_object_ += '<PARAM NAME="ShowPositionControls" VALUE="0">';
	_object_ += '<PARAM NAME="ShowStatusBar" VALUE="0">';
	_object_ += '<PARAM NAME="Volume" VALUE="0">';
	_object_ += '<PARAM NAME="ShowCaptioning" VALUE="0">';
	_object_ += '<PARAM NAME="TransparentAtStart" value="1">';
	_object_ += '<EMBED invokeURLs="false" type="application/x-mpPlayerOnPlayBtn_div" pluginspage="http://www.microsoft.com/Windows/Downloads/Contents/Products/MediaPlayer/" id="MediaPlayer" Name="MediaPlayer" DisplaySize="4" AutoSize="1" ShowControls="0" ShowDisplay="0" ShowStatusBar="1" autostart="1"></EMBED>';
	_object_ += '</OBJECT>';
	
	document.write(_object_);
}
function loadingUCCModule(programLink, userId)
{
	var _object_ = "";
	
	_object_= '<OBJECT id="MovieUp" name="MovieUp" width ="0"  height ="0" id="AactiveXTest" classid="CLSID:5002118E-45F8-4AAB-95A3-2EF269057B97" codebase="' + url_upblog + '/post/enjsoft/ActiveX/NHNActiveX.cab#version=1,0,0,17" VIEWASTEXT standby="Loading ActiveX....">';
	_object_ += '<PARAM NAME ="ProgramUrl"  VALUE="' + programLink + '">';
	_object_ += '<PARAM NAME ="UserID"  VALUE="' + userId + '">';
	_object_ += '<PARAM NAME ="Code"  VALUE="data2">';
	_object_ += '<PARAM NAME ="Domain"  VALUE="up.media.naver.com">';
	_object_ += '</OBJECT>';
	document.write(_object_);
}

function appendActiveX(_str)
{
	var _object_ = _str;
	document.write(_object_);
}


	String.prototype.trim = function()
	{
	    return this.replace(/(^\s*)|(\s*$)/g, ""); 
	}
function validTag(tagObj, e)
{
	var tagVal = tagObj.value;
	var commacnt = 0;
	var key = window.event ? e.keyCode : e.which; 
	
	if(tagVal.charAt(tagVal.length-1) == ',' && (key == 44 || key == 32))
		return false;
	for(var i=0; i < tagVal.length; i++) {
		if(tagVal.charAt(i) == ',') {
			commacnt++;
		}
		if(commacnt >= 9) {
			alert("�±״� �ִ� 10������ �Է��� �� �ֽ��ϴ�.");
		 	return false; 
		 }
	}
	
	if (key != 0x2C && (key > 32 && key < 48) || (key > 57 && key < 65) || (key > 90 && key < 97)) 
		return false;
}

	function check_tagvalidate(aEvent, input)
	{
		
		var keynum;
		if(typeof aEvent=="undefined") aEvent=window.event;
		if(IE)
		{
			keynum = aEvent.keyCode;
		}
		else
		{
			keynum = aEvent.which;
		}
		//edited by mj.chong 20061206
		//if(keynum == 188 ) {
		//	input.value = BlogTag.validateTagString(input.value);
		//}
		//  %, &, +, -, ., /, <, >, ?, \n, \ |
		var ret = input.value;
		if(ret.match(restrictedTagChars) != null ) {
			 ret = ret.replace(restrictedTagChars, "");
			 input.value=ret;
		}
		//�޸��� �������� ������ �ϳ��� �����.
		re = /[\x2c][\x2c]+/g;
		if(ret.match(re) != null ){
			ret = ret.replace(re, ",");
			input.value=ret;
		}
		highlightMyTag();		

	}

	function check_tagsvalidate(input)
	{
		input.value = BlogTag.validateTagString(input.value);

		//�ߺ��Ǵ� �±� ����
		input.value = BlogTag.eliminateDuplicate(input.value);

		var tagcount = BlogTag.length(input.value);
		highlightMyTag();
		//�±� �� ����
		if(tagcount > 10)
		{
			alert("�±״� �ִ� 10�� ���� �Է��� �����մϴ�.");
			input.value = BlogTag.absoluteTagString(input.value, 10);			
			input.focus();
			
			return;
		}
		

		//�±��� ���� ����
		var bvalidate;
		var tagmaxlength = 100;
		bvalidate = BlogTag.isValidateTagLength(input.value, tagmaxlength);

		if(!bvalidate)
		{
			alert("�±״�  100�� �̻� �Է��� �� �����ϴ�.");
			input.focus();
			return;
		}
	}

var BlogTag =
{
	//��ȿ�� �±׸����� Ȯ��.
	isTagname : function(tagname)
	{
		return tagname.match(restrictedTagChars)==null;
	}
	,

	//�±� ���ڿ��� ��ȿ�ϰ� �����.( �������� ����, ���ӵǴ� �ĸ� ���� )
	validateTagString : function(tagstring)
	{
		var ret = tagstring.replace(restrictedTagChars, "");

		//�޸��� �������� ������ �ϳ��� �����.
		re = /[\x2c]+/g;
		return ret.replace(re, ",");
	}
	,
	
	absoluteTagString : function(tagstring, maxcnt)
	{
		var valitags = BlogTag.validateTagString(tagstring);
	
		var arraytag = valitags.split(",");
		
		var tagnames = "";

		var absolutecnt = arraytag.length;
		if(absolutecnt > maxcnt)
			absolutecnt = maxcnt;
			
		for(var i=0; i< absolutecnt; i++)
		{
			tagnames = tagnames + arraytag[i] + ",";
		}
		tagnames = BlogTag.validateTagString(tagnames);
		
		tagnames = tagnames.substring(0, tagnames.length-1);

		return tagnames;	
	} 
	,

	//�ߺ��Ǵ� �±׸� ���ش�.
	eliminateDuplicate : function(tagstring)
	{
		var valitags = BlogTag.validateTagString(tagstring);
	
		var arraytag = valitags.split(",");
		
		var tagnames = "";
		
		for(var i=0; i<arraytag.length; i++)
		{
			for(var j=0; j<i; j++)
			{
				//�̹� ���� �ϴ� �±׶�� ����.
				if(arraytag[j]==arraytag[i])
				{
					arraytag[i]="";
				}
			}

			tagnames = tagnames + arraytag[i] + ",";
			
		}
		tagnames = BlogTag.validateTagString(tagnames);
		
		tagnames = tagnames.substring(0, tagnames.length-1);

		return tagnames;

	}
	,

	//�±׼� �� ��� �Ѵ�.
	length : function(tagstring)
	{
		var arraytag = tagstring.split(",");
	
		return arraytag.length;
	}
	,

	//�� �±��� ���̸� Ư�� ũ�� ���Ϸ� �����Ѵ�.
	validateTagLength : function(tagstring, maxlen)
	{
		var arraytag = tagstring.split(",");
		var tagnames = '';

		for(var i=0; i<arraytag.length; i++)
		{
			if(arraytag[i].length > maxlen)
			{
				arraytag[i] = arraytag[i].substring(0, maxlen);
			}
			tagnames = tagnames + arraytag[i] + ",";
		}

		tagnames = tagnames.substring(0, tagnames.length-1);

		tagnames = BlogTag.eliminateDuplicate(tagnames);

		return tagnames;
	}
	,

	//�� �±��� ���̰� ��ȿ���� Ȯ���Ѵ�.
	isValidateTagLength : function(tagstring, maxlen)
	{
		var arraytag = tagstring.split(",");

		for(var i=0; i<arraytag.length; i++)
		{
			if(arraytag[i].length > maxlen)
			{
				return false;
			}
		}

		return true;
	}
}	

	/*  Function Equivalent to java.net.URLEncoder.encode(String, "UTF-8")
	    Copyright (C) 2002, Cresc Corp.
	    Version: 1.0
	*/
	function encodeURL(str){
	    var s0, i, s, u;
	    s0 = "";                // encoded str
	    for (i = 0; i < str.length; i++){   // scan the source
	        s = str.charAt(i);
	        u = str.charCodeAt(i);          // get unicode of the char
	        if (s == " "){s0 += "+";}       // SP should be converted to "+"
	        else {
	            if ( u == 0x2a || u == 0x2d || u == 0x2e || u == 0x5f || ((u >= 0x30) && (u <= 0x39)) || ((u >= 0x41) && (u <= 0x5a)) || ((u >= 0x61) && (u <= 0x7a))){       // check for escape
	                s0 = s0 + s;            // don't escape
	            }
	            else {                  // escape
	                if ((u >= 0x0) && (u <= 0x7f)){     // single byte format
	                    s = "0"+u.toString(16);
	                    s0 += "%"+ s.substr(s.length-2);
	                }
	                else if (u > 0x1fffff){     // quaternary byte format (extended)
	                    s0 += "%" + (oxf0 + ((u & 0x1c0000) >> 18)).toString(16);
	                    s0 += "%" + (0x80 + ((u & 0x3f000) >> 12)).toString(16);
	                    s0 += "%" + (0x80 + ((u & 0xfc0) >> 6)).toString(16);
	                    s0 += "%" + (0x80 + (u & 0x3f)).toString(16);
	                }
	                else if (u > 0x7ff){        // triple byte format
	                    s0 += "%" + (0xe0 + ((u & 0xf000) >> 12)).toString(16);
	                    s0 += "%" + (0x80 + ((u & 0xfc0) >> 6)).toString(16);
	                    s0 += "%" + (0x80 + (u & 0x3f)).toString(16);
	                }
	                else {                      // double byte format
	                    s0 += "%" + (0xc0 + ((u & 0x7c0) >> 6)).toString(16);
	                    s0 += "%" + (0x80 + (u & 0x3f)).toString(16);
	                }
	            }
	        }
	    }
	    return s0;
	}
	
	/*  Function Equivalent to java.net.URLDecoder.decode(String, "UTF-8")
	    Copyright (C) 2002, Cresc Corp.
	    Version: 1.0
	*/
	function decodeURL(str){
	    var s0, i, j, s, ss, u, n, f;
	    s0 = "";                // decoded str
	    for (i = 0; i < str.length; i++){   // scan the source str
	        s = str.charAt(i);
	        if (s == "+"){s0 += " ";}       // "+" should be changed to SP
	        else {
	            if (s != "%"){s0 += s;}     // add an unescaped char
	            else{               // escape sequence decoding
	                u = 0;          // unicode of the character
	                f = 1;          // escape flag, zero means end of this sequence
	                while (true) {
	                    ss = "";        // local str to parse as int
	                        for (j = 0; j < 2; j++ ) {  // get two maximum hex characters for parse
	                            sss = str.charAt(++i);
	                            if (((sss >= "0") && (sss <= "9")) || ((sss >= "a") && (sss <= "f"))  || ((sss >= "A") && (sss <= "F"))) {
	                                ss += sss;      // if hex, add the hex character
	                            } else {--i; break;}    // not a hex char., exit the loop
	                        }
	                    n = parseInt(ss, 16);           // parse the hex str as byte
	                    if (n <= 0x7f){u = n; f = 1;}   // single byte format
	                    if ((n >= 0xc0) && (n <= 0xdf)){u = n & 0x1f; f = 2;}   // double byte format
	                    if ((n >= 0xe0) && (n <= 0xef)){u = n & 0x0f; f = 3;}   // triple byte format
	                    if ((n >= 0xf0) && (n <= 0xf7)){u = n & 0x07; f = 4;}   // quaternary byte format (extended)
	                    if ((n >= 0x80) && (n <= 0xbf)){u = (u << 6) + (n & 0x3f); --f;}         // not a first, shift and add 6 lower bits
	                    if (f <= 1){break;}         // end of the utf byte sequence
	                    if (str.charAt(i + 1) == "%"){ i++ ;}                   // test for the next shift byte
	                    else {break;}                   // abnormal, format error
	                }
	            s0 += String.fromCharCode(u);           // add the escaped character
	            }
	        }
	    }
	    return s0;
	}
	
	function setSlidePhoto2Preview(method, sec, width, height, files) {
		document.getElementById("previewArea").innerHTML =
		 '<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0" width="'+ width + '" height="'+ height + '" id="motionPhoto" align="middle">'
		 + '<param name="allowScriptAccess" value="always" />'
		 + '<param name="FlashVars" value="" />'
		 + '<param name="movie" value="/post/editor/slide_photo/swf/motionPhoto.swf?c=0&m=' + method + '&w=' + width + '&h=' + height + '&f=' + files + '&s=' + sec
		 + '" />'
		 + '<param name="quality" value="high" />'
		 + '<param name="bgcolor" value="#ffffff" />'
		 + '<param name="wmode" value="transparent" />'
		 + '<embed wmode="transparent" src="/post/editor/slide_photo/swf/motionPhoto.swf?c=0&m=' + method + '&w=' + width + '&h=' + height + '&f=' + files + '&s=' + sec+ '" quality="high" bgcolor="#ffffff"'
		 + 'width="' + width + '" height="'+ height +'" name="motionPhoto" align="middle" allowScriptAccess="always" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" autostart="true"/></object></div>';
	}
	
	function setSlidePhoto2View(method, sec, width, height, files) {
	var sTag = '' 
	 + '<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0" width="'+ width + '" height="'+ height + '" id="motionPhoto" align="middle">'
		 + '<param name="allowScriptAccess" value="always" />'
		 + '<param name="FlashVars" value="" />'
		 + '<param name="movie" value="http://blog.naver.com/post/editor/slide_photo/swf/motionPhoto.swf?c=0&m=' + method + '&w=' + width + '&h=' + height + '&f=' + files + '&s=' + sec
		 + '" />'
		 + '<param name="quality" value="high" />'
		 + '<param name="bgcolor" value="#ffffff" />'
		 + '<param name="wmode" value="transparent" />'
		 + '<embed wmode="transparent" src="http://blog.naver.com/post/editor/slide_photo/swf/motionPhoto.swf?c=0&m=' + method + '&w=' + width + '&h=' + height + '&f=' + files + '&s=' + sec+ '" quality="high" bgcolor="#ffffff"'
		 + 'width="' + width + '" height="'+ height +'" name="motionPhoto" align="middle" allowScriptAccess="always" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" autostart="true"/></object>';
		document.write (sTag);
	}


	
//--------- added by mj.chong 2006. 8. 18 -----------------
if(!window.ajax) window.ajax = {};

	window.ajax.getXmlHttpRequest = function() {
		var xmlhttp = false
		//Mozila
		if(window.XMLHttpRequest){
			xmlhttp = new XMLHttpRequest()
		} else {
			//IE
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP")
		}
		return xmlhttp;
	} 

	window.ajax.loadData = function(successFunc, path) { 
		var xmlhttp = ajax.getXmlHttpRequest();
		if(xmlhttp!=null) {
			xmlhttp.open("GET",path,true);
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4) {
					if (xmlhttp.status == 200) {
						var data = xmlhttp.responseText;
						successFunc(data);
					} 
				}
			}
			xmlhttp.send(null);
		}
		return false;
	}

	// �˾� ��� ���̾�
	// blogId : blogId
	// afterFunc : �α��� üũ �� ������ function
	// vars : afterFunc �� ������ �Ű� ������
	// params : loginCheck ���� ����ϴ� �������� �⺻ ���� �ٸ� ���� ����ؾ��� ��� ����
	window.ajax.loginCheck = function(afterFunc, blogId, vars, params) { 
		if(blogId == null) {
			alert("Blog id is needed.");
			return;
		}
	
  		var xmlhttp = ajax.getXmlHttpRequest();
		if(xmlhttp!=null) {
			url = "/BlogLoginCheck.nhn?blogId="+blogId;
			callback = "http://nid.naver.com/nidlogin.login?mode=form&template=auto&id="+blogId
				+"&url=http://blog.naver.com/common/util/loginFrameClose.jsp";
			msg = "";
			if(params!=null) {
				if(params['callback']!=null) url = params['callback'];
				if(params['msg']!=null) url = params['msg'];
			} else {
				//checkFrame � ���� ���� ������ ������ ������...������ ������ ����� �޾����� �ʾ� �� ����� �� �Ѥ�
				params = { checkFrame:parent.document.getElementById("checkFrame")
							, loginLayer:parent.document.getElementById("login_layer")
							, bgLayer:parent.document.getElementById("login_layer_bg")};
			}

			if(params['checkFrame']==null) params['checkFrame'] = parent.document.getElementById("checkFrame");
			if(params['loginLayer']==null) params['loginLayer'] = parent.document.getElementById("login_layer");
			if(params['bgLayer']==null) params['bgLayer'] = parent.document.getElementById("login_layer_bg");

			xmlhttp.open("GET", url, true);
			xmlhttp.onreadystatechange = function() {
				if(xmlhttp.readyState == 4 && xmlhttp.status == 200){
					if(xmlhttp.responseText == "LOGOUT") {
						params['bgLayer'].style.display="";
						params['checkFrame'].src = callback + "&pmsg=" + msg ; 
						params['loginLayer'].style.display = "";
						params['loginLayer'].focus();
					}
					else {
						params['bgLayer'].style.display="none";
						params['loginLayer'].style.display = "none";
						afterFunc(vars);
					}
				}
			}
			xmlhttp.send(null);
		}
	}

	// �˾� ������
	// blogId : blogId
	// afterFunc : �α��� üũ �� ������ function
	// vars : afterFunc �� ������ �Ű� ������
	// params : loginCheck ���� ����ϴ� �������� �⺻ ���� �ٸ� ���� ����ؾ��� ��� ����
	window.ajax.loginPop = function(afterFunc, blogId, vars, params) { 
		if(blogId == null) {
			alert("Blog id is needed.");
			return;
		}

  		var xmlhttp = ajax.getXmlHttpRequest();
		if(xmlhttp!=null) {
			url = "/BlogLoginCheck.nhn?blogId="+blogId;
			callback = "/common/util/loginFramePop.jsp?blogId="+blogId;

			xmlhttp.open("GET", url, true);
			xmlhttp.onreadystatechange = function() {
				if(xmlhttp.readyState == 4 && xmlhttp.status == 200){
					if(xmlhttp.responseText == "LOGOUT") {
						//login check pop up
						window.open(callback, "", "top=100, left=100, width=465 height=355, toolbar=no,location=no,directories=no, mebar=no");
					} else {
						afterFunc(vars);
					}					
				}
			}
			xmlhttp.send(null);
		}
	}
//--end of logout data lost protection --------------------
//added by mj.chong to chekc user's OS is Vista or not
function isVista() {
	var agt = navigator.userAgent;
	return agt.indexOf('NT 6') != -1;
}