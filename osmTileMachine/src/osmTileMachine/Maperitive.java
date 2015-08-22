package osmTileMachine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Maperitive {

	public static void runRenderAction(Configuration sessionConfiguration,
			RenderAction renderAction) 
	{
		DecimalFormat coordinateFormat = new DecimalFormat("###.#############");
		DecimalFormatSymbols coordinateFormatSymbols = coordinateFormat.getDecimalFormatSymbols();
		coordinateFormatSymbols.setDecimalSeparator('.');
		coordinateFormat.setDecimalFormatSymbols(coordinateFormatSymbols);

		Tile tileToRender = new Tile(renderAction.getX(), renderAction.getY(), renderAction.getZ(), "Temporary tile for maperitive");
		File scriptFile = new File(sessionConfiguration.getWorkingDirectory() + File.separator + tileToRender.toString() + ".maperitivescript");
		String content;

		int zMax = renderAction.getzMax();		
		content = "use-script-dir" + "\r\n"
				+ "clear-map" + "\r\n"
				+ "use-ruleset location=" + '"' + sessionConfiguration.getRuleSetFilename() + '"' + "\r\n"
				+ "load-source " + renderAction.getDataFileName() +  "\r\n"
				+ "set-geo-bounds " 
				+ coordinateFormat.format((tileToRender.getLonMin()+0.0001)) + ","
				+  coordinateFormat.format((tileToRender.getLatMin()+0.0001)) + ","
				+  coordinateFormat.format((tileToRender.getLonMax()-0.0001)) + ","
				+  coordinateFormat.format((tileToRender.getLatMax()-0.0001)) + "\r\n"
				+ "change-dir " + renderAction.getOutputDirectoryName() + "\r\n"
				+ "generate-tiles minzoom=" + renderAction.getzMin() + " maxzoom=" + zMax + "\r\n";
		//Create output directory
		File dir = new File(renderAction.getOutputDirectoryName());
		dir.mkdir();
		
		//Write contents
		try {


			// if file doesnt exists, then create it
			if (!scriptFile.exists()) {
				scriptFile.createNewFile();
			}

			FileWriter fw = new FileWriter(scriptFile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();

		}

		ExternalToolLauncher maperitiveLauncher = new ExternalToolLauncher(sessionConfiguration);
		try {
			maperitiveLauncher.setCommand(sessionConfiguration.getMaperitiveExecutableFileName());
			maperitiveLauncher.addArgument("-exitafter");
			maperitiveLauncher.addArgument(scriptFile.getCanonicalFile().toString());
			maperitiveLauncher.run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getScriptFileName(RenderAction renderAction) {
		// TODO Auto-generated method stub
		Tile tileToRender = new Tile(renderAction.getX(), renderAction.getY(), renderAction.getZ(), "Temporary tile for maperitive");
		String scriptFileName = tileToRender.toString() + ".maperitivescript";

		return scriptFileName;
	}
}
