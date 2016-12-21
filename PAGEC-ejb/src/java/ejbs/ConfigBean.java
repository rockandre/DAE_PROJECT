package ejbs;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class ConfigBean {

    @EJB
    private AdministratorBean administratorBean;
    @EJB
    private PatientBean patientBean;
    @EJB
    private NeedBean needBean;

    @PostConstruct
    public void populateBD() {

        try {

            administratorBean.create("a1", "a1", "a1", "a1@ipleiria.pt");
            
            patientBean.create(1, "Zé");
            needBean.create(1, "Alcool");
            
            patientBean.enrollNeed(1, 1);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } 
    }
}
