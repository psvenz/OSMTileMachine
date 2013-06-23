package osmTileMachine;
import java.lang.System;
public class MainClass {
	public static void main(String[] args) throws Exception
	{
		
		
		
		
		
		
		// Quick and dirty debugging
		if (args[0].equals("DEBUGSECTION"))
		{
			TileArea t = new TileArea(8903, 4703, 14, "Falun");
			
			TileArea tParent = t.getLowerZoomLevelArea(6);
			

			System.out.println("MIN LAT: " + t.getLatMin());
			System.out.println("CEN LAT: " + t.getLatCenter());
			System.out.println("MAX LAT: " + t.getLatMax());

			System.out.println("MIN LON: " + t.getLonMin());
			System.out.println("CEN LON: " + t.getLonCenter());
			System.out.println("MAX LON: " + t.getLonMax());
			
			System.out.println(t.getBoundingBox().toString());
			
			
			System.out.println("PARENT:");
			System.out.println(tParent.getBoundingBox().toString());
			System.out.println("With margin:");
			
			
			System.out.println(tParent.getBoundingBoxWithMargin().toString());
			
			System.out.println(tParent.toString());
			
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
