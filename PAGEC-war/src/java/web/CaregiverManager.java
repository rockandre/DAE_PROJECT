package web;

import dtos.PatientDTO;
import ejbs.AdministratorBean;
import ejbs.CaregiverBean;
import ejbs.HealthcareProfBean;
import ejbs.NeedBean;
import ejbs.PatientBean;
import ejbs.ProcedureBean;
import ejbs.TrainingMaterialBean;
import entities.Administrator;
import entities.Caregiver;
import entities.HealthcareProf;
import entities.Need;
import entities.Patient;
import entities.Procedure;
import entities.TrainingMaterial;
import enumerations.TRMAT;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.EntityEnrolledException;
import exceptions.MyConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

@ManagedBean
@SessionScoped
public class CaregiverManager {

    @EJB
    private CaregiverBean caregiverBean;
    @EJB
    private TrainingMaterialBean trainingMaterialBean;
    @EJB
    private NeedBean needBean;
    @EJB
    private ProcedureBean procedureBean;
    
    @EJB
    private PatientBean patientBean;
    private static final Logger logger = Logger.getLogger("web.CaregiverManager");
    private Caregiver currentCaregiver;
    private Caregiver newCaregiver;
    private Procedure currentProcedure;
    private Procedure newProcedure;
    private TrainingMaterial currentTrainingMaterial;
    private TrainingMaterial newTrainingMaterial;
    private UIComponent component;
    private Client client;
    private final String baseUri = "http://localhost:8080/AcademicManagement_FICHA6-war/webapi";
    public CaregiverManager() {
        newCaregiver= new Caregiver();
        newTrainingMaterial = new TrainingMaterial();
        newProcedure = new Procedure();
        client = ClientBuilder.newClient();
    }

    
    
    
    
    
    
    public List<Patient> getEnrolledPatients() {
        try {
            return caregiverBean.getEnrolledPatients(currentCaregiver.getUsername());
        } catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }

    public List<Patient> getUnrolledPatients() {
        try {
            return caregiverBean.getUnrolledPatients();
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }

    public void enrollPatient(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("patientId");
            int id  = Integer.parseInt(param.getValue().toString());
            caregiverBean.enrollPatient(currentCaregiver.getUsername(), id);
        } catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }

