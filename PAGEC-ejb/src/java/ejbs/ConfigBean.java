package ejbs;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class ConfigBean {

    //@EJB
    //private AdministratorBean administratorBean;
    
    @EJB
    private CaregiverBean caregiverBean;

    @PostConstruct
    public void populateBD() {

        try {

            //administratorBean.create("a1", "a1", "a1", "a1@ipleiria.pt");
            //administratorBean.create("a2", "a2", "a2", "a2@ipleiria.pt");
            //administratorBean.create("a3", "a3", "a3", "a3@ipleiria.pt");
            
            caregiverBean.create("11111", "123456789", "André", "1111@11111.com");
            caregiverBean.create("11121", "123456789", "João", "1141@11111.com");
            caregiverBean.create("11131", "123456789", "Rodrigo", "1131@11111.com");
            caregiverBean.create("11141", "123456789", "Jaquim", "1121@11111.com");
            
            caregiverBean.searchCaregiver("João");
            
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } 
    }
}
