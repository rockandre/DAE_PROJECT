package ejbs;

import enumerations.TRMAT;
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
    
    @EJB
    private CaregiverBean caregiverBean;
    
    @EJB
    private TrainingMaterialBean trainingMaterialBean;
    
    @EJB
    private ProcedureBean procedureBean;
    @EJB
    private HealthcareProfBean healthcareProfBean;

    @PostConstruct
    public void populateBD() {

        try {
            administratorBean.create("a1", "a1", "a1", "a1@ipleiria.pt");
            administratorBean.create("Admin", "adm", "Xico", "cenas@fsa.pt");
            healthcareProfBean.create("healthcare", "healthcare", "Health", "health@xico.pt");
            patientBean.create(1, "Zé");
            needBean.create(1, "Alcool");
            
            patientBean.enrollNeed(1, 1);

            caregiverBean.create("11111", "123456789", "André", "1111@11111.com");
            caregiverBean.create("11121", "123456789", "João", "1141@11111.com");
            caregiverBean.create("11131", "123456789", "Rodrigo", "1131@11111.com");
            caregiverBean.create("11141", "123456789", "Jaquim", "1121@11111.com");
            
            caregiverBean.enrollPatient("11111", 1);
            
            trainingMaterialBean.create(1, "Respiracao boca a boca", "Suporte Basico de Vida", TRMAT.VIDEO);
            
            needBean.enrollTrainingMaterial(1, 1);
            
            //needBean.unrollPatient(1, 1);
            
            procedureBean.create(1, "Arranjar uma perna", "Fazer estalar 3 vezes para o lado esquerdo e depois uma para o direito!");
            
            patientBean.enrollProcedure(1, 1);

            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } 
    }
}
