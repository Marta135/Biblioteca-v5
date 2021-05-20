package org.iesalandalus.programacion.biblioteca.mvc.vista;

import org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.VistaIUGPestanas;
import org.iesalandalus.programacion.biblioteca.mvc.vista.texto.VistaTexto;

/**
 * 
 * @author Marta García
 * versión: Biblioteca_v4
 *
 */

public enum FactoriaVista {

	TEXTO {
		@Override
		public IVista crear() {
			return new VistaTexto();
		}
	},
	
	IUGPESTANAS {

		@Override
		public IVista crear() {
			return new VistaIUGPestanas();
		}
	};
	
	public abstract IVista crear();
}
