package co.edu.udea.prestamoDispositivos.shared;

import com.google.gwt.i18n.client.Dictionary;

public class UsuarioGWT {

	private String nombreCompleto;
	private String usuario;
	private String rol;
	
	private static UsuarioGWT instancia;
	
	
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	
	public static UsuarioGWT getInstancia(){
		if(instancia == null)
			instancia = new UsuarioGWT();
		
		return instancia;
	}
	
	public static void setUpFromDictionary(Dictionary dic){
		instancia = new UsuarioGWT();
		instancia.setRol(dic.get("rol"));
		instancia.setNombreCompleto(dic.get("nombre"));
		instancia.setUsuario(dic.get("login"));
	}
}
