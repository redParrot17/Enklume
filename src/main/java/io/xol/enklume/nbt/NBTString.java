package io.xol.enklume.nbt;

import java.io.IOException;
import java.io.DataInputStream;
import java.nio.charset.StandardCharsets;

public class NBTString extends NBTNamed {
	public String data;
	
	@Override
	void feed(DataInputStream is) throws IOException {
		super.feed(is);
		byte[] n = new byte[is.readUnsignedShort()];
		is.readFully(n);
		data = new String(n, StandardCharsets.UTF_8);
	}

	@Override
	public String stringifyTag(int tabCount) {
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<tabCount;i++) sb.append("\t");
		sb.append("TAG_String(").append(list?"None":"'"+getName()+"'").append("): '").append(data).append("'\n");
		return sb.toString();
	}

	public String getText() {
		return data == null ? "" : data;
	}
}
