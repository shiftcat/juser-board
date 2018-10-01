
	/* editor config object */
	var HABYconfig = function (eId, user)
	{
		/* default config */
		var __cfg            = new Array();

		/* guest userid */
		__cfg.MemberId       = 'guest';

		/* editor width */
		__cfg.Width          = '100%';

		/* editor height */
		__cfg.Height         = '300';

		if(_GetE(document, eId, 'TEXTAREA', false))
		{
			var oElement = _GetE(document, eId, 'TEXTAREA', false);

			if(oElement.style.width)
			{
				var wPixel = oElement.style.width.match(/^\s*(\d+)px\s*$/i);

				if(wPixel)
				{
					__cfg.Width = wPixel[1];
				}
				else
				{
					var wPercent = oElement.style.width.match(/^\s*(\d+)%\s*$/i);

					if(wPercent)
					{
						if(oElement.offsetWidth > 0)
						{
							__cfg.Width = oElement.offsetWidth;
						}
					}
					else
					{
						__cfg.Width = oElement.style.width;
					}
				}
			}
			else
			{
				if(oElement.offsetWidth > 0)
				{
					__cfg.Width = oElement.offsetWidth;
				}
			}

			if(oElement.style.height)
			{
				var hPixel = oElement.style.height.match(/^\s*(\d+)px\s*$/i);

				if(hPixel)
				{
					__cfg.Height = hPixel[1];
				}
				else
				{
					var hPercent = oElement.style.height.match(/^\s*(\d+)%\s*$/i);

					if(hPercent)
					{
						if(oElement.offsetHeight > 0)
						{
							__cfg.Height = oElement.offsetHeight;
						}
					}
					else
					{
						__cfg.Height = oElement.style.height;
					}
				}
			}
			else
			{
				if(oElement.offsetHeight > 0)
				{
					__cfg.Height = oElement.offsetHeight;
				}
			}
		}

		/* serverscript language */
		__cfg.ServerLanguage = 'asp';

		/* toggle enable html element id */
		__cfg.ToggleHtmlId   = null;

		/* toggle enable html element value */
		__cfg.ToggleHtmlOn   = null;

		/* editor insert time */
		__cfg.CommTime       = 50;

		/* editor skin */
		__cfg.Skin           = 'default';

		/* editor stylesheet */
		__cfg.MainStyle      = 'main.css';

		/* show border stylesheet */
		__cfg.BorderStyle    = 'borders.css';

		/* toggle source view */
		__cfg.HtmlMode       = true;

		/* editor height auto resize */
		__cfg.AutoResize     = false;

		/* editor size drag and drop resize */
		__cfg.DragResize     = 1;   // 0 == none, 1 == height, 2 == all

		/* layer absolute position value */
		__cfg.PositionValue  = 'relative';   // relative or absolute

		/* auto content value update */
		__cfg.AutoUpdate     = true;

		/* html convert xhtml */
		__cfg.EnableXHTML    = 'html';   // xml, html, none

		/* html format */
		__cfg.EnableFormat   = true;

		/* IE enter insert tagname */
		__cfg.ReplaceEnter   = 'div'; // p, br, div(p margin:0px)

		/* user enable contextmenu */
		__cfg.IsContextMenu  = true;

		/* media tab buton view */
		__cfg.ViewMediaTab   = true;

		/* max media width ( 0 == none ) */
		__cfg.MaxMediaWidth  = 0;

		/* editor rqueues */
		__cfg.IsRqueue       = false; /* default : not change */
		__cfg.Rqueues        = new Array();

		/* rqueue maxindex */
		__cfg.MaxRqueueIndex = 20;

		/* toolbar listset */
		__cfg.ToolbarSet     = 'Default';

		/* user input toolbalist */
		__cfg.MenuButton     = null;

		/* user emoticon count */
		__cfg.EmoticonCount  = 20;

		/* fontname list */
		__cfg.FontName       = ['GulimChe', 'DotumChe' , 'BatangChe', 'GungsuhChe', 'Arial', 'Courier New', 'Tahoma', 'Times New Roman', 'Verdana'];

		/* fontsize list */
		__cfg.FontSize       = ['1', '2', '3', '4', '5', '6', '7'];

		/* fontstyle list */
		__cfg.FontFormat     = ['Pre', 'Address', 'H1', 'H2', 'H3', 'H4', 'H5', 'H6'];

		/* enter mode list */
		__cfg.EnterMode      = ['P', 'BR', 'Div'];

		/* contextmenu list */
		__cfg.ContextMenu    = [
			['Cut', 'Copy', 'Paste', 'Delete'], ['SelectAll'], ['InsertImage', 'CreateLink', 'UnLink']
		];

		__cfg.SubContextMenu = [
			['MergeCellCol', 'MergeCellRow', 'SplitCellCol', 'SplitCellRow', 'line', 'ModifyCell'],
			['InsertRow', 'DeleteRow'],
			['InsertColumn', 'DeleteColumn']
		];

		/* user config setup */
		if(user)
		{
			for(var params in user)
			{
				if(user[params] !== '')
				{
					__cfg[params] = user[params];
				}
			}
		}

		/* user config save */
		for(var params in __cfg)
		{
			this[params] = __cfg[params];
		}

		/* color list */
		this.BasicColor = new Array();

		/* basiccolor list */
		this.BasicColor["Default"] = [
			['000000', '993300', '333300', '003300', '003366', '000080', '333399', '333333'],
			['800000', 'ff0066', '808000', '008000', '008080', '0000ff', '666699', '808080'],
			['ff0000', 'ff9900', '99cc00', '339966', '33cccc', '3366ff', '800080', '999999'],
			['ff00ff', 'ffcc00', 'ffff00', '00ff00', '00ffff', '00ccff', '993366', 'c0c0c0'],
			['ff99cc', 'ffcc99', 'ffff99', 'ccffcc', 'ccffff', '99ccff', 'cc99ff', 'ffffff']
		];

		/* usercolor list */
		this.BasicColor["User"] = [
			['ff0000', 'ff8000', 'ffff00', '80ff00', '00ff00', '00ff80', '00ffff', '0080ff'],
			['0000ff', '8000ff', 'ff00ff', 'ff0080', 'ffffff', 'ffffff', 'ffffff', 'ffffff']
		];

		/* editor basepath */
		this.BasePath        = '.';

		/* editor stylesheet path */
		this.StylePath       = this.BasePath + '/skin/' + this.Skin + '/style/';

		/* editor icons path */
		this.IconsPath       =  this.BasePath + '/skin/' + this.Skin + '/icons/';

		/* editor dialogbox path */
		this.DialogPath      =  this.BasePath + '/dialog/';

		/* editor zindex */
		this.BoxIndex        = 1000;

		/* layer zindex */
		this.PlusIndex       = 10000;
		this.MinusIndex      = 10000;

		/* show basic border */
		this.ShowBorders     = true;

		/* undo begin index - not change - */
		this.RqueueIndex     = 0;

		/* fullscreen mode  - not change - */
		this.FullScreen      = false;

		/* active editor - not change - */
		this.IsActive        = false;

		if(_IsOpera)
		{
			this.IsContextMenu = false;
		}
	}
