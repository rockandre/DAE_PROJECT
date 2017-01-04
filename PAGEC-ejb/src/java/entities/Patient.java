/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author joaop
 */
@Entity
@Table(name = "PATIENTS")
@NamedQueries({
    @NamedQuery(name = "getAllPatients",
    query = "SELECT c FROM Patient c ORDER BY c.name")
})
@XmlRootElement
public class Patient implements Serializable {

    @Id
    private int id;
    @NotNull (message = "Patient name must not be empty")
    private String name;
    @ManyToOne
    @JoinColumn(name = "CAREGIVER_USERNAME")
    private Caregiver caregiver;
    
    @ManyToMany(mappedBy = "patients")
    private List<Need> needs;
    
    
    @OneToMany(mappedBy = "patient", cascade = CascadeType.REMOVE)
    private List<Procedure> procedures;
    
    
    public Patient(){
        needs = new LinkedList<>();
        procedures = new LinkedList<>();
    }
    
    public Patient(int id, String name){
        this.id = id;
        this.name = name;
        this.needs = new LinkedList<>();
        this.procedures = new LinkedList<>();
    }

    public List<Procedure> getProcedures() {
        return procedures;
    }

    public void setProcedures(List<Procedure> procedures) {
        this.procedures = procedures;
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

    public Caregiver getCaregiver() {
        return caregiver;
    }

    public void setCaregiver(Caregiver caregiver) {
        this.caregiver = caregiver;
    }

    @XmlTransient
    public List<Need> getNeeds() {
        return needs;
    }

    public void setNeeds(List<Need> needs) {
        this.needs = needs;
    }
    
    public void addNeed(Need need) {
        needs.add(need);
    }

    public void removeNeed(Need need) {
        needs.remove(need);
    }
    
    public void removeCaregiver() {
        this.caregiver = null;
    }
    
    public void addProcedure(Procedure procedure) {
        procedures.add(procedure);
    }

    public void removeProcedure(Procedure procedure) {
        procedures.remove(procedure);
    }
/*
    @Override
    public String toString() {
        return "Id: " + id + ", Name: " + name;
    }
    
    
    */
}
