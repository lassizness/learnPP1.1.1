package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.entity.UsersEntity;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            String sql="CREATE TABLE IF NOT EXISTS Users (id SERIAL PRIMARY KEY, name varchar(255), lastname varchar(255), age smallint)";
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql);
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            String sql = "DROP TABLE IF EXISTS Users";
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql);
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().openSession();
        Transaction tx = null;

        try {
          /*  tx = session.beginTransaction();
            UsersEntity user = new UsersEntity();
            user.setName(name);
            user.setLastname(lastName);
            user.setAge(age);
            session.save(user);
            tx.commit();*/
            String sql="INSERT INTO Users (name, lastname, age) VALUES ('"+name+"','"+lastName+"',"+age+")";
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql);
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            UsersEntity user = session.get(UsersEntity.class, id);//указывается класс описывающий сущность, а не модель
            session.delete(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
      //  List<User> usersEntity = Util.getSessionFactory().openSession().createQuery("from UsersEntity").list();
        List<UsersEntity> usersEntity = Util.getSessionFactory().openSession().createQuery("from UsersEntity").list();
        List<User> users = new ArrayList<>();
        for(UsersEntity var :usersEntity){
            users.add(new User(var.getName(), var.getLastname(), var.getAge()));
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            String sql = "DROP TABLE IF EXISTS Users";
            tx = session.beginTransaction();
            Query query = session.createSQLQuery(sql);
            query.executeUpdate();
            tx.commit();
            createUsersTable();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
