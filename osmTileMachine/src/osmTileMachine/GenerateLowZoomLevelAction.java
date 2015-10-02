package osmTileMachine;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
//import java.io.FileOutputStream;
//import java.net.URL;
//import java.nio.channels.Channels;
//import java.nio.channels.ReadableByteChannel;

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

			BufferedImage[] imgQ = new BufferedImage[5];

			//Verify existing images for Q1 - Q4 and download alternatives (with different filename) if needed
			for (int q=1;q<=4;q++)
			{
				Tile sourceTile = t.getNextHigherZoomTile(q);
				Boolean error = false;
				{
					String sourceTileFileName = sessionConfiguration.getTileDirectoryName() + File.separator + sourceTile.getFileName();
					File sourceFile = new File(sourceTileFileName);
					try	
					{
						imgQ[q] = ImageIO.read(sourceFile);
					}
					catch (Exception e)
					{
						error = true;
					}
				}

//				//If local file read didn't succeed, try the cached webalternative
//				if (error)
//				{
//					String sourceTileFileName = sessionConfiguration.getTileDirectoryName() + File.separator + sourceTile.getFileName() + ".webcache";
//					File sourceFile = new File(sourceTileFileName);
//					error = false;
//					try	
//					{
//						imgQ[q] = ImageIO.read(sourceFile);
//					}
//					catch (Exception e)
//					{
//						error = true;
//					}	
//				}
//
//				//If cached webalternative didn't succeed, it needs to be downloaded and coloradjusted
//				if (error)
//				{
//					//Download image from the internet to a file
//					String sourceTileFileName = sessionConfiguration.getTileDirectoryName() + File.separator + sourceTile.getFileName() + ".webcache";
//					File sourceFile = new File(sourceTileFileName);
//					sourceFile.getAbsoluteFile().getParentFile().mkdirs();
//					sourceFile.createNewFile();	
//					URL webImage = new URL(OpenStreetMapProject.getOpenStreetmapTileURL(sourceTile));
//					MessagePrinter.notify(sessionConfiguration, "Downloading " + webImage.toString());
//					ReadableByteChannel rbc = Channels.newChannel(webImage.openStream());
//					FileOutputStream fos = new FileOutputStream(sourceTileFileName, false);
//					fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
//					fos.flush();
//					fos.close();
//
//					// Color adjust the file "theFile"  goes here (optional)
//
//					// Read file back from filesystem
//					error = false;
//					try	
//					{
//						imgQ[q] = ImageIO.read(sourceFile);
//					}
//					catch (Exception e)
//					{
//						error = true;
//					}	
//				}
				
				// so success so far, deliver a transparent image...
				if (error)
				{
					imgQ[q] = new BufferedImage(512, 512, BufferedImage.TYPE_4BYTE_ABGR);
					Graphics g = imgQ[q].getGraphics();
					g.setColor(new Color(0f,1f,0f,0f)); //Green color and fully Transparent

					g.fillRect(0, 0, 512, 512);
//					g.fillRect(0, 0, 112, 512);
					error=false;
				}
			}


			/////////////////////////////////////////////
			// legacy code below, please clean up
			/////////////////////////////////////////////
