package osmTileMachine;

import java.io.File;

//import java.io.File;

public class SplitAndRenderStrategy {
	private static double smallMargin = 0.04; //Degrees
	//	private static double smallMargin = 0.05; //Degrees
	private static double largeMargin = 2; //Degrees

	public static ActionList CreateActionList(Configuration sessionConfiguration, TileSet RequestedTileSet, String inputFileName, ActionList deleteSourceFilesActionList) throws Exception
	{

		ActionList actionList = new ActionList();


		// Assuming all requested tiles are at zoom level 10

		TileSet thisZoomLevelTileSet;

		// Add actions to create data for zoomlevel 4
		thisZoomLevelTileSet = RequestedTileSet.getAllParentTiles(4);
		thisZoomLevelTileSet.tileSetIteratorStart();
		DeleteFileSetAction z4deleteList = new DeleteFileSetAction();
		while (thisZoomLevelTileSet.tileSetIteratorHasNext())
		{
			Tile t = thisZoomLevelTileSet.tileSetIteratorGetTile();
			String fileName = t.toString() + ".pbf";
			ExtractAction extractAction = new ExtractAction(ExtractAction.TOOL_OSMCONVERT, t.getBoundingBoxWithMargin(largeMargin), ExtractAction.CUTMETHOD_CLIP, inputFileName, fileName);
			actionList.addItem(extractAction);
			z4deleteList.addFileName(sessionConfiguration.getWorkingDirectory() + File.separator + fileName);
		}

		// All z4 data files created. If requested, remove all source files...
		if (!sessionConfiguration.getKeepDownload()) {
			actionList.append(deleteSourceFilesActionList);
		}


		// Add actions to create data for zoomlevel 5
		thisZoomLevelTileSet = RequestedTileSet.getAllParentTiles(5);
		thisZoomLevelTileSet.tileSetIteratorStart();
		DeleteFileSetAction z5deleteList = new DeleteFileSetAction();
		while (thisZoomLevelTileSet.tileSetIteratorHasNext())
		{
			Tile t = thisZoomLevelTileSet.tileSetIteratorGetTile();
			String fileName = t.toString() + ".pbf";
			ExtractAction extractAction = new ExtractAction(ExtractAction.TOOL_OSMCONVERT, t.getBoundingBoxWithMargin(largeMargin), ExtractAction.CUTMETHOD_CLIP, t.getLowerZoomLevelTile(4).toString() + ".pbf", fileName);
			actionList.addItem(extractAction);
			z5deleteList.addFileName(sessionConfiguration.getWorkingDirectory() + File.separator + fileName);

		}

		// Delete files needed to generate zoomlevel 5
		if (sessionConfiguration.getKeepIntermediateFiles() == false) 
			actionList.addItem(z4deleteList);



		// Add actions to create data for zoomlevel 6
		thisZoomLevelTileSet = RequestedTileSet.getAllParentTiles(6);
		thisZoomLevelTileSet.tileSetIteratorStart();
		DeleteFileSetAction z6deleteList = new DeleteFileSetAction();
		while (thisZoomLevelTileSet.tileSetIteratorHasNext())
		{
			Tile t = thisZoomLevelTileSet.tileSetIteratorGetTile();
			String fileName = t.toString() + ".pbf";
			ExtractAction extractAction = new ExtractAction(ExtractAction.TOOL_OSMCONVERT, t.getBoundingBoxWithMargin(largeMargin), ExtractAction.CUTMETHOD_CLIP, t.getLowerZoomLevelTile(5).toString() + ".pbf", fileName);
			actionList.addItem(extractAction);
			z6deleteList.addFileName(sessionConfiguration.getWorkingDirectory() + File.separator + fileName);

		}

		// Delete files needed to generate zoomlevel 6
		if (sessionConfiguration.getKeepIntermediateFiles() == false) 
			actionList.addItem(z5deleteList);


		// Add actions to create data for zoomlevel 7
		thisZoomLevelTileSet = RequestedTileSet.getAllParentTiles(7);
		thisZoomLevelTileSet.tileSetIteratorStart();
		DeleteFileSetAction z7deleteList = new DeleteFileSetAction();
		while (thisZoomLevelTileSet.tileSetIteratorHasNext())
		{
			Tile t = thisZoomLevelTileSet.tileSetIteratorGetTile();
			String fileName = t.toString() + ".pbf";
			ExtractAction extractAction = new ExtractAction(ExtractAction.TOOL_OSMCONVERT, t.getBoundingBoxWithMargin(largeMargin), ExtractAction.CUTMETHOD_CLIP, t.getLowerZoomLevelTile(6).toString() + ".pbf", fileName);
			actionList.addItem(extractAction);
			z7deleteList.addFileName(sessionConfiguration.getWorkingDirectory() + File.separator + fileName);

		}

		// Delete files needed to generate zoomlevel 7
		if (sessionConfiguration.getKeepIntermediateFiles() == false) 
			actionList.addItem(z6deleteList);


		// Add actions to create data for zoomlevel 8
		thisZoomLevelTileSet = RequestedTileSet.getAllParentTiles(8);
		thisZoomLevelTileSet.tileSetIteratorStart();
		DeleteFileSetAction z8deleteList = new DeleteFileSetAction();
		while (thisZoomLevelTileSet.tileSetIteratorHasNext())
		{
			Tile t = thisZoomLevelTileSet.tileSetIteratorGetTile();
			String fileName = t.toString() + ".pbf";
			ExtractAction extractAction = new ExtractAction(ExtractAction.TOOL_OSMCONVERT, t.getBoundingBoxWithMargin(largeMargin), ExtractAction.CUTMETHOD_CLIP, t.getLowerZoomLevelTile(7).toString() + ".pbf", fileName);
			actionList.addItem(extractAction);
			z8deleteList.addFileName(sessionConfiguration.getWorkingDirectory() + File.separator + fileName);

		}

		// Delete files needed to generate zoomlevel 8
		if (sessionConfiguration.getKeepIntermediateFiles() == false) 
			actionList.addItem(z7deleteList);



		// Add actions to create data for zoomlevel 10
		thisZoomLevelTileSet = RequestedTileSet.getAllParentTiles(10);
		thisZoomLevelTileSet.tileSetIteratorStart();
		while (thisZoomLevelTileSet.tileSetIteratorHasNext())
		{
			DeleteFileSetAction z10_eachTileDeleteList = new DeleteFileSetAction();
			Tile t = thisZoomLevelTileSet.tileSetIteratorGetTile();

			ExtractAction extractActionLargeClip = new ExtractAction(ExtractAction.TOOL_OSMCONVERT, t.getBoundingBoxWithMargin(largeMargin), ExtractAction.CUTMETHOD_CLIP, t.getLowerZoomLevelTile(8).toString() + ".pbf", t.toString() + "_largecut.o5m");
			z10_eachTileDeleteList.addFileName(   sessionConfiguration.getWorkingDirectory() + File.separator +                                                                                                            t.toString() + "_largecut.o5m");
			actionList.addItem(extractActionLargeClip);

			ExtractAction extractActionCompleteRelations = new ExtractAction(ExtractAction.TOOL_OSMCONVERT, t.getBoundingBoxWithMargin(smallMargin), ExtractAction.CUTMETHOD_COMPLEXWAYS, t.toString() + "_largecut.o5m", t.toString() + ".osm");
			z10_eachTileDeleteList.addFileName(   sessionConfiguration.getWorkingDirectory() + File.separator +                                                                                                           t.toString() + ".osm");
			actionList.addItem(extractActionCompleteRelations);

			RenderAction renderAction = new RenderAction(RenderAction.TOOL_MAPERITIVE, t.getX(), t.getY(), t.getZ(), getHighestRenderLevel(), sessionConfiguration.getRuleSetFilename(), sessionConfiguration.getWebrootDirectoryName(), t.toString() + ".osm");
			z10_eachTileDeleteList.addFileName(sessionConfiguration.getWorkingDirectory() + File.separator + renderAction.getScriptFileName());
			actionList.addItem(renderAction);


			if (sessionConfiguration.getMinFileSize() > 0) 
			{
				for (int i=13; i<=sessionConfiguration.getMaxZoom();i++)
				{
					DeleteEmptyTilesAction deleteEmptyTilesAction = new DeleteEmptyTilesAction(sessionConfiguration.getMinFileSize(), t,i); 
					actionList.addItem(deleteEmptyTilesAction);
				}
			}
			
			if (sessionConfiguration.getKeepIntermediateFiles() == false)
				actionList.addItem(z10_eachTileDeleteList);
		}

		// Delete z8deletelist
		if (sessionConfiguration.getKeepIntermediateFiles() == false) 
			actionList.addItem(z8deleteList);



		// lower zoom levels requested? 
		if (sessionConfiguration.getLowZoom()){
			TileSet z9 = RequestedTileSet.getAllParentTiles(9);
			TileSet z8 = RequestedTileSet.getAllParentTiles(8);
			TileSet z7 = RequestedTileSet.getAllParentTiles(7);
			TileSet z6 = RequestedTileSet.getAllParentTiles(6);
			TileSet z5 = RequestedTileSet.getAllParentTiles(5);
			TileSet z4 = RequestedTileSet.getAllParentTiles(4);
			TileSet z3 = RequestedTileSet.getAllParentTiles(3);
			TileSet z2 = RequestedTileSet.getAllParentTiles(2);
			TileSet z1 = RequestedTileSet.getAllParentTiles(1);
			TileSet z0 = RequestedTileSet.getAllParentTiles(0);

			actionList.addItem(new GenerateLowZoomLevelAction(z9, " z9"));
			actionList.addItem(new GenerateLowZoomLevelAction(z8, " z8"));
			actionList.addItem(new GenerateLowZoomLevelAction(z7, " z7"));
			actionList.addItem(new GenerateLowZoomLevelAction(z6, " z6"));
			actionList.addItem(new GenerateLowZoomLevelAction(z5, " z5"));
			actionList.addItem(new GenerateLowZoomLevelAction(z4, " z4"));
			actionList.addItem(new GenerateLowZoomLevelAction(z3, " z3"));
			actionList.addItem(new GenerateLowZoomLevelAction(z2, " z2"));
			actionList.addItem(new GenerateLowZoomLevelAction(z1, " z1"));
			actionList.addItem(new GenerateLowZoomLevelAction(z0, " z0"));

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