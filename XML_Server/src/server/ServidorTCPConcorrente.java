package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import server.xml.DocumentXML;

public class ServidorTCPConcorrente {

	public final static int DEFAULT_PORT = 5025;
	private int port;
	private ServerSocket serverSocket;
	private static DocumentXML doc;
	private DataDynamicStructure data;
	
	
	public ServidorTCPConcorrente() {
		port = DEFAULT_PORT;
		try {
			doc = new DocumentXML();
		} catch (ParserConfigurationException | SAXException | IOException e1) {
			e1.printStackTrace();
		}
		data = new DataDynamicStructure();
		serverSocket = null;
		createSocketAndThread();
		
		
	
	}
	
	public int getPort() {
		return this.port;
	}
	
	public void setport(int port) {
		this.port = port;
	}
	
	private void createSocketAndThread() {
		try {
			serverSocket = new ServerSocket(getPort());
			Socket newSock = null;

			for (;;) {
				System.out.println("Servidor TCP concorrente aguarda ligacao no porto " + port + "...");

				newSock = serverSocket.accept();// Espera connect do cliente

				Thread th = new HandleConnectionThread(newSock,doc,data);
				th.start();
			}
		} catch (IOException e) {
			System.err.println("Excepção no servidor: " + e);
		}
	}
	
	protected static String getStringXML() {
		return doc.xmlDocStringFormat();
	}
	
	protected static String getStringXMLReload() {
		return doc.xmlDocStringFormatReload();
	}

}


