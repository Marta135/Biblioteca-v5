package org.iesalandalus.programacion.biblioteca.mvc.modelo;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.ficheros.FactoriaFuenteDatosFicheros;

/**
 * 
 * @author Marta García
 * versión: v3
 *
 */

public enum FactoriaFuenteDatos {

	FICHEROS {
		@Override
		public IFuenteDatos crear() {
			return new FactoriaFuenteDatosFicheros();
		}
	};
	
	public abstract IFuenteDatos crear();
}
