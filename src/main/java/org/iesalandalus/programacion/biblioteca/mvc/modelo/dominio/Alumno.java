package org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio;

public class Alumno {

	
	/*********ATRIBUTOS*********/
	
	private static final String ER_NOMBRE = "[a-zA-ZÁáÉéÍíÓóÚúÑñ]+[\\s]+[a-zA-ZÁáÉéÍíÓóÚúÑñ\\s]*";
	private static final String ER_CORREO = ".+@[a-zA-Z]+\\.[a-zA-Z]+";
	private String nombre;
	private String correo;
	private Curso curso;
	
	
	/*******CONSTRUCTORES*******/
	
	/**
	 * Constructor con parámetros:
	 * @param nombre: Nombre y apellido del alumno.
	 * @param correo: Correo del alumno.
	 * @param curso: Curso al que pertenece el alumno. 
	 */
	public Alumno (String nombre, String correo, Curso curso) throws NullPointerException, IllegalArgumentException {
		setNombre(nombre);
		setCorreo (correo);
		setCurso (curso);
	}
	
	/**
	 * Constructor copia:
	 * @param copiaAlumno: Copia del objeto Alumno. 
	 */
	public Alumno (Alumno copiaAlumno) throws NullPointerException, IllegalArgumentException {
		if(copiaAlumno == null)
			throw new NullPointerException("ERROR: No es posible copiar un alumno nulo.");
		setNombre(copiaAlumno.getNombre());
		setCorreo(copiaAlumno.getCorreo());
		setCurso(copiaAlumno.getCurso());
	}

	/**
	 * Método que devolverá un alumno ficticio para usarlo en búsquedas y borrados.
	 * @param correo
	 * @return nombre, correo y curso
	 */
	public static Alumno getAlumnoFicticio (String correo) throws NullPointerException, IllegalArgumentException {
		return new Alumno("Marta García", correo, Curso.CUARTO);
	}
	
	
	/*********GETTERS Y SETTERS**********/
	
	/**
	 * Método que devuelve el nombre del alumno.
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Método que modifica el nombre del alumno.
	 * @param nombre
	 */
	private void setNombre(String nombre) {
		if(nombre == null) {
			throw new NullPointerException("ERROR: El nombre no puede ser nulo.");
		}
		if(!nombre.matches(ER_NOMBRE)) {
			throw new IllegalArgumentException("ERROR: El nombre no tiene un formato válido.");
		}
		this.nombre = formateaNombre(nombre);
	}

	
	/**
	 * Método que formatea el nombre del alumno.
	 * Convierte la primera letra de cada palabra en mayúscula y elimina espacios
	 * en blanco sobrantes.
	 * @param nombre
	 * @return nombreFormateado
	 */
	private String formateaNombre(String nombre) {
		
		String nombreFormateado = nombre.toLowerCase();
		
		char[] caracteres = nombreFormateado.toCharArray();
		caracteres[0] = Character.toUpperCase(caracteres[0]);
		
		for (int i = 0; i < caracteres.length; i++) {
			if (caracteres[i] == ' ' || caracteres[i] == '.' || caracteres[i] == ',') {
				caracteres[i + 1] = Character.toUpperCase(caracteres[i + 1]);
			}
		}
		nombreFormateado = new String(caracteres);
		
		return nombreFormateado.replaceAll(" +", " ").trim();
	}
	
	
	/**
	 * Método que devuelve el correo del alumno.
	 * @return correo
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Método que modifica el correo del alumno.
	 * @param correo
	 */
	private void setCorreo(String correo) {
		if(correo == null) {
			throw new NullPointerException("ERROR: El correo no puede ser nulo.");
		}
		if(!correo.matches(ER_CORREO)) {
			throw new IllegalArgumentException("ERROR: El formato del correo no es válido.");
		}
		this.correo = correo;
	}

	
	/**
	 * Método que devuelve las iniciales del nombre del alumno.
	 * @return iniciales
	 */
	private String getIniciales() {
		
		String iniciales = "";
		String nombreFormateado = formateaNombre(nombre);
		
		String[] letras = nombreFormateado.split(" ");
		for (int i = 0; i < letras.length; i++) {
			iniciales += letras[i].substring(0, 1);
		}
		return iniciales;
	}
	
	
	/**
	 * Método que devuelve el curso del alumno.
	 * @return curso
	 */
	public Curso getCurso() {
		return curso;
	}

	/**
	 * Método que modifica el curso del alumno.
	 * @param curso
	 */
	public void setCurso(Curso curso) {
		if(curso == null) {
			throw new NullPointerException("ERROR: El curso no puede ser nulo.");
		}
		this.curso = curso;
	}

	
	/********OTROS MÉTODOS********/
	/**
	 * Método hashCode.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((correo == null) ? 0 : correo.hashCode());
		return result;
	}

	/**
	 * Método para comparar dos alumnos.
	 * Dos alumnos son iguales si tienen el mismo correo.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Alumno other = (Alumno) obj;
		if (correo == null) {
			if (other.correo != null)
				return false;
		} else if (!correo.equals(other.correo))
			return false;
		return true;
	}
	
	/**
	 * Método que muestra la información del alumno:
	 * nombre, iniciales, correo y curso.
	 */
	@Override
	public String toString() {
		return String.format("nombre=%s (%s), correo=%s, curso=%s", 
				formateaNombre(nombre), getIniciales(), correo, curso);
	}

	
}
