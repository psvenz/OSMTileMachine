package osmTileMachine;

public class Osmosis {

	public static void runExtractAction(Configuration sessionConfiguration,
			ExtractAction extractAction) throws Exception {
		// TODO Auto-generated method stub
		ExternalToolLauncher e = new ExternalToolLauncher(sessionConfiguration);
		e.setCommand("osmosis.bat");
		if (extractAction.getInputFileName().toLowerCase().endsWith(".pbf"))
		{
			e.addArgument("--read-pbf-fast");
			e.addArgument("workers=1");
		} else {
			e.addArgument("--fast-read-xml");
			e.addArgument("enableDateParsing=no");
		}
		
		
		e.addArgument(extractAction.getInputFileName());
		
//		e.addArgument("--buffer");	
		e.addArgument("--bounding-box");		
		
		
		e.addArgument("left=" + extractAction.getBoundingBox().getMinLon()); 
		e.addArgument("right=" + extractAction.getBoundingBox().getMaxLon()); 
		e.addArgument("top=" + extractAction.getBoundingBox().getMaxLat()); 
		e.addArgument("bottom=" + extractAction.getBoundingBox().getMinLat()); 
	
	
	e.addArgument("completeRelations=yes");
		if (extractAction.getCutMethod() == ExtractAction.CUTMETHOD_COMPLEXWAYS){
		}

//		e.addArgument("--drop-author");

//		e.addArgument("--buffer");	
		
		e.addArgument("--log-progress");
		e.addArgument("interval=60");
		e.addArgument("--write-xml");  
		e.addArgument(extractAction.getOutputFileName());
		e.run();

		if (e.ExitValue() == 0) 
		{
			return;
		}
		else
		{
			throw new Exception("Osmosis failed to produce extract!");
		}



	}
	
}
