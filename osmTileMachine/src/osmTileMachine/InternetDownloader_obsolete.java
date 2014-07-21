package osmTileMachine;
/*package osmTileMachine;

import java.io.File;
import java.util.ArrayList;

public class InternetDownloader {

	public static void downloadFile(Configuration sessionConfiguration, String localFileName, ArrayList<String> mirrors) throws Exception 
	{
		
		
		if (WGETDownloader.testToolAvailability(sessionConfiguration)){
			Boolean successDownload = false;
			long backofftime = 30; // If wget cannot succeed, backoff (seconds)
			
			while (successDownload == false){
				try {
					String whileDownloadingSuffix = ".downloading";
					File fFinished = new File(sessionConfiguration.getWorkingDirectory() + File.separator + localFileName);
					fFinished.delete();
					WGETDownloader.TryDownload(sessionConfiguration, localFileName + whileDownloadingSuffix, mirrors);
					successDownload = true;
					MessagePrinter.notify(sessionConfiguration, "File downloaded successfully!");
					
					File fDownloading = new File(sessionConfiguration.getWorkingDirectory() + File.separator + localFileName + whileDownloadingSuffix);
					fDownloading.renameTo(fFinished);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					successDownload = false;
					MessagePrinter.error(sessionConfiguration, "Failed to download file!");
					try {
						MessagePrinter.error(sessionConfiguration, "Waiting " + Long.toString(backofftime) + " seconds before retrying...");
						Thread.sleep(1000*backofftime);
						backofftime = backofftime*4;
						if (backofftime > 3600*10) backofftime = 3600*10; // Max backoff time (seconds)
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
		else
		{
			throw new Exception("No download tool (WGET) installed or not in PATH.");
		}
	}
}
*/