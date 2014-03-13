package co.edu.udea.prestamoDispositivos.client.server;

import java.util.Date;
import java.util.List;

import co.edu.udea.PrestamoDispositivos.model.Dispositivo;
import co.edu.udea.prestamoDispositivos.shared.DispositivoListado;
import co.edu.udea.prestamoDispositivos.shared.PrestamosListado;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PrestamoRemoteServiceAsync {
	void guardarPrestamo(String nUsuario, Date fecha_inicial, Date fecha_final,
			Integer dispositivo, AsyncCallback<Void> callback);
	
	void verPrestamosPendientes(AsyncCallback<List<PrestamosListado>> callback);

	void obtenerDispositivos(AsyncCallback<List<DispositivoListado>> callback);

	
}
