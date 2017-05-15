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

package svk.sglubos.tagformat.utils;

import java.nio.charset.Charset;

import svk.sglubos.sgserialization.basic.BasicStructedSerializer;

public class TagStructedSerializer extends BasicStructedSerializer {
	private static TagStructedSerializer INSTANCE = null;
	
	protected TagStructedSerializer(){}
	
	public static TagStructedSerializer get() {
		if(INSTANCE == null) {
			INSTANCE = new TagStructedSerializer();
		}
		
		return INSTANCE;
	}

	@Override
	public int write(String data, int index, byte[] destination) {
		assert index >= 0 : "Index cannot be less than 0";
		byte[] dataBytes = data.getBytes();
		assert index + 4 + dataBytes.length <= destination.length : "Destionation does not have enough capacity";
		
		destination[index++] = (byte)((dataBytes.length >> 24) & 0xFF);
		destination[index++] = (byte)((dataBytes.length >> 16) & 0x00FF);
		destination[index++] = (byte)((dataBytes.length >> 8) & 0x0000FF);
		destination[index++] = (byte)(dataBytes.length & 0x000000FF);
		
		System.arraycopy(dataBytes, 0, destination, index, dataBytes.length);
		
		return index + dataBytes.length;
	}

	@Override
	public String readString(int byteLength, int index, byte[] source) {
		assert index >= 0 : "Index cannot be less than 0";
		assert index + byteLength <= source.length : "Source does not contain enough data";
		
		if(byteLength == -1) {
			byteLength = ((source[index++] & 0xFF) << 24) ^ ((source[index++] & 0xFF) << 16) ^ ((source[index++] & 0xFF) << 8) ^ (source[index] & 0xFF);
		}
		assert byteLength > 0 : "String length (bytes) must be greater than 0";
		
		return new String(source, index, byteLength);
	}
	
	@Override
	public int write(String data, Charset charset, int index, byte[] destination) {
		assert index >= 0 : "Index cannot be less than 0";
		assert charset != null : "Charset cannot be null";
		assert Charset.isSupported(charset.name()) : "Unsupported charset";
		byte[] dataBytes = data.getBytes(charset);
		assert index + 4 + dataBytes.length <= destination.length : "Destionation does not have enough capacity";

		destination[index++] = (byte)((dataBytes.length >> 24) & 0xFF);
		destination[index++] = (byte)((dataBytes.length >> 16) & 0x00FF);
		destination[index++] = (byte)((dataBytes.length >> 8) & 0x0000FF);
		destination[index++] = (byte)(dataBytes.length & 0x000000FF);
		
		System.arraycopy(dataBytes, 0, destination, index, dataBytes.length);
		
		return index + dataBytes.length;
	}

	@Override
	public String readString(int byteLength, Charset charset, int index, byte[] source) {
		assert index >= 0 : "Index cannot be less than 0";
		assert index + byteLength <= source.length : "Source does not contain enough data";
		
		if(byteLength == -1) {
			byteLength = ((source[index++] & 0xFF) << 24) ^ ((source[index++] & 0xFF) << 16) ^ ((source[index++] & 0xFF) << 8) ^ (source[index] & 0xFF);
		}
		assert byteLength > 0 : "String length (bytes) must be greater than 0";
		
		return new String(source, index, byteLength, charset);
	}
}
