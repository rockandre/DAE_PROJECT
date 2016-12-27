/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import entities.Caregiver;
import entities.HealthcareProf;
import entities.Need;
import entities.Patient;
import entities.Procedure;
import entities.TrainingMaterial;
import enumerations.TRMAT;
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
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author rockandre
 */
@Stateless
@Path("/trainingMaterials")
public class TrainingMaterialBean {

    @PersistenceContext
    private EntityManager em;
    
    public void create(int id, String name, TRMAT tipoTM, String link) 
            throws EntityAlreadyExistsException, MyConstraintViolationException{
        try {
            if (em.find(TrainingMaterial.class, id) != null) {
                throw new EntityAlreadyExistsException("A Training Material with that id already exists.");
            }
            
            em.persist(new TrainingMaterial(id, name, tipoTM, link));
        } catch (EntityAlreadyExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<TrainingMaterial> getAll() {
        try {
            List<TrainingMaterial> trainingMaterials = (List<TrainingMaterial>) em.createNamedQuery("getAllTrainingMaterials").getResultList();
            return trainingMaterials;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void remove(int id) throws EntityDoesNotExistsException {
        try {
            TrainingMaterial trainingMaterial = em.find(TrainingMaterial.class, id);
            if (trainingMaterial == null) {
                throw new EntityDoesNotExistsException("There is no Training Material with that id.");
            }
            
            for (Need need : trainingMaterial.getNeeds()) {
                need.removeTrainingMaterial(trainingMaterial);
            }
            
            for (Procedure procedure : trainingMaterial.getProcedures()) {
                procedure.setTrainingMaterial(null);
            }
            
            em.remove(trainingMaterial);
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    } 
    
    public void update(int id, String name, TRMAT tipoTM, String link) 
        throws EntityDoesNotExistsException, MyConstraintViolationException{
        try {
            TrainingMaterial trainingMaterial = em.find(TrainingMaterial.class, id);
            if (trainingMaterial == null) {
                throw new EntityDoesNotExistsException("There is no Training Material with that id.");
            }
            
            trainingMaterial.setName(name);
            trainingMaterial.setTipoTM(tipoTM);
            trainingMaterial.setLink(link);
            em.merge(trainingMaterial);
            
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));            
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<Need> getEnrolledNeeds(int trainingMaterialId) throws EntityDoesNotExistsException{
        try {
            TrainingMaterial trainingMaterial = em.find(TrainingMaterial.class, trainingMaterialId);
            if (trainingMaterial == null) {
                throw new EntityDoesNotExistsException("There is no training material with that id.");
            }           
            List<Need> needsEnrolled = (List<Need>) trainingMaterial.getNeeds();
            return needsEnrolled;
        } catch (EntityDoesNotExistsException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
}
