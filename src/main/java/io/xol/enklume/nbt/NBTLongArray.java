package io.xol.enklume.nbt;

import java.io.IOException;
import java.io.DataInputStream;

public class NBTLongArray extends NBTNamed {

    public long[] data;

    @Override
    void feed(DataInputStream is) throws IOException {
        super.feed(is);
        data = new long[is.readInt()];
        for(int i = 0; i < data.length; i++) {
            data[i] = is.readLong();
        }
    }

    @Override
    public String stringifyTag(int tabCount) {
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<tabCount;i++) sb.append("\t");
        sb.append("TAG_Long_Array(").append(list?"None":"'"+getName()+"'").append("): [").append(data.length).append(data.length==1?" long]\n":" longs]\n");
        return sb.toString();
    }
}
