package clienteAluno;

import cliente.Cliente;

public class RunProgram {
	
	public static void main(String[] args) {
		ClienteAluno aluno = new ClienteAluno();

		if (args.length > 0)
			aluno.setHost(args[0]);

		if (args.length > 1) {
			try {
				aluno.setPort(Integer.parseInt(args[1]));
				if (aluno.getPort() < 1 || aluno.getPort() > 65535)
					aluno.setPort(Cliente.DEFAULT_PORT);
			} catch (NumberFormatException e) {
				System.err.println("Erro no porto indicado");
			}
		}
	}

}
