package osmTileMachine;

import java.io.File;

public class Osmupdate {
	public static boolean testToolAvailability(Configuration sessionConfiguration) {
			ExternalToolLauncher e = new ExternalToolLauncher(sessionConfiguration);
			try {
				e.setCommand("osmu");
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

		public static void runUpdate(Configuration sessionConfiguration, Boolean useCache) throws Exception {
			// TODO Auto-generated method stub

			Boolean successfulUpdate = false;
			String updatingFilePrefix ="updating_";
			String fileName = "\"" + sessionConfiguration.getWorkingDirectory() + File.separator + PlanetMaintainer.planetFilename + "\""; 
			String fileNameUpdated = "\"" + sessionConfiguration.getWorkingDirectory() + File.separator + updatingFilePrefix + PlanetMaintainer.planetFilename + "\""; 

			ExternalToolLauncher e = new ExternalToolLauncher(sessionConfiguration);
			e.setCommand(sessionConfiguration.getOsmupdateFilename());
			e.addArgument("-v");
			e.addArgument("--hour");
			e.addArgument("--day");
			if (useCache) e.addArgument("--keep-tempfiles");
			e.addArgument(fileName);
			e.addArgument(fileNameUpdated);
			//		e.addArgument()
			e.run();

			if (e.ExitValue() == 0){
				successfulUpdate = true;
			}


			if (successfulUpdate == false) throw new Exception("Update failed...");

		}

	}
