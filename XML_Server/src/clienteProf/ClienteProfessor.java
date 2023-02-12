package clienteProf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.w3c.dom.Document;

import PathAndExpressions.Expression;
import cliente.Cliente;
import guiProf.ProfFrameGestor;
import xml.XMLReadWrite;

public class ClienteProfessor {

	public static String xmlProtocol = "";
	private String host;
	private int port;
	private Socket socket;
	private BufferedReader in = null;
	private PrintWriter out = null;
	private ProfFrameGestor frame;
	private DataDynamicProf data;

	//private String inputLine;
	private static Document doc;

	public ClienteProfessor() {
		host = Cliente.DEFAULT_HOSTNAME;// Máquina onde reside a aplicação servidora
		port = Cliente.DEFAULT_PORT; // Porto da aplicação servidora
		data = new DataDynamicProf();

		createFrame();
		createSocket();

	}

	private void createSocket() {
		try {
			socket = new Socket(this.getHost(), this.getPort());

			out = new PrintWriter(socket.getOutputStream(), true);// Stream para escrita no socket
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));// Stream para leitura do socket

			checkInput();
			String inputLine;
			for (;;) {
				try {
					if ((inputLine = in.readLine()) != null) {
						System.out.println("Recebi P -> " + inputLine);
						if (inputLine.substring(0, 12).equals(Expression.xmlFileSent)) {
							convertStringToDoc(inputLine);
						} else if ((inputLine.substring(0, 8).equals(Expression.notifyAlive))) {
							data.processNotifyAddList(inputLine);
							data.removeFromList(inputLine);
							frame.getNumberStudentsField().setText("" + data.getNumberStudentsConnected());
						} else if((inputLine.substring(0, 9).equals(Expression.sendAnswers))) {
							frame.setStudentAnswers(inputLine);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			

		} catch (IOException e) {
			System.err.println("Erro na ligação " + e.getMessage());
		}
	}

	// função para receber mensagens do server, tem de ter uma thread por o método
	// readline é bloqueante
	private void checkInput() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (;;) {
					try {
						Thread.sleep(1000);
						if (xmlProtocol != "") {
							out.println(xmlProtocol);
							xmlProtocol = "";
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	private void createFrame() {
		frame = new ProfFrameGestor(data);
		frame.showFrame();
	}

	protected String getHost() {
		return this.host;
	}

	protected int getPort() {
		return this.port;
	}

	protected void setHost(String host) {
		this.host = host;
	}

	protected void setPort(int port) {
		this.port = port;
	}

	private void convertStringToDoc(String xml) {
		doc = XMLReadWrite.documentFromString(xml);
	}

	public static Document getDocument() {
		return doc;
	}

}
