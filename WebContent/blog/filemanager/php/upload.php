<?
	include("./filemanager_lib.php");

	$Mode        = $_POST["mode"];
	$MCD         = $_POST["mcd"];
	$memberId    = $_POST["memberId"];
	$openFile    = $_POST["openfile"];
	$upFile      = "mediaFile";
	$usefile     = "0";

	$EditorUpDir = $cfgUploadDir."/editor/".$memberId."/";

	if($Mode == "0")
	{
		$useFormat     = $useImageFormat;
		$maxUploadSize = $maxImageSize;
	}
	else if($Mode == "1")
	{
		$useFormat     = $useFlashFormat;
		$maxUploadSize = $maxFlashSize;
	}
	else
	{
		$useFormat     = $useMediaFormat;
		$maxUploadSize = $maxMediaSize;
	}

	// ++ 미사용 이미지 삭제 ++
	$sql = "select member, filename from habyeditor_media where usefile = '0' and regdate < (curdate() - 2)";
	$rs  = mysql_query($sql, $Dbconn);
	$cnt = mysql_affected_rows();

	for($i = 0; $i < $cnt; $i++)
	{
		$row = mysql_fetch_object($rs);

		$deldir = $cfgUploadDir."/editor/".$row->member."/";
		$delimg = $row->filename;

		deletefile($deldir, $delimg);
	}

	mysql_free_result($rs);
	mysql_query("delete from habyeditor_media where usefile = '0' and regdate < (curdate() - 2)", $Dbconn);

	if($_FILES[$upFile][name] && $_FILES[$upFile][error] == 0)
	{
		$newname   = date("Ymd_").mt_rand();
		$upimgname = "";
		$imgwidth  = "";
		$imgheight = "";

		if(is_uploaded_file($_FILES[$upFile][tmp_name]))
		{
			if($Mode == "0")
			{
				$size      = getimagesize($_FILES[$upFile][tmp_name]);
				$imgwidth  = $size[0];
				$imgheight = $size[1];
			}

			$upimgname = upsavefile($EditorUpDir, $_FILES[$upFile], $newname, $maxUploadSize, $useFormat);
		}

		if(!$upimgname)
		{
			echo "<script type='text/javascript'>";
			echo "        alert('[".$useFormat."] 확장자만 업로드 가능');";
			echo "        parent.uploading = false;";
			echo "        parent.ResetFile();";
			echo "</script>";
		}
		else if($upimgname == "err_size")
		{
			echo "<script type='text/javascript'>";
			echo "        alert('최대 업로드 사이즈는 [".$maxUploadSize."MB]');";
			echo "        parent.uploading = false;";
			echo "        parent.ResetFile();";
			echo "</script>";
		}
		else
		{
			if(!file_exists($EditorUpDir."/".$upimgname) || !filesize($EditorUpDir."/".$upimgname))
			{
				echo "<script type='text/javascript'>";
				echo "        alert('[".$upimgname."] 업로드 실패');";
				echo "        parent.uploading = false;";
				echo "        parent.ResetFile();";
				echo "</script>";
			}
			else
			{
				$upimgpath = $cfgUploadPath."/editor/".$memberId."/".$upimgname;

				$sql = "select max(mediaid) as maxnum from habyeditor_media";
				$rs  = mysql_query($sql, $Dbconn);
				$row = mysql_fetch_object($rs);
				$max = $row->maxnum;

				if(!$max) $UploadId = 1;
				else $UploadId = $max + 1;

				mysql_free_result($rs);

				$sql = "insert into habyeditor_media (mcd, mediaid, member, filetype, filepath, filename, imgwidth, imgheight, usefile, openfile, regdate) ";
				$sql = $sql."values('".$MCD."', '".$UploadId."', '".$memberId."', '".$Mode."', '".$upimgpath."', '".$upimgname."', '".$imgwidth."', '".$imgheight."', '".$usefile."', '".$openFile."', curdate())";
				mysql_query($sql, $Dbconn);

				echo "<script type='text/javascript'>";
				echo "        parent.SetUploadMedia('".$upimgpath."', '".$UploadId."', '".$imgwidth."', '".$imgheight."');";
				echo "</script>";
			}
		}
	}
	else
	{
		echo "<script type='text/javascript'>";
		echo "        alert('업로드 실패');";
		echo "        parent.uploading = false;";
		echo "        parent.ResetFile();";
		echo "</script>";
	}

	mysql_close($Dbconn);
?>
