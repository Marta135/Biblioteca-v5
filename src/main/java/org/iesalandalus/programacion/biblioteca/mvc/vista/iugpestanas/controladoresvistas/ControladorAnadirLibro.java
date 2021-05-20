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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class ControladorAnadirLibro {

	private static final String ER_OBLIGATORIO = ".+";
	private static final String ER_CIFRAS = "\\d+";
	
	private IControlador controladorMVC;
	private ObservableList<Libro> libros;
	
	@FXML private TextField tfTitulo;
	@FXML private TextField tfAutor;
    @FXML private RadioButton rbLibroEscrito;
    @FXML private TextField tfNumPaginas;
    @FXML private RadioButton rbAudioLibro;
    @FXML private TextField tfDuracion;
    @FXML private Button btAnadir;
    @FXML private Button btCancelar;

    private ToggleGroup tgTipoLibro = new ToggleGroup();
    
    @FXML
    private void initialize() {
    	tfTitulo.textProperty().addListener((ob, ov, nv) -> compruebaCampoTexto(ER_OBLIGATORIO, tfTitulo));
    	tfAutor.textProperty().addListener((ob, ov, nv) -> compruebaCampoTexto(ER_OBLIGATORIO, tfAutor));
    	rbLibroEscrito.setToggleGroup(tgTipoLibro);
    	rbAudioLibro.setToggleGroup(tgTipoLibro);
    	tgTipoLibro.selectedToggleProperty().addListener((ob, ov, nv) -> habilitaSeleccion());
    	tfNumPaginas.textProperty().addListener((ob, ov, nv) -> compruebaCampoTexto(ER_CIFRAS, tfNumPaginas));
    	tfDuracion.textProperty().addListener((ob, ov, nv) -> compruebaCampoTexto(ER_CIFRAS, tfDuracion));
    	
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
		tfTitulo.setText("");
		compruebaCampoTexto(ER_OBLIGATORIO, tfTitulo);
		tfAutor.setText("");
		compruebaCampoTexto(ER_OBLIGATORIO, tfAutor);
		tgTipoLibro.selectToggle(rbLibroEscrito);
		tfNumPaginas.setText("");
		//compruebaCampoTexto(ER_CIFRAS, tfNumPaginas);
		tfDuracion.setText("");
		//compruebaCampoTexto(ER_CIFRAS, tfDuracion);
	}
	
	private void compruebaCampoTexto(String er, TextField campoTexto) {	
		
		String texto = campoTexto.getText();
		if (texto.matches(er)) {
			campoTexto.setStyle("-fx-border-color: green; -fx-border-radius: 5;");
		}
		else {
			campoTexto.setStyle("-fx-border-color: red; -fx-border-radius: 5;");
		}
	}
	
	private void habilitaSeleccion() {
		RadioButton seleccionado = (RadioButton) tgTipoLibro.getSelectedToggle();
		if (seleccionado == rbLibroEscrito) {
			tfNumPaginas.setDisable(false);
			tfDuracion.setDisable(true);
		} else {
			tfNumPaginas.setDisable(true);
			tfDuracion.setDisable(false);
		}
	}
	
	private Libro getLibro() throws OperationNotSupportedException {
		
		String titulo = tfTitulo.getText();
		String autor = tfAutor.getText();
		Libro libro = null;
		
		RadioButton seleccionado = (RadioButton) tgTipoLibro.getSelectedToggle();
		if (seleccionado == rbLibroEscrito) {
			int numPaginas = Integer.parseInt(tfNumPaginas.getText());
			libro = new LibroEscrito(titulo, autor, numPaginas);
		} else {
			int duracion = Integer.parseInt(tfDuracion.getText());
			libro = new AudioLibro(titulo, autor, duracion);
		}
		return libro;
	}

}
