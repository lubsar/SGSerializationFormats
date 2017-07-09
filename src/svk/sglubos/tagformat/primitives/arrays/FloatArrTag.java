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

package svk.sglubos.tagformat.primitives.arrays;

import svk.sglubos.sgserialization.PrimiSerializer;
import svk.sglubos.sgserialization.StructedSerializer;
import svk.sglubos.tagformat.ArrayTag;
import svk.sglubos.tagformat.Tags;

public class FloatArrTag extends ArrayTag {
	private static final int DATA_TYPE_SIZE = 4;
	
	public float[] data;
	
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
		assert index + 1 >= destination.length: "Destination does not have enough capacity";
		
		destination[index++] = tag;
		index = serializeID(index, destination);
		index = primiSerializer.write(data.length, index, destination);
		index = primiSerializer.write(data, index, destination);
		
		return index;
	}

	@SuppressWarnings("unchecked")
	@Override
	public FloatArrTag deserialize(int index, byte[] source) {
		assert index >= 0 : "Index cannot be less than 0";
		assert tag == source[index] : "Incorect datatype tag";
		assert index + 4 <= source.length : "Source does not contain enough data";
		
		FloatArrTag data = new FloatArrTag(null, null, primiSerializer, structedSerializer);
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
		return 1 + 4 + getIdSize() + DATA_TYPE_SIZE * data.length ;
	}
}
