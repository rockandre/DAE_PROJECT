/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import entities.Caregiver;
import entities.Need;
import entities.Patient;
import entities.TrainingMaterial;
import entities.User;
import enumerations.TRMAT;
import exceptions.EntityDoesNotExistsException;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author rockandre
 */
@Stateless
public class TrainingMaterialBean {

    @PersistenceContext
    private EntityManager em;
    
    public void create(int id, String name, String tipoSuporte, TRMAT tipoTM) {
        try {
            if (em.find(TrainingMaterial.class, id) != null) {
                return;
            }
            
            em.persist(new TrainingMaterial(id, name, tipoSuporte, tipoTM));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<TrainingMaterial> getAllTrainingMaterials() {
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
