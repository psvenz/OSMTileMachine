package osmTileMachine;

public class SplitAndRenderStrategy {
	private static double smallMargin = 0.2; //Degrees
	private static double largeMargin = 1.2; //Degrees
	
	public static ActionList CreateActionList(TileSet RequestedTileSet, String inputFileName) throws Exception
	{

		ActionList actionList = new ActionList();
		
		
		// Assuming all requested tiles are at zoom level 9
		
		TileSet thisZoomLevelTileSet;
		
		// Add actions to create data for zoomlevel 4
		thisZoomLevelTileSet = RequestedTileSet.getAllParentTiles(4);
		thisZoomLevelTileSet.tileSetIteratorStart();
		while (thisZoomLevelTileSet.tileSetIteratorHasNext())
		{
			Tile t = thisZoomLevelTileSet.tileSetIteratorGetTile();
			ExtractAction extractAction = new ExtractAction(ExtractAction.TOOL_OSMCONVERT, t.getBoundingBoxWithMargin(largeMargin), ExtractAction.CUTMETHOD_CLIP, inputFileName, t.toString() + ".o5m");
			actionList.addItem(extractAction);
		}

		
		// Add actions to create data for zoomlevel 5
		thisZoomLevelTileSet = RequestedTileSet.getAllParentTiles(5);
		thisZoomLevelTileSet.tileSetIteratorStart();
		while (thisZoomLevelTileSet.tileSetIteratorHasNext())
		{
			Tile t = thisZoomLevelTileSet.tileSetIteratorGetTile();
			ExtractAction extractAction = new ExtractAction(ExtractAction.TOOL_OSMCONVERT, t.getBoundingBoxWithMargin(largeMargin), ExtractAction.CUTMETHOD_CLIP, t.getLowerZoomLevelTile(4).toString() + ".o5m", t.toString() + ".o5m");
			actionList.addItem(extractAction);
		}

		
		// Add actions to create data for zoomlevel 6
		thisZoomLevelTileSet = RequestedTileSet.getAllParentTiles(6);
		thisZoomLevelTileSet.tileSetIteratorStart();
		while (thisZoomLevelTileSet.tileSetIteratorHasNext())
		{
			Tile t = thisZoomLevelTileSet.tileSetIteratorGetTile();
			ExtractAction extractAction = new ExtractAction(ExtractAction.TOOL_OSMCONVERT, t.getBoundingBoxWithMargin(largeMargin), ExtractAction.CUTMETHOD_CLIP, t.getLowerZoomLevelTile(5).toString() + ".o5m", t.toString() + ".o5m");
			actionList.addItem(extractAction);
		}

		
		// Add actions to create data for zoomlevel 7
		thisZoomLevelTileSet = RequestedTileSet.getAllParentTiles(7);
		thisZoomLevelTileSet.tileSetIteratorStart();
		while (thisZoomLevelTileSet.tileSetIteratorHasNext())
		{
			Tile t = thisZoomLevelTileSet.tileSetIteratorGetTile();
			ExtractAction extractAction = new ExtractAction(ExtractAction.TOOL_OSMCONVERT, t.getBoundingBoxWithMargin(smallMargin), ExtractAction.CUTMETHOD_COMPLEXWAYS, t.getLowerZoomLevelTile(6).toString() + ".o5m", t.toString() + ".o5m");
			actionList.addItem(extractAction);
		}

		
		// Add actions to create data for zoomlevel 8
		thisZoomLevelTileSet = RequestedTileSet.getAllParentTiles(8);
		thisZoomLevelTileSet.tileSetIteratorStart();
		while (thisZoomLevelTileSet.tileSetIteratorHasNext())
		{
			Tile t = thisZoomLevelTileSet.tileSetIteratorGetTile();
			ExtractAction extractAction = new ExtractAction(ExtractAction.TOOL_OSMCONVERT, t.getBoundingBoxWithMargin(smallMargin), ExtractAction.CUTMETHOD_COMPLEXWAYS, t.getLowerZoomLevelTile(7).toString() + ".o5m", t.toString() + ".o5m");
			actionList.addItem(extractAction);
		}

		
		// Add actions to create data for zoomlevel 9
		thisZoomLevelTileSet = RequestedTileSet.getAllParentTiles(9);
		thisZoomLevelTileSet.tileSetIteratorStart();
		while (thisZoomLevelTileSet.tileSetIteratorHasNext())
		{
			Tile t = thisZoomLevelTileSet.tileSetIteratorGetTile();
			ExtractAction extractAction = new ExtractAction(ExtractAction.TOOL_OSMCONVERT, t.getBoundingBoxWithMargin(smallMargin), ExtractAction.CUTMETHOD_COMPLEXWAYS, t.getLowerZoomLevelTile(8).toString() + ".o5m", t.toString() + ".o5m");
			actionList.addItem(extractAction);
		}

		
		

		
		
		return actionList;
	}
}
