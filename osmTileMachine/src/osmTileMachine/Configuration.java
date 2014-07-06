package osmTileMachine;

// import java.io.File;
import java.text.ParseException;

public class Configuration {

	private boolean downloadSource;

	private boolean updateSource;

	private boolean render;

	private boolean enableDebugOutput;
	private String requestedArea;

	private int firstAction;

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


			if (arg.toLowerCase().contentEquals("-download") ||  arg.toLowerCase().contentEquals("-d"))
			{
				setDownload(true);
			} 
			else if (arg.toLowerCase().contentEquals("-update") ||  arg.toLowerCase().contentEquals("-u"))
			{
				setUpdate(true);
			} 

			else if (arg.toLowerCase().contentEquals("-renderarea") ||  arg.toLowerCase().contentEquals("-r"))
			{
				if (i+1 == args.length) // nothing follows -operatingmode 
				{
					throw new ParseException("area argument missing", i);
				}
				setRequestedArea(args[i+1]);
				setRender(true);
				i++;
			}
			else if (arg.toLowerCase().contentEquals("-debug"))
			{
				setDebugOutput(true);
			}
			else if (arg.toLowerCase().contentEquals("-firstaction"))
			{
				if (i+1 == args.length) // nothing follows -operatingmode 
				{
					throw new ParseException("jumptoaction argument missing", i);
				}
				setFirstAction(Integer.parseUnsignedInt(args[i+1]));
				i++;
			}

			else
			{
				throw new ParseException(arg + " is not a valid input argument.", i);
			}

			i++;			
		}	
		if (enableDebugOutput) System.out.println("Successfully parsed all input arguments.");

	}
	private void setFirstAction(int i) {
		// TODO Auto-generated method stub
		firstAction = i;
	}
	private void setRender(boolean b) {
		// TODO Auto-generated method stub
		render = b;
	}

	public boolean getRender() {
		// TODO Auto-generated method stub
		return render;
	}
	
	private void setUpdate(boolean b) {
		// TODO Auto-generated method stub
		updateSource = b;
	}

	public boolean getUpdate()
	{
		return updateSource;
	}

	public int getFirstAction()
	{
		return firstAction;
	}

	public boolean getDownload()
	{
		return downloadSource;
	}

	private void setDownload(boolean b) {
		// TODO Auto-generated method stub
		downloadSource = b;
	}

	public Configuration()
	{
		//Creator
		setDownload(false);
		setUpdate(false);
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
