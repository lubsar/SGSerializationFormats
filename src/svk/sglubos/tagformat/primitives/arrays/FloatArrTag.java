package svk.sglubos.tagformat.primitives.arrays;

import svk.sglubos.sgserialization.PrimiSerializer;
import svk.sglubos.sgserialization.StructedSerializer;
import svk.sglubos.tagformat.ArrayTag;
import svk.sglubos.tagformat.Tags;

public class FloatArrTag extends ArrayTag {
	private static final int DATA_TYPE_SIZE = 4;
	
	public float[] data;
	
	public FloatArrTag(float[] data) {
		super(Tags.FLOAT, null);
		this.data = data;
	}
	
	public FloatArrTag(String id, float[] data) {
		super(Tags.FLOAT, id);
		this.data = data;
	}
	
	public FloatArrTag(String id, float[] data, PrimiSerializer primiSerializer, StructedSerializer structedSerializer) {
		super(Tags.FLOAT, id, primiSerializer, structedSerializer);
		this.data = data;
	}

	@Override
	public int serialize(int index, byte[] destination) {
		assert index >= 0 : "Index cannot be less than 0";
		assert destination != null: "Desitantion cannot be null";
		assert index + Tags.TAG_SIZE >= destination.length: "Destination does not have enough capacity";
		
		destination[index++] = tag;
		index = structedSerializer.write(getID(), idCharset, index, destination);
		index = primiSerializer.write(data, index, destination);
		
		return index;
	}

	@SuppressWarnings("unchecked")
	@Override
	public FloatArrTag deserialize(int index, byte[] source) {
		assert index >= 0 : "Index cannot be less than 0";
		assert tag == source[index] : "Incorect datatype tag";
		assert index + 4 <= source.length : "Source does not contain enough data";
		
		index = index + 1;
		index = deserializeId(index, source);
		
		assert index + 4 <= source.length : "Source does not contain enough data";
		
		int length = primiSerializer.readInt(index, source);
		index += 4;
		
		assert index + length * DATA_TYPE_SIZE <= source.length : "Source does not contain enough data";
		
		if(data == null || data.length != length) {
			data = new float[length];
		}
		
		primiSerializer.read(data, index, source);
		
		return this;
	}

	@Override
	public int deserialize2(int index, byte[] source) {
		assert index >= 0 : "Index cannot be less than 0";
		assert tag == source[index] : "Incorect datatype tag";
		assert index + 4 <= source.length : "Source does not contain enough data";
		
		index = index + 1;
		index = deserializeId(index, source);
		
		assert index + 4 <= source.length : "Source does not contain enough data";
		
		int length = primiSerializer.readInt(index, source);
		index += 4;
		
		assert index + length * DATA_TYPE_SIZE <= source.length : "Source does not contain enough data";
		
		if(data == null || data.length != length) {
			data = new float[length];
		}
		
		index = primiSerializer.read(data, index, source);
		
		return index;
	}

	@Override
	public int getSize() {
		return 1 + 4 + getIdLength() + DATA_TYPE_SIZE * data.length ;
	}
}
