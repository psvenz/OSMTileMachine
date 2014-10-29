package osmTileMachine;

import java.io.File;
import java.util.Hashtable;

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
		diskFileSizes = new Hashtable<String, Long>();
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


					Long fileSize = lookupFileSize(fileName);

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

	Hashtable<String, Long> diskFileSizes;

	private long lookupFileSize(String fileName) {
		// TODO Auto-generated method stub

		// Cached filesize?		
		Long n = diskFileSizes.get(fileName);
		if (n != null) {
			return n;
		}


		File dFile = new File(fileName);
		long fileSize = dFile.length();
		diskFileSizes.put(fileName, fileSize);



		return fileSize;


	}

	@Override
	String getActionInHumanReadableFormat() {
		return "DeleteEmptyTilesAction: minsize=" + this.minFileSize + " tile: " + this.tile.toString();
	}

}
