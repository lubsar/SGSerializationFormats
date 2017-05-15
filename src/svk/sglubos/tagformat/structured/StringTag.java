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
