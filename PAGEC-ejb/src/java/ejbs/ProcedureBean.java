package ejbs;

import entities.Caregiver;
import entities.Patient;
import entities.Procedure;
import entities.TrainingMaterial;
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

@Stateless
@Path("/procedures")
public class ProcedureBean {

    @PersistenceContext
    private EntityManager em;

    public void create(int id, int patientId, String caregiverUsername, int trainingMaterialId)
            throws EntityAlreadyExistsException, MyConstraintViolationException, EntityDoesNotExistsException {
        try {
            if (em.find(Procedure.class, id) != null) {
                throw new EntityAlreadyExistsException("A procedure with that id already exists.");
            }
            Patient patient = em.find(Patient.class, patientId);
            if(patient == null){
                throw new EntityDoesNotExistsException("There is no patient with that id.");
            }
            
            Caregiver caregiver = em.find(Caregiver.class, caregiverUsername);
            if(caregiver == null){
                throw new EntityDoesNotExistsException("There is no caregiver with that username.");
            }
            
            TrainingMaterial trainingMaterial = em.find(TrainingMaterial.class, trainingMaterialId);
            if(trainingMaterial == null){
                throw new EntityDoesNotExistsException("There is no training material with that id.");
            }
            

            em.persist(new Procedure(id, patient, caregiver, trainingMaterial));

        } catch (EntityAlreadyExistsException | EntityDoesNotExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<Procedure> getAll() {
        try {
            List<Procedure> procedures = (List<Procedure>) em.createNamedQuery("getAllProcedures").getResultList();
            return procedures;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void remove(int id) throws EntityDoesNotExistsException {

        try {
            Procedure procedure = em.find(Procedure.class, id);
            if (procedure == null) {
                throw new EntityDoesNotExistsException("There is no patient with that id");
            }
            //falta remover de onde é usada
            
            em.remove(procedure);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    
    
}
