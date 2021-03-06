package org.iesalandalus.programacion.biblioteca.mvc.vista.texto;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.AudioLibro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Curso;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.LibroEscrito;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {

	/**
	 * Constructor.
	 * Evita que se puedan crear instancias de esta clase.
	 */
	private Consola() {
		
	}
	
	/**
	 * Método que muestra el menú de opciones.
	 */
	public static void mostrarMenu() {
		System.out.println("MENÚ DE OPCIONES");
		System.out.println("-----------------");
		for (Opcion opcion : Opcion.values()) {
			System.out.println(opcion);
		}
	}
	
	/**
	 * Método que muestra la cabecera de la aplicación. 
	 * @param mensaje
	 */
	public static void mostrarCabecera(String mensaje) {
		System.out.println(mensaje);
		int longitudCadena = mensaje.length();
		for (int i = 0; i < longitudCadena; i++) {
			System.out.print("-");
		}
		System.out.println("\n");
	}
	
	/**
	 * Método para elegir una de las opciones del menú.
	 * @return opcion
	 */
	public static int elegirOpcion() {
		int opcion;
		do {
			System.out.print("\nElige una opción: ");
			opcion = Entrada.entero();
			if (!Opcion.esOrdinalValido(opcion)) {
				System.out.println("La opción escogida no es válida. Elige una opción: ");
			}
		} while (!Opcion.esOrdinalValido(opcion));
		return opcion;
	}
	
	/**
	 * Método para introducir los datos de un alumno.
	 * @return Alumno
	 */
	public static Alumno leerAlumno() {
		Alumno alumno = null;
		Curso curso = null;
		String nombre;
		String correo;
		int numeroCurso;
		
		System.out.print("Introduce el nombre del alumno: ");
		nombre = Entrada.cadena();
		System.out.print("Introduce el correo del alumno: ");
		correo = Entrada.cadena();
		
		do {
			System.out.print("Introduce el curso del alumno: ");
			numeroCurso = Entrada.entero();
		} while (numeroCurso<1 || numeroCurso>4);
		
		switch(numeroCurso) {
		case 1:
			curso = Curso.PRIMERO;
			break;
		case 2: 
			curso = Curso.SEGUNDO;
			break;
		case 3: 
			curso = Curso.TERCERO;
			break;
		case 4: 
			curso = Curso.CUARTO;
			break;
		}
		alumno = new Alumno(nombre, correo, curso); 
		return alumno;
	}
	
	/**
	 * Método para introducir los datos de un alumno ficticio.
	 * @return alumnoFicticio
	 */
	public static Alumno leerAlumnoFicticio() {
		System.out.print("Introduce el correo del alumno: ");
		return Alumno.getAlumnoFicticio(Entrada.cadena());
	}
	
	/**
	 * Método para introducir los datos de un libro.
	 * @return Libro
	 */
	public static Libro leerLibro() {
		Libro libro = null;
		int tipoLibro = 0;
		System.out.print("Introduce el título del libro: ");
		String titulo = Entrada.cadena();
		System.out.print("Introduce el autor del libro: ");
		String autor = Entrada.cadena();
		
		do {
			System.out.println("Introduce el tipo de libro:");
			System.out.println("1.- Libro Escrito");
			System.out.println("2.- Audio Libro");
			tipoLibro = Entrada.entero();
		} while (tipoLibro<1 && tipoLibro>2);
		
		if (tipoLibro == 1) {
			System.out.print("Introduce el número de páginas del libro: ");
			int numPaginas = Entrada.entero();
			libro = new LibroEscrito(titulo, autor, numPaginas);
		} else {
			System.out.print("Introduce la duración del libro: ");
			int duracion = Entrada.entero();
			libro = new AudioLibro(titulo, autor, duracion);
		}
		return libro;
	}
	
	/**
	 * Método para introducir los datos de un libro ficticio.
	 * @return libroFicticio
	 */
	public static Libro leerLibroFicticio() {
		System.out.print("Introduce el título del libro: ");
		String titulo = Entrada.cadena();
		System.out.print("Introduce el autor del libro: ");
		String autor = Entrada.cadena();
		return Libro.getLibroFicticio(titulo, autor);
	}
	
	/**
	 * Método para introducir los datos de un préstamo.
	 * @return Prestamo
	 */
	public static Prestamo leerPrestamo() {
		return new Prestamo(leerAlumno(), leerLibro(), leerFecha());
	}
	
	/**
	 * Método para introducir los datos de un préstamo ficticio.
	 * @return prestamoFicticio
	 */
	public static Prestamo leerPrestamoFicticio() {
		return Prestamo.getPrestamoFicticio(leerAlumnoFicticio(), leerLibroFicticio());
	}
	
	/**
	 * Método para introducir la fecha de un préstamo.
	 * @return fecha
	 */
	public static LocalDate leerFecha() {
		LocalDate fecha = null;
		String cadenaFormato = "dd/MM/yyyy";
		DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern(cadenaFormato);
		System.out.printf("Introduce la fecha del préstamo (%s): ", formatoFecha);
		String diaLeido = Entrada.cadena();
		try {
			fecha = LocalDate.parse(diaLeido, formatoFecha);
		} catch (DateTimeException e) {
			System.out.println("ERROR: El formato de la fecha no es correcto.");
		}
		return fecha;
	}
}
