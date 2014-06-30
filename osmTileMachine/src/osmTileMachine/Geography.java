package osmTileMachine;

public class Geography {

	public static TileSet getTileSetForRegion(String nameOfRegion)
	{
		TileSet tileSet = new TileSet();
		nameOfRegion = nameOfRegion.trim();
		if (nameOfRegion.equalsIgnoreCase("dalarna"))
		{
			BoundingBox bbox = new BoundingBox(12.12, 59.85, 16.75, 60.28);
			tileSet = getTileSetForRegion(bbox);
		} 
		
		else if (nameOfRegion.equalsIgnoreCase("v�nern"))
		{
			BoundingBox bbox = new BoundingBox(12.306, 58.38, 14.094, 59.42);
			tileSet = getTileSetForRegion(bbox);
		}
		else if (nameOfRegion.equalsIgnoreCase("Niemisel"))
		{
			BoundingBox bbox = new BoundingBox(21.6, 65.9, 22.41, 66.9);
			tileSet = getTileSetForRegion(bbox);
		}
		else if (nameOfRegion.equalsIgnoreCase("germany"))
		{
			BoundingBox bbox = new BoundingBox(5, 47, 16, 55);
			tileSet = getTileSetForRegion(bbox);
		}

		else if (nameOfRegion.equalsIgnoreCase("scandinavia"))
		{
			BoundingBox bbox = new BoundingBox(4.8, 54.54, 31.6, 71.11);
			tileSet = getTileSetForRegion(bbox);
		}

		else if (nameOfRegion.equalsIgnoreCase("falun"))
		{
			BoundingBox bbox = new BoundingBox(15.1364136,60.3812903, 16.1553955, 60.7591595);
			tileSet = getTileSetForRegion(bbox);
		}


		return tileSet;
	}


	public static TileSet getTileSetForRegion(BoundingBox boundingBox)
	{
		TileSet tileSet = new TileSet();
		Tile t; 

		double xMinCoord = boundingBox.getMinLon();
		double xMaxCoord = boundingBox.getMaxLon();
		double yMinCoord = boundingBox.getMaxLat();
		double yMaxCoord = boundingBox.getMinLat();

		Tile topLeftTile = Tile.getTile(xMinCoord, yMinCoord, SplitAndRenderStrategy.getLowestRenderLevel());
		Tile bottomRightTile = Tile.getTile(xMaxCoord, yMaxCoord, SplitAndRenderStrategy.getLowestRenderLevel());

		int xMin = topLeftTile.getX();
		int xMax = bottomRightTile.getX();


		int yMin = topLeftTile.getY();
		int yMax = bottomRightTile.getY();

		for (int x = xMin ;x<=xMax;x++)			
		{
			for (int y = yMin;y<=yMax;y++)			
			{
				t = new Tile(x, y, SplitAndRenderStrategy.getLowestRenderLevel(), "");
				tileSet.add(t);
			}

		}



		return tileSet;
	}
}
