
// 두번째 파라미터로 넘긴 특정 문자 체크 
function isCharaterCheck(arg_v,arg_ch)
{
	for (i=0; i < arg_v.length; i++)
	{
		var substr = arg_v.substring(i, i+1);		
		if (arg_ch.indexOf(substr) < 0) 
			return false;	
		
	}
	
	return true;
}
// 영숫자 판별
function isAlphaNumCheck(arg_v)
{
	var alpha_num_Str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	for (i=0; i < arg_v.length; i++)
	{
		var substr = arg_v.substring(i, i+1);		
		if (alpha_num_Str.indexOf(substr) < 0) 
			return false;	
		
	}
	
	return true;
}

// 숫자 체크
function isNumber(arg) {
	for (i =0 ; i < arg.length; i++) {
  	
	  	if (arg.charCodeAt(i) < 48 || arg.charCodeAt(i) > 57) {
	  		return false;
	  	}
	}
	return true;
}

//문자열 안의 공백 지우기
function deleteSpace(str) { 
	var out = "";

	for (common_i = 0; common_i < str.length; common_i++) { 
		if (str.charAt(common_i) == " ") { 
			continue;
		}
		out += str.charAt(common_i); 
	} 
	return out; 
}


	// 문자 체크
function Check_nonCharNumeric(id_text)
{
		var nonchar = '~`!@#$%^&*()-_=+\|<>?,./;:"';
		var numeric = '1234567890';
		var nonkorean = nonchar+numeric; 
		
		var i ; 
		for ( i=0; i < id_text.length; i++ )  {
			if( nonkorean.indexOf(id_text.substring(i,i+1)) > 0) {
				break ; 
			}
		}
		if ( i != id_text.length ) {
			return false ; 
		}
		else{
			return true ;
		} 

		return false;
}
	// 특수문자 체크
function Check_nonChar(id_text)
{
		//var nonchar = '~`!@#$%^&*()-_=+\|<>?,./;:"';
		var nonchar = '`@#$%&\|<>;"';

		var i ; 
		for ( i=0; i < id_text.length; i++ )  {
			if( nonchar.indexOf(id_text.substring(i,i+1)) > 0) {
				break ; 
			}
		}
		if ( i != id_text.length ) {
			return false ; 
		}
		else{
			return true ;
		} 

		return false;
}

//문자열 개수
function LengthCheck(data) {

		var count = 0;
		for ( var i=0; i < data.length; i++ ) {
			if( data.charCodeAt(i) < 127 )
				count++;
			else
				count = count + 2;
		}
		
		return count;
}
	
//Only Korean
function Check_onlyKorean(id_text){
	for ( var i=0; i < id_text.length; i++ ) {
		if ( id_text.charCodeAt(i) < 0xAC00 || id_text.charCodeAt(i) > 0xD7A3){
			if (( id_text.charCodeAt(i) < 12593 || id_text.charCodeAt(i) > 12643 ) && ( id_text.charCodeAt(i) != 32)) {
				return true;
			}
		}
	}	
	return false;
}	
	// 태그 체크
function Check_nonTag(id_text)
{
		var opentag = '><';
		var i ; 
		for ( i=0; i < id_text.length; i++ )  {
			if( opentag.indexOf(id_text.substring(i,i+1)) > 0) {
				break ; 
			}
		}
		if ( i != id_text.length ) {
			return false ; 
		}
		else{
			return true ;
		} 

		return false;
}

function checkTag(str)
{
  var regArr = new Array('(<)[\\s]*(table)' , '(<)[\\s]*(\\/)[\\s]*(table)' ,
                         '(<)[\\s]*(tr)'    , '(<)[\\s]*(\\/)[\\s]*(tr)'    ,
                         '(<)[\\s]*(td)'    , '(<)[\\s]*(\\/)[\\s]*(td)'    ,
                         '(<)[\\s]*(div)'   , '(<)[\\s]*(\\/)[\\s]*(div)'   ,
                         '(<)[\\s]*(iframe)', '(<)[\\s]*(\\/)[\\s]*(iframe)',
                         '(<)[\\s]*(script)', '(<)[\\s]*(\\/)[\\s]*(script)',
                         '(<)[\\s]*(embed)' , '(<)[\\s]*(\\/)[\\s]*(embed)');
  var strMyRe;
  strMyRe = '/';
  for( i=0; i<regArr.length; i++ )
  {
    if( i == regArr.length-1 )
    {
      strMyRe += regArr[i];
      strMyRe += '/gi';
    }
    else
    {
      strMyRe += regArr[i];
      strMyRe += '|';
    }
  }
  myRe = eval(strMyRe);
  //alert(strMyRe);
  reArray = str.match(myRe);
  if( reArray != null )
  {
    var alvar = "";
    for( j=0; j<reArray.length; j++ )
    {
      if( j != reArray.length-1 )
        alvar += reArray[j] + ">,";
      else
        alvar += reArray[j] + ">";
    }
    alert(alvar + " 과 같은 태그는 사용하실 수 없습니다.");
    return false;
  }
  else
  {
    return true;
  }
  //if( myRe.test(str) )
  //  alert("사용 못 할 태그가 들어있다.");
  // else
  //  alert("사용 못 할 태그가 들어있지 않다.");
}

// 팝업 윈도우
function MM_open(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}

//////////////////////////////////////////////////
//  appended by kozidane 2003.05.01
//////////////////////////////////////////////////
function toLoginPage()
{
  var url = "http://nid.naver.com/nidlogin.login?mode=form&url=" + location.href;
  top.location.href = url;
}

//////////////////////////////////////////////////////////////////////////////
// CSS definition for button text
//////////////////////////////////////////////////////////////////////////////
document.write ("\
<style type=text/css>\
#white9 { line-height: 11pt ; font-size: 9pt ; text-decoration: none ; color: #FFFFFF ;}\
#white9b { line-height: 11pt ; font-size: 9pt ; font-weight: bold ; text-decoration: none ; color: #FFFFFF ;}\
#black9 { line-height: 11pt ; font-size: 9pt ; text-decoration: none ; color: #000000 ;}\
#black9b { line-height: 11pt ; font-size: 9pt ; font-weight: bold ; text-decoration: none ; color: #000000 ;}\
#gulim8 { line-height: 11pt ; font-size: 8pt ; text-decoration: none ; color: #000000 ;}\
#gulim9 { line-height: 11pt ; font-size: 9pt ; text-decoration: none ; color: #000000 ;}\
#gulim9b { line-height: 11pt ; font-size: 9pt ; font-weight: bold ; text-decoration: none ; color: #000000 ;}\
#gulim10 { line-height: 11pt ; font-size: 10pt ; text-decoration: none ; color: #000000 ;}\
#gulim10b { line-height: 11pt ; font-size: 10pt ; font-weight: bold ; text-decoration: none ; color: #000000 ;}\
#gulim10W { line-height: 11pt ; font-size: 10pt ; text-decoration: none ; color: #FFFFFF ;}\
#gulim10Wb { line-height: 11pt ; font-size: 10pt ; font-weight: bold ; text-decoration: none ; color: #FFFFFF ;}\
\
</style>\
")

		//document.domain = "naver.com";