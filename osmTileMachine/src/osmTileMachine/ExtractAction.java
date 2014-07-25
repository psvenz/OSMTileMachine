package osmTileMachine;

public class ExtractAction extends Action{

//	int getActionType() {
//		return ACTIONTYPE_EXTRACTACTION;
//	}
	
	public final static int TOOL_OSMCONVERT = 1;
	public final static int CUTMETHOD_CLIP = 1;
	public final static int CUTMETHOD_COMPLEXWAYS = 2;
	private int tool;
	private BoundingBox boundingBox;
	private int cutMethod;
	private String inputFileName;
	private String outputFileName;

	public ExtractAction(int tool, BoundingBox boundingBox, int cutMethod, String inputFileName, String outputFileName){
		this.setTool(tool);
		this.setBoundingBox(boundingBox);
		this.setCutMethod(cutMethod);
		this.setInputFileName(inputFileName);
		this.setOutputFileName(outputFileName);
	}
	
	void runAction(Configuration sessionConfiguration) throws Exception {
		if (this.getTool() == TOOL_OSMCONVERT)
		{
			Osmconvert.runExtractAction(sessionConfiguration, this);
		}
			
	}
	
	public BoundingBox getBoundingBox() {
		return boundingBox;
	}

	private void setBoundingBox(BoundingBox boundingBox) {
		this.boundingBox = boundingBox;
	}

	public int getCutMethod() {
		return cutMethod;
	}

	private void setCutMethod(int cutMethod) {
		this.cutMethod = cutMethod;
	}

	public String getInputFileName() {
		return inputFileName;
	}

	private void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	private void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

	private int getTool() {
		return tool;
	}

	private void setTool(int tool) {
		this.tool = tool;
	}

	public String getActionInHumanReadableFormat() {
		return "ExtractAction, input: " + inputFileName + ", output: " + outputFileName + ", bbox:" + boundingBox.toString(); 
	}


}
