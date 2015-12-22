package myBatchProcessor;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

public class FileTag extends Command {

	public Map<String, Map<String, String>> Ftag = new HashMap<String, Map<String, String>>();

	public void parseTag(Element nodes, Map<String, Map<String, String>> Ftag) {

		if (!(nodes.getAttribute("path") == null || nodes.getAttribute("path").isEmpty())) {

			tag.put("FILE_PATH", nodes.getAttribute("path"));
		}
		Ftag.put(nodes.getAttribute("id"), tag);
		System.out.println("FILE DONE PARSING");
	}

}
