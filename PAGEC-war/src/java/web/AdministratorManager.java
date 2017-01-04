package web;

import dtos.AdministratorDTO;
import dtos.CaregiverDTO;
import dtos.HealthcareProfDTO;
import dtos.NeedDTO;
import dtos.TrainingMaterialDTO;
import ejbs.AdministratorBean;
import ejbs.CaregiverBean;
import ejbs.HealthcareProfBean;
import ejbs.NeedBean;
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
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@ManagedBean
@SessionScoped
public class AdministratorManager {

    @EJB
    private CaregiverBean caregiverBean;
    @EJB
    private AdministratorBean administratorBean;
    @EJB
    private HealthcareProfBean healthcareProfBean;
    @EJB
    private TrainingMaterialBean trainingMaterialBean;
    @EJB
    private NeedBean needBean;
    private static final Logger logger = Logger.getLogger("web.AdministratorManager");
    private AdministratorDTO currentAdministrator;
    private AdministratorDTO newAdministrator;
    private HealthcareProfDTO currentHealthcareProf;
    private HealthcareProfDTO newHealthcareProf;
    private CaregiverDTO currentCaregiver;
    private CaregiverDTO newCaregiver;
    private TrainingMaterialDTO currentTrainingMaterial;
    private TrainingMaterialDTO newTrainingMaterial;

    private UIComponent component;
    private final String baseUri = "http://localhost:8080/PAGEC-war/webapi";

    public AdministratorManager() {
        newAdministrator = new AdministratorDTO();
        newHealthcareProf = new HealthcareProfDTO();
        newCaregiver = new CaregiverDTO();
        newTrainingMaterial = new TrainingMaterialDTO();

    }

    //ADMINISTRATORS
    public List<AdministratorDTO> getAllAdministrators() {
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
        return "admin_administrators_update";
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
    public List<HealthcareProfDTO> getAllHealthcareProfs() {
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
        return "admin_healthcareprofs_update";
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
            return "/faces/admin/admin_index?faces-redirect=true";
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
            return "/faces/admin/admin_index?faces-redirect=true";

        } catch (EntityDoesNotExistsException | MyConstraintViolationException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return "admin_caregivers_update";
    }

    public void removeCaregiver(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("caregiverUsername");
            String username = param.getValue().toString();
            caregiverBean.remove(username);
        } catch (EntityDoesNotExistsException e) {
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
            return "/faces/admin/admin_index?faces-redirect=true";
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
            return "/faces/admin/admin_index?faces-redirect=true";

        } catch (EntityDoesNotExistsException | MyConstraintViolationException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
        return "admin_trainingmaterials_update";
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
    public AdministratorDTO getCurrentAdministrator() {
        return currentAdministrator;
    }

    public void setCurrentAdministrator(AdministratorDTO currentAdministrator) {
        this.currentAdministrator = currentAdministrator;
    }

    public HealthcareProfDTO getCurrentHealthcareProf() {
        return currentHealthcareProf;
    }

    public void setCurrentHealthcareProf(HealthcareProfDTO currentHealthcareProf) {
        this.currentHealthcareProf = currentHealthcareProf;
    }

    public HealthcareProfDTO getNewHealthcareProf() {
        return newHealthcareProf;
    }

    public void setNewHealthcareProf(HealthcareProfDTO newHealthcareProf) {
        this.newHealthcareProf = newHealthcareProf;
    }

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

    public UIComponent getComponent() {
        return component;
    }

    public void setComponent(UIComponent component) {
        this.component = component;
    }

    public AdministratorDTO getNewAdministrator() {
        return newAdministrator;
    }

    public void setNewAdministrator(AdministratorDTO newAdministrator) {
        this.newAdministrator = newAdministrator;
    }

    public TRMAT[] getTRMATs() {
        return TRMAT.values();
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
