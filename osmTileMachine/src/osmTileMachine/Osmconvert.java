package osmTileMachine;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Osmconvert {
	public static void runExtractAction(Configuration sessionConfiguration, ExtractAction extractAction) throws Exception
	{
		

		
		ExternalToolLauncher e = new ExternalToolLauncher(sessionConfiguration);
		e.setCommand("osmconvert");
		e.addArgument(extractAction.getInputFileName());
		if (extractAction.getCutMethod() != ExtractAction.CUTMETHOD_NOCUT) {
				e.addArgument(boundaryBoxToArgument(extractAction.getBoundingBox()));
		}
		
		if (extractAction.getCutMethod() == ExtractAction.CUTMETHOD_COMPLEXWAYS){
			e.addArgument("--complex-ways");
		}
		
		if (extractAction.getAuthorStrategy() == ExtractAction.AUTHORSTRATEGY_DROP){
			e.addArgument("--drop-author");
		}
		if (extractAction.getAuthorStrategy() == ExtractAction.AUTHORSTRATEGY_FAKE){
			e.addArgument("--fake-author");
		}
		
		e.addArgument("-o=" + extractAction.getOutputFileName());
		e.addArgument("--hash-memory=900");
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
		DecimalFormat coordinateFormat = new DecimalFormat("###.#############");
		DecimalFormatSymbols coordinateFormatSymbols = coordinateFormat.getDecimalFormatSymbols();
		coordinateFormatSymbols.setDecimalSeparator('.');
		coordinateFormat.setDecimalFormatSymbols(coordinateFormatSymbols);

		return "-b=" + coordinateFormat.format(boundingBox.getMinLon()) + 
				"," + coordinateFormat.format(boundingBox.getMinLat()) + 
				"," + coordinateFormat.format(boundingBox.getMaxLon()) + 
				"," + coordinateFormat.format(boundingBox.getMaxLat()); 
	}
}
