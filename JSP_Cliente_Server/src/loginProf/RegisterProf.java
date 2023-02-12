package loginProf;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import answersCorrection.ValidateXmlWithXSD;
import loginProf.xmlMessageConvert.MessagesConverter;
import xmlWriter.XMLReadWrite;

/**
 * Servlet implementation class registerProf
 */
@WebServlet(description = "Permite registar um professor na base de dados", urlPatterns = {
		"/handleNewProf"})
public class RegisterProf extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String AccessCredential = "isel";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String userName = request.getParameter("username");
		String pass1 = request.getParameter("password");
		String pass2 = request.getParameter("verify_password");
		String credential = request.getParameter("credential");

		HttpSession session = request.getSession(false);
		if (session == null) {
			session = request.getSession();
		}
		
		if(validateData(userName,pass1,pass2) && credential.equals(AccessCredential)) {
			String xmlStr = MessagesConverter.resultRegisterProfXML(userName, pass1);
			session.setAttribute("registerResult", convertMsnToOutput(MessagesConverter.getResponseFromServer(xmlStr)));
			getServletContext().getRequestDispatcher("/TemplatesProf/TemplateCriarNovoProf.jsp").forward(request,
					response);
		}else {
			session.setAttribute("registerResult","Failure register your account");
			getServletContext().getRequestDispatcher("/TemplatesProf/TemplateCriarNovoProf.jsp").forward(request,
					response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private String convertMsnToOutput(String xml) {
		String result = XMLReadWrite.documentFromString(xml).getElementsByTagName("newprof").item(0).getTextContent();
		
		if(result.equals("sucesso") && ValidateXmlWithXSD.validateNewProf(xml)) {
			return "Success register your account";
		}
		return "Failure register your account";
	}
	
	private boolean validateData(String username, String pass, String repeat) {
		if(LoginFormGestor.checkUsername(username) && LoginFormGestor.checkPassword(pass) && LoginFormGestor.checkPassword(repeat)) {
			return true;
		}
		return false;
	}

}
