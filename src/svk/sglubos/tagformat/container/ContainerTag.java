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

package svk.sglubos.tagformat.container;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import svk.sglubos.sgserialization.PrimiSerializer;
import svk.sglubos.sgserialization.StructedSerializer;
import svk.sglubos.tagformat.Tag;
import svk.sglubos.tagformat.Tags;
import svk.sglubos.tagformat.primitives.ByteTag;
import svk.sglubos.tagformat.primitives.IntTag;

public class ContainerTag extends Tag {
	public Map<String, Tag> data = new HashMap<String, Tag>();
	
	public ContainerTag(String id) {
		super(Tags.CONTAINER, id);
	}

	public ContainerTag(String id, PrimiSerializer primiSerializer, StructedSerializer structedSerializer) {
		super(Tags.CONTAINER, id, primiSerializer, structedSerializer);
	}

	@Override
	public int serialize(int index, byte[] location) {
		location[index++] = tag;
		index = serializeID(index, location);
		index = primiSerializer.write(getSize(), index, location);
		
		Collection<Tag> tags = data.values();
		
		for(Tag tag : tags) {
			index = tag.serialize(index, location);
		}
		
		return index;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ContainerTag deserialize(int index, byte[] source) {
		assert index >= 0 : "Index cannot be less than 0";
		assert tag == source[index] : "Incorect datatype tag";
		assert index + 4  <= source.length : "Source does not contain enough data";
		
		ContainerTag data = new ContainerTag(null);
		data.deserialize2(index, source);
		
		return data;
	}

	@Override
	public int deserialize2(int index, byte[] source) {
		assert index >= 0 : "Index cannot be less than 0";
		assert tag == source[index] : "Incorect datatype tag";
		assert index + 4  <= source.length : "Source does not contain enough data";
		
		index = deserializeId(index, source);
		int size = primiSerializer.readInt(index, source);
		index += 4;
		
		return deserializeContent(index, size, source);
	}
	
	protected int deserializeContent(int index, int size, byte[] source) {
		assert index >= 0 : "Index cannot be less than 0";
		assert index + size  <= source.length : "Source does not contain enough data";
		
		int counter = 0; 
		
		do {
			Tag tag;
			if(source[index] != Tags.ARRAY) {
				tag = Tags.getArrayTag(source[index + 1]).deserialize(index, source);
			} else {
				tag = Tags.getTag(source[index]).deserialize(index, source);
			}
			
			data.put(tag.getID(), tag);
			counter += tag.getSize();
		} while(counter < size);
		
		return index;
	}
	
	@Override
	public int getSize() {
		int size = 1 + getIdSize();
		Collection<Tag> tags = data.values();
		
		for(Tag tag : tags) {
			size += tag.getSize();
		}
		
		return size;
	}
	
	public <T extends Tag> T getTag(String name, Class<T> clazz) {
	    try {
	        return clazz.cast(data.get(name));
	    } catch(ClassCastException e) {
	    	throw new ClassCastException();
	    }
	}
}
