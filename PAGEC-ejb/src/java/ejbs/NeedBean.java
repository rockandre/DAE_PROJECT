package ejbs;

import dtos.NeedDTO;
import entities.Need;
import entities.TrainingMaterial;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.EntityEnrolledException;
import exceptions.EntityNotEnrolledException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/needs")
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
    
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<NeedDTO> getAll() {
        try {
            List<Need> needs = (List<Need>) em.createNamedQuery("getAllNeeds").getResultList();
            return needsToDTOs(needs);
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

            for(TrainingMaterial trainingMaterial: need.getTrainingMaterials()){
                trainingMaterial.removeNeed(need);
            }
            
            
            
            
            em.remove(need);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void enrollTrainingMaterial(int needId, int trainingMaterialId) 
            throws EntityDoesNotExistsException, EntityEnrolledException{
        try {

            TrainingMaterial trainingMaterial = em.find(TrainingMaterial.class, trainingMaterialId);
            if (trainingMaterial == null) {
                throw new EntityDoesNotExistsException("There is no training material with that id.");
            }

            Need need = em.find(Need.class, needId);
            if (need == null) {
                throw new EntityDoesNotExistsException("There is no need with that id.");
            }

            if (trainingMaterial.getNeeds().contains(need)) {
                throw new EntityEnrolledException("That training material already has that need!");
            }

            if (need.getTrainingMaterials().contains(trainingMaterial)) {
                throw new EntityEnrolledException("Training material is already enrolled in that need.");
            }

            need.addTrainingMaterial(trainingMaterial);
            trainingMaterial.addNeed(need);

        } catch (EntityDoesNotExistsException | EntityEnrolledException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void unrollTrainingMaterial(int needId, int trainingMaterialId)
            throws EntityDoesNotExistsException, EntityNotEnrolledException{
        try {
            Need need = em.find(Need.class, needId);
            if(need == null){
                throw new EntityDoesNotExistsException("There is no need with that id.");
            }            
            
            TrainingMaterial trainingMaterial = em.find(TrainingMaterial.class, trainingMaterialId);
            if(trainingMaterial == null){
                throw new EntityDoesNotExistsException("There is no training material with that id.");
            }
            
            if(!need.getTrainingMaterials().contains(trainingMaterial)){
                throw new EntityNotEnrolledException("That training material is not enrolled in that need");
            }            
            
            need.removeTrainingMaterial(trainingMaterial);
            trainingMaterial.removeNeed(need);
        } catch (EntityDoesNotExistsException | EntityNotEnrolledException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    NeedDTO needToDTO(Need need) {
        return new NeedDTO(need.getId(), need.getName());
    }
    
    
    List<NeedDTO> needsToDTOs(List<Need> needs) {
        List<NeedDTO> dtos = new ArrayList<>();
        for (Need need : needs) {
            dtos.add(needToDTO(need));            
        }
        return dtos;
    }
    
}
