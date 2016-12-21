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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 *
 * @author joaop
 */
@Entity
@Table(name = "PATIENTS")
//uniqueConstraints =
//@UniqueConstraint(columnNames = {"NAME"}))
@NamedQueries({
    @NamedQuery(name = "getAllPatients",
    query = "SELECT c FROM Patient c ORDER BY c.name"),
    //@NamedQuery(name = "getAllCoursesNames",
    //query = "SELECT c.name FROM Course c ORDER BY c.name")
})
public class Patient implements Serializable {

    @Id
    private int id;
    @NotNull (message = "Patient name must not be empty")
    private String name;
    @NotNull
    private Caregiver caregiver;
    
    private List<Need> needs;

    
    public Patient(){
        needs = new LinkedList<>();
    }
    
    public Patient(int id, String name, Caregiver caregiver){
        this.id = id;
        this.name = name;
        this.caregiver = caregiver;
        needs = new LinkedList<>();
    }

    
}
