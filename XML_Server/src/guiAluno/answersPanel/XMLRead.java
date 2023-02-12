package guiAluno.answersPanel;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import xml.XMLReadWrite;

public class XMLRead {
	
	private String question;
	
	protected String getThemeFromXML(String strXML) {
		String theme = "";
		for(int i = strXML.length()-1;i>0;i--) {
			if(strXML.charAt(i) != '>') {
				theme = theme+strXML.charAt(i);
			}
			else
				break;
		}
		String reverse = "";
		for(int i = theme.length()-1;i>-1;i--) {
			reverse = reverse+theme.charAt(i);
		}
		return reverse;
	}
	
	protected String clearString(String str) {
		int count = 0;
		for(int i = str.length()-1;i>0;i--) {
			if(str.charAt(i) != '>') {
				count++;
			}
			else
				break;
		}
		return str.substring(0,str.length()-count);
	}
	
	public static String clearExtraString(String str) {
		int count = 0;
		for(int i = str.length()-1;i>0;i--) {
			if(str.charAt(i) != '>') {
				count++;
			}
			else
				break;
		}
		return str.substring(0,str.length()-count);
	}
	
	public static String getTimeFromXML(String str) {
		Document doc = XMLReadWrite.documentFromString(str);
		NodeList timeNode = doc.getElementsByTagName("tempo");
		
		String time = timeNode.item(0).getTextContent();
		String[] parts = time.split(":");
		
		int timer = 0;
		for(int i = 0;i<parts.length;i++) {
			if(i == 2) {
				timer = timer+(Integer.parseInt(parts[i]));
			}
			else if(i == 1) {
				timer = timer+(Integer.parseInt(parts[i]))*60;
			}else {
				timer = timer+((Integer.parseInt(parts[i]))*60*60);
			}
			
		}
		
		return ""+timer;
	}
	
	protected String getTextQuestion(String str) {
		Document doc = XMLReadWrite.documentFromString(str);
		NodeList quest = doc.getElementsByTagName("questao");
		return quest.item(0).getTextContent();
	}
	
	protected String[] getTextOpcions(String str) {
		Document doc = XMLReadWrite.documentFromString(str);
		NodeList timeNode = doc.getElementsByTagName("opcao");
		
		String[] opcions = new String[timeNode.getLength()];
		
		for(int i = 0; i< opcions.length;i++) {
			opcions[i] = timeNode.item(i).getTextContent();
		}
		return opcions;
	}
	
	protected String getIdOfQuestion(String str) {
		Document doc = XMLReadWrite.documentFromString(str);
		NodeList quest = doc.getElementsByTagName("pergunta");
		return quest.item(0).getAttributes().getNamedItem("id").getNodeValue();
	}
	
	protected String getXMLQuestion() {
		return this.question;
	}
	
	protected void setXMLQuest(String xml) {
		this.question = xml;
	}
	

}
