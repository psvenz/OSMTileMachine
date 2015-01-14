package osmTileMachine;

import java.io.File;

public class RenderAction extends Action{
	public final static int TOOL_MAPERITIVE = 1;

	//	int getActionType() {
	//		return ACTIONTYPE_RENDERACTION;
	//	}

	private int x, y, z, zMax, tool, zMin;
	private String ruleSetFileName, outputDirectoryName;
	private String dataFileName;

	public RenderAction (int tool, int x, int y, int z, int zMax, String ruleSetFileName, String outputDirectoryName, String dataFileName)
	{
		this.tool = tool;
		this.x = x;
		this.y = y;
		this.z = z;
		this.zMax = zMax;
		this.zMin = z;
		this.dataFileName = dataFileName;
		this.ruleSetFileName = ruleSetFileName;
		this.outputDirectoryName = outputDirectoryName;
	}

	void runAction(Configuration sessionConfiguration) throws Exception {
		if (this.tool == TOOL_MAPERITIVE)
		{
			if ((this.zMax <= 14) || (sessionConfiguration.getLazyUpdate() == false))
			{
				//Lazyupdate disabled or requested zMax is 14 or less, just render...
				Maperitive.runRenderAction(sessionConfiguration, this);
			}
			else 
			{
				MessagePrinter.notify(sessionConfiguration, "Lazyupdate status: Evaluating png file sizes at zoom level 14");

				//Requested zMax deeper than 14, render in two steps z-14 first, then 14-zMax if criterion is met
				int requestedZmax = this.zMax;
				String requestedOutputDirectoryName = this.outputDirectoryName; // Render into the workdirectory first...
				this.outputDirectoryName = sessionConfiguration.getWorkingDirectory();		
				this.zMax = 14;
				Maperitive.runRenderAction(sessionConfiguration, this);


				if (pngFileCompareEquals(sessionConfiguration.getWorkingDirectory(), requestedOutputDirectoryName, new Tile(this.x, this.y, this.z, "temp"),this.zMax, sessionConfiguration) == false)
				{
					// Insert logic to skip deeper levels conditionally here...
					this.zMin = this.z;
					this.zMax = requestedZmax; //Restore
					this.outputDirectoryName = requestedOutputDirectoryName; // Restore
					Maperitive.runRenderAction(sessionConfiguration, this);				
				}
			}

		}

	}

	private boolean pngFileCompareEquals(String workDir,
			String targetDir, Tile tile, int zMax, Configuration sessionConfiguration) throws Exception {



		TileSet ts = tile.getAllHigherZoomTiles(zMax);
		ts.tileSetIteratorStart();
		while (ts.tileSetIteratorHasNext()){
			Tile t = ts.tileSetIteratorGetTile();

			String fileName1 = RenderAction.getImageFileName(t, workDir, sessionConfiguration);
			Long fileSize1 = lookupFileSize(fileName1);

			String fileName2 = RenderAction.getImageFileName(t, targetDir, sessionConfiguration);
			Long fileSize2 = lookupFileSize(fileName2);

			if (fileSize1.equals(fileSize2) == false) 
				{
				MessagePrinter.notify(sessionConfiguration, "Lazyupdate status: File size difference found in " + t.toString());
				sessionConfiguration.setLazyUpdadateLastStatus(sessionConfiguration.LAZYUPDATELASTSTATUS_RENDERED);
				deleteFolder(workDir + File.separator + "Tiles", true);
				return false;
				}
			

		}
		MessagePrinter.notify(sessionConfiguration, "lazyupdate status: No difference found in tiles in 1) " + workDir + " and 2) " + targetDir + " (lazyupdate: skipping re-rendering)");
		sessionConfiguration.setLazyUpdadateLastStatus(sessionConfiguration.LAZYUPDATELASTSTATUS_SKIPPED);
		deleteFolder(workDir + File.separator + "Tiles", true);
		return true;
	}

	  public static boolean deleteFolder(String filePath, boolean recursive) {
	      File file = new File(filePath);
	      if (!file.exists()) {
	          return true;
	      }

	      if (!recursive || !file.isDirectory())
	          return file.delete();

	      String[] list = file.list();
	      for (int i = 0; i < list.length; i++) {
	          if (!deleteFolder(filePath + File.separator + list[i], true))
	              return false;
	      }

	      return file.delete();
	  }
	
	private long lookupFileSize(String fileName) {
		// TODO Auto-generated method stub

		File dFile = new File(fileName);
		long fileSize = dFile.length();

		return fileSize;


	}

	String getActionInHumanReadableFormat() {
		return "RenderAction, input: " + dataFileName + ", output: " + outputDirectoryName + ", Z: " + z + " X: " + x + " Y: " + y;
	}

	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getZ() {
		return z;
	}

	public String getRuleSetFileName() {
		return ruleSetFileName;
	}

	public String getOutputDirectoryName() {
		return outputDirectoryName;
	}

	public String getDataFileName() {
		return dataFileName;
	}

	public int getzMax() {
		return zMax;
	}

	public int getzMin() {
		return zMin;
	}

	public String toString()
	{
		return  "Z: " + z + " X: " + x + " Y: " + y;
	}

	public String getScriptFileName() {
		// TODO Auto-generated method stub
		return Maperitive.getScriptFileName(this);
	}

	public static String getImageFileName(Tile t, String dir,
			Configuration sessionConfiguration) {
		// TODO Auto-generated method stub
		return dir + File.separator + "Tiles" + File.separator + t.getZ() + File.separator + t.getX() + File.separator + t.getY() + ".png";
	}


}
