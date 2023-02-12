package guiProf.answers;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import clienteProf.ClienteProfessor;
import xml.XMLReadWrite;

public class XMLReader {
	
	private Document document;

	
	public XMLReader(){
		this.document = ClienteProfessor.getDocument();
	}
	
	//função para meter as respostas dos aluno no scroll do prof
	protected String setTextToScroll(String xml) {
		System.out.println(xml);
		Document doc = XMLReadWrite.documentFromString(xml);
		
		NodeList aluno = doc.getElementsByTagName("aluno");
		NodeList quest = doc.getElementsByTagName("pergunta");
		NodeList questText = document.getElementsByTagName("pergunta");
		
		String result = "";
		for(int i = 0; i < aluno.getLength();i++) {
			result = result+"Aluno Nº"+aluno.item(i).getAttributes().getNamedItem("numero").getNodeValue();
			String response = "";
			for(int j = 0; j < questText.getLength();j++) {
				if(questText.item(j).getAttributes().getNamedItem("id").getNodeValue().equals(quest.item(i).getAttributes().getNamedItem("id").getNodeValue())) {
					
					Element elem = (Element) questText.item(j);
					NodeList opcions = elem.getElementsByTagName("opcao");
					NodeList index = doc.getElementsByTagName("resposta");
					result = result+", respondeu:";
					for(int k = 0; k < index.getLength();k++) {
						if(k == 0) {
							result = result+opcions.item(Integer.parseInt(index.item(k).getAttributes().getNamedItem("indice").getNodeValue())).getAttributes().getNamedItem("resposta").getNodeValue();
						}else {
							result = result+" e "+opcions.item(Integer.parseInt(index.item(k).getAttributes().getNamedItem("indice").getNodeValue())).getAttributes().getNamedItem("resposta").getNodeValue();
						}
						
					}
					response = elem.getElementsByTagName("questao").item(0).getTextContent();
				}
			}
			
			result = result+" à pergunta: "+response+"\n";
		}
		return result;
		
	}

}
