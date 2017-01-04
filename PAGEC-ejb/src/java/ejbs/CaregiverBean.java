/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.CaregiverDTO;
import dtos.PatientDTO;
import dtos.TrainingMaterialDTO;
import entities.Caregiver;
import entities.Need;
import entities.Patient;
import entities.TrainingMaterial;
import entities.User;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.EntityEnrolledException;
import exceptions.EntityNotEnrolledException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author rockandre
 */
@Stateless
@Path("/caregivers")
public class CaregiverBean {

    @PersistenceContext
    private EntityManager em;
    
    public void create(String username, String password, String name, String email) 
            throws EntityAlreadyExistsException, MyConstraintViolationException{
        try {
            if (em.find(User.class, username) != null) {
                throw new EntityAlreadyExistsException("A user with that username already exists.");
            }
            em.persist(new Caregiver(username, password, name, email));
        } catch (EntityAlreadyExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<CaregiverDTO> getAll() {
        try {
            List<Caregiver> caregivers = (List<Caregiver>) em.createNamedQuery("getAllCaregivers").getResultList();
            return caregiversToDTOs(caregivers);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public CaregiverDTO getCaregiver(String username) {
        try {
            Caregiver caregiver = em.find(Caregiver.class, username);
            
            return caregiverToDTO(caregiver);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<CaregiverDTO> searchCaregiver (String name) {
        try {
            List<Caregiver> caregivers = em.createNamedQuery("searchCaregivers").setParameter("name", name).getResultList();
            
            return caregiversToDTOs(caregivers);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    /*
    public void remove(int code) throws EntityDoesNotExistsException {
        try {
            Caregiver caregiver = em.find(Caregiver.class, code);
            if (caregiver == null) {
                throw new EntityDoesNotExistsException("There is no Caregiver with that code.");
            }
            
            for(Patient patient : caregiver.getPatients()){
                patient.removeCaregiver();
            }
            
            em.remove(caregiver);
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    } 
    */
    public void enrollPatient(String username, int patientId) 
            throws EntityDoesNotExistsException, EntityEnrolledException {
        try {
            Caregiver caregiver = em.find(Caregiver.class, username);
            if (caregiver == null) {
                throw new EntityDoesNotExistsException("There is no caregiver with that username.");
            }

            Patient patient = em.find(Patient.class, patientId);
            if (patient == null) {
                throw new EntityDoesNotExistsException("There is no patient with that ID.");
            }

            if (patient.getCaregiver() != null) {
                throw new EntityEnrolledException("Patient already has a caregiver!");
            }


            caregiver.addPatient(patient);
            patient.setCaregiver(caregiver);
        
        } catch (EntityDoesNotExistsException | EntityEnrolledException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void unrollPatient(String username, int patientId) 
        throws EntityDoesNotExistsException, EntityNotEnrolledException {
        try {
            Patient patient = em.find(Patient.class, patientId);
            if(patient == null){
                throw new EntityDoesNotExistsException("There is no Patient with that id.");
            }            
            
            Caregiver caregiver = em.find(Caregiver.class, username);
            if(caregiver == null){
                throw new EntityDoesNotExistsException("There is no Caregiver with that username.");
            }
            
            if(patient.getCaregiver() == null){
                throw new EntityNotEnrolledException("The patient already has no caregiver!");
            }
            
            patient.removeCaregiver();
            caregiver.removePatient(patient);
                  
        } catch (EntityDoesNotExistsException | EntityNotEnrolledException e) {
            throw e;  
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    
    public void update(String username, String password, String name, String email) 
        throws EntityDoesNotExistsException, MyConstraintViolationException{
        try {
            Caregiver caregiver = em.find(Caregiver.class, username);
            if (caregiver == null) {
                throw new EntityDoesNotExistsException("There is no caregiver with that username.");
            }
            
            caregiver.setCleanPassword(password); //Quando faz update já vem em hash
            caregiver.setName(name);
            caregiver.setEmail(email);
            em.merge(caregiver);
            
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
            Caregiver caregiver = em.find(Caregiver.class, username);
            if (caregiver == null) {
                throw new EntityDoesNotExistsException("There is no caregiver with that username.");
            }
            
            
            em.remove(caregiver);
        
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("enrolledPatients/{username}")
    public List<PatientDTO> getEnrolledPatients(@PathParam("username") String username) throws EntityDoesNotExistsException{
        try {
            Caregiver caregiver = em.find(Caregiver.class, username);
            if (caregiver == null) {
                throw new EntityDoesNotExistsException("There is no caregiver with that username.");
            }           
            List<Patient> patientsEnrolled = (List<Patient>) caregiver.getPatients();
            return  patientsToDTOs(patientsEnrolled);
        } catch (EntityDoesNotExistsException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<PatientDTO> getUnrolledPatients() {
        try {
            List<Patient> patients = (List<Patient>) em.createNamedQuery("getAllPatients").getResultList();
            LinkedList<Patient> patientsUnrolled = new LinkedList<>();
            for(Patient patient: patients){
                if(patient.getCaregiver() == null){
                    patientsUnrolled.add(patient);
                }
            }

            return patientsToDTOs(patientsUnrolled);            
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("caregiverTrainingMaterials/{username}")
    public List<TrainingMaterialDTO> getCaregiverTrainingMaterials(@PathParam("username") String username) {
        try{
            Caregiver caregiver = em.find(Caregiver.class, username);
            if (caregiver == null) {
                throw new EntityDoesNotExistsException("There is no caregiver with that username.");
            }  
            
            LinkedList<TrainingMaterial> trainingMaterials = new LinkedList<>();
            LinkedList<TrainingMaterial> trainingMaterialsClean = new LinkedList<>();
            List<Patient> patients = (List<Patient>) caregiver.getPatients();
            
            List<Need> patientsNeeds = new ArrayList<>();
            for(Patient patient: patients){
                patientsNeeds.addAll(patient.getNeeds());
            }
            
            for(Need need: patientsNeeds){
                trainingMaterials.addAll(need.getTrainingMaterials());
            }
            
            for(TrainingMaterial trainingMaterial: trainingMaterials){
                if(!trainingMaterialsClean.contains(trainingMaterial)){
                    trainingMaterialsClean.add(trainingMaterial);
                }
            }
            
            return trainingMaterialsToDTOs(trainingMaterialsClean);
            
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
          
    }
    
    CaregiverDTO caregiverToDTO(Caregiver caregiver) {
        return new CaregiverDTO(
                caregiver.getUsername(),
                caregiver.getPassword(),
                caregiver.getName(),
                caregiver.getEmail());
    }
    
    
    List<CaregiverDTO> caregiversToDTOs(List<Caregiver> caregivers) {
        List<CaregiverDTO> dtos = new ArrayList<>();
        for (Caregiver c : caregivers) {
            dtos.add(new CaregiverDTO(c.getUsername(), c.getPassword(), c.getName(), c.getEmail()));            
        }
        return dtos;
    }
    
    PatientDTO patientToDTO(Patient patient) {
        if(patient.getCaregiver() != null){
            return new PatientDTO(patient.getId(), patient.getName(), patient.getCaregiver().getUsername());
        } else {
            return new PatientDTO(patient.getId(), patient.getName(), null); 
        }
        
    }
    
    
    List<PatientDTO> patientsToDTOs(List<Patient> patients) {
        List<PatientDTO> dtos = new ArrayList<>();
        for (Patient patient : patients) {
            dtos.add(patientToDTO(patient));            
        }
        return dtos;
    }
    
    TrainingMaterialDTO trainingMaterialToDTO(TrainingMaterial trainingMaterial) {
        
        return new TrainingMaterialDTO(trainingMaterial.getId(), trainingMaterial.getName(), 
                trainingMaterial.getTipoTM(), trainingMaterial.getLink());
    }
    
    
    List<TrainingMaterialDTO> trainingMaterialsToDTOs(List<TrainingMaterial> trainingMaterials) {
        List<TrainingMaterialDTO> dtos = new ArrayList<>();
        for (TrainingMaterial trainingMaterial : trainingMaterials) {
            dtos.add(trainingMaterialToDTO(trainingMaterial));            
        }
        return dtos;
    }
}
