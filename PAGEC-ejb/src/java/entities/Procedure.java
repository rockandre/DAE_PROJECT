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
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author joaop
 */
@Entity
@Table(name = "PROCEDURES")
@NamedQueries({
    @NamedQuery(name = "getAllProcedures",
    query = "SELECT c FROM Procedure c ORDER BY c.name"),
    //@NamedQuery(name = "getAllCoursesNames",
    //query = "SELECT c.name FROM Course c ORDER BY c.name")
})
public class Procedure implements Serializable {

    @Id
    private int id;
    @NotNull (message = "Training Material name must not be empty")
    private String name;
    
    private String descricao;
    
    @ManyToMany(mappedBy = "procedures")
    List<Patient> patients;

    public Procedure() {
        patients = new LinkedList<>();
    }

    public Procedure(int id, String name, String descricao) {
        this.id = id;
        this.name = name;
        this.descricao = descricao;
        this.patients = new LinkedList<>();
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
    
    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public void removePatient(Patient patient) {
        patients.remove(patient);
    }
    
    
}
