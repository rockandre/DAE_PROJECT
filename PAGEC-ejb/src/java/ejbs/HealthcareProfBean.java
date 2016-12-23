package ejbs;

import entities.HealthcareProf;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class HealthcareProfBean {

    @PersistenceContext
    private EntityManager em;

    public void create(String username, String password, String name, String email) {
        try {
            if(em.find(HealthcareProf.class, username) != null){
                return;
            }
            em.persist(new HealthcareProf(username, password, name, email));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }}
