package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Launcher extends Application {

    @Override
    public void start(Stage primaryStage) {
        NetworkForm form = new NetworkForm();
        VBox root = new VBox(form.getForm());

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Network Configuration System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}