package io.xol.enklume.nbt;

import java.io.IOException;
import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.List;

public class NBTList extends NBTNamed {
	int type;
	int number;
	
	public List<NBTNamed> elements = new ArrayList<>();
	
	@Override
	void feed(DataInputStream is) throws IOException {
		super.feed(is);
		type = is.read();
		number = is.readInt();
		if(type > 0) {
			for(int i = 0; i < number; i++) {
				NBTag tag = NBTag.createNamedFromList(type, i);
				tag.feed(is);
				elements.add((NBTNamed) tag);
			}
		} else if (number > 0) {
			System.out.println("Warning : found an illegal NBTList of TAG_END !");
		}
	}

	@Override
	public String stringifyTag(int tabCount) {
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<tabCount;i++) sb.append("\t");
		sb.append("TAG_List(").append(list?"None":"'"+getName()+"'").append("): ").append(elements.size()).append(elements.size()==1?" entry\n":" entries\n");
		for (int i=0;i<tabCount;i++) sb.append("\t");
		sb.append("{\n");
		elements.forEach(e -> sb.append(e.stringifyTag(tabCount+1)));
		for (int i=0;i<tabCount;i++) sb.append("\t");
		sb.append("}\n");
		return sb.toString();
	}
	
	//@Override
	public NBTNamed getTag(String path) {
		if(path.startsWith(".")) path = path.substring(1);
		if(path.equals("")) return this;
		
		String[] s = path.split("\\.");
		String looking = s[0];
		
		int index = Integer.parseInt(looking);
		
		//If this is within the list
		if(index < elements.size()) {
			NBTNamed found = elements.get(index);
			
			//There is still hierarchy to traverse
			if(s.length > 1) {
				String deeper = path.substring(looking.length() + 1);
				
				if(found instanceof NBTCompound)
					return ((NBTCompound) found).getTag(deeper);
				if(found instanceof NBTList)
					return ((NBTList) found).getTag(deeper);
				else
					System.out.println("error: Can't traverse tag "+found+"; not a Compound, nor a List tag.");
			}
			//There isn't
			else return found;
		}
		
		return null;
	}
}
