package osmTileMachine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GenerateLowZoomLevelAction extends Action{

	private TileSet tS;
	private String name;
	@Override
	void runAction(Configuration sessionConfiguration) throws Exception {
		// TODO Auto-generated method stub
		MessagePrinter.notify(sessionConfiguration, "Generating tiles for lower zoom level: " + name);
		tS.tileSetIteratorStart();
		Tile t;
		while (tS.tileSetIteratorHasNext())
		{
			t = tS.tileSetIteratorGetTile();
			String targetFileName = sessionConfiguration.getTileDirectoryName() + File.separator + t.getFileName(); 			
			File targetFile = new File(targetFileName);
			targetFile.getParentFile().mkdirs();
			
			Tile source1Tile = t.getNextHigherZoomTile(1);
			Tile source2Tile = t.getNextHigherZoomTile(2);
			Tile source3Tile = t.getNextHigherZoomTile(3);
			Tile source4Tile = t.getNextHigherZoomTile(4);

			String source1TileFileName = sessionConfiguration.getTileDirectoryName() + File.separator + source1Tile.getFileName();
			String source2TileFileName = sessionConfiguration.getTileDirectoryName() + File.separator + source2Tile.getFileName();
			String source3TileFileName = sessionConfiguration.getTileDirectoryName() + File.separator + source3Tile.getFileName();
			String source4TileFileName = sessionConfiguration.getTileDirectoryName() + File.separator + source4Tile.getFileName();

			File source1File = new File(source1TileFileName);
			File source2File = new File(source2TileFileName);
			File source3File = new File(source3TileFileName);
			File source4File = new File(source4TileFileName);

			MessagePrinter.debug(sessionConfiguration, "Tile: " + t.toString()); 
			MessagePrinter.debug(sessionConfiguration, "in1: " + source1File.getCanonicalFile().toString());
			MessagePrinter.debug(sessionConfiguration, "in2: " + source2File.getCanonicalFile().toString());
			MessagePrinter.debug(sessionConfiguration, "in3: " + source3File.getCanonicalFile().toString());
			MessagePrinter.debug(sessionConfiguration, "in4: " + source4File.getCanonicalFile().toString());
			MessagePrinter.debug(sessionConfiguration, "out: " + targetFile.getCanonicalFile().toString());

			BufferedImage imgQ1 = null;
			try {
				imgQ1 = ImageIO.read(source1File);
			} catch (IOException e) {
			}

			BufferedImage imgQ2 = null;
			try {
				imgQ2 = ImageIO.read(source2File);
			} catch (IOException e) {
			}

			BufferedImage imgQ3 = null;
			try {
				imgQ3 = ImageIO.read(source3File);
			} catch (IOException e) {
			}

			BufferedImage imgQ4 = null;
			try {
				imgQ4 = ImageIO.read(source4File);
			} catch (IOException e) {
			}

			BufferedImage imgStiched = new BufferedImage(512, 512, BufferedImage.TYPE_4BYTE_ABGR);
			Graphics g = imgStiched.getGraphics();

//			G.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, bgcolor, observer)
			
			g.setColor(Color.black);
			g.fillRect(0, 0, 512, 512);
			
			g.drawImage(imgQ1, 0, 255, 256, 512, 0, 0, 255, 255, Color.black, null);
			g.drawImage(imgQ2, 0, 0, 256, 256, 0, 0, 255, 255, Color.black,null);
			g.drawImage(imgQ3, 255, 255, 512, 512, 0, 0, 255, 255, Color.black,null);
			g.drawImage(imgQ4, 255, 0, 512, 256, 0, 0, 255, 255, Color.black,null);
		
			imgStiched = scaleWithInstance(imgStiched, 256, 256, Image.SCALE_AREA_AVERAGING);
			ImageIO.write(imgStiched, "png", targetFile);
		}
	}
	
	private static BufferedImage scaleWithInstance(BufferedImage source, int desiredWidth, int desiredHeight, int scaleType){
	     Image temp1 = source.getScaledInstance(desiredWidth, desiredHeight, scaleType);
	        BufferedImage image = new BufferedImage(desiredWidth, desiredHeight, BufferedImage.TYPE_INT_ARGB);
	        Graphics2D g = (Graphics2D)image.getGraphics();
	        g.drawImage(temp1, 0, 0, null);
	         
	        return image;
	    } 

	public GenerateLowZoomLevelAction (TileSet tileSet, String n)
	{
		tS = tileSet;
		name = n;
	}


	@Override
	String getActionInHumanReadableFormat() {
		// TODO Auto-generated method stub
		return "Generate low zoom level tiles: "+name;
	}

}
