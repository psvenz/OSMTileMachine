package osmTileMachine;

import java.util.ArrayList;

public final class OpenStreetMapProject {

	public static ArrayList<String> getSourceFileMirrors(Configuration sessionConfiguration, String s) {

		ArrayList<String> addressList = new ArrayList<String>();


		if (s.equalsIgnoreCase("downloadsweden"))
		{
			addressList.add("http://download.geofabrik.de/europe/sweden-latest.osm.pbf");			
		}

		else if (s.equalsIgnoreCase("downloadeurope"))
		{
			addressList.add("http://download.geofabrik.de/europe-latest.osm.pbf");
			addressList.add("http://ftp5.gwdg.de/pub/misc/openstreetmap/download.geofabrik.de/europe-latest.osm.pbf");

		}
		else if (s.equalsIgnoreCase("downloadgermany"))
		{

			addressList.add("http://download.geofabrik.de/europe/germany-latest.osm.pbf");
			addressList.add("http://ftp5.gwdg.de/pub/misc/openstreetmap/download.geofabrik.de/germany-latest.osm.pbf");

		}
		else //downloadplanet as well
		{
			addressList.add("ftp://ftp.spline.de/pub/openstreetmap/pbf/planet-latest.osm.pbf");
			addressList.add("http://ftp.osuosl.org/pub/openstreetmap/pbf/planet-latest.osm.pbf");
			addressList.add("http://ftp.snt.utwente.nl/pub/misc/openstreetmap/planet-latest.osm.pbf");
			addressList.add("http://ftp.heanet.ie/mirrors/openstreetmap.org/pbf/planet-latest.osm.pbf");
			addressList.add("http://planet.openstreetmap.org/pbf/planet-latest.osm.pbf");	
		}


		return addressList;


	}

	public static long approximateSourceSize(){
		return 10*1000000; //10 MB is an OK source file size. Smaller is invalid!
	}

	public static long timeUntilNextPlanetRelease() {
		// TODO Auto-generated method stub
		return 1000*60*60*24*8; //Next planet is available in one week + one spare day
	}

	//Known servers to avoid
	// If we should avoid a server, only use it if it is 10 times faster than the other mirrors...
	public static boolean avoidServer(String url)
	{
		return url.contains("http://planet.openstreetmap.org");
	}

	public static String getOpenStreetmapTileURL(Tile source1Tile) {
		return "http://a.tile.openstreetmap.org/" + source1Tile.getZ() +"/" + source1Tile.getX() + "/" + source1Tile.getY() + ".png";
	}
}
