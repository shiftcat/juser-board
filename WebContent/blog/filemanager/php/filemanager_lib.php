<?
  /* upload config */
	$cfgUploadDir  = "D:/MsSQLHm/habyboard/upload";
	$cfgUploadPath  = "/habyboard/upload";

	$maxImageSize   = 1; /* 단위 MB */
	$maxFlashSize   = 2; /* 단위 MB */
	$maxMediaSize   = 5; /* 단위 MB */
	$useImageFormat = "|.jpg|.gif|.bmp|.png|";
	$useFlashFormat = "|.swf|";
	$useMediaFormat = "|.asf|.avi|.mpg|.mp3|.wma|.wmv|";
	$useHtmlFormat  = "|.htm|.txt|";

  /* db config */
	$Dbconn = mysql_connect("localhost", "xxxxxxxx", "xxxxxxxx") or die("DB 연결 실패");
	mysql_select_db("xxxxxxxx", $Dbconn);

  /* command folder */
	function execfolder($action, $path)
	{
		switch($action)
		{
			case "make" :
				if(is_dir($path))
				{
					return;
				}
				else
				{
					@mkdir("$path", 0755);
					@exec("chmod 777 $path");
				}

				break;
			case "del" :
				if(!is_dir($path))
				{
					return;
				}
				else
				{
					@rmdir("$path");
				}

				break;
		}
				
	}

  /* same file check */
	function newfilename($filepath, $filename, $renameonly, $filetype)
	{
		$nowname  = pathinfo($filename);
		$nameonly = basename($filename, ".".$nowname[extension]);
		$fileext  = $nowname[extension];

		if($renameonly)
		{
			$nameonly = $renameonly;
		}

		$filename = $nameonly.".".$fileext;

		if(!is_dir($filepath))
		{
			if(substr($filepath, -1, 1) == "/") $filepath = substr($filepath, 0, strlen($filepath) - 1);

			$arr_path = explode("/", $filepath);

			for($i=0; $i < count($arr_path); $i++)
			{
				if(!$arr_path[$i]) continue;

				if($i == 0)
				{
					$onfolder = $arr_path[$i];
				}
				else
				{
					$onfolder .= "/".$arr_path[$i];

					execfolder("make", $onfolder);
				}
			}
		}

		if(!strchr($filetype, "|.".strtolower($fileext)."|"))
		{
			$filename = "";
		}

		if($filename)
		{
	                $checkfile = file_exists($filepath."/".$filename);
			$upcount   = 0;

	                while($checkfile)
			{
				$upcount++;

				$filename  = $nameonly.$checkfile.".".$fileext;
				$checkfile = file_exists($filepath."/".$filename);

				if($checkfile == 1) $checkfile = $checkfile + $upcount;
			}
		}

		return $filename;
	}


  /* upload file */
	function upsavefile($filepath, $filename, $renameonly, $maxsize, $filetype)
	{
		$nowupname  = $filename[name];
		$upfilesize = $filename[size];

		if($maxsize > 0 && ($upfilesize / 1048576) > $maxsize)
		{
			return "err_size";
		}
		else
		{
	                $upsavename = newfilename($filepath, $nowupname, $renameonly, $filetype);

			if($upsavename)
			{
				$upfilepath = $filepath."/".$upsavename;

				move_uploaded_file($filename[tmp_name], $upfilepath);
			}

			return $upsavename;
		}
	}

  /* url upload file */
	function remotesavefile($filepath, $filename, $renameonly)
	{
		$tmpupname  = pathinfo($filename);
		$nowupname  = basename($filename, "/".$tmpupname[extension]);
		$tmptype    = pathinfo($nowupname);
		$filetype   = "|.".$tmptype[extension]."|";
		$upsavename = newfilename($filepath, $nowupname, $renameonly, $filetype);

		if($upsavename)
		{
			$upfilepath = $filepath."/".$upsavename;

			$url_info = parse_url($filename); 

			$host = $url_info["host"];
			$port = $url_info["port"];

			if($port == 0) $port = 80;

			$path = $url_info["path"];

			if($url_info["query"] != "")	$path .= "?".$url_info["query"];

			$fp = fsockopen($host, $port, $errno, $errstr, 30);

			if($fp)
			{
   				fputs($fp,"GET ".$path." HTTP/1.0\r\n"); 
				fputs($fp,"Host: ".$host."\r\n"); 
				fputs($fp,"User-Agent: PHP Script\r\n"); 
				fputs($fp,"Connection: close\r\n\r\n"); 

			        $contents        = ""; 
			        $response_header = ""; 

				while(trim($buf = fgets($fp, 1024)) != "")
				{ 
					$response_header .= $buf; 
			        }

				while(!feof($fp))
				{
					$contents .=  fgets($fp, 1024); 
				}

				fclose($fp); 

				$newFile = fopen($upfilepath, "wb");
				fwrite($newFile, $contents);
				fclose($newFile);

				@chmod($upfilepath, 0777);

				return $upsavename;
			}
			else
			{
				return "";
			}
		}

		return "";
	}

  /* delete file */
	function deletefile($filepath, $filename)
	{

		$deletefilepath = $filepath."/".$filename;

		if(file_exists($deletefilepath))
		{
			@unlink($deletefilepath);
		}
	}

  /* alret msg box */
	function showmsgbox($msg='', $move='')
	{
		echo "<script>";

		if($msg) echo 'alert("'.$msg.'");';

		if($move)
		{
			if($move == "close") 'window.close();';
			else echo 'location.replace("'.$move.'");';
		} 
		else echo 'history.go(-1);';

		echo "</script>";
	}
?>
