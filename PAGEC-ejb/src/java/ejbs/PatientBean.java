package ejbs;

import dtos.NeedDTO;
import dtos.PatientDTO;
import entities.Need;
import entities.Patient;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistsException;
import exceptions.EntityEnrolledException;
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

@Stateless
@Path("/patients")
public class PatientBean {

    @PersistenceContext
    private EntityManager em;

    public void create(int id, String name)
            throws EntityAlreadyExistsException, MyConstraintViolationException {
        try {
            if (em.find(Patient.class, id) != null) {
                throw new EntityAlreadyExistsException("A patient with that id already exists.");
            }

            em.persist(new Patient(id, name));

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
    public List<PatientDTO> getAll() {
        try {
            List<Patient> patients = (List<Patient>) em.createNamedQuery("getAllPatients").getResultList();
            return patientsToDTOs(patients);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void remove(int id) throws EntityDoesNotExistsException {

        try {
            Patient patient = em.find(Patient.class, id);
            if (patient == null) {
                throw new EntityDoesNotExistsException("There is no patient with that id");
            }

            em.remove(patient);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void enrollNeed(int patientId, int needId)
            throws EntityDoesNotExistsException, EntityEnrolledException {
        try {
            Patient patient = em.find(Patient.class, patientId);
            if (patient == null) {
                throw new EntityDoesNotExistsException("There is no patient with that username.");
            }

            Need need = em.find(Need.class, needId);
            if (need == null) {
                throw new EntityDoesNotExistsException("There is no need with that id.");
            }

            if (patient.getNeeds().contains(need)) {
                throw new EntityEnrolledException("That need is already associated to that patient");
            }

            if (need.getPatients().contains(patient)) {
                throw new EntityEnrolledException("That patient is already associated to that need");
            }

            need.addPatient(patient);
            patient.addNeed(need);

        } catch (EntityDoesNotExistsException | EntityEnrolledException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void unrollNeed(int id, int needId)
            throws EntityDoesNotExistsException {
        try {
            Need need = em.find(Need.class, needId);
            if (need == null) {
                throw new EntityDoesNotExistsException("There is no need with that id.");
            }

            Patient patient = em.find(Patient.class, id);
            if (patient == null) {
                throw new EntityDoesNotExistsException("There is no patient with that id.");
            }

            if (!need.getPatients().contains(patient)) {
                throw new Exception("The patient don't have that need.");
            }

            need.removePatient(patient);
            patient.removeNeed(need);

        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("patientNeeds/{patientId}")
    public List<NeedDTO> getNeeds(@PathParam("patientId") int patientId) throws EntityDoesNotExistsException {
        try {
            Patient patient = em.find(Patient.class, patientId);
            if (patient == null) {
                throw new EntityDoesNotExistsException("There is no patient with that id.");
            }
            List<Need> needs = (List<Need>) patient.getNeeds();
            return needsToDTOs(needs);
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public List<NeedDTO> getUnrolledNeeds(int patientId)
            throws EntityDoesNotExistsException {
        try {
            List<Need> needs = (List<Need>) em.createNamedQuery("getAllNeeds").getResultList();
            Patient patient = em.find(Patient.class, patientId);
            if (patient == null) {
                throw new EntityDoesNotExistsException("There is no patient with that id.");
            }
            List<Need> patientNeeds = patient.getNeeds();
            LinkedList<Need> result = new LinkedList<>();
            for (Need need : needs) {
                if (!patientNeeds.contains(need)) {
                    result.add(need);
                }
            }
            return needsToDTOs(result);
        } catch (EntityDoesNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    PatientDTO patientToDTO(Patient patient) {
        if (patient.getCaregiver() != null) {
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
