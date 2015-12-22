package myBatchProcessor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.w3c.dom.Element;


public class CmdTag extends Command {

	public void parseTag(Element nodes, Map<String, Map<String, String>> Ctag) {

		if (!(nodes.getAttribute("id") == null || nodes.getAttribute("id").isEmpty())) {

			tag.put("CMD_ID", nodes.getAttribute("id"));
		}

		if (!(nodes.getAttribute("path") == null || nodes.getAttribute("path").isEmpty())) {

			tag.put("CMD_PATH", nodes.getAttribute("path"));
		}

		if ((String) nodes.getAttribute("args") != null) {

			tag.put("CMD_ARG", nodes.getAttribute("args"));
		}

		if (!(nodes.getAttribute("in") == null || nodes.getAttribute("in").isEmpty())) {

			tag.put("CMD_IN", nodes.getAttribute("in"));
		}

		if (!(nodes.getAttribute("out") == null || nodes.getAttribute("out").isEmpty())) {

			tag.put("CMD_OUT", nodes.getAttribute("out"));
		}

		Ctag.put(nodes.getAttribute("id"), tag);
		System.out.println("CMD DONE PARSING");

	}

	public ProcessBuilder executeTag(Map<String, Map<String, String>> Ctag, String id, String s) {

		List<String> Cmd = new ArrayList<String>();
		Map<String, String> map = Ctag.get(id);

		Cmd.add(map.get("CMD_PATH"));

		String arg = map.get("CMD_ARG");

		StringTokenizer st = new StringTokenizer(arg);

		while (st.hasMoreTokens()) {
			String tok = st.nextToken();
			Cmd.add(tok);
		}

		ProcessBuilder builder = new ProcessBuilder();

		try {
			if (map.containsKey("CMD_IN"))
				builder.redirectInput(new File(s, (((Map<String, String>) (Ctag.get(map.get("CMD_IN")))).get("FILE_PATH"))));
		} catch (Exception e) {
			System.out.println("**INPUT FILE " + map.get("CMD_IN") + " NOT FOUND** ");
		}
		builder.command(Cmd);

		return (builder);
	}

}

