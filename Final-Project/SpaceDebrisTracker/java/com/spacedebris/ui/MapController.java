package com.spacedebris.ui;

import com.spacedebris.model.Satellite;
import com.spacedebris.service.DatabaseService;
import com.spacedebris.service.OrbitCalculatorService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class MapController {

    private BorderPane rootLayout;
    private TableView<Satellite> table;
    private ObservableList<Satellite> satelliteData = FXCollections.observableArrayList();

    private OrbitCalculatorService orbitService = new OrbitCalculatorService();
    private DatabaseService dbService = new DatabaseService();

    public MapController() {
        buildUI();
        loadDataFromDatabase();
    }

    private void buildUI() {
        rootLayout = new BorderPane();
        rootLayout.setPadding(new Insets(15));

        // Título superior
        Label titleLabel = new Label("🛰️ Space Debris Tracker - Panel de Monitoreo ");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        VBox topBox = new VBox(titleLabel);
        topBox.setPadding(new Insets(0, 0, 15, 0));
        rootLayout.setTop(topBox);

        // Tabla central para mostrar los satélites
        table = new TableView<>();
        table.setItems(satelliteData);

        TableColumn<Satellite, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(60);

        TableColumn<Satellite, String> nameCol = new TableColumn<>("Objeto / Satélite");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(200);

        TableColumn<Satellite, Double> latCol = new TableColumn<>("Latitud (°)");
        latCol.setCellValueFactory(new PropertyValueFactory<>("latitude"));
        latCol.setPrefWidth(100);

        TableColumn<Satellite, Double> lonCol = new TableColumn<>("Longitud (°)");
        lonCol.setCellValueFactory(new PropertyValueFactory<>("longitude"));
        lonCol.setPrefWidth(100);

        TableColumn<Satellite, Double> altCol = new TableColumn<>("Altitud (km)");
        altCol.setCellValueFactory(new PropertyValueFactory<>("altitude"));
        altCol.setPrefWidth(100);

        table.getColumns().addAll(idCol, nameCol, latCol, lonCol, altCol);
        rootLayout.setCenter(table);

        // Botones de acción inferiores
        Button btnRunCpp = new Button("🔄 Simular con C++ y Guardar en MySQL");
        btnRunCpp.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
        btnRunCpp.setOnAction(e -> handleRunSimulation());

        Button btnLoadDb = new Button("📂 Cargar desde MySQL");
        btnLoadDb.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white;");
        btnLoadDb.setOnAction(e -> loadDataFromDatabase());

        HBox bottomBox = new HBox(10, btnRunCpp, btnLoadDb);
        bottomBox.setPadding(new Insets(15, 0, 0, 0));
        rootLayout.setBottom(bottomBox);
    }

    private void handleRunSimulation() {
        System.out.println("Ejecutando simulación C++ SGP4...");
        List<Satellite> simulated = orbitService.fetchSimulatedOrbits();
        
        if (simulated.isEmpty()) {
            showAlert("Advertencia", "No se obtuvieron datos del motor C++. Revisa la consola.");
            return;
        }

        // Guardamos cada satélite y sus coordenadas calculadas en MySQL
        for (Satellite sat : simulated) {
            dbService.saveOrUpdateSatellite(sat);
            dbService.saveOrbitalPosition(sat.getId(), sat.getLatitude(), sat.getLongitude(), sat.getAltitude());
        }

        // Refrescamos la vista con los datos nuevos desde la BD
        loadDataFromDatabase();
        System.out.println("¡Simulación completada y guardada en MySQL con éxito!");
    }

    private void loadDataFromDatabase() {
        List<Satellite> dbList = dbService.getAllSatellites();
        satelliteData.setAll(dbList);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public BorderPane getRootLayout() {
        return rootLayout;
    }
}
