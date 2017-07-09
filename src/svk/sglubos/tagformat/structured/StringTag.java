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

package svk.sglubos.tagformat.structured;

import java.nio.charset.Charset;

import svk.sglubos.sgserialization.PrimiSerializer;
import svk.sglubos.sgserialization.StructedSerializer;
import svk.sglubos.tagformat.Tag;
import svk.sglubos.tagformat.Tags;

public class StringTag extends Tag {
	public String data;
	public Charset charset = Charset.defaultCharset();
	
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
		assert index + 1 >= destination.length: "Destination does not have enough capacity";
		
		destination[index++] = tag;
		index = serializeID(index, destination);
		
		byte[] bytes = data.getBytes(idCharset);
		
		assert index + bytes.length + 4 <= destination.length : "Destination does not have enough capacity";
		
		index = primiSerializer.write(bytes.length, index, destination);
		index = primiSerializer.write(bytes, index, destination);
		return index;
	}

	@SuppressWarnings("unchecked")
	@Override	
	public StringTag deserialize(int index, byte[] source) {
		assert index >= 0 : "Index cannot be less than 0";
		assert tag == source[index] : "Incorect datatype tag";
		assert index + 4 <= source.length : "Source does not contain enough data";
		
		StringTag data = new StringTag(null, null, primiSerializer, structedSerializer);
		data.deserialize2(index, source);
		
		return data;
	}

	@Override
	public int deserialize2(int index, byte[] source) {
		assert index >= 0 : "Index cannot be less than 0";
		assert tag == source[index] : "Incorect datatype tag";
		assert index + 4 <= source.length : "Source does not contain enough data";
		
		index = index + 1;
		index = deserializeId(index, source);
		
		int stringLength = primiSerializer.readInt(index, source);
		index += 4;
		
		assert index >= source.length : "Source does not contain enough data";
		data = structedSerializer.readString(stringLength, index, source);
		
		index += stringLength;
		
		return index;
	}

	@Override
	public int getSize() {
		return 1 + getIdSize() + 4 + data.getBytes(charset).length;
	}
}