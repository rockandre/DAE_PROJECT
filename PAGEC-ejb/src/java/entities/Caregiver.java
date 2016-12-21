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
import javax.persistence.Entity;
import javax.persistence.NamedQuery;

/**
 *
 * @author joaop
 */
@Entity
@NamedQuery(name = "getAllCaregivers", query = "SELECT t FROM Caregiver t ORDER BY t.name")
public class Caregiver extends User implements Serializable {

    private List<Patient> patients;
    
    protected Caregiver() {
        patients = new LinkedList<>();
    }

    public Caregiver(String username, String password, String name, String email) {
        super(username, password, GROUP.Caregiver, name, email);
        patients = new LinkedList<>();
    }
    
    
    
}
