package myBatchProcessor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PipeTag extends Command {

	public void parseTag(Element nodes, Map<String, Map<String, String>> PipeTag) {
		NodeList pipeNode = nodes.getChildNodes();
		Map<String, String> tag;
		String k;

		for (int x = 0; x < pipeNode.getLength(); x++) {
			Node node = pipeNode.item(x);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element elemPipe = (Element) node;

				tag = new HashMap<String, String>();

				tag.put("CMD_ID", elemPipe.getAttribute("id"));
				tag.put("CMD_PATH", elemPipe.getAttribute("path"));
				tag.put("CMD_ARG", elemPipe.getAttribute("args"));
				tag.put("CMD_IN", elemPipe.getAttribute("in"));
				tag.put("CMD_OUT", elemPipe.getAttribute("out"));

				k = elemPipe.getAttribute("id");
				PipeTag.put(k, tag);

			}
		}

	}

	public void executeTag(Map<String, Map<String, String>> PipeTag, Element nodes, String s) throws Exception {

		List<String> Cmd1 = new ArrayList<String>();
		List<String> Cmd2 = new ArrayList<String>();

		NodeList pipeNode = nodes.getChildNodes();

		Node node1 = pipeNode.item(1);
		Node node2 = pipeNode.item(3);

		Element e1 = (Element) node1;
		Element e2 = (Element) node2;

		Cmd1.add(e1.getAttribute("path"));
		Cmd2.add(e2.getAttribute("path"));

		String arg1 = e1.getAttribute("args");
		String arg2 = e2.getAttribute("args");

		StringTokenizer st1 = new StringTokenizer(arg1);
		StringTokenizer st2 = new StringTokenizer(arg2);

		while (st1.hasMoreTokens() || st2.hasMoreTokens()) {
			if (st1.hasMoreTokens()) {
				String tok1 = st1.nextToken();
				Cmd1.add(tok1);
			}

			if (st2.hasMoreTokens()) {
				String tok2 = st2.nextToken();
				Cmd2.add(tok2);
			}

		}

		ProcessBuilder builder1 = new ProcessBuilder(Cmd1);
		ProcessBuilder builder2 = new ProcessBuilder(Cmd2);
		builder1.directory(new File(s));
		builder2.directory(new File(s));

		try {
			if (PipeTag.containsKey(e1.getAttribute("in")) && PipeTag.containsKey(e2.getAttribute("out"))) {

				builder1.redirectInput(
						new File(s, (((Map<String, String>) (PipeTag.get(e1.getAttribute("in")))).get("FILE_PATH"))));
				builder2.redirectOutput(
						new File(s, (((Map<String, String>) (PipeTag.get(e2.getAttribute("out")))).get("FILE_PATH"))));
			}
		} catch (Exception e) {
			System.out.println("**FILE NOT FOUND** ");
		}

		System.out.println("Executing Command: " + e1.getAttribute("id"));
		System.out.println("Executing Command: " + e2.getAttribute("id"));

		Process process1 = builder1.start();
		Process process2 = builder2.start();
		InputStream is = process1.getInputStream();
		OutputStream os = process2.getOutputStream();

		copyStreams(is, os);

		System.out.println("PIPE CLOSED");
	}

	static void copyStreams(final InputStream is, final OutputStream os) {
		Runnable copyThread = (new Runnable() {
			@Override
			public void run() {

				try {
					int achar;
					while ((achar = is.read()) != -1) {
						os.write(achar);

					}
					os.close();
				} catch (IOException ex) {
					throw new RuntimeException(ex.getMessage(), ex);
				}
			}
		});
		new Thread(copyThread).start();
	}

}
