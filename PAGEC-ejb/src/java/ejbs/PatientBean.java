package ejbs;

import dtos.PatientDTO;
import entities.Need;
import entities.Patient;
import entities.Procedure;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/patients")
public class PatientBean {

    @PersistenceContext
    private EntityManager em;

    public void create(int id, String name)
            throws EntityAlreadyExistsException, MyConstraintViolationException {
        try {
            if (em.find(Patient.class, id) != null) {
                throw new EntityAlreadyExistsException("A patient with that id already exists.");
            }

            em.persist(new Patient(id, name));

        } catch (EntityAlreadyExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<PatientDTO> getAll() {
        try {
            List<Patient> patients = (List<Patient>) em.createNamedQuery("getAllPatients").getResultList();
            return patientsToDTOs(patients);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void remove(int id) throws EntityDoesNotExistsException {

        try {
            Patient patient = em.find(Patient.class, id);
            if (patient == null) {
                throw new EntityDoesNotExistsException("There is no patient with that id");
            }

            em.remove(patient);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void enrollNeed(int patientId, int needId) {
            
        try {
            Patient patient = em.find(Patient.class, patientId);
            if (patient == null) {
                throw new EntityDoesNotExistsException("There is no patient with that username.");
            }

            Need need = em.find(Need.class, needId);
            if (need == null) {
                throw new EntityDoesNotExistsException("There is no need with that code.");
            }

            if (patient.getNeeds().contains(need)) {
                return;//throw new Exception("Patient's course has no such need.");
            }

            if (need.getPatients().contains(patient)) {
                return;//throw new Exception("Patient is already enrolled in that need.");
            }

            need.addPatient(patient);
            patient.addNeed(need);

        
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void unrollNeed(int id, int needId) 
            throws EntityDoesNotExistsException {
        try {
            Need need = em.find(Need.class, needId);
            if(need == null){
                throw new EntityDoesNotExistsException("There is no need with that code.");
            }            
            
            Patient patient = em.find(Patient.class, id);
            if(patient == null){
                throw new EntityDoesNotExistsException("There is no patient with that username.");
            }
            
            if(!need.getPatients().contains(patient)){
                throw new Exception("Patient not enrolled");
            }            
            
            need.removePatient(patient);
            patient.removeNeed(need);

        } catch (EntityDoesNotExistsException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    
    public void enrollProcedure(int patientId, int procedureId){
        try {

            Procedure procedure = em.find(Procedure.class, procedureId);
            if (procedure == null) {
                throw new EntityDoesNotExistsException("There is no training material with that username.");
            }

            Patient patient = em.find(Patient.class, patientId);
            if (patient == null) {
                throw new EntityDoesNotExistsException("There is no need with that code.");
            }

            if (procedure.getPatients().contains(patient)) {
                throw new Exception("Patient's course has no such need.");
            }

            if (patient.getProcedures().contains(procedure)) {
                throw new Exception("Patient is already enrolled in that need.");
            }

            procedure.addPatient(patient);
            patient.addProcedure(procedure);
            

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
/*
    public void unrollProcedure(int patientId, int procedureId){
        try {
            Patient patient = em.find(Patient.class, patientId);
            if(patient == null){
                throw new EntityDoesNotExistsException("There is no need with that code.");
            }            
            
            Procedure procedure = em.find(Procedure.class, procedureId);
            if(procedure == null){
                throw new EntityDoesNotExistsException("There is no patient with that username.");
            }
            
            if(!patient.getProcedures().contains(procedure)){
                throw new Exception("Patient not enrolled");
            }            
            
            patient.removeProcedure(procedure);
            procedure.removePatient(patient);
            
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
*/
    List<PatientDTO> patientsToDTOs(List<Patient> patients) {
        List<PatientDTO> dtos = new ArrayList<>();
        for(Patient patient : patients) {
            dtos.add(new PatientDTO(patient.getId(), patient.getName(), patient.getCaregiver().getUsername(), patient.getCaregiver().getName()));
        }
        return dtos;
    }
}
