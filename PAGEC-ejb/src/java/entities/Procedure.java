/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author joaop
 */
@Entity
@Table(name = "PROCEDURES")
@NamedQueries({
    @NamedQuery(name = "getAllProcedures",
    query = "SELECT c FROM Procedure c ORDER BY c.id"),
    //@NamedQuery(name = "getAllCoursesNames",
    //query = "SELECT c.name FROM Course c ORDER BY c.name")
})
@XmlRootElement
public class Procedure implements Serializable {

    @Id
    private int id;
    
    @NotNull
    private Date date; //mudar
    @ManyToOne
    @JoinColumn(name = "PATIENT_ID")
    private Patient patient;
    
    @ManyToOne
    @JoinColumn(name = "CAREGIVER_ID")
    private Caregiver caregiver;
    
    @ManyToOne
    @JoinColumn(name = "TRAININGMATERIAL_ID")
    private TrainingMaterial trainingMaterial;
    

    public Procedure() {
    }

    public Procedure(int id, Patient patient, Caregiver caregiver, TrainingMaterial trainingMaterial) {
        this.id = id;
        this.date = new Date();
        this.patient = patient;
        this.caregiver = caregiver;
        this.trainingMaterial = trainingMaterial;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Caregiver getCaregiver() {
        return caregiver;
    }

    public void setCaregiver(Caregiver caregiver) {
        this.caregiver = caregiver;
    }

    public TrainingMaterial getTrainingMaterial() {
        return trainingMaterial;
    }

    public void setTrainingMaterial(TrainingMaterial trainingMaterial) {
        this.trainingMaterial = trainingMaterial;
    }

    
    
    
}
