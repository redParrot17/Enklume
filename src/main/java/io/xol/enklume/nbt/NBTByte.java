package io.xol.enklume.nbt;

import java.io.DataInputStream;
import java.io.IOException;

public class NBTByte extends NBTNamed {
	public byte data;
	
	@Override
	void feed(DataInputStream is) throws IOException {
		super.feed(is);
		data = is.readByte();
	}

	@Override
	public String stringifyTag(int tabCount) {
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<tabCount;i++) sb.append("\t");
		sb.append("TAG_Byte(").append(list?"None":"'"+getName()+"'").append("): ").append(data).append("\n");
		return sb.toString();
	}
}
