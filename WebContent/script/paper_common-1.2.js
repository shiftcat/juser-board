
var IE = false ;
if (window.navigator.appName.indexOf("Explorer") !=-1)
{
	IE = true;
}

function reSize()
{
	    var ParentFrame	    =	null;
	    if(IE){
	    	ParentFrame = papermain.document.body;
	    }else{
	    	ParentFrame = papermain.contentDocument.body;
	    }
	    var ContentFrame	=	document.all["papermain"];
	    ContentFrame.style.height = ParentFrame.scrollHeight + (ParentFrame.offsetHeight - ParentFrame.clientHeight) + 100;
		//ContentFrame.style.width = ParentFrame.scrollWidth + (ParentFrame.offsetWidth - ParentFrame.clientWidth);
		
}
function reSizeG()
{
	try
	{
	    var ParentFrame	    =	gameiframe.document.body;
	    var ContentFrame	=	document.all["gameiframe"];
	    ContentFrame.style.height = ParentFrame.scrollHeight + (ParentFrame.offsetHeight - ParentFrame.clientHeight);
		//ContentFrame.style.width = ParentFrame.scrollWidth + (ParentFrame.offsetWidth - ParentFrame.clientWidth);		
	}catch(e){}
}
function reSizeF()
{
	    var ParentFrame	    =	papermain.document.body;
	    var ContentFrame	=	document.all["papermain"];
	    ContentFrame.style.height = ParentFrame.scrollHeight + (ParentFrame.offsetHeight - ParentFrame.clientHeight);
		
}

function reSize0()
{
	    var ParentFrame	    =	papermain.document.body;
	    var ContentFrame	=	document.all["papermain"];
	    ContentFrame.style.height = ParentFrame.scrollHeight + (ParentFrame.offsetHeight - ParentFrame.clientHeight);
		//ContentFrame.style.width = ParentFrame.scrollWidth + (ParentFrame.offsetWidth - ParentFrame.clientWidth);

		//parent.document.location.href = parent.document.location.href + '#';
}

function reSize2(fno)
{
	try{
	    var ParentFrame	    =	null;
	    if(IE){
	    	ParentFrame = eval("CommentFrm" + fno +  ".document.body");
	    }else{
	    	ParentFrame = document.getElementById("CommentFrm" + fno).contentDocument.body;
	    }
	    var ContentFrame	=	eval("document.all.CommentFrm" +fno);

	    ContentFrame.style.height = ParentFrame.scrollHeight + (ParentFrame.offsetHeight - ParentFrame.clientHeight);
		parent.reSize0();
	}catch(e){}
}

function reSize3(fno)
{
	    
	    var ParentFrame	    =	eval("EventCommentFrm" + fno +  ".document.body");
	    var ContentFrame	=	eval("document.all.EventCommentFrm" +fno);

	    ContentFrame.style.height = ParentFrame.scrollHeight + (ParentFrame.offsetHeight - ParentFrame.clientHeight);
		parent.reSize();
}
function reSize4(obj)
{
	    var ParentFrame	    =	eval(obj+".document.body");
	    var ContentFrame	=	eval("document.all."+obj);
	    ContentFrame.style.height = ParentFrame.scrollHeight + (ParentFrame.offsetHeight - ParentFrame.clientHeight);
}

function paperInit()
{
	    parent.reSize();
	    //parent.document.location.href = '#';
}
function paperInit2()
{
	    parent.reSizeF();
	    //parent.document.location.href = '#';
}
function paperInit_sub(fno)
{
	    parent.reSize2(fno);
	 //parent.document.location.href = parent.document.location.href + '#';
}
function paperInit_eventcomment(fno)
{
	    parent.reSize3(fno);
	 //parent.document.location.href = parent.document.location.href + '#';
}

function strLenCk(str)
{
  var len = 0;
  for (var i=0;i<str.length; i++) {
    var n = str.charCodeAt(i);
    if ((n>= 0)&&(n<256)) {
      len ++;
    } else {
      len += 2;
	}
  }
  return len;
}


