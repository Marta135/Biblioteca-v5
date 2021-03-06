package org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.ficheros;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.AudioLibro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.LibroEscrito;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio.ILibros;

public class Libros implements ILibros{

	/*********ATRIBUTOS*********/
	
	private static final String NOMBRE_FICHERO_LIBROS = "datos" + File.separator + "libros.dat";
	private List<Libro> coleccionLibros;
	
	
	/*******CONSTRUCTORES*******/
	
	/**
	 * Constructor sin parámetros.
	 */
	public Libros() throws NullPointerException, IllegalArgumentException {
		coleccionLibros = new ArrayList<>();
	}
	
	
	/**
	 * Método que llama al método leer.
	 */
	public void comenzar() {
		leer();
	}
	
	/**
	 * Método que premite leer el fichero de alumnos.
	 */
	private void leer() {
		File ficheroLibros = new File(NOMBRE_FICHERO_LIBROS);
		try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(ficheroLibros))) {
			Libro libro = null;
			do {
				libro = (Libro) entrada.readObject();
				insertar(libro);
			} while (libro != null);
		} catch (ClassNotFoundException e) {
			System.out.println("No se ha podido encontrar la clase a leer.");
		} catch (FileNotFoundException e) {
			System.out.println("No se ha podido abrir el fichero de libros.");
		} catch (EOFException e) {
			System.out.println("Fichero libros leido satisfactoriamente.");
		} catch (IOException e) {
			System.out.println("Error inesperado de Entrada/Salida");
		} catch (OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Método que llama al método escribir.
	 */
	public void terminar() {
		escribir();
	}
	
	/**
	 * Método que permite escribir en el fichero de alumnos.
	 */
	private void escribir() {
		File ficheroLibros = new File(NOMBRE_FICHERO_LIBROS);
		try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ficheroLibros))) {
			for (Libro libro : coleccionLibros) {
				salida.writeObject(libro);
				System.out.println("Fichero libros escrito satisfactoriamente.");
			}
		} catch (FileNotFoundException e) {
			System.out.println("No se ha podido crear el fichero de libros.");
		} catch (IOException e) {
			System.out.println("Error inesperado de Entrada/Salida");
		}
	}
	
		
	/**
	 * Método que devuelve una copia de la colección.
	 * @return librosOrdenados
	 */
	public List<Libro> get() throws NullPointerException, IllegalArgumentException {
		List<Libro> librosOrdenados = copiaProfundaLibros();
		librosOrdenados.sort(Comparator.comparing(Libro::getTitulo).thenComparing(Libro::getAutor));
		return librosOrdenados;
	}
	
	/**
	 * Método que devuelve una copia de la colección de libros.
	 * @return copiaLibros
	 */
	private List<Libro> copiaProfundaLibros() throws NullPointerException, IllegalArgumentException {
		List<Libro> copiaLibros = new ArrayList<>();
		for (Libro libro : coleccionLibros) {
			if (libro instanceof LibroEscrito) {
				copiaLibros.add(new LibroEscrito((LibroEscrito)libro));
			} 
			if (libro instanceof AudioLibro) {
				copiaLibros.add(new AudioLibro((AudioLibro)libro));
			}
		}
		return copiaLibros;
	}
	
	
	/**
	 * Método que devuelve el tamaño de la colección.
	 * @return coleccionLibros.size
	 */
	public int getTamano() {
		return coleccionLibros.size();
	}
	

	/********OTROS MÉTODOS********/
	
	/**
	 * Método para insertar un libro a la colección.
	 * @param libro
	 * @throws OperationNotSupportedException
	 */
	public void insertar(Libro libro) throws OperationNotSupportedException, NullPointerException, IllegalArgumentException {
		if (libro == null) {
			throw new NullPointerException("ERROR: No se puede insertar un libro nulo.");
		}
		int indice = coleccionLibros.indexOf(libro);
		if (indice == -1) {
			if (libro instanceof LibroEscrito) {
				coleccionLibros.add(new LibroEscrito((LibroEscrito)libro));
			}  
			if (libro instanceof AudioLibro) {
				coleccionLibros.add(new AudioLibro((AudioLibro)libro));
			}
		} else {
			throw new OperationNotSupportedException("ERROR: Ya existe un libro con ese título y autor.");
		}
	}
	
	
	/**
	 * Método que permite buscar un libro en la colección.
	 * @param libro
	 * @return libro
	 */
	public Libro buscar(Libro libro) {
		if (libro == null) {
			throw new IllegalArgumentException("ERROR: No se puede buscar un libro nulo.");
		}
		int indice = coleccionLibros.indexOf(libro);
		if (indice == -1) {
			return null;
		} else {
			if (libro instanceof LibroEscrito) {
				return new LibroEscrito((LibroEscrito)coleccionLibros.get(indice));
			} 
			if (libro instanceof AudioLibro) {
				return new AudioLibro((AudioLibro)coleccionLibros.get(indice));
			}
		}
		return libro;
	}
	
	/**
	 * Método para borrar un libro de la colección.
	 * @param libro
	 * @throws OperationNotSupportedException
	 */
	public void borrar(Libro libro) throws OperationNotSupportedException {
		if (libro == null) {
			throw new IllegalArgumentException("ERROR: No se puede borrar un libro nulo.");
		}
		int indice = coleccionLibros.indexOf(libro);
		if (indice == -1) {
			throw new OperationNotSupportedException("ERROR: No existe ningún libro con ese título y autor.");
		} else {
			coleccionLibros.remove(indice);
		}
	}

}
