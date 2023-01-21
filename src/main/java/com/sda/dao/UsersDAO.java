package com.sda.dao;

import com.sda.db.HibernateUtils;
import com.sda.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.crypto.SealedObject;
import java.util.List;

public class UsersDAO {

    public void create(User user) {
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        }
    }

    public boolean deleteByUsername(String username) {
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.find(User.class, username);
            boolean exist = user != null;
            if (exist) {
                session.remove(user);
            }
            transaction.commit();
            return exist;
        }
    }

    public List<User> findAll() {
        Session session = HibernateUtils.openSession();
        String query = "SELECT u FROM User u";
        List<User> users = session.createQuery(query, User.class).list();
        session.close();
        return users;
    }

    public User findUserByUsername(String username) {
        Session session = HibernateUtils.openSession();
        User user = session.find(User.class, username);
        session.close();
        return user;
    }

    public void update(User updatedUser) {
        try (Session session = HibernateUtils.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(updatedUser);
            transaction.commit();
        }
    }


}