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
    //@EJB
    //private AdministratorBean administratorBean;
    
    @EJB
    private CaregiverBean caregiverBean;

    @PostConstruct
    public void populateBD() {

        try {


            administratorBean.create("a1", "a1", "a1", "a1@ipleiria.pt");
            
            patientBean.create(1, "Zé");
            needBean.create(1, "Alcool");
            
            patientBean.enrollNeed(1, 1);

            
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
