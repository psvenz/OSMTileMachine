package osmTileMachine;

import java.io.File;
// import java.io.File;
import java.text.ParseException;

public class Configuration {

	public final int SOURCETYPE_UNSPECIFIED = 0;
	public final int SOURCETYPE_DOWNLOAD = 1;
	public final int SOURCETYPE_URL = 2;
	public final int SOURCETYPE_LOCALFILE = 3;

	private int minFileSize = 0;
	private int sourceType;
	private boolean updateSource;
	private boolean render;
	private boolean enableDebugOutput;
	private String requestedArea;
	private int firstAction;
	private int maxZoom;
	private String source;
	private boolean keepDownload;
	private boolean keepIntermediateFiles;
	private String webrootDirectoryName;
	private boolean lowZoom;
	private String ruleSetFilename;
	private boolean lazyUpdate;

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


			if (arg.toLowerCase().contentEquals("-update"))
			{
				setUpdate(true);
			} 

			else if (arg.toLowerCase().contentEquals("-renderarea"))
			{
				if (i+1 == args.length) // nothing follows  
				{
					throw new ParseException("area argument missing", i);
				}
				setRequestedArea(args[i+1]);
				setRender(true);
				i++;
			}


			else if (arg.toLowerCase().contentEquals("-source"))
			{
				if (i+1 == args.length) // nothing follows -source 
				{
					throw new ParseException("source argument missing", i);
				}

				
				if (args[i+1].startsWith("download"))
				{
					setSource(args[i+1]);
					setSourceType(SOURCETYPE_DOWNLOAD);
				}
				else if (args[i+1].toLowerCase().startsWith("http://") || args[i+1].toLowerCase().startsWith("ftp://"))
				{
					setSource(args[i+1]);
					setSourceType(SOURCETYPE_URL);
				}
				else
				{
					//Assuming local file as source
					setSource(args[i+1]);
					setSourceType(SOURCETYPE_LOCALFILE);
				}
				i++;
			}
			else if (arg.toLowerCase().contentEquals("-verbose"))
			{
				setDebugOutput(true);
			}
			else if (arg.toLowerCase().contentEquals("-keepdownloaded"))
			{
				setKeepDownload(true);
			}			
			else if (arg.toLowerCase().contentEquals("-keepintermediate"))
			{
				setKeepIntermediateFiles(true);
			}
			
			
			else if (arg.toLowerCase().contentEquals("-generatelowzoom"))
			{
				setLowZoom(true);
			}

			
			else if (arg.toLowerCase().contentEquals("-lazyupdate"))
			{
				setLazyUpdate(true);
			}

			
			
			else if (arg.toLowerCase().contentEquals("-firstaction"))
			{
				if (i+1 == args.length) // nothing follows -firstaction 
				{
					throw new ParseException("firstaction argument missing", i);
				}
				setFirstAction(Integer.parseInt(args[i+1]));
				i++;
			}

			
			
			
			else if (arg.toLowerCase().contentEquals("-minfilesize"))
			{
				if (i+1 == args.length) // nothing follows -minfilesize 
				{
					throw new ParseException("minfilesize argument missing", i);
				}
				setMinFileSize(Integer.parseInt(args[i+1]));
				i++;
			}

			
			
			
			
			
			
			else if (arg.toLowerCase().contentEquals("-webroot"))
			{
				if (i+1 == args.length) // nothing follows 
				{
					throw new ParseException("webroot argument missing", i);
				}
				setWebrootDirectoryName(args[i+1]);
				i++;
			}

			
			else if (arg.toLowerCase().contentEquals("-rules"))
			{
				if (i+1 == args.length) // nothing follows 
				{
					throw new ParseException("rules argument missing", i);
				}
				setRuleSetFilename(args[i+1]);
				i++;
			}


			else if (arg.toLowerCase().contentEquals("-maxzoom"))
			{
				if (i+1 == args.length) // nothing follows -maxzoom 
				{
					throw new ParseException("maxzoom argument missing", i);
				}
				setMaxZoom(Integer.parseInt(args[i+1]));
				i++;
			}

			else
			{
				throw new ParseException(arg + " is not a valid input argument.", i);
			}

			i++;			
		}	

		if (getSourceType() == SOURCETYPE_UNSPECIFIED)
		{
			MessagePrinter.error(this, "source unspecified, mandatory input argument");
			throw new ParseException("Cannot continue without a specified source.", i);
		}
		if (enableDebugOutput) System.out.println("Successfully parsed all input arguments.");

	}
	private void setMinFileSize(int i) {
		// TODO Auto-generated method stub
		minFileSize = i;
	}
	private void setMaxZoom(int i) {
		// TODO Auto-generated method stub
		maxZoom = i;
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

	public int getSourceType()
	{
		return sourceType;
	}

	public boolean getLazyUpdate()
	{
		return lazyUpdate;
	}

	private void setSourceType(int i) {
		// TODO Auto-generated method stub
		sourceType = i;
	}

	public Configuration()
	{
		//Creator
		setSourceType(SOURCETYPE_UNSPECIFIED);
		setUpdate(false);
		setMaxZoom(13);
		webrootDirectoryName = System.getProperty("user.dir") +"\\" + "webroot";
		setLowZoom(false);
		setKeepDownload(false);
		setKeepIntermediateFiles(false);
		setRuleSetFilename("default.mrules");
		setLazyUpdate(false);
	}

	private void setLazyUpdate(boolean b) {
		this.lazyUpdate = b;
	}
	private void setRuleSetFilename(String string) {
		// TODO Auto-generated method stub
		ruleSetFilename = System.getProperty("user.dir") + "\\rules\\" + string;	
	}
	
	private void setLowZoom(boolean b) {
		// TODO Auto-generated method stub
		lowZoom = b;
	}

	public boolean getLowZoom() {
		// TODO Auto-generated method stub
		return lowZoom;
	}

	public void setSource(String string) {
		// TODO Auto-generated method stub
		source=string;
	}
	public String getSource()
	{
		return source;
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
		return ruleSetFilename;
//		return System.getProperty("user.dir") + "\\rules\\default.mrules";
	}
	
	public String getWebrootDirectoryName() {
		// TODO Auto-generated method stub
		return webrootDirectoryName;
	}

	public String getTileDirectoryName() {
		// TODO Auto-generated method stub
		return webrootDirectoryName + File.separator + "Tiles";
	}


	private void setWebrootDirectoryName(String s) {
		// TODO Auto-generated method stub
		webrootDirectoryName = s;
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
	public int getMaxZoom() {
		// TODO Auto-generated method stub
		return maxZoom;
	}
	public boolean getKeepDownload() {
		return keepDownload;
	}
	private void setKeepDownload(boolean keepDownload) {
		this.keepDownload = keepDownload;
	}
	public boolean getKeepIntermediateFiles() {
		return keepIntermediateFiles;
	}
	private void setKeepIntermediateFiles(boolean keepIntermediateFiles) {
		this.keepIntermediateFiles = keepIntermediateFiles;
	}
	public int getMinFileSize() {
		// TODO Auto-generated method stub
		return minFileSize;
	}

}
