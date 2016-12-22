/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import entities.Caregiver;
import entities.Patient;
import entities.User;
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
@LocalBean
public class CaregiverBean {

    @PersistenceContext
    private EntityManager em;
    
    public void create(String username, String password, String name, String email) {
        try {
            if (em.find(User.class, username) != null) {
                return;
            }
            em.persist(new Caregiver(username, password, name, email));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<Caregiver> getAllCaregivers() {
        try {
            List<Caregiver> caregivers = (List<Caregiver>) em.createNamedQuery("getAllCaregivers").getResultList();
            return caregivers;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public Caregiver getCaregiver(int code) {
        try {
            Caregiver caregiver = em.find(Caregiver.class, code);
            
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
    
    public void enrollPatient(String username, int patientId){
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
                throw new Exception("Patient already has a caregiver.");
            }

            if (caregiver.getPatients().contains(patient)) {
                throw new Exception("Caregiver is already enrolled in that Patient.");
            }

            caregiver.addPatient(patient);
            patient.setCaregiver(caregiver);
        
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void unrollPatient(String username, int patientId) {
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
                throw new Exception("Already has no Caregiver.");
            }

            if(!caregiver.getPatients().contains(patient)) {
                throw new Exception("Caregiver has no such Patient.");
            }
            
            patient.removeCaregiver();
            caregiver.removePatient(patient);
                       
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
}
