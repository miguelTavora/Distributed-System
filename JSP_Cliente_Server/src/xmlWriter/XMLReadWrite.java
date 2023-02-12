package xmlWriter;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class XMLReadWrite {
	
	public static final String documentToString(Document xmlDoc) {
		Writer out = new StringWriter();
		try {
			Transformer tf = TransformerFactory.newInstance().newTransformer();
			tf.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1"); // 
			tf.setOutputProperty(OutputKeys.INDENT, "yes");
			tf.transform(new DOMSource(xmlDoc), new StreamResult(out));
		} catch (Exception e) {
			System.out.println("Error: Unable to write XML to string!\n\t" + e);
			e.printStackTrace();
		}
		return out.toString();
	}
	
	public static final Document documentFromString(String strXML) {
		Document xmlDoc = null;
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			xmlDoc = builder.parse(new InputSource(new StringReader(strXML)));
		} catch (Exception e) {
			System.err.println("Error: Unable to read XML from string!\n\t" + e);
			e.printStackTrace();
		}
		return xmlDoc;
	}
	

}