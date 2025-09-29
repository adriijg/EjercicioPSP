package org.tierno.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.tierno.controller.InstitutoController;
import javafx.event.ActionEvent;


import java.io.IOException;


public class FichaViewController {

    @FXML
    private Button btnCrear;

    @FXML
    private TextField txtApellidos;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtEdad;

    protected InstitutoController institutoController;

    public void setInstitutoController(InstitutoController institutoController){
        this.institutoController = institutoController;
    }

    private boolean campoRelleno(TextField campo){
        boolean relleno = false;
        if(campo!=null && !"".equals(campo.getText().trim())){
            relleno = true;
        }
        return relleno;
    }

    public boolean camposRellenos(){
        return campoRelleno(this.txtApellidos) && campoRelleno(this.txtNombre) && campoRelleno(this.txtEdad);
    }

    @FXML
    public void crear(ActionEvent event) {
        final String MSG_TITLE = "Error en el formulario";
        final String MSG_CONTENT = "Todos los campos son obligatorios";

        if(camposRellenos()){
            institutoController.crearAlumno(txtNombre.getText(),txtApellidos.getText(), Integer.parseInt(txtEdad.getText()));

            txtNombre.setText("");
            txtApellidos.setText("");
            txtEdad.setText("");
        } else {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle(MSG_TITLE);
            alerta.setContentText(MSG_CONTENT);
            alerta.show();
        }
    }

    @FXML
    public void listar(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/listaAlumnosView.fxml"));
            Parent root = loader.load();

            ListaAlumnosController listaController = loader.getController();

            listaController.cargarAlumnos(institutoController.listaAlumnos());

            Stage stage = new Stage();
            stage.setTitle("Alumnos matriculado");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
