package org.iesalandalus.programacion.biblioteca.mvc.controlador;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.IModelo;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Curso;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;
import org.iesalandalus.programacion.biblioteca.mvc.vista.IVista;

public class Controlador  implements IControlador{

	/*********ATRIBUTOS*********/
	
	private IVista vista;
	private IModelo modelo;
	
	
	/*******CONSTRUCTOR*******/
	
	/**
	 * Constructor con parámetros.
	 * @param modelo
	 * @param vista
	 */
	public Controlador(IModelo modelo, IVista vista) {
		if (modelo == null) {
			throw new IllegalArgumentException("ERROR: El modelo no puede ser nulo.");
		}
		if (vista == null) {
			throw new IllegalArgumentException("ERROR: La vista no puede ser nula.");
		}
		
		this.modelo = modelo;
		this.vista = vista;
		this.vista.setControlador(this);
	}
	
	
	/********OTROS MÉTODOS********/
	
	/**
	 * Método que llama a otro de la clase Vista para ejecutar la aplicación. 
	 */
	public void comenzar() {
		vista.comenzar();
	}
	
	/**
	 * Método que llama a otro de la clase Vista para terminar la aplicación. 
	 */
	public void terminar() {
		System.out.println("¡Hasta pronto!");
	}
	
	/**
	 * Método que llama a otro de la clase Modelo para insertar un alumno. 
	 * @param alumno
	 * @throws OperationNotSupportedException
	 */
	public void insertar(Alumno alumno) throws OperationNotSupportedException {
		modelo.insertar(alumno);
	}
	
	/**
	 * Método que llama a otro de la clase Modelo para insertar un libro. 
	 * @param libro
	 * @throws OperationNotSupportedException
	 */
	public void insertar(Libro libro) throws OperationNotSupportedException {
		modelo.insertar(libro);
	}
	
	/**
	 * Método que llama a otro de la clase Modelo para realizar un préstamo. 
	 * @param prestamo
	 * @throws OperationNotSupportedException
	 */
	public void prestar(Prestamo prestamo) throws OperationNotSupportedException {
		modelo.prestar(prestamo);
	}
	
	/**
	 * Método que llama a otro de la clase Modelo para realizar una devolución. 
	 * @param prestamo
	 * @param fechaDevolucion
	 * @throws OperationNotSupportedException
	 */
	public void devolver(Prestamo prestamo, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		modelo.devolver(prestamo, fechaDevolucion);
	}
	
	/**
	 * Método que llama a otro de la clase Modelo para buscar un alumno. 
	 * @param alumno
	 * @return alumno
	 */
	public Alumno buscar(Alumno alumno) {
		return modelo.buscar(alumno);
	}
	
	/**
	 * Método que llama a otro de la clase Modelo para buscar un libro. 
	 * @param libro
	 * @return libro
	 */
	public Libro buscar(Libro libro) {
		return modelo.buscar(libro);
	}
	
	/**
	 * Método que llama a otro de la clase Modelo para buscar un préstamo. 
	 * @param prestamo
	 * @return prestamo
	 */
	public Prestamo buscar(Prestamo prestamo) {
		return modelo.buscar(prestamo);
	}
	
	/**
	 * Método que llama a otro de la clase Modelo para borrar un alumno. 
	 * @param alumno
	 * @throws OperationNotSupportedException
	 */
	public void borrar(Alumno alumno) throws OperationNotSupportedException {
		modelo.borrar(alumno);
	}
	
	/**
	 * Método que llama a otro de la clase Modelo para borrar un libro. 
	 * @param libro
	 * @throws OperationNotSupportedException
	 */
	public void borrar(Libro libro) throws OperationNotSupportedException {
		modelo.borrar(libro);
	}
	
	/**
	 * Método que llama a otro de la clase Modelo para borrar un préstamo. 
	 * @param prestamo
	 * @throws OperationNotSupportedException
	 */
	public void borrar(Prestamo prestamo) throws OperationNotSupportedException {
		modelo.borrar(prestamo);
	}
	
	/**
	 * Método que llama a otro de la clase Modelo para mostrar los alumnos. 
	 * @return alumnos
	 */
	public List<Alumno> getAlumnos() {
		return modelo.getAlumnos();
	}
	
	/**
	 * Método que llama a otro de la clase Modelo para mostrar los libros. 
	 * @return libros
	 */
	public List<Libro> getLibros() {
		return modelo.getLibros();
	}
	
	/**
	 * Método que llama a otro de la clase Modelo para mostrar los préstamos. 
	 * @return prestamos
	 */
	public List<Prestamo> getPrestamos() {
		return modelo.getPrestamos();
	}
	
	/**
	 * Método que llama a otro de la clase Modelo para mostrar los préstamos de un alumno. 
	 * @param alumno
	 * @return prestamos
	 */
	public List<Prestamo> getPrestamos(Alumno alumno) {
		return modelo.getPrestamos(alumno);
	}
	
	/**
	 * Método que llama a otro de la clase Modelo para mostrar los préstamos de un libro. 
	 * @param libro
	 * @return prestamos
	 */
	public List<Prestamo> getPrestamos(Libro libro) {
		return modelo.getPrestamos(libro);
	}
	
	/**
	 * Método que llama a otro de la clase Modelo para mostrar los préstamos por fecha. 
	 * @param fecha
	 * @return prestamos
	 */
	public List<Prestamo> getPrestamos(LocalDate fechaPrestamo) {
		return modelo.getPrestamos(fechaPrestamo);
	}
	
	/**
	 * Método que llama a otro de la clase Modelo para mostrar las estadísticas mensuales.
	 * @param fecha
	 * @return getEstadisticaMensualPorCurso
	 */
	public Map<Curso, Integer> getEstadisticaMensualPorCurso(LocalDate fecha) {
		return modelo.getEstadisticaMensualPorCurso(fecha);
	}
}
