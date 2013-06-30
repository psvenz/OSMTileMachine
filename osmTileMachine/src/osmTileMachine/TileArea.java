package osmTileMachine;

public class TileArea {
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
	public TileArea (int param_x, int param_y, int param_z, String param_desc)
	{
		x = param_x;
		y = param_y;
		z = param_z;
		description = param_desc;
	}
	public boolean equals(TileArea T2){
		if (x != T2.getX()) return false;
		if (y != T2.getY()) return false;
		if (z != T2.getZ()) return false;
		return true;
	}
	
	public int hashCode(){
		return (("X" + x + "Y" + y + "Z" + z).hashCode());
	}
	
	public String toString()
	{
		return ("Tile: Z: " + z + "  X: " + x + "  Y:" + y + "  Description: " + description);
	}
	
	public double getExtractLatLonMargin(){
		if (z > 9) return (getLatMax() - getLatMin()) * 0.8; // 80% margin 
		else if (z == 9) return (getLatMax() - getLatMin()) * 0.2; // 30% margin 
		else if (z == 8) return (getLatMax() - getLatMin()) * 0.2; // 20% margin 
		else return (getLatMax() - getLatMin()) * 0.1; // 10% margin 		
	}

	public BoundingBox getBoundingBox(){
		return new BoundingBox(getLonMin(), getLatMin(), getLonMax(), getLatMax());
	}

	public BoundingBox getBoundingBoxWithMargin(){
		double margin = getExtractLatLonMargin();
		return new BoundingBox(getLonMin()-margin , getLatMin()-margin, getLonMax()+margin, getLatMax()+margin);
	}

	public TileArea getLowerZoomLevelArea(int requestedZ){
		double middle_lon = getLonCenter();
		double middle_lat = getLatCenter();	
		
		
	   int newX = (int)Math.floor( (middle_lon + 180) / 360 * (1<<requestedZ) ) ;
	   int newY = (int)Math.floor( (1 - Math.log(Math.tan(Math.toRadians(middle_lat)) + 1 / Math.cos(Math.toRadians(middle_lat))) / Math.PI) / 2 * (1<<requestedZ) ) ;
		
		return new TileArea(newX, newY , requestedZ, description);
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
	
	 
	
	private static double tile2lon(int x, int z) {
		return x / Math.pow(2.0, z) * 360.0 - 180;
	}

	private static double tile2lat(int y, int z) {
		double n = Math.PI - (2.0 * Math.PI * y) / Math.pow(2.0, z);
		return Math.toDegrees(Math.atan(Math.sinh(n)));
	}
}
