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
            
            //TODO - Provavelmente tbm se tem de remover os patients
            
            em.remove(caregiver);
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    } 
    
    public void enrollCaregiver(String username, int patientId) 
            throws EntityDoesNotExistsException{
        try {
            Caregiver caregiver = em.find(Caregiver.class, username);
            if (caregiver == null) {
                throw new EntityDoesNotExistsException("There is no student with that username.");
            }

            Patient patient = em.find(Patient.class, patientId);
            if (patient == null) {
                throw new EntityDoesNotExistsException("There is no subject with that code.");
            }

            if (!caregiver.getPatients().contains(patient)) {
                throw new Exception("Student's course has no such subject.");
            }

            if (patient.getCaregiver().equals(caregiver)) {
                throw new Exception("Student is already enrolled in that subject.");
            }

            caregiver.addPatient(patient);
            patient.setCaregiver(caregiver);
            
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void unrollCaregiver(String username, int patientId) 
            throws EntityDoesNotExistsException {
        try {
            Patient patient = em.find(Patient.class, patientId);
            if(patient == null){
                throw new EntityDoesNotExistsException("There is no Patient with that code.");
            }            
            
            Caregiver caregiver = em.find(Caregiver.class, username);
            if(caregiver == null){
                throw new EntityDoesNotExistsException("There is no Caregiver with that username.");
            }
            
            if(!patient.getCaregiver().equals(caregiver)){
                throw new Exception();
            }            
            
            //Removes...
            
        } catch (EntityDoesNotExistsException  e) {
            throw e;             
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
}
