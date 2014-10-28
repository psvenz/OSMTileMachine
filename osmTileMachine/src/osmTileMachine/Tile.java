package osmTileMachine;

import java.io.File;

public class Tile {
	private int x;
	private int y;
	private int z;
	String description;


	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getZ() {
		return z;
	}
	public Tile (int param_x, int param_y, int param_z, String param_desc)
	{
		x = param_x;
		y = param_y;
		z = param_z;
		description = param_desc;
	}
	public boolean equals(Object T2){
		if (x != ((Tile) T2).getX()) return false;
		if (y != ((Tile) T2).getY()) return false;
		if (z != ((Tile) T2).getZ()) return false;
		return true;
	}

	public int hashCode(){
		return (("X" + x + "Y" + y + "Z" + z).hashCode());
	}

	public String toString()
	{
		return ("z" + z + "x" + x + "y" + y);
	}

	public BoundingBox getBoundingBox(){
		return new BoundingBox(getLonMin(), getLatMin(), getLonMax(), getLatMax());
	}

	public BoundingBox getBoundingBoxWithMargin(double margin){
		return new BoundingBox(getLonMin()-margin , getLatMin()-margin, getLonMax()+margin, getLatMax()+margin);
	}

	public Tile getLowerZoomLevelTile(int requestedZ){
		double middle_lon = getLonCenter();
		double middle_lat = getLatCenter();	


		int newX = (int)Math.floor( (middle_lon + 180) / 360 * (1<<requestedZ) ) ;
		int newY = (int)Math.floor( (1 - Math.log(Math.tan(Math.toRadians(middle_lat)) + 1 / Math.cos(Math.toRadians(middle_lat))) / Math.PI) / 2 * (1<<requestedZ) ) ;

		return new Tile(newX, newY , requestedZ, description);
	}

	public static Tile getTile(double middle_lon,  double middle_lat, int requestedZ)
	{


		int newX = (int)Math.floor( (middle_lon + 180) / 360 * (1<<requestedZ) ) ;
		int newY = (int)Math.floor( (1 - Math.log(Math.tan(Math.toRadians(middle_lat)) + 1 / Math.cos(Math.toRadians(middle_lat))) / Math.PI) / 2 * (1<<requestedZ) ) ;

		return new Tile(newX, newY , requestedZ, "");
	}


	public double getLatMax(){
		return tile2lat(y,z);
	}
	public double getLatMin(){
		return tile2lat(y+1,z);
	}
	public double getLatCenter(){
		return (tile2lat(y,z) + tile2lat(y+1,z)) / 2;
	}
	public double getLonMax(){
		return tile2lon(x+1,z);
	}
	public double getLonMin(){
		return tile2lon(x,z);
	}
	public double getLonCenter(){
		return (tile2lon(x,z)+tile2lon(x+1,z))/2;
	}

	private double getLonWidth(){
		return getLonMax() - getLonMin();
	}

	private double getLatWidth(){
		return getLatMax() - getLatMin();
	}

	private static double tile2lon(int x, int z) {
		return x / Math.pow(2.0, z) * 360.0 - 180;
	}

	private static double tile2lat(int y, int z) {
		double n = Math.PI - (2.0 * Math.PI * y) / Math.pow(2.0, z);
		return Math.toDegrees(Math.atan(Math.sinh(n)));
	}

	public String getFileName() {
		// TODO Auto-generated method stub
		return z + File.separator + x + File.separator + y + ".png";
	}

	public Tile getNextHigherZoomTile(int i) {
		// TODO Auto-generated method stub
		if (i==1)
		{
			// quadrant 1			
			return getTile(this.getLonMin() + this.getLonWidth()*0.25, this.getLatMin() + this.getLatWidth()*0.25, this.z+1);
		} else if (i==2){
			// quadrant 2			
			return getTile(this.getLonMin() + this.getLonWidth()*0.25, this.getLatMin() + this.getLatWidth()*0.75, this.z+1);
		} else if (i==3){
			// quadrant 3			
			return getTile(this.getLonMin() + this.getLonWidth()*0.75, this.getLatMin() + this.getLatWidth()*0.25, this.z+1);
		} else {
			// quadrant 4			
			return getTile(this.getLonMin() + this.getLonWidth()*0.75, this.getLatMin() + this.getLatWidth()*0.75, this.z+1);
		}
	}

	public TileSet getAllHigherZoomTiles(int requestedZ) throws Exception {
		int z = this.z;

		TileSet tileSet = new TileSet();
		tileSet.add(this);

		while (z < requestedZ)
		{
			TileSet intermediateTileSet = new TileSet();
			tileSet.tileSetIteratorStart();

			while (tileSet.tileSetIteratorHasNext()){	
				Tile tile;
				tile = tileSet.tileSetIteratorGetTile();
				
				intermediateTileSet.add(tile.getNextHigherZoomTile(1));
				intermediateTileSet.add(tile.getNextHigherZoomTile(2));
				intermediateTileSet.add(tile.getNextHigherZoomTile(3));
				intermediateTileSet.add(tile.getNextHigherZoomTile(4));
			}
			z++;
			tileSet = intermediateTileSet;
		}
		return tileSet;
	}
}

