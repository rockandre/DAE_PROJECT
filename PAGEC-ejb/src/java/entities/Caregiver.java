/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import enumerations.GROUP;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author joaop
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "getAllCaregivers", query = "SELECT t FROM Caregiver t ORDER BY t.name"),
    @NamedQuery(name = "searchCaregivers", query = "SELECT t FROM Caregiver t WHERE t.name LIKE :name")
})
@XmlRootElement
public class Caregiver extends User implements Serializable {

    @OneToMany(mappedBy = "caregiver", cascade = CascadeType.REMOVE)
    private List<Patient> patients;
    
    @OneToMany(mappedBy = "caregiver", cascade = CascadeType.REMOVE)
    private List<Procedure> procedures; //procedimentos que aplicou!
    
    public Caregiver() {
        patients = new LinkedList<>();
        procedures = new LinkedList<>();
    }

    public Caregiver(String username, String password, String name, String email) {
        super(username, password, GROUP.Caregiver, name, email);
        patients = new LinkedList<>();
        procedures = new LinkedList<>();
    }

    @XmlTransient
    public List<Patient> getPatients() {
        return patients;
    }

    
    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public List<Procedure> getProcedures() {
        return procedures;
    }

    public void setProcedures(List<Procedure> procedures) {
        this.procedures = procedures;
    }
    
    
    
    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public void removePatient(Patient patient) {
        patients.remove(patient);
    }
    
    public void addProcedure(Procedure procedure) {
        procedures.add(procedure);
    }

    public void removeProcedure(Procedure procedure) {
        procedures.remove(procedure);
    }
    
}
