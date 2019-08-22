package io.xol.enklume;

import io.xol.enklume.nbt.*;
import io.xol.enklume.util.BlockProperty;
import io.xol.enklume.util.Coords;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MinecraftChunk {

	private NBTCompound root = null;
	private int[] sectionsMap = new int[16];

	private final int x;
	private final int z;

	private byte[][] blocks = new byte[16][];
	private byte[][] mData = new byte[16][];

	private HashMap<Coords, MinecraftBlock> blockData;

	/**
	 * Create an empty chunk
	 *
	 * @param x X position of the chunk
	 * @param z Z position of the chunk
	 */
	MinecraftChunk(int x, int z) {
		this.x = x;
		this.z = z;
		blockData = null;
	}

	/**
	 * Create a chunk with data
	 *
	 * @param x X position of the chunk
	 * @param z Z position of the chunk
	 * @param byteArray byte array containing the chunk data
	 */
	MinecraftChunk(int x, int z, byte[] byteArray) {
		this(x, z);
		ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
		root = (NBTCompound) NBTag.parseInputStream(bais);
		if (root == null) return;
		blockData = new HashMap<>();
		for (int i = 0; i < 16; i++)
			sectionsMap[i] = -1;
		for (int i = 0; i < 16; i++) {
			NBTCompound section = (NBTCompound) root.getTag("Level.Sections." + i);
			if (section != null) {
				int y = ((NBTByte) section.getTag("Y")).data;
				if (y < 0) continue;
				sectionsMap[y] = i;

				NBTag blocksNBT = root.getTag("Level.Sections." + i + ".Blocks");
				if (blocksNBT != null) blocks[i] = ((NBTByteArray) blocksNBT).data;
				NBTag mDataNBT = root.getTag("Level.Sections." + i + ".Data");
				if (mDataNBT != null) mData[i] = ((NBTByteArray) mDataNBT).data;
				//parseBlockData(i);
			}
		}
		//System.out.println(blockData.values().size() + " blocks retrieved");
	}

	private void parseBlockData(int sec) {
		NBTLongArray blockStates = (NBTLongArray) root.getTag("Level.Sections."+sec+".BlockStates");
		if (blockStates == null) return;
		int bitsPerBlock = Math.max((blockStates.data.length * 64) / 4096, 4);
		StringBuilder sb = new StringBuilder();
		for (int i=blockStates.data.length-1; i>=0; i--) {
			sb.append(String.format("%64s", Long.toBinaryString(blockStates.data[i])).replaceAll(" ", "0"));
		}
		String allbits = sb.toString();
		String[] blocks = new String[4096];
		for (int i=0; i<4096; i++) {
			blocks[i] = allbits.substring(allbits.length()-(bitsPerBlock * (i + 1)), allbits.length()-(bitsPerBlock * i));
		}

		int inc = 0;
		for (int y=0; y<16; y++) {
			for (int z=0; z<16; z++) {
				for (int x=0; x<16; x++) {
					int blockData = Integer.parseInt(blocks[inc], 2);
					String name = ((NBTString)root.getTag("Level.Sections."+sec+".Palette."+blockData+".Name")).getText();
					int xBlock = ((NBTInt)root.getTag("Level.xPos")).getData()*16 + x;
					int yBlock = ((NBTByte)root.getTag("Level.Sections."+sec+".Y")).data*16 + y;
					int zBlock = ((NBTInt)root.getTag("Level.zPos")).getData()*16 + z;
					Coords blockCoords = new Coords(xBlock, yBlock, zBlock);
					NBTCompound props = (NBTCompound)root.getTag("Level.Sections."+sec+".Palette."+blockData+".Properties");
					try {
						ArrayList<BlockProperty> ps = new ArrayList<>();
						props.iterator().forEachRemaining(property -> {
							if (property instanceof NBTByte)
								ps.add(new BlockProperty<>(property.getName(), ((NBTByte) property).data));
							else if (property instanceof NBTByteArray)
								ps.add(new BlockProperty<>(property.getName(), ((NBTByteArray) property).data));
							else if (property instanceof NBTDouble)
								ps.add(new BlockProperty<>(property.getName(), ((NBTDouble) property).data));
							else if (property instanceof NBTFloat)
								ps.add(new BlockProperty<>(property.getName(), ((NBTFloat) property).data));
							else if (property instanceof NBTInt)
								ps.add(new BlockProperty<>(property.getName(), ((NBTInt) property).data));
							else if (property instanceof NBTIntArray)
								ps.add(new BlockProperty<>(property.getName(), ((NBTIntArray) property).data));
							else if (property instanceof NBTLong)
								ps.add(new BlockProperty<>(property.getName(), ((NBTLong) property).data));
							else if (property instanceof NBTLongArray)
								ps.add(new BlockProperty<>(property.getName(), ((NBTLongArray) property).data));
							else if (property instanceof NBTShort)
								ps.add(new BlockProperty<>(property.getName(), ((NBTShort) property).data));
							else if (property instanceof NBTString)
								ps.add(new BlockProperty<>(property.getName(), ((NBTString) property).data));
						});
						this.blockData.put(blockCoords, new MinecraftBlock(blockCoords, name, ps.toArray(new BlockProperty[0])));
					} catch (NullPointerException npe) {
						this.blockData.put(blockCoords, new MinecraftBlock(blockCoords, name));
					}
					inc++;
				}
			}
		}
	}
	
	public NBTCompound getRootTag() {
		return root;
	}

	public int xPos() {
		return x;
	}

	public int zPos() {
		return z;
	}

	public String getFancyString() {
		return root.stringifyTag(0);
	}

	@Deprecated
	public int getBlockID(int x, int y, int z) {
		if (root == null) return 0;

		int i = sectionsMap[y / 16];
		if (y > 0 && y < 256) {
			if (i >= 0) {
				if (blocks[i] != null) {
					y %= 16;
					int index = y * 16 * 16 + z * 16 + x;
					return blocks[i][index] & 0xFF;
				}
			}
		}

		if (i >= 0) {
			NBTag blocksNBT = root.getTag("Level.Sections." + i + ".Blocks");
			if (blocksNBT != null) {
				y = y % 16;
				int index = y * 16 * 16 + z * 16 + x;
				return ((NBTByteArray) blocksNBT).data[index] & 0xFF;
			}
		}

		return 0;
	}

	@Deprecated
	public int getBlockMeta(int x, int y, int z) {
		int i = sectionsMap[y / 16];
		if (y > 0 && y < 256) {
			if (i >= 0) {
				if (mData[i] != null) {
					y %= 16;
					int index = y * 16 * 16 + z * 16 + x;
					byte unfilteredMeta = mData[i][index / 2];
					//4-bit nibbles, classic bullshit !
					return index % 2 != 0 ? (unfilteredMeta >> 4) & 0xF : (unfilteredMeta) & 0xF;
				}
			}
		}
		return 0;
	}
}
