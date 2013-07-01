package osmTileMachine;
import java.lang.System;
public class MainClass {
	public static void main(String[] args) throws Exception
	{
		
		
		
		
		
		
		// Quick and dirty debugging
		if (args[0].equals("DEBUGSECTION"))
		{
			
			
			Tile t = new Tile(8903, 4703, 14, "Falun");
			Tile t2 = new Tile(1, 2, 3, "Falun");
			Tile t4 = new Tile(11, 10, 9, "Falun");
			Tile t3 = new Tile(100, 101, 5, "Falun");
	
			TileSet st = new TileSet();
			st.add(t);
			st.add(t2);
			st.add(t3);
			st.add(t4);
			st.add(t3);
			st.add(t3);
			
			st.tileSetIteratorStart();
			Tile tPop;
			
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
