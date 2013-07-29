package osmTileMachine;
import java.lang.System;
public class MainClass {
	public static void main(String[] args) throws Exception
	{






		// Quick and dirty debugging
		if (args[0].equals("DEBUGSECTION"))
		{

			TileSet ts9 = new TileSet();
			ts9.addSet(Geography.getTileSetForRegion("dalarna"));

			Configuration tempsessionConfiguration = new Configuration();
			tempsessionConfiguration.setDebugOutput(true);

			PlanetMaintainer.updatePlanet(tempsessionConfiguration);
			ActionList ExtractDalarnaActionList = SplitAndRenderStrategy.CreateActionList(tempsessionConfiguration, ts9, PlanetMaintainer.updatedplanetFilename);

			System.out.println("printing list...");
			System.out.println(ExtractDalarnaActionList.getListInHumanReadableFormat());
			System.out.println("printing list done...");

			System.out.println("Executing actionlist...");

			while (ExtractDalarnaActionList.actionsLeft()){
				ExtractDalarnaActionList.getNextAction().runAction(tempsessionConfiguration);
			}
			
			/*			

			TileSet ts5 = new TileSet();
			ts5 = ts9.getAllParentTiles(5);

			System.out.println("printing list TS9:");
			System.out.println(ts9.getSetInHumanReadableFormat());
			System.out.println("printing list done...");


			System.out.println("printing list TS5:");
			System.out.println(ts5.getSetInHumanReadableFormat());
			System.out.println("printing list done...");



			Tile t1 = new Tile(244, 194, 9, "Viseu194, portugal");			
			Tile t2 = new Tile(244, 195, 9, "Viseu195, portugal");			
			ExtractAction e1 = new ExtractAction(ExtractAction.TOOL_OSMCONVERT, t1.getBoundingBox(), ExtractAction.CUTMETHOD_COMPLEXWAYS, "planet_updated.o5m", "viseu_194.osm");
			ExtractAction e2 = new ExtractAction(ExtractAction.TOOL_OSMCONVERT, t2.getBoundingBox(), ExtractAction.CUTMETHOD_COMPLEXWAYS, "planet_updated.o5m", "viseu_195.osm");

			ActionList al = new ActionList();

			al.addItem(e1);
			al.addItem(e2);


			System.out.println("printing list...");
			System.out.println(al.getListInHumanReadableFormat());
			System.out.println("printing list done...");


			Configuration tempsessionConfiguration = new Configuration();
			tempsessionConfiguration.setDebugOutput(true);
/*
//			tempsessionConfiguration.parseInputArguments(args);

//			while (al.actionsLeft()){
//				al.getNextAction().runAction(tempsessionConfiguration);
//			}


			/*

			Tile t2 = new Tile(1, 1, 2, "Falun");
			Tile t4 = new Tile(1, 1, 3, "Falun");
			Tile t3 = new Tile(1, 1, 4, "Falun");

			TileSet st = new TileSet();
			st.add(t);
			st.add(t2);
			st.add(t3);
			st.add(t4);
			st.add(t3);
			st.add(t3);

			Tile x = new Tile(1, 1, 3, "Falun");
			Tile x2 = new Tile(1, 1, 4, "Falun");
			Tile x4 = new Tile(1, 1, 5, "Falun");
			Tile x3 = new Tile(1, 1, 6, "Falun");


			TileSet st2 = new TileSet();
			st2.add(x);
			st2.add(x2);
			st2.add(x3);
			st2.add(x4);
			st2.add(x3);
			st2.add(x3);

			st.tileSetIteratorStart();
			Tile tPop;

			System.out.println("st contents:");
			while (st.tileSetIteratorHasNext())
			{
				tPop = st.tileSetIteratorGetTile();
				System.out.println(tPop.toString());
			}

			System.out.println("st2 contents:");
			st2.tileSetIteratorStart();
			while (st2.tileSetIteratorHasNext())
			{
				tPop = st2.tileSetIteratorGetTile();
				System.out.println(tPop.toString());
			}


			System.out.println("Merging st2 into st1...");
			st.addSet(st2);
			st.addSet(st2);
			st.addSet(st2);
			st.addSet(st2);

			System.out.println("st1 contents:");
			st.tileSetIteratorStart();
			while (st.tileSetIteratorHasNext())
			{
				tPop = st.tileSetIteratorGetTile();
				System.out.println(tPop.toString());
			}


//			Tile tParent = t.getLowerZoomLevelArea(9);
//			System.out.println(tParent.toString());
//			System.out.println(tParent.getBoundingBoxWithMargin().toString());
//			System.out.println("");
//
//			 tParent = t.getLowerZoomLevelArea(7);
//			System.out.println(tParent.toString());
//			System.out.println(tParent.getBoundingBoxWithMargin().toString());
//			System.out.println("");
//
//			 tParent = t.getLowerZoomLevelArea(5);
//			System.out.println(tParent.toString());
//			System.out.println(tParent.getBoundingBoxWithMargin().toString());
//			System.out.println("");
//
//			 tParent = t.getLowerZoomLevelArea(3);
//			System.out.println(tParent.toString());
//			System.out.println(tParent.getBoundingBoxWithMargin().toString());
//			System.out.println("");
//
//			//			
//
//			System.out.println("MIN LAT: " + t.getLatMin());
//			System.out.println("CEN LAT: " + t.getLatCenter());
//			System.out.println("MAX LAT: " + t.getLatMax());
//
//			System.out.println("MIN LON: " + t.getLonMin());
//			System.out.println("CEN LON: " + t.getLonCenter());
//			System.out.println("MAX LON: " + t.getLonMax());
//			
//			System.out.println(t.getBoundingBox().toString());
//			
//			
//			System.out.println("PARENT:");
//			System.out.println("With margin:");
//			
//			
//			System.out.println(tParent.getBoundingBoxWithMargin().toString());

			 */

			throw new Exception("Debug section reached end of code, exiting...");
		}




		Configuration sessionConfiguration = new Configuration();
		sessionConfiguration.setDebugOutput(true);


		try
		{
			sessionConfiguration.parseInputArguments(args);
		}catch(Exception e)
		{

			System.out.println("Error parsing input arguments!");
			System.out.println(e);
		}

		if (sessionConfiguration.getOperatingMode() == Configuration.OPERATINGMODE_FORCEPLANETDOWNLOAD)
		{
			PlanetMaintainer.forcePlanetDownload(sessionConfiguration);

		}		
		else if (sessionConfiguration.getOperatingMode() == Configuration.OPERATINGMODE_UPDATEPLANET) {
			PlanetMaintainer.updatePlanet(sessionConfiguration);
		}
		else if (sessionConfiguration.getOperatingMode() == Configuration.OPERATINGMODE_VERIFYPLANET) {
			boolean planetStatus = PlanetMaintainer.verifyPlanet(sessionConfiguration);
			if (planetStatus == true) {
				MessagePrinter.notify(sessionConfiguration, "Planet OK!");
			} else {
				MessagePrinter.notify(sessionConfiguration, "Planet NOT OK!");
			}
		}
		else
		{
			throw new Exception("Error: no operating mode specified.");
		}
	}
}
