package co.edu.udea.prestamoDispositivos.client;

import co.edu.udea.prestamoDispositivos.shared.UsuarioGWT;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SolicitarPrestamo extends Composite {
	
	private UsuarioGWT user;
	PrestamosPendientes prestamosPendiente;

	public SolicitarPrestamo(PrestamosPendientes prestamosPendientes){
		this.prestamosPendiente = prestamosPendientes;
		new UsuarioGWT();
		user = UsuarioGWT.getInstancia();		
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);

		Label output = new Label();
		Button solicitarPrestamo = new Button();
		solicitarPrestamo.setText("Solicitar prestamo");
		output.setText("Hemos llegado");
		verticalPanel.add(output);
		verticalPanel.add(solicitarPrestamo);
		
		if (user.getRol().equalsIgnoreCase("administrador")){			
			System.out.println(user.getRol());
			mostrarPrestamosPendientes();
		}

	}
private void mostrarPrestamosPendientes(){		
		prestamosPendiente.setVisible(true);
//		this.setVisible(false);
	}
}
