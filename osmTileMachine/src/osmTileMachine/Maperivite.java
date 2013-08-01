package osmTileMachine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Maperivite {

	public static void runRenderAction(Configuration sessionConfiguration,
			RenderAction renderAction) 
	{
		Tile tileToRender = new Tile(renderAction.getX(), renderAction.getY(), renderAction.getZ(), "Temporary tile for maperitive");
		File scriptFile = new File(tileToRender.toString() + ".maperitivescript");
		String content;

		content = "use-script-dir" + "\r\n"
				+ "clear-map" + "\r\n"
				+ "use-ruleset location="+ sessionConfiguration.getRuleSetFilename() + "\r\n"
				+ "load-source " + renderAction.getDataFileName() +  "\r\n"
				+ "set-geo-bounds " 
				+ (tileToRender.getLonMin()+0.0001) + ","
				+ (tileToRender.getLatMin()+0.0001) + ","
				+ (tileToRender.getLonMax()-0.0001) + ","
				+ (tileToRender.getLatMax()-0.0001) + "\r\n"
				+ "change-dir " + renderAction.getOutputDirectoryName() + "\r\n"
				+ "generate-tiles minzoom=" + renderAction.getZ() + " maxzoom=" + renderAction.getzMax() + "\r\n";
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
}
