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

import java.util.HashMap;
import java.util.Map;

import svk.sglubos.tagformat.container.ContainerTag;
import svk.sglubos.tagformat.primitives.ByteTag;
import svk.sglubos.tagformat.primitives.DoubleTag;
import svk.sglubos.tagformat.primitives.FloatTag;
import svk.sglubos.tagformat.primitives.IntTag;
import svk.sglubos.tagformat.primitives.LongTag;
import svk.sglubos.tagformat.primitives.ShortTag;
import svk.sglubos.tagformat.primitives.arrays.ByteArrTag;
import svk.sglubos.tagformat.primitives.arrays.DoubleArrTag;
import svk.sglubos.tagformat.primitives.arrays.FloatArrTag;
import svk.sglubos.tagformat.primitives.arrays.IntArrTag;
import svk.sglubos.tagformat.primitives.arrays.LongArrTag;
import svk.sglubos.tagformat.primitives.arrays.ShortArrTag;
import svk.sglubos.tagformat.structured.StringTag;

public final class Tags {	
	public static final byte BYTE = 1;
	public static final byte SHORT = 2;
	public static final byte INT = 3;
	public static final byte LONG = 4;
	public static final byte FLOAT = 5;
	public static final byte DOUBLE = 6;
	public static final byte CHAR = 7;
	public static final byte BOOLEAN = 8;
	
	public static final byte ARRAY = 9;
	public static final byte CONTAINER = 10;
	public static final byte STRING = 11;
	
	public static final byte BIT8 = 20;
	public static final byte BIT16 = 21;
	public static final byte BIT32 = 22;
	public static final byte BIT64 = 23;
	
	private static final Map<Byte, Tag> tags = new HashMap<Byte, Tag>();
	private static final Map<Byte, ArrayTag> arrays = new HashMap<Byte, ArrayTag>();
	
	public static void registerTag(byte identifier, Tag tag) {
		tags.put(identifier, tag);
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Tag> T getTag(byte identifier) {
		if(identifier == ARRAY) {
			throw new RuntimeException("Arrays are stored in separate map");
		}
		return (T) tags.get(identifier);
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends ArrayTag> T getArrayTag(byte dataType) {
		return (T) arrays.get(dataType);
	}
	
	static {
		tags.put(BYTE, new ByteTag(null, (byte) 0));
		tags.put(SHORT, new ShortTag(null, (short) 0));
		tags.put(INT, new IntTag(null,  0));
		tags.put(LONG, new LongTag(null, 0));
		tags.put(FLOAT, new FloatTag(null, 0.0f));
		tags.put(DOUBLE, new DoubleTag(null,  0.0));
		
		tags.put(CONTAINER, new ContainerTag(null));
		tags.put(STRING, new StringTag(null, null));
		
		arrays.put(BYTE, new ByteArrTag(null, null));
		arrays.put(SHORT, new ShortArrTag(null, null));
		arrays.put(INT, new IntArrTag(null,  null));
		arrays.put(LONG, new LongArrTag(null, null));
		arrays.put(FLOAT, new FloatArrTag(null, null));
		arrays.put(DOUBLE, new DoubleArrTag(null,  null));
	}
}
