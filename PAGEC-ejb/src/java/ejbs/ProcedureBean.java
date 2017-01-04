package ejbs;

import dtos.ProcedureDTO;
import entities.Caregiver;
import entities.Need;
import entities.Patient;
import entities.Procedure;
import entities.TrainingMaterial;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
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
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/procedures")
public class ProcedureBean {

    @PersistenceContext
    private EntityManager em;

    public void create(int id, int patientId, String caregiverUsername, int trainingMaterialId)
            throws EntityAlreadyExistsException, MyConstraintViolationException, EntityDoesNotExistsException, EntityNotEnrolledException {
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
            for(Need need: patient.getNeeds()){
                if(trainingMaterial.getNeeds().contains(need)){
                    
                    em.persist(new Procedure(id, patient, caregiver, trainingMaterial));
                    Procedure procedure = em.find(Procedure.class, id);
                    if(procedure == null){
                        throw new EntityDoesNotExistsException("Error creating procedure.");
                    } else {
                        trainingMaterial.addProcedure(procedure);
                        caregiver.addProcedure(procedure);
                        patient.addProcedure(procedure);
                    }
                    return;
                }
            }
            throw new EntityNotEnrolledException("The patient doesn't have any need with that training material!");
            

            

        } catch (EntityAlreadyExistsException | EntityDoesNotExistsException | EntityNotEnrolledException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @PUT
    @Path("/create/")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void createREST(ProcedureDTO procedure)
            throws EntityAlreadyExistsException, MyConstraintViolationException, EntityDoesNotExistsException, EntityNotEnrolledException {
        try {
            if (em.find(Procedure.class, procedure.getId()) != null) {
                throw new EntityAlreadyExistsException("A procedure with that id already exists.");
            }
            Patient patient = em.find(Patient.class, procedure.getPatientId());
            if(patient == null){
                throw new EntityDoesNotExistsException("There is no patient with that id.");
            }
            
            Caregiver caregiver = em.find(Caregiver.class, procedure.getCaregiverUsername());
            if(caregiver == null){
                throw new EntityDoesNotExistsException("There is no caregiver with that username.");
            }
            
            TrainingMaterial trainingMaterial = em.find(TrainingMaterial.class, procedure.getTrainingMaterialId());
            if(trainingMaterial == null){
                throw new EntityDoesNotExistsException("There is no training material with that id.");
            }
            for(Need need: patient.getNeeds()){
                if(trainingMaterial.getNeeds().contains(need)){
                    
                    em.persist(new Procedure(procedure.getId(), patient, caregiver, trainingMaterial));
                    Procedure procedure2 = em.find(Procedure.class, procedure.getId());
                    if(procedure2 == null){
                        throw new EntityDoesNotExistsException("Error creating procedure.");
                    } else {
                        trainingMaterial.addProcedure(procedure2);
                        caregiver.addProcedure(procedure2);
                        patient.addProcedure(procedure2);
                    }
                    return;
                }
            }
            throw new EntityNotEnrolledException("The patient doesn't have any need with that training material!");
            

            

        } catch (EntityAlreadyExistsException | EntityDoesNotExistsException | EntityNotEnrolledException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<ProcedureDTO> getAll() {
        try {
            List<Procedure> procedures = (List<Procedure>) em.createNamedQuery("getAllProcedures").getResultList();
            return proceduresToDTOs(procedures);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
/*
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
    */
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("proceduresByCaregiver/{username}")
    public List<ProcedureDTO> getProceduresByCaregiver(@PathParam("username") String caregiverUsername) {
        try {
            List<Procedure> procedures = (List<Procedure>) em.createNamedQuery("getProceduresByCaregiver")
                    .setParameter("caregiverUsername", caregiverUsername)
                    .getResultList();
            return proceduresToDTOs(procedures);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    ProcedureDTO procedureToDTO(Procedure procedure) {
        return new ProcedureDTO(procedure.getId(), procedure.getDate(), procedure.getPatient().getId(), procedure.getPatient().getName() , procedure.getCaregiver().getUsername(), procedure.getCaregiver().getName(), procedure.getTrainingMaterial().getId(), procedure.getTrainingMaterial().getName());
    }
    
    
    List<ProcedureDTO> proceduresToDTOs(List<Procedure> procedures) {
        List<ProcedureDTO> dtos = new ArrayList<>();
        for (Procedure procedure : procedures) {
            dtos.add(procedureToDTO(procedure));            
        }
        return dtos;
    }
    
}
