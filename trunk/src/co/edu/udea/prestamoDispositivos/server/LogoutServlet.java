package co.edu.udea.prestamoDispositivos.server;

import java.io.IOException;



import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * clase usada para terminar la sesion del usuario
 * @author Andres Ortiz
 *
 */

public class LogoutServlet extends HttpServlet {


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		HttpSession session = req.getSession();
		session.setAttribute("UsuarioConectado", null);

		String referrer = req.getHeader("referer");
		resp.sendRedirect(referrer);
	}

}
