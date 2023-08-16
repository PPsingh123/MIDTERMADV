package com.example.midtermexam;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CarDBManager {
    private Connection connection;

    public CarDBManager(String url, String username, String password) throws SQLException {
        this.connection = DriverManager.getConnection(url, username, password);
    }

    public List<Car> fetchAllCars() throws SQLException {
        List<Car> cars = new ArrayList<>();
        String sqlQuery = "SELECT * FROM carSales";
        try (Statement stmt = this.connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sqlQuery);
            while (rs.next()) {
                Car car = extractCarFromResultSet(rs);
                cars.add(car);
            }
            rs.close();
        }
        return cars;
    }

    public List<Car> getCarsByYear(int year) throws SQLException {
        List<Car> cars = new ArrayList<>();
        String sqlQuery = "SELECT * FROM carSales WHERE modelYear = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(sqlQuery)) {
            stmt.setInt(1, year);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Car car = extractCarFromResultSet(rs);
                cars.add(car);
            }
            rs.close();
        }
        return cars;
    }

    public List<Integer> getAllYears() throws SQLException {
        List<Integer> years = new ArrayList<>();
        String sqlQuery = "SELECT DISTINCT modelYear FROM carSales";
        try (Statement stmt = this.connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sqlQuery);
            while (rs.next()) {
                int year = rs.getInt("modelYear");
                years.add(year);
            }
            rs.close();
        }
        return years;
    }

    private Car extractCarFromResultSet(ResultSet rs) throws SQLException {
        int carID = rs.getInt("carID");
        int modelYear = rs.getInt("modelYear");
        String make = rs.getString("make");
        String model = rs.getString("model");
        int price = rs.getInt("price");
        LocalDate dateSold = rs.getDate("dateSold").toLocalDate();
        return new Car(carID, modelYear, make, model, price, dateSold);
    }
}
