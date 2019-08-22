package io.xol.enklume.nbt;

import java.io.DataInputStream;
import java.io.IOException;

public class NBTByteArray extends NBTNamed {
	
	public byte[] data;
	
	@Override
	void feed(DataInputStream is) throws IOException {
		super.feed(is);
		data = new byte[is.readInt()];
		is.readFully(data);
	}

	@Override
	public String stringifyTag(int tabCount) {
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<tabCount;i++) sb.append("\t");
		sb.append("TAG_Byte_Array(").append(list?"None":"'"+getName()+"'").append("): [").append(data.length).append(data.length==1?" byte]\n":" bytes]\n");
		return sb.toString();
	}
}
