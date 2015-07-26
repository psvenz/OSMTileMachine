package osmTileMachine;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Geography {

	public static TileSet getTileSetForRegion(String nameOfRegion)
	{
		TileSet tileSet = new TileSet();
		nameOfRegion = nameOfRegion.trim();
		if (nameOfRegion.equalsIgnoreCase("dalarna"))
		{
			BoundingBox bbox = new BoundingBox(12.12, 59.85, 16.75, 62.28);
			tileSet = getTileSetForRegion(bbox);
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

		else if (nameOfRegion.equalsIgnoreCase("vanernvatternmalarentest"))
		{
			BoundingBox bbox = new BoundingBox(12, 57.6, 19, 59.9);
			tileSet = getTileSetForRegion(bbox);
		}
		else if (nameOfRegion.equalsIgnoreCase("bodensee"))
		{
			BoundingBox bbox = new BoundingBox(8.75, 47.3, 9.77, 47.9);
			tileSet = getTileSetForRegion(bbox);
		}
		else if (nameOfRegion.equalsIgnoreCase("frankfurt"))
		{
			BoundingBox bbox = new BoundingBox(7.7, 49.8, 10, 50.6);
			tileSet = getTileSetForRegion(bbox);
		}

		else if (nameOfRegion.equalsIgnoreCase("holland"))
		{
			BoundingBox bbox = new BoundingBox(3, 50.7, 8, 53.7);
			tileSet = getTileSetForRegion(bbox);
		}

		else if (nameOfRegion.equalsIgnoreCase("falun"))
		{
			BoundingBox bbox = new BoundingBox(15.1364136,60.3812903, 16.1553955, 60.7591595);
			tileSet = getTileSetForRegion(bbox);
		}
		else if (nameOfRegion.startsWith("box="))
		{
			int separator1 = nameOfRegion.indexOf(";", 0);
			int separator2 = nameOfRegion.indexOf(";", separator1+1);
			int separator3 = nameOfRegion.indexOf(";", separator2+1);

			String minLonString = nameOfRegion.substring(4, separator1); 
			String minLatString = nameOfRegion.substring(separator1+1, separator2); 
			String maxLonString = nameOfRegion.substring(separator2+1, separator3); 
			String maxLatString = nameOfRegion.substring(separator3+1, nameOfRegion.length()); 

			BoundingBox bbox = new BoundingBox(Double.parseDouble(minLonString), Double.parseDouble(minLatString),Double.parseDouble(maxLonString), Double.parseDouble(maxLatString));
			tileSet = getTileSetForRegion(bbox);

		}
		else if (nameOfRegion.startsWith("file="))
		{

			String fileName = nameOfRegion.substring(5, nameOfRegion.length()); 
			try {
				tileSet = getTileSetForFileArea(fileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}



		return tileSet;
	}


	private static TileSet getTileSetForFileArea(String fileName) throws IOException {
		TileSet tileSet = new TileSet();

		// Open the file
		FileInputStream fstream = new FileInputStream(fileName);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String strLine;

		//Read File Line By Line
		while ((strLine = br.readLine()) != null)   {
			// Print the content on the console
			//determine if is is a node
			if (strLine.trim().substring(0, 6).equals("<node "))
			{
				double lat = 0;
				double lon = 0;
				String remainingLine = strLine.trim().substring(6, strLine.trim().length());

				//extract one parameter at a time
				while (remainingLine.length()>4)
				{
					int firstQuotePos = remainingLine.indexOf('"', 0);
					int secondQuotePos = remainingLine.indexOf('"', firstQuotePos+1);
					String extractedParameter = remainingLine.substring(0, secondQuotePos+1);

					String ParameterKey = remainingLine.substring(0, firstQuotePos-1);
					String ParameterValue = remainingLine.substring(firstQuotePos+1, secondQuotePos);
					
//					System.out.println ("key:"+ParameterKey + "ENDKEY... Value:" + ParameterValue + "ENDVALUE");
	
					if (ParameterKey.equals("lat")) lat = Double.parseDouble(ParameterValue);
					if (ParameterKey.equals("lon")) lon = Double.parseDouble(ParameterValue);
					
					
					remainingLine = remainingLine.substring(secondQuotePos+1, remainingLine.length()).trim();
				}
				
				if (lat != 0 && lon != 0)
				{
//					System.out.println("Node detected at lat=" + lat + " lon=" +lon);
					Tile t = Tile.getTile(lon, lat, SplitAndRenderStrategy.getLowestRenderLevel());
					int tX = t.getX();
					int tY = t.getY();
					int tZ = t.getZ();
					
					tileSet.add(t);
					
					//Add surrounding tiles as well
					tileSet.add(new Tile(tX+1,tY+1 ,tZ,"surrounding"));
					tileSet.add(new Tile(tX+1,tY   ,tZ,"surrounding"));
					tileSet.add(new Tile(tX+1,tY-1 ,tZ,"surrounding"));
					tileSet.add(new Tile(tX  ,tY+1 ,tZ,"surrounding"));
					tileSet.add(new Tile(tX  ,tY-1 ,tZ,"surrounding"));
					tileSet.add(new Tile(tX-1,tY+1 ,tZ,"surrounding"));
					tileSet.add(new Tile(tX-1,tY   ,tZ,"surrounding"));
					tileSet.add(new Tile(tX-1,tY-1 ,tZ,"surrounding"));

				}
			}


			//Extract lat
			//Extract lon
			//Create tile from lat,lon pair
			//Add to tile to tileset
		}

		br.close();


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
