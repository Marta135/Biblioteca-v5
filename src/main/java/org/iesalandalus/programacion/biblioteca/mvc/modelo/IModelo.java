package org.iesalandalus.programacion.biblioteca.mvc.modelo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Curso;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;

public interface IModelo {

	void insertar(Alumno alumno) throws OperationNotSupportedException, NullPointerException;
	
	void insertar(Libro libro) throws OperationNotSupportedException, NullPointerException;
	
	void prestar(Prestamo prestamo) throws OperationNotSupportedException;
	
	void devolver(Prestamo prestamo, LocalDate fechaDevolucion) throws OperationNotSupportedException, NullPointerException, IllegalArgumentException;
	
	Alumno buscar(Alumno alumno) throws NullPointerException, IllegalArgumentException;
	
	Libro buscar(Libro libro) throws NullPointerException, IllegalArgumentException;
	
	Prestamo buscar(Prestamo prestamo) throws NullPointerException, IllegalArgumentException;
	
	void borrar(Alumno alumno) throws OperationNotSupportedException, IllegalArgumentException;
	
	void borrar(Libro libro) throws OperationNotSupportedException, IllegalArgumentException;
	
	void borrar(Prestamo prestamo) throws OperationNotSupportedException, IllegalArgumentException;
	
	List<Alumno> getAlumnos();
	
	List<Libro> getLibros();
	
	List<Prestamo> getPrestamos();
	
	List<Prestamo> getPrestamos(Alumno alumno);
	
	List<Prestamo> getPrestamos(Libro libro);
	
	List<Prestamo> getPrestamos(LocalDate fechaPrestamo);
	
	Map<Curso, Integer> getEstadisticaMensualPorCurso(LocalDate fecha);
	
	
}
