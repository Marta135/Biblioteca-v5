package org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.memoria;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Curso;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.IPrestamos;

public class Prestamos implements IPrestamos{

	/*********ATRIBUTOS*********/
	
	private List<Prestamo> coleccionPrestamos;
	
	
	/*******CONSTRUCTORES*******/
	
	/**
	 * Constructor sin parámetros.
	 */
	public Prestamos() throws NullPointerException, IllegalArgumentException {
		coleccionPrestamos = new ArrayList<>();
	}

	
	/**
	 * Método que devuelve una copia de la colección.
	 * @return prestamosOrdenados
	 */
	public List<Prestamo> get() throws NullPointerException, IllegalArgumentException {
		List<Prestamo> prestamosOrdenados = copiaProfundaPrestamos();
		Comparator<Alumno> comparadorAlumno = Comparator.comparing(Alumno::getNombre);
		Comparator<Libro> comparadorLibro = Comparator.comparing(Libro::getTitulo).thenComparing(Libro::getAutor);
		Comparator<Prestamo> comparadorPrestamo = Comparator.comparing(Prestamo::getFechaPrestamo)
				.thenComparing(Prestamo::getAlumno, comparadorAlumno)
				.thenComparing(Prestamo::getLibro, comparadorLibro);
		prestamosOrdenados.sort(comparadorPrestamo);
		
		return prestamosOrdenados;
	}
	
	/**
	 * Método que devuelve una copia de la colección de alumnos.
	 * @return copiaAlumnos
	 */
	private List<Prestamo> copiaProfundaPrestamos() throws NullPointerException, IllegalArgumentException {
		List<Prestamo> copiaPrestamos = new ArrayList<>();
		for (Prestamo prestamo : coleccionPrestamos) {
			copiaPrestamos.add(new Prestamo(prestamo));
		}
		return copiaPrestamos;
	}
	
	/**
	 *  Método que devuelve el tamaño de la colección.
	 * @return coleccionPrestamos.size()
	 */
	public int getTamano() {
		return coleccionPrestamos.size();
	}
	
	
	/**
	 * Método que devuelve los préstamos realizados por un alumno.
	 * @param alumno
	 * @return prestamosAlumno
	 */
	public List<Prestamo> get(Alumno alumno) throws NullPointerException, IllegalArgumentException {
		if (alumno == null) {
			throw new NullPointerException("ERROR: El alumno no puede ser nulo.");
		}
		List<Prestamo> prestamosAlumno = new ArrayList<>();
		for (Prestamo prestamo : coleccionPrestamos) {
			if (prestamo.getAlumno().equals(alumno)) {
				prestamosAlumno.add(new Prestamo(prestamo));
			}
		}
		Comparator<Libro> comparadorLibro = Comparator.comparing(Libro::getTitulo).thenComparing(Libro::getAutor);
		Comparator<Prestamo> comparadorPrestamo = Comparator.comparing(Prestamo::getFechaPrestamo)
				.thenComparing(Prestamo::getLibro, comparadorLibro);
		prestamosAlumno.sort(comparadorPrestamo);
		
		return prestamosAlumno;
	}
	
	/**
	 * Método que devuelve los préstamos realizados de un libro.
	 * @param libro
	 * @return prestamosLibro
	 */
	public List<Prestamo> get(Libro libro) throws NullPointerException, IllegalArgumentException {
		if (libro == null) {
			throw new NullPointerException("ERROR: El libro no puede ser nulo.");
		}
		List<Prestamo> prestamosLibro = new ArrayList<>();
		for (Prestamo prestamo : coleccionPrestamos) {
			if (prestamo.getLibro().equals(libro)) {
				prestamosLibro.add(new Prestamo(prestamo));
			}
		}
		Comparator<Alumno> comparadorAlumno = Comparator.comparing(Alumno::getNombre);
		Comparator<Prestamo> comparadorPrestamo = Comparator.comparing(Prestamo::getFechaPrestamo)
				.thenComparing(Prestamo::getAlumno, comparadorAlumno);
		prestamosLibro.sort(comparadorPrestamo);
		
		return prestamosLibro;
	}
	
