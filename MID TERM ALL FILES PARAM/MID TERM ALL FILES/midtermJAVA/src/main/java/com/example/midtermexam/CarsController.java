package com.example.midtermexam;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.util.List;

public class CarsController {
    @FXML
    private TableView<Cars> carTable;
    @FXML
    private TableColumn<Cars, Integer> carIDColumn;
    @FXML
    private TableColumn<Cars, Integer> modelYearColumn;
    @FXML
    private TableColumn<Cars, String> makeColumn;
    @FXML
    private TableColumn<Cars, String> modelColumn;
    @FXML
    private TableColumn<Cars, Integer> priceColumn;
    @FXML
    private TableColumn<Cars, String> dateSoldColumn;
    @FXML
    private ComboBox<Integer> yearComboBox;
    @FXML
    private Label totalCarsLabel;
    @FXML
    private Label totalSalesLabel;
    @FXML
    private BarChart<String, Number> barChart;

    private CarsDatabase carDatabase;
    private ObservableList<Cars> carData = FXCollections.observableArrayList();

    public void initialize() {
        // Initialize CarDatabase
        String url = "jdbc:mysql://localhost:3306/F22Midterm";
        String username = "student";
        String password = "student";
        try {
            carDatabase = new CarsDatabase(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database connection error
        }

        // Initialize TableView columns
        carIDColumn.setCellValueFactory(cellData -> cellData.getValue().carIDProperty().asObject());
        modelYearColumn.setCellValueFactory(cellData -> cellData.getValue().modelYearProperty().asObject());
        makeColumn.setCellValueFactory(cellData -> cellData.getValue().makeProperty());
        modelColumn.setCellValueFactory(cellData -> cellData.getValue().modelProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        dateSoldColumn.setCellValueFactory(cellData -> cellData.getValue().dateSoldProperty().asString());

        // Populate TableView and ComboBox
        try {
            carData.addAll(carDatabase.getAllCars());
            carTable.setItems(carData);

            List<Integer> years = carDatabase.getAllYears();
            yearComboBox.setItems(FXCollections.observableArrayList(years));
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database query error
        }

        // Update total cars sold and total sales labels
        updateTotalLabels();

        // Update bar chart
        updateBarChart();

        // Add listener to ComboBox selection
        yearComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (newValue != null) {
                    List<Cars> carsByYear = carDatabase.getAllCarsByYear(newValue);
                    carData.setAll(carsByYear);
                } else {
                    carData.setAll(carDatabase.getAllCars());
                }
                updateTotalLabels();
                updateBarChart();
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle database query error
            }
        });
    }

    private void updateTotalLabels() {
        int totalCars = carData.size();
        totalCarsLabel.setText(Integer.toString(totalCars));
        int totalSales = carData.stream().mapToInt(Cars::getPrice).sum();
        totalSalesLabel.setText("$" + totalSales);
    }

    private void updateBarChart() {
        String[] manufacturers = {"Acura", "Ford", "Honda", "Nissan", "Tesla"};
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart.Series<String, Number> series = new BarChart.Series<>();
        series.setName("Cars Sold");
        for (String manufacturer : manufacturers) {
            int carsSold = (int) carData.stream().filter(car -> car.getMake().equals(manufacturer)).count();
            series.getData().add(new BarChart.Data<>(manufacturer, carsSold));
        }
        barChart.getData().clear();
        barChart.getData().add(series);
    }
}


