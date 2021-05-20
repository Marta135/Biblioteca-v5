package org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.controladoresvistas;

import java.time.format.DateTimeFormatter;

import org.iesalandalus.programacion.biblioteca.mvc.controlador.IControlador;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;
import org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.utilidades.Dialogos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ControladorRealizarDevolucion {

	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private IControlador controladorMVC;
	private ControladorVentanaPrincipal padre;
	private Prestamo prestamo;

	@FXML private Label lbAlumno;
    @FXML private Label lbLibro;
    @FXML private Label lbFechaPrestamo;
    @FXML private DatePicker dpFechaDevolucion;
    @FXML private Button btAceptar;
    @FXML private Button btCancelar;
    
    
    public void setControlador(IControlador controladorMVC) {
		this.controladorMVC = controladorMVC;
	}
   
    public void setPadre(ControladorVentanaPrincipal padre) {
    	this.padre = padre;
	}
    
    public void setPrestamo(Prestamo prestamo) {
    	this.prestamo = prestamo;
    }
    
	public void inicializa() {
		lbAlumno.setText(prestamo.getAlumno().getCorreo());
		lbLibro.setText(prestamo.getLibro().getTitulo()+ ", " + prestamo.getLibro().getAutor());
		lbFechaPrestamo.setText(FORMATO_FECHA.format(prestamo.getFechaPrestamo()));
		dpFechaDevolucion.setValue(null);
	}
    
    @FXML
    void cancelar(ActionEvent event) {
    	((Stage) btCancelar.getScene().getWindow()).close();
    }

    @FXML
    void realizarDevolucion(ActionEvent event) {
    	try {
    		controladorMVC.devolver(prestamo, dpFechaDevolucion.getValue());
    		padre.actualizaAlumnos();
    		padre.actualizaLibros();
    		padre.actualizaPrestamos();
    		Stage propietario = ((Stage) btAceptar.getScene().getWindow());
    		Dialogos.mostrarDialogoInformacion("Realizar Devolución", "Devolución realizada correctamente", propietario);
    	} catch (Exception e) {
    		Dialogos.mostrarDialogoError("Realizar Devolución", e.getMessage());
    	}
    }

}
