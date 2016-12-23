package web;

import dtos.PatientDTO;
import ejbs.PatientBean;
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
    /*@EJB
    private CourseBean courseBean;
    @EJB
    private SubjectBean subjectBean;*/
    private static final Logger logger = Logger.getLogger("web.AdministratorManager");
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
        client = ClientBuilder.newClient();
    }


    public List<PatientDTO> getAllStudents() {
        try {
            return patientsBean.getAll();
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return null;
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