    public void unrollPatient(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("patientId");
            int id  = Integer.parseInt(param.getValue().toString());
            caregiverBean.unrollPatient(currentCaregiver.getUsername(), id);
        } catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }
                                //NEEDS
    
    public List<Need> getPatientNeeds(int id) {
        try {
            return patientBean.getNeeds(id);
        } catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }
    
    public List<Need> getPatientNotNeeds(int id) {
        try {
            return patientBean.getUnrolledNeeds(id);
        } catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }
    
    public void unrollPatientNeed(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("needId");
            int needId  = Integer.parseInt(param.getValue().toString());
            UIParameter param2 = (UIParameter) event.getComponent().findComponent("patientId");
            int patientId  = Integer.parseInt(param2.getValue().toString());
            patientBean.unrollNeed(patientId, needId);
        } catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }
    
    public void enrollPatientNeed(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("needId");
            int needId  = Integer.parseInt(param.getValue().toString());
            UIParameter param2 = (UIParameter) event.getComponent().findComponent("patientId");
            int patientId  = Integer.parseInt(param2.getValue().toString());
            patientBean.enrollNeed(patientId, needId);
        } catch (EntityDoesNotExistsException | EntityEnrolledException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }
    
    
    //PROCEDURES
    
    public List<Procedure> proceduresByCaregiver(String username) {
        try {
            //String username = userManager.getUsername();
            return procedureBean.getProceduresByCaregiver(username);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }
    
    
    public void removeProcedure(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("procedureId");
            int id  = Integer.parseInt(param.getValue().toString());
            procedureBean.remove(id);
        } catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }
    
    public String createProcedure(String caregiverUsername) {
        try {
            procedureBean.create(
                    newProcedure.getId(),
                    newProcedure.getPatient().getId(),
                    caregiverUsername,
                    currentTrainingMaterial.getId());
            return "/faces/caregiver/caregiver_index?faces-redirect=true";
        } catch (EntityAlreadyExistsException | MyConstraintViolationException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), component, logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }
        return null;
    }
    
    
    //TRAINING MATERIALS
    public List<TrainingMaterial> caregiverTrainingMaterials(String username) {
        try {
            //String username = userManager.getUsername();
            return caregiverBean.getCaregiverTrainingMaterials(username);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }
    
    
    
    public List<TrainingMaterial> getAllTrainingMaterials() {
        try {
            return trainingMaterialBean.getAll();
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }
    
    public String createTrainingMaterial() {
        try {
            trainingMaterialBean.create(
                    newTrainingMaterial.getId(),
                    newTrainingMaterial.getName(),
                    newTrainingMaterial.getTipoTM(),
                    newTrainingMaterial.getLink());
            return "/faces/healthcareProf/healthcareProf_index?faces-redirect=true";
        } catch (EntityAlreadyExistsException | MyConstraintViolationException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), component, logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }
        return null;
    }
    
    public String updateTrainingMaterial() {
        try {
            trainingMaterialBean.update(
                    currentTrainingMaterial.getId(),
                    currentTrainingMaterial.getName(),
                    currentTrainingMaterial.getTipoTM(),
                    currentTrainingMaterial.getLink());
            return "/faces/healthcareProf/healthcareProf_index?faces-redirect=true";

        } catch (EntityDoesNotExistsException | MyConstraintViolationException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return "healthcareProf_trainingmaterials_update";
    }
    
    public void removeTrainingMaterial(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("trainingMaterialId");
            int id  = Integer.parseInt(param.getValue().toString());
            trainingMaterialBean.remove(id);
        } catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }
    
                            //NEEDS
    
    public List<Need> getEnrolledNeeds() {
        try {
            return trainingMaterialBean.getEnrolledNeeds(currentTrainingMaterial.getId());
        } catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }

    public List<Need> getAllNeeds() {
        try {
            return needBean.getAll();
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }
    
    public List<Need> getTrainingMaterialNotNeeds() {
        try {
            return trainingMaterialBean.getTrainingMaterialNotNeeds(currentTrainingMaterial.getId());
        } catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }

    public void enrollNeed(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("needId");
            int id  = Integer.parseInt(param.getValue().toString());
            needBean.enrollTrainingMaterial(id, currentTrainingMaterial.getId());
        } catch (EntityDoesNotExistsException | EntityEnrolledException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }

    public void unrollNeed(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("needId");
            int id  = Integer.parseInt(param.getValue().toString());
            needBean.unrollTrainingMaterial(id, currentTrainingMaterial.getId());
        } catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }
    
    
    // GETTERS E SETTERS


    public Caregiver getCurrentCaregiver() {
        return currentCaregiver;
    }

    public void setCurrentCaregiver(Caregiver currentCaregiver) {
        this.currentCaregiver = currentCaregiver;
    }

    public Caregiver getNewCaregiver() {
        return newCaregiver;
    }

    public void setNewCaregiver(Caregiver newCaregiver) {
        this.newCaregiver = newCaregiver;
    }

    public TrainingMaterial getCurrentTrainingMaterial() {
        return currentTrainingMaterial;
    }

    public void setCurrentTrainingMaterial(TrainingMaterial currentTrainingMaterial) {
        this.currentTrainingMaterial = currentTrainingMaterial;
    }

    public TrainingMaterial getNewTrainingMaterial() {
        return newTrainingMaterial;
    }

    public void setNewTrainingMaterial(TrainingMaterial newTrainingMaterial) {
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

    public Procedure getCurrentProcedure() {
        return currentProcedure;
    }

    public void setCurrentProcedure(Procedure currentProcedure) {
        this.currentProcedure = currentProcedure;
    }

    public Procedure getNewProcedure() {
        return newProcedure;
    }

    public void setNewProcedure(Procedure newProcedure) {
        this.newProcedure = newProcedure;
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
