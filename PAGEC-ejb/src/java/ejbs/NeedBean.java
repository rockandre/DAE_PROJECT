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
public class NeedBean {

    @PersistenceContext
    private EntityManager em;

    public void create(int id, String name)
            throws EntityAlreadyExistsException, MyConstraintViolationException {
        try {
            if (em.find(Need.class, id) != null) {
                throw new EntityAlreadyExistsException("A need with that id already exists.");
            }

            em.persist(new Need(id, name));

        } catch (EntityAlreadyExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public List<Need> getAll() {
        try {
            List<Need> needs = (List<Need>) em.createNamedQuery("getAllNeeds").getResultList();
            return needs;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void remove(int id) throws EntityDoesNotExistsException {

        try {
            Need need = em.find(Need.class, id);
            if (need == null) {
                throw new EntityDoesNotExistsException("There is no patient with that id");
            }

            em.remove(need);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    /*
    public void enrollNeed(String username, int needId) 
            throws EntityDoesNotExistsException{
        try {

            Patient patient = em.find(Patient.class, username);
            if (patient == null) {
                throw new EntityDoesNotExistsException("There is no patient with that username.");
            }

            Need need = em.find(Need.class, needId);
            if (need == null) {
                throw new EntityDoesNotExistsException("There is no need with that code.");
            }

            if (!patient.getNeeds().contains(need)) {
                throw new Exception("Patient's course has no such need.");
            }

            if (need.getPatients().contains(patient)) {
                throw new Exception("Patient is already enrolled in that need.");
            }

            need.addPatient(patient);
            patient.addNeed(need);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void unrollPatient(String username, int needId) 
            throws EntityDoesNotExistsException {
        try {
            Need need = em.find(Need.class, needId);
            if(need == null){
                throw new EntityDoesNotExistsException("There is no need with that code.");
            }            
            
            Patient patient = em.find(Patient.class, username);
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
    */
}
