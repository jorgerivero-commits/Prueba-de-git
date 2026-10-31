package com.spacedebris.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        MapController controller = new MapController();
        Scene scene = new Scene(controller.getRootLayout(), 800, 600);
        primaryStage.setTitle("Space Debris Tracker - OOP Project");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
