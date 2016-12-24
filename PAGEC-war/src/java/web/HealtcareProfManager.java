package web;

import dtos.PatientDTO;
import ejbs.AdministratorBean;
import ejbs.CaregiverBean;
import ejbs.HealthcareProfBean;
import ejbs.PatientBean;
import entities.Administrator;
import entities.Caregiver;
import entities.HealthcareProf;
import entities.Patient;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.MyConstraintViolationException;
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
public class HealtcareProfManager {

    @EJB
    private CaregiverBean caregiverBean;
    private static final Logger logger = Logger.getLogger("web.HealthcareProfManager");
    private Caregiver currentCaregiver;
    private Caregiver newCaregiver;
    private UIComponent component;
    private Client client;
    private final String baseUri = "http://localhost:8080/AcademicManagement_FICHA6-war/webapi";
    public HealtcareProfManager() {
        newCaregiver= new Caregiver();
        client = ClientBuilder.newClient();
    }

    
    
    //CAREGIVERS
    public List<Caregiver> getAllCaregivers() {
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
