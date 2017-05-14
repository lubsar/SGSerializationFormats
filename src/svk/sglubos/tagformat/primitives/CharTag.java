package svk.sglubos.tagformat.primitives;

import svk.sglubos.sgserialization.PrimiSerializer;
import svk.sglubos.sgserialization.StructedSerializer;
import svk.sglubos.tagformat.Tag;
import svk.sglubos.tagformat.Tags;

public class CharTag extends Tag {
	public char data;
	private static final int DATA_SIZE = 2;
	
	public CharTag(char data) {
		super(Tags.CHAR, null);
		this.data = data;
	}
	
	public CharTag(String id, char data) {
		super(Tags.CHAR, id);
		this.data = data;
	}
	
	public CharTag(String id, char data, PrimiSerializer primiSerializer, StructedSerializer structedSerializer) {
		super(Tags.CHAR, id, primiSerializer, structedSerializer);
		this.data = data;
	}

	@Override
	public int serialize(int index, byte[] destination) {
		destination[index++] = tag;
		index = structedSerializer.write(getID(), idCharset, index, destination);
		index = primiSerializer.write(data, index, destination);
		
		return index;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CharTag deserialize(int index, byte[] source) {
		assert index >= 0 : "Index cannot be less than 0";
		assert tag == source[index] : "Incorect datatype tag";
		assert index + 4 + DATA_SIZE <= source.length : "Source does not contain enough data";
		
		index = index + 1;
		index = deserializeId(index, source);
		
		data = primiSerializer.readChar(index, source);
		
		return this;
	}

	@Override
	public int deserialize2(int index, byte[] source) {
		assert index >= 0 : "Index cannot be less than 0";
		assert tag == source[index] : "Incorect datatype tag";
		assert index + 4 + DATA_SIZE <= source.length : "Source does not contain enough data";
		
		index = index + 1;
		index = deserializeId(index, source);
		
		data = primiSerializer.readChar(index, source);
		
		return index + DATA_SIZE;
	}

	@Override
	public int getSize() {
		return 1 + getIdLength() + DATA_SIZE;
	}
}
