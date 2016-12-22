package ejbs;

import entities.Need;
import entities.Patient;
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

@Stateless
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

    public List<Patient> getAll() {
        try {
            List<Patient> patients = (List<Patient>) em.createNamedQuery("getAllPatients").getResultList();
            return patients;
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

    
}
