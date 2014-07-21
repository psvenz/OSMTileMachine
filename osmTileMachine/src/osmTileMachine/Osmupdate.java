package osmTileMachine;

// import java.io.File;



public class Osmupdate {
	static String toolFileName = "";
	public static boolean testToolAvailability(Configuration sessionConfiguration) {

		if (tryTool("osmu", sessionConfiguration))
		{
			toolFileName = "osmu";
			return true;
		} else if (tryTool("osmup", sessionConfiguration)) 
		{
			toolFileName = "osmup";
			return true;		
		} else if (tryTool("osmupdate", sessionConfiguration)) 
		{
			toolFileName = "osmupdate";
			return true;
		} else
			return false;
	}

	private static boolean tryTool (String executablefileName, Configuration sessionConfiguration)
	{
		ExternalToolLauncher e = new ExternalToolLauncher(sessionConfiguration);
		try {
			e.setCommand(executablefileName);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			return false;
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

	public static void runUpdate(Configuration sessionConfiguration, Boolean useCache, String fileName, String fileNameUpdated) throws Exception {
		// TODO Auto-generated method stub

		Boolean successfulUpdate = false;
//		String fileName = "\"" + sessionConfiguration.getWorkingDirectory() + File.separator + SourceFileMaintainer.sourceFileName + "\""; 
//		String fileNameUpdated = "\"" + sessionConfiguration.getWorkingDirectory() + File.separator + SourceFileMaintainer.updatedSourceFilename + "\""; 

		ExternalToolLauncher e = new ExternalToolLauncher(sessionConfiguration);
		e.setCommand(toolFileName);
		e.addArgument("-v");
		e.addArgument("--hour");
		e.addArgument("--day");
		e.addArgument("--minute");
		
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
