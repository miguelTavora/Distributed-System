package server.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import PathAndExpressions.Expression;
import PathAndExpressions.Path;
import xml.XMLReadWrite;

public class DocumentXML {

	private String filePath;
	private DocumentBuilderFactory factory;
	private DocumentBuilder builder;
	private Document document;

	public DocumentXML() throws ParserConfigurationException, SAXException, IOException {
		try {
			filePath = Path.pathXML + "dados.xml";
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
			document = builder.parse(Path.pathXML+"dados.xml");
			Document doc = builder.parse(Path.pathXML+"users.xml");
			if (ValidatorXML.validDoc(document, Path.pathXML + "dados.xsd", XMLConstants.W3C_XML_SCHEMA_NS_URI) 
					&& ValidatorXML.validDoc(doc, Path.pathXML + "users.xsd", XMLConstants.W3C_XML_SCHEMA_NS_URI))
				System.out.println("Validação do XML feita com sucesso");// Validação com XSD realizada com sucesso!
			else
				System.out.println("O documento XML não é válido para o documento XSD");// Falhou a validação com XSD

		} catch (Exception e) {
			e.printStackTrace(System.out);
			System.out.print("Erro ao analisar o documento XML.");
		}
	}

	//retorna o XML em formato String sem o header
	public String xmlDocStringFormat() {
		return getClearXMLString(XMLReadWrite.documentToString(document));
	}
	
	// retorna o XML em formato String sem o header apos ser alterado
	public String xmlDocStringFormatReload() {
		try {
			document = builder.parse(Path.pathXML + "dados.xml");
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return getClearXMLString(XMLReadWrite.documentToString(document));
	}

	//envia perguntas do XML em formato String sem header
	public String questionXMLFormat(String strXML) {
		Document doc = XMLReadWrite.documentFromString(strXML);

		NodeList request = (NodeList) doc.getElementsByTagName("pergunta");

		NodeList questions = document.getElementsByTagName("pergunta");

		String id = request.item(0).getAttributes().getNamedItem("id").getNodeValue();
		
		String theme = "";
		NodeList categories = document.getElementsByTagName("categoria");
		for (int i = 0; i < categories.getLength(); i++) {
			Element elemCat = (Element) categories.item(i);
			NodeList cate = elemCat.getElementsByTagName("pergunta");
			for (int j = 0; j < cate.getLength(); j++) {
				if(cate.item(j).getAttributes().getNamedItem("id").getNodeValue().equals(id)) {
					theme = categories.item(i).getAttributes().getNamedItem("tema").getNodeValue();
					break;
				}
			}
			if(theme != "")
				break;
		}
		
		for (int i = 0; i < questions.getLength(); i++) {
			if (questions.item(i).getAttributes().getNamedItem("id").getNodeValue().equals(id)) {
				Node node = questions.item(i);
				Document newDocument = builder.newDocument();
				Node importedNode = newDocument.importNode(node, true);
				newDocument.appendChild(importedNode);
				return getClearXMLString(XMLReadWrite.documentToString(newDocument))+theme;
			}
		}

		return null;
	}
	//função para limpar o header
	private String getClearXMLString(String strXML) {
		strXML = strXML.substring(61,strXML.length());
		return strXML.replace("\n", "").replace("\r", "");
	}
	
	//função que envia o xml em String corrigido
	public String sendResultWithCorrection(String strXML) {
		System.out.println(strXML);
		Document doc = XMLReadWrite.documentFromString(strXML);
		
		

		String number = doc.getElementsByTagName("aluno").item(0).getAttributes().getNamedItem("numero").getNodeValue();
		String st = Expression.sendResultOfQuestions;
		st = st+"<aluno numero=\""+number+"\">";
		
		NodeList request = (NodeList) doc.getElementsByTagName("pergunta");
		for(int i = 0;i < request.getLength(); i++) {
			String id = request.item(i).getAttributes().getNamedItem("id").getNodeValue();
			if(request.item(i).hasChildNodes()) {
				int index = 0;
				NodeList res = document.getElementsByTagName("pergunta");
				for(int j = 0;j < res.getLength(); j++) {
					if(res.item(j).getAttributes().getNamedItem("id").getNodeValue().equals(id)) {
						index = j;
						break;
					}
				}
				Element elemRes = (Element) request.item(i);
				NodeList respostas = elemRes.getElementsByTagName("resposta");
				Element opcao = (Element)res.item(index);
				NodeList opcoes = opcao.getElementsByTagName("opcao");
				st = st+"<pergunta id=\""+id+"\">";
				for(int j = 0;j < respostas.getLength(); j++) {
					st = st+"<resposta correcao=\"";
					int indice = Integer.parseInt(respostas.item(j).getAttributes().getNamedItem("indice").getNodeValue());
					st = st+opcoes.item(indice).getAttributes().getNamedItem("resposta").getNodeValue();
					st = st+"\"/>";
				}
				st = st+"</pergunta>";
			}
			else
				st = st+"<pergunta id=\""+id+"\"/>";
			
		}
		st = st+"</aluno>";
		return st+Expression.sendResultOfQuestionsEnd;
		
	}
	
	public boolean addQuestionToXML(String categoria, String questao,
			String tempo, String[] opcoesResposta, String[] responses) {

		if (categoria == null || questao == null || tempo == null) {
			return false;
		}

		if (categoria.length() > 64 || questao.length() > 1024
				|| opcoesResposta.length > 6) {
			return false;
		}

		Element elementoCategoriaAtual = null;
		NodeList elementosCategoria = document
				.getElementsByTagName("categoria");

		for (int i = 0; i < elementosCategoria.getLength(); i++) {
			final Element elemento = (Element) elementosCategoria.item(i);
			if (elemento.getAttributes().getNamedItem("tema").getNodeValue()
					.equals(categoria)) {
				elementoCategoriaAtual = elemento;
				break;
			}
		}
		Element elementoPergunta = criarXMLPergunta(questao, tempo,opcoesResposta, responses);

		if (elementoCategoriaAtual == null) {
			elementoCategoriaAtual = document.createElement("categoria");
			elementoCategoriaAtual.setAttribute("tema", categoria);
			document.getDocumentElement().appendChild(elementoCategoriaAtual);
		}
		elementoCategoriaAtual.appendChild(elementoPergunta);

		//Escrever para o ficheiro
		try {
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File(filePath));
			transformer.transform(source, result);

		} catch (TransformerFactoryConfigurationError
				| TransformerException exception) {
			exception.printStackTrace();
			return false;
		}

		return true;
	}
	
