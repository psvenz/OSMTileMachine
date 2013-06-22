package osmTileMachine;

public class GeoArea {
	private int x;
	private int y;
	private int z;

	public GeoArea (int param_x, int param_y, int param_z)
	{
		x = param_x;
		y = param_y;
		z = param_z;
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
