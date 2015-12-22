package myBatchProcessor;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Batch {
	public Map<String, Map<String, String>> allTag = new HashMap<String, Map<String, String>>();
	public String Dir, value;

	public void parseCmd(Element elem) throws Exception {
		String cmdName = elem.getNodeName();

		if (cmdName == null) {
			System.out.println("EMPTY TAG");
		} else if ("wd".equalsIgnoreCase(cmdName)) {
			System.out.println("PARSING WD : " + elem.getAttribute("id"));
			value = elem.getAttribute("path");
			if ((value == null || value.isEmpty())) {
				System.out.println("WORKING DIRECTORY NOT SET");

			} else {

				value = elem.getAttribute("path");
				System.out.println("WORKING DIRECTORY SET TO: " + value);
				Dir = elem.getAttribute("path");
				System.out.println("WD DONE PARSING");
			}

		}

		else if ("file".equalsIgnoreCase(cmdName)) {
			System.out.println("PARSING FILE : " + elem.getAttribute("id"));
			 Command file = new FileTag();
			file.parseTag(elem, allTag);
		}

		else if ("pipe".equalsIgnoreCase(cmdName)) {
			System.out.println("PARSING PIPE : " + elem.getAttribute("id"));

			Command pipe = new PipeTag();
			pipe.parseTag(elem, allTag);
			System.out.println("PIPE DONE PARSING");

		}

		else if ("cmd".equalsIgnoreCase(cmdName)) {
			System.out.println("PARSING CMD : " + elem.getAttribute("id"));
			Command cmd = new CmdTag();
			cmd.parseTag(elem, allTag);
		}

		else {
			System.out.println("UNKNOWN TAG");

		}

	}

	public void executeCmd(NodeList n1) throws Exception {

		for (int y = 0; y < n1.getLength(); y++) {
			Node node = n1.item(y);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element elem = (Element) node;

				String cmdName = elem.getNodeName();
				if ("cmd".equalsIgnoreCase(cmdName)) {

					CmdTag cmdd = new CmdTag();
					ProcessBuilder pb = new ProcessBuilder();
					System.out.println("EXECUTING COMMAND: " + elem.getAttribute("id"));
					
					pb = cmdd.executeTag(allTag, elem.getAttribute("id"), Dir);

					try {
						pb.directory(new File(Dir));

						value = elem.getAttribute("out");

						if (!(value == null || value.isEmpty())) {
							if (allTag.containsKey(value)) {
								pb.redirectOutput(new File(Dir,(((Map<String, String>) allTag.get(elem.getAttribute("out")))).get("FILE_PATH")));

							} else {
								System.out.println("**OUTPUT FILE " + value + " NOT FOUND** ");
								System.out.println("EXECUTION TERMINATED");
								continue;
							}

						}
					} catch (NullPointerException e) {
						System.out.println("FILE NOT FOUND");
					}

					try {

						Process process = pb.start();
						System.out.println("EXECUTION COMPLETED");
						process.waitFor();
					} catch (IOException e) {
						System.out.println("WORKING DIRECTORY NOT FOUND");
						System.out.println("EXECUTION TERMINATED");

					}
				}
				if ("pipe".equalsIgnoreCase(cmdName)) {
					System.out.println("EXECUTING PIPE");

					PipeTag pc = new PipeTag();
					pc.executeTag(allTag, elem,Dir);

				}
			}
		}

	}
}