	private Element criarXMLPergunta(String questao, String tempo,
			String[] opcoesResposta, String[] responses) {
		Element novoElementoPergunta = document.createElement("pergunta");
		novoElementoPergunta.setAttribute("id", "ID_"
				+ (document.getElementsByTagName("pergunta").getLength() + 1));

		Element elementoQuestao = document.createElement("questao");
		elementoQuestao.setTextContent(questao);

		Element elementoTempo = document.createElement("tempo");
		elementoTempo.setTextContent(tempo);

		Element elementoMultimedia = document.createElement("multimedia");

		ArrayList<Element> elementosOpcoes = new ArrayList<Element>();
		for (int i = 0; i < opcoesResposta.length; i++) {
			Element elementoOpcaoAtual = document.createElement("opcao");
			
			boolean flag = false;
			for(int j = 0; j< responses.length; j++ ) {
				if(i == (Integer.parseInt(responses[j]) -1)) {
					elementoOpcaoAtual.setAttribute("resposta", "certo");
					flag = true;
				}
			}
			if(!flag)
				elementoOpcaoAtual.setAttribute("resposta", "errado");
		
			elementoOpcaoAtual.setTextContent(opcoesResposta[i]);
			elementosOpcoes.add(elementoOpcaoAtual);
		}

		novoElementoPergunta.appendChild(elementoQuestao);
		novoElementoPergunta.appendChild(elementoTempo);
		novoElementoPergunta.appendChild(elementoMultimedia);
		for (Element element : elementosOpcoes) {
			novoElementoPergunta.appendChild(element);
		}

		return novoElementoPergunta;
	}

}
