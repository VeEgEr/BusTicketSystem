package com.example.veyisegemenerden.ticketsystem;

/**
 * Created by veyisegemenerden on 8.12.2016.
 */

public class InformationForRegister {
    private String name;
    private String surname;
    private String gender;

    public InformationForRegister(String name,String surname,String gender){
        this.name=name;
        this.surname=surname;
        this.gender = gender;


    }
    public InformationForRegister(){

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Name: "+getName()+" Surname: "+ getSurname()+" Gender: "+getGender();
    }


}
