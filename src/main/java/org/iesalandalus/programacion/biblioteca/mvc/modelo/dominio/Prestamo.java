package org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Prestamo {

	/*********ATRIBUTOS*********/
	
	private static final int MAX_DIAS_PRESTAMO = 20;
	public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private LocalDate fechaPrestamo;
	private LocalDate fechaDevolucion;
	private Alumno alumno;
	private Libro libro;
	
	
	/*******CONSTRUCTORES*******/
	
	/**
	 * Constructor con parámetros:
	 * @param alumno
	 * @param libro
	 * @param fechaPrestamo
	 */
	public Prestamo (Alumno alumno, Libro libro, LocalDate fechaPrestamo) throws NullPointerException, IllegalArgumentException {
		setAlumno(alumno);
		setLibro(libro);
		setFechaPrestamo(fechaPrestamo);
	}
	
	/**
	 * Constructor copia:
	 * @param copiaPrestamo: copia del objeto Prestamo.
	 */
	public Prestamo (Prestamo copiaPrestamo) throws NullPointerException, IllegalArgumentException {
		if(copiaPrestamo == null) {
			throw new NullPointerException("ERROR: No es posible copiar un préstamo nulo.");
		}
		setAlumno(copiaPrestamo.getAlumno());
		setLibro(copiaPrestamo.getLibro());
		setFechaPrestamo(copiaPrestamo.getFechaPrestamo());
		
		if(copiaPrestamo.getFechaDevolucion() != null) {
			setFechaDevolucion(copiaPrestamo.getFechaDevolucion());
		}
	}
	
	/**
	 * Método que devolverá un préstamo ficticio para usarlo en búsquedas y borrados.
	 * @param alumno
	 * @param libro
	 * @return correo, titulo, autor y fechaPrestamo.
	 */
	public static Prestamo getPrestamoFicticio (Alumno alumno, Libro libro) throws NullPointerException, IllegalArgumentException {
		if(alumno == null) {
			throw new NullPointerException("ERROR: El alumno no puede ser nulo.");
		}
		if(libro == null) {
			throw new NullPointerException("ERROR: El libro no puede ser nulo.");
		}
		return new Prestamo(alumno, libro, LocalDate.now());
	}
	
	/**
	 * Método para devolver un libro.
	 * @param fechaDevolucion
	 */
	public void devolver (LocalDate fechaDevolucion) throws NullPointerException, IllegalArgumentException {
		setFechaDevolucion(fechaDevolucion);
	}
	
	public int getPuntos() {
		if(getFechaDevolucion() == null) {
			return 0;
		} else {
			long diasIntermedios = ChronoUnit.DAYS.between(getFechaPrestamo(), getFechaDevolucion());
			if (diasIntermedios <= MAX_DIAS_PRESTAMO && diasIntermedios > 0) {
				return Math.round(libro.getPuntos()/diasIntermedios);
			} else {
				return 0;
			}
		}
	}
	
	/*********GETTERS Y SETTERS**********/
	
	/**
	 * Método que devuelve los datos del alumno.
	 * @return alumno
	 */
	public Alumno getAlumno() {
		return alumno;
	}
	
	/**
	 * Método que modifica los datos del alumno.
	 * @param alumno
	 */
	private void setAlumno(Alumno alumno) throws NullPointerException, IllegalArgumentException {
		if (alumno == null) {
			throw new NullPointerException("ERROR: El alumno no puede ser nulo.");
		}
		this.alumno = new Alumno(alumno);
	}
	
	/**
	 * Método que devuelve los datos del libro.
	 * @return libro
	 */
	public Libro getLibro() {
		Libro libro = null;
		if (this.libro instanceof LibroEscrito) {
			libro = new LibroEscrito((LibroEscrito)this.libro);
		} else if (this.libro instanceof AudioLibro) {
			libro = new AudioLibro((AudioLibro)this.libro);
		}
		return libro;
	}
	
	/**
	 * Método que modifica los datos del libro.
	 * @param libro
	 */
	private void setLibro(Libro libro) throws NullPointerException, IllegalArgumentException {
		if (libro == null) {
			throw new NullPointerException("ERROR: El libro no puede ser nulo.");
		}
		if (libro instanceof LibroEscrito) {
			this.libro = new LibroEscrito((LibroEscrito)libro);
		} else if (libro instanceof AudioLibro) {
			this.libro = new AudioLibro((AudioLibro)libro);
		}
	}
	
	/**
	 * Método que devuelve la fecha de préstamo del libro.
	 * @return fechaPrestamo
	 */
	public LocalDate getFechaPrestamo() {
		return fechaPrestamo;
	}
	
	/**
	 *  Método que modifica la fecha de préstamo del libro.
	 * @param fechaPrestamo
	 */
	private void setFechaPrestamo(LocalDate fechaPrestamo) {
		if(fechaPrestamo == null) {
			throw new NullPointerException("ERROR: La fecha de préstamo no puede ser nula.");
		}
		if (fechaPrestamo.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("ERROR: La fecha de préstamo no puede ser futura.");
		}
		this.fechaPrestamo = fechaPrestamo;
	}
	
	/**
	 * Método que devuelve la fecha de devolución del libro.
	 * @return
	 */
	public LocalDate getFechaDevolucion() {
		return fechaDevolucion;
	}
	
	/**
	 * Método que modifica la fecha de préstamo del libro.
	 * @param fechaDevolucion
	 */
	private void setFechaDevolucion(LocalDate fechaDevolucion) {
		if(this.fechaDevolucion != null) {
			throw new IllegalArgumentException("ERROR: La devolución ya estaba registrada.");
		}
		if(fechaDevolucion == null) {
			throw new NullPointerException("ERROR: La fecha de devolución no puede ser nula.");
		}
		if (fechaDevolucion.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("ERROR: La fecha de devolución no puede ser futura.");
		}
		if(fechaDevolucion.isBefore(fechaPrestamo) || fechaDevolucion.isEqual(fechaPrestamo)) {
			throw new IllegalArgumentException("ERROR: La fecha de devolución debe ser posterior a la fecha de préstamo.");
		}
		this.fechaDevolucion = fechaDevolucion;
	}


	/********OTROS MÉTODOS********/
	
	/**
	 * Método hashCode.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alumno == null) ? 0 : alumno.hashCode());
		result = prime * result + ((libro == null) ? 0 : libro.hashCode());
		return result;
	}

	/**
	 * Método para comparar dos préstamos.
	 * Dos préstamos son iguales si alumno y libro son iguales.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Prestamo other = (Prestamo) obj;
		if (alumno == null) {
			if (other.alumno != null)
				return false;
		} else if (!alumno.equals(other.alumno))
			return false;
		if (libro == null) {
			if (other.libro != null)
				return false;
		} else if (!libro.equals(other.libro))
			return false;
		return true;
	}

	/**
	 * Método que muestra la información del préstamo:
	 * 
	 */
	@Override
	public String toString() {
		if(fechaDevolucion == null) {
			return String.format("alumno=(%s), libro=(%s), fecha de préstamo=%s, puntos=%s", 
					alumno, libro, fechaPrestamo.format(FORMATO_FECHA), getPuntos());
		} else {
			return String.format("alumno=(%s), libro=(%s), fecha de préstamo=%s, fecha de devolución=%s, puntos=%s", 
					alumno, libro, fechaPrestamo.format(FORMATO_FECHA), fechaDevolucion.format(FORMATO_FECHA), getPuntos());
		}
	}

}
