package myBatchProcessor;

import java.io.File;
import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MyBatchProcessor {

	public static void main(String[] args) throws Exception {
		File f = new File(args[0]);
		System.out.println("PROGRAM STARTS");
		FileInputStream fis = new FileInputStream(f);

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fis);

		Element pnode = doc.getDocumentElement();

		NodeList nodes = pnode.getChildNodes();

		Batch b = new Batch();
		System.out.println("PARSING STARTS");
		for (int idx = 0; idx < nodes.getLength(); idx++) {
			Node node = nodes.item(idx);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element elem = (Element) node;
				b.parseCmd(elem);

			}
		}
		System.out.println("PARSING COMPLETED");
		b.executeCmd(nodes);
		System.out.println("PROGRAM ENDS");
	}
}
