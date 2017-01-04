package web;

import dtos.CaregiverDTO;
import dtos.NeedDTO;
import dtos.PatientDTO;
import dtos.ProcedureDTO;
import dtos.TrainingMaterialDTO;
import enumerations.TRMAT;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@ManagedBean
@SessionScoped
public class CaregiverManager {

    private static final Logger logger = Logger.getLogger("web.CaregiverManager");
    private CaregiverDTO currentCaregiver;
    private CaregiverDTO newCaregiver;
    private ProcedureDTO currentProcedure;
    private ProcedureDTO newProcedure;
    private TrainingMaterialDTO currentTrainingMaterial;
    private TrainingMaterialDTO newTrainingMaterial;
    private int patientId;
    @ManagedProperty("#{userManager}")
    private UserManager userManager;
    private UIComponent component;
    private Client client;
    private final String baseUri = "http://localhost:8080/PAGEC-war/webapi";
    public CaregiverManager() {
        newCaregiver= new CaregiverDTO();
        newTrainingMaterial = new TrainingMaterialDTO();
        newProcedure = new ProcedureDTO();
        client = ClientBuilder.newClient();
    }

    
    
    
    public List<PatientDTO> getEnrolledPatientsByUsername(String username) {
        try {
            client.register(new Authenticator(userManager.getUsername(), userManager.getPassword()));
            List<PatientDTO> patients = new ArrayList<>();
            patients = client.target(baseUri)
                    .path("/caregivers/enrolledPatients/" + username)
                    //.queryParam("username", username)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<PatientDTO>>() {
                    });
            
            return patients; //caregiverBean.getEnrolledPatients(username);
        /*} catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);*/
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }
    
                                //NEEDS
    
    public List<NeedDTO> getPatientNeeds(int id) {
        try {
            client.register(new Authenticator(userManager.getUsername(), userManager.getPassword()));
            List<NeedDTO> needs = new ArrayList<>();
            needs = client.target(baseUri)
                    .path("/patients/patientNeeds/" + id)
                    //.queryParam("username", username)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<NeedDTO>>() {
                    });
            
            return needs;//patientBean.getNeeds(id);
        /*} catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);*/
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }
    
    
    //PROCEDURES
    
    public List<ProcedureDTO> proceduresByCaregiver(String username) {
        try {
            client.register(new Authenticator(userManager.getUsername(), userManager.getPassword()));
            List<ProcedureDTO> procedures = new ArrayList<>();
            procedures = client.target(baseUri)
                    .path("/procedures/proceduresByCaregiver/" + username)
                    //.queryParam("username", username)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ProcedureDTO>>() {
                    });
            //String username = userManager.getUsername();
            return procedures;//procedureBean.getProceduresByCaregiver(username);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }
    
    public String createProcedure(String caregiverUsername) {
        try {
            
            ProcedureDTO procedureDTO = new ProcedureDTO(newProcedure.getId(), new Date(), patientId, "", caregiverUsername, "", currentTrainingMaterial.getId(), "");
            client.register(new Authenticator(userManager.getUsername(), userManager.getPassword()));
            WebTarget target = client.target(baseUri);
            target = target.path("/procedures/create/");
            Response response = target.request().put(Entity.xml(procedureDTO));
            /*
            procedureBean.create(
                    newProcedure.getId(),
                    patientId,
                    caregiverUsername,
                    currentTrainingMaterial.getId());*/
            return "/faces/caregiver/caregiver_index?faces-redirect=true";
        /*} catch (EntityAlreadyExistsException | MyConstraintViolationException | EntityNotEnrolledException | EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), component, logger);*/
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }
        return null;
    }
    
    
    //TRAINING MATERIALS
    public List<TrainingMaterialDTO> caregiverTrainingMaterials(String username) {
        try {
            client.register(new Authenticator(userManager.getUsername(), userManager.getPassword()));
            List<TrainingMaterialDTO> trainingMaterials = new ArrayList<>();
            trainingMaterials = client.target(baseUri)
                    .path("/caregivers/caregiverTrainingMaterials/" + username)
                    //.queryParam("username", username)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<TrainingMaterialDTO>>() {
                    });
            return trainingMaterials;//caregiverBean.getCaregiverTrainingMaterials(username);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }
    
    
    
    
    
    // GETTERS E SETTERS


    public CaregiverDTO getCurrentCaregiver() {
        return currentCaregiver;
    }

    public void setCurrentCaregiver(CaregiverDTO currentCaregiver) {
        this.currentCaregiver = currentCaregiver;
    }

    public CaregiverDTO getNewCaregiver() {
        return newCaregiver;
    }

    public void setNewCaregiver(CaregiverDTO newCaregiver) {
        this.newCaregiver = newCaregiver;
    }

    public TrainingMaterialDTO getCurrentTrainingMaterial() {
        return currentTrainingMaterial;
    }

    public void setCurrentTrainingMaterial(TrainingMaterialDTO currentTrainingMaterial) {
        this.currentTrainingMaterial = currentTrainingMaterial;
    }

    public TrainingMaterialDTO getNewTrainingMaterial() {
        return newTrainingMaterial;
    }

    public void setNewTrainingMaterial(TrainingMaterialDTO newTrainingMaterial) {
        this.newTrainingMaterial = newTrainingMaterial;
    }
    
    public TRMAT[] getTRMATs() {
        return TRMAT.values();
    }
    
    
    public UIComponent getComponent() {
        return component;
    }

    public void setComponent(UIComponent component) {
        this.component = component;
    }

    public ProcedureDTO getCurrentProcedure() {
        return currentProcedure;
    }

    public void setCurrentProcedure(ProcedureDTO currentProcedure) {
        this.currentProcedure = currentProcedure;
    }

    public ProcedureDTO getNewProcedure() {
        return newProcedure;
    }

    public void setNewProcedure(ProcedureDTO newProcedure) {
        this.newProcedure = newProcedure;
    }

   public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    
    


    
    ///////////// VALIDATORS ////////////////////////
    public void validateUsername(FacesContext context, UIComponent toValidate, Object value) {
        try {
            //Your validation code goes here
            String username = (String) value;
            //If the validation fails
            if (username.startsWith("xpto")) {
                FacesMessage message = new FacesMessage("Error: invalid username.");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                context.addMessage(toValidate.getClientId(context), message);
                ((UIInput) toValidate).setValid(false);
            }
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unkown error.", logger);
        }
    }

}
