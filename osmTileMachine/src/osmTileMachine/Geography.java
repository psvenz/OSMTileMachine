package osmTileMachine;

public class Geography {

	public static TileSet getTileSetForRegion(String nameOfRegion)
	{
		TileSet tileSet = new TileSet();
		nameOfRegion = nameOfRegion.trim();
		if (nameOfRegion.equalsIgnoreCase("dalarna"))
		{
			Tile t; 
			t = new Tile(273, 142, 9, "Dalarna"); tileSet.add(t);
			t = new Tile(274, 142, 9, "Dalarna"); tileSet.add(t);
			t = new Tile(275, 142, 9, "Dalarna"); tileSet.add(t);

			t = new Tile(273, 143, 9, "Dalarna"); tileSet.add(t);
			t = new Tile(274, 143, 9, "Dalarna"); tileSet.add(t);
			t = new Tile(275, 143, 9, "Dalarna"); tileSet.add(t);
			t = new Tile(276, 143, 9, "Dalarna"); tileSet.add(t);

			t = new Tile(273, 144, 9, "Dalarna"); tileSet.add(t);
			t = new Tile(274, 144, 9, "Dalarna"); tileSet.add(t);
			t = new Tile(275, 144, 9, "Dalarna"); tileSet.add(t);
			t = new Tile(276, 144, 9, "Dalarna"); tileSet.add(t);
			t = new Tile(277, 144, 9, "Dalarna"); tileSet.add(t);
			t = new Tile(278, 144, 9, "Dalarna"); tileSet.add(t);

			t = new Tile(273, 145, 9, "Dalarna"); tileSet.add(t);
			t = new Tile(274, 145, 9, "Dalarna"); tileSet.add(t);
			t = new Tile(275, 145, 9, "Dalarna"); tileSet.add(t);
			t = new Tile(276, 145, 9, "Dalarna"); tileSet.add(t);
			t = new Tile(277, 145, 9, "Dalarna"); tileSet.add(t);
			t = new Tile(278, 145, 9, "Dalarna"); tileSet.add(t);
			t = new Tile(279, 145, 9, "Dalarna"); tileSet.add(t);

			t = new Tile(273, 146, 9, "Dalarna"); tileSet.add(t);
			t = new Tile(274, 146, 9, "Dalarna"); tileSet.add(t);
			t = new Tile(275, 146, 9, "Dalarna"); tileSet.add(t);
			t = new Tile(276, 146, 9, "Dalarna"); tileSet.add(t);
			t = new Tile(277, 146, 9, "Dalarna"); tileSet.add(t);
			t = new Tile(278, 146, 9, "Dalarna"); tileSet.add(t);
			t = new Tile(279, 146, 9, "Dalarna"); tileSet.add(t);


			t = new Tile(273, 147, 9, "Dalarna"); tileSet.add(t);
			t = new Tile(274, 147, 9, "Dalarna"); tileSet.add(t);
			t = new Tile(275, 147, 9, "Dalarna"); tileSet.add(t);
			t = new Tile(276, 147, 9, "Dalarna"); tileSet.add(t);
			t = new Tile(277, 147, 9, "Dalarna"); tileSet.add(t);
			t = new Tile(278, 147, 9, "Dalarna"); tileSet.add(t);
			t = new Tile(279, 147, 9, "Dalarna"); tileSet.add(t);


			t = new Tile(273, 148, 9, "Dalarna"); tileSet.add(t);
			t = new Tile(274, 148, 9, "Dalarna"); tileSet.add(t);
			t = new Tile(275, 148, 9, "Dalarna"); tileSet.add(t);
			t = new Tile(276, 148, 9, "Dalarna"); tileSet.add(t);
			t = new Tile(277, 148, 9, "Dalarna"); tileSet.add(t);
			t = new Tile(278, 148, 9, "Dalarna"); tileSet.add(t);
			t = new Tile(279, 148, 9, "Dalarna"); tileSet.add(t);

			t = new Tile(277, 149, 9, "Dalarna"); tileSet.add(t);
		} 
		
		else if (nameOfRegion.equalsIgnoreCase("vänern"))
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
