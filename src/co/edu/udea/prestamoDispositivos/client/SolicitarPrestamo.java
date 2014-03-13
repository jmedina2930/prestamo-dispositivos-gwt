package co.edu.udea.prestamoDispositivos.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.edu.udea.PrestamoDispositivos.model.Dispositivo;
import co.edu.udea.prestamoDispositivos.client.server.PrestamoRemoteService;
import co.edu.udea.prestamoDispositivos.shared.DispositivoListado;
import co.edu.udea.prestamoDispositivos.shared.PrestamosListado;
import co.edu.udea.prestamoDispositivos.shared.UsuarioGWT;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.google.gwt.core.client.*;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;

public class SolicitarPrestamo extends Composite {
	
	private UsuarioGWT user;
	PrestamosPendientes prestamosPendiente;
	ListBox lBHoraInicial;
	ListBox lBHoraFinal;
	ListBox lBdispositivos;
	List<Integer> lIdDispositivos = new ArrayList<>();
	Date date;

	public SolicitarPrestamo(PrestamosPendientes prestamosPendientes){
		
		this.prestamosPendiente = prestamosPendientes;
		FlexTable tabla = new FlexTable();
		lBdispositivos = new ListBox();
		final TextBox textFecha = new TextBox();
		lBHoraInicial = new ListBox();
		lBHoraFinal = new ListBox();
		final DatePicker calendario = new DatePicker();		
		new UsuarioGWT();
		user = UsuarioGWT.getInstancia();
		Button btnSolicitarPrestamo = new Button();
		
		btnSolicitarPrestamo.setText("Solicitar prestamo");
		
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		
		llenarDispositivos();
		llenarHorarios();
		
		
		tabla.setText(0, 0, "Dispositivo");
		tabla.setWidget(0, 1, lBdispositivos);
		tabla.setText(1, 0, "Fecha");
		tabla.setWidget(1, 1, textFecha);
		tabla.setWidget(1, 2, calendario);
		tabla.setText(2, 0, "Hora Inicial");
		tabla.setWidget(2, 1, lBHoraInicial);
		tabla.setText(2, 2, "Hora Final");
		tabla.setWidget(2, 3, lBHoraFinal);		
		tabla.setWidget(3, 2, btnSolicitarPrestamo);
			
		//agregamos la tabla con los componentes al panel		
		verticalPanel.add(tabla);
		
		verticalPanel.add(btnSolicitarPrestamo);
		
//		if (user.getRol().equalsIgnoreCase("administrador")){			
//			System.out.println(user.getRol());
//			mostrarPrestamosPendientes();
//		}

		calendario.addValueChangeHandler(new ValueChangeHandler<Date>() {
		      public void onValueChange(ValueChangeEvent<Date> event) {
		        date = event.getValue();
		        String dateString = DateTimeFormat.getFormat("yyyy-MM-dd").format(date);
		        textFecha.setText(dateString);
		      }
		 });
		
		btnSolicitarPrestamo.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Date fechaIni = new Date();
				Date fechaFin = new Date();
				
				if(lBHoraInicial.getSelectedIndex()>lBHoraFinal.getSelectedIndex()){
					
					Window.alert("la hora inicial no puede ser mayor que la hora final");
					return;
					
				}				
				if("".equals(textFecha.getText())){
					Window.alert("Por favor elija una fecha");
					return;
				}
							
				
				int idDispositivo = lIdDispositivos.get(lBdispositivos.getSelectedIndex());
				
				System.out.println("el id del dispositivo seleccionado es"+idDispositivo);			
				
				
				int horaInicial = lBHoraInicial.getSelectedIndex();
				int HoraFinal = lBHoraFinal.getSelectedIndex();
				int minutos = 0;
				
				
				fechaIni.setYear(date.getYear());
				fechaIni.setMonth(date.getMonth());
				fechaIni.setDate(date.getDate());	
				fechaFin.setYear(date.getYear());
				fechaFin.setMonth(date.getMonth());
				fechaFin.setDate(date.getDate());	
				fechaIni.setHours(horaInicial);
				fechaIni.setMinutes(minutos);
				fechaFin.setHours(HoraFinal);
				fechaFin.setMinutes(minutos);	
				
				
				String dateString1 = DateTimeFormat.getFormat("yyyyMMddhhmm").format(fechaIni);
				String dateString2 = DateTimeFormat.getFormat("yyyyMMddhhmm").format(fechaFin);
				
				System.out.println(horaInicial);
				System.out.println(HoraFinal);
				System.out.println(dateString1);
				System.out.println(dateString2);
				
				
				PrestamoRemoteService.Util.getInstance().guardarPrestamo(user.getUsuario(), fechaIni, fechaFin,
						idDispositivo ,new AsyncCallback<Void>() {
							
							@Override
							public void onSuccess(Void result) {
								Window.alert("la solicitud de prestamo ha sido enviada satisfactoriamente");
								
//								ClienteListado cl = new ClienteListado();
//								cl.setCedula(txtCedula.getText());
//								cl.setNombreCompleto(txtNombres.getText() + " " + txtApellidos.getText());
//								cl.setCorreo(txtCorreo.getText());
//								
//								txtCedula.setText("");
//								txtNombres.setText(""); 
//								txtApellidos.setText("");
//								txtCorreo.setText("");
//								
//								
//								lista.agregarCliente(cl);
//								mostrarListado();
								
							}
							
							@Override
							public void onFailure(Throwable caught) {
								Window.alert(caught.getMessage());
							}
						});
			}
		});
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	private void mostrarPrestamosPendientes(){		
		prestamosPendiente.setVisible(true);
//		this.setVisible(false);
	}

	public void llenarHorarios(){
		
		String item ="";
		int horas = 24;
		for(int i=0; i<horas;i++){
			
			item = i+":00";
			lBHoraInicial.addItem(item);
			lBHoraFinal.addItem(item);
		}
	}
	
	
	
	public void llenarDispositivos(){
		
			PrestamoRemoteService.Util.getInstance().obtenerDispositivos(new AsyncCallback<List<DispositivoListado>>(){
			
			@Override
			public void onSuccess(List<DispositivoListado> result) {
				
				if(result.size() == 0)
					Window.alert("No hay dispositivos en la base de datos");
				
				for(DispositivoListado dispositivo : result){
					System.out.println("llegamos aqui");
					lBdispositivos.addItem(dispositivo.getDescripcion());
					lIdDispositivos.add(dispositivo.getId_dispositivo());
					
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("error");
				Window.alert(caught.getMessage());
			}
		});
		
		
	}
}
