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

	public Boolean tileSetIteratorHasNext()
	{

		return theTileSetIterator.hasNext();
	}

}
