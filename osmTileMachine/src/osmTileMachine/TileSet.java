package osmTileMachine;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;



public class TileSet {
	private Set<Tile> theTileSet;
	public TileSet()
	{
		theTileSet = new HashSet<Tile>();	
		theTileSetIteratorInitialized = false;
	}
	public void add(Tile t)
	{
		theTileSet.add(t);
		theTileSetIteratorInitialized = false;
		theTileSetIterator = null;
	}
	
	private Iterator<Tile> theTileSetIterator;
	private Boolean theTileSetIteratorInitialized;
	
	public void tileSetIteratorStart()
	{
		theTileSetIterator = theTileSet.iterator();
		theTileSetIteratorInitialized = true;
	}
	
	public Tile tileSetIteratorGetTile() throws Exception
	{
		if (theTileSetIterator.hasNext() )
		{
			if (theTileSetIteratorInitialized == true)
			{
				return theTileSetIterator.next();
			}
			else
			{
				throw new Exception("tileSetIteratorGetTile() failed, tileSetIterator not started");
			}
		}
		else
		{
			throw new Exception("tileSetIteratorGetTile() failed, no objects to deliver");
		}
	}
	public void addSet(TileSet s)
	{
		s.tileSetIteratorStart();
		while (s.tileSetIteratorHasNext())
		{
			try {
				this.add(s.tileSetIteratorGetTile());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public TileSet getAllParentTiles(int z) throws Exception
	{
		TileSet newTileSet = new TileSet();
		this.tileSetIteratorStart();
		while (this.tileSetIteratorHasNext())
		{
			newTileSet.add(this.tileSetIteratorGetTile().getLowerZoomLevelTile(z));
		}
		
		return newTileSet;
	}

	public Boolean tileSetIteratorHasNext()
	{

		return theTileSetIterator.hasNext();
	}

	public String getSetInHumanReadableFormat() throws Exception{

		
		
		
		
		String s = "Tileset:";
		s = s + System.getProperty("line.separator");
		this.tileSetIteratorStart();
		int i = 0;
		
		while (this.tileSetIteratorHasNext())
		{
			i++;
			s = s + "tile #" + i + ": ";
			s = s + this.tileSetIteratorGetTile().toString();
			s = s + System.getProperty("line.separator");
		}
		return s;
		
		
		
		
	}
}
