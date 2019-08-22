package io.xol.enklume.nbt;

import java.io.DataInputStream;

class NBTEnd extends NBTag {

	@Override
	void feed(DataInputStream is) {
		//Read nothing
	}

	@Override
	public String stringifyTag(int tabCount) {
		return "";
	}

}
