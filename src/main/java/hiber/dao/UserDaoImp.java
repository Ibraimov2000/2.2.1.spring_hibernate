package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   private final SessionFactory sessionFactory;

   public UserDaoImp(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   public User getUserForCar(String model, int series) {
      String hql = "FROM User users LEFT OUTER JOIN FETCH users.car " +
              "car WHERE car.model=:modelStr " +
              "AND car.series=:seriesStr";
      User user = sessionFactory.getCurrentSession().createQuery(hql, User.class)
              .setParameter("modelStr", model)
              .setParameter("seriesStr", series).uniqueResult();
      return user;
   }


}
