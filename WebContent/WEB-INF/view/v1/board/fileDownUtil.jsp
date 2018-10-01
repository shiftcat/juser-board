<%@ page contentType="text/html;charset=euc-kr" 
				import = "java.io.*,han.juser.dao.board.*" %>

<%
	String strbbs = new String(request.getParameter("bbsId"));
  	
	int bbsId = Integer.parseInt(strbbs);
	
	BoardDAO dao = new BoardOraDAO();
	BoardDTO dto = dao.getContent(bbsId);

    String path = application.getRealPath("/upload/bbs");
    System.out.println(path);
	
    
	File file = new File(path + File.separator + dto.getSystemFiles());

	response.setContentType("application/octet-stream"); 

	String Agent=request.getHeader("USER-AGENT");

	if( Agent.indexOf("MSIE") >= 0 ){
		int i = Agent.indexOf( 'M', 2 );

		String IEV = Agent.substring( i + 5, i + 8 );

		if( IEV.equalsIgnoreCase("5.5") ){
			response.setHeader("Content-Disposition", "filename=" + new String( dto.getOriginalFiles().getBytes("euc-kr"), "8859_1") );
		}else{
			response.setHeader("Content-Disposition", "attachment;filename="+new String(dto.getOriginalFiles().getBytes("euc-kr"),"8859_1"));
		}
	}else{
		response.setHeader("Content-Disposition", "attachment;filename=" + new String( dto.getOriginalFiles().getBytes("euc-kr"), "8859_1") );
	}

	byte b[] = new byte[1024];
	
	if( file.isFile()){ 
		try{ 
			BufferedInputStream fin = new BufferedInputStream( new FileInputStream( file ) );  
			BufferedOutputStream outs = new BufferedOutputStream( response.getOutputStream() );  

			int read = 0;

			while( ( read = fin.read( b ) ) != -1 ){  
				outs.write(b,0,read);
			}

			outs.flush();
			outs.close();  
			fin.close(); 
		}catch( Exception e ){}
	}
%>
