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
		

		return tileSet;
	}
}
