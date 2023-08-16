package com.example.midtermexam;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Cars {
    private IntegerProperty carID;
    private IntegerProperty modelYear;
    private StringProperty make;
    private StringProperty model;
    private IntegerProperty price;
    private ObjectProperty<LocalDate> dateSold;

    public Cars(int carID, int modelYear, String make, String model, int price, LocalDate dateSold) {
        validateCarID(carID);
        validateMake(make);
        validateModel(model);
        validatePrice(price);
        validateDateSold(dateSold);

        this.carID = new SimpleIntegerProperty(carID);
        this.modelYear = new SimpleIntegerProperty(modelYear);
        this.make = new SimpleStringProperty(make);
        this.model = new SimpleStringProperty(model);
        this.price = new SimpleIntegerProperty(price);
        this.dateSold = new SimpleObjectProperty<>(dateSold);
    }

    private void validateCarID(int carID) {
        if (carID <= 0) {
            throw new IllegalArgumentException("Car ID should be greater than 0.");
        }
    }

    private void validateMake(String make) {
        String[] validMakes = {"Acura", "Ford", "Honda", "Nissan", "Tesla"};
        boolean isValid = false;
        for (String validMake : validMakes) {
            if (validMake.equals(make)) {
                isValid = true;
                break;
            }
        }
        if (!isValid) {
            throw new IllegalArgumentException("Invalid make. Valid makes are: Acura, Ford, Honda, Nissan, Tesla.");
        }
    }

    private void validateModel(String model) {
        if (model.length() < 2) {
            throw new IllegalArgumentException("Model should be at least 2 characters long.");
        }
    }

    private void validatePrice(int price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price should be greater than 0.");
        }
    }

    private void validateDateSold(LocalDate dateSold) {
        LocalDate currentDate = LocalDate.now();
        if (dateSold.isAfter(currentDate)) {
            throw new IllegalArgumentException("Invalid date sold. Date cannot be in the future.");
        }
    }

    // Getters and property access methods...
    public int getCarID() {
        return carID.get();
    }

    public IntegerProperty carIDProperty() {
        return carID;
    }

    public int getModelYear() {
        return modelYear.get();
    }

    public IntegerProperty modelYearProperty() {
        return modelYear;
    }

    public String getMake() {
        return make.get();
    }

    public StringProperty makeProperty() {
        return make;
    }

    public String getModel() {
        return model.get();
    }

    public StringProperty modelProperty() {
        return model;
    }

    public int getPrice() {
        return price.get();
    }

    public IntegerProperty priceProperty() {
        return price;
    }

    public LocalDate getDateSold() {
        return dateSold.get();
    }

    public ObjectProperty<LocalDate> dateSoldProperty() {
        return dateSold;
    }
}

