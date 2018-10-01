String.prototype.trim = function() {
    return this.replace(/^\s*/,'').replace(/\s*$/, ''); ;
}
//action => �⺻1, ����2, ����3
var selectedOptIndex;

	function f_newChange()
	{
		try {
			var board = new Object();
			var to = document.cgiform.to;
			var pcd = document.cgiform.pcode.value;

			board = new parseSelectValue(to, selectedOptIndex);

 			if (board.action == '3')
            	action = '3';
        	else
            	action = '2';
            	
			value = make_value(board.categoryno, board.categoryname, board.openyn, pcd, "", board.listorder, action, board.type,board.blockyn);
			text  = to.options[selectedOptIndex].text;
			change_option(text, value, to, selectedOptIndex);
			to.options[selectedOptIndex].selected = true;
			getData(dataUrl + pcd, pcd, '');
			
			if(pcd=="") {
				directory_openyn('0');
				document.cgiform.directoryyn[1].checked = true;
				return;
			}
			
		} catch (e) {
			alert ("ī�װ��� �������ּ���.");
		}
		
	}
       
	function f_category_select(selectedIndex)
    {
      	if(document.cgiform.to.options[selectedOptIndex]) // ���� ī�װ��� �������� �ʰ� �������� �� 
	        document.cgiform.to.options[selectedOptIndex].selected = false    
        document.cgiform.to.options[selectedIndex].selected = true;                        
        selectedOptIndex = selectedIndex;

        var board = new Object();
        var trdiv = document.getElementById("sortDIV");
        board = new parseSelectValue(document.cgiform.to, selectedIndex);
        
        if (board.type=='S')
        {
          document.cgiform.pcode.disabled = true;
          document.cgiform.subcode.disabled = true;          
          document.cgiform.board_nm.disabled=true;
          document.cgiform.openyn.disabled=true;
          //directory_openyn('0');
          trdiv.style.display="none";
        }
        else
        {
          document.cgiform.pcode.disabled = false;
          document.cgiform.subcode.disabled = false;                    
          document.cgiform.board_nm.disabled=false;
          document.cgiform.directoryyn.disabled = false;
          document.cgiform.openyn.disabled=false;
          trdiv.style.display="";
        }
        board.deleteFrm();
        board.changeFrm();
    }
    
    function f_category_select_first()
    {
        selectedIndex = 0; // first
        selectedOptIndex = selectedIndex;
        document.cgiform.to.options[selectedIndex].selected = true;
        var board = new Object();
        board = new parseSelectValue(document.cgiform.to, selectedIndex);
        
        if (board.type=='S')
        {
          document.cgiform.pcode.disabled = true;
          document.cgiform.board_nm.disabled=true;
          document.cgiform.openyn.disabled=true;
        }
        board.changeFrm();
    }
    
    function listup()
    {
      try{
        var to = document.cgiform.to;
        var pos = selectedOptIndex;
        if (pos == 0) {
            return;
        }
        swap_option(to, pos, pos-1);
        selected_option(to, pos-1);
        previewDivDisplay();
       }
 	 catch(e)
     {
     alert(e);
     alert("�̵��� ī�װ��� �������ּ���.");
     
     }
       
    }
    function listdown()
    {
     try{
        var to = document.cgiform.to;
        var pos = selectedOptIndex;
        if (pos == to.length-1) {
            return;
        }

        swap_option(to, pos, pos+1);
        selected_option(to, pos+1);
        previewDivDisplay();
      }  
     catch(e)
     {
     
     alert("�̵��� ī�װ��� �������ּ���.");
     
     }
    }
    
    function f_category_create(type)
    {
        var to = document.cgiform.to;
        var prev_board;
        var to_pos, board_type, board_sort;

        to_pos = to.length;
        
        if (to_pos > 0) {
          if (type=='B')
          {
            if (to_pos>=40)
            {
              alert("ī�װ��� ���м��� ����  40�� �̻� ���� �� �����ϴ�.");
              return;
            }
            else
            {
            prev_board = new parseSelectValue(to, to_pos-1);
            board_sort = Number(prev_board.listorder)+1;
            }
          } else { 
            prev_board = new parseSelectValue(to, to_pos-1);
            board_sort = Number(prev_board.listorder)+1;
          }
        }
        else {
            board_sort = 1;
        }

        if (type=='B')
        {
          board_nm = "�Խ���";
          make_option(board_nm, make_value('0', board_nm, '1', '0', '', board_sort,'3',type,'0'), to, to_pos);

          selected_option(to, to_pos);
          document.cgiform.pcode.disabled = false;
          document.cgiform.board_nm.disabled=false;
          document.cgiform.directoryyn.disabled = false;
          
          document.cgiform.pcode[0].selected = true;
          document.cgiform.directoryyn[0].checked = true;
          document.cgiform.board_nm.focus();
        }
        else
        {
          board_nm = "-------------------";
          make_option("-------------------", make_value('0', board_nm, '1', '0', '', board_sort,'3',type,'0'), to, to_pos);
          selected_option(to, to_pos);
          document.cgiform.pcode.disabled = true;
          document.cgiform.board_nm.disabled=true;
          document.cgiform.openyn.disabled=true;
          directory_openyn('0');
        }

        previewDivDisplay();
        
    }

	var deletecnt = 0;
    function f_category_delete() {   
     
    try{
		var to = document.cgiform.to;		
		if(to.length <= 1) {
			alert("ī�װ��� ��� ������ �� �����ϴ�.");
			return;
		}
				
		if ((to.options[selectedOptIndex] == null) || (to.options[selectedOptIndex].selected == false)) {			
			alert("������ ī�װ��� �������ּ���.");
			return;	
		} 

		var board = new Object();		
		board = new parseSelectValue(to, selectedOptIndex);

		var msg = 'ī�װ��� �����Ͻðڽ��ϱ�?';
		if(board.type =='S') {
	        msg = '���м��� �����Ͻðڽ��ϱ�?';
		}

        if (board.action == '3') {
            if (confirm(msg)) {
                to.options[selectedOptIndex] = null;
                document.cgiform.board_nm.value='';
                previewDivDisplay();
            }
            else {
                return;
            } 
        }
        else {
            msg = msg + '\n����Ʈ�� ��� �����˴ϴ�.';
	           
            if(deletecnt>=1) {
            	alert("ī�װ� ������ �ϳ����� �����մϴ�.");
            	to.options[selectedOptIndex].selected = false;
            	return;
            }
            else
            {
              var board = new Object();
              board =  new parseSelectValue(to, selectedOptIndex);
              var delCategoryNo =board.categoryno;
              var blockYN =board.blockyn;
              
              // ���м��� ��� �ٷ� ����
              if(board.type =='S') {
              	f_delete_setting(delCategoryNo, blockYN);
              }
     
               if (board.type!="S")
              {
                window.open("/blogadmin/popCategoryDel.jsp?blogId="+ blogId +"&delCategoryNo="+delCategoryNo+"&blockyn="+blockYN,"popCategoryDel","width=346,height=260,scrollbars=yes");
              }
              else
              {
                f_set_moveCategory("0","1");
              }
              previewDivDisplay();
              return;
            }
        }
   }
    catch(e)
     {}
    }
    
    function f_delete_setting(delCategoryNo, blockYN)
    {
     	var to = document.cgiform.to;
     	to.options[selectedOptIndex] = null;
     	document.cgiform.board_nm.value='';
	    document.cgiform.deleteCategory.value =delCategoryNo;
        document.cgiform.blockyn.value =blockYN;
        deletecnt = deletecnt +1;
    }
    
    function f_set_moveCategory(moveCategoryNo,delType)
    {
      document.cgiform.moveCategory.value= moveCategoryNo;
      document.cgiform.delType.value= delType;
    }
    
    function f_category_change(change_name)
    {
        
        change_name = wordchk1(change_name);
	    if(!Check_nonChar(change_name)){
	     	alert("Ư�����ڴ� ����Ͻ� �� �����ϴ�.");
	       	document.cgiform.board_nm.value = change_name.substring(0,change_name.length-1);
	       	document.cgiform.board_nm.focus();
	       	return;
	    }
	    
	    if(LengthCheck(change_name) > 18){
	     	alert("�ѱ� 9�� ���� 18�ڱ����� ����Ͻ� �� �ֽ��ϴ�.");
	       	document.cgiform.board_nm.value = change_name.substring(0,change_name.length-1);
	       	document.cgiform.board_nm.focus();
	       	return;
	    }

  		try{
	        var board = new Object();
	        var to = document.cgiform.to;
	        var value;
	        var text;
	        
	        	
	        board = new parseSelectValue(to, selectedOptIndex);
	        
	        if (board.action == '3')
            	action = '3';
        	else
            	action = '2';
	
	        value = make_value(board.categoryno, change_name, board.openyn, board.directory, board.subdirectory, board.listorder, action, board.type,board.blockyn);
            change_option(change_name, value, to, selectedOptIndex);
	        to.options[selectedOptIndex].selected = true;
	        previewDivDisplay();
	    }
	    catch(e)
	    {
	     
	     alert("�̸��� ������ ī�װ��� �������ּ���.");
	     
	    }
    }
    
    function f_category_openyn(open)
    {
		try {
			var board = new Object();
			var to = document.cgiform.to;
			
			board = new parseSelectValue(to, selectedOptIndex);

			if (open.checked)
			{
				alert("ī�װ��� ������� �ϸ�, ����Ʈ�� ���������� �������\n���� ����Ʈ�� Ÿ���� �� �� ����, �˻������� ���ܵ˴ϴ�.");
			    openyn = '0';
			}
			else
			{
			    openyn = '1';
			}

 			if (board.action == '3')
            	action = '3';
        	else
            	action = '2';
            
			board = new parseSelectValue(to, selectedOptIndex);

			value = make_value(board.categoryno, board.categoryname, openyn, board.directory, board.subdirectory, board.listorder, action, board.type, board.blockyn);
			text  = to.options[selectedOptIndex].text;
            change_option(text, value, to, selectedOptIndex);  				
			to.options[selectedOptIndex].selected = true;
			previewDivDisplay();
		} catch (e) {
			alert ("������� ������ ī�װ��� �������ּ���.");
			open.checked = false;
		}
    }
    
    function parseSelectValue(select, selectedIndex)
    {
        var temp_nm
        var option_value = select.options[selectedIndex].value;

        this.categoryno = option_value.substring(option_value.indexOf('categoryno=') + 11, option_value.indexOf(','));
        option_value = option_value.substring(option_value.indexOf(',') + 1);

        temp_nm = option_value.substring(option_value.indexOf('categoryname=') + 13, option_value.indexOf('openyn=')-1);
        this.categoryname = temp_nm.substring(1, temp_nm.length-1);
        option_value = option_value.substring(option_value.indexOf('openyn='));

        this.openyn  = option_value.substring(option_value.indexOf('openyn=') + 7, option_value.indexOf(','));
        option_value = option_value.substring(option_value.indexOf(',') + 1);

        this.directory = option_value.substring(option_value.indexOf('directory=') + 10, option_value.indexOf(','));
        option_value = option_value.substring(option_value.indexOf(',') + 1);

        this.subdirectory = option_value.substring(option_value.indexOf('subdirectory=') + 13 ,option_value.indexOf(','));
        option_value = option_value.substring(option_value.indexOf(',') + 1);

        this.listorder = option_value.substring(option_value.indexOf('listorder=') + 10 ,option_value.indexOf(','));
        option_value = option_value.substring(option_value.indexOf(',') + 1);

        this.action = option_value.substring(option_value.indexOf('action=') + 7, option_value.indexOf(','));
        option_value = option_value.substring(option_value.indexOf(',') + 1);
        
        this.type = option_value.substring(option_value.indexOf('type=') + 5, option_value.indexOf(','));
        option_value = option_value.substring(option_value.indexOf(',') + 1);
        
        this.blockyn = option_value.substring(option_value.indexOf('blockyn=') + 8);
        
        
        this.changeFrm = f_category_init_change;
        this.deleteFrm = f_category_init_delete;

        return this;
    }
    
    function make_value(categoryno, categoryname, openyn, directory, subdirectory, listorder, action, type, blockyn)
    {
        return "categoryno=" + categoryno + ",categoryname='" + categoryname + "',openyn=" + openyn + ",directory=" + directory + ",subdirectory="+ subdirectory +",listorder="+ listorder+",action="+action+",type="+type+",blockyn="+blockyn;
    }
    function make_option(text, value, target, index)
    {
        target[index] = new Option(text, value);
    }
    
    
    function selected_option(target, pos)
    {
        target.options[pos].selected = true;
        f_category_select(pos);
    }
    function swap_option(target, swap_a, swap_b)
    {
        var board_a = new Object();
        var board_b = new Object();

        board_a = new parseSelectValue(target, swap_a);
        board_b = new parseSelectValue(target, swap_b);

        if (board_a.action == '1') board_a.action = '2';
        if (board_b.action == '1') board_b.action = '2';
        
        var swap_a_value = make_value(board_a.categoryno, board_a.categoryname,board_a.openyn,board_a.directory,board_a.subdirectory,board_b.listorder,board_a.action,board_a.type,board_a.blockyn);        
        var swap_a_text = target.options[swap_a].text;
        var swap_b_value = make_value(board_b.categoryno, board_b.categoryname,board_b.openyn,board_b.directory,board_b.subdirectory,board_a.listorder,board_b.action,board_b.type,board_b.blockyn)
        var swap_b_text = target.options[swap_b].text;
                
		change_option(swap_b_text, swap_b_value, target, swap_a);                
		change_option(swap_a_text, swap_a_value, target, swap_b);                		
			        
    }   
    
    function f_category_update(name, openyn, directory, subdirectory)
    {
        var frm = document.cgiform;
        frm.board_nm.value = name;
        var num = 0;

        if (openyn == '0') {
            frm.openyn.checked = true;
        }
        else {
            frm.openyn.checked = false;
        }

        if(directory!="" && (subdirectory!="" || subdirectory=="")) {
            
            if (directory=="0") directory = "";
        	document.cgiform.pcode.value = directory;					            			
            getData(dataUrl + directory, directory, subdirectory);        			
            document.cgiform.pcode.disabled = false;
            document.cgiform.subcode.disabled = false;   
         	frm.directoryyn[0].checked = true;

        }else{
        	document.cgiform.pcode[0].selected = true;        	
        	document.cgiform.subcode[0].selected = true;             	
        	frm.directoryyn[1].checked = true;        	
    		document.cgiform.pcode.disabled = true;
    		document.cgiform.subcode.disabled = true;    		  
        }

    }


    function f_category_init_change()
    {
      f_category_update(this.categoryname, this.openyn, this.directory, this.subdirectory);
    }

    function f_category_init_delete()
    {
      f_category_update('', '1','','');
    }    
    
    function directory_openyn(open){
	    		
		
		try {
	    	if(open=='1') {
				var board = new Object();
				var to = document.cgiform.to;
  				document.cgiform.pcode.disabled = false;
  				document.cgiform.subcode.disabled = false;  				
  				board = new parseSelectValue(to, selectedOptIndex);
  	
  	 			if (board.action == '3')
  	            	action = '3';
  	        	else
  	            	action = '2';
  	            
  				
  				board = new parseSelectValue(to, selectedOptIndex);
  	
  				value = make_value(board.categoryno, board.categoryname, board.openyn, '0', board.subdirectory, board.listorder, action, board.type,board.blockyn);
  				text  = to.options[selectedOptIndex].text;
                change_option(text, value, to, selectedOptIndex);  				
  		    	to.options[selectedOptIndex].selected = true;
           
	    	}else{
				var board = new Object();
				var to = document.cgiform.to;

				document.cgiform.pcode[0].selected = true;
				document.cgiform.subcode[0].selected = true;
				document.cgiform.pcode.disabled = true;
				document.cgiform.subcode.disabled = true;
				board = new parseSelectValue(to, selectedOptIndex);
	
	 			if (board.action == '3')
	            	action = '3';
	        	else
	            	action = '2';
	            
				var directory = "";
				var subdirectory = "";
				
				board = new parseSelectValue(to, selectedOptIndex);
	
				value = make_value(board.categoryno, board.categoryname, board.openyn, directory, subdirectory, board.listorder, action, board.type,board.blockyn);
				text  = to.options[selectedOptIndex].text;
		        change_option(text, value, to, selectedOptIndex);				
		    	to.options[selectedOptIndex].selected = true;
		
	    	}
	    	
		} catch (e) {
		    	alert ("�����з��� ������ ī�װ��� �������ּ���.");
			open.checked = false;
	   		document.cgiform.pcode.disabled = true;
		}
    }
    
    
