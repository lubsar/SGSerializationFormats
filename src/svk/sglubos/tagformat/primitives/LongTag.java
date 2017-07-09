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

package svk.sglubos.tagformat.primitives;

import svk.sglubos.sgserialization.PrimiSerializer;
import svk.sglubos.sgserialization.StructedSerializer;
import svk.sglubos.tagformat.Tag;
import svk.sglubos.tagformat.Tags;

public class LongTag extends Tag {
	public long data;
	private static final int DATA_SIZE = 8;
	
	public LongTag(String id, long data) {
		super(Tags.LONG, id);
		this.data = data;
	}
	
	public LongTag(String id, long data, PrimiSerializer primiSerializer, StructedSerializer structedSerializer) {
		super(Tags.LONG, id, primiSerializer, structedSerializer);
		this.data = data;
	}

	@Override
	public int serialize(int index, byte[] destination) {
		destination[index++] = tag;
		index = serializeID(index, destination);
		index = primiSerializer.write(data, index, destination);
		
		return index;
	}

	@SuppressWarnings("unchecked")
	@Override
	public LongTag deserialize(int index, byte[] source) {
		assert index >= 0 : "Index cannot be less than 0";
		assert tag == source[index] : "Incorect datatype tag";
		assert index + 4 + DATA_SIZE <= source.length : "Source does not contain enough data";
		
		LongTag data = new LongTag(null, 0, primiSerializer, structedSerializer);
		data.deserialize2(index, source);
		
		return data;
	}

	@Override
	public int deserialize2(int index, byte[] source) {
		assert index >= 0 : "Index cannot be less than 0";
		assert tag == source[index] : "Incorect datatype tag";
		assert index + 4 + DATA_SIZE <= source.length : "Source does not contain enough data";
		
		index = index + 1;
		index = deserializeId(index, source);
		
		data = primiSerializer.readLong(index, source);
		
		return index + DATA_SIZE;
	}

	@Override
	public int getSize() {
		return 1 + getIdSize() + DATA_SIZE;
	}
}
