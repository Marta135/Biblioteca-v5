package org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;

/**
 * 
 * @author Marta García
 * version: 3v
 *
 */

public interface ILibros {

	void comenzar();
	
	void terminar();
	
	List<Libro> get() throws NullPointerException, IllegalArgumentException;
	
	int getTamano();
	
	void insertar(Libro libro) throws OperationNotSupportedException, NullPointerException, IllegalArgumentException;
	
	Libro buscar(Libro libro) throws NullPointerException, IllegalArgumentException;
	
	void borrar(Libro libro) throws OperationNotSupportedException;
}
