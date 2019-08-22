package io.xol.enklume.nbt;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class NBTDouble extends NBTNamed {
	public double data = 0;
	
	@Override
	void feed(DataInputStream is) throws IOException {
		super.feed(is);
		data = is.readDouble();
	}

	@Override
	public String stringifyTag(int tabCount) {
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<tabCount;i++) sb.append("\t");
		sb.append("TAG_Double(").append(list?"None":"'"+getName()+"'").append("): ").append(data).append("\n");
		return sb.toString();
	}
	
	public double getData()
	{
		return data;
	}
}
