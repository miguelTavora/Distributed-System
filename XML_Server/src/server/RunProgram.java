package server;

public class RunProgram {

	public static void main(String[] args) {
		ServidorTCPConcorrente server = new ServidorTCPConcorrente();

		if (args.length > 0) {
			try {
				server.setport(Integer.parseInt(args[0]));
			} catch (NumberFormatException e) {
				System.err.println("Erro no porto indicado");
			}
		}

	}

}
