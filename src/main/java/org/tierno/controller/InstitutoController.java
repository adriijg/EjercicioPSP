package org.tierno.controller;

import java.io.IOException;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.tierno.App;
import org.tierno.model.Acceso;
import org.tierno.model.Alumno;
import org.tierno.model.Instituto;
import org.tierno.model.InstitutoFactory;
import org.tierno.view.FichaViewController;

public class InstitutoController extends Application{

    private final static String VIEW_MAIN = "view/fichaView.fxml";

    //Lógica de negocio (modelo)
    private static Instituto model;


    // Este método se llama al ejecutar lanzar la aplicación
    // Carga la pantalla inicial
    // Carga el modelo
    @Override
    public void start(Stage stage) throws IOException {

        //Inicializo el modelo
        model = InstitutoFactory.obtener(Acceso.MOCK);

        //Cargo la vista
        stage.setScene(cargarVista(VIEW_MAIN));
        stage.show();
    }

    private Scene cargarVista(String ficheroView) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/fichaView.fxml"));
        Parent root = (Parent)fxmlLoader.load();

        //Obtengo el controlador de la vista para pasarle una referencia al controlador - MVC:
        FichaViewController viewController = fxmlLoader.<FichaViewController>getController();
        viewController.setInstitutoController(this);
        Scene scene = new Scene(root);

        return scene;
    }




    //Métodos de acceso al modelo

    public Alumno crearAlumno(String nombre, String apellidos, int edad){

        Alumno al = new Alumno(nombre, apellidos, edad);
        model.matricular(al);

        return al;
    }

    public List<Alumno> listaAlumnos(){
        return model.listarAlumnos();
    }

}