	/**
	 * Método que devuelve los préstamos realizados en una fecha determinada.
	 * @param fechaPrestamo
	 * @return prestamosFecha
	 */
	public List<Prestamo> get(LocalDate fechaPrestamo) throws NullPointerException, IllegalArgumentException {
		if (fechaPrestamo == null) {
			throw new NullPointerException("ERROR: La fecha no puede ser nula.");
		}
		List<Prestamo> prestamosFecha = new ArrayList<>();
		for (Prestamo prestamo : coleccionPrestamos) {
			if (mismoMes(prestamo.getFechaPrestamo(), fechaPrestamo)) {
				prestamosFecha.add(new Prestamo(prestamo));
			}
		}
		Comparator<Alumno> comparadorAlumno = Comparator.comparing(Alumno::getNombre);
		Comparator<Libro> comparadorLibro = Comparator.comparing(Libro::getTitulo).thenComparing(Libro::getAutor);
		Comparator<Prestamo> comparadorPrestamo = Comparator.comparing(Prestamo::getFechaPrestamo)
				.thenComparing(Prestamo::getAlumno, comparadorAlumno)
				.thenComparing(Prestamo::getLibro, comparadorLibro);
		prestamosFecha.sort(comparadorPrestamo);
		
		return prestamosFecha;
	}
	
	
	/**
	 * Método que devolverá un mapa con los puntos obtenidos por cada curso en un mes dado.
	 * @param fecha
	 * @return estadisticasMensualesPorCurso
	 */
	public Map<Curso, Integer> getEstadisticaMensualPorCurso(LocalDate fecha) {
		Map<Curso, Integer> estadisticasMensualesPorCurso = inicializarEstadisticas();
		List<Prestamo> prestamosMensuales = get(fecha);
		for (Prestamo prestamo : prestamosMensuales) {
			Curso cursoAlumno = prestamo.getAlumno().getCurso();
			estadisticasMensualesPorCurso.put(cursoAlumno, estadisticasMensualesPorCurso.get(cursoAlumno)
					+ Math.round(prestamo.getPuntos()));
		}
		return estadisticasMensualesPorCurso;
	}
	
	
	/**
	 * Método para inicializar las estadísticas.
	 * @return mapa
	 */
	private Map<Curso, Integer> inicializarEstadisticas() {
		Map<Curso, Integer> mapa = new EnumMap<>(Curso.class);
		for (Curso curso : Curso.values()) {
			mapa.put(curso, 0);
		}
		return mapa;
	}
	
	
	/**
	 * Método que devuelve true o false si las fechas de préstamo son iguales o no.
	 * @param fechaUno
	 * @param fechaDos
	 * @return fechaIgual
	 */
	private boolean mismoMes(LocalDate fechaUno, LocalDate fechaDos) {
		boolean fechaIgual = false;
		int anoUno = fechaUno.getYear();
		int anoDos = fechaDos.getYear();
		if (anoUno == anoDos && fechaUno.getMonth().equals(fechaDos.getMonth())) {
			fechaIgual = true;
		}
		return fechaIgual;
	}
	

	/********OTROS MÉTODOS********/
	
	/**
	 * Método para insertar un préstamo a la colección.
	 * @param prestamo
	 * @throws OperationNotSupportedException
	 */
	public void prestar(Prestamo prestamo) throws OperationNotSupportedException, NullPointerException, IllegalArgumentException {
		if (prestamo == null) {
			throw new NullPointerException("ERROR: No se puede prestar un préstamo nulo.");
		}
		int indice = coleccionPrestamos.indexOf(prestamo);
		if (indice == -1) {
			coleccionPrestamos.add(new Prestamo(prestamo));
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe un préstamo igual.");
		}
	}
	
	
	/**
	 * Método para devolver un préstamo. 
	 * @param prestamo
	 * @param fechaDevolucion
	 * @throws OperationNotSupportedException
	 */
	public void devolver(Prestamo prestamo, LocalDate fechaDevolucion) throws OperationNotSupportedException, NullPointerException, IllegalArgumentException {
		if (prestamo == null) {
			throw new NullPointerException("ERROR: No se puede devolver un préstamo nulo.");
		}
		if (fechaDevolucion == null) {
			throw new NullPointerException("ERROR: La fecha no puede ser nula.");
		}
		int indice = coleccionPrestamos.indexOf(prestamo);
		if (indice == -1) {
			throw new OperationNotSupportedException("ERROR: No existe ningún préstamo igual.");
		} else {
			coleccionPrestamos.get(indice).devolver(fechaDevolucion);
		}
	}
	
	
	/**
	 * Método que permite buscar un préstamo en la colección.
	 * @param prestamo
	 * @return prestamo
	 */
	public Prestamo buscar(Prestamo prestamo) throws NullPointerException, IllegalArgumentException {
		if (prestamo == null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar un préstamo nulo.");
		}
		int indice = coleccionPrestamos.indexOf(prestamo);
		if (indice == -1) {
			return null;
		} else {
			return new Prestamo(coleccionPrestamos.get(indice));
		}
	}
	
	/**
	 * Método para borrar un préstamo de la colección.
	 * @param prestamo
	 * @throws OperationNotSupportedException
	 */
	public void borrar(Prestamo prestamo) throws OperationNotSupportedException {
		if (prestamo == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar un préstamo nulo.");
		}
		int indice = coleccionPrestamos.indexOf(prestamo);
		if (indice == -1) {
			throw new OperationNotSupportedException("ERROR: No existe ningún préstamo igual.");
		} else {
			coleccionPrestamos.remove(indice);
		}
	}
	
}
