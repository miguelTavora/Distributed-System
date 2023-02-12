package server.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import PathAndExpressions.Path;
import xml.XMLReadWrite;

public class ValidatorXML {
	
	public static final boolean validDoc(Document document, String xsdFileName, String type) throws SAXException {

		// create a SchemaFactory capable of understanding WXS schemas
		SchemaFactory factory = SchemaFactory.newInstance(type);

		// load a WXS schema, represented by a Schema instance
		Source schemaFile = new StreamSource(new File(xsdFileName));
		Schema schema = factory.newSchema(schemaFile);

		// create a Validator instance, which can be used to validate an instance document
		Validator validator = schema.newValidator();
		

		// validate the DOM tree
		try {
		    validator.validate(new DOMSource(document));
		    return true;
		} catch (IOException e) {
		    // instance document is invalid!
			return false;
		}
	}
	
	public static boolean validateProfLogin(String xml) {
		Document doc = XMLReadWrite.documentFromString(xml);
		try {
			if(ValidatorXML.validDoc(doc, Path.pathXML + "prof.xsd", XMLConstants.W3C_XML_SCHEMA_NS_URI)) {
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
			if(ValidatorXML.validDoc(doc, Path.pathXML + "changepass.xsd", XMLConstants.W3C_XML_SCHEMA_NS_URI)) {
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
	
	public static boolean validateAddQuestion(String xml) {
		Document doc = XMLReadWrite.documentFromString(xml);
		try {
			if(ValidatorXML.validDoc(doc, Path.pathXML + "addquest.xsd", XMLConstants.W3C_XML_SCHEMA_NS_URI)) {
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
	
	public static boolean validateAddProf(String xml) {
		Document doc = XMLReadWrite.documentFromString(xml);
		try {
			if(ValidatorXML.validDoc(doc, Path.pathXML + "addprof.xsd", XMLConstants.W3C_XML_SCHEMA_NS_URI)) {
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
