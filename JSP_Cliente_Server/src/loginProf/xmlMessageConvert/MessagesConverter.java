package loginProf.xmlMessageConvert;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import answersCorrection.ValidateXmlWithXSD;
import xmlWriter.XMLReadWrite;

public class MessagesConverter {

	// da mensagem do servidor retorna a string de resposta (correto, incorreto)
	// no formato <info id=0 client="prof"><answer>correto</answer></info>
	public static String resultResponseProfInfo(String xmlReceived) {
		if (ValidateXmlWithXSD.validateLoginProf(xmlReceived)) {
			Document doc = XMLReadWrite.documentFromString(xmlReceived);

			NodeList request = doc.getElementsByTagName("answer");
			return request.item(0).getTextContent();
		}
		return "incorreto";
	}

	public static String resultRegisterProfXML(String userName, String pass) {
		return "<registaProf><userName>" + userName + "</userName><password>" + pass
				+ "</password></registaProf>";
	}
	
	public static String getResponseFromServer(String xmlMsg) {
		ArrayList<CreateComServer> a = new ArrayList<CreateComServer>();
		
		CreateComServer com = new CreateComServer();
		a.add(com);
		
		com.sendXMLToServer(xmlMsg);
		String result = "";
		for(;;) {
			try {
				Thread.sleep(100);
				if(com.getResponseReceived() != null) {
					result = com.getResponseReceived();
					break;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		a.remove(com);
		return result;
	}

}
