package com.example.veyisegemenerden.ticketsystem;

/**
 * Created by veyisegemenerden on 19.12.2016.
 */

public class Person {
    private String name;
    private String surname;
    private String email;
    private String gender;
    public Person(){

    }
    public Person(String name, String surname, String email, String gender) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
