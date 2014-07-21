package osmTileMachine;

import java.io.File;
import java.util.ArrayList;

public class DownloadAction extends Action{

	private ArrayList<String> urls;
	private String outputFilename;
	
	@Override
	int getActionType() {
		// TODO Auto-generated method stub
		return ACTIONTYPE_DOWNLOADACTION;
	}

	@Override
	void runAction(Configuration sessionConfiguration) throws Exception {
		// TODO Auto-generated method stub
		if (WGETDownloader.testToolAvailability(sessionConfiguration)){
			Boolean successDownload = false;
			long backofftime = 30; // If wget cannot succeed, backoff (seconds)
			
			while (successDownload == false){
				try {
					String whileDownloadingSuffix = ".downloading";
					File fFinished = new File(outputFilename);
					
					fFinished.delete();
					WGETDownloader.TryDownload(sessionConfiguration, outputFilename + whileDownloadingSuffix, urls);
					successDownload = true;
					MessagePrinter.notify(sessionConfiguration, "File downloaded successfully!");
					
					File fDownloading = new File(outputFilename + whileDownloadingSuffix);
					
					boolean retvalue = fDownloading.renameTo(fFinished);
					if (retvalue == false) throw new Exception("cannot rename downloaded file");
					
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

	public DownloadAction (String localFileName, ArrayList<String> mirrors) {
		urls = mirrors;
		outputFilename = localFileName;
	}
	
	@Override
	String getActionInHumanReadableFormat() {
		// TODO Auto-generated method stub
		return "DownloadAction " + outputFilename;

	}

}
