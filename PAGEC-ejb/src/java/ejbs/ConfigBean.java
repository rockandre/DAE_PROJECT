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

    @PostConstruct
    public void populateBD() {

        try {

            //administratorBean.create("a1", "a1", "a1", "a1@ipleiria.pt");
            //administratorBean.create("a2", "a2", "a2", "a2@ipleiria.pt");
            //administratorBean.create("a3", "a3", "a3", "a3@ipleiria.pt");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } 
    }
}
