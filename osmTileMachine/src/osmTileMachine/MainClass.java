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

		if (sessionConfiguration.getDownload())
		{
			PlanetMaintainer.forcePlanetDownload(sessionConfiguration);
		}		


		if (sessionConfiguration.getUpdate())
		{
			PlanetMaintainer.updatePlanet(sessionConfiguration);
		}

		if (sessionConfiguration.getRender())			
		{
			TileSet ts = new TileSet();
			ts.addSet(Geography.getTileSetForRegion(sessionConfiguration.getRequestedArea()));

			System.out.println("Generating actionlist...");
			ActionList ExtractAreaActionList = SplitAndRenderStrategy.CreateActionList(sessionConfiguration, ts, PlanetMaintainer.updatedplanetFilename);
			System.out.println(ExtractAreaActionList.getListInHumanReadableFormat());
			
			System.out.println("Executing actionlist...");
			int i = 0;
			while (ExtractAreaActionList.actionsLeft()){
				System.out.println((i+1) + " ");

				if (i < (sessionConfiguration.getFirstAction()-1) ) ExtractAreaActionList.getNextAction(); //Debug ability to skip early actions, or for resuming aborted operations
				else
				{
					ExtractAreaActionList.getNextAction().runAction(sessionConfiguration);
				}
				i++;
			}

		}
	}
}
