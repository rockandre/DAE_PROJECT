package entities;

import enumerations.GROUP;
import java.io.Serializable;
import javax.persistence.Entity;

@Entity
public class Administrator extends User implements Serializable {
    
    public Administrator() {
    }

    public Administrator(String userName, String password, String name, String email) {
        super(userName, password, GROUP.Administrator, name, email);
    }

}
