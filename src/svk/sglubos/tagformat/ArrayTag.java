package svk.sglubos.tagformat;

import svk.sglubos.sgserialization.PrimiSerializer;
import svk.sglubos.sgserialization.StructedSerializer;

public abstract class ArrayTag extends Tag {
	protected final byte dataTag;
	
	public ArrayTag(byte dataTag, String id, PrimiSerializer primiSerializer, StructedSerializer structedSerializer) {
		super(Tags.ARRAY, id, primiSerializer, structedSerializer);
		this.dataTag = dataTag;
	}

	public ArrayTag(byte dataTag, String id) {
		super(Tags.ARRAY, id);
		this.dataTag = dataTag;
	}
	
	public byte getDataTag() {
		return dataTag;
	}
}
