/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package co.edu.udea.prestamoDispositivos.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import co.edu.udea.PrestamoDispositivos.bl.DispositivoBL;
import co.edu.udea.PrestamoDispositivos.bl.PrestamoBL;
import co.edu.udea.PrestamoDispositivos.model.Dispositivo;
import co.edu.udea.PrestamoDispositivos.model.Prestamo;
import co.edu.udea.PrestamoDispositivos.util.exception.PrestamoDispositivoException;
import co.edu.udea.prestamoDispositivos.client.server.PrestamoRemoteService;
import co.edu.udea.prestamoDispositivos.shared.DispositivoListado;
import co.edu.udea.prestamoDispositivos.shared.MyException;
import co.edu.udea.prestamoDispositivos.shared.PrestamosListado;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * clase utilizada para implementar los metodos de la logica del negocio definidos en PrestamoRemoteService
 * @author Jonathan, Andres Ortiz
 *
 */
@SuppressWarnings("serial")
public class PrestamoRemoteServiceImpl extends RemoteServiceServlet implements PrestamoRemoteService {
	

	/**
	 * es la implementacion del metodo usado para guardar un prestamo
	 * 
	 */
	@Override
	public void guardarPrestamo(String nUsuario, Date fecha_inicial,
			Date fecha_final, Integer id_dispositivo) throws MyException {
		
		ServletContext sc = getServletContext();
		ApplicationContext webApp = WebApplicationContextUtils.getWebApplicationContext(sc);
		
		HttpServletRequest req = this.getThreadLocalRequest();
		HttpSession ses = req.getSession();
		
		try{
			PrestamoBL prestamoBL = (PrestamoBL) webApp.getBean("prestamoBL");
			DispositivoBL dispositivoBL = (DispositivoBL) webApp.getBean("dispositivoBL");
			Dispositivo dispositivo = dispositivoBL.obtenerPorId(id_dispositivo);
			prestamoBL.guardar(nUsuario, fecha_inicial, fecha_final, "pendiente", dispositivo);
		}catch(PrestamoDispositivoException e){
			throw new MyException(e.getMessage());
			
		}
		
	}

	/**
	 * es la implementacion del metodo usado para listar los prestamos pendientes
	 */
	@Override
	public List<PrestamosListado> verPrestamosPendientes() throws MyException {
		List<PrestamosListado> prestamosListado = new ArrayList<PrestamosListado>();		
		List<Prestamo> prestamos = null;
		ServletContext sc = getServletContext();
		ApplicationContext webApp = WebApplicationContextUtils.getWebApplicationContext(sc);
		
		HttpServletRequest req = this.getThreadLocalRequest();
		HttpSession ses = req.getSession();
		
		try{
			
			PrestamoBL prestamoBL = (PrestamoBL) webApp.getBean("prestamoBL");
			prestamos = prestamoBL.verPrestamosPendientes();
			for (Prestamo prestamo : prestamos){
				PrestamosListado prestamoL = new PrestamosListado();
				
				prestamoL.setCodigo_prestamo(prestamo.getCodigo_prestamo());
				prestamoL.setEstado_prestamo(prestamo.getEstado_prestamo());
				prestamoL.setFecha_final(prestamo.getFecha_final());
				prestamoL.setFecha_inicial(prestamo.getFecha_inicial());
				prestamoL.setId_dispositivo(prestamo.getId_dispositivo().getId_dispositivo().toString());
				prestamoL.setDescripcion_dispositivo(prestamo.getId_dispositivo().getDescripcion().toString());
				prestamoL.setUsuario(prestamo.getUsuario().getUsuario());
				prestamosListado.add(prestamoL);
			}
		}catch(PrestamoDispositivoException e){
			throw new MyException(e.getMessage());
		}
		return prestamosListado;
		
	}
	
	
/**
 * es la implementacion del metodo usado para obtener la lista de dispositivos
 */
	@Override
	public List<DispositivoListado> obtenerDispositivos() throws MyException  {
		
		List<Dispositivo> dispositivos = null;
		List<DispositivoListado> listadoDispositivos = new ArrayList<DispositivoListado>();		
		
		ServletContext sc = getServletContext();
		ApplicationContext webApp = WebApplicationContextUtils.getWebApplicationContext(sc);
		
		HttpServletRequest req = this.getThreadLocalRequest();
		HttpSession ses = req.getSession();
		
		try{			
			
			DispositivoBL dispositivoBL = (DispositivoBL) webApp.getBean("dispositivoBL");
			dispositivos = dispositivoBL.obtener();
			
			for (Dispositivo dispositivo : dispositivos){
				DispositivoListado dispositivoL = new DispositivoListado();
				
				dispositivoL.setId_dispositivo(dispositivo.getId_dispositivo());
				dispositivoL.setEstado(dispositivo.getEstado());
				dispositivoL.setDescripcion(dispositivo.getDescripcion());
				listadoDispositivos.add(dispositivoL);
			}
		}catch(PrestamoDispositivoException e){
			throw new MyException(e.getMessage());
		}
		return listadoDispositivos;
	}
}
