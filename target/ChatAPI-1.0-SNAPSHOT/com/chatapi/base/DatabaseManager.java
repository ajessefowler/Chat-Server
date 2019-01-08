package com.chatapi.base;

import com.chatapi.authentication.models.Token;
import com.chatapi.authentication.models.User;
import com.chatapi.base.models.Message;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class DatabaseManager {
    public static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(User.class).addAnnotatedClass(Message.class).addAnnotatedClass(Token.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
        return sessionFactory;
    }

    public User getUser(String username) {
        Session session = getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> cr = cb.createQuery(User.class);
        Root<User> root = cr.from(User.class);
        Predicate condition = cb.equal(root.get("username"), username);
        cr.select(root).where(condition);
        Query<User> query = session.createQuery(cr);
        return query.uniqueResult();
    }

    public Token getToken(String user) {
        Session session = getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Token> cr = cb.createQuery(Token.class);
        Root<Token> root = cr.from(Token.class);
        Predicate condition = cb.equal(root.get("user"), user);
        cr.select(root).where(condition);
        Query<Token> query = session.createQuery(cr);
        return query.uniqueResult();
    }

    public void addToken(Token token) {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        session.save(token);
        session.getTransaction().commit();
        session.close();
    }

    public void addUser(User user) {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
        System.out.println("Created user " + user.getUsername());
    }

    public void addMessage(Message message) {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        session.save(message);
        session.getTransaction().commit();
        session.close();
        System.out.println("Created message to " + message.getRecipient() + ": " + message.getContent());
    }
}
