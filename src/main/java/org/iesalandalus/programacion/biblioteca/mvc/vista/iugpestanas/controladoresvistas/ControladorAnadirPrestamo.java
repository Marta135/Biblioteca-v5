package org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.controladoresvistas;

import java.time.LocalDate;

import org.iesalandalus.programacion.biblioteca.mvc.controlador.IControlador;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;
import org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.utilidades.Dialogos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class ControladorAnadirPrestamo {

	private IControlador controladorMVC;
	private ControladorVentanaPrincipal padre;
	private ObservableList<Alumno> alumnos = FXCollections.observableArrayList();
	private ObservableList<Libro> libros = FXCollections.observableArrayList();
	private boolean alumnosBool, librosBool;
	
	@FXML private ListView<Alumno> lvAlumno;
	@FXML private ListView<Libro> lvLibro;
    @FXML private DatePicker dpFechaPrestamo;
    @FXML private Button btAceptar;
    @FXML private Button btCancelar;


    private class CeldaAlumno extends ListCell<Alumno> {
    	@Override
    	public void updateItem(Alumno alumno, boolean vacio) {
    		super.updateItem(alumno, vacio);
    		if (vacio) {
    			setText("");
    		} else {
    			setText(alumno.getNombre());
    		}
    	}
    }
    
    private class CeldaLibro extends ListCell<Libro> {
    	@Override
    	public void updateItem(Libro libro, boolean vacio) {
    		super.updateItem(libro, vacio);
    		if (vacio) {
    			setText("");
    		} else {
    			setText(libro.getTitulo());
    		}
    	}
    }
    
    @FXML
    private void initialize() {
    	lvAlumno.setItems(alumnos);
    	lvAlumno.setCellFactory(lv -> new CeldaAlumno());
    	lvLibro.setItems(libros);
    	lvLibro.setCellFactory(lv -> new CeldaLibro());
    }
    
	public void setControladorMVC(IControlador controladorMVC) {
		this.controladorMVC = controladorMVC;
	}
	
	public void setPadre(ControladorVentanaPrincipal padre) {
		this.padre = padre;	
	}

	public void setAlumnos(ObservableList<Alumno> alumnos) {
		this.alumnos.setAll(alumnos);
	}

	public void setLibros(ObservableList<Libro> libros) {
		this.libros.setAll(libros);
	}

	public void inicializa() {
		lvAlumno.getSelectionModel().clearSelection();
		lvLibro.getSelectionModel().clearSelection();
		lvAlumno.setItems(alumnos);
		lvLibro.setItems(libros);
		dpFechaPrestamo.setValue(null);
	}

	public void setAlumnosBool(boolean trigger) {
		alumnosBool = trigger;
	}
	
	public void setLibrosBool(boolean trigger) {
		librosBool = trigger;
	}
	
	@FXML
	void anadirPrestamo(ActionEvent event) {
		Prestamo prestamo = null;
		try {
			prestamo = getPrestamo();
			controladorMVC.prestar(prestamo);
			padre.actualizaPrestamos();
			if (alumnosBool) {
				padre.mostrarPrestamosAlumno(prestamo.getAlumno());
				alumnosBool = false;
			}
			if (librosBool) {
				padre.mostrarPrestamosLibro(prestamo.getLibro());
				librosBool = false;
			}
			Stage propietario = ((Stage) btAceptar.getScene().getWindow());
			Dialogos.mostrarDialogoInformacion("Realizar Préstamo", "Préstamo realizado correctamente", propietario);
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Realizar Préstamo", e.getMessage());
		}
    }
	
	@FXML
	void cancelar(ActionEvent event) {
		((Stage) btCancelar.getScene().getWindow()).close();
	}
	
	private Prestamo getPrestamo() {
		Alumno alumno = lvAlumno.getSelectionModel().getSelectedItem();
		Libro libro = lvLibro.getSelectionModel().getSelectedItem();
		LocalDate fechaPrestamo = dpFechaPrestamo.getValue();
		return new Prestamo(alumno, libro, fechaPrestamo);
	}
	

		

}
