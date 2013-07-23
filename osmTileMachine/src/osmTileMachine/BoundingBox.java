package osmTileMachine;

public class BoundingBox {
	private double minLat;
	private double maxLat;
	private double minLon;
	private double maxLon;

	public BoundingBox(double argMinLon, double argMinLat,double argMaxLon, double argMaxLat){
		minLat = argMinLat;
		minLon = argMinLon;
		maxLat = argMaxLat;
		maxLon = argMaxLon;
	}
	public void Expand(double LatExpand, double LonExpand){
		minLat = minLat - LatExpand;
		maxLat = maxLat + LatExpand;
		minLon = minLon - LonExpand;
		maxLon = maxLon + LonExpand;
	}
	public String toString(){
		return ("LAT: " + minLat + " TO " + maxLat + "; LON: " + minLon + " TO " + maxLon + "   osmconvert -b=" + minLon + "," + minLat + "," + maxLon + "," + maxLat);
	}
	public double getMinLon() {
		return minLon;
	}
	public double getMaxLon() {
		return maxLon;
	}
	public double getMinLat() {
		return minLat;
	}
	public double getMaxLat() {
		return maxLat;
	}
}
