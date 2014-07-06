package osmTileMachine;

import java.io.File;

public class SourceFileMaintainer {

	public static String sourceFileName = "sourcefile.pbf";
	public static String updatedSourceFilename = "sourcefile_updated.pbf";
	
	public static void forceSourceDownload(Configuration sessionConfiguration) throws Exception {
		MessagePrinter.notify(sessionConfiguration, "Downloading planet file...");
		InternetDownloader.downloadFile(sessionConfiguration, sourceFileName, OpenStreetMapProject.getSourceFileMirrors(sessionConfiguration));
		MessagePrinter.notify(sessionConfiguration, "Planet file downloaded!");
	}

	public static void updateSourceFile(Configuration sessionConfiguration) throws Exception{

		MessagePrinter.notify(sessionConfiguration, "Updating planet file...");
		if (Osmupdate.testToolAvailability(sessionConfiguration) == false) throw new Exception("updatePlanet failed, could not find osmupdate tool (osmu.exe)");
		MessagePrinter.debug(sessionConfiguration, "osmupdate testToolAvailability OK");

		boolean updateSuccess = false;
		int attemptNumber = 0;
		long backoffTime = 5;
		boolean useCache = true;
		while (updateSuccess == false){
			attemptNumber++;
			MessagePrinter.debug(sessionConfiguration, "updatePlanet attempt no: " + attemptNumber);
			// Change strategy depending on attemptNumber
			
			// Verifyplanet fail => download a new planet
			if (verifyPlanet(sessionConfiguration) == false){
				try {
					MessagePrinter.debug(sessionConfiguration, "Downloading planet.");
					SourceFileMaintainer.forceSourceDownload(sessionConfiguration);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					throw new Exception("PlanetMaintainer: could not execute forcePlanetDownload");
				}

			}
					
			// Fail after four attempts, could be osmupdate cache that is broken, use without cache
			if (attemptNumber > 4) useCache = false;


			
			//still no luck? planet may be broken at openstreetmap project, wait until next release...
			if (attemptNumber > 21){
				try {
					MessagePrinter.debug(sessionConfiguration, "Planet still not OK,Already tried redownload, planet may be broken this week, wait until next release, sleeping " + OpenStreetMapProject.timeUntilNextPlanetRelease()/1000 + " seconds before attempting redownload.");
					Thread.sleep(OpenStreetMapProject.timeUntilNextPlanetRelease());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// Fail after 20 attempts, probably broken local copy of planet, download a new one
			if (attemptNumber > 20) {
				try {
					SourceFileMaintainer.forceSourceDownload(sessionConfiguration);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					throw new Exception("PlanetMaintainer: could not execute forcePlanetDownload");
				}
			}

			
			
			// Try the actual update
			try {				
				Osmupdate.runUpdate(sessionConfiguration, useCache);
				updateSuccess = true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				MessagePrinter.debug(sessionConfiguration, "osmupdate failed.");
				e.printStackTrace();
			}

			// Back off time 
			if (updateSuccess == false)
			{
				backoffTime = backoffTime * 2;
				if (backoffTime > 3600) backoffTime = 3600;
				try {
					MessagePrinter.debug(sessionConfiguration, "sleeping " + backoffTime + " seconds.");
					Thread.sleep(1000*backoffTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}


	public static boolean verifyPlanet(Configuration sessionConfiguration){
		MessagePrinter.debug(sessionConfiguration, "Verifying planet...");
		boolean planetGood = true;
		// assume planet is OK until fault has been found

		// File exists
		File f = new File(sessionConfiguration.getWorkingDirectory() + File.separator + sourceFileName);
		if (f.exists() == false){
			planetGood = false;
			MessagePrinter.debug(sessionConfiguration,  sourceFileName + " does not exist... FAIL!");
		}
		else {
			MessagePrinter.debug(sessionConfiguration,  sourceFileName + " exists... OK!");

		}

		// Size is at least the right size
		if (f.length() < OpenStreetMapProject.approximateSourceSize()*0.9){
			planetGood = false;
			MessagePrinter.debug(sessionConfiguration,  sourceFileName + " too small to be OK!");
		}
		else {
			MessagePrinter.debug(sessionConfiguration,  sourceFileName + " file size OK (" + f.length()/1000000 + " MB)");			
		}


		// Max lastmodified is -14 to 1 days compared to current time.
		double fileDate = (f.lastModified() - System.currentTimeMillis())/(1000*60*60*24); //fileDate unit is days

		MessagePrinter.debug(sessionConfiguration,  sourceFileName + " age is " + -fileDate*24 + " hours");			

		if (fileDate < - 14)
		{
			planetGood = false;
			MessagePrinter.debug(sessionConfiguration, sourceFileName + " is too old. (Last modified " + -fileDate + " days ago...)");			

		}

		if (fileDate > 1)
		{
			planetGood = false;
			MessagePrinter.debug(sessionConfiguration, sourceFileName + " is too new. (Last modified " + -fileDate + " days ago...)");			

		}

		return planetGood;
	}
}
