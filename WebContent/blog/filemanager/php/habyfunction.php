<?
	//위지윅 파일 삭제
	function deleteHABYMedia($db, $boardid, $parent, $child, $all)
	{
		if(!$child)
		{
			$child = 0;
		}

		$sql = "select mediaid from habyeditor_usedata where boardid = '".$boardid."'";

		if(!$all)
		{
			$sql .= " and parent = ".$parent." and child = ".$child;
		}

		$rs  = mysql_query($sql, $db);
		$cnt = mysql_affected_rows();

		for($i = 0; $i < $cnt; $i++)
		{
			$row = mysql_fetch_object($rs);

			mysql_query("update habyeditor_media set usefile = usefile - 1, regdate = curdate() where mediaid = ".$row->mediaid." and usefile > 0", $db);
		}

		mysql_free_result($rs);

		$delsql = "delete from habyeditor_usedata where  boardid = '".$boardid."'";

		if(!$all)
		{
			$delsql .= " and parent = ".$parent." and child = ".$child."";
		}

		mysql_query($delsql, $db);
	}

	//위지윅 파일 이동 / 복사
	function copyHABYMedia($db, $boardid, $parent, $child, $mboardid, $mparent, $mchild)
	{
		if(!$child)
		{
			$child = 0;
		}

		if(!$mchild)
		{
			$mchild = 0;
		}

		$sql = "select mediaid from habyeditor_usedata where boardid = '".$boardid."' and parent = ".$parent." and child = ".$child;
		$rs  = mysql_query($sql, $db);
		$cnt = mysql_affected_rows();

		for($i = 0; $i < $cnt; $i++)
		{
			$row = mysql_fetch_object($rs);

			mysql_query("insert into habyeditor_usedata (mediaid, boardid, parent, child) values(".$row->mediaid.", '".$mboardid."', ".$mparent.", ".$mchild.")", $db);
			mysql_query("update habyeditor_media set usefile = usefile + 1 where mediaid = ".$row->mediaid."", $db);
		}

		mysql_free_result($rs);
	}

	//위지윅 파일 사용
	function insertHABYMedia($db, $mediaids, $boardid, $parent, $child)
	{
		if($mediaids)
		{
			if(!$child)
			{
				$child = 0;
			}

			$arrMedia = split(",", trim($mediaids));

			for($i = 0; $i < sizeof($arrMedia); $i++)
			{
				$oMedia = $arrMedia[$i];

				$sql    = "select mediaid from habyeditor_media where mediaid = ".$oMedia."";
				$rs  = mysql_query($sql, $db);
				$cnt = mysql_affected_rows();

				for($j = 0; $j < $cnt; $j++)
				{
					$row = mysql_fetch_object($rs);

					mysql_query("insert into habyeditor_usedata (mediaid, boardid, parent, child) values(".$row->mediaid.", '".$boardid."', ".$parent.", ".$child.")", $db);
					mysql_query("update habyeditor_media set usefile = usefile + 1 where mediaid = ".$row->mediaid."", $db);
				}

				mysql_free_result($rs);
			}
		}
	}
?>