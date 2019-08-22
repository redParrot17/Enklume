package io.xol.enklume.nbt;

import java.io.DataInputStream;
import java.io.IOException;

public class NBTLong extends NBTNamed {
	public long data = 0L;
	
	@Override
	void feed(DataInputStream is) throws IOException {
		super.feed(is);
		data = is.readLong();
	}

	@Override
    public String stringifyTag(int tabCount) {
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<tabCount;i++) sb.append("\t");
		sb.append("TAG_Long(").append(list?"None":"'"+getName()+"'").append("): ").append(data).append("L\n");
		return sb.toString();
	}
}
