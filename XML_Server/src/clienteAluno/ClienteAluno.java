package clienteAluno;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import PathAndExpressions.Expression;
import cliente.Cliente;
import guiAluno.AlunoGestor;

public class ClienteAluno {

	public static String xmlQuest = "";
	private String host;
	private int port;
	private Socket socket;
	private BufferedReader in = null;
	private PrintWriter out = null;
	private AlunoGestor frame;
	private DataDynamicAluno data;

	public ClienteAluno() {
		host = Cliente.DEFAULT_HOSTNAME;
		port = Cliente.DEFAULT_PORT;
		data = new DataDynamicAluno();
		createFrame();

		createSocket();
	}

	private void createSocket() {

		try {
			socket = new Socket(host, port);

			out = new PrintWriter(socket.getOutputStream(), true);// Stream para escrita no socket
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));// Stream para leitura do socket

			createSocketWithThread();
			
			// função para receber mensagens do server, tem de ter uma thread por o método
			// readline é bloqueante
			String inputLine;
			for (;;) {
				try {
					if ((inputLine = in.readLine()) != null) {
						System.out.println("Recebi -> " + inputLine);
						if(inputLine.equals(Expression.increaseQuestionIndex))
							data.questionAnswered();
						
						else if(inputLine.substring(0,10).equals(Expression.sendResultOfQuestions)) {
							try {
								Thread.sleep(100);
								frame.setResultTextToBoard(inputLine);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							
							
						}
						else 
							frame.setResquestOfQuestion(inputLine);
						
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		} catch (IOException e) {
			System.err.println("Erro na ligação " + e.getMessage());
		}

	}
	//função para mandar mensagens para o server
	private void createSocketWithThread() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (;;) {
					try {
						Thread.sleep(10);
						if (xmlQuest != "") {
							System.out.println(xmlQuest);
							out.println(xmlQuest);
							xmlQuest = "";
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		}).start();
	}

	private void createFrame() {
		frame = new AlunoGestor(data);
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

}
