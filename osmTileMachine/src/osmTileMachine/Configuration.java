package osmTileMachine;

// import java.io.File;
import java.text.ParseException;

public class Configuration {
	public static final int OPERATINGMODE_NOTSET = 0;
	public static final int OPERATINGMODE_FORCEPLANETDOWNLOAD = 1;
	public static final int OPERATINGMODE_UPDATEPLANET = 2;
	public static final int OPERATINGMODE_VERIFYPLANET = 3;
	public static final int OPERATINGMODE_RENDERAREA = 4;

	private int operatingMode;
	private boolean enableDebugOutput;
	private String requestedArea;

	public void parseInputArguments(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		if (args.length == 0)
		{
			throw new ParseException("No input arguments given, see documentation for usage.",0);
		}
		int i = 0;
		
		while (i < args.length)
		{
			String arg;
			arg = args[i];

			if (enableDebugOutput) System.out.println("Parsing argument number " + String.valueOf(i) + ": " + arg);


			if (arg.toLowerCase().contentEquals("-operatingmode") ||  arg.toLowerCase().contentEquals("-o"))
			{
				if (i+1 == args.length) // nothing follows -operatingmode 
				{
					throw new ParseException("operatingmode argument missing", i);
				}
				String operatingModeString = args[i+1].toUpperCase();
				if (operatingModeString.contentEquals("FORCEPLANETDOWNLOAD")) 
				{
					setOperatingMode(OPERATINGMODE_FORCEPLANETDOWNLOAD);	
					if (enableDebugOutput) System.out.println("Operating mode is FORCEPLANETDOWNLOAD");
				}
				else if (operatingModeString.contentEquals("UPDATEPLANET")) {
					setOperatingMode(OPERATINGMODE_UPDATEPLANET);
					if (enableDebugOutput) System.out.println("Operating mode is UPDATEPLANET");
				}
				else if (operatingModeString.contentEquals("RENDERAREA")) {
					setOperatingMode(OPERATINGMODE_RENDERAREA);
					if (enableDebugOutput) System.out.println("Operating mode is RENDERAREA");
				}
				else if (operatingModeString.contentEquals("VERIFYPLANET")) {
					setOperatingMode(OPERATINGMODE_VERIFYPLANET);
					if (enableDebugOutput) System.out.println("Operating mode is VERIFYPLANET");
				}
				else 
				{
					throw new ParseException(operatingModeString + " is not a valid operatingmode.", i);
				}
				i++;
			} 
			else if (arg.toLowerCase().contentEquals("-area") ||  arg.toLowerCase().contentEquals("-a"))
			{
				if (i+1 == args.length) // nothing follows -operatingmode 
				{
					throw new ParseException("area argument missing", i);
				}
				setRequestedArea(args[i+1]);
				i++;
			}
			else if (arg.toLowerCase().contentEquals("-debug") ||  arg.toLowerCase().contentEquals("-d"))
			{
				
				setDebugOutput(true);
				
			}
			else
			{
				throw new ParseException(arg + " is not a valid input argument.", i);
			}

			i++;			
		}	
		if (enableDebugOutput) System.out.println("Successfully parsed all input arguments.");

	}
	public void setOperatingMode(int i) throws ParseException {
		// TODO Auto-generated method stub
		if (operatingMode != OPERATINGMODE_NOTSET)
		{
			throw new ParseException("Cannot set operatingmode again.",0);
		}
		operatingMode = i;
	}
	
	public Configuration()
	{
		//Creator
		this.operatingMode = OPERATINGMODE_NOTSET;
	}
	public int getOperatingMode() {
		return operatingMode;
	}


	public void setDebugOutput(boolean b) {
		// TODO Auto-generated method stub
		enableDebugOutput = b;
	}
	public String getWorkingDirectory() {
		// TODO Auto-generated method stub
		return System.getProperty("user.dir") + "\\workdir";
	}
	public boolean getDebugOutput() {
		// TODO Auto-generated method stub
		return enableDebugOutput;
	}
	public String getRuleSetFilename() {

	return System.getProperty("user.dir") + "\\rules\\mtbmap.se.rules";
	
	}
	public String getOutputDirectoryName() {
		// TODO Auto-generated method stub
		return System.getProperty("user.dir") +"\\" + "webroot";
	}
	public String getMaperitiveExecutableFileName() {
		// TODO Auto-generated method stub
		return "tools\\maperitive\\maperitive.exe";
	}
	public String getRequestedArea() {
		return requestedArea;
	}
	public void setRequestedArea(String requestedArea) {
		this.requestedArea = requestedArea;
	}

}
