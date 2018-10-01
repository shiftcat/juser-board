<?
	include("./filemanager_lib.php");

	mysql_close($Dbconn);

	$content  = $_POST["htmlsource"];
	$mainpath = $cfgUploadDir."/temp";
	$filepath = date("md");

	if($content)
	{
		$content  = stripslashes($content);
		$downpath = $mainpath."/".$filepath;

		if(!is_dir($downpath))
		{
			if(substr($downpath, -1, 1) == "/") $downpath = substr($downpath, 0, strlen($downpath) - 1);

			$arr_path = explode("/", $downpath);

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

		$ofolder = opendir($mainpath);

		while($folder = readdir($ofolder))
		{
			if($folder != "." && $folder != "..")
			{
				if(is_dir($mainpath."/".$folder))
				{
					if((int)$folder < (int)$filepath - 1)
					{
						execfolder("del", $mainpath."/".$folder);
					}
				}
			}
		}

		closedir($ofolder);

		$nameonly  = "tmpcontent";
		$fileext   = ".htm";
		$filename  = "tmpcontent.htm";

		if($filename)
		{
	                $checkfile = file_exists($downpath."/".$filename);
			$upcount   = 0;

	                while($checkfile)
			{
				$upcount++;

				$filename  = $nameonly.$checkfile.$fileext;
				$checkfile = file_exists($filepath."/".$filename);

				if($checkfile == 1) $checkfile = $checkfile + $upcount;
			}
		}

		$downfile = $downpath."/".$filename;

		$newFile  = fopen($downfile, "wb");
		fwrite($newFile, $content);
		fclose($newFile);

		@chmod($downfile, 0777);

		if(file_exists($downfile))
		{
			if(eregi("msie", $_SERVER[HTTP_USER_AGENT]) && eregi("5\.5", $_SERVER[HTTP_USER_AGENT]))
			{
				header("content-type: doesn/matter");
				header("content-length: ".filesize("$downfile"));
				header("content-disposition: attachment; filename=\"$filename\"");
				header("content-transfer-encoding: binary");
			}
			else
			{
				header("content-type: file/unknown");
				header("content-length: ".filesize("$downfile"));
				header("content-disposition: attachment; filename=\"$filename\"");
				header("content-description: php generated data");
			}

			header("pragma: no-cache");
			header("expires: 0");
			flush();

			if(is_file("$downfile"))
			{
				$fp = fopen("$downfile", "rb");

				while(!feof($fp))
				{ 
					echo fread($fp, 1024); 
					flush(); 
				}

				fclose($fp); 
				flush();
			}
		}
	}
	else
	{
		echo "<script type='text/javascript'>";
		echo "        parent.DialogClose();";
		echo "</script>";
	}
?>