/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "NEEDS",
        uniqueConstraints
        = @UniqueConstraint(columnNames = {"NAME"}))
@NamedQueries({
    @NamedQuery(name = "getAllNeeds",
    query = "SELECT c FROM Need c ORDER BY c.name")
})
@XmlRootElement
public class Need implements Serializable {

    @Id
    private int id;
    @NotNull (message = "Need name must not be empty")
    private String name;
    
    @ManyToMany
    @JoinTable(name = "NEED_PATIENT",
            joinColumns
            = @JoinColumn(name = "NEED_ID", referencedColumnName = "ID"),
            inverseJoinColumns
            = @JoinColumn(name = "PATIENT_ID", referencedColumnName = "ID"))
    private List<Patient> patients;
    
    @ManyToMany
    @JoinTable(name = "NEED_TRAININGMATERIAL",
            joinColumns
            = @JoinColumn(name = "NEED_ID", referencedColumnName = "ID"),
            inverseJoinColumns
            = @JoinColumn(name = "TRAININGMATERIAL_ID", referencedColumnName = "ID"))
    private List<TrainingMaterial> trainingMaterials;
    
    public Need(){
        patients = new LinkedList<>();
        trainingMaterials = new LinkedList<>();
    }
    
    public Need(int id, String name){
        this.id = id;
        this.name = name;
        patients = new LinkedList<>();
        trainingMaterials = new LinkedList<>();
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    @XmlTransient
    public List<TrainingMaterial> getTrainingMaterials() {
        return trainingMaterials;
    }

    public void setTrainingMaterials(List<TrainingMaterial> trainingMaterials) {
        this.trainingMaterials = trainingMaterials;
    }
    
    public void addTrainingMaterial(TrainingMaterial trainingMaterial) {
        trainingMaterials.add(trainingMaterial);
    }

    public void removeTrainingMaterial(TrainingMaterial trainingMaterial) {
        trainingMaterials.remove(trainingMaterial);
    }
    
    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public void removePatient(Patient patient) {
        patients.remove(patient);
    }
}
