<?
	include("./filemanager_lib.php");

	$Mode        = $_POST["mode"];
	$memberId    = $_POST["memberId"];
	$upFile      = $_POST["mediaPath"];
	$openFile    = $_POST["openfile"];
	$usefile     = "0";

	$EditorUpDir = $cfgUploadDir."/editor/".$memberId."/";

	if($upFile)
	{
		$newname  = date("Ymd_").mt_rand();
		$upimgname = remotesavefile($EditorUpDir, $upFile, $newname);
		$imgwidth  = $_POST["tmpwidth"];
		$imgheight = $_POST["tmpheight"];

		if($upimgname)
		{
			if(!file_exists($EditorUpDir."/".$upimgname) || !filesize($EditorUpDir."/".$upimgname))
			{
				echo "<script type='text/javascript'>";
				echo "        parent.InsertMedia();";
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
				$sql = $sql."values('0', '".$UploadId."', '".$memberId."', '".$Mode."', '".$upimgpath."', '".$upimgname."', '".$imgwidth."', '".$imgheight."', '".$usefile."', '".$openFile."', curdate())";
				mysql_query($sql, $Dbconn);

				echo "<script type='text/javascript'>";
				echo "        parent.SetUploadMedia('".$upimgpath."', '".$UploadId."');";
				echo "</script>";
			}
		}
		else
		{
			echo "<script type='text/javascript'>";
			echo "        parent.InsertMedia();";
			echo "</script>";
		}
	}
	else
	{
		echo "<script type='text/javascript'>";
		echo "        parent.InsertMedia();";
		echo "</script>";
	}

	mysql_close($Dbconn);
?>
