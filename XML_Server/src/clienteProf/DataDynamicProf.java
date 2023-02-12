package clienteProf;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import xml.XMLReadWrite;

public class DataDynamicProf {

	private ArrayList<String> studentsConnected;

	public DataDynamicProf() {
		studentsConnected = new ArrayList<String>();
	}

	protected void addStudentToList(String studentNumber) {
		studentsConnected.add(studentNumber);
	}

	protected void removeStudentToList(String studentNumber) {
		for (int i = 0; i < studentsConnected.size(); i++) {
			if (studentsConnected.get(i).equals(studentNumber)) {
				studentsConnected.remove(i);
			}
		}
	}

	public String[] getStudentsConnected() {
		String[] data = new String[studentsConnected.size()];

		for (int i = 0; i < studentsConnected.size(); i++) {
			data[i] = studentsConnected.get(i);
		}
		return data;
	}

	//adicionar a lista os alunos que ainda n existem
	protected void processNotifyAddList(String xml) {
		Document doc = XMLReadWrite.documentFromString(xml);

		if (!xml.subSequence(9, 11).equals("</")) {
			NodeList request = (NodeList) doc.getElementsByTagName("aluno");
			for (int i = 0; i < request.getLength(); i++) {
				if (studentsConnected.size() >  0) {
					boolean equal = false;
					for (int j = 0; j < studentsConnected.size(); j++) {
						if (request.item(i).getAttributes().getNamedItem("numero").getNodeValue().equals(studentsConnected.get(j))) 
							equal = true;
						else if(!equal && j == studentsConnected.size()-1) 
							studentsConnected.add(request.item(i).getAttributes().getNamedItem("numero").getNodeValue());
						
						if(equal) 
							break;
						
					}
				}
				else
					studentsConnected.add(request.item(i).getAttributes().getNamedItem("numero").getNodeValue());
			}
		}

	}
	//função para verificar se existem alunos na lista que já não estao conectados
	protected void removeFromList(String strXML) {
		Document doc = XMLReadWrite.documentFromString(strXML);
		
		NodeList number =  doc.getElementsByTagName("notify");
		
		NodeList alunos = doc.getElementsByTagName("aluno");
		
		if(!number.item(0).hasChildNodes() && studentsConnected.size() > 0) {
			for (int i = 0; i < studentsConnected.size(); i++) {
				studentsConnected.remove(i);
			}
		}
		if(alunos.getLength() == studentsConnected.size())
			return;
		
		for (int i = 0; i < studentsConnected.size(); i++) {
			boolean flag = false;
			for(int j = 0; j < alunos.getLength(); j++) {
				if(alunos.item(j).getAttributes().getNamedItem("numero").getNodeValue().equals(studentsConnected.get(i))) {
					flag = true;
				}
			}
			if(!flag) {
				studentsConnected.remove(i);
			}
			
		}
		
	}

	public int getNumberStudentsConnected() {
		return this.studentsConnected.size();
	}

}
