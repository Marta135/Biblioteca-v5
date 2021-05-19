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
	private static final String PUNTOS = "puntos";
	
	private IControlador controladorMVC;
	
	private ObservableList<Alumno> alumnos = FXCollections.observableArrayList();
	private ObservableList<Prestamo> prestamosAlumno = FXCollections.observableArrayList();
	private ObservableList<Libro> libros = FXCollections.observableArrayList();
	private ObservableList<Prestamo> prestamosLibro = FXCollections.observableArrayList();
	private ObservableList<Prestamo> prestamos = FXCollections.observableArrayList();
	

	
	public void setControladorMVC(IControlador controladorMVC) {
		this.controladorMVC = controladorMVC;
	}
	
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
	
	
	@FXML
	private void initialize() {
		tcNombreAlumno.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		tcCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
		tcCurso.setCellValueFactory(new PropertyValueFactory<>("correo"));
		tvAlumnos.setItems(alumnos);
		tvAlumnos.getSelectionModel().selectedItemProperty().addListener((ob, ov, nv) -> mostrarPrestamosAlumno(nv));
		
		tcPALibro.setCellValueFactory(prestamo -> new SimpleStringProperty(prestamo.getValue().getLibro().getTitulo()));
		tcPAFechaPrestamo.setCellValueFactory(prestamo -> new SimpleStringProperty(FORMATO_FECHA.format(prestamo.getValue().getFechaPrestamo())));
		tcPAFechaDevolucion.setCellValueFactory(prestamo -> new SimpleStringProperty(FORMATO_FECHA.format(prestamo.getValue().getFechaDevolucion())));
		tcPAPuntos.setCellValueFactory(new PropertyValueFactory<>(PUNTOS));
		tvPrestamosAlumno.setItems(prestamosAlumno);
		
	}
	
	@FXML
	void salir() {
		if (Dialogos.mostrarDialogoConfirmacion("Salir", "¿Estás seguro de que quieres salir de la aplicación?", null)) {
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
	
	
    public void actualizaAlumnos() {
    	prestamosLibro.clear();
    	tvLibros.getSelectionModel().clearSelection();
    	alumnos.setAll(controladorMVC.getAlumnos());
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
    
   
    
}
