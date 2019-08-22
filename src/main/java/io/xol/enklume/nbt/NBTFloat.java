package io.xol.enklume.nbt;

import java.io.IOException;
import java.io.DataInputStream;
import java.nio.ByteBuffer;

public class NBTFloat extends NBTNamed {
	public float data = 0;
	
	@Override
	void feed(DataInputStream is) throws IOException {
		super.feed(is);
		data = is.readFloat();
	}

	@Override
    public String stringifyTag(int tabCount) {
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<tabCount;i++) sb.append("\t");
		sb.append("TAG_Float(").append(list?"None":"'"+getName()+"'").append("): ").append(data).append("\n");
		return sb.toString();
	}
}
