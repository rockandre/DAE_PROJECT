/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import enumerations.GROUP;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;

/**
 *
 * @author joaop
 */
@Entity
@NamedQuery(name = "getAllHealthcareProfs", query = "SELECT t FROM HealthcareProf t ORDER BY t.name")
public class HealthcareProf extends User implements Serializable {

    public HealthcareProf() {
    }

    public HealthcareProf(String username, String password, String name, String email) {
        super(username, password, GROUP.HealthcareProf, name, email);
    }
}