var chkCnt = 0;	

function ckConfirm(frm) 
{
	

  if(!chkCnt>0){
		var board = new Object();
		form = document.cgiform;
		var changed = false;
		
		
		for (i=0; i<form.to.length; i++) {
			board = new parseSelectValue(form.to, i);
			
			if (board.action != '1') {
                changed = true;
            }
        }
        
        if (form.deleteCategory.value.length !=0)
            changed = true;

        if (changed && !frm=="1") {
            if (confirm('ī�װ��� ��������� �����Ͻðڽ��ϱ�?')) {
                for (i=0; i<form.to.length; i++) {
                    board = new parseSelectValue(form.to, i);
                    if (board.type=='B')
                    {
                      if (board.categoryname == '') {
                      	alert('ī�װ����� �Է����� �ʾ� ���忡 �����߽��ϴ�.(��Ģ��� ����� �� �����ϴ�)');
                      	return;
                  	  }
                      if(board.directory!="" &&( board.subdirectory=="" || board.subdirectory=="none")) {
                      	alert("�����з��� �������� �ʾ� ���忡 �����߽��ϴ�.");
                      	return;
                      }
                    }
                   	form.to.options[i].selected = true;
                }
                form.action = "AdminCategoryUpdate.nhn";
                form.method = "post";
                form.submit();

            }else{
                return;
            }
        }else if(changed && frm=="1"){
                for (i=0; i<form.to.length; i++) {
                    board = new parseSelectValue(form.to, i);
                    if (board.type=='B')
                    {
                      if (board.categoryname == '') {
                      	alert('ī�װ����� �־��ֽʽÿ�');
                      	f_category_select(i);
                      	form.board_nm.focus();
                      	return;
                  	  }
                      if(board.directory!="" &&( board.subdirectory=="" || board.subdirectory=="none")) {
                      	alert("�����з��� ������ �ֽʽÿ�");
                      	for (j=0; j<form.to.length; j++)
                      		form.to.options[j].selected = false;
                      	f_category_select(i);
                      	return;
                      }
                    }
                    if (board.action == '2' || board.action == '3')
                     	form.to.options[i].selected = true;
                }
                form.action = "AdminCategoryUpdate.nhn";
                form.method = "post";
                form.submit();
        
        }else if(!changed && frm=="1"){
				form.action = "AdminCategoryUpdate.nhn";
                form.method = "post";
                form.submit();
        }
        
        chkCnt++;
  }			
}
function previewDivDisplay() {
    form = document.cgiform;
	var str = '';
	// ????
	str = "<table width=169 cellspacing=0 cellpadding=0 border=0 bgcolor='#ffffff'><tr><td style='padding:7 8 0 8' colspan=3><img src='" + url_images + "/imgs/ico_note.gif' width='10' height='12' align=absmiddle style='margin-bottom:1px'> &nbsp;<span class='c b u'>��ü����</span></td></tr>";
	// ????
	if(form.to.length > 0) {
		for(i=0; i<form.to.length; i++) {
    		var board = new Object();
            board = new parseSelectValue(form.to, i);    
			
			if(board.type=='S') { 
				str += "<tr height=8><td></td></tr><tr><td width=5 nowrap></td><td width=100% height=1 background='" + url_images + "/imgs/bg_dot02.gif'></td><td width=5 nowrap></td></tr><tr height=5><td></td></tr>";
			} 
			else { 
			
				str += "<tr><td style='padding:6 8 0 8;'' colspan=3><img src='" + url_images + "/imgs/ico_note.gif' width='10' height='12' align=absmiddle style='margin-bottom:1px'> &nbsp;<span class=c>"+board.categoryname+" </span>";
				if (board.openyn=='0') {
				str += "<img src='" + url_images + "/imgs/ico_key.gif'>";
				}
				str += "</td></tr>";
			}
		}
	
	}
	// ????
	str += "<tr><td height=8 colspan=3></td></tr></table>";
	document.all.divpreview.innerHTML = str;
       paperInit();
}
	    
	    
	    
