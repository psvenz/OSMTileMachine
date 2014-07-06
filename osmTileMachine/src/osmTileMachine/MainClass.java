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
			SourceFileMaintainer.forceSourceDownload(sessionConfiguration);
		}		


		if (sessionConfiguration.getUpdate())
		{
			SourceFileMaintainer.updateSourceFile(sessionConfiguration);
		}

		if (sessionConfiguration.getRender())			
		{
			TileSet ts = new TileSet();
			ts.addSet(Geography.getTileSetForRegion(sessionConfiguration.getRequestedArea()));

			System.out.println("Generating actionlist...");
			ActionList ExtractAreaActionList = SplitAndRenderStrategy.CreateActionList(sessionConfiguration, ts, SourceFileMaintainer.updatedSourceFilename);
			System.out.println(ExtractAreaActionList.getListInHumanReadableFormat());

			System.out.println("Executing actionlist...");
			int i = 0;
			while (ExtractAreaActionList.actionsLeft()){
				System.out.print((i+1) + "/"+ ExtractAreaActionList.originalSize());

				if (i < (sessionConfiguration.getFirstAction()-1) ) 
				{
					ExtractAreaActionList.getNextAction(); //Debug ability to skip early actions, or for resuming aborted operations
					System.out.println(" Skipped");
				}
				else
				{
					ExtractAreaActionList.getNextAction().runAction(sessionConfiguration);
					System.out.println(" Done");
				}
				i++;
			}

		}
	}
}
