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

public class PrestamosPendientes extends Composite{

	SolicitarPrestamo solicitarPrestamo;
	Label labelMensaje;
	FlexTable tabla = new FlexTable();
	
	public PrestamosPendientes(){
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);		
		Label output = new Label();
		output.setText("Solicitudes de pr\u00E9stamos pendientes");
		verticalPanel.add(output);
		
		tabla.setText(0, 0, "C\u00F3digo");
		tabla.setText(0, 1, "Fecha inicial");
		tabla.setText(0, 2, "Fecha final");
		tabla.setText(0, 3, "Usuario");
		tabla.setText(0, 4, "Estado");
		
		tabla.getCellFormatter().setWidth(0, 0, "100px");
		tabla.getCellFormatter().setWidth(0, 1, "200px");
		tabla.getCellFormatter().setWidth(0, 2, "200px");
		tabla.getCellFormatter().setWidth(0, 3, "50px");
		tabla.getCellFormatter().setWidth(0, 4, "50px");
		
		tabla.getRowFormatter().addStyleName(0, "tablaHeader");
		
		PrestamoRemoteService.Util.getInstance().verPrestamosPendientes(new AsyncCallback<List<PrestamosListado>>(){
			
			@Override
			public void onSuccess(List<PrestamosListado> result) {
				
				if(result.size() == 0)
					mostrarMensaje("No hay clientes en la base de datos");
				
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
	
	private void mostrarMensaje(String mensaje){
		labelMensaje.setVisible(true);
		labelMensaje.setText(mensaje);
	}
	public void setSolicitarPrestamo(SolicitarPrestamo solicitarPrestamo){
		this.solicitarPrestamo = solicitarPrestamo;
	}
	
	public void agregarPrestamo(final PrestamosListado prestamo){
		
		final int indice = tabla.getRowCount();
		
		tabla.setText(indice, 0, prestamo.getCodigo_prestamo().toString());
		tabla.setText(indice, 1, prestamo.getFecha_inicial().toString());
		tabla.setText(indice, 2, prestamo.getFecha_final().toString());
		tabla.setText(indice, 3, prestamo.getUsuario().toString());
		tabla.setText(indice, 4, prestamo.getEstado_prestamo().toString());
		
			
		
	}
}