//			Tile source1Tile = t.getNextHigherZoomTile(1);
//			Tile source2Tile = t.getNextHigherZoomTile(2);
//			Tile source3Tile = t.getNextHigherZoomTile(3);
//			Tile source4Tile = t.getNextHigherZoomTile(4);
//
//			String backupSource1URL = OpenStreetMapProject.getOpenStreetmapTileURL(source1Tile);
//			String backupSource2URL = OpenStreetMapProject.getOpenStreetmapTileURL(source2Tile);
//			String backupSource3URL = OpenStreetMapProject.getOpenStreetmapTileURL(source3Tile);
//			String backupSource4URL = OpenStreetMapProject.getOpenStreetmapTileURL(source4Tile);
//
//			String source1TileFileName = sessionConfiguration.getTileDirectoryName() + File.separator + source1Tile.getFileName();
//			String source2TileFileName = sessionConfiguration.getTileDirectoryName() + File.separator + source2Tile.getFileName();
//			String source3TileFileName = sessionConfiguration.getTileDirectoryName() + File.separator + source3Tile.getFileName();
//			String source4TileFileName = sessionConfiguration.getTileDirectoryName() + File.separator + source4Tile.getFileName();
//
//			String BackupSource1TileFileName = sessionConfiguration.getTileDirectoryName() + File.separator + source1Tile.getFileName() + ".backupsource";
//			String BackupSource2TileFileName = sessionConfiguration.getTileDirectoryName() + File.separator + source2Tile.getFileName() + ".backupsource";
//			String BackupSource3TileFileName = sessionConfiguration.getTileDirectoryName() + File.separator + source3Tile.getFileName() + ".backupsource";
//			String BackupSource4TileFileName = sessionConfiguration.getTileDirectoryName() + File.separator + source4Tile.getFileName() + ".backupsource";
//
//			File source1File = new File(source1TileFileName);
//			File source2File = new File(source2TileFileName);
//			File source3File = new File(source3TileFileName);
//			File source4File = new File(source4TileFileName);
//
//			File backupSource1File = new File(BackupSource1TileFileName);
//			File backupSource2File = new File(BackupSource2TileFileName);
//			File backupSource3File = new File(BackupSource3TileFileName);
//			File backupSource4File = new File(BackupSource4TileFileName);
//
//			MessagePrinter.debug(sessionConfiguration, "Tile: " + t.toString()); 
//			MessagePrinter.debug(sessionConfiguration, "in1: " + source1File.getCanonicalFile().toString());
//			MessagePrinter.debug(sessionConfiguration, "in2: " + source2File.getCanonicalFile().toString());
//			MessagePrinter.debug(sessionConfiguration, "in3: " + source3File.getCanonicalFile().toString());
//			MessagePrinter.debug(sessionConfiguration, "in4: " + source4File.getCanonicalFile().toString());
//			MessagePrinter.debug(sessionConfiguration, "out: " + targetFile.getCanonicalFile().toString());
//
//
//			BufferedImage imgQ1 = null;
//			BufferedImage imgQ2 = null;
//			BufferedImage imgQ3 = null;
//			BufferedImage imgQ4 = null;
//			int tries;
//			boolean success;
//
//			tries = 0;
//			success = false;
//			while ((tries < 3) && success == false)
//			{
//				try 
//				{
//					tries = tries + 1;
//					if (tries == 1) imgQ1 = ImageIO.read(source1File);
//					if (tries >1) imgQ1 = ImageIO.read(backupSource1File);
//					success = true;
//				} 
//				catch (Exception e) 
//				{
//					//Create urls first
//					File theFile = new File(source1TileFileName);
//					theFile.getAbsoluteFile().getParentFile().mkdirs();
//					theFile.createNewFile();
//
//
//					URL webImage = new URL(backupSource1URL);
//					ReadableByteChannel rbc = Channels.newChannel(webImage.openStream());
//					FileOutputStream fos = new FileOutputStream(source1TileFileName, false);
//					fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
//					fos.flush();
//					fos.close();
//				}
//			}
//
//
//			tries = 0;
//			success = false;
//			while ((tries < 3) && success == false)
//			{
//				try 
//				{
//					tries = tries + 1;
//					imgQ2 = ImageIO.read(source2File);
//					success = true;
//				} 
//				catch (Exception e) 
//				{
//					File theFile = new File(source2TileFileName);
//					theFile.getAbsoluteFile().getParentFile().mkdirs();
//					theFile.createNewFile();
//
//					URL webImage = new URL(backupSource2URL);
//					ReadableByteChannel rbc = Channels.newChannel(webImage.openStream());
//					FileOutputStream fos = new FileOutputStream(source2TileFileName, false);
//					fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
//					fos.flush();
//					fos.close();
//				}
//			}
//
//
//			tries = 0;
//			success = false;
//			while ((tries < 3) && success == false)
//			{
//				try 
//				{
//					tries = tries + 1;
//					imgQ3 = ImageIO.read(source3File);
//					success = true;
//				} 
//				catch (Exception e) 
//				{
//					File theFile = new File(source3TileFileName);
//					theFile.getAbsoluteFile().getParentFile().mkdirs();
//					theFile.createNewFile();
//
//					URL webImage = new URL(backupSource3URL);
//					ReadableByteChannel rbc = Channels.newChannel(webImage.openStream());
//					FileOutputStream fos = new FileOutputStream(source3TileFileName, false);
//					fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
//					fos.flush();
//					fos.close();
//				}
//			}
//
//
//			tries = 0;
//			success = false;
//			while ((tries < 3) && success == false)
//			{
//				try 
//				{
//					tries = tries + 1;
//					imgQ4 = ImageIO.read(source4File);
//					success = true;
//				} 
//				catch (Exception e) 
//				{
//					File theFile = new File(source4TileFileName);
//					theFile.getAbsoluteFile().getParentFile().mkdirs();
//					theFile.createNewFile();
//
//					URL webImage = new URL(backupSource4URL);
//					ReadableByteChannel rbc = Channels.newChannel(webImage.openStream());
//					FileOutputStream fos = new FileOutputStream(source4TileFileName, false);
//					fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
//					fos.flush();
//					fos.close();
//				}
//			}
//
//
			// Stitch the four quadrants together...

			BufferedImage imgStiched = new BufferedImage(512, 512, BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D g;
			g = imgStiched.createGraphics();

			//			G.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, bgcolor, observer)

			g.setColor(new Color(1f,0f,0f,0f)); //Red color and fully Transparent
			g.fillRect(0, 0, 512, 512);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
			g.drawImage(imgQ[1], 0, 255, null);
			g.drawImage(imgQ[2], 0, 0, null);
			g.drawImage(imgQ[3], 255, 255, null);
			g.drawImage(imgQ[4], 255, 0, null);

			
//			g.drawImage(imgQ[1], 0, 255, 256, 512, 0, 0, 255, 255, new Color(0f,0f,1f,0.1f), null);
//			g.drawImage(imgQ[2], 0, 0, 256, 256, 0, 0, 255, 255, new Color(0f,0f,1f,0.1f),null);
//			g.drawImage(imgQ[3], 255, 255, 512, 512, 0, 0, 255, 255, new Color(0f,0f,1f,0.1f),null);
//			g.drawImage(imgQ[4], 255, 0, 512, 256, 0, 0, 255, 255, new Color(0f,0f,1f,0.1f),null);

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
