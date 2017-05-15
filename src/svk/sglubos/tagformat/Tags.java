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

public interface Tags {
	static final byte TAG_SIZE = 1;
	
	static final byte BYTE = 1;
	static final byte SHORT = 2;
	static final byte INT = 3;
	static final byte LONG = 4;
	static final byte FLOAT = 5;
	static final byte DOUBLE = 6;
	static final byte CHAR = 7;
	static final byte BOOLEAN = 8;
	
	static final byte ARRAY = 9;
	static final byte OBJECT = 10;
	static final byte STRING = 11;
	
	static final byte BIT8 = 20;
	static final byte BIT16 = 21;
	static final byte BIT32 = 22;
	static final byte BIT64 = 23;
}
