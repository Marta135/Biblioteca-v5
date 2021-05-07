package org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.ficheros;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

/**
 * 
 * @author Marta García
 * versión: v3
 *
 */

public class Prestamos implements IPrestamos{

	
	/*********ATRIBUTOS*********/
	
	private static final String NOMBRE_FICHERO_PRESTAMOS = "datos/prestamos.dat";
	private List<Prestamo> coleccionPrestamos;
	
	
	/*******CONSTRUCTORES*******/
	
	public Prestamos() throws NullPointerException, IllegalArgumentException {
		coleccionPrestamos = new ArrayList<>();
	}

	@Override
	public void comenzar() {
		leer();
	}
	
	private void leer() {
		File ficheroPrestamos = new File(NOMBRE_FICHERO_PRESTAMOS);
		try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(ficheroPrestamos))) {
			Prestamo prestamo = null;
			do {
				prestamo = (Prestamo) entrada.readObject();
				prestar(prestamo);
			} while (prestamo != null);
		} catch (ClassNotFoundException e) {
			System.out.println("No se ha podido encontrar la clase a leer.");
		} catch (FileNotFoundException e) {
			System.out.println("No se ha podido abrir el fichero préstamos.");
		} catch (EOFException e) {
			System.out.println("Fichero préstamos leido satisfactoriamente.");
		} catch (IOException e) {
			System.out.println("Error inesperado de Entrada/Salida");
		} catch (OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public void terminar() {
		escribir();
	}
	
	private void escribir() {
		File ficheroPrestamos = new File(NOMBRE_FICHERO_PRESTAMOS);
		try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ficheroPrestamos))) {
			for (Prestamo prestamo : coleccionPrestamos) {
				salida.writeObject(prestamo);
				System.out.println("Fichero préstamos escrito satisfactoriamente.");
			}
		} catch (FileNotFoundException e) {
			System.out.println("No se ha podido crear el fichero préstamos.");
		} catch (IOException e) {
			System.out.println("Error inesperado de Entrada/Salida");
		}
	}
	
	public List<Prestamo> get() throws NullPointerException, IllegalArgumentException {
		Comparator<Alumno> comparadorAlumno = Comparator.comparing(Alumno::getNombre);
		Comparator<Libro> comparadorLibro = Comparator.comparing(Libro::getTitulo).thenComparing(Libro::getAutor);
		Comparator<Prestamo> comparadorPrestamo = Comparator.comparing(Prestamo::getFechaPrestamo)
				.thenComparing(Prestamo::getAlumno, comparadorAlumno)
				.thenComparing(Prestamo::getLibro, comparadorLibro);
		List<Prestamo> prestamosOrdenados = copiaProfundaPrestamos();
		prestamosOrdenados.sort(comparadorPrestamo);
		
		return prestamosOrdenados;
	}
	
	private List<Prestamo> copiaProfundaPrestamos() throws NullPointerException, IllegalArgumentException {
		List<Prestamo> copiaPrestamos = new ArrayList<>();
		for (Prestamo prestamo : coleccionPrestamos) {
			copiaPrestamos.add(new Prestamo(prestamo));
		}
		return copiaPrestamos;
	}
	
	public int getTamano() {
		return coleccionPrestamos.size();
	}
	
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
	
	public Map<Curso, Integer> getEstadisticaMensualPorCurso(LocalDate fecha) {
		Map<Curso, Integer> estadisticasMensualesPorCurso = inicializarEstadisticas();
		List<Prestamo> prestamosMensuales = get(fecha);
		for (Prestamo prestamo : prestamosMensuales) {
			Curso cursoAlumno = prestamo.getAlumno().getCurso();
			estadisticasMensualesPorCurso.put(cursoAlumno, estadisticasMensualesPorCurso.get(cursoAlumno)
					+ prestamo.getPuntos());
		}
		return estadisticasMensualesPorCurso;
	}
	
	private Map<Curso, Integer> inicializarEstadisticas() {
		Map<Curso, Integer> mapa = new EnumMap<>(Curso.class);
		for (Curso curso : Curso.values()) {
			mapa.put(curso, 0);
		}
		return mapa;
	}
	
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
