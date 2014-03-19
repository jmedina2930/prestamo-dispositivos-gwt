package co.edu.udea.prestamoDispositivos.shared;

import java.io.Serializable;

/**
 * esta clase se utiliza para mapear el DTO de dispositivo del proyecto de spring
 * @author Jonathan
 *
 */

public class DispositivoListado implements Serializable {

	/**
	 * identificador de dispositvo
	 */
	private Integer id_dispositivo;
	/**
	 *  informacion relevante del dispositivo
	 */
	private String descripcion;
	/**
	 *  indica si dispositivo esta disponible.
	 */
	private String estado;
	/**
	 * 
	 * @return id_dispositivo
	 */
	public Integer getId_dispositivo() {
		return id_dispositivo;
	}
	/**
	 * Asigna el id del dispositivo
	 * @param id_dispositivo
	 */
	public void setId_dispositivo(Integer id_dispositivo) {
		this.id_dispositivo = id_dispositivo;
	}
	/**
	 * 
	 * @return la descripcion del dispositivo
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * asigna la descripcion del dispositivo
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 * 
	 * @return el estado del dispositivo
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * asigna el estado en el dispositivo
	 * @param estado
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
}
