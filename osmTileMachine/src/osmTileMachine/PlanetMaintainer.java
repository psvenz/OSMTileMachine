package osmTileMachine;

import java.io.File;

public class PlanetMaintainer {

	public static String planetFilename = "planet.pbf";
	public static String updatedplanetFilename = "planet_updated.o5m";
	public static void forcePlanetDownload(Configuration sessionConfiguration) throws Exception {
		// TODO Auto-generated method stub
		String fileName = sessionConfiguration.getWorkingDirectory() + File.separator + planetFilename; 
		InternetDownloader.downloadFile(sessionConfiguration, fileName, OpenStreetMapProject.getPlanetMirrors());
	}

	public static void updatePlanet(Configuration sessionConfiguration) throws Exception{

		MessagePrinter.debug(sessionConfiguration, "updatePlanet called...");
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
					PlanetMaintainer.forcePlanetDownload(sessionConfiguration);
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
					PlanetMaintainer.forcePlanetDownload(sessionConfiguration);
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

	public static void deleteme_oldupdatePlanet(Configuration sessionConfiguration) {
		// TODO Auto-generated method stub
		MessagePrinter.debug(sessionConfiguration, "updatePlanet called...");
		// Ensure good status of planet before updating...
		boolean planetStatusOK = false;
		boolean reDownloaded = false;
		while (planetStatusOK == false) {
			MessagePrinter.debug(sessionConfiguration, "Verifying existing " + planetFilename);

			if (PlanetMaintainer.verifyPlanet(sessionConfiguration)){
				planetStatusOK = true;
				MessagePrinter.debug(sessionConfiguration, "planet verified OK");

			}
			else
			{
				MessagePrinter.debug(sessionConfiguration, "planet verified FAIL");

				if (reDownloaded == true)
				{
					// Already tried redownload, planet may be broken this week, wait until next release...
					try {
						MessagePrinter.debug(sessionConfiguration, "Planet still not OK,Already tried redownload, planet may be broken this week, wait until next release, sleeping " + OpenStreetMapProject.timeUntilNextPlanetRelease()/1000 + " seconds before attempting redownload.");
						Thread.sleep(OpenStreetMapProject.timeUntilNextPlanetRelease());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				try {
					MessagePrinter.debug(sessionConfiguration, "Attempting redownload of planet...");
					forcePlanetDownload(sessionConfiguration);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				reDownloaded = true;
			}
		}

		// planet exists and verified OK, continue update...
		MessagePrinter.debug(sessionConfiguration, "planet exists and verifyed OK, continue update...");
		if (Osmupdate.testToolAvailability(sessionConfiguration) == true){

			Boolean updateSuccessful = false;
			int tries = 0;
			while ((tries < 5) && (updateSuccessful == false)){



				try {
					tries++;
					MessagePrinter.debug(sessionConfiguration, "Running osmupdate...");
					Osmupdate.runUpdate(sessionConfiguration, false);
					updateSuccessful = true;
					MessagePrinter.debug(sessionConfiguration, "Osmupdate finished OK!");

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					MessagePrinter.error(sessionConfiguration, "Update failed...");
					MessagePrinter.error(sessionConfiguration, "Waiting 3600 seconds before trying again.");
					try {
						Thread.sleep(1000*3600);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}			
		}
		else{
			MessagePrinter.error(sessionConfiguration, "osmupdate not found in path! (please rename osmupdate.exe to osmu.exe)");
		}


	}

	public static boolean verifyPlanet(Configuration sessionConfiguration){
		MessagePrinter.debug(sessionConfiguration, "Verifying planet...");
		boolean planetGood = true;
		// assume planet is OK until fault has been found

		// File exists
		File f = new File(sessionConfiguration.getWorkingDirectory() + File.separator + "planet.pbf");
		if (f.exists() == false){
			planetGood = false;
			MessagePrinter.debug(sessionConfiguration, sessionConfiguration.getWorkingDirectory() + File.separator + planetFilename + " does not exist... FAIL!");
		}
		else {
			MessagePrinter.debug(sessionConfiguration, sessionConfiguration.getWorkingDirectory() + File.separator + planetFilename + " exists... OK!");

		}

		// Size is at least the right size
		if (f.length() < OpenStreetMapProject.ApproximatePlanetSize()*0.9){
			planetGood = false;
			MessagePrinter.debug(sessionConfiguration, sessionConfiguration.getWorkingDirectory() + File.separator + planetFilename + " too small to be OK!");
		}
		else {
			MessagePrinter.debug(sessionConfiguration, sessionConfiguration.getWorkingDirectory() + File.separator + planetFilename + " file size OK (" + f.length()/1000000 + " MB)");			
		}


		// Max lastmodified is -14 to 1 days compared to current time.
		double fileDate = (f.lastModified() - System.currentTimeMillis())/(1000*60*60*24); //fileDate unit is days

		MessagePrinter.debug(sessionConfiguration, sessionConfiguration.getWorkingDirectory() + File.separator + planetFilename + " age is " + -fileDate*24 + " hours");			

		if (fileDate < - 14)
		{
			planetGood = false;
			MessagePrinter.debug(sessionConfiguration, sessionConfiguration.getWorkingDirectory() + File.separator + planetFilename + " is too old. (Last modified " + -fileDate + " days ago...)");			

		}

		if (fileDate > 1)
		{
			planetGood = false;
			MessagePrinter.debug(sessionConfiguration, sessionConfiguration.getWorkingDirectory() + File.separator + planetFilename + " is too new. (Last modified " + -fileDate + " days ago...)");			

		}

		return planetGood;
	}
}
