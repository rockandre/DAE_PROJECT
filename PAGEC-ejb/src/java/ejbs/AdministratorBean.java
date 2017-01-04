package ejbs;

import dtos.AdministratorDTO;
import entities.Administrator;
import entities.HealthcareProf;
import entities.User;
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
public class AdministratorBean {

    @PersistenceContext
    private EntityManager em;

    public void create(String username, String password, String name, String email) 
            throws EntityAlreadyExistsException, MyConstraintViolationException{
        try {
            if(em.find(User.class, username) != null){
                throw new EntityAlreadyExistsException("A user with that username already exists.");
            }
            em.persist(new Administrator(username, password, name, email));
        } catch (EntityAlreadyExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public List<AdministratorDTO> getAll() {
        try {
            List<Administrator> administrators = (List<Administrator>) em.createNamedQuery("getAllAdministrators").getResultList();
            return administratorsToDTOs(administrators);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void update(String username, String password, String name, String email) 
        throws EntityDoesNotExistsException, MyConstraintViolationException{
        try {
            Administrator administrator = em.find(Administrator.class, username);
            if (administrator == null) {
                throw new EntityDoesNotExistsException("There is no administrator with that username.");
            }
            
            administrator.setCleanPassword(password);
            administrator.setName(name);
            administrator.setEmail(email);
            em.merge(administrator);
            
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
            Administrator administrator = em.find(Administrator.class, username);
            if (administrator == null) {
                throw new EntityDoesNotExistsException("There is no administrator with that username.");
            }
            
            
            em.remove(administrator);
        
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    List<AdministratorDTO> administratorsToDTOs(List<Administrator> administrators) {
        List<AdministratorDTO> dtos = new ArrayList<>();
        for (Administrator a : administrators) {
            dtos.add(new AdministratorDTO(a.getUsername(), a.getPassword(), a.getName(), a.getEmail()));            
        }
        return dtos;
    }
}
