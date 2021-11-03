package Se.Iths.alexis;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class UserDaoWithJPAImpl implements UserDao {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestServerNEW");

    @Override
    public List<User> getByName(String name) {
        List<User> list;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        list = em.createQuery("from User u where u.firstName = :firstName", User.class)
                .setParameter("firstName", name).getResultList();
        em.getTransaction().commit();
        return list;
    }


      @Override
    public boolean remove(String firstName) {
        boolean success = false;
       EntityManager em = emf.createEntityManager();
      em.getTransaction().begin();
        User p = em.find(User.class, firstName);
        if (p != null ) {
            em.remove(p);
            success = true;
        }
        em.getTransaction().commit();
        return success;
    }
}
