package org.tierno;

import javafx.application.Application;
import org.tierno.controller.InstitutoController;

public class App {

    public static void main(String[] args) {

        InstitutoController controller = new InstitutoController();

        Application.launch(InstitutoController.class, args);

    }
}