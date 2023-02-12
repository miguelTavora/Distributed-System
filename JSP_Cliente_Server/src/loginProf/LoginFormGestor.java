package loginProf;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import serverData.ServerData;

@WebServlet("/handleLoginData")
public class LoginFormGestor extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String user = request.getParameter("username");// vai buscar o name que está no input da JSP
		String pass = request.getParameter("password");

		HttpSession session = request.getSession(false);
		if (session == null) {
			session = request.getSession();

		}

		if (validateLogin(user, pass)) {
			createNewClientProf(user);// cria instancia professor fica guardado
			ServerData.getProfByName(user).sendXMLToServer(sendUserPassServer(user, pass));// envia mensagem para o servidor
			try {
				for (;;) {
					Thread.sleep(100);
					if (ServerData.getProfByName(user).getResponseReceived() != null ) {
						if (ServerData.getProfByName(user).getResponseReceived().equals("correto")) {
							ServerData.getProfByName(user).resetResponseReceived();
							session.setAttribute("username", user);
							if(session.getAttribute("existingKey") != null) {
								session.setAttribute("existingKey",null);
							}
							if(session.getAttribute("errorKey") != null) {
								session.setAttribute("errorKey",null);
							}
							getServletContext().getRequestDispatcher("/TemplatesProf/CreateRoom.jsp").forward(request,
									response);
							break;

						} else {
							ServerData.removeProfConnectedByName(user);
							session.setAttribute("usernameError", user);
							getServletContext().getRequestDispatcher("/ErrorPages/TemplateLoginProfError.jsp").forward(request,
									response);
							break;
						}
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} else {
			session.setAttribute("usernameError", user);
			getServletContext().getRequestDispatcher("/ErrorPages/TemplateLoginProfError.jsp").forward(request, response);

		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	// validações para username e password
	private boolean validateLogin(String username, String password) {
		if (checkUsername(username) && checkPassword(password)) {
			return true;
		}
		return false;
	}

	public static boolean isAlphaNumeric(String s) {
		if (s != null) {
			String pattern = "^[a-zA-Z0-9]*$";
			return s.matches(pattern);
		}
		return false;
	}
	
	public static boolean isANumer(String s) {
		if (s != null) {
			String regex = "^\\d+$";
			return s.matches(regex);
		}
		return false;
	}


	public static boolean checkUsername(String username) {
		if (isAlphaNumeric(username) && username.length() > 3 && username.length() < 25) {
			return true;
		}
		return false;
	}

	public static boolean checkPassword(String password) {
		if (isANumer(password) && password.length() > 5 && password.length() < 25) {
			return true;
		}
		return false;
	}

	private void createNewClientProf(String profName) {
		ClientServerProf prof = new ClientServerProf(profName);
		ServerData.storeProfConnected(prof);

	}

	// função para mandar os dados para o servidor para serem validados
	public String sendUserPassServer(String username, String password) {
		return "<info id=\"" + ServerData.getIndexProfByName(username) + "\" client=\"prof\">" + "<username>" + username
				+ "</username>" + "<password>" + password + "</password>" + "</info>";
	}

}
