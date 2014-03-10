package co.edu.udea.prestamoDispositivos.client;

import co.edu.udea.prestamoDispositivos.shared.FieldVerifier;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PrestamoDispositivosGWT implements EntryPoint {	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		SolicitarPrestamo solicitarPrestamo = new SolicitarPrestamo();
		PrestamosPendietes prestamosPendietes = new PrestamosPendietes();
		solicitarPrestamo.setVisible(false);
		prestamosPendietes.setVisible(false);
		RootPanel.get("contenido").add(solicitarPrestamo);
		RootPanel.get("contenido").add(prestamosPendietes);
	}
}
