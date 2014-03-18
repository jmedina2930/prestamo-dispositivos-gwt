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
package co.edu.udea.prestamoDispositivos.client.server;

import java.util.Date;
import java.util.List;

import co.edu.udea.PrestamoDispositivos.model.Dispositivo;
import co.edu.udea.PrestamoDispositivos.util.exception.PrestamoDispositivoException;
import co.edu.udea.prestamoDispositivos.shared.DispositivoListado;
import co.edu.udea.prestamoDispositivos.shared.MyException;
import co.edu.udea.prestamoDispositivos.shared.PrestamosListado;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * esta interface se usa para definir los metodos de la logica del negocio
 * @author Jonathan, Andres Ortiz
 *
 */
@RemoteServiceRelativePath("PrestamoRemoteService")
public interface PrestamoRemoteService extends RemoteService {
	/**
	 * Utility class for simplifying access to the instance of async service.
	 */
	public static class Util {
		private static PrestamoRemoteServiceAsync instance;
		public static PrestamoRemoteServiceAsync getInstance(){
			if (instance == null) {
				instance = GWT.create(PrestamoRemoteService.class);
			}
			return instance;
		}
	}

	/**
	 * declaracion del metodo que sera usado para guardar un prestamo
	 * @param nUsuario es el nombre del usuario que realiza el prestamo
	 * @param fecha_inicial es la fecha inicial del prestamo
	 * @param fecha_final es la fecha final del prestamo
	 * @param dispositivo es el dispositivo que se desea prestar
	 * @throws MyException clase que captura las excepciones
	 */
	void guardarPrestamo(String nUsuario, Date fecha_inicial, Date fecha_final, Integer dispositivo) throws MyException;

	/**
	 * declaracion del metodo que sera usado para ver los prestamos pendientes
	 * @return la lista con los dispositivos pendientes
	 * @throws MyException clase que captura las excepciones
	 */
	List<PrestamosListado> verPrestamosPendientes() throws MyException;
	
	/**
	 * declaracion del metodo que sera usado para obtener la lista de dispositivos
	 * @return la lista de los dispositivos
	 * @throws MyException clase que captura las excepciones
	 */
	List<DispositivoListado> obtenerDispositivos() throws MyException;
}
