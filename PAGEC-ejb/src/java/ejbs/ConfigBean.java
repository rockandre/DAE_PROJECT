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
            administratorBean.create("a2", "a2", "a2", "a2@ipleiria.pt");
            administratorBean.create("a3", "a3", "a3", "a3@ipleiria.pt");
            administratorBean.create("a4", "a4", "a4", "a4@ipleiria.pt");
            healthcareProfBean.create("hc1", "hc1", "hc1", "hc1@ipleiria.pt");
            healthcareProfBean.create("hc2", "hc2", "hc2", "hc2@ipleiria.pt");
            healthcareProfBean.create("hc3", "hc3", "hc3", "hc3@ipleiria.pt");
            healthcareProfBean.create("hc4", "hc4", "hc4", "hc4@ipleiria.pt");
            patientBean.create(1, "Zé");
            patientBean.create(2, "Armando");
            patientBean.create(3, "Pato");
            patientBean.create(4, "Crocodilo");
            patientBean.create(5, "Tubarão");
            caregiverBean.create("11111", "123456789", "André", "1111@11111.com");
            caregiverBean.create("11121", "123456789", "João", "1141@11111.com");
            caregiverBean.create("11131", "123456789", "Rodrigo", "1131@11111.com");
            caregiverBean.create("11141", "123456789", "Jaquim", "1121@11111.com");
            needBean.create(1, "Alcool");
            needBean.create(2, "Diabetes");
            needBean.create(3, "Lesão");
            needBean.create(4, "Dor de cabeca");
            needBean.create(5, "Pouca visão");
            trainingMaterialBean.create(1, "Como beber vinho", TRMAT.VIDEO, "http://cenas.pt/");
            trainingMaterialBean.create(2, "Como tomar insulina", TRMAT.QUESTIONARIO, "http://cenas.pt/");
            trainingMaterialBean.create(3, "Treinar musculos lesionados", TRMAT.TEXT, "http://cenas.pt/");
            trainingMaterialBean.create(4, "Como fazer massagens na cabeca", TRMAT.TURORIAL, "http://cenas.pt/");
            trainingMaterialBean.create(5, "Como meter gotas nos olhos", TRMAT.TURORIAL, "http://cenas.pt/");
            
            needBean.enrollTrainingMaterial(1, 1);
            needBean.enrollTrainingMaterial(2, 2);
            needBean.enrollTrainingMaterial(3, 3);
            //needBean.enrollTrainingMaterial(4, 4);
            //needBean.enrollTrainingMaterial(5, 5);
                    
            patientBean.enrollNeed(1, 1);
            patientBean.enrollNeed(1, 2);
            patientBean.enrollNeed(1, 3);
            patientBean.enrollNeed(2, 2);
            patientBean.enrollNeed(3, 3);
            //patientBean.enrollNeed(4, 4);
            //patientBean.enrollNeed(5, 5);
            
            
            caregiverBean.enrollPatient("11111", 1);
            //caregiverBean.enrollPatient("11111", 2);
            //caregiverBean.enrollPatient("11111", 3);
            

            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } 
    }
}
