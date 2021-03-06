package org.iesalandalus.programacion.biblioteca.mvc.vista.texto;

import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.controlador.IControlador;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Curso;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;
import org.iesalandalus.programacion.biblioteca.mvc.vista.IVista;

public class VistaTexto implements IVista {

	/*********ATRIBUTO*********/
	
	private IControlador controlador;
	
	
	/********OTROS MÉTODOS********/
	
	/**
	 * Constructor para instanciar objetos de esta clase.
	 */
	public VistaTexto() {
		Opcion.setVista(this);
	}
	
	/**
	 * Método set que asigna valor al atributo.
	 * @param controlador
	 */
	public void setControlador(IControlador controlador) {
		this.controlador = controlador;
	}
	
	/**
	 * Método para inicializar la aplicación.
	 */
	public void comenzar() {
		Consola.mostrarCabecera("GESTIÓN DE PRÉSTAMOS DE LA BIBLIOTECA <<IES AL-ÁNDALUS>>");
		int opcion;
		do {
			Consola.mostrarMenu();
			opcion = Consola.elegirOpcion();
			Opcion opcionElegida = Opcion.getOpcionSegunOrdinal(opcion);
			opcionElegida.ejecutar();
		} while (opcion != Opcion.SALIR.ordinal());
	}
	
	/**
	 * Método para finalizar la aplicación.
	 */
	public void terminar() {
		controlador.terminar();
	}
	
