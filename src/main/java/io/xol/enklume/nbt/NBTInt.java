package io.xol.enklume.nbt;

import java.io.IOException;
import java.io.DataInputStream;

public class NBTInt extends NBTNamed {
	public int data;
	
	@Override
	void feed(DataInputStream is) throws IOException {
		super.feed(is);
		data = is.readInt();
	}

	@Override
	public String stringifyTag(int tabCount) {
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<tabCount;i++) sb.append("\t");
		sb.append("TAG_Int(").append(list?"None":"'"+getName()+"'").append("): ").append(data).append("\n");
		return sb.toString();
	}

	public int getData()
	{
		return data;
	}
}
