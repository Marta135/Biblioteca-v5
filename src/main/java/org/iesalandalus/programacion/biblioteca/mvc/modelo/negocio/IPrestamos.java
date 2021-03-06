package org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Curso;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;

public interface IPrestamos {

	List<Prestamo> get() throws NullPointerException, IllegalArgumentException;
	
	int getTamano();
	
	List<Prestamo> get(Alumno alumno) throws NullPointerException, IllegalArgumentException;
	
	List<Prestamo> get(Libro libro) throws NullPointerException, IllegalArgumentException;
	
	List<Prestamo> get(LocalDate fechaPrestamo) throws NullPointerException, IllegalArgumentException;
	
	Map<Curso, Integer> getEstadisticaMensualPorCurso(LocalDate fecha);
	
	void prestar(Prestamo prestamo) throws OperationNotSupportedException, NullPointerException, IllegalArgumentException;
	
	void devolver(Prestamo prestamo, LocalDate fechaDevolucion) throws OperationNotSupportedException, NullPointerException, IllegalArgumentException;
	
	Prestamo buscar(Prestamo prestamo) throws NullPointerException, IllegalArgumentException;
	
	void borrar(Prestamo prestamo) throws OperationNotSupportedException;
	
}
