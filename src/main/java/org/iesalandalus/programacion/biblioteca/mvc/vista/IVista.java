package org.iesalandalus.programacion.biblioteca.mvc.vista;

import org.iesalandalus.programacion.biblioteca.mvc.controlador.IControlador;

/**
 * 
 * @author Marta García
 * versión: Biblioteca_v4
 *
 */

public interface IVista {

	 void setControlador(IControlador controlador);
	 
	 void comenzar();
	 
	 void terminar();
}
