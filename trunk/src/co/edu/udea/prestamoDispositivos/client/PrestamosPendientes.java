package co.edu.udea.prestamoDispositivos.client;

import java.util.List;

import co.edu.udea.prestamoDispositivos.client.server.PrestamoRemoteService;
import co.edu.udea.prestamoDispositivos.shared.PrestamosListado;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * esta clase es la vista de los prestamos pendientes
 * @author Jonathan
 *
 */
public class PrestamosPendientes extends Composite{

	SolicitarPrestamo solicitarPrestamo;	
	FlexTable tabla = new FlexTable();
	
	/**
	 * constructor de la clase, en este se crean los widgets y demas componentes usados en la vista
	 */
	public PrestamosPendientes(){
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);		
		Label output = new Label();
		output.setText("Solicitudes de pr\u00E9stamos pendientes");
		verticalPanel.add(output);
		verticalPanel.add(tabla);
		
		
		tabla.setText(0, 0, "C\u00F3digo");
		tabla.setText(0, 1, "Id dispositivo");
		tabla.setText(0, 2, "Nombre dispositivo");
		tabla.setText(0, 3, "Usuario");
		tabla.setText(0, 4, "Fecha inicial");
		tabla.setText(0, 5, "Fecha final");		
		
		
		
		tabla.getCellFormatter().setWidth(0, 0, "100px");
		tabla.getCellFormatter().setWidth(0, 1, "80px");
		tabla.getCellFormatter().setWidth(0, 2, "200px");
		tabla.getCellFormatter().setWidth(0, 3, "100px");
		tabla.getCellFormatter().setWidth(0, 4, "200px");
		tabla.getCellFormatter().setWidth(0, 5, "200px");
		
		tabla.getRowFormatter().addStyleName(0, "tabla.header");
		
		//llamado al metodo asincrono ver prestamos pendientes
		PrestamoRemoteService.Util.getInstance().verPrestamosPendientes(new AsyncCallback<List<PrestamosListado>>(){
			
			@Override
			public void onSuccess(List<PrestamosListado> result) {
				
				if(result.size() == 0)
					mostrarMensaje("No hay prestamos pendientes en la base de datos");
				
				for(PrestamosListado prestamo : result){					
					agregarPrestamo(prestamo);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {				
				mostrarMensaje(caught.getMessage());
			}
		});
	}
	
	/**
	 * metodo usado para mostrar una ventana con un mensaje
	 * @param mensaje que se desea mostrar
	 */
	private void mostrarMensaje(String mensaje){
		Window.alert(mensaje);
	}
	
	/**
	 * metodo usado para asignar una solicitud de un prestamo
	 * @param solicitarPrestamo solicitud de prestamo que se desea asignar
	 */
	public void setSolicitarPrestamo(SolicitarPrestamo solicitarPrestamo){
		this.solicitarPrestamo = solicitarPrestamo;
	}
	
	/**
	 * metodo usado para agregar un prestamo
	 * @param prestamo es el prestamo que se desea agregar
	 */
	public void agregarPrestamo(final PrestamosListado prestamo){		
		final int indice = tabla.getRowCount();
				
		tabla.setText(indice, 0, prestamo.getCodigo_prestamo().toString());
		tabla.setText(indice, 1, prestamo.getId_dispositivo().toString());
		tabla.setText(indice, 2, prestamo.getDescripcion_dispositivo().toString());
		tabla.setText(indice, 3, prestamo.getUsuario().toString());
		tabla.setText(indice, 4, prestamo.getFecha_inicial().toString());
		tabla.setText(indice, 5, prestamo.getFecha_final().toString());
				
	}
}
