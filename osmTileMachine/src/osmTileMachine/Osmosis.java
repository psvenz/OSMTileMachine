package osmTileMachine;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Osmosis {

	public static void runExtractAction(Configuration sessionConfiguration,
			ExtractAction extractAction) throws Exception {

		DecimalFormat coordinateFormat = new DecimalFormat("###.#############");
		DecimalFormatSymbols coordinateFormatSymbols = coordinateFormat.getDecimalFormatSymbols();
		coordinateFormatSymbols.setDecimalSeparator('.');
		coordinateFormat.setDecimalFormatSymbols(coordinateFormatSymbols);

		
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
		
		
		e.addArgument("left=" + coordinateFormat.format(extractAction.getBoundingBox().getMinLon())); 
		e.addArgument("right=" + coordinateFormat.format(extractAction.getBoundingBox().getMaxLon())); 
		e.addArgument("top=" + coordinateFormat.format(extractAction.getBoundingBox().getMaxLat())); 
		e.addArgument("bottom=" + coordinateFormat.format(extractAction.getBoundingBox().getMinLat())); 
	
	
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
	public static void verifyPBFintegrity(Configuration sessionConfiguration,
			String filename) throws Exception {
//		// TODO Auto-generated method stub
//		ExternalToolLauncher e = new ExternalToolLauncher(sessionConfiguration);
//		e.setCommand("osmosis.bat");
//			e.addArgument("--read-pbf-fast");
//			e.addArgument("enableDateParsing=no");
//		e.addArgument("--write-null");		
//		
//		
//		e.addArgument("left=" + extractAction.getBoundingBox().getMinLon()); 
//		e.addArgument("right=" + extractAction.getBoundingBox().getMaxLon()); 
//		e.addArgument("top=" + extractAction.getBoundingBox().getMaxLat()); 
//		e.addArgument("bottom=" + extractAction.getBoundingBox().getMinLat()); 
//	
//	
//	e.addArgument("completeRelations=yes");
//		if (extractAction.getCutMethod() == ExtractAction.CUTMETHOD_COMPLEXWAYS){
//		}
//
////		e.addArgument("--drop-author");
//
////		e.addArgument("--buffer");	
//		
//		e.addArgument("--log-progress");
//		e.addArgument("interval=60");
//		e.addArgument("--write-xml");  
//		e.addArgument(extractAction.getOutputFileName());
//		e.run();
//
//		if (e.ExitValue() == 0) 
//		{
//			return;
//		}
//		else
//		{
//			throw new Exception("Osmosis failed to produce extract!");
//		}
//
//

	}

}
