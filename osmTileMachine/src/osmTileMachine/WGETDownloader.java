package osmTileMachine;

import java.io.File;
import java.util.ArrayList;


public class WGETDownloader {

//	static String wgetFileName = "tools" + File.separator + "wget" + File.separator + "wget";
	static String wgetFileName = "wget";

	public static void TryDownload(Configuration sessionConfiguration, String fileName, ArrayList<String> mirrors) throws Exception
	{
		String fastestMirrorURL = null;
		boolean mirrorFound = false;
		long backofftime = 5;
		while (mirrorFound == false){
			try 
			{
				MessagePrinter.notify(sessionConfiguration, "Finding fastest mirror...");
				fastestMirrorURL = findFastestMirror(sessionConfiguration, mirrors);
				mirrorFound = true;
			} catch (Exception e) {
				mirrorFound = false;
				MessagePrinter.error(sessionConfiguration, "No mirror found!");
				MessagePrinter.error(sessionConfiguration, "Waiting " + backofftime + " seconds before retrying");
				try {
					Thread.sleep(1000*backofftime);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				backofftime = backofftime * 3;
				if (backofftime > 3600) backofftime = 3600; //Max backoff wait time 1 hours

			}
		}
		MessagePrinter.debug(sessionConfiguration, "The fastest mirror is " + fastestMirrorURL);
		MessagePrinter.notify(sessionConfiguration, "Starting download from " + fastestMirrorURL);
		
		
		// Try actual wget download here
		
		ExternalToolLauncher tool = new ExternalToolLauncher(sessionConfiguration); 	
		
//		File f = new File(wgetFileName);
//		String pathf = f.getCanonicalFile().toString();
//		tool.setCommand(pathf);
		tool.setCommand(wgetFileName);

		tool.addArgument("--dot-style=giga");	
		tool.addArgument("-O");
		tool.addArgument(fileName);
		tool.addArgument(fastestMirrorURL);
		tool.run();
		

		Boolean successfulDownload = false;
		if (tool.ExitValue() == 0){
			successfulDownload = true;
		}
		
		if (successfulDownload == false) throw new Exception("Download failed...");
	}


	public static boolean testToolAvailability(Configuration sessionConfiguration) {
		
		ExternalToolLauncher e = new ExternalToolLauncher(sessionConfiguration);
		try {
//			File f = new File(wgetFileName);
//			String pathf = f.getCanonicalFile().toString();

			e.setCommand(wgetFileName);
//			e.setCommand("tools" + File.separator + "wget" + File.separator + "wget");
			e.addArgument("--version");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		e.addArgument("--help");
		e.run();

		if (e.ExitValue() == 0) 
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}


	private static String findFastestMirror (Configuration sessionConfiguration, ArrayList<String> mirrors) throws Exception{
		if (mirrors.size()==1) return mirrors.get(0);
		
		if (sessionConfiguration.getDebugOutput())
		{
			MessagePrinter.debug(sessionConfiguration, "Attempting WGET download with the following mirrors:");

			for (String string : mirrors) {
				MessagePrinter.debug(sessionConfiguration, string);
			}
		}

		//Probe all mirrors and find the fastest one
		

		double largestFile = 0;
		String largestUrl = "";
		String testFileName = "downloadperformancetestfile.tmp";
		
		for (String mirrorURL : mirrors) {
			ExternalToolLauncher e = new ExternalToolLauncher(sessionConfiguration);
			
//			File fn = new File(wgetFileName);
//			String pathf = fn.getCanonicalFile().toString();
//			e.setCommand(pathf);
			e.setCommand(wgetFileName);
			e.addArgument("--timeout=5");
			e.addArgument("--tries=0");
			e.addArgument("--output-document=" + testFileName);
			e.addArgument("--dot-style=mega");
			e.addArgument(mirrorURL);

			File f = new File(sessionConfiguration.getWorkingDirectory() + File.separator + testFileName);
			f.delete();
			MessagePrinter.debug(sessionConfiguration, "Waiting before download speed test");
			TimeConsumer.sleepSeconds(0.1);
			double downloadTime = 10;
			e.runAndKill(downloadTime);

			double fileSize;
			fileSize =  f.length();
			f.delete();
			MessagePrinter.notify(sessionConfiguration, mirrorURL + " download speed is  " +Long.toString((long) (fileSize/ (1000*downloadTime))) + " kB/s");


			// If we should avoid a server, only use it if it is 10 times faster than the other mirrors...
			if (OpenStreetMapProject.avoidServer(mirrorURL)) fileSize = fileSize / 10;
			
			if (fileSize > largestFile)
			{
				largestFile = fileSize;
				largestUrl = mirrorURL;
			}

		}
		if (largestFile < 1000)
		{
			throw new Exception("No mirrors found, check your connection.");
		}
		return largestUrl;

	}
}
