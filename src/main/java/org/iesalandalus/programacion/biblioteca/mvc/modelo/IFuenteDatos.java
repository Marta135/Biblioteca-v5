package org.iesalandalus.programacion.biblioteca.mvc.modelo;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.IAlumnos;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.ILibros;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.IPrestamos;

/**
 * 
 * @author Marta García
 * versión: v3
 *
 */

public interface IFuenteDatos {

	IAlumnos crearAlumnos();
	
	ILibros crearLibros();	
	
	IPrestamos crearPrestamos();
	
}
