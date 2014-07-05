package osmTileMachine;

public class Osmconvert {
	public static void runExtractAction(Configuration sessionConfiguration, ExtractAction extractAction) throws Exception
	{
		ExternalToolLauncher e = new ExternalToolLauncher(sessionConfiguration);
		e.setCommand("osmconvert");
		e.addArgument(extractAction.getInputFileName());
		e.addArgument(boundaryBoxToArgument(extractAction.getBoundingBox()));

		if (extractAction.getCutMethod() == ExtractAction.CUTMETHOD_COMPLEXWAYS){
			e.addArgument("--complex-ways");
		}
		
		e.addArgument("--drop-author");
		
		e.addArgument("-o=" + extractAction.getOutputFileName());
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
