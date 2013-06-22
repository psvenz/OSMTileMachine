package osmTileMachine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class WGETDownloader {

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
				// TODO Auto-generated catch block
				mirrorFound = false;
				MessagePrinter.error(sessionConfiguration, "No mirror found!");
				MessagePrinter.error(sessionConfiguration, "Waiting " + backofftime + " seconds before retrying");
				try {
					Thread.sleep(1000*backofftime);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				backofftime = backofftime * 3;
				if (backofftime > 3600) backofftime = 3600; //Max backoff wait time 1 hours

			}
		}
		MessagePrinter.debug(sessionConfiguration, "The fastest mirror is " + fastestMirrorURL);
		MessagePrinter.notify(sessionConfiguration, "Starting download from " + fastestMirrorURL);
		// Try actual wget download here
		String downloadFilename = fileName;
		
		ExternalToolLauncher tool = new ExternalToolLauncher(sessionConfiguration); 
		
		tool.setCommand("wget");

//		tool.addArgument("--output-document=\"" + downloadFilename + "\" --output-file=\"" + sessionConfiguration.getWorkingDirectory() + File.separator + "wgetlogfile.txt\" " + fastestMirrorURL);

		tool.addArgument("--dot-style=giga");
		
		tool.addArgument("-O");
		tool.addArgument(downloadFilename);
		tool.addArgument(fastestMirrorURL);
	
		tool.run();
//		String systemCommand = "wget  " + 
//				"--output-document=\"" + downloadFilename + "\" --output-file=\"" + sessionConfiguration.getWorkingDirectory() + File.separator + "wgetlogfile.txt\" " + fastestMirrorURL;


/*		Process p = null;
		File f = new File(downloadFilename);
		f.delete();
		Boolean successfulDownload = false;
		try {
			MessagePrinter.debug(sessionConfiguration, "System call: " + systemCommand);
			p = Runtime.getRuntime().exec(systemCommand);
			p.waitFor();
			if (p.exitValue() == 0) successfulDownload = true;
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		Boolean successfulDownload = false;
		if (tool.ExitValue() == 0){
			successfulDownload = true;
		}
		
		if (successfulDownload == false) throw new Exception("Download failed...");
	}


	public static boolean testToolAvailability() {
		Process p;
		int returnCode = -1;
		try 
		{
			p = Runtime.getRuntime().exec("wget --version");
			p.waitFor();
			returnCode = p.exitValue();

		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		// TODO Auto-generated method stub
		if (returnCode == 0) 
		{
			return true;
		}
		else
		{
			return false;
		}

	}


	private static String findFastestMirror (Configuration sessionConfiguration, ArrayList<String> mirrors) throws Exception{
		if (sessionConfiguration.getDebugOutput())
		{
			MessagePrinter.debug(sessionConfiguration, "Attempting WGET download with the following mirrors:");

			for (String string : mirrors) {
				MessagePrinter.debug(sessionConfiguration, string);
			}
		}

		//Probe all mirrors and find the fastest one
		String systemCommand = "";

		float largestFile = 0;
		String largestUrl = "";

		for (String string : mirrors) {
			systemCommand = "wget --timeout=5 --tries=0 " + 
					"--output-document=\"" + sessionConfiguration.getWorkingDirectory() + File.separator + "downloadperformancetestfile.tmp\" --output-file=\"" + sessionConfiguration.getWorkingDirectory() + File.separator + "wgetlogfile.txt\" " + string;
			MessagePrinter.debug(sessionConfiguration, "System command: " + systemCommand);

			Process p = null;
			File f = new File(sessionConfiguration.getWorkingDirectory() + File.separator + "downloadperformancetestfile.tmp");
			f.delete();
			MessagePrinter.debug(sessionConfiguration, "Waiting before download speed test");
			try 
			{
				Thread.sleep(100);
			} catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				MessagePrinter.error(sessionConfiguration, "Thread sleep failed.");
			}
			MessagePrinter.debug(sessionConfiguration, "Calling system " + systemCommand);

			try {
				p = Runtime.getRuntime().exec(systemCommand);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				MessagePrinter.error(sessionConfiguration, "Could not spawn wget process");
			}

			MessagePrinter.debug(sessionConfiguration, "Sleeping");

			int downloadTime = 4000; //milliseconds

			try {
				Thread.sleep(downloadTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				MessagePrinter.error(sessionConfiguration, "Thread sleep failed.");
			}

			MessagePrinter.debug(sessionConfiguration, "Killing wget");


			p.destroy();
			p.waitFor();

			long fileSize;
			fileSize =  f.length();
			f.delete();
			MessagePrinter.notify(sessionConfiguration, string + " download speed is  " +Long.toString((fileSize)/(downloadTime)) + " kB/s");

			if (fileSize > largestFile)
			{
				largestFile = fileSize;
				largestUrl = string;
			}

		}
		if (largestFile < 1000)
		{
			throw new Exception("No mirrors found, check your connection.");
		}
		return largestUrl;

	}
}
