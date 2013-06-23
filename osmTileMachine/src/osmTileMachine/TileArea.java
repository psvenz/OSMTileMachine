package osmTileMachine;

public class TileArea {
	private int x;
	private int y;
	private int z;
	String description;
	public TileArea (int param_x, int param_y, int param_z, String param_desc)
	{
		x = param_x;
		y = param_y;
		z = param_z;
		description = param_desc;
	}
	
	
	
	public double getExtractLatLonMargin(){
		if (z > 9) return (getLatMax() - getLatMin()) * 0.8; // 80% margin 
		else if (z == 9) return (getLatMax() - getLatMin()) * 0.2; // 30% margin 
		else if (z == 8) return (getLatMax() - getLatMin()) * 0.2; // 20% margin 
		else return (getLatMax() - getLatMin()) * 0.1; // 10% margin 		
	}

	public double getLatMax(){
		return tile2lat(y+1,z);
	}
	public double getLatMin(){
		return tile2lat(y,z);
	}
	public double getLonMax(){
		return tile2lon(x+1,z);
	}
	public double getLonMin(){
		return tile2lon(x,z);
	}
	
	 
	
	public static double tile2lon(int x, int z) {
		return x / Math.pow(2.0, z) * 360.0 - 180;
	}

	public static double tile2lat(int y, int z) {
		double n = Math.PI - (2.0 * Math.PI * y) / Math.pow(2.0, z);
		return Math.toDegrees(Math.atan(Math.sinh(n)));
	}
}
