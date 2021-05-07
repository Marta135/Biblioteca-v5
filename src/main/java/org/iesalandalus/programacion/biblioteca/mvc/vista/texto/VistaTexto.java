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

/**
 * 
 * @author Marta García
 * versión: 3v
 *
 */

public class VistaTexto implements IVista {

	
	/*********ATRIBUTO*********/
	
	private IControlador controlador;
	
	
	/********OTROS MÉTODOS********/
	
	public VistaTexto() {
		Opcion.setVista(this);
	}
	
	@Override
	public void setControlador(IControlador controlador) {
		if (controlador == null) {
			throw new NullPointerException("ERROR: El controlador no puede ser nulo.");
		}
		this.controlador = controlador;
	}
	
	@Override
	public void comenzar() {
		Consola.mostrarCabecera("GESTIÓN DE PRÉSTAMOS DE LA BIBLIOTECA <<IES AL-ÁNDALUS>>");
		int opcion;
		do {
			Consola.mostrarMenu();
			opcion = Consola.elegirOpcion();
			Opcion opcionElegida = Opcion.getOpcionSegunOrdinal(opcion);
			opcionElegida.ejecutar();
			System.out.println("\n");
		} while (opcion != Opcion.SALIR.ordinal());
	}
	
	@Override
	public void terminar() {
		controlador.terminar();
	}
	
	public void insertarAlumno() {
		Consola.mostrarCabecera("INSERTAR ALUMNO");
		try {
			controlador.insertar(Consola.leerAlumno());
			System.out.println("Alumno insertado correctamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void buscarAlumno() {
		Consola.mostrarCabecera("BUSCAR ALUMNO");
		try {
			Alumno alumno = controlador.buscar(Consola.leerAlumnoFicticio());
			String mensaje = (alumno != null) ? alumno.toString() : "No existe dicho alumno.";
			System.out.println(mensaje);
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void borrarAlumno() {
		Consola.mostrarCabecera("BORRAR ALUMNO");
		try {
			controlador.borrar(Consola.leerAlumnoFicticio());
			System.out.println("Alumno borrado correctamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}	
	
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
	
	public void insertarLibro() {
		Consola.mostrarCabecera("INSERTAR LIBRO");
		try {
			controlador.insertar(Consola.leerLibro());
			System.out.println("Libro insertado correctamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void buscarLibro() {
		Consola.mostrarCabecera("BUSCAR LIBRO");
		try {
			Libro libro = controlador.buscar(Consola.leerLibroFicticio());
			String mensaje = (libro != null) ? libro.toString() : "No existe dicho libro.";
			System.out.println(mensaje);
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	public void borrarLibro() {
		Consola.mostrarCabecera("BORRAR LIBRO");
		try {
			controlador.borrar(Consola.leerLibroFicticio());
			System.out.println("Libro borrado correctamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
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
	
	public void prestarLibro() {
		Consola.mostrarCabecera("PRÉSTAMO DE LIBRO");
		try {
			Prestamo prestamo = Consola.leerPrestamo();
			controlador.prestar(prestamo);
			System.out.println("Préstamo realizado correctamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void devolverLibro() {
		Consola.mostrarCabecera("DEVOLUCIÓN DE LIBRO");
		try {
			controlador.devolver(Consola.leerPrestamo(), Consola.leerFecha("Introduce la fecha de devolución"));
			System.out.println("Libro devuelto correctamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void buscarPrestamo() {
		Consola.mostrarCabecera("BUSCAR PRÉSTAMO");
		try {
			Prestamo prestamo = controlador.buscar(Consola.leerPrestamoFicticio());
			String mensaje = (prestamo != null) ? prestamo.toString() : "No existe dicho préstamo.";
			System.out.println(mensaje);
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void borrarPrestamo() {
		Consola.mostrarCabecera("BORRAR PRÉSTAMO");
		try {
			controlador.borrar(Consola.leerPrestamoFicticio());
			System.out.println("Préstamo borrado correctamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}
	
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
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
		
	}

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
		} catch (IllegalArgumentException| NullPointerException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public void listarPrestamosFecha() {
		Consola.mostrarCabecera("LISTADO DE PRÉSTAMOS POR FECHA");
		try {
			List<Prestamo> prestamos = controlador.getPrestamos(Consola.leerFecha("Introduce la fecha del préstamo"));
			if (!prestamos.isEmpty()) {
				for (Prestamo prestamo : prestamos) {
					if (prestamo != null) {
						System.out.println(prestamo);
					}
				}
			} else {
				System.out.println("No hay préstamos para dicha fecha.");
			}
		} catch (NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public void mostrarEstadisticaMensualPorCurso() {
		Consola.mostrarCabecera("ESTADÍSTICA MENSUAL POR CURSO");
		try {
			Map<Curso, Integer> estadisticasMensuales = controlador.getEstadisticaMensualPorCurso
					(Consola.leerFecha("Introduce la fecha para mostrar las estadísticas mensuales"));
			if (!estadisticasMensuales.isEmpty()) {
				System.err.println(estadisticasMensuales);
			} else {
				System.out.println("No existen estadísticas para la fecha seleccionada.");
			}
		} catch (NullPointerException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

}
