package answersCorrection;

import javax.xml.XMLConstants;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import otherServerInfo.ServerInfo;
import xmlWriter.ValidatorXML;
import xmlWriter.XMLReadWrite;

public class ValidateXmlWithXSD {
	/*public static void main(String[] args) {
		String a = "<info id=\"a\" client=\"prof\"><answer>correto</answer></info>";
		System.out.println(validateLoginProf(a));
	}*/
	
	public static boolean validateLoginProf(String xml) {
		Document doc = XMLReadWrite.documentFromString(xml);
		try {
			if(ValidatorXML.validDoc(doc, ServerInfo.pathXSD+"prof.xsd", XMLConstants.W3C_XML_SCHEMA_NS_URI)) {
				return true;
			}
			else {
				System.out.println("Erro validar o xml login prof");
				return false;
			}
		} catch (SAXException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean validateChangePass(String xml) {
		Document doc = XMLReadWrite.documentFromString(xml);
		try {
			if(ValidatorXML.validDoc(doc, ServerInfo.pathXSD+"changepass.xsd", XMLConstants.W3C_XML_SCHEMA_NS_URI)) {
				return true;
			}
			else {
				System.out.println("Erro validar o xml login prof");
				return false;
			}
		} catch (SAXException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean validateNewProf(String xml) {
		Document doc = XMLReadWrite.documentFromString(xml);
		try {
			if(ValidatorXML.validDoc(doc, ServerInfo.pathXSD+"newprof.xsd", XMLConstants.W3C_XML_SCHEMA_NS_URI)) {
				return true;
			}
			else {
				System.out.println("Erro validar o xml login prof");
				return false;
			}
		} catch (SAXException e) {
			e.printStackTrace();
			return false;
		}
	}
}
