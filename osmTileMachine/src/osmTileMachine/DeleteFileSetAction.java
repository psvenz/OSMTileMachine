package osmTileMachine;

import java.io.File;
import java.util.ArrayList;

public class DeleteFileSetAction extends Action{

	public DeleteFileSetAction() {
		fileNames = new ArrayList<String>();
	}

	private ArrayList<String> fileNames;

	@Override
	void runAction(Configuration sessionConfiguration) throws Exception {
		// TODO Auto-generated method stub
		String fileNameToDelete;
		while (fileNames.size() > 0) {
			fileNameToDelete = fileNames.remove(0);
			MessagePrinter.debug(sessionConfiguration, "deleting file " + fileNameToDelete);
			File dFile = new File(fileNameToDelete);
			dFile.delete();
		}
	}

	public void addFileName(String newFileName){
		fileNames.add(newFileName);
	}



	@Override
	String getActionInHumanReadableFormat() {
		// TODO Auto-generated method stub

		if (fileNames.size() == 1){ 
			return "DeleteFileSetAction, file: " + fileNames.get(0);
		} else if  (fileNames.size() > 1){
			return "DeleteFileSetAction, (" + fileNames.size() + " files) First file: " + fileNames.get(0);
		}
		else{
			return "DeleteFileSetAction, (empty filelist)";
		}

	}
}

