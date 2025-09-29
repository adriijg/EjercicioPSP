package org.tierno.view;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.tierno.model.Alumno;

import java.util.List;

public class ListaAlumnosController {

    @FXML
    private ListView<String> listaAlumnos;

    public void cargarAlumnos(List<Alumno> alumnos){
        listaAlumnos.setItems(FXCollections.observableArrayList(
                alumnos.stream()
                        .map(a -> a.getNombre() + " " + a.getApellido() + ", " + a.getEdad() + " años")
                        .toList()
        ));
    }
}
