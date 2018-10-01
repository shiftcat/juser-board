<?
	include("./filemanager_lib.php");

	$Mode     = $_POST["mode"];
	$memberId = $_POST["memberId"];
	$page     = $_POST["page"];

	if(!$Mode)     $Mode     = 0;
	if(!$memberId) $memberId = "guest";

	$rs_count    = mysql_query("select count(mediaid) from habyeditor_media where filetype = '".$Mode."' and (member = '".$memberId."' or member = 'guest' or openfile = '1')", $Dbconn); 
	$rs_total    = mysql_fetch_row($rs_count); 
	$totalrecord = $rs_total[0];

	$listsize  = 15;
	$totalpage = floor(((int)$totalrecord - 1) / (int)$listsize) + 1;

	if(!$page)
	{
		$page = 1;
	}
	else if((int)$page < 1)
	{
		$page = 1;
	}
	else if((int)$page > (int)$totalpage)
	{
		$page = (int)$totalpage;
	}

	$prevpage = $page - 1;
	$nextpage = $page + 1;

	$pagesize = 7;
	
	$currentpage = floor(($page - 1) / $pagesize) * $pagesize + 1;

	$usepagelist = "\n";

	if($totalpage > 1)
	{
		if(($currentpage - $pagesize) >= 1)
		{
			$usepagelist .= "   <span onclick=\"PageView(1);\" style=\"cursor:pointer;\">[1]</span>\n";
			$usepagelist .= "   <span onclick=\"PageView(".$prevpage.");\" style=\"cursor:pointer;\">[prev]</span>\n";
		}

		for($i = $currentpage; $i < $currentpage + $pagesize - 1; $i++)
		{
			if($i <= $totalpage)
			{
				if($i == $page)
				{
					$usepagelist .= "   <font style=\"color:#000000\"><b>[".$i."]</b></font>\n";
				}
				else
				{
					$usepagelist .= "   <span onclick=\"PageView(".$i.");\" style=\"cursor:pointer;\">[".$i."]</span>\n";
				}

			} 
		}

		if(($currentpage + $pagesize) <= $totalpage)
		{
			$usepagelist .= "   <span onclick=\"PageView(".$nextpage.");\" style=\"cursor:pointer;\">[next]</span>\n";
			$usepagelist .= "   <span onclick=\"PageView(".$totalpage.");\" style=\"cursor:pointer;\">[".$totalpage."]</span>\n";
		}

		$usepagelist .= "\n";
	}
	if($totalrecord > 0)
	{
		$sql       = "select mediaid, filepath, filename, imgwidth, imgheight, usefile from habyeditor_media where filetype = '".$Mode."' and (member = '".$memberId."' or member = 'guest' or openfile = '1') order by mediaid asc limit ".$listsize * ($page - 1).", ".$listsize."";
		$rs_list   = mysql_query($sql, $Dbconn); 
		$media_cnt = mysql_affected_rows(); 
	}
	else
	{
		$media_cnt = "";
	}
?>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="robots" content="all" />
<title> File List </title>
<style type="text/css">
	body
	{
		margin: 0px;
		padding: 0px;
		background-color: #fcfcfc;
		border-style: none;
	}

	body, td, input, select, textarea, button
	{
		font-size: 9pt;
		font-family: tahoma, Gulim;
	}
</style>
<script type="text/javascript">
	function PageView(page)
	{
		document.frmPage.page.value = page;
		document.frmPage.submit();
	}
</script>
</head>
<body ondragstart="return false" onselectstart="return false" oncontextmenu="return false">
<table width="230" border="0" cellspacing="0" cellpadding="0">

<?
	if($media_cnt)
	{
		for($media = 0; $media < $media_cnt; $media++)
		{
			$rs_media = mysql_fetch_row($rs_list);

			if($media % 2)
			{
				$bgcolor = "threedface";
			}
			else
			{
				$bgcolor = "buttonhighlight";
			}

			$fileinfo = pathinfo($rs_media[2]);
			$fileext  = $fileinfo[extension];

			switch(strtolower($fileext))
			{
				case "gif"  :
					$typeicon = "gif.gif"; break;
				case "jpg"  :
				case "jpeg" :
					$typeicon = "jpg.gif"; break;
				case "avi"  :
					$typeicon = "avi.gif"; break;
				case "mpg"  :
				case "mpeg" :
					$typeicon = "mpg.gif"; break;
				case "swf"  :
					$typeicon = "swf.gif"; break;
				default     :
					$typeicon = "ect.gif"; break;
			}


?>

 <tr onclick="parent.PreviewUpdate('<?=$rs_media[1]?>', '<?=$rs_media[0]?>', '<?=$rs_media[3]?>', '<?=$rs_media[4]?>')" style="cursor:pointer;">
  <td width="20" style="background-color:<?=$bgcolor?>;"><img src="../icons/<?=$typeicon?>" align="absmiddle"> </td>
  <td height="20" style="font-size: 8pt; font-family: tahoma; background-color:<?=$bgcolor?>;" onmouseover="this.style.textDecoration='underline';"  onmouseout="this.style.textDecoration='none';"><?=$rs_media[2]?></td>
  <td height="20" align="right" style="font-size: 8pt; font-family: tahoma; background-color:<?=$bgcolor?>;"><?=$rs_media[5]."&nbsp;"?></td>
 </tr>

<?
		}
	}
	else
	{
?>

 <tr>
  <td height="20" align="center" style="font-size: 8pt; font-family: tahoma; background-color:buttonhighlight;">No file</td>
 </tr>

<?
	}
?>

</table>
<table width="230" border="0" cellspacing="0" cellpadding="0">
 <tr>
  <td height="30" align="center" style="font-size: 8pt; font-family: tahoma; background-color:#fcfcfc;">
   <form name="frmPage" action="./filelist.php" method="post">
    <input type="hidden" name="mode" value="<?=$Mode?>">
    <input type="hidden" name="memberId" value="<?=$memberId?>">
    <input type="hidden" name="page">
   </form>
   <?=$usepagelist?>
  </td>
 </tr>
</table>
</body>
</html>




