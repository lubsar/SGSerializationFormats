/* MIT License
 *
 * Copyright (c) 2017 Ľubomír Hlavko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
