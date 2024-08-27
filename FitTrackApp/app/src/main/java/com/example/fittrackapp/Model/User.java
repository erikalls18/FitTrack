package com.example.fittrackapp.Model;

public class User {

    private String Uid;

    private String name;
    private String password;

    private Float weight, height;

    int age;
    private String  sex;

    private int calories_consumed =0;

    private int calories_burned =0;

    private double fat=0;

    private double carbs =0;

    private double cholesterol =0;

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    public double getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(double cholesterol) {
        this.cholesterol = cholesterol;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public int getCalories_consumed() {
        return calories_consumed;
    }

    public  int getCalories_burned() {
        return calories_burned;
    }

    public void setCalories_burned( int calories_burned) {
        this.calories_burned = calories_burned;
    }

    public void setCalories_consumed( int calories_consumed) {
        this.calories_consumed = calories_consumed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }
}
