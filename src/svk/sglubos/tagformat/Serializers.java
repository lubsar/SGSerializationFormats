package svk.sglubos.tagformat;

import svk.sglubos.sgserialization.PrimiSerializer;
import svk.sglubos.sgserialization.StructedSerializer;
import svk.sglubos.sgserialization.basic.BasicPrimiSerializer;
import svk.sglubos.tagformat.utils.TagStructedSerializer;

public interface Serializers {
	static PrimiSerializer primiSerializer = BasicPrimiSerializer.get();
	static StructedSerializer structedSerializer = TagStructedSerializer.get();
}
