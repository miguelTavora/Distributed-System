package guiProf.questions;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XMLReader {
	
	private Document document;
	private XPath xPath;
	
	public XMLReader(Document document){
		this.document = document;
		xPath = XPathFactory.newInstance().newXPath();
	}
	
	//para ir buscar os temas
	public String[] getThemes() {
		NodeList categories = document.getElementsByTagName("categoria");
		String[] strs = new String[categories.getLength()];

		for (int i = 0; i < categories.getLength(); i++) 
			strs[i] = categories.item(i).getAttributes().getNamedItem("tema").getNodeValue();
		
		return strs;
	}
	
	public String[] getQuestions(String theme) {
		NodeList categories = document.getElementsByTagName("categoria");
		String[] strs;
		
		for (int i = 0; i < categories.getLength(); i++) {
			if(categories.item(i).getAttributes().getNamedItem("tema").getNodeValue().equals(theme)) {
				Element elemQuest = (Element) categories.item(i);
				NodeList questions = elemQuest.getElementsByTagName("questao");
				strs = new String[questions.getLength()];
				for (int j = 0; j < questions.getLength(); j++) {
					strs[j] = questions.item(j).getTextContent();
					if(j == questions.getLength()-1) {
						return strs;
					}
				}
			}
		}
		return null;
		
	}
	
	public String getTime(String theme, String question) throws XPathExpressionException {
		String expression = "/categorias/categoria";
		NodeList categories = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
		
		for (int i = 0; i < categories.getLength(); i++) {
			if(categories.item(i).getAttributes().getNamedItem("tema").getNodeValue().equals(theme)) {
				Element elemTheme = (Element) categories.item(i);
				NodeList questions = elemTheme.getElementsByTagName("questao");
				for (int j = 0; j < questions.getLength(); j++) {
					if(questions.item(j).getTextContent().equals(question)) {
						return elemTheme.getElementsByTagName("tempo").item(j).getTextContent();
						
					}
				}
			}
		}
		return null;
	}
	
	public String[] getOpcions(String theme, String question) throws XPathExpressionException {
		String expression = "/categorias/categoria";
		NodeList categories = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
		String[] strs;
		
		for (int i = 0; i < categories.getLength(); i++) {
			if(categories.item(i).getAttributes().getNamedItem("tema").getNodeValue().equals(theme)) {
				Element elemTheme = (Element) categories.item(i);
				NodeList questions = elemTheme.getElementsByTagName("questao");
				NodeList questionElem = elemTheme.getElementsByTagName("pergunta");
				for (int j = 0; j < questions.getLength(); j++) {
					if(questions.item(j).getTextContent().equals(question)) {
						Element elemQuestion = (Element) questionElem.item(j);
						NodeList opcionsQuestion = elemQuestion.getElementsByTagName("opcao");
						strs = new String[opcionsQuestion.getLength()];
						for(int k = 0; k < opcionsQuestion.getLength(); k++) {
							strs[k] = opcionsQuestion.item(k).getTextContent();
							if(k == opcionsQuestion.getLength()-1) {
								return strs;
							}
						}
						
					}
				}
			}
		}
		return null;
	}
	
	//retorna array cheio de ints com as opções e com zeros
	private int[] getCorrectOpcionsWithZeros(String theme, String question) throws XPathExpressionException {
		String expression = "/categorias/categoria";
		NodeList categories = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
		int[] checked;
		
		for (int i = 0; i < categories.getLength(); i++) {
			if(categories.item(i).getAttributes().getNamedItem("tema").getNodeValue().equals(theme)) {
				Element elemTheme = (Element) categories.item(i);
				NodeList questions = elemTheme.getElementsByTagName("questao");
				NodeList questionElem = elemTheme.getElementsByTagName("pergunta");
				for (int j = 0; j < questions.getLength(); j++) {
					if(questions.item(j).getTextContent().equals(question)) {
						Element elemQuestion = (Element) questionElem.item(j);
						NodeList opcionsQuestion = elemQuestion.getElementsByTagName("opcao");
						checked = new int[opcionsQuestion.getLength()];
						for(int k = 0; k < opcionsQuestion.getLength(); k++) {
							if(opcionsQuestion.item(k).getAttributes().getNamedItem("resposta").getNodeValue().equals("certo")) {
								checked[k] = k+1;
							}
							if(k == opcionsQuestion.getLength()-1) {
								return checked;
							}
						}
						
					}
				}
			}
		}
		return null;
	}
	//vai buscar função de cima mas retorna sem os zeros
	public int[] getCorrectOpcions(String theme, String question) throws XPathExpressionException{
		int[] withZeros = getCorrectOpcionsWithZeros(theme,question);
		int count = 0;
		for(int i = 0; i < withZeros.length; i++) {
			if(withZeros[i] != 0) {
				count++;
			}
		}
		int[] clear = new int[count];
		int counter = 0;
		for(int i = 0; i < withZeros.length; i++) {
			if(withZeros[i] != 0) {
				clear[counter] = withZeros[i];
				counter++;
			}
		}
		return clear;
		
	}
	
	//vai buscar o id da pergunta selecionada
	public String getIdOfQuestionSelected(String theme, String question) {
		String expression = "/categorias/categoria";
		NodeList categories = null;
		try {
			categories = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < categories.getLength(); i++) {
			if(categories.item(i).getAttributes().getNamedItem("tema").getNodeValue().equals(theme)) {
				Element elemTheme = (Element) categories.item(i);
				NodeList questions = elemTheme.getElementsByTagName("questao");
				for (int j = 0; j < questions.getLength(); j++) {
					if(questions.item(j).getTextContent().equals(question)) 
						return elemTheme.getElementsByTagName("pergunta").item(j).getAttributes().getNamedItem("id").getNodeValue();
					
				}
			}
		}
		return null;
		
	}

}
