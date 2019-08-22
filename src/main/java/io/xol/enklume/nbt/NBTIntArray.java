package io.xol.enklume.nbt;

import java.io.IOException;
import java.io.DataInputStream;

public class NBTIntArray extends NBTNamed {

	public int[] data;
	
	@Override
	void feed(DataInputStream is) throws IOException {
		super.feed(is);
		data = new int[is.readInt()];
		for(int i = 0; i < data.length; i++) {
			data[i] = is.readInt();
		}
	}

	@Override
	public String stringifyTag(int tabCount) {
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<tabCount;i++) sb.append("\t");
		sb.append("TAG_Int_Array(").append(list?"None":"'"+getName()+"'").append("): [").append(data.length).append(data.length==1?" int]\n":" ints]\n");
		return sb.toString();
	}
}
