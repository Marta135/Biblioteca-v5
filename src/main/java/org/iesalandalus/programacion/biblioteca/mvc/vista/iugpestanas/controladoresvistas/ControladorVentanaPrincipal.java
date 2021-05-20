package org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.controladoresvistas;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import org.iesalandalus.programacion.biblioteca.mvc.controlador.IControlador;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Curso;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControladorVentanaPrincipal {

	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final String BORRAR_ALUMNO = "Borrar Alumno";
	private static final String BORRAR_LIBRO = "Borrar Libro";
	private static final String ELIMINAR_PRESTAMO = "Eliminar Préstamo";
	private static final String PRESTAMO_ELIMINADO_OK = "Préstamo eliminado correctamente";
	private static final String SEGURO_ELIMINAR_PRESTAMO = "¿Estás seguro de que quieres eliminar el préstamo?";
	private static final String PUNTOS = "puntos";
	
	private IControlador controladorMVC;
	
	private ObservableList<Alumno> alumnos = FXCollections.observableArrayList();
	private ObservableList<Prestamo> prestamosAlumno = FXCollections.observableArrayList();
	private ObservableList<Libro> libros = FXCollections.observableArrayList();
	private ObservableList<Prestamo> prestamosLibro = FXCollections.observableArrayList();
	private ObservableList<Prestamo> prestamos = FXCollections.observableArrayList();
	

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
	@FXML private TableColumn<Libro, String> tcDuracion;
	
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
	
	private Stage anadirAlumno;
	private ControladorAnadirAlumno cAnadirAlumno;
	private Stage anadirLibro;
	private ControladorAnadirLibro cAnadirLibro;
	private Stage realizarPrestamo;
	private ControladorRealizarPrestamo cRealizarPrestamo;
	private Stage realizarDevolucion;
	private ControladorRealizarDevolucion cRealizarDevolucion;
	private Stage mostrarEstadistica;
	private ControladorMostrarEstadistica cMostrarEstadistica;
	
	

	public void setControladorMVC(IControlador controladorMVC) {
		this.controladorMVC = controladorMVC;
	}
	
	@FXML
	private void initialize() {
		tcNombreAlumno.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		tcCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
		tcCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
		tvAlumnos.setItems(alumnos);
		tvAlumnos.getSelectionModel().selectedItemProperty().addListener((ob, ov, nv) -> mostrarPrestamosAlumno(nv));
		
		tcPALibro.setCellValueFactory(prestamo -> new SimpleStringProperty(prestamo.getValue().getLibro().getTitulo()));
		tcPAFechaPrestamo.setCellValueFactory(prestamo -> new SimpleStringProperty(FORMATO_FECHA.format(prestamo.getValue().getFechaPrestamo())));
		tcPAFechaDevolucion.setCellValueFactory(prestamo -> new SimpleStringProperty(FORMATO_FECHA.format(prestamo.getValue().getFechaDevolucion())));
		tcPAPuntos.setCellValueFactory(new PropertyValueFactory<>(PUNTOS));
		tvPrestamosAlumno.setItems(prestamosAlumno);
		
		tcTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
		tcAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
		tcTipo.setCellValueFactory(libro -> new SimpleStringProperty(libro.getValue().getNombreClase()));
		tcPaginas.setCellValueFactory(new PropertyValueFactory<>("numPaginas"));
		tcDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));
		tvLibros.setItems(libros);
		tvLibros.getSelectionModel().selectedItemProperty().addListener((ob, ov, nv) -> mostrarPrestamosLibro(nv));;
		
		tcPLAlumno.setCellValueFactory(prestamo -> new SimpleStringProperty(prestamo.getValue().getAlumno().getNombre()));
		tcPLFechaPrestamo.setCellValueFactory(prestamo -> new SimpleStringProperty(FORMATO_FECHA.format(prestamo.getValue().getFechaPrestamo())));
		tcPLFechaDevolucion.setCellValueFactory(prestamo -> new SimpleStringProperty(FORMATO_FECHA.format(prestamo.getValue().getFechaDevolucion())));
		tcPLPuntos.setCellValueFactory(new PropertyValueFactory<>(PUNTOS));
		tvPrestamosLibro.setItems(prestamosLibro);
		
		tcAlumno.setCellValueFactory(prestamo -> new SimpleStringProperty(prestamo.getValue().getAlumno().getNombre()));
		tcLibro.setCellValueFactory(prestamo -> new SimpleStringProperty(prestamo.getValue().getLibro().getTitulo()));
		tcFechaPrestamo.setCellValueFactory(prestamo -> new SimpleStringProperty(FORMATO_FECHA.format(prestamo.getValue().getFechaPrestamo())));
		tcFechaDevolucion.setCellValueFactory(prestamo -> new SimpleStringProperty(FORMATO_FECHA.format(prestamo.getValue().getFechaDevolucion())));
		tcPuntos.setCellValueFactory(new PropertyValueFactory<>(PUNTOS));
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
	
	@FXML
	void anadirLibro(ActionEvent event) throws IOException {
		crearAnadirLibro();
	    anadirLibro.showAndWait();
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
	
	@FXML
	void realizarPrestamo(ActionEvent event) throws IOException {
		crearRealizarPrestamo();
		realizarPrestamo.showAndWait();
	}	 

    private void crearRealizarPrestamo() throws IOException {
		if (realizarPrestamo == null) {
			realizarPrestamo = new Stage();
			FXMLLoader cargadorRealizarPrestamo = new FXMLLoader(
					LocalizadorRecursos.class.getResource("vistas/RealizarPrestamo.fxml"));
			VBox raizRealizarPrestamo = cargadorRealizarPrestamo.load();
			cRealizarPrestamo = cargadorRealizarPrestamo.getController();
			cRealizarPrestamo.setControladorMVC(controladorMVC);
			cRealizarPrestamo.setPadre(this);
			cRealizarPrestamo.setAlumnos(alumnos);
			cRealizarPrestamo.setLibros(libros);
			Scene escenaRealizarPrestamo = new Scene(raizRealizarPrestamo);
			realizarPrestamo.setTitle("Realizar Préstamo");
			realizarPrestamo.initModality(Modality.APPLICATION_MODAL); 
			realizarPrestamo.setScene(escenaRealizarPrestamo);
			cRealizarPrestamo.inicializa();
		} else {
			cRealizarPrestamo.inicializa();
		}
	}	
	
    @FXML
    void realizarDevolucion(ActionEvent event) throws IOException {
    	crearRealizarDevolucion();
    	realizarDevolucion.showAndWait();
    }
    
    private void crearRealizarDevolucion() throws IOException {
    	if (realizarDevolucion == null) {
    		realizarDevolucion = new Stage();
    		FXMLLoader cargadorRealizarDevolucion = new FXMLLoader(
    				LocalizadorRecursos.class.getResource("vistas/RealizarDevolucion.fxml"));
    		VBox raizRealizarDevolucion = cargadorRealizarDevolucion.load();
    		cRealizarDevolucion = cargadorRealizarDevolucion.getController();
    		cRealizarDevolucion.setControlador(controladorMVC);
    		cRealizarDevolucion.setPadre(this);
    		cRealizarDevolucion.setPrestamo(tvPrestamos.getSelectionModel().getSelectedItem());
    		Scene escenaRealizarDevolucion = new Scene(raizRealizarDevolucion);
    		realizarDevolucion.setTitle("Realizar Devolución");
    		realizarDevolucion.initModality(Modality.APPLICATION_MODAL);
    		realizarDevolucion.setScene(escenaRealizarDevolucion);
    		cRealizarDevolucion.inicializa();
       	} else {
       		cRealizarDevolucion.inicializa();
       	}
    }
    
    @FXML
    void mostrarEstadistica(ActionEvent event) throws IOException {
    	crearMostrarEstadistica();
    	mostrarEstadistica.showAndWait();
    }
    
    public void crearMostrarEstadistica() throws IOException {
    	if (mostrarEstadistica == null) {
    		mostrarEstadistica = new Stage();
    		FXMLLoader cargadorMostrarEstadistica = new FXMLLoader(
    				LocalizadorRecursos.class.getResource("vistas/MostrarEstadistica.fxml"));
    		VBox raizMostrarEstadistica = cargadorMostrarEstadistica.load();
    		cMostrarEstadistica = cargadorMostrarEstadistica.getController();
    		cMostrarEstadistica.setControlador(controladorMVC);
    		Scene escenaMostrarEstadistica = new Scene(raizMostrarEstadistica);
    		mostrarEstadistica.setTitle("Mostrar Estadísticas");
    		mostrarEstadistica.initModality(Modality.APPLICATION_MODAL);
    		mostrarEstadistica.setScene(escenaMostrarEstadistica);
    		cMostrarEstadistica.inicializa();
    	} else {
    		cMostrarEstadistica.inicializa();
    	}
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
	void borrarLibro(ActionEvent event) {
		Libro libro = null;
			try {
				libro = tvLibros.getSelectionModel().getSelectedItem();
				if (libro != null && Dialogos.mostrarDialogoConfirmacion(BORRAR_LIBRO, "¿Estás seguro de que quieres borrar el libro?", null)) {
					controladorMVC.borrar(libro);
					libros.remove(libro);
					prestamosLibro.clear();
					actualizaLibros();
					Dialogos.mostrarDialogoInformacion(BORRAR_LIBRO, "Libro borrado satisfactoriamente");
				}
			} catch (Exception e) {
				Dialogos.mostrarDialogoError(BORRAR_LIBRO, e.getMessage());
			}
	}
	 
	@FXML
	void eliminarPrestamo(ActionEvent event) {
		Prestamo prestamo = null;
		try {
			prestamo = tvPrestamos.getSelectionModel().getSelectedItem();
			if (prestamo != null && Dialogos.mostrarDialogoConfirmacion(ELIMINAR_PRESTAMO, SEGURO_ELIMINAR_PRESTAMO, null)) {
				controladorMVC.borrar(prestamo);
				prestamos.remove(prestamo);
				actualizaPrestamos();
				actualizaLibros();
				actualizaAlumnos();
				Dialogos.mostrarDialogoInformacion(ELIMINAR_PRESTAMO, PRESTAMO_ELIMINADO_OK);
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError(ELIMINAR_PRESTAMO, e.getMessage());
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
    
 
	
    
    
}
