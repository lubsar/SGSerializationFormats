package svk.sglubos.tagformat.structured;

import svk.sglubos.sgserialization.PrimiSerializer;
import svk.sglubos.sgserialization.StructedSerializer;
import svk.sglubos.tagformat.Tag;
import svk.sglubos.tagformat.Tags;

public class StringTag extends Tag {
	public String data;
		
	public StringTag(String data) {
		super(Tags.STRING, null);
	}
	
	public StringTag(String id, String data) {
		super(Tags.STRING, id);
		this.data = data;
	}
	
	public StringTag(String id, String data, PrimiSerializer primiSerializer, StructedSerializer structedSerializer) {
		super(Tags.STRING, id, primiSerializer, structedSerializer);
		this.data = data;
	}

	@Override
	public int serialize(int index, byte[] destination) {
		assert index >= 0 : "Index cannot be less than 0";
		assert destination != null: "Desitantion cannot be null";
		assert index + Tags.TAG_SIZE >= destination.length: "Destination does not have enough capacity";
		
		destination[index++] = tag;
		index = structedSerializer.write(getID(), idCharset, index, destination);
		index = structedSerializer.write(data, index, destination);
		return index;
	}

	@SuppressWarnings("unchecked")
	@Override	
	public StringTag deserialize(int index, byte[] source) {
		return null;
	}

	@Override
	public int deserialize2(int index, byte[] source) {
		return 0;
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
