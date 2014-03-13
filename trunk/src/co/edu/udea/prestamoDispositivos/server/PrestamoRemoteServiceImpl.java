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

import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import co.edu.udea.PrestamoDispositivos.bl.PrestamoBL;
import co.edu.udea.PrestamoDispositivos.model.Dispositivo;
import co.edu.udea.PrestamoDispositivos.model.Prestamo;
import co.edu.udea.PrestamoDispositivos.util.exception.PrestamoDispositivoException;
import co.edu.udea.prestamoDispositivos.client.server.PrestamoRemoteService;
import co.edu.udea.prestamoDispositivos.shared.PrestamosListado;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class PrestamoRemoteServiceImpl extends RemoteServiceServlet implements PrestamoRemoteService {

	@Override
	public void guardarPrestamo(String nUsuario, Date fecha_inicial,
			Date fecha_final, String estado_prestamo, Dispositivo dispositivo) {
		
		ServletContext sc = getServletContext();
		ApplicationContext webApp = WebApplicationContextUtils.getWebApplicationContext(sc);
		
		HttpServletRequest req = this.getThreadLocalRequest();
		HttpSession ses = req.getSession();
		
		try{
			PrestamoBL prestamoBL = (PrestamoBL) webApp.getBean("prestamoBL");
			prestamoBL.guardar(nUsuario, fecha_inicial, fecha_final, estado_prestamo, dispositivo);
		}catch(PrestamoDispositivoException e){
			e.getStackTrace();
		}
		
	}

	@Override
	public List<PrestamosListado> verPrestamosPendientes() {
		List<PrestamosListado> prestamosListado = null;
		PrestamosListado prestamoL = null;
		List<Prestamo> prestamos;
		ServletContext sc = getServletContext();
		ApplicationContext webApp = WebApplicationContextUtils.getWebApplicationContext(sc);
		
		HttpServletRequest req = this.getThreadLocalRequest();
		HttpSession ses = req.getSession();
		
		try{
			PrestamoBL prestamoBL = (PrestamoBL) webApp.getBean("prestamoBL");
			prestamos = prestamoBL.verPrestamosPendientes();
			for (Prestamo prestamo : prestamos){
				prestamoL.setCodigo_prestamo(prestamo.getCodigo_prestamo());
				prestamoL.setEstado_prestamo(prestamo.getEstado_prestamo());
				prestamoL.setFecha_final(prestamo.getFecha_final());
				prestamoL.setFecha_inicial(prestamo.getFecha_inicial());
				prestamoL.setId_dispositivo(prestamo.getId_dispositivo().toString());
				prestamoL.setUsuario(prestamo.getUsuario().toString());
				prestamosListado.add(prestamoL);
			}
		}catch(PrestamoDispositivoException e){
			e.getStackTrace();
		}
		return prestamosListado;
		
	}
}