	/**
	 * Método que llama a otro para insertar un alumno.
	 */
	public void insertarAlumno() {
		Consola.mostrarCabecera("INSERTAR ALUMNO");
		try {
			controlador.insertar(Consola.leerAlumno());
			System.out.println("Alumno insertado correctamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Método que llama a otro para buscar a un alumno.
	 */
	public void buscarAlumno() {
		Consola.mostrarCabecera("BUSCAR ALUMNO");
		Alumno alumno;
		try {
			alumno = controlador.buscar(Consola.leerAlumnoFicticio());
			String mensaje = (alumno != null) ? alumno.toString() : "No existe dicho alumno.";
			System.out.println(mensaje);
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Método que llama a otro para borrar a un alumno.
	 */
	public void borrarAlumno() {
		Consola.mostrarCabecera("BORRAR ALUMNO");
		try {
			controlador.borrar(Consola.leerAlumnoFicticio());
			System.out.println("Alumno borrado correctamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}	
	
	/**
	 * Método que llama a otro para listar todos los alumnos.
	 */
	public void listarAlumnos() {
		Consola.mostrarCabecera("LISTADO DE ALUMNOS");
		List<Alumno> alumnos = controlador.getAlumnos();
		if (!alumnos.isEmpty()) {
			for (Alumno alumno : alumnos) {
				if (alumno != null) {
					System.out.println(alumno);
				}
			}
		} else {
			System.out.println("No hay alumnos que mostrar.");
		}
		
	}
	
	/**
	 * Método que llama a otro para insertar un libro.
	 */
	public void insertarLibro() {
		Consola.mostrarCabecera("INSERTAR LIBRO");
		try {
			controlador.insertar(Consola.leerLibro());
			System.out.println("Libro insertado correctamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Método que llama a otro para buscar un libro.
	 */
	public void buscarLibro() {
		Consola.mostrarCabecera("BUSCAR LIBRO");
		Libro libro;
		try {
			libro = controlador.buscar(Consola.leerLibroFicticio());
			String mensaje = (libro != null) ? libro.toString() : "No existe dicho libro.";
			System.out.println(mensaje);
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Método que llama a otro para borrar un libro.
	 */
	public void borrarLibro() {
		Consola.mostrarCabecera("BORRAR LIBRO");
		try {
			controlador.borrar(Consola.leerLibroFicticio());
			System.out.println("Libro borrado correctamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	/**
	 * Método que llama a otro para listar todos los libros.
	 */
	public void listarLibros() {
		Consola.mostrarCabecera("LISTADO DE LIBROS");
		List<Libro> libros = controlador.getLibros();
		if (!libros.isEmpty()) {
			for (Libro libro : libros) {
				if (libro != null) {
					System.out.println(libro);
				}
			}
		} else {
			System.out.println("No hay libros que mostrar.");
		}
		
	}
	
	/**
	 * Método que llama a otro para prestar un libro.
	 */
	public void prestarLibro() {
		Consola.mostrarCabecera("PRÉSTAMO DE LIBRO");
		try {
			controlador.prestar(Consola.leerPrestamo());
			System.out.println("Libro prestado correctamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Método que llama a otro para devolver un libro.
	 */
	public void devolverLibro() {
		Consola.mostrarCabecera("DEVOLUCIÓN DE LIBRO");
		try {
			controlador.devolver(Consola.leerPrestamo(), Consola.leerFecha());
			System.out.println("Libro devuelto correctamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Método que llama a otro para buscar un préstamo.
	 */
	public void buscarPrestamo() {
		Consola.mostrarCabecera("BUSCAR PRÉSTAMO");
		Prestamo prestamo;
		try {
			prestamo = controlador.buscar(Consola.leerPrestamoFicticio());
			String mensaje = (prestamo != null) ? prestamo.toString() : "No existe dicho préstamo.";
			System.out.println(mensaje);
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Método que llama a otro para borrar un préstamo.
	 */
	public void borrarPrestamo() {
		Consola.mostrarCabecera("BORRAR PRÉSTAMO");
		try {
			controlador.borrar(Consola.leerPrestamoFicticio());
			System.out.println("Préstamo borrado correctamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Método que llama a otro para listar todos los préstamos.
	 */
	public void listarPrestamos() {
		Consola.mostrarCabecera("LISTADO DE PRÉSTAMOS");
		List<Prestamo> prestamos = controlador.getPrestamos();
		if (!prestamos.isEmpty()) {
			for (Prestamo prestamo : prestamos) {
				if (prestamo != null) {
					System.out.println(prestamo);
				}
			}
		} else {
			System.out.println("No hay préstamos para mostrar.");
		}
	}
	
	/**
	 * Método que llama a otro para listar los préstamos de un alumno.
	 */
	public void listarPrestamosAlumno() {
		Consola.mostrarCabecera("LISTADO DE PRÉSTAMOS POR ALUMNO");
		try {
			List<Prestamo> prestamos =  controlador.getPrestamos(Consola.leerAlumno());
			if (!prestamos.isEmpty()) {
				for (Prestamo prestamo : prestamos) {
					if (prestamo != null) {
						System.out.println(prestamo);
					}
				}
			} else {
				System.out.println("No hay préstamos de dicho alumno.");
			}
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		
	}

	/**
	 * Método que llama a otro para listar los préstamos de un libro.
	 */
	public void listarPrestamosLibro() {
		Consola.mostrarCabecera("LISTADO DE PRÉSTAMOS POR LIBRO");
		try {
			List<Prestamo> prestamos = controlador.getPrestamos(Consola.leerLibro());
			if (!prestamos.isEmpty()) {
				for (Prestamo prestamo : prestamos) {
					if (prestamo != null){
						System.out.println(prestamo);
					}
				}
			} else {
				System.out.println("No hay préstamos de dicho libro.");
			}
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	/**
	 * Método que llama a otro para listar los préstamos por fecha.
	 */
	public void listarPrestamosFecha() {
		Consola.mostrarCabecera("LISTADO DE PRÉSTAMOS POR FECHA");
		try {
			List<Prestamo> prestamos = controlador.getPrestamos(Consola.leerFecha());
			if (!prestamos.isEmpty()) {
				for (Prestamo prestamo : prestamos) {
					if (prestamo != null) {
						System.out.println(prestamo);
					}
				}
			} else {
				System.out.println("No hay préstamos de dicha fecha.");
			}
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	/**
	 * Método que llama a otro para mostrar la estadística mensual por curso.
	 */
	public void mostrarEstadisticaMensualPorCurso() {
		Consola.mostrarCabecera("ESTADÍSTICA MENSUAL POR CURSO");
		try {
			Map<Curso, Integer> estadisticasMensualesPorCurso = controlador.getEstadisticaMensualPorCurso(Consola.leerFecha());
			if (!estadisticasMensualesPorCurso.isEmpty()) {
				System.out.println(estadisticasMensualesPorCurso);
			} else {
				System.out.println("No hay estadísticas mensuales a mostrar para ese mes");
			}
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}
		
	}

}
