package osmTileMachine;

public class DataUpdateAction extends Action{

	private String inputFileName;
	private String outputFileName;
	
	public DataUpdateAction(String i, String o)
	{
		inputFileName = i;
		outputFileName = o;
	}
	
	@Override
	int getActionType() {
		// TODO Auto-generated method stub
		return ACTIONTYPE_DATAUPDATEACTION;
	}


	
	@Override
	void runAction(Configuration sessionConfiguration) throws Exception {
		// TODO Auto-generated method stub
		if (Osmupdate.testToolAvailability(sessionConfiguration) == false) throw new Exception("DataUpdateAction failed, could not find osmupdate tool (osmu.exe)");
		Boolean useCache = true;
		Boolean SuccessfulUpdate = false;
		
		
		try {				
			Osmupdate.runUpdate(sessionConfiguration, useCache, inputFileName, outputFileName);
			SuccessfulUpdate = true;
		} catch (Exception e) {
			useCache = false;
			SuccessfulUpdate = false;
		}

		// Retry without cache
		if (SuccessfulUpdate == false) 
		{
			Osmupdate.runUpdate(sessionConfiguration, useCache, inputFileName, outputFileName);
		}
	
	}

	@Override
	String getActionInHumanReadableFormat() {
		// TODO Auto-generated method stub
		return "DataUpdateAction, input file: " + inputFileName + " output file: " + outputFileName;
	}

}
