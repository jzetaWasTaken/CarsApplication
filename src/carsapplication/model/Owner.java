/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carsapplication.model;

import java.util.Date;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author jon
 */
public class Owner {
    // Fields
    private final SimpleStringProperty name;
    private final SimpleStringProperty surname;
    private final SimpleStringProperty id;
    private final SimpleObjectProperty<Date> dateOfBirth;
    // Constructors
    public Owner() {
        name = new SimpleStringProperty();
        surname =  new SimpleStringProperty();
        id = new SimpleStringProperty();
        dateOfBirth = new SimpleObjectProperty<>();
    }
    // Getters & setters
    public String getName() {
        return name.get();
    }
    public void setName(String name) {
        this.name.set(name);
    }
    public String getSurname() {
        return surname.get();
    }
    public void setSurname(String surname) {
        this.surname.set(surname);
    }
    public String getId() {
        return id.get();
    }
    public void setId(String id) {
        this.id.set(id);
    }
    public Date getDateOfBirth() {
        return dateOfBirth.get();
    }
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth.set(dateOfBirth);
    }
}

