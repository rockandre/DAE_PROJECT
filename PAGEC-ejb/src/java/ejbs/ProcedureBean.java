package ejbs;

import entities.Patient;
import entities.Procedure;
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

    public void create(int id, String name, String descricao)
            throws EntityAlreadyExistsException, MyConstraintViolationException {
        try {
            if (em.find(Procedure.class, id) != null) {
                throw new EntityAlreadyExistsException("A procedure with that id already exists.");
            }

            em.persist(new Procedure(id, name, descricao));

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

            for(Patient patient: procedure.getPatients()){
                patient.removeProcedure(procedure);
            }
            
            em.remove(procedure);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    
    
}
