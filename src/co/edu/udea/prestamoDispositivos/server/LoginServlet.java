package co.edu.udea.prestamoDispositivos.server;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import co.edu.udea.PrestamoDispositivos.bl.impl.UsuarioBLImpl;
import co.edu.udea.PrestamoDispositivos.model.Usuario;
import co.edu.udea.prestamoDispositivos.shared.UsuarioGWT;

public class LoginServlet extends HttpServlet {
	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		procesarLogin(request, response);
		
		String referer = request.getHeader("referer");
		
		response.sendRedirect(referer);
	}
	
	
	protected void procesarLogin(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		
		String userName = request.getParameter("usuario");
		
		
		String password = request.getParameter("pws");
		
		HttpSession sesion = request.getSession();
		
		try{
			System.out.println("Usuario: " + userName);
			if(getUsuarioService().validar(userName, password)){
				
				
				Usuario usuario = getUsuarioService().obtenerPorUsuario(userName);
				
				UsuarioGWT usuarioGWT = new UsuarioGWT();
				
				usuarioGWT.setUsuario(userName);
				usuarioGWT.setNombreCompleto(usuario.getNombre() + " " + usuario.getApellidos());
				usuarioGWT.setRol(usuario.getRol());
				
				sesion.setAttribute("UsuarioConectado", usuarioGWT);
				
			}else{
				sesion.setAttribute("UsuarioConectado", null);
				sesion.setAttribute("DatosInvalidos", "Usuario o contraseña no valido");
				System.out.println("Usuario o contraseña no valido");
			}
		}catch(Exception e){
			sesion.setAttribute("UsuarioConectado", null);
			sesion.setAttribute("DatosInvalidos", "Ocurrio un error validando los datos del usuario");
			e.printStackTrace();
		}
		
	}
	
	
	public UsuarioBLImpl getUsuarioService(){
		
		ServletContext sc = getServletContext();
		ApplicationContext context =  WebApplicationContextUtils.getWebApplicationContext(sc);
		return (UsuarioBLImpl)context.getBean("usuarioBL");
	}
	
	

}
