package org.iesalandalus.programacion.biblioteca.mvc.modelo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Curso;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;

/**
 * 
 * @author Marta García
 * versión: v3
 *
 */

public interface IModelo {

	void comenzar();
	
	void terminar();
	
	void insertar(Alumno alumno) throws OperationNotSupportedException, NullPointerException, IllegalArgumentException;
	
	void insertar(Libro libro) throws OperationNotSupportedException, NullPointerException, IllegalArgumentException;
	
	void prestar(Prestamo prestamo) throws OperationNotSupportedException, NullPointerException, IllegalArgumentException;
	
	void devolver(Prestamo prestamo, LocalDate fechaDevolucion) throws OperationNotSupportedException, NullPointerException, IllegalArgumentException;
	
	Alumno buscar(Alumno alumno) throws NullPointerException, IllegalArgumentException;
	
	Libro buscar(Libro libro) throws NullPointerException, IllegalArgumentException;
	
	Prestamo buscar(Prestamo prestamo) throws NullPointerException, IllegalArgumentException;
	
	void borrar(Alumno alumno) throws OperationNotSupportedException;
	
	void borrar(Libro libro) throws OperationNotSupportedException;
	
	void borrar(Prestamo prestamo) throws OperationNotSupportedException;
	
	List<Alumno> getAlumnos();
	
	List<Libro> getLibros();
	
	List<Prestamo> getPrestamos();
	
	List<Prestamo> getPrestamos(Alumno alumno);
	
	List<Prestamo> getPrestamos(Libro libro);
	
	List<Prestamo> getPrestamos(LocalDate fechaPrestamo);
	
	Map<Curso, Integer> getEstadisticaMensualPorCurso(LocalDate fecha);
	
	
}
