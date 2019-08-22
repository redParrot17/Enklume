package io.xol.enklume;

import java.io.File;
import java.io.IOException;

import io.xol.enklume.nbt.NBTDouble;
import io.xol.enklume.nbt.NBTFile;
import io.xol.enklume.nbt.NBTList;
import io.xol.enklume.nbt.NBTString;
import io.xol.enklume.nbt.NBTFile.CompressionScheme;

public class MinecraftWorld {
	private final File folder;
	private final NBTFile nbtFile;
	
	private final String levelName;
	
	public MinecraftWorld(File folder) throws IOException {
		this.folder = folder;
		
		//Tries to read level.dat
		File levelDat = new File(this.folder.getAbsolutePath()+"/level.dat");
		assert levelDat.exists();
		
		nbtFile = new NBTFile(levelDat, CompressionScheme.GZIPPED);
		
		levelName = ((NBTString)nbtFile.getRoot().getTag("Data.LevelName")).getText();
	}
	
	public String getName()
	{
		return levelName;
	}
	
	public NBTFile getLevelDotDat()
	{
		return nbtFile;
	}
	
	public static int blockToRegionCoordinates(int blockCoordinates) {
		if (blockCoordinates >= 0)
			return (int) Math.floor(blockCoordinates / 512f);
		blockCoordinates = -blockCoordinates;
		return -(int) Math.floor(blockCoordinates / 512f) - 1;
	}
	
	public MinecraftRegion getRegion(int regionCoordinateX, int regionCoordinateZ) {
		File regionFile = new File(folder.getAbsolutePath() + "/region/r." + regionCoordinateX + "." + regionCoordinateZ + ".mca");
		return regionFile.exists() ? new MinecraftRegion(regionFile) : null;
	}

	public MinecraftRegion getPlayerRegion() {
		return getRegion(getPlayerX() >> 9, getPlayerZ() >> 9);
	}

	public MinecraftChunk getPlayerChunk() {
		MinecraftRegion region = getRegion(getPlayerX() >> 9, getPlayerZ() >> 9);
		if (region == null) return null;
		return region.getChunk(getPlayerX() >> 4, getPlayerZ() >> 4);
	}

	public int getPlayerX() {
		NBTDouble posX = (NBTDouble) nbtFile.getRoot().getTag("Data.Player.Pos.0");
		return (int)Math.floor(posX.getData());
	}

	public int getPlayerY() {
		NBTDouble posY = (NBTDouble) nbtFile.getRoot().getTag("Data.Player.Pos.1");
		return (int)Math.floor(posY.getData());
	}

	public int getPlayerZ() {
		NBTDouble posZ = (NBTDouble) nbtFile.getRoot().getTag("Data.Player.Pos.2");
		return (int)Math.floor(posZ.getData());
	}
}
