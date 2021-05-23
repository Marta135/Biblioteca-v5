package org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.controladoresvistas;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.iesalandalus.programacion.biblioteca.mvc.controlador.IControlador;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.AudioLibro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Curso;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.LibroEscrito;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;
import org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.utilidades.Dialogos;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControladorVentanaPrincipal {

	private static final String BORRAR_ALUMNO = "Borrar Alumno";
	private static final String BORRAR_LIBRO = "Borrar Libro";
	private static final String BORRAR_PRESTAMO = "Eliminar Préstamo";
	private static final String DEVOLVER_PRESTAMO = "Devolver Préstamo";
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final String PRESTAMO_BORRADO_OK = "Préstamo eliminado correctamente";
	private static final String SEGURO_BORRAR_PRESTAMO = "¿Estás seguro de que quieres eliminar el préstamo?";
	//private static final String PUNTOS = "puntos";

	private ObservableList<Alumno> alumnos = FXCollections.observableArrayList();
	private ObservableList<Prestamo> prestamosAlumno = FXCollections.observableArrayList();
	private ObservableList<Libro> libros = FXCollections.observableArrayList();
	private ObservableList<Prestamo> prestamosLibro = FXCollections.observableArrayList();
	private ObservableList<Prestamo> prestamos = FXCollections.observableArrayList();
	
	private IControlador controladorMVC;
	
	@FXML private TableView<Alumno> tvAlumnos;
	@FXML private TableColumn<Alumno, String> tcNombreAlumno;
	@FXML private TableColumn<Alumno, String> tcCorreo;
	@FXML private TableColumn<Alumno, Curso> tcCurso;
	
	@FXML private TableView<Prestamo> tvPrestamosAlumno;
	@FXML private TableColumn<Prestamo, String> tcPALibro;
	@FXML private TableColumn<Prestamo, String> tcPAFechaPrestamo;
	@FXML private TableColumn<Prestamo, String> tcPAFechaDevolucion;
	@FXML private TableColumn<Prestamo, String> tcPAPuntos;
	
	@FXML private TableView<Libro> tvLibros;
	@FXML private TableColumn<Libro, String> tcTitulo;
	@FXML private TableColumn<Libro, String> tcAutor;
    @FXML private TableColumn<Libro, String> tcTipo;
    @FXML private TableColumn<Libro, String> tcPaginas;
	
	@FXML private TableView<Prestamo> tvPrestamosLibro;
	@FXML private TableColumn<Prestamo, String> tcPLAlumno;
	@FXML private TableColumn<Prestamo, String> tcPLFechaPrestamo;
	@FXML private TableColumn<Prestamo, String> tcPLFechaDevolucion;
	@FXML private TableColumn<Prestamo, String> tcPLPuntos;
	
	@FXML private TableView<Prestamo> tvPrestamos;
	@FXML private TableColumn<Prestamo, String> tcAlumno;
	@FXML private TableColumn<Prestamo, String> tcLibro;
	@FXML private TableColumn<Prestamo, String> tcFechaPrestamo;
	@FXML private TableColumn<Prestamo, String> tcFechaDevolucion;
	@FXML private TableColumn<Prestamo, String> tcPuntos;
    @FXML private Label primeroESO;
    @FXML private DatePicker dpMes;
    @FXML private CheckBox cbMes; 

	private Stage anadirAlumno;
	private ControladorAnadirAlumno cAnadirAlumno;
	private Stage anadirLibro;
	private ControladorAnadirLibro cAnadirLibro;
	private Stage anadirPrestamo;
	private ControladorAnadirPrestamo cAnadirPrestamo; 
    
	public void setControladorMVC(IControlador controladorMVC) {
		this.controladorMVC = controladorMVC;
	}

	@FXML
	private void initialize() {
		//Tabla alumnos
		tcNombreAlumno.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		tcCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
		tcCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
		tvAlumnos.setItems(alumnos);
		tvAlumnos.getSelectionModel().selectedItemProperty().addListener((ob, ov, nv) -> mostrarPrestamosAlumno(nv));	
	
		//Tabla libros
		tcTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
		tcAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
		tcTipo.setCellValueFactory(libro -> new SimpleStringProperty(libro.getValue().getNombreClase()));
		tcPaginas.setCellValueFactory(libro -> new SimpleStringProperty(getLibroString(libro.getValue())));
		tvLibros.setItems(libros);
		tvLibros.getSelectionModel().selectedItemProperty().addListener((ob, ov, nv) -> mostrarPrestamosLibro(nv));;
		
		//Tabla préstamos
		tcAlumno.setCellValueFactory(prestamo -> new SimpleStringProperty(prestamo.getValue().getAlumno().getNombre() + "\n" + prestamo.getValue().getAlumno().getCorreo()));
		tcLibro.setCellValueFactory(prestamo -> new SimpleStringProperty(prestamo.getValue().getLibro().getTitulo() + "\n" + prestamo.getValue().getLibro().getAutor()));
		tcFechaPrestamo.setCellValueFactory(prestamo -> new SimpleStringProperty(FORMATO_FECHA.format(prestamo.getValue().getFechaPrestamo())));
		tcFechaDevolucion.setCellValueFactory(prestamo -> new SimpleStringProperty(mostrarFechaDevolucion(prestamo.getValue())));
		tcPuntos.setCellValueFactory(prestamo -> new SimpleStringProperty(Integer.toString(prestamo.getValue().getPuntos())));
		tvPrestamos.setItems(prestamos);
		
		//Tabla préstamos alumno
		tcPALibro.setCellValueFactory(prestamo -> new SimpleStringProperty(prestamo.getValue().getLibro().getTitulo() + ", " + prestamo.getValue().getLibro().getAutor()));
		tcPAFechaPrestamo.setCellValueFactory(prestamo -> new SimpleStringProperty(FORMATO_FECHA.format(prestamo.getValue().getFechaPrestamo())));
		tcPAFechaDevolucion.setCellValueFactory(prestamo -> new SimpleStringProperty(mostrarFechaDevolucion(prestamo.getValue())));
		tcPAPuntos.setCellValueFactory(prestamo -> new SimpleStringProperty(Integer.toString(prestamo.getValue().getPuntos())));
		tvPrestamosAlumno.setItems(prestamosAlumno);
		
		//Tabla préstamos libro
		tcPLAlumno.setCellValueFactory(prestamo -> new SimpleStringProperty(prestamo.getValue().getAlumno().getNombre() + ", " + prestamo.getValue().getAlumno().getCorreo()));
		tcPLFechaPrestamo.setCellValueFactory(prestamo -> new SimpleStringProperty(FORMATO_FECHA.format(prestamo.getValue().getFechaPrestamo())));
		tcPLFechaDevolucion.setCellValueFactory(prestamo -> new SimpleStringProperty(mostrarFechaDevolucion(prestamo.getValue())));
		tcPLPuntos.setCellValueFactory(prestamo -> new SimpleStringProperty(Integer.toString(prestamo.getValue().getPuntos())));
		tvPrestamosLibro.setItems(prestamosLibro);
		
		//Mes
		dpMes.setValue(null);
		cbMes.selectedProperty().addListener((ob, ov, nv) -> mostrarPrestamosGlobal(nv));
		dpMes.valueProperty().addListener((ob, ov, nv) -> mostrarPrestamosMes(nv));
	}
	




	@FXML
	void salir() {
		if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estás seguro de que desea salir de la aplicación?", null)) {
			controladorMVC.terminar();
			System.exit(0);
		}
	}
	
	@FXML
	void acercaDe() throws IOException {
		VBox contenido = FXMLLoader.load(LocalizadorRecursos.class.getResource("vistas/AcercaDe.fxml"));
		Dialogos.mostrarDialogoInformacionPersonalizado("Gestión de la Biblioteca", contenido);
	}
	

	@FXML
	void anadirAlumno(ActionEvent event) throws IOException {
		crearAnadirAlumno();
		anadirAlumno.showAndWait();
	}

	@FXML
	void borrarAlumno(ActionEvent event) {
		Alumno alumno = null;
		try {
			alumno = tvAlumnos.getSelectionModel().getSelectedItem();
			if (alumno != null && Dialogos.mostrarDialogoConfirmacion(BORRAR_ALUMNO, "¿Estás seguro de que quieres borrar el alumno?", null)) {
				controladorMVC.borrar(alumno);
				alumnos.remove(alumno);
				prestamosAlumno.clear();
				actualizaAlumnos();
				Dialogos.mostrarDialogoInformacion(BORRAR_ALUMNO, "Alumno borrado correctamente");
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError(BORRAR_ALUMNO, e.getMessage());
		}
	}

	@FXML
	void anadirLibro(ActionEvent event) throws IOException {
		crearAnadirLibro();
	    anadirLibro.showAndWait();
	}

	@FXML
	void borrarLibro(ActionEvent event) {
		Libro libro = null;
			try {
				libro = tvLibros.getSelectionModel().getSelectedItem();
				if (libro != null && Dialogos.mostrarDialogoConfirmacion(BORRAR_LIBRO, "¿Estás seguro de que quieres borrar el libro?", null)) {
					controladorMVC.borrar(libro);
					libros.remove(libro);
					prestamosLibro.clear();
					actualizaLibros();
					Dialogos.mostrarDialogoInformacion(BORRAR_LIBRO, "Libro borrado correctamente");
				}
			} catch (Exception e) {
				Dialogos.mostrarDialogoError(BORRAR_LIBRO, e.getMessage());
			}
	}
	
    @FXML
    void anadirPrestamo(ActionEvent event) throws IOException {
    	crearAnadirPrestamo();
		anadirPrestamo.showAndWait();
    }
    
    @FXML
    void anadirPrestamoAlumno(ActionEvent event) throws IOException {
    	crearAnadirPrestamoAlumno();
		anadirPrestamo.showAndWait();
    }

	@FXML
    void anadirPrestamoLibro(ActionEvent event) throws IOException {
    	crearAnadirPrestamoLibro();
		anadirPrestamo.showAndWait();
    }

	@FXML
    void devolverPrestamoAlumno(ActionEvent event) {
    	Prestamo prestamo = null;
    	try {
    		prestamo = tvPrestamosAlumno.getSelectionModel().getSelectedItem();
    		if (prestamo != null && Dialogos.mostrarDialogoConfirmacion(DEVOLVER_PRESTAMO, "¿Estás seguro de que quieres devolver el libro?", null)) {
    			controladorMVC.devolver(prestamo, LocalDate.now());
    			actualizaAlumnos();
    			actualizaPrestamos();
    			Dialogos.mostrarDialogoInformacion(DEVOLVER_PRESTAMO, "Libro devuelto correctamente");
    		}
    	} catch (Exception e) {
    		Dialogos.mostrarDialogoError(DEVOLVER_PRESTAMO, e.getMessage());
    	}
    }

    @FXML
    void devolverPrestamoLibro(ActionEvent event) {
    	Prestamo prestamo = null;
    	try {
    		prestamo = tvPrestamosLibro.getSelectionModel().getSelectedItem();
    		if (prestamo != null && Dialogos.mostrarDialogoConfirmacion(DEVOLVER_PRESTAMO, "¿Estás seguro de que quieres devolver el libro?", null)) {
    			controladorMVC.devolver(prestamo, LocalDate.now());
    			actualizaLibros();
    			actualizaPrestamos();
    			Dialogos.mostrarDialogoInformacion(DEVOLVER_PRESTAMO, "Libro devuelto correctamente");
    		}
    	} catch (Exception e) {
    		Dialogos.mostrarDialogoError(DEVOLVER_PRESTAMO, e.getMessage());
    	}
    }
    
    @FXML
    void devolverPrestamoGlobal(ActionEvent event) {
    	Prestamo prestamo = null;
    	try {
    		prestamo = tvPrestamos.getSelectionModel().getSelectedItem();
    		if (prestamo != null && Dialogos.mostrarDialogoConfirmacion(DEVOLVER_PRESTAMO, "¿Estás seguro de que quieres devolver el libro?", null)) {
    			controladorMVC.devolver(prestamo, LocalDate.now());
    			actualizaPrestamos();
    			Dialogos.mostrarDialogoInformacion(DEVOLVER_PRESTAMO, "Libro devuelto correctamente");
    		}
    	} catch (Exception e) {
    		Dialogos.mostrarDialogoError(DEVOLVER_PRESTAMO, e.getMessage());
    	}
    }
    
    @FXML
    void borrarPrestamoAlumno(ActionEvent event) {
    	Prestamo prestamo = null;
		try {
			prestamo = tvPrestamosAlumno.getSelectionModel().getSelectedItem();
			if (prestamo != null && Dialogos.mostrarDialogoConfirmacion(BORRAR_PRESTAMO, SEGURO_BORRAR_PRESTAMO, null)) {
				controladorMVC.borrar(prestamo);
				prestamos.remove(prestamo);
				actualizaAlumnos();
				Dialogos.mostrarDialogoInformacion(BORRAR_PRESTAMO, PRESTAMO_BORRADO_OK);
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError(BORRAR_PRESTAMO, e.getMessage());
		}
    }

    @FXML
    void borrarPrestamoLibro(ActionEvent event) {
    	Prestamo prestamo = null;
		try {
			prestamo = tvPrestamosLibro.getSelectionModel().getSelectedItem();
			if (prestamo != null && Dialogos.mostrarDialogoConfirmacion(BORRAR_PRESTAMO, SEGURO_BORRAR_PRESTAMO, null)) {
				controladorMVC.borrar(prestamo);
				prestamos.remove(prestamo);
				actualizaLibros();
				Dialogos.mostrarDialogoInformacion(BORRAR_PRESTAMO, PRESTAMO_BORRADO_OK);
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError(BORRAR_PRESTAMO, e.getMessage());
		}
    }

    @FXML
    void eliminarPrestamoGlobal(ActionEvent event) {
    	Prestamo prestamo = null;
		try {
			prestamo = tvPrestamos.getSelectionModel().getSelectedItem();
			if (prestamo != null && Dialogos.mostrarDialogoConfirmacion(BORRAR_PRESTAMO, SEGURO_BORRAR_PRESTAMO, null)) {
				controladorMVC.borrar(prestamo);
				prestamos.remove(prestamo);
				actualizaLibros();
				actualizaAlumnos();
				Dialogos.mostrarDialogoInformacion(BORRAR_PRESTAMO, PRESTAMO_BORRADO_OK);
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError(BORRAR_PRESTAMO, e.getMessage());
		}
    }
	
    public void actualizaAlumnos() {
    	prestamosLibro.clear();
    	tvLibros.getSelectionModel().clearSelection();
    	alumnos.setAll(controladorMVC.getAlumnos());
    }
    
    public void actualizaLibros() {
    	prestamosAlumno.clear();
    	tvAlumnos.getSelectionModel().clearSelection();
    	libros.setAll(controladorMVC.getLibros());
    }
    
    public void actualizaPrestamos() {
    	prestamos.setAll(controladorMVC.getPrestamos());
    	actualizaEstadisticasMensuales();
    }
  
    public void actualizaPrestamosPorMes(LocalDate mes) {
    	prestamos.setAll(controladorMVC.getPrestamos(mes));
    	actualizaEstadisticasMensuales();
    }
  
	private void actualizaEstadisticasMensuales() {
		if (dpMes.getValue() != null) {
			primeroESO.setText("Estadísticas por cursos: " + controladorMVC.getEstadisticaMensualPorCurso(dpMes.getValue()).toString());
		} else {
			primeroESO.setText("Estadísticas por cursos: " + controladorMVC.getEstadisticaMensualPorCurso(LocalDate.now()).toString());
		}
	}

    public void mostrarPrestamosAlumno(Alumno alumno) {
    	try {
    		if (alumno != null) {
    			prestamosAlumno.setAll(controladorMVC.getPrestamos(alumno));
    		}
    	} catch (IllegalArgumentException e) {
    		prestamosAlumno.setAll(FXCollections.observableArrayList());
    	}
    	actualizaPrestamos();
    }
    
    public void mostrarPrestamosLibro(Libro libro) {
    	try {
    		if (libro != null) {
    			prestamosLibro.setAll(controladorMVC.getPrestamos(libro));
    		}
    	} catch (IllegalArgumentException e) {
    		prestamosLibro.setAll(FXCollections.observableArrayList());
    	}
    	actualizaPrestamos();
    }
    
    public void mostrarPrestamosMes(LocalDate mes) {
    	if (mes != null) {
    		actualizaPrestamosPorMes(mes);
    	}
    }
	
    public void mostrarPrestamosGlobal(boolean selected) {
    	if (selected) {
    		dpMes.setDisable(false);
    		mostrarPrestamosMes(dpMes.getValue());
    	} else {
    		dpMes.setDisable(true);
    		actualizaPrestamos();
    	}
    }
    
	private String mostrarFechaDevolucion(Prestamo prestamo) {
		if (prestamo.getFechaDevolucion() == null) {
			return "";
		} else {
			return FORMATO_FECHA.format(prestamo.getFechaDevolucion());
		}
	}
    
	private String getLibroString(Libro libro) {
		String paginasDuracion = "";
		if (libro instanceof LibroEscrito) {
			paginasDuracion = Integer.toString(((LibroEscrito) libro).getNumPaginas()) + " páginas";
		} else if (libro instanceof AudioLibro) {
			paginasDuracion = Integer.toString(((AudioLibro) libro).getDuracion()) + " minutos";
		}
		return paginasDuracion;
	}    
	
	private void crearAnadirAlumno() throws IOException {
	   	if (anadirAlumno == null) {
	   		anadirAlumno = new Stage();
			FXMLLoader cargadorAnadirAlumno = new FXMLLoader(
					LocalizadorRecursos.class.getResource("vistas/AnadirAlumno.fxml"));
			VBox raizAnadirAlumno = cargadorAnadirAlumno.load();
			cAnadirAlumno = cargadorAnadirAlumno.getController();
			cAnadirAlumno.setControladorMVC(controladorMVC);
			cAnadirAlumno.setAlumnos(alumnos);
			cAnadirAlumno.inicializa();
			Scene escenaAnadirAlumno = new Scene(raizAnadirAlumno);
			anadirAlumno.setTitle("Añadir Alumno");
			anadirAlumno.initModality(Modality.APPLICATION_MODAL); 
			anadirAlumno.setScene(escenaAnadirAlumno);
		} else {
			cAnadirAlumno.inicializa();
		}
	}
	
	private void crearAnadirLibro() throws IOException {
		if (anadirLibro == null) {
			anadirLibro = new Stage();
			FXMLLoader cargadorAnadirLibro = new FXMLLoader(
					LocalizadorRecursos.class.getResource("vistas/AnadirLibro.fxml"));
			VBox raizAnadirLibro = cargadorAnadirLibro.load();
			cAnadirLibro = cargadorAnadirLibro.getController();
			cAnadirLibro.setControladorMVC(controladorMVC);
			cAnadirLibro.setLibros(libros);
			cAnadirLibro.inicializa();
			Scene escenaAnadirLibro = new Scene(raizAnadirLibro);
			anadirLibro.setTitle("Añadir Libro");
			anadirLibro.initModality(Modality.APPLICATION_MODAL); 
			anadirLibro.setScene(escenaAnadirLibro);
		} else {
			cAnadirLibro.inicializa();
		}
	}	    
	
    private void crearAnadirPrestamo() throws IOException {
		if (anadirPrestamo == null) {
			anadirPrestamo = new Stage();
			FXMLLoader cargadorAnadirPrestamo = new FXMLLoader(
					LocalizadorRecursos.class.getResource("vistas/AnadirPrestamo.fxml"));
			VBox raizAnadirPrestamo = cargadorAnadirPrestamo.load();
			cAnadirPrestamo = cargadorAnadirPrestamo.getController();
			cAnadirPrestamo.setControladorMVC(controladorMVC);
			cAnadirPrestamo.setAlumnos(alumnos);
			cAnadirPrestamo.setLibros(libros);
			cAnadirPrestamo.setPadre(this);
			cAnadirPrestamo.inicializa();
			Scene escenaAnadirPrestamo = new Scene(raizAnadirPrestamo);
			anadirPrestamo.setTitle("Añadir Préstamo");
			anadirPrestamo.initModality(Modality.APPLICATION_MODAL); 
			anadirPrestamo.setScene(escenaAnadirPrestamo);
		} else {
			cAnadirPrestamo.setAlumnos(alumnos);
			cAnadirPrestamo.setLibros(libros);
			cAnadirPrestamo.inicializa();
		}
	}		


    private void crearAnadirPrestamoAlumno() throws IOException {
    	if (anadirPrestamo == null) {
			anadirPrestamo = new Stage();
			FXMLLoader cargadorAnadirPrestamo = new FXMLLoader(
					LocalizadorRecursos.class.getResource("vistas/AnadirPrestamo.fxml"));
			VBox raizAnadirPrestamo = cargadorAnadirPrestamo.load();
			cAnadirPrestamo = cargadorAnadirPrestamo.getController();
			cAnadirPrestamo.setControladorMVC(controladorMVC);
			cAnadirPrestamo.setAlumnos(alumnos);
			cAnadirPrestamo.setLibros(libros);
			cAnadirPrestamo.setPadre(this);
			cAnadirPrestamo.inicializa();
			Scene escenaAnadirPrestamo = new Scene(raizAnadirPrestamo);
			anadirPrestamo.setTitle("Añadir Préstamo");
			anadirPrestamo.initModality(Modality.APPLICATION_MODAL); 
			anadirPrestamo.setScene(escenaAnadirPrestamo);
		} else {
			cAnadirPrestamo.setAlumnosBool(true);
			cAnadirPrestamo.setLibrosBool(false);
			cAnadirPrestamo.setAlumnos(alumnos);
			cAnadirPrestamo.setLibros(libros);
			cAnadirPrestamo.inicializa();
		}
	}			

    private void crearAnadirPrestamoLibro() throws IOException {
    	if (anadirPrestamo == null) {
			anadirPrestamo = new Stage();
			FXMLLoader cargadorAnadirPrestamo = new FXMLLoader(
					LocalizadorRecursos.class.getResource("vistas/AnadirPrestamo.fxml"));
			VBox raizAnadirPrestamo = cargadorAnadirPrestamo.load();
			cAnadirPrestamo = cargadorAnadirPrestamo.getController();
			cAnadirPrestamo.setControladorMVC(controladorMVC);
			cAnadirPrestamo.setAlumnos(alumnos);
			cAnadirPrestamo.setLibros(libros);
			cAnadirPrestamo.setPadre(this);
			cAnadirPrestamo.inicializa();
			Scene escenaAnadirPrestamo = new Scene(raizAnadirPrestamo);
			anadirPrestamo.setTitle("Añadir Préstamo");
			anadirPrestamo.initModality(Modality.APPLICATION_MODAL); 
			anadirPrestamo.setScene(escenaAnadirPrestamo);
		} else {
			cAnadirPrestamo.setLibrosBool(true);
			cAnadirPrestamo.setAlumnosBool(false);
			cAnadirPrestamo.setAlumnos(alumnos);
			cAnadirPrestamo.setLibros(libros);
			cAnadirPrestamo.inicializa();
		}
	}   
    
}
