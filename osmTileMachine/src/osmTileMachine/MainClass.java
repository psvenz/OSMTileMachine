package osmTileMachine;
import java.lang.System;
public class MainClass {
	public static void main(String[] args) throws Exception
	{
		Configuration sessionConfiguration = new Configuration();
		sessionConfiguration.setDebugOutput(true);
		
		
		try
		{
			sessionConfiguration.parseInputArguments(args);
		}catch(Exception e)
		{
		
			System.out.println("Error parsing input arguments!");
			System.out.println(e);
		}
		
		if (sessionConfiguration.getOperatingMode() == Configuration.OPERATINGMODE_FORCEPLANETDOWNLOAD)
		{
			PlanetMaintainer.forcePlanetDownload(sessionConfiguration);
			
		}		
		else if (sessionConfiguration.getOperatingMode() == Configuration.OPERATINGMODE_UPDATEPLANET) {
			PlanetMaintainer.updatePlanet(sessionConfiguration);
		}
		else if (sessionConfiguration.getOperatingMode() == Configuration.OPERATINGMODE_VERIFYPLANET) {
			boolean planetStatus = PlanetMaintainer.verifyPlanet(sessionConfiguration);
			if (planetStatus == true) {
				MessagePrinter.notify(sessionConfiguration, "Planet OK!");
			} else {
				MessagePrinter.notify(sessionConfiguration, "Planet NOT OK!");
			}
		}
		else
		{
			throw new Exception("Error: no operating mode specified.");
		}
	}
}
