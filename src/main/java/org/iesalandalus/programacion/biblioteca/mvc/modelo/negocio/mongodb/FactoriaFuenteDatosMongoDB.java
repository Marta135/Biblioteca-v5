package org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.mongodb;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.IFuenteDatos;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.IAlumnos;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.ILibros;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.IPrestamos;

public class FactoriaFuenteDatosMongoDB implements IFuenteDatos{

	@Override
	public IAlumnos crearAlumnos() {
		return Alumnos();
	}

	@Override
	public ILibros crearLibros() {
		return Libros();
	}

	@Override
	public IPrestamos crearPrestamos() {
		return Prestamos();
	}

}
