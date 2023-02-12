package server.xml;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import org.w3c.dom.Document;

import PathAndExpressions.Path;
import xml.XMLReadWrite;

public class SerializeObjectsXML {
	
	private static final String FILENAME = Path.pathXML+"users.xml";
	
	public static boolean addUser(String username,  String password) {
		try {
			
			final XMLDecoder decoder = new XMLDecoder(new FileInputStream(FILENAME));
			@SuppressWarnings("unchecked")
			final List<String> listFromFile = (List<String>) decoder.readObject();
			decoder.close();
			
			for(int i = 0; i < listFromFile.size();i++) {
				if(listFromFile.get(i).equals(username)) {
					return false;
				}
				else
					i++;
			}

			listFromFile.add(username);
			listFromFile.add(password);
			// serializar para XML
			final XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(FILENAME)));
			encoder.writeObject(listFromFile);
			encoder.close();
			return true;
		}catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean addUserWithoutCheck(String username,  String password) {
		try {
			
			final XMLDecoder decoder = new XMLDecoder(new FileInputStream(FILENAME));
			@SuppressWarnings("unchecked")
			final List<String> listFromFile = (List<String>) decoder.readObject();
			decoder.close();
			
			for(int i = 0; i < listFromFile.size();i++) {
				if(listFromFile.get(i).equals(username)) {
					listFromFile.remove(i);
					listFromFile.remove(i);
					break;
				}
				else
					i++;
			}
			
			listFromFile.add(username);
			listFromFile.add(password);
			// serializar para XML
			final XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(FILENAME)));
			encoder.writeObject(listFromFile);
			encoder.close();
			return true;
		}catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean validateUser(String username,  String password) {
		try {
			
			final XMLDecoder decoder = new XMLDecoder(new FileInputStream(FILENAME));
			@SuppressWarnings("unchecked")
			final List<String> listFromFile = (List<String>) decoder.readObject();
			decoder.close();

			for(int i = 0; i < listFromFile.size();i++) {
				if(listFromFile.get(i).equals(username)) {
					if(listFromFile.get(i+1).equals(password)) 
						return true;
					return false;
				}else 
					i++;
				
			}
			return false;
		}catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static String changePassword(String xmlStr) {
		Document doc = XMLReadWrite.documentFromString(xmlStr);
		String user = doc.getElementsByTagName("user").item(0).getTextContent();
		String oldPass = doc.getElementsByTagName("old").item(0).getTextContent();
		
		if(!validateUser(user,oldPass)) {
			return "<resultpass>insucesso</resultpass>";
			
		}
		
		String newPass = doc.getElementsByTagName("new").item(0).getTextContent();
		if(addUserWithoutCheck(user,newPass) && ValidatorXML.validateChangePass(xmlStr)) {
			return "<resultpass>sucesso</resultpass>";
		}

		return "<resultpass>insucesso</resultpass>";
		
	}
	
	/*public static void main(String[] args) {

		addUser("deus", "123456");
		addUser("miguel", "123456");
		addUser("pedro", "123456");
		addUser("corrida", "123456");
		addUser("deus", "123456");
		addUser("stor", "123456");
		
	}*/
}