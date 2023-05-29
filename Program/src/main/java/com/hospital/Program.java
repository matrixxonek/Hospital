package com.hospital;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;

public class Program extends Application {

    MySQL mysql = new MySQL();

    Button btDodajPacjenta;
    StackPane spSceneRoot;

    @Override
    public void start(Stage stage) throws IOException {

        InitializeElements();
        btDodajPacjenta.setText("Dodaj Pacjenta");
        btDodajPacjenta.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FormularzDodajPacjenta fdp = new FormularzDodajPacjenta();
                fdp.show();
            }
            
        });
		
        spSceneRoot.getChildren().add(btDodajPacjenta);

        Scene scene = new Scene(spSceneRoot);
        stage.setTitle("Program szpitalny");
        stage.setScene(scene);
        stage.show();
    }

    private void InitializeElements() {
        btDodajPacjenta = new Button();
        spSceneRoot = new StackPane();
    }

    public static void main(String[] args) {
        launch();
    }
}