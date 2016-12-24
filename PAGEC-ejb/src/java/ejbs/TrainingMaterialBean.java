/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import entities.Need;
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
    
    public void create(int id, String name, String tipoSuporte, TRMAT tipoTM) 
            throws EntityAlreadyExistsException, MyConstraintViolationException{
        try {
            if (em.find(TrainingMaterial.class, id) != null) {
                throw new EntityAlreadyExistsException("A Training Material with that id already exists.");
            }
            
            em.persist(new TrainingMaterial(id, name, tipoSuporte, tipoTM));
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
    
    public void remove(int code) throws EntityDoesNotExistsException {
        try {
            TrainingMaterial trainingMaterial = em.find(TrainingMaterial.class, code);
            if (trainingMaterial == null) {
                throw new EntityDoesNotExistsException("There is no Caregiver with that code.");
            }
            
            for (Need need : trainingMaterial.getNeeds()) {
                need.removeTrainingMaterial(trainingMaterial);
            }
            
            em.remove(trainingMaterial);
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    } 
}
