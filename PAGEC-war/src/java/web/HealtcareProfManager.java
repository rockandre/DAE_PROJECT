package web;

import dtos.CaregiverDTO;
import dtos.NeedDTO;
import dtos.PatientDTO;
import dtos.TrainingMaterialDTO;
import ejbs.CaregiverBean;
import ejbs.NeedBean;
import ejbs.PatientBean;
import ejbs.TrainingMaterialBean;
import enumerations.TRMAT;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.EntityEnrolledException;
import exceptions.MyConstraintViolationException;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

@ManagedBean
@SessionScoped
public class HealtcareProfManager {

    @EJB
    private CaregiverBean caregiverBean;
    @EJB
    private TrainingMaterialBean trainingMaterialBean;
    @EJB
    private NeedBean needBean;
    @EJB
    private PatientBean patientBean;
    private static final Logger logger = Logger.getLogger("web.HealthcareProfManager");
    private CaregiverDTO currentCaregiver;
    private CaregiverDTO newCaregiver;
    private TrainingMaterialDTO currentTrainingMaterial;
    private TrainingMaterialDTO newTrainingMaterial;
    private UIComponent component;
    private Client client;
    private final String baseUri = "http://localhost:8080/PAGEC-war/webapi";

    public HealtcareProfManager() {
        newCaregiver = new CaregiverDTO();
        newTrainingMaterial = new TrainingMaterialDTO();
        client = ClientBuilder.newClient();
    }

    //CAREGIVERS
    public List<CaregiverDTO> getAllCaregivers() {
        try {
            return caregiverBean.getAll();
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }

    public String createCaregiver() {
        try {
            caregiverBean.create(
                    newCaregiver.getUsername(),
                    newCaregiver.getPassword(),
                    newCaregiver.getName(),
                    newCaregiver.getEmail());
            return "/faces/healthcareProf/healthcareProf_index?faces-redirect=true";
        } catch (EntityAlreadyExistsException | MyConstraintViolationException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), component, logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }
        return null;
    }

    public String updateCaregiver() {
        try {
            caregiverBean.update(
                    currentCaregiver.getUsername(),
                    currentCaregiver.getPassword(),
                    currentCaregiver.getName(),
                    currentCaregiver.getEmail());
            return "/faces/healthcareProf/healthcareProf_index?faces-redirect=true";

        } catch (EntityDoesNotExistsException | MyConstraintViolationException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return "healthcareProf_caregivers_update";
    }

    public void removeCaregiver(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("caregiverUsername");
            String id = param.getValue().toString();
            caregiverBean.remove(id);
        } catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }

    public List<PatientDTO> getEnrolledPatients() {
        try {
            return caregiverBean.getEnrolledPatients(currentCaregiver.getUsername());
        } catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }

    public List<PatientDTO> getUnrolledPatients() {
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
            int id = Integer.parseInt(param.getValue().toString());
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
            int id = Integer.parseInt(param.getValue().toString());
            caregiverBean.unrollPatient(currentCaregiver.getUsername(), id);
        } catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }
    //NEEDS

    public List<NeedDTO> getPatientNeeds(int id) {
        try {
            return patientBean.getNeeds(id);
        } catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }

    public List<NeedDTO> getPatientNotNeeds(int id) {
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
            int needId = Integer.parseInt(param.getValue().toString());
            UIParameter param2 = (UIParameter) event.getComponent().findComponent("patientId");
            int patientId = Integer.parseInt(param2.getValue().toString());
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
            int needId = Integer.parseInt(param.getValue().toString());
            UIParameter param2 = (UIParameter) event.getComponent().findComponent("patientId");
            int patientId = Integer.parseInt(param2.getValue().toString());
            patientBean.enrollNeed(patientId, needId);
        } catch (EntityDoesNotExistsException | EntityEnrolledException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }

    //TRAINING MATERIALS
    public List<TrainingMaterialDTO> getAllTrainingMaterials() {
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
            int id = Integer.parseInt(param.getValue().toString());
            trainingMaterialBean.remove(id);
        } catch (EntityDoesNotExistsException | MyConstraintViolationException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }

    //NEEDS
    public List<NeedDTO> getEnrolledNeeds() {
        try {
            return trainingMaterialBean.getEnrolledNeeds(currentTrainingMaterial.getId());
        } catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }

    public List<NeedDTO> getTrainingMaterialNotNeeds() {
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
            int id = Integer.parseInt(param.getValue().toString());
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
            int id = Integer.parseInt(param.getValue().toString());
            needBean.unrollTrainingMaterial(id, currentTrainingMaterial.getId());
        } catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
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
