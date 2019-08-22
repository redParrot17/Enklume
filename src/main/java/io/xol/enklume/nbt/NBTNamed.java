package io.xol.enklume.nbt;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public abstract class NBTNamed extends NBTag {

	private String tagName;
	boolean list = false;
	
	@Override
	void feed(DataInputStream is) throws IOException {
		if(!list) {
			int nameSize = is.readUnsignedShort();
			byte[] n = new byte[nameSize];
			try{
				is.readFully(n);
				tagName = new String(n, StandardCharsets.UTF_8);
			} catch(Exception e) { e.printStackTrace(); }
		}
	}
	
	public String getName()
	{
		return tagName;
	}

	public void setNamedFromListIndex(int i) {
		tagName = i+"";
		list = true;
	}

}
