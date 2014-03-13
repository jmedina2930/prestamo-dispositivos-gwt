package co.edu.udea.prestamoDispositivos.client;

import co.edu.udea.prestamoDispositivos.shared.UsuarioGWT;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.i18n.client.Dictionary;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PrestamoDispositivosGWT implements EntryPoint {
	SolicitarPrestamo solicitarPrestamo = null;
	PrestamosPendientes prestamosPendientes = null;
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {		
		Dictionary var = Dictionary.getDictionary("userInSession");
		UsuarioGWT.setUpFromDictionary(var);
		prestamosPendientes= new PrestamosPendientes();
		solicitarPrestamo= new SolicitarPrestamo(prestamosPendientes);
		prestamosPendientes.setSolicitarPrestamo(solicitarPrestamo);
		
//		prestamosPendientes.setVisible(false);
		solicitarPrestamo.setVisible(false);
		
		RootPanel.get("contenido").add(solicitarPrestamo);
		RootPanel.get("contenido").add(prestamosPendientes);
	}
}
