<?header("Content-Type: text/html; charset=UTF-8");?>
<?
	include("./filemanager_lib.php");

	mysql_close($Dbconn);

	$upFile      = "htmlFile";
	$filepath    = date("md");
	$EditorUpDir = $cfgUploadDir."/temp/".$filepath."/";

	if($_FILES[$upFile][name] && $_FILES[$upFile][error] == 0)
	{
		$uphtmname  = "";
		$refilename = "tmpcontent";

		if(is_uploaded_file($_FILES[$upFile][tmp_name]))
		{
			$uphtmname = upsavefile($EditorUpDir, $_FILES[$upFile], $refilename, $useHtmlFormat);
		}

		if($uphtmname)
		{
			if(!file_exists($EditorUpDir."/".$uphtmname) || !filesize($EditorUpDir."/".$uphtmname))
			{
				echo "<script type='text/javascript'>";
				echo "        parent.uploading = false;";
				echo "        parent.ResetFile();";
				echo "        alert('[".$uphtmname."] upload error');";
				echo "</script>";
			}
			else
			{
				@chmod($EditorUpDir."/".$uphtmname, 0777);

			        $content = ""; 

				$fp = fopen($EditorUpDir."/".$uphtmname, "r");

				while(!feof($fp))
				{ 
					$content .= fread($fp, 1024); 
				}

				fclose($fp);

				if(iconv("UTF-8","UTF-8", $content) != $content)
				{ 
					$content = iconv("EUC-KR","UTF-8", $content); 
				} 

				$content = str_replace("\r\n", "\\n", addslashes($content));

				echo "<script type='text/javascript'>";
				echo "        parent.uploading = false;";
				echo "        parent.ResetFile();";
				echo "        parent.InsertHTML('".$content."');";
				echo "</script>";
			}
		}
		else
		{
				echo "<script type='text/javascript'>";
				echo "        parent.uploading = false;";
				echo "        parent.ResetFile();";
				echo "        alert('file extension not of [".$useHtmlFormat."]');";
				echo "</script>";
		}
	}

?>

