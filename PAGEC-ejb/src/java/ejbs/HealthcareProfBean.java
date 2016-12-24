package ejbs;

import dtos.PatientDTO;
import entities.Administrator;
import entities.HealthcareProf;
import entities.Patient;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
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
public class HealthcareProfBean {

    @PersistenceContext
    private EntityManager em;

    public void create(String username, String password, String name, String email) 
            throws EntityAlreadyExistsException, MyConstraintViolationException{
        try {
            if(em.find(HealthcareProf.class, username) != null){
                throw new EntityAlreadyExistsException("A HealthcareProfissional with that username already exists.");
            }
            em.persist(new HealthcareProf(username, password, name, email));
        } catch (EntityAlreadyExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }


    public List<HealthcareProf> getAll() {
        try {
            List<HealthcareProf> healthcareProfs = (List<HealthcareProf>) em.createNamedQuery("getAllHealthcareProfs").getResultList();
            return healthcareProfs;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void update(String username, String password, String name, String email) 
        throws EntityDoesNotExistsException, MyConstraintViolationException{
        try {
            HealthcareProf healthcareProf = em.find(HealthcareProf.class, username);
            if (healthcareProf == null) {
                throw new EntityDoesNotExistsException("There is no HealthcareProf with that username.");
            }
            
            healthcareProf.setPassword(password);
            healthcareProf.setName(name);
            healthcareProf.setEmail(email);
            em.merge(healthcareProf);
            
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));            
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void remove(String username) throws EntityDoesNotExistsException {
        try {
            HealthcareProf healthcareProf = em.find(HealthcareProf.class, username);
            if (healthcareProf == null) {
                throw new EntityDoesNotExistsException("There is no healthcareProf with that username.");
            }
            
            
            em.remove(healthcareProf);
        
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
}
