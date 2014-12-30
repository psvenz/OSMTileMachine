package osmTileMachine;
import java.io.File;
import java.lang.System;
import java.util.ArrayList;
public class MainClass {
	public static void main(String[] args) throws Exception
	{

		ActionList mainActionList = new ActionList();

		Configuration sessionConfiguration = new Configuration();

		sessionConfiguration.parseInputArguments(args);

		ActionList deleteDownlodedFileActionList = new ActionList();
		ActionList deleteUpdatedFileActionList = new ActionList();

		if (sessionConfiguration.getSourceType() == sessionConfiguration.SOURCETYPE_DOWNLOAD)
		{
			String newSourceFileName = sessionConfiguration.getWorkingDirectory() + File.separator + "datasource.pbf";
			mainActionList.addItem(new DownloadAction(newSourceFileName, OpenStreetMapProject.getSourceFileMirrors(sessionConfiguration, sessionConfiguration.getSource())));
			sessionConfiguration.setSource(newSourceFileName);

			DeleteFileSetAction delFileSetAction = new DeleteFileSetAction();
			delFileSetAction.addFileName(newSourceFileName);
			deleteDownlodedFileActionList.addItem(delFileSetAction);
		}		
		else if (sessionConfiguration.getSourceType() == sessionConfiguration.SOURCETYPE_URL)
		{
			String newSourceFileName = sessionConfiguration.getWorkingDirectory() + File.separator + "datasource.pbf";
			ArrayList<String> addressList = new ArrayList<String>();
			addressList.add(sessionConfiguration.getSource());
			mainActionList.addItem(new DownloadAction(newSourceFileName,addressList));
			sessionConfiguration.setSource(newSourceFileName);		

			DeleteFileSetAction delFileSetAction = new DeleteFileSetAction();
			delFileSetAction.addFileName(newSourceFileName);
			deleteDownlodedFileActionList.addItem(delFileSetAction);

		}


		if (sessionConfiguration.getUpdate())
		{
			String newSourceFileName = sessionConfiguration.getWorkingDirectory() + File.separator + "datasource_updated.pbf";
			mainActionList.addItem(new DataUpdateAction(sessionConfiguration.getSource(), newSourceFileName));	
			
			if (!sessionConfiguration.getKeepDownload()) {
				mainActionList.append(deleteDownlodedFileActionList);
			}
			
			sessionConfiguration.setSource(newSourceFileName);

			DeleteFileSetAction delFileSetAction = new DeleteFileSetAction();
			delFileSetAction.addFileName(newSourceFileName);
			deleteUpdatedFileActionList.addItem(delFileSetAction);

		}

		//Merge the two actionlist to one common "remove source action list"
		ActionList deleteSourceFilesActionList = new ActionList();
		deleteSourceFilesActionList.append (deleteDownlodedFileActionList);
		deleteSourceFilesActionList.append (deleteUpdatedFileActionList);

		
		if (sessionConfiguration.getRender())			
		{
			TileSet ts = new TileSet();
			ts.addSet(Geography.getTileSetForRegion(sessionConfiguration.getRequestedArea()));
			
			
			
			ActionList splitAndRenderActionList = SplitAndRenderStrategy.CreateActionList(sessionConfiguration, ts, sessionConfiguration.getSource(), deleteSourceFilesActionList);
			mainActionList.append(splitAndRenderActionList);
		}

		mainActionList.PrintListInHumanReadableFormat(sessionConfiguration);


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
				Action a = 	mainActionList.getNextAction();
				System.out.println(a.toString());
				a.runAction(sessionConfiguration);
			}
			i++;
		}
		System.out.println("Done");
	}
}

