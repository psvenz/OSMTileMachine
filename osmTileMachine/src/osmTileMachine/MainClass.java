package osmTileMachine;
import java.lang.System;
public class MainClass {
	public static void main(String[] args) throws Exception
	{





		Configuration sessionConfiguration = new Configuration();

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
		else if (sessionConfiguration.getOperatingMode() == Configuration.OPERATINGMODE_UPDATEPLANET) 
		{
			PlanetMaintainer.updatePlanet(sessionConfiguration);
		}
		else if (sessionConfiguration.getOperatingMode() == Configuration.OPERATINGMODE_VERIFYPLANET) 
		{
			boolean planetStatus = PlanetMaintainer.verifyPlanet(sessionConfiguration);
			if (planetStatus == true) {
				MessagePrinter.notify(sessionConfiguration, "Planet OK!");
			} else {
				MessagePrinter.notify(sessionConfiguration, "Planet NOT OK!");
			}
		}
		else if (sessionConfiguration.getOperatingMode() == Configuration.OPERATINGMODE_RENDERAREA)
		{
			TileSet ts = new TileSet();
			ts.addSet(Geography.getTileSetForRegion(sessionConfiguration.getRequestedArea()));

			System.out.println("Updating planet...");
			PlanetMaintainer.updatePlanet(sessionConfiguration);

			System.out.println("Generating actionlist...");
			ActionList ExtractAreaActionList = SplitAndRenderStrategy.CreateActionList(sessionConfiguration, ts, PlanetMaintainer.updatedplanetFilename);
			System.out.println(ExtractAreaActionList.getListInHumanReadableFormat());
			
			System.out.println("Executing actionlist...");
			int i = 0;
			while (ExtractAreaActionList.actionsLeft()){
				System.out.println((i+1) + " ");

				if (i < -1 ) ExtractAreaActionList.getNextAction(); //developers ability to skip early actions
				else
				{
					ExtractAreaActionList.getNextAction().runAction(sessionConfiguration);
				}
				i++;
			}

		}
		else
		{
			throw new Exception("Error: no operating mode specified.");
		}
	}
}
