package svk.sglubos.tagformat.primitives;

import svk.sglubos.sgserialization.PrimiSerializer;
import svk.sglubos.sgserialization.StructedSerializer;
import svk.sglubos.tagformat.Tag;
import svk.sglubos.tagformat.Tags;

public class ByteTag extends Tag {
	public byte data;
	private static final int DATA_SIZE = 1;
	
	public ByteTag(byte data) {
		super(Tags.BYTE, null);
		this.data = data;
	}
	
	public ByteTag(String id, byte data) {
		super(Tags.BYTE, id);
		this.data = data;
	}
	
	public ByteTag(String id, byte data, PrimiSerializer primiSerializer, StructedSerializer structedSerializer) {
		super(Tags.BYTE, id, primiSerializer, structedSerializer);
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
	public ByteTag deserialize(int index, byte[] source) {
		assert index >= 0 : "Index cannot be less than 0";
		assert tag == source[index] : "Incorect datatype tag";
		assert index + 4 + DATA_SIZE <= source.length : "Source does not contain enough data";
		
		index = index + 1;
		index = deserializeId(index, source);
		
		data = primiSerializer.readByte(index, source);
		
		return this;
	}

	@Override
	public int deserialize2(int index, byte[] source) {
		assert index >= 0 : "Index cannot be less than 0";
		assert tag == source[index] : "Incorect datatype tag";
		assert index + 4 + DATA_SIZE <= source.length : "Source does not contain enough data";
		
		index = index + 1;
		index = deserializeId(index, source);
		
		data = primiSerializer.readByte(index, source);
		
		return index + DATA_SIZE;
	}

	@Override
	public int getSize() {
		return 1 + getIdLength() + DATA_SIZE;
	}
}
