package myBatchProcessor;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

public abstract class Command {

	protected Map<String, String> tag = new HashMap<String, String>();

	abstract void parseTag(Element nodes, Map<String, Map<String, String>> Tag);

}
