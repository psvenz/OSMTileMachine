package osmTileMachine;
import java.io.File;
import java.lang.System;
public class MainClass {
	public static void main(String[] args) throws Exception
	{

		ActionList mainActionList = new ActionList();

		Configuration sessionConfiguration = new Configuration();

		sessionConfiguration.parseInputArguments(args);


		if (sessionConfiguration.getSourceType() == sessionConfiguration.SOURCETYPE_DOWNLOAD)
		{
			String newSourceFileName = sessionConfiguration.getWorkingDirectory() + File.separator + "datasource.pbf";
			mainActionList.addItem(new DownloadAction(newSourceFileName, OpenStreetMapProject.getSourceFileMirrors(sessionConfiguration, sessionConfiguration.getSource())));
			sessionConfiguration.setSource(newSourceFileName);
		}		
		else
		{
			
		}
		
		
		if (sessionConfiguration.getUpdate())
		{
			String newSourceFileName = sessionConfiguration.getWorkingDirectory() + File.separator + "datasource_updated.pbf";
			mainActionList.addItem(new DataUpdateAction(sessionConfiguration.getSource(), newSourceFileName));	
			sessionConfiguration.setSource(newSourceFileName);
		}

		if (sessionConfiguration.getRender())			
		{
			TileSet ts = new TileSet();
			ts.addSet(Geography.getTileSetForRegion(sessionConfiguration.getRequestedArea()));

			ActionList splitAndRenderActionList = SplitAndRenderStrategy.CreateActionList(sessionConfiguration, ts, sessionConfiguration.getSource());
			mainActionList.append(splitAndRenderActionList);
		}

		System.out.println(mainActionList.getListInHumanReadableFormat());

		

		System.out.println("Executing actionlist...");
		int i = 0;
		while (mainActionList.actionsLeft()){
			System.out.print("(" + (i+1) + "/"+ mainActionList.originalSize() +") ");

			if (i < (sessionConfiguration.getFirstAction()-1) ) 
			{
				mainActionList.getNextAction(); //Debug ability to skip early actions, or for resuming aborted operations
				System.out.println("Skipped");
			}
			else
			{
				mainActionList.getNextAction().runAction(sessionConfiguration);
				System.out.println("Done");
			}
			i++;
		}
	}
}

