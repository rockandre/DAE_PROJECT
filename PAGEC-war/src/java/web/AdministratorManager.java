package web;

import dtos.PatientDTO;
import ejbs.AdministratorBean;
import ejbs.HealthcareProfBean;
import ejbs.PatientBean;
import entities.Administrator;
import entities.HealthcareProf;
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
public class AdministratorManager {

    @EJB
    private PatientBean patientsBean;
    @EJB
    private AdministratorBean administratorBean;
    @EJB
    private HealthcareProfBean healthcareProfBean;
    /*@EJB
    private CourseBean courseBean;
    @EJB
    private SubjectBean subjectBean;*/
    private static final Logger logger = Logger.getLogger("web.AdministratorManager");
    private Administrator currentAdministrator;
    private Administrator newAdministrator;
    private HealthcareProf currentHealthcareProf;
    private HealthcareProf newHealthcareProf;
    /*private StudentDTO newStudent;
    private StudentDTO currentStudent;
    private CourseDTO newCourse;
    private CourseDTO currentCourse;
    private SubjectDTO newSubject;
    private SubjectDTO currentSubject;*/
    private UIComponent component;
    private Client client;
    private final String baseUri = "http://localhost:8080/AcademicManagement_FICHA6-war/webapi";
    public AdministratorManager() {/*
        newStudent = new StudentDTO();
        newCourse = new CourseDTO();
        newSubject = new SubjectDTO();*/
        newAdministrator = new Administrator();
        newHealthcareProf= new HealthcareProf();
        client = ClientBuilder.newClient();
    }

    //ADMINISTRATORS
    public List<Administrator> getAllAdministrators() {
        try {
            return administratorBean.getAll();
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }
    
    public String createAdministrator() {
        try {
            administratorBean.create(
                    newAdministrator.getUsername(),
                    newAdministrator.getPassword(),
                    newAdministrator.getName(),
                    newAdministrator.getEmail());
            return "/faces/admin/admin_index?faces-redirect=true";
        } catch (EntityAlreadyExistsException | MyConstraintViolationException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), component, logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }
        return null;
    }
    
    public String updateAdministrator() {
        try {
            administratorBean.update(
                    currentAdministrator.getUsername(),
                    currentAdministrator.getPassword(),
                    currentAdministrator.getName(),
                    currentAdministrator.getEmail());
            return "/faces/admin/admin_index?faces-redirect=true";

        } catch (EntityDoesNotExistsException | MyConstraintViolationException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return "admin_students_update";
    }
    
    public void removeAdministrator(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("administratorUsername");
            String id = param.getValue().toString();
            administratorBean.remove(id);
        } catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }
    
    
    //HEALTHCAREPROFS
    public List<HealthcareProf> getAllHealthcareProfs() {
        try {
            return healthcareProfBean.getAll();
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
    }
    
    public String createHealthcareProf() {
        try {
            healthcareProfBean.create(
                    newHealthcareProf.getUsername(),
                    newHealthcareProf.getPassword(),
                    newHealthcareProf.getName(),
                    newHealthcareProf.getEmail());
            return "/faces/admin/admin_index?faces-redirect=true";
        } catch (EntityAlreadyExistsException | MyConstraintViolationException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), component, logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
        }
        return null;
    }
    
    public String updateHealthcareProf() {
        try {
            healthcareProfBean.update(
                    currentHealthcareProf.getUsername(),
                    currentHealthcareProf.getPassword(),
                    currentHealthcareProf.getName(),
                    currentHealthcareProf.getEmail());
            return "/faces/admin/admin_index?faces-redirect=true";

        } catch (EntityDoesNotExistsException | MyConstraintViolationException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return "admin_students_update";
    }
    
    public void removeHealthcareProf(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("healthcareProfUsername");
            String id = param.getValue().toString();
            healthcareProfBean.remove(id);
        } catch (EntityDoesNotExistsException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }
    
    // GETTERS E SETTERS

    public Administrator getCurrentAdministrator() {
        return currentAdministrator;
    }

    public void setCurrentAdministrator(Administrator currentAdministrator) {
        this.currentAdministrator = currentAdministrator;
    }

    public HealthcareProf getCurrentHealthcareProf() {
        return currentHealthcareProf;
    }

    public void setCurrentHealthcareProf(HealthcareProf currentHealthcareProf) {
        this.currentHealthcareProf = currentHealthcareProf;
    }

    public HealthcareProf getNewHealthcareProf() {
        return newHealthcareProf;
    }

    public void setNewHealthcareProf(HealthcareProf newHealthcareProf) {
        this.newHealthcareProf = newHealthcareProf;
    }
    
    
    
    public UIComponent getComponent() {
        return component;
    }

    public void setComponent(UIComponent component) {
        this.component = component;
    }

    public Administrator getNewAdministrator() {
        return newAdministrator;
    }

    public void setNewAdministrator(Administrator newAdministrator) {
        this.newAdministrator = newAdministrator;
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