//simple AJAX  
   function getXmlHttpRequest() {
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
	
   function getData(url, pcode, code){

   oSubcode = document.getElementById("subcode");
   oSubcodeLen = oSubcode.length;
    
    for (i=1; i<oSubcodeLen; i++) {
      oSubcode.options[oSubcode.length - 1] = null;
    }
     var xmlhttp = getXmlHttpRequest();
	 xmlhttp.open("GET", url, true);
	 	 	 
	 xmlhttp.onreadystatechange = function() {
	    if (xmlhttp.readyState == 4) {
	       if (xmlhttp.status == 200) {

	            var directoryData = xmlhttp.responseText;
	            directoryData = directoryData.trim();   
			    var DataArray = directoryData.split('#');
			    for(i=0;i<(DataArray.length-1);i++)
			    {    

  			        var DataSubArray = DataArray[i].split(',');			    
				    var vOption = document.createElement("OPTION");
				    vOption.value = DataSubArray[0];				    
				    vOption.text = DataSubArray[1];				    
				    oSubcode.options.add(vOption);				    			    
			    }
			    
		    
			    if (code != '' && pcode == code.substring(0,1))
			    {
			       oSubcode.value = code;
			    }
			    else
			    {
			       oSubcode.value = "";			    
			    }
    	            
	       }
	       else
	       {
	            alet("GET Data Failed");
	       }
	     }
	    }
    xmlhttp.send(null);
    return false;
    
  }  
  

 
 	function f_code(subcd)
	{

		var board = new Object();
		var to = document.cgiform.to;		
		var idx = selectedOptIndex;

		board = new parseSelectValue(to, idx);
		
		if (board.action == '3')
            action = '3';
        else
           	action = '2';
		
		value = make_value(board.categoryno, board.categoryname, board.openyn, board.directory, subcd, board.listorder, action);
		text  = to.options[idx].text;
        change_option(text, value, to, idx);
		selected_option(to, idx);
	}
	
	function change_option(text, value, obj, idx)
	{
	    obj.options[idx].value = value;
        obj.options[idx].text = text;  
	}
	    
	    