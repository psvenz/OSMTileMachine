package osmTileMachine;

public class Osmconvert {
	public static void runExtractAction(Configuration sessionConfiguration, ExtractAction extractAction) throws Exception
	{
		ExternalToolLauncher e = new ExternalToolLauncher(sessionConfiguration);
		e.setCommand("osmconvert");
		String inputFileName = extractAction.getInputFileName(); 
		e.addArgument(inputFileName);
		e.addArgument(boundaryBoxToArgument(extractAction.getBoundingBox()));
		String outputFileName = extractAction.getOutputFileName(); 
		if (extractAction.getCutMethod() == ExtractAction.CUTMETHOD_COMPLEXWAYS){
			e.addArgument("--complex-ways");
		}
		e.addArgument("-o=" + outputFileName);
		e.run();
		if (e.ExitValue() == 0) 
		{
			return;
		}
		else
		{
			throw new Exception("Osmconvert failed to produce extract!");
		}
		
	}

	private static String boundaryBoxToArgument(BoundingBox boundingBox)
	{
		return "-b=" + boundingBox.getMinLon() + 
				"," + boundingBox.getMinLat() + 
				"," + boundingBox.getMaxLon() + 
				"," + boundingBox.getMaxLat(); 
	}
}
