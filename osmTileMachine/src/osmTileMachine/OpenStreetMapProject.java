package osmTileMachine;

import java.util.ArrayList;

public final class OpenStreetMapProject {

	public static ArrayList<String> getPlanetMirrors() {

		ArrayList<String> addressList = new ArrayList<String>();

//		addressList.add("ftp://ftp.spline.de/pub/openstreetmap/pbf/planet-latest.osm.pbf");
//		addressList.add("http://ftp.osuosl.org/pub/openstreetmap/pbf/planet-latest.osm.pbf");
//		addressList.add("http://ftp.snt.utwente.nl/pub/misc/openstreetmap/planet-latest.osm.pbf");
//		addressList.add("http://ftp.heanet.ie/mirrors/openstreetmap.org/pbf/planet-latest.osm.pbf");
//		addressList.add("http://planet.openstreetmap.org/pbf/planet-latest.osm.pbf");
//		
		addressList.add("http://download.geofabrik.de/europe-latest.osm.pbf");
		
		
//		addressList.add("ftp://ftp.sunet.se/ls-lR.gz");
		
//		addressList.add("http://download.geofabrik.de/europe/sweden-latest.osm.pbf");
//		addressList.add("http://download.geofabrik.de/europe/portugal-latest.osm.pbf");
		return addressList;
			
		
		
			
//		return null;
		// TODO Auto-generated method stub
		
	}
	
	public static long ApproximatePlanetSize(){
		return 10*1000000; //10 MB is an OK planet.pbf file size. Smaller is invalid!
	}

	public static long timeUntilNextPlanetRelease() {
		// TODO Auto-generated method stub
		return 1000*60*60*24*8; //Next planet is available in one week + one spare day
	}

	public static boolean avoidServer(String url)
	{
		return url.contains("http://planet.openstreetmap.org");
	}
}
