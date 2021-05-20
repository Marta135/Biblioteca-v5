package org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.controladoresvistas;

import java.time.LocalDate;
import java.util.Map;

import org.iesalandalus.programacion.biblioteca.mvc.controlador.IControlador;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Curso;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class ControladorMostrarEstadistica {
	
	private IControlador controladorMVC;
	
    @FXML private DatePicker dpFechaEstadistica;
	@FXML private TableView<Curso> tvEstadisticas;
	@FXML private TableColumn<Curso, String> tcPrimero;
	@FXML private TableColumn<Curso, String> tcSegundo;
	@FXML private TableColumn<Curso, String> tcTercero;
	@FXML private TableColumn<Curso, String> tcCuarto;
	@FXML private Button btAceptar;
	@FXML private Button btCancelar;

	
	public void setControlador(IControlador controladorMVC) {
		this.controladorMVC = controladorMVC;
	}
	
	@FXML
	private void inicialize() {
		dpFechaEstadistica.valueProperty().addListener((ob, ov, nv) -> estadisticaMensual());
	}
	
	private void estadisticaMensual() {
		LocalDate fecha = dpFechaEstadistica.getValue();
		Map<Curso,Integer> mapa = controladorMVC.getEstadisticaMensualPorCurso(fecha);
		tcPrimero.setText(mapa.get(Curso.PRIMERO).toString());
		tcSegundo.setText(mapa.get(Curso.SEGUNDO).toString());
		tcTercero.setText(mapa.get(Curso.TERCERO).toString());
		tcCuarto.setText(mapa.get(Curso.CUARTO).toString());
	}
	
	@FXML
	void limpiar(ActionEvent event) {
		inicializa();
    }
	    
	@FXML
    void cancelar(ActionEvent event) {
		((Stage) btCancelar.getScene().getWindow()).close();
	}

	public void inicializa() {
		dpFechaEstadistica.setValue(null);
		tcPrimero.setText("");
		tcSegundo.setText("");
		tcTercero.setText("");
		tcCuarto.setText("");
	}	    
 
}
