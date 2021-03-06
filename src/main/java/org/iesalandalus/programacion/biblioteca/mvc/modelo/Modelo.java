package org.iesalandalus.programacion.biblioteca.mvc.modelo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Curso;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.IAlumnos;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.ILibros;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.IPrestamos;

public class Modelo implements IModelo{

	/*********ATRIBUTOS*********/
	
	private IAlumnos alumnos;
	private ILibros libros;
	private IPrestamos prestamos;
	
	
	/*******CONSTRUCTOR*******/
	/**
	 * Constructor con parámetros:
	 * Acepta como parámetro una fuente de datos, crea los objetos llamando a
	 * los métodos de la interfaz IFuenteDatos. 
	 */
	public Modelo(IFuenteDatos fuenteDatos) {
		alumnos = fuenteDatos.crearAlumnos();
		libros = fuenteDatos.crearLibros();
		prestamos = fuenteDatos.crearPrestamos();
	}
	

	/********OTROS MÉTODOS********/
	
	/**
	 * Método que permite insertar un alumno.
	 * @param alumno
	 * @throws OperationNotSupportedException
	 */
	public void insertar(Alumno alumno) throws OperationNotSupportedException, NullPointerException {
		alumnos.insertar(alumno);
	}
	
	/**
	 * Método que permite insertar un libro.
	 * @param libro
	 * @throws OperationNotSupportedException
	 */
	public void insertar(Libro libro) throws OperationNotSupportedException, NullPointerException  {
		libros.insertar(libro);
	}
	
	/**
	 * Método que permite hacer un préstamo de un libro a un alumno.
	 * @param prestamo
	 * @throws OperationNotSupportedException
	 */
	public void prestar(Prestamo prestamo) throws OperationNotSupportedException {
		if (prestamo ==  null) {
			throw new NullPointerException("ERROR: No se puede prestar un préstamo nulo.");
		}
		Alumno alumno = alumnos.buscar(prestamo.getAlumno());
		if (alumno == null) {
			throw new OperationNotSupportedException("ERROR: No existe el alumno del préstamo.");
		}
		Libro libro = libros.buscar(prestamo.getLibro());
		if (libro == null) {
			throw new OperationNotSupportedException("ERROR: No existe el libro del préstamo.");
		}
		prestamos.prestar(new Prestamo(alumno, libro, prestamo.getFechaPrestamo()));
	}
	
	/**
	 * Método que premite devolver un libro.
	 * @param prestamo
	 * @param fechaDevolucion
	 * @throws OperationNotSupportedException
	 */
	public void devolver(Prestamo prestamo, LocalDate fechaDevolucion) throws OperationNotSupportedException, NullPointerException, IllegalArgumentException {
		if (prestamos.buscar(prestamo) == null) {
			throw new OperationNotSupportedException("ERROR: No se puede devolver un préstamo no prestado.");
		}
		prestamos.devolver(prestamo, fechaDevolucion);
	}
	
	/**
	 * Método que permite buscar un alumno determinado.
	 * @param alumno
	 * @return alumno
	 */
	public Alumno buscar(Alumno alumno) throws NullPointerException, IllegalArgumentException {
		return alumnos.buscar(alumno);
	}
	
	/**
	 * Método que permite buscar un libro determinado.
	 * @param libro
	 * @return libro
	 */
	public Libro buscar(Libro libro) throws NullPointerException, IllegalArgumentException {
		return libros.buscar(libro);
	}
	
	/**
	 * Método que permite buscar un préstamo determinado.
	 * @param prestamo
	 * @return prestamo
	 */
	public Prestamo buscar(Prestamo prestamo) throws NullPointerException, IllegalArgumentException {
		return prestamos.buscar(prestamo);
	}
	
	/**
	 * Método que permite eliminar un alumno de la colección.
	 * @param alumno
	 * @throws OperationNotSupportedException
	 */
	public void borrar(Alumno alumno) throws OperationNotSupportedException, IllegalArgumentException {
		List<Prestamo> prestamosAlumno = prestamos.get(alumno);
		for (Prestamo prestamo : prestamosAlumno) {
			prestamos.borrar(prestamo);
		}
		alumnos.borrar(alumno);
	}
	
	/**
	 *  Método que permite eliminar un libro de la colección.
	 * @param libro
	 * @throws OperationNotSupportedException
	 */
	public void borrar(Libro libro) throws OperationNotSupportedException, IllegalArgumentException {
		List<Prestamo> prestamosLibro = prestamos.get(libro);
		for (Prestamo prestamo : prestamosLibro) {
			prestamos.borrar(prestamo);
		}
		libros.borrar(libro);
	}
	
	/**
	 *  Método que permite borrar un préstamo.
	 * @param prestamo
	 * @throws OperationNotSupportedException
	 */
	public void borrar(Prestamo prestamo) throws OperationNotSupportedException, IllegalArgumentException {
		prestamos.borrar(prestamo);
	}
	
	/**
	 * Método que devuelve los datos de los alumnos.
	 * @return alumnos
	 */
	public List<Alumno> getAlumnos() {
		return alumnos.get();
	}
	
	/**
	 * Método que devuelve los datos de los libros prestados.
	 * @return libros
	 */
	public List<Libro> getLibros() {
		return libros.get();
	}
	
	/**
	 * Método que devuelve todos los préstamos realizados.
	 * @return prestamos
	 */
	public List<Prestamo> getPrestamos() {
		return prestamos.get();
	}
	
	/**
	 * Método que devuelve los préstamos de un alumno determinado.
	 * @param alumno
	 * @return prestamos.get(alumno)
	 */
	public List<Prestamo> getPrestamos(Alumno alumno) {
		return prestamos.get(alumno);
	}
	
	/**
	 * Método que devuelve los préstamos de un libro determinado.
	 * @param libro
	 * @return prestamos.get(libro)
	 */
	public List<Prestamo> getPrestamos(Libro libro) {
		return prestamos.get(libro);
	}
	
	/**
	 * Método que devuelve los préstamos realizados en una fecha determinada.
	 * @param fechaPrestamo
	 * @return prestamos.get(fechaPrestamo)
	 */
	public List<Prestamo> getPrestamos(LocalDate fechaPrestamo) {
		return prestamos.get(fechaPrestamo);
	}
	
	/**
	 * Método que devuelve la estadística mensual por curso.
	 * @param fecha
	 * @return getEstadisticaMensualPorCurso
	 */
	public Map<Curso, Integer> getEstadisticaMensualPorCurso(LocalDate fecha) {
		return prestamos.getEstadisticaMensualPorCurso(fecha);
	}
	
}
