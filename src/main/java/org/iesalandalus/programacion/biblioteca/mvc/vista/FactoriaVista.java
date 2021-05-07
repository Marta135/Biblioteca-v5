package org.iesalandalus.programacion.biblioteca.mvc.vista;

import org.iesalandalus.programacion.biblioteca.mvc.vista.texto.VistaTexto;

/**
 * 
 * @author Marta García
 * versión: v3
 *
 */

public enum FactoriaVista {

	TEXTO {
		@Override
		public IVista crear() {
			return new VistaTexto();
		}
	};
	
	public abstract IVista crear();
}
