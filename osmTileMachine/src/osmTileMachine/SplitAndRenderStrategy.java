package osmTileMachine;

import java.io.File;

public class SplitAndRenderStrategy {
	private static double smallMargin = 0.03; //Degrees
//	private static double smallMargin = 0.05; //Degrees
	private static double largeMargin = 0.9; //Degrees

	public static ActionList CreateActionList(Configuration sessionConfiguration, TileSet RequestedTileSet, String inputFileName) throws Exception
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
			ExtractAction extractAction = new ExtractAction(ExtractAction.TOOL_OSMCONVERT, t.getBoundingBoxWithMargin(largeMargin), ExtractAction.CUTMETHOD_CLIP, inputFileName, t.toString() + ".pbf");
			actionList.addItem(extractAction);
		}


		// Add actions to create data for zoomlevel 5
		thisZoomLevelTileSet = RequestedTileSet.getAllParentTiles(5);
		thisZoomLevelTileSet.tileSetIteratorStart();
		while (thisZoomLevelTileSet.tileSetIteratorHasNext())
		{
			Tile t = thisZoomLevelTileSet.tileSetIteratorGetTile();
			ExtractAction extractAction = new ExtractAction(ExtractAction.TOOL_OSMCONVERT, t.getBoundingBoxWithMargin(largeMargin), ExtractAction.CUTMETHOD_CLIP, t.getLowerZoomLevelTile(4).toString() + ".pbf", t.toString() + ".pbf");
			actionList.addItem(extractAction);
		}


		// Add actions to create data for zoomlevel 6
		thisZoomLevelTileSet = RequestedTileSet.getAllParentTiles(6);
		thisZoomLevelTileSet.tileSetIteratorStart();
		while (thisZoomLevelTileSet.tileSetIteratorHasNext())
		{
			Tile t = thisZoomLevelTileSet.tileSetIteratorGetTile();
			ExtractAction extractAction = new ExtractAction(ExtractAction.TOOL_OSMCONVERT, t.getBoundingBoxWithMargin(largeMargin), ExtractAction.CUTMETHOD_CLIP, t.getLowerZoomLevelTile(5).toString() + ".pbf", t.toString() + ".pbf");
			actionList.addItem(extractAction);
		}


		// Add actions to create data for zoomlevel 7
		thisZoomLevelTileSet = RequestedTileSet.getAllParentTiles(7);
		thisZoomLevelTileSet.tileSetIteratorStart();
		while (thisZoomLevelTileSet.tileSetIteratorHasNext())
		{
			Tile t = thisZoomLevelTileSet.tileSetIteratorGetTile();
			ExtractAction extractAction = new ExtractAction(ExtractAction.TOOL_OSMCONVERT, t.getBoundingBoxWithMargin(largeMargin), ExtractAction.CUTMETHOD_CLIP, t.getLowerZoomLevelTile(6).toString() + ".pbf", t.toString() + ".pbf");
			actionList.addItem(extractAction);
		}


		// Add actions to create data for zoomlevel 8
		thisZoomLevelTileSet = RequestedTileSet.getAllParentTiles(8);
		thisZoomLevelTileSet.tileSetIteratorStart();
		while (thisZoomLevelTileSet.tileSetIteratorHasNext())
		{
			Tile t = thisZoomLevelTileSet.tileSetIteratorGetTile();
			ExtractAction extractAction = new ExtractAction(ExtractAction.TOOL_OSMCONVERT, t.getBoundingBoxWithMargin(largeMargin), ExtractAction.CUTMETHOD_CLIP, t.getLowerZoomLevelTile(7).toString() + ".pbf", t.toString() + ".pbf");
			actionList.addItem(extractAction);
		}

		
		// Add actions to create data for zoomlevel 9
		thisZoomLevelTileSet = RequestedTileSet.getAllParentTiles(9);
		thisZoomLevelTileSet.tileSetIteratorStart();
		while (thisZoomLevelTileSet.tileSetIteratorHasNext())
		{
			Tile t = thisZoomLevelTileSet.tileSetIteratorGetTile();
			ExtractAction extractAction = new ExtractAction(ExtractAction.TOOL_OSMCONVERT, t.getBoundingBoxWithMargin(largeMargin), ExtractAction.CUTMETHOD_CLIP, t.getLowerZoomLevelTile(8).toString() + ".pbf", t.toString() + ".o5m");
			actionList.addItem(extractAction);
		}


		// Add actions to create data for zoomlevel 10
		thisZoomLevelTileSet = RequestedTileSet.getAllParentTiles(10);
		thisZoomLevelTileSet.tileSetIteratorStart();
		while (thisZoomLevelTileSet.tileSetIteratorHasNext())
		{
			Tile t = thisZoomLevelTileSet.tileSetIteratorGetTile();

			ExtractAction extractAction = new ExtractAction(ExtractAction.TOOL_OSMCONVERT, t.getBoundingBoxWithMargin(smallMargin), ExtractAction.CUTMETHOD_COMPLEXWAYS, t.getLowerZoomLevelTile(9).toString() + ".o5m", t.toString() + ".osm");
			actionList.addItem(extractAction);

			RenderAction renderAction = new RenderAction(RenderAction.TOOL_MAPERITIVE, t.getX(), t.getY(), t.getZ(), getHighestRenderLevel(), sessionConfiguration.getRuleSetFilename(), sessionConfiguration.getOutputDirectoryName(), t.toString() + ".osm");
			actionList.addItem(renderAction);
		}







		return actionList;
	}

	public static int getLowestRenderLevel() {
		return 10;
	}
	private static int getHighestRenderLevel() {
		return Maperitive.DYNAMIC_ZMAX;
	}


	public static int getMaxZoomLevel(Configuration sessionConfiguration, RenderAction renderAction) {
		return sessionConfiguration.getMaxZoom();
//		return 13; //Limited in proof of concept demo

	/*	File inputFile = new File( sessionConfiguration.getWorkingDirectory() + "\\" +renderAction.getDataFileName());
		long sizeKB = inputFile.length()/1024;
		if (sizeKB > 10000)
		{
			return 16;
		}
		else if(sizeKB > 100)
		{
			return 15;
		}
		else {
			return 12;
		}*/
		
	}
}
