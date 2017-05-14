package svk.sglubos.tagformat;

import java.nio.charset.Charset;

import svk.sglubos.sgserialization.PrimiSerializer;
import svk.sglubos.sgserialization.Serializable;
import svk.sglubos.sgserialization.StructedSerializer;

public abstract class Tag implements Serializable {
	protected final byte tag;
	
	private String id;
	private int idLength = -1;
	protected Charset idCharset = Charset.defaultCharset();
	
	protected PrimiSerializer primiSerializer;
	protected StructedSerializer structedSerializer;
	
	public Tag(byte tag, String id, PrimiSerializer primiSerializer, StructedSerializer structedSerializer) {
		this.tag = tag;
		this.id = id;
		this.primiSerializer = primiSerializer;
		this.structedSerializer = structedSerializer;
	}

	public Tag(byte tag, String id) {
		this(tag, id, Serializers.primiSerializer, Serializers.structedSerializer);
	}
	
	public byte getTag() {
		return tag;
	}
	
	public void setID(String id) {
		this.id = id;
		this.idLength = -1;
	}
	
	public void setIDCharset(Charset charset) {
		this.idCharset = charset;
		this.idLength = -1;
	}
	
	public Charset getIDCharset() {
		return idCharset;
	}
	
	public String getID() {
		return id;
	}
	
	protected int getIdLength() {
		if(idLength == -1) {
			return idLength = id.getBytes(idCharset).length;
		}
		return idLength;
	}
	
	protected int deserializeId(int index, byte[] source) {
		int length = primiSerializer.readInt(index, source);
		index += 4;
		
		assert index + length <= source.length : "Source does not contain enough data";
		id = structedSerializer.readString(length, idCharset, index, source);
		
		return index + length;
	}
}
