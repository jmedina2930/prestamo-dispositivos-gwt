package co.edu.udea.prestamoDispositivos.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.jdt.internal.core.util.SimpleWordSet;

import co.edu.udea.PrestamoDispositivos.model.Dispositivo;
import co.edu.udea.prestamoDispositivos.client.server.PrestamoRemoteService;
import co.edu.udea.prestamoDispositivos.shared.DispositivoListado;
import co.edu.udea.prestamoDispositivos.shared.PrestamosListado;
import co.edu.udea.prestamoDispositivos.shared.UsuarioGWT;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.google.gwt.core.client.*;
import com.google.gwt.dev.jjs.impl.Simplifier;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;

/**
 * esta clase es la vista para solicitar prestamo
 * @author Jonathan
 *
 */
public class SolicitarPrestamo extends Composite {
	
	//Atributos
	private UsuarioGWT user;
	PrestamosPendientes prestamosPendiente;
	ListBox lBHoraInicial;
	ListBox lBHoraFinal;
	ListBox lBdispositivos;
	List<Integer> lIdDispositivos = new ArrayList<>();
	Date date;

	/**
	 * constructor de la clase, en el se crear los componentes para la vista
	 * @param prestamosPendientes es el prestamo que se acaba de solicitar
	 */
	public SolicitarPrestamo(PrestamosPendientes prestamosPendientes){		
		this.prestamosPendiente = prestamosPendientes;
		FlexTable tabla = new FlexTable();
		lBdispositivos = new ListBox();
		final TextBox textFecha = new TextBox();
		lBHoraInicial = new ListBox();
		lBHoraFinal = new ListBox();
		final DatePicker calendario = new DatePicker();	
		final PopupPanel simplePopup = new PopupPanel(true);
		simplePopup.setWidget(calendario);
		
		user = UsuarioGWT.getInstancia();
		Button btnSolicitarPrestamo = new Button();
		
		//btnSolicitarPrestamo.addStyleName(getStyleName());
		
		btnSolicitarPrestamo.setText("Solicitar prestamo");
		
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);		
		
		
		llenarDispositivos();
		llenarHorarios();
		
		
		tabla.setText(0, 0, "Dispositivo");
		tabla.setWidget(0, 1, lBdispositivos);
		tabla.setText(1, 0, "Fecha");
		tabla.setWidget(1, 1, textFecha);
//		tabla.setWidget(1, 2, calendario);
		tabla.setText(2, 0, "Hora Inicial");
		tabla.setWidget(2, 1, lBHoraInicial);
		tabla.setText(2, 2, "Hora Final");
		tabla.setWidget(2, 3, lBHoraFinal);		
		tabla.setWidget(3, 2, btnSolicitarPrestamo);
			
		//agregamos la tabla con los componentes al panel		
		verticalPanel.add(tabla);
		
		verticalPanel.add(btnSolicitarPrestamo);
		
		//metodo para mostrar un Popup con el calendario
		textFecha.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {	
				Widget source = (Widget) event.getSource();
	            int left = source.getAbsoluteLeft() + 10;
	            int top = source.getAbsoluteTop() + 10;
	            simplePopup.setPopupPosition(left, top);
	            simplePopup.show();

				
			}
		});

		calendario.addValueChangeHandler(new ValueChangeHandler<Date>() {
		      public void onValueChange(ValueChangeEvent<Date> event) {
		        date = event.getValue();
		        String dateString = DateTimeFormat.getFormat("yyyy-MM-dd").format(date);
		        textFecha.setText(dateString);
		        simplePopup.hide();
		      }
		 });
		
		//llamada al evento del boton guardar prestamo
		btnSolicitarPrestamo.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Date fechaIni = new Date();
				Date fechaFin = new Date();
				
				if(lBHoraInicial.getSelectedIndex()>=lBHoraFinal.getSelectedIndex()){
					
					Window.alert("la hora inicial no puede ser mayor que la hora final");
					return;					
				}
				if (lBHoraInicial.getSelectedIndex()<lBHoraFinal.getSelectedIndex()-8){
					Window.alert("El prestamo no puede ser mayor a 8 horas");
					return;
				}
				if("".equals(textFecha.getText())){
					Window.alert("Por favor elija una fecha");
					return;
				}
							
				
				int idDispositivo = lIdDispositivos.get(lBdispositivos.getSelectedIndex());
				
							
				
				
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
				
				//llamada al metodo asincrono de guardar prestamo
				PrestamoRemoteService.Util.getInstance().guardarPrestamo(user.getUsuario(), fechaIni, fechaFin,
						idDispositivo ,new AsyncCallback<Void>() {
							
							@Override
							public void onSuccess(Void result) {
								Window.alert("la solicitud de prestamo ha sido enviada satisfactoriamente");				

								
							}
							
							@Override
							public void onFailure(Throwable caught) {
								Window.alert(caught.getMessage());
							}
						});
			}
		});
		
		
		
		
	}

	/**
	 * metodo usado para llenar los listBox con las horas
	 */
	public void llenarHorarios(){
		
		String item ="";
		int horas = 24;
		for(int i=0; i<horas;i++){
			
			item = i+":00";
			lBHoraInicial.addItem(item);
			lBHoraFinal.addItem(item);
		}
	}
	
	
	/**
	 * metodo usado para llenar el listBox con los dispositivos
	 */
	public void llenarDispositivos(){
		
			PrestamoRemoteService.Util.getInstance().obtenerDispositivos(new AsyncCallback<List<DispositivoListado>>(){
			
			@Override
			public void onSuccess(List<DispositivoListado> result) {
				
				if(result.size() == 0)
					Window.alert("No hay dispositivos en la base de datos");
				
				for(DispositivoListado dispositivo : result){					
					lBdispositivos.addItem(dispositivo.getDescripcion());
					lIdDispositivos.add(dispositivo.getId_dispositivo());
					
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {				
				Window.alert(caught.getMessage());
			}
		});
		
		
	}
}
