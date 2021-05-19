package org.iesalandalus.programacion.biblioteca.mvc.vista.texto;

import java.time.DateTimeException;
import java.time.LocalDate;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.AudioLibro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Curso;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.LibroEscrito;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;
import org.iesalandalus.programacion.utilidades.Entrada;

/**
 * 
 * @author Marta García
 * versión: v3
 *
 */

public class Consola {


	private Consola() {
		
	}
	
	public static void mostrarMenu() {
		System.out.println("MENÚ DE OPCIONES");
		System.out.println("-----------------");
		for (Opcion opcion : Opcion.values()) {
			System.out.println(opcion);
		}
	}
	
	public static void mostrarCabecera(String mensaje) {
		System.out.println();
		System.out.println(mensaje);
		int longitudCadena = mensaje.length();
		for (int i = 0; i < longitudCadena; i++) {
			System.out.print("-");
		}
		System.out.println("\n");
	}
	
	public static int elegirOpcion() {
		int opcion;
		do {
			System.out.print("\nElige una opción: ");
			opcion = Entrada.entero();
			System.out.println();
			if (!Opcion.esOrdinalValido(opcion)) {
				System.out.println("La opción escogida no es válida. Elige una opción: ");
			}
		} while (!Opcion.esOrdinalValido(opcion));
		return opcion;
	}
	
	public static Alumno leerAlumno() {
		Curso curso = null;
		
		System.out.print("Introduce el nombre del alumno: ");
		String nombre = Entrada.cadena();
		System.out.print("Introduce el correo del alumno: ");
		String correo = Entrada.cadena();
		
		int numeroCurso;
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
		return new Alumno(nombre, correo, curso); 
	}
	
	public static Alumno leerAlumnoFicticio() {
		System.out.print("Introduce el correo del alumno: ");
		return Alumno.getAlumnoFicticio(Entrada.cadena());
	}
	
	public static Libro leerLibro() {
		System.out.print("Introduce el título del libro: ");
		String titulo = Entrada.cadena();
		System.out.print("Introduce el autor del libro: ");
		String autor = Entrada.cadena();
		
		int tipoLibro = 0;
		do {
			System.out.println("Introduce el tipo de libro:");
			System.out.println("1.- Libro Escrito");
			System.out.println("2.- Audio Libro");
			tipoLibro = Entrada.entero();
		} while (tipoLibro<1 || tipoLibro>2);
		
		Libro libro = null;
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
	
	public static Libro leerLibroFicticio() {
		System.out.print("Introduce el título del libro: ");
		String titulo = Entrada.cadena();
		System.out.print("Introduce el autor del libro: ");
		String autor = Entrada.cadena();
		return Libro.getLibroFicticio(titulo, autor);
	}
	
	public static Prestamo leerPrestamo() {
		return new Prestamo(leerAlumno(), leerLibro(), leerFecha("Introduce la fecha del préstamo "));
	}
	
	public static Prestamo leerPrestamoFicticio() {
		return Prestamo.getPrestamoFicticio(leerAlumnoFicticio(), leerLibroFicticio());
	}
	
	public static LocalDate leerFecha(String mensaje) {
		LocalDate fecha = null;
		System.out.printf("%s (dd/MM/yyyy): ", mensaje);
		String diaLeido = Entrada.cadena();
		try {
			fecha = LocalDate.parse(diaLeido, Prestamo.FORMATO_FECHA);
		} catch (DateTimeException e) {
			System.out.println("ERROR: El formato de la fecha no es correcto.");
		}
		return fecha;
	}
}
