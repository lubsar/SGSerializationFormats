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
