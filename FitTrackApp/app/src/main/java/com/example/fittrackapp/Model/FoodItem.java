package com.example.fittrackapp.Model;

public class FoodItem {
    // Propiedades
    private String name;
    private String calories;
    private String servingSizeG;
    private double fatTotalG;
    private double fat_saturated_g;
    private String proteinG;
    private int sodiumMg;
    private int potassiumMg;
    private int cholesterol_mg;
    private double carbohydrates_total_g;
    private double fiberG;
    private double sugarG;
    private int imageResource;



    // Getters y Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCalories() {
        return calories;
    }

    /*public void setCalories(double calories) {
        this.calories = calories;
    }*/

    public String getServingSizeG() {
        return servingSizeG;
    }

    /*public void setServingSizeG(double servingSizeG) {
        this.servingSizeG = servingSizeG;
    }*/

    public double getFatTotalG() {
        return fatTotalG;
    }

    public void setFatTotalG(double fatTotalG) {
        this.fatTotalG = fatTotalG;
    }

    public double getFatSaturatedG() {
        return fat_saturated_g;
    }

    public void setFatSaturatedG(double fatSaturatedG) {
        this.fat_saturated_g = fatSaturatedG;
    }

    public String getProteinG() {
        return proteinG;
    }

    /*public void setProteinG(double proteinG) {
        this.proteinG = proteinG;
    }*/

    public int getSodiumMg() {
        return sodiumMg;
    }

    public void setSodiumMg(int sodiumMg) {
        this.sodiumMg = sodiumMg;
    }

    public int getPotassiumMg() {
        return potassiumMg;
    }

    public void setPotassiumMg(int potassiumMg) {
        this.potassiumMg = potassiumMg;
    }

    public int getCholesterolMg() {
        return cholesterol_mg;
    }

    public void setCholesterolMg(int cholesterolMg) {
        this.cholesterol_mg = cholesterolMg;
    }

    public double getCarbohydratesTotalG() {
        return carbohydrates_total_g;
    }

    public void setCarbohydratesTotalG(double carbohydratesTotalG) {
        this.carbohydrates_total_g = carbohydratesTotalG;
    }

    public double getFiberG() {
        return fiberG;
    }

    public void setFiberG(double fiberG) {
        this.fiberG = fiberG;
    }

    public double getSugarG() {
        return sugarG;
    }

    public void setSugarG(double sugarG) {
        this.sugarG = sugarG;
    }

    public int getImageResource() {
        return imageResource;
    }
}
