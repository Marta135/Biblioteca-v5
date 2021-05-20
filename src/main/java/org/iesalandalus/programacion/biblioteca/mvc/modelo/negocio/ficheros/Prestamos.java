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
import java.time.Month;
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

public class Prestamos implements IPrestamos {

	private static final String NOMBRE_FICHERO_PRESTAMOS = "datos" + File.separator + "prestamos.dat";
	private List<Prestamo> coleccionPrestamos;

	public Prestamos() {
		coleccionPrestamos = new ArrayList<>();
	}

	@Override
	public void comenzar() {
		leer();
	}
	
	private void leer() {
		File ficheroLibros = new File(NOMBRE_FICHERO_PRESTAMOS);
		try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(ficheroLibros))) {
			Prestamo prestamo = null;
			do {
				prestamo = (Prestamo) entrada.readObject();
				prestar(prestamo);
			} while (prestamo != null);
		} catch (ClassNotFoundException e) {
			System.out.println("No se ha podido encontrar la clase a leer.");
		} catch (FileNotFoundException e) {
			System.out.println("No se ha podido abrir el fichero de prestamos.");
		} catch (EOFException e) {
			System.out.println("Fichero prestamos leído satisfactoriamente.");
		} catch (IOException e) {
			System.out.println("Error inesperado de Entrada/Salida.");
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
			for (Prestamo prestamo : coleccionPrestamos)
				salida.writeObject(prestamo);
			System.out.println("Fichero prestamos escrito satisfactoriamente.");
		} catch (FileNotFoundException e) {
			System.out.println("No se ha podido crear el fichero de prestamos.");
		} catch (IOException e) {
			System.out.println("Error inesperado de Entrada/Salida.");
		}
	}
	
	@Override
	public List<Prestamo> get() {
		List<Prestamo> prestamosOrdenados = copiaProfundaPrestamos();
		Comparator<Alumno> comparadorAlumno = Comparator.comparing(Alumno::getNombre);
		Comparator<Libro> comparadorLibro = Comparator.comparing(Libro::getTitulo).thenComparing(Libro::getAutor);
		prestamosOrdenados.sort(Comparator.comparing(Prestamo::getFechaPrestamo).thenComparing(Prestamo::getAlumno, comparadorAlumno).thenComparing(Prestamo::getLibro, comparadorLibro));
		return prestamosOrdenados;
	}

	private List<Prestamo> copiaProfundaPrestamos() {
		List<Prestamo> copiaPrestamos = new ArrayList<>();
		for (Prestamo prestamo : coleccionPrestamos) {
			copiaPrestamos.add(new Prestamo(prestamo));
		}
		return copiaPrestamos;
	}

	@Override
	public int getTamano() {
		return coleccionPrestamos.size();
	}

	@Override
	public List<Prestamo> get(Alumno alumno) {
		if (alumno==null) {
			throw new NullPointerException("ERROR: El alumno no puede ser nulo.");
		}
		List<Prestamo> prestamosAlumno = new ArrayList<>();
		for (Prestamo prestamo : coleccionPrestamos) {
			if (prestamo.getAlumno().equals(alumno)) {
				prestamosAlumno.add(new Prestamo(prestamo));
			}
		}
		Comparator<Alumno> comparadorAlumno = Comparator.comparing(Alumno::getNombre);
		Comparator<Libro> comparadorLibro = Comparator.comparing(Libro::getTitulo).thenComparing(Libro::getAutor);
		prestamosAlumno.sort(Comparator.comparing(Prestamo::getFechaPrestamo).thenComparing(Prestamo::getAlumno, comparadorAlumno).thenComparing(Prestamo::getLibro, comparadorLibro));
		return prestamosAlumno;
	}

	@Override
	public List<Prestamo> get(Libro libro) {
		if (libro==null) {
			throw new NullPointerException("ERROR: El libro no puede ser nulo.");
		}
		List<Prestamo> prestamosLibro = new ArrayList<>();
		for (Prestamo prestamo : coleccionPrestamos) {
			if (prestamo.getLibro().equals(libro)) {
				prestamosLibro.add(new Prestamo(prestamo));
			}
		}
		Comparator<Alumno> comparadorAlumno = Comparator.comparing(Alumno::getNombre);
		Comparator<Libro> comparadorLibro = Comparator.comparing(Libro::getTitulo).thenComparing(Libro::getAutor);
		prestamosLibro.sort(Comparator.comparing(Prestamo::getFechaPrestamo).thenComparing(Prestamo::getAlumno, comparadorAlumno).thenComparing(Prestamo::getLibro, comparadorLibro));
		return prestamosLibro;
	}

	@Override
	public List<Prestamo> get(LocalDate fechaPrestamo) {
		if (fechaPrestamo==null) {
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
		prestamosFecha.sort(Comparator.comparing(Prestamo::getFechaPrestamo).thenComparing(Comparator.comparing(Prestamo::getAlumno, comparadorAlumno).thenComparing(Prestamo::getLibro, comparadorLibro)));
		return prestamosFecha;
	}

	@Override
	public Map<Curso, Integer> getEstadisticaMensualPorCurso(LocalDate fecha) {
		Map<Curso, Integer>estadisticasMensualesPorCurso = inicializarEstadisticas();
		List<Prestamo> prestamosMensuales = get(fecha);
		for (Prestamo prestamo : prestamosMensuales) {
			Curso cursoAlumno = prestamo.getAlumno().getCurso();
			estadisticasMensualesPorCurso.put(cursoAlumno, estadisticasMensualesPorCurso.get(cursoAlumno) + prestamo.getPuntos());
		}
		return estadisticasMensualesPorCurso;
	}

	private Map<Curso, Integer> inicializarEstadisticas() {
		Map<Curso, Integer>map = new EnumMap<>(Curso.class);
		for (Curso curso : Curso.values()) {
			map.put(curso, 0);
		}
		return map;
	}

	private boolean mismoMes(LocalDate fechaInicial, LocalDate fechaFinal) {
		boolean fecha = false;
		Month mes = fechaInicial.getMonth();
		int anio = fechaInicial.getYear();
		if (mes.equals(fechaFinal.getMonth()) && anio==fechaFinal.getYear()) {
			fecha = true;
		}
		return fecha;
	}

	@Override
	public void prestar(Prestamo prestamo) throws OperationNotSupportedException {
		if (prestamo == null) {
			throw new NullPointerException("ERROR: No se puede prestar un préstamo nulo.");
		}
		int indice = coleccionPrestamos.indexOf(prestamo);
		if (indice == -1) {
			coleccionPrestamos.add(prestamo);
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe un préstamo igual.");
		}
	}

	@Override
	public void devolver(Prestamo prestamo, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		if (prestamo == null) {
			throw new NullPointerException("ERROR: No se puede devolver un préstamo nulo.");
		}
		if (fechaDevolucion==null) {
			throw new NullPointerException("ERROR: La fecha no puede ser nula.");
		}
		int indice = coleccionPrestamos.indexOf(prestamo);
		if (indice == -1) {
			throw new OperationNotSupportedException("ERROR: No existe ningún préstamo igual.");
		} else {
			coleccionPrestamos.get(indice).devolver(fechaDevolucion);
		}	
	}

	@Override
	public Prestamo buscar(Prestamo prestamo) {
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

	@Override
	public void borrar(Prestamo prestamo) throws OperationNotSupportedException {
		if (prestamo == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar un préstamo nulo.");
		}
		int indice = coleccionPrestamos.indexOf(prestamo);
		if (indice == -1) {
			throw new OperationNotSupportedException("ERROR: No existe ningún préstamo igual.");
		} else {
			coleccionPrestamos.remove(prestamo);
		}
	}

}
