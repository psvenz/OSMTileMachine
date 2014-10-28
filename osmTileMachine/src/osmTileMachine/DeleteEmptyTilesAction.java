package osmTileMachine;

import java.io.File;

public class DeleteEmptyTilesAction extends Action{

	Tile tile;
	int deleteZoomLevel;
	int minFileSize;

	public DeleteEmptyTilesAction(int minFileSize, Tile tile, int deleteZoomLevel) throws Exception {
		this.tile = tile;
		this.minFileSize = minFileSize;
		this.deleteZoomLevel = deleteZoomLevel;
	}

	@Override
	void runAction(Configuration sessionConfiguration) throws Exception {
		TileSet ts = tile.getAllHigherZoomTiles(deleteZoomLevel);
		ts.tileSetIteratorStart();
		long totalFilesExamined = 0;
		long deletedFiles = 0;
		while (ts.tileSetIteratorHasNext()){
			totalFilesExamined++;
			Tile t = ts.tileSetIteratorGetTile();

			int windowSize = 3;
			long maxFileSizeNearby = 0;
			
			
			for (int xDelta = -windowSize;xDelta <= windowSize; xDelta++)
			{
				for (int yDelta = -windowSize;yDelta <= windowSize; yDelta++)
				{
					String fileName = RenderAction.getImageFileName(new Tile(t.getX()+xDelta,t.getY()+yDelta,t.getZ(),""), sessionConfiguration);
					File dFile = new File(fileName);
					long fileSize = dFile.length();
					if (fileSize > maxFileSizeNearby) maxFileSizeNearby = fileSize;
				}
			}
			
			String fileName = RenderAction.getImageFileName(t, sessionConfiguration);
			File dFile = new File(fileName);
			
			
			if (maxFileSizeNearby < minFileSize){
//				MessagePrinter.debug(sessionConfiguration, "deleting file " + fileName + " filesizeNearby=" + maxFileSizeNearby);
				dFile.delete();
				deletedFiles++;
			}
			else
			{
//				MessagePrinter.debug(sessionConfiguration, "keeping  file " + fileName + " filesizeNearby=" + maxFileSizeNearby);
			}
		}
		MessagePrinter.notify(sessionConfiguration, "DeleteEmptyTiles: deleted " + deletedFiles + " of " + totalFilesExamined + " files");

	}



	@Override
	String getActionInHumanReadableFormat() {
		return "DeleteEmptyTilesAction: minsize=" + this.minFileSize + " tile: " + this.tile.toString();
	}

}
