package clienteProf;

import cliente.Cliente;

public class RunProgram {

	public static void main(String[] args) {
		ClienteProfessor prof = new ClienteProfessor();

		if (args.length > 0)
			prof.setHost(args[0]);

		if (args.length > 1) {
			try {
				prof.setPort(Integer.parseInt(args[1]));
				if (prof.getPort() < 1 || prof.getPort() > 65535)
					prof.setPort(Cliente.DEFAULT_PORT);
			} catch (NumberFormatException e) {
				System.err.println("Erro no porto indicado");
			}
		}
	}

}
