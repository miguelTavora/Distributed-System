package loginProf.xmlMessageConvert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import otherServerInfo.ServerInfo;

public class CreateComServer {
	
	private String xmlProtocol = "";//string que envia msn server
	private String host;
	private int port;
	private Socket socket;
	private BufferedReader in = null;
	private PrintWriter out = null;

	private String responseReceived;


	public CreateComServer() {
		host = ServerInfo.DEFAULT_HOSTNAME;// Máquina onde reside a aplicação servidora
		port = ServerInfo.DEFAULT_PORT; // Porto da aplicação servidora
		createSocket();

	}

	private void createSocket() {
		try {
			socket = new Socket(host, port);

			out = new PrintWriter(socket.getOutputStream(), true);// Stream para escrita no socket
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));// Stream para leitura do socket

			checkInput();
			new Thread(new Runnable() {
				@Override
				public void run() {
					String inputLine;
					for (;;) {
						try {
							if ((inputLine = in.readLine()) != null) {
								System.out.println("Recebi P -> " + inputLine);
								responseReceived = inputLine;
								break;
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();

		} catch (IOException e) {
			System.err.println("Erro na ligação " + e.getMessage());
		}
	}

	// função para enviar mensagens para o server
	private void checkInput() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (;;) {
					try {
						Thread.sleep(50);
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
	
	

	public void sendXMLToServer(String messageXML) {
		this.xmlProtocol = messageXML;
	}
	
	public String getResponseReceived() {
		return this.responseReceived;
	}

}
