package org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.controladoresvistas;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.controlador.IControlador;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.AudioLibro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.LibroEscrito;
import org.iesalandalus.programacion.biblioteca.mvc.vista.iugpestanas.utilidades.Dialogos;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class ControladorAnadirLibro {
	
	private IControlador controladorMVC;
	private ObservableList<Libro> libros;
	
    @FXML private ComboBox<String> cbTipoLibro;
	@FXML private TextField tfTitulo;
	@FXML private TextField tfAutor;
	@FXML private TextField tfPaginaDuracion;
	@FXML private Label lbPaginaDuracion;
    @FXML private Button btAnadir;
    @FXML private Button btCancelar;

    @FXML
    private void initialize() {
    	tfTitulo.textProperty().addListener((ob, ov, nv) -> compruebaCampoTexto(tfTitulo));
    	tfAutor.textProperty().addListener((ob, ov, nv) -> compruebaCampoTexto(tfAutor));
    	tfPaginaDuracion.textProperty().addListener((ob, ov, nv) -> compruebaCampoTexto(tfPaginaDuracion));
    	cbTipoLibro.valueProperty().addListener((ob, ov, nv) -> actualizarLibro(cbTipoLibro));
    }
    
    public void setControladorMVC(IControlador controladorMVC) {
		this.controladorMVC = controladorMVC;
	}
    
	public void setLibros(ObservableList<Libro> libros) {
		this.libros = libros;
	}
    
    @FXML
    private void anadirLibro(ActionEvent event) {
    	Libro libro = null;
    	try {
    		libro = getLibro();
    		controladorMVC.insertar(libro);
    		libros.setAll(controladorMVC.getLibros());
    		Stage propietario = ((Stage) btAnadir.getScene().getWindow());
    		Dialogos.mostrarDialogoInformacion("Añadir Libro", "Libro añadido correctamente", propietario);
    	} catch (Exception e) {
    		Dialogos.mostrarDialogoError("Añadir Libro", e.getMessage());
    	}
    }    

	@FXML 
	private void cancelar(ActionEvent event) {
		((Stage) btCancelar.getScene().getWindow()).close();
	}
    
	public void inicializa() {
		cbTipoLibro.getItems().setAll("Libro Escrito", "Audiolibro");
		cbTipoLibro.getSelectionModel().select("Libro Escrito");
		tfTitulo.setText("");
		compruebaCampoTexto(tfTitulo);
		tfAutor.setText("");
		compruebaCampoTexto(tfAutor);
		tfPaginaDuracion.setText("");
		compruebaCampoTexto(tfPaginaDuracion);
	} 
	
	private void actualizarLibro(ComboBox<String> tipo) {
    	if (tipo.getValue()=="Libro Escrito") {
    		lbPaginaDuracion.setText("Páginas");
    	} else {
    		lbPaginaDuracion.setText("Duración");
    	}
    }
    
	private void compruebaCampoTexto(TextField campoTexto) {	
		String texto = campoTexto.getText();
		if (!texto.isEmpty()) {
			campoTexto.setStyle("-fx-border-color: green; -fx-border-radius: 5;");
		}
		else {
			campoTexto.setStyle("-fx-border-color: red; -fx-border-radius: 5;");
		}
	}    
    
	private Libro getLibro() {
		String titulo = tfTitulo.getText();
		String autor = tfAutor.getText();
		String paginaDuracion = tfPaginaDuracion.getText();
		if (cbTipoLibro.getValue()=="Libro Escrito") {
			return new LibroEscrito(titulo, autor, Integer.parseInt(paginaDuracion));
		} else {
			return new AudioLibro(titulo, autor, Integer.parseInt(paginaDuracion));
		}
	}   

}
