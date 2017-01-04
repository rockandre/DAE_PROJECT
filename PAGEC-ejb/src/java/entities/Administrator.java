package entities;

import enumerations.GROUP;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "getAllAdministrators", query = "SELECT t FROM Administrator t ORDER BY t.name")
public class Administrator extends User implements Serializable {

    public Administrator() {
    }

    public Administrator(String userName, String password, String name, String email) {
        super(userName, password, GROUP.Administrator, name, email);
    }

}
