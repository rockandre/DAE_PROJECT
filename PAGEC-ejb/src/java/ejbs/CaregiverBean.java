/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import entities.Administrator;
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
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

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
                throw new EntityAlreadyExistsException("A administrator with that username already exists.");
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
    
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<Caregiver> getAll() {
        try {
            List<Caregiver> caregivers = (List<Caregiver>) em.createNamedQuery("getAllCaregivers").getResultList();
            return caregivers;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public Caregiver getCaregiver(String username) {
        try {
            Caregiver caregiver = em.find(Caregiver.class, username);
            
            return caregiver;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<Caregiver> searchCaregiver (String name) {
        try {
            List<Caregiver> caregivers = em.createNamedQuery("searchCaregivers").setParameter("name", name).getResultList();
            
            return caregivers;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
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
                throw new EntityDoesNotExistsException("There is no Patient with that code.");
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
            
            caregiver.setPassword(password);
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
    
    public List<Patient> getEnrolledPatients(String caregiverUsername) throws EntityDoesNotExistsException{
        try {
            Caregiver caregiver = em.find(Caregiver.class, caregiverUsername);
            if (caregiver == null) {
                throw new EntityDoesNotExistsException("There is no caregiver with that username.");
            }           
            List<Patient> patientsEnrolled = (List<Patient>) caregiver.getPatients();
            return patientsEnrolled;
        } catch (EntityDoesNotExistsException e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<Patient> getUnrolledPatients() {
        try {
            List<Patient> patients = (List<Patient>) em.createNamedQuery("getAllPatients").getResultList();
            LinkedList<Patient> patientsUnrolled = new LinkedList<>();
            for(Patient patient: patients){
                if(patient.getCaregiver() == null){
                    patientsUnrolled.add(patient);
                }
            }

            return patientsUnrolled;            
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public List<TrainingMaterial> getCaregiverTrainingMaterials(String username) {
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
            
            return trainingMaterialsClean;
            
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
          
    }
}
