package osmTileMachine;

import java.io.File;


public class RenderAction extends Action{
	public final static int TOOL_MAPERITIVE = 1;

//	int getActionType() {
//		return ACTIONTYPE_RENDERACTION;
//	}

	private int x, y, z, zMax, tool;
	private String ruleSetFileName, outputDirectoryName;
	private String dataFileName;
	
	public RenderAction (int tool, int x, int y, int z, int zMax, String ruleSetFileName, String outputDirectoryName, String dataFileName)
	{
		this.tool = tool;
		this.x = x;
		this.y = y;
		this.z = z;
		this.zMax = zMax;
		this.dataFileName = dataFileName;
		this.ruleSetFileName = ruleSetFileName;
		this.outputDirectoryName = outputDirectoryName;
	}
	
	void runAction(Configuration sessionConfiguration) throws Exception {
		if (this.tool == TOOL_MAPERITIVE)
		{
			Maperitive.runRenderAction(sessionConfiguration, this);
		}
			
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
	
	public String toString()
	{
		return  "Z: " + z + " X: " + x + " Y: " + y;
	}

	public String getScriptFileName() {
		// TODO Auto-generated method stub
		return Maperitive.getScriptFileName(this);
	}

	public static String getImageFileName(Tile t,
			Configuration sessionConfiguration) {
		// TODO Auto-generated method stub
		return sessionConfiguration.getWebrootDirectoryName() + File.separator + "Tiles" + File.separator + t.getZ() + File.separator + t.getX() + File.separator + t.getY() + ".png";
	}
	
	
}
