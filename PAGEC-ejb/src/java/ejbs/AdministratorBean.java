package ejbs;

import entities.Administrator;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class AdministratorBean {

    @PersistenceContext
    private EntityManager em;

    public void create(String username, String password, String name, String email) {
        try {
            if(em.find(Administrator.class, username) != null){
                return;
            }
            em.persist(new Administrator(username, password, name, email));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }}
